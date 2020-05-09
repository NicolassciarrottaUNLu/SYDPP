package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Replica {

	private String name;
	private int _PORTSERVERREPLICA;
	ServerSocket socketReplica;
	Socket createReplica;
	private String server = "localhost";
	private int _PORTSERVERMASTER;
	
	public Replica(String name, int _PORTSERVERREPLICA,int _PORTSERVERMASTER) {
		this.name=name;
		this._PORTSERVERREPLICA=_PORTSERVERREPLICA;
		this._PORTSERVERMASTER = _PORTSERVERMASTER;
	}
	
	public void startReplica() {
		System.out.println("Creating replica of master server");
		try {
			createReplica = new Socket(server, _PORTSERVERMASTER);
			System.out.println("Connected");
			
			BufferedReader inputChannel = new BufferedReader (new InputStreamReader (createReplica.getInputStream()));
			PrintWriter outputChannel = new PrintWriter (createReplica.getOutputStream(),true);
			
				outputChannel.println("getReplica");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
