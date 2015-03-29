package map;


import java.util.ArrayList;

public class Node {
	
	//status constants
	public static final int STATUS_CONNECTED = 0;
	public static final int STATUS_CAPTURED = 1;
	public static final int STATUS_DISCONNECTED = 2;
	

	//Connections
	private ArrayList<Connection> connectedTo;
	private int numOfConnections;
	private int status;

	private ArrayList<String> topics;
	private String url;	
	private int trust;
	
	private double latitude, longitude;
	private int x;
	private int y;

	public Node(){
		this.connectedTo = new ArrayList<Connection>();
		this.topics = new ArrayList<String>();
		this.numOfConnections = 0;
		this.status = STATUS_CONNECTED;
		this.trust = 0;
		this.url = "";
	}
	
	public Node(String url, int trust){
		this();
		this.url = url;
		this.trust = trust;
	}
	
	public Node(String url){
		this();
		this.url = url;
	}
	
	public Node(String url, int trust, ArrayList<String> topics){
		this();
		this.url = url;
		this.trust = trust;
		this.topics = topics;
	}
	
	/**
	 * returns number of steps it takes to reach node from current node, returns -1 if greater than 2
	 */
//	public int stepsToConnect(Node node){
//		if (isConnectedTo(node)) {
//			return 1;
//		}else{
//			for (Node node2 : connectedTo) {
//				if (node2.isConnectedTo(node)) {
//					return 2;
//				}
//			}
//		}
//		return -1;
//	}
//	
	public boolean isConnectedTo(Node node){
		for (Connection connection : connectedTo) {
			if (connection.getNode(1).equals(node) || connection.getNode(2).equals(node)) {
				return true;
			}
		}
		return false;
	}
	
	public void addConnection(Connection connection){
		if (!connectedTo.contains(connection)) {
			connectedTo.add(connection);
			numOfConnections++;
		}
	}
	
	public void removeConnection(Connection connection){
		if (connectedTo.remove(connection)) {
			numOfConnections--;
		}
		
	}
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Node)) {
			return false;
		} else {
			if (this.url.equals(((Node)obj).getURL())) {
				return true;
			}
			return false;
		}
	}
	
	public String getURL(){
		return this.url;
	}
	
	public int getTrust(){
		return this.trust;
	}
	
	public void setTrust(int trust){
		this.trust = trust;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public int getNumberOfConnections(){
		int res = 0;
		
		for (Connection connection : connectedTo) {
			if (connection.getState() == Connection.State.Active) {
				res++;
			}
		}

		return res;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public static void main(String[] args) {
		Node a = new Node("URL", 10);
		Node b = new Node("URM", 10);
		Node c = new Node("URN", 10);
		
		//a.addConnection(b);
		//a.addConnection(c);
		System.out.println(a.getNumberOfConnections());
		
	}

	public void setX(int x) {
		this.x = x;
		
	}

	public void setY(int y) {
		this.y = y;
		
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}

	public void setUrl(String substring) {
		this.url = substring;
		
	}

	public void removeConnection(Node node) {
		for (int i = connectedTo.size()-1; i >= 0; i--) {
			if (connectedTo.get(i).getNode(1).equals(node) || connectedTo.get(i).getNode(2).equals(node) ) {
				connectedTo.remove(i);
				numOfConnections--;
			}
		}
		
	}

}
