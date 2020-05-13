package red_flexible;

public class Node {

	private int position;
	private String _SERVER;
	private int port;
	private int tasks;
	// cambiar por tasks
	public Node() {
		
	}
	
	public Node(int position,String _SERVER, int port, int charge) {
		super();
		this.position=position;
		this._SERVER = _SERVER;
		this.port = port;
		this.tasks = charge;
	}
	
	
	public String get_SERVER() {
		return _SERVER;
	}
	public int getPort() {
		return port;
	}
	public int getTasks() {
		return tasks;
	}
	
	public String getDirection() {
		return (_SERVER + ":" + port);
	}
	
	public void addTasks() {
		this.tasks++;
	}
	
	public void substracTasks() {
		this.tasks--;
	}

	public int getPosition() {
		return this.position;
	}
	
	
}
