package p2p;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeerClient implements Runnable{

	private final static Logger log = LoggerFactory.getLogger(Peer.class);
	private Socket socketClient;
	private String _SERVER = "localhost";
	private int _PORTSERVER;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;
	private String file;
	private String directoryOfDownload;
	private String guardo;
	
	public PeerClient(int _PORTSERVER,String file,String directoryOfDownload,String guardo) {
		this._PORTSERVER = _PORTSERVER;
		this.file=file;
		this.directoryOfDownload=directoryOfDownload;
		this.guardo=guardo;
	}


	@Override
	public void run() {
		try {
			socketClient = new Socket(_SERVER,_PORTSERVER);
			log.info("[PEER] - Connection established ");
			
			inputChannel =  new BufferedReader (new InputStreamReader (socketClient.getInputStream()));
			outputChannel = new PrintWriter (socketClient.getOutputStream(),true);
			
			String route = directoryOfDownload +"/" + file;
			outputChannel.println("getFile@"+route);
			
			byte[] received = new byte[1024];
			
			int in;
			
			String archive= guardo +"/"+ file;
			
			BufferedInputStream bis = new BufferedInputStream(socketClient.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archive));
			
			while ((in = bis.read(received)) != -1){
				 bos.write(received,0,in);
				 }
				
			bos.close();
		
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
