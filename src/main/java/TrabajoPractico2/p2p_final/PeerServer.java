package TrabajoPractico2.p2p_final;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeerServer implements Runnable {

	private ServerSocket serverPeer;
	private int _PORTSERVER;
	private final static Logger log = LoggerFactory.getLogger(Peer.class);
	

	public PeerServer(int _PORTSERVER) {
		this._PORTSERVER=_PORTSERVER;
	}

	@Override
	public void run() {
		
		try {
			serverPeer = new ServerSocket(_PORTSERVER);
			log.info("[PEER SERVER] - Server Peer created");
			
				while(true) {
					Socket clientPeer = serverPeer.accept();
						log.info("[PEER SERVER] - Client accepted");
						
							PeerServerThread peerServerThread = new PeerServerThread(clientPeer);
							Thread tPeerServerThread = new Thread(peerServerThread);
							tPeerServerThread.start();
				}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
