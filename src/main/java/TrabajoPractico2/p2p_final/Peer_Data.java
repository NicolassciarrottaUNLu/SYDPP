package TrabajoPractico2.p2p_final;

import java.util.ArrayList;

public class Peer_Data {
	private int name;
	private int _PORTSERVER;
	private ArrayList<String> files = new ArrayList<String>();
	private String directory;
	private String share;
	
	public Peer_Data(int name, ArrayList<String> files, String directory,String share, int _PORTSERVER) {
		this.name = name;
		this.files = files;
		this.directory = directory;
		this.share=share;
		this._PORTSERVER = _PORTSERVER;
	}

	public int getName() {
		return this.name;
	}
	
	public String getDirectory() {
		return this.directory;
	}
	
	public int getPortServer() {
		return this._PORTSERVER;
	}
	
	public ArrayList<String> getFiles(){
		return files;
	}
	public String getRute() {
		return this.share;
	}

}
