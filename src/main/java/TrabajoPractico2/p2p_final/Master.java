package TrabajoPractico2.p2p_final;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Master {
	private final static Logger log = LoggerFactory.getLogger(Master.class);
	private static ArrayList<Peer_Data> listOfPeerData = new ArrayList<Peer_Data>();
	private static int _PORTMASTER = 9000;
	private static ServerSocket socketMaster;
	
	
	
	public void update(ArrayList<Peer_Data> listOfPeerData) {
		this.listOfPeerData=listOfPeerData;
	}
	

	public static void createServer() {
		try {
		socketMaster = new ServerSocket(_PORTMASTER);
		log.info("[SERVER] - Server socket has created as port: " + _PORTMASTER);
			while (true) {
					Socket peerSocket = socketMaster.accept();
					log.info("[SERVER] - Client accepted. Configuration sended");
					MasterRunnable masterRunnable = new MasterRunnable(peerSocket,listOfPeerData);
					Thread tMasterRunnable = new Thread(masterRunnable);
					tMasterRunnable.start();
			}
		} catch (IOException e) {
			log.error("[SERVER] - " + e.getMessage());
			}
}
	
	public static void main(String[] args) {
		String packetName=Master.class.getSimpleName().toString();
        System.setProperty("log.name",packetName);
		createServer();
	}


}
