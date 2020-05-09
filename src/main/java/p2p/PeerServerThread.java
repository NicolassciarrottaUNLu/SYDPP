package p2p;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeerServerThread implements Runnable {

	private Socket clientPeer;
	private final static Logger log = LoggerFactory.getLogger(Peer.class);
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;

	public PeerServerThread(Socket clientPeer) {
		this.clientPeer=clientPeer;
	}

	@Override
	public void run() {
		try {
			inputChannel =  new BufferedReader (new InputStreamReader (clientPeer.getInputStream()));
			outputChannel = new PrintWriter (clientPeer.getOutputStream(),true);
			
			String msgPeerClient = inputChannel.readLine();
			
				if (msgPeerClient.startsWith("getFile")) {
					String msgPeerClientParts[] = msgPeerClient.split("@");
					
					//msgPeerClientParts[1] ->Route 
					
					File localFile = new File(msgPeerClientParts[1]);
					
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile));
					BufferedOutputStream bos = new BufferedOutputStream(clientPeer.getOutputStream());
				
										
					byte[] byteArray = new byte[1024];
					int in;
						while((in= bis.read(byteArray))!= -1) {
							bos.write(byteArray,0,in);
						}
					bis.close();
					bos.close();
					
					
				}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
