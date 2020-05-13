package red_flexible;


import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.rmi.server.UnicastServerRef;

public class Balanceador{


	/*
	 * @Threshold definition
	 * 0 clients -> light 
	 * 0-3 clients -> normal
	 * 4 clients -> alert
	 * 5 clients -> critic
	 * 
	 */
	
	private int _LIGHT = 0; 
	private int _NORMAL= 3; 
	private int _ALERT = 4;
	private int _CRITIC = 5;
	private int _PORT = 9000;
	private int _INITIALSERVERS = 3;
	private String _SERVER = "localhost";
	private ArrayList<Node> listNodes = new ArrayList<Node>();
	private static Logger log = LoggerFactory.getLogger(Balanceador.class);
	
	
	private int generatePort(int endPort) {
		if (endPort>9) {
			return (9020 + endPort);
		}else
			return(9010 + endPort);
	}
	
	
	private void loadFirstNodes() {
		for(int i =0; i<_INITIALSERVERS;i++) {
			Node node = new Node(i,_SERVER,generatePort(i),_LIGHT);
			listNodes.add(node);
			log.info("Initial server created: " + node.getDirection());
		}
	}
	
	
	/*
	 * create a new node and new thread
	 */
	public Node newNode(){
		Node node = null;
			synchronized(listNodes){
				//Assigned the last position synchronized
				node = new Node(listNodes.size(),_SERVER,generatePort(listNodes.size()),_LIGHT);
				listNodes.add(node);
				log.info("Node "+ node.getDirection() +" created");
			}
			Server server = new Server(node.getPort()); //Create a new instace of Server
			Thread tserver = new Thread(server); //Create a new Thread
			tserver.start();		
		return node;
	}
	
	
	public void stopNode(Node node) throws NotBoundException {
		try {
			Registry registry = LocateRegistry.getRegistry(node.get_SERVER(),node.getPort());
			IControl remoteInt = (IControl) registry.lookup("service");
			remoteInt.serverStop();
			synchronized(listNodes) {
				listNodes.remove(node);
			}
			
		} catch (NumberFormatException |RemoteException e) {
			log.error("Error - Can't stop server");
		}
	}
	
	/*
	 *Assign task depending node load
	 */
	
	public synchronized  Node assignTask() {
		Collections.sort(listNodes, new SortMyList());
		//get first on the list
		Node nodo = listNodes.get(0);
		String direction = nodo.getDirection();
			if(nodo.getTasks()<=_NORMAL) {
				nodo.addTasks();
				log.info("SERVER " + nodo.getDirection() + " STATUS is NORMAL");
				log.info("Task assigned to " + nodo.getDirection());
			}else if((nodo.getTasks()>=_ALERT) && (nodo.getTasks()<_CRITIC)) { 
				nodo.addTasks();
				log.info("SERVER " + nodo.getDirection() + " STATUS is ALERT");
				log.info("Task assigned to " + nodo.getDirection());
			}else if(nodo.getTasks()==_CRITIC){
				Node nNew = newNode(); //Creo el nodo pero asigno la tarea al nodo anterior
				nodo.addTasks();
				log.info("SERVER " + direction + " STATUS is CRITIC. This server isn't work for now");
				log.info("New server has created as " + nNew.getDirection());
				log.info("Task assigned to " + nNew.getDirection());
			}
			
		
	return nodo;
	}
	
	
	public synchronized void terminateTask(int nodePosition) throws NotBoundException {
		synchronized (listNodes) {
			listNodes.get(nodePosition).substracTasks();
			log.info("Task terminate for node: " + listNodes.get(nodePosition).getDirection());
			if ((listNodes.get(nodePosition).getTasks()==0) && (listNodes.size()>_INITIALSERVERS)) {
				stopNode(listNodes.get(nodePosition));
			}
	}

	}
	
	
	public void serveCostumer() {
		
	}
	
	public void initBalancer() throws RemoteException {
		
		Remote iRemote = UnicastRemoteObject.exportObject( new IRemoteInt() {
		
			@Override
			public ArrayList<Integer> suma(ArrayList<Integer> numerosA, ArrayList<Integer> numerosB)
					throws RemoteException {
					Registry registry;
					ArrayList<Integer> resultado = new ArrayList<Integer>();
					try {
						Node nodo = assignTask();
						int i = nodo.getPosition();
						registry = LocateRegistry.getRegistry(nodo.get_SERVER(), nodo.getPort());
						IRemoteInt iRBalancer = (IRemoteInt) registry.lookup("service");
						resultado=iRBalancer.suma(numerosA, numerosB);
						//terminateTask(i);
					}catch (Exception e) {
						System.out.println("In balancer " + e.getMessage());
					}
				return resultado;
			}
			
		},0);
		
		if (listNodes.size()>1) {
			Registry registry = LocateRegistry.createRegistry(_PORT);
			registry.rebind("service", iRemote);
			log.info("Balancer RMI initiated");
		}
		
	}
	
	
}