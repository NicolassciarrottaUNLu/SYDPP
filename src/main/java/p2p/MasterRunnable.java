package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterRunnable implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(Master.class);
	private ArrayList<Peer_Data> listOfPeerData = new ArrayList<Peer_Data>();
	private Socket peerSocket;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;

	private Master replica = new Master();

	public MasterRunnable(Socket peerSocket, ArrayList<Peer_Data> listOfPeerData) {
		this.peerSocket=peerSocket;
		this.listOfPeerData=listOfPeerData;
	}

	private int getNode() {
		int nodeFinal = listOfPeerData.size();
		nodeFinal++;
		return (nodeFinal);
	}
	
	
	private ArrayList<String> convertStringToArray(String received) {
		ArrayList<String> files = new ArrayList<String>();
		String copy = received.substring(1,(received.length()-1));
		String a = copy.replace(" ", "");
		String array[] = a.split(",");
		for(int i=0; i<array.length;i++) {
			files.add(array[i]);
		}
		return files;
	}

	public void run() {
		
		try {
			inputChannel =  new BufferedReader (new InputStreamReader (peerSocket.getInputStream()));
			outputChannel = new PrintWriter (peerSocket.getOutputStream(),true);
			
			String msgPeer = inputChannel.readLine();
			System.out.println(msgPeer);
			
			while(!msgPeer.startsWith("bye")) {
					if(msgPeer.startsWith("initialConfiguration")) {
						String msgPeerParts[]=msgPeer.split("@");
						
						int node = getNode();
						ArrayList<String> files = convertStringToArray(msgPeerParts[1]);
						
						Random rnd = new Random();
						int port = peerSocket.getPort()+ rnd.nextInt(200);
						
							Peer_Data peer = new Peer_Data(node,files,msgPeerParts[2],msgPeerParts[3],port);
							listOfPeerData.add(peer);
							
							outputChannel.println(node+"@"+port);
					}
							if(msgPeer.startsWith("download")) {
								boolean encontre = false;
								String msgPeerParts[]=msgPeer.split("@");
									for(int i=0; i<listOfPeerData.size();i++) {
										for(int j=0;j<listOfPeerData.get(i).getFiles().size();j++) {
											if(listOfPeerData.get(i).getFiles().get(j).equalsIgnoreCase(msgPeerParts[1]) && !encontre) {
												outputChannel.println((listOfPeerData.get(i).getPortServer())+"@"+listOfPeerData.get(i).getRute());
												encontre = true;
											}
										}
									}
									if(!encontre) {
										outputChannel.println("fileNotFound");
										
									}
							} 
							
							if (msgPeer.startsWith("updateFiles")) {
								String msgPeerParts[]=msgPeer.split("@");
								int name = Integer.parseInt(msgPeerParts[1]);
									for(int i=0;i<listOfPeerData.size();i++) {
										if(listOfPeerData.get(i).getName()==name) {
											listOfPeerData.get(i).addFile(msgPeerParts[2]);
										}
									}
								log.info("[SERVER] - Update files of Peer");
							}
							
							
							//Posibilidad de levantar la replica.
							//Solo update
							replica.update(listOfPeerData);
	
							msgPeer = inputChannel.readLine();
							if(msgPeer.startsWith("Bye")){
								String msgPeerParts[] = msgPeer.split("@");
								int name = Integer.parseInt(msgPeerParts[1]);
									for(int i=0;i<listOfPeerData.size();i++) {
										if(listOfPeerData.get(i).getName()==name) {
											listOfPeerData.remove(i);
										}
									}
									log.info("[SERVER] - Peer remove");
									outputChannel.close();
									inputChannel.close();
							}
			}
			
			
		}
	
		 catch (IOException e) {
			 if (!peerSocket.isConnected()) {
				 log.error("[PEER] - ERROR Client disconnect - " + e.getMessage());
			 }
		}
		
		
		
		
}
}