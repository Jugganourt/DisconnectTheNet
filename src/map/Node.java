package map;

import java.util.ArrayList;

public class Node {
	
	//status constants
	public static final int STATUS_CONNECTED = 0;
	public static final int STATUS_CAPTURED = 1;
	public static final int STATUS_DISCONNECTED = 2;
	

	//Connections
	private ArrayList<Node> connectedTo;
	private int numOfConnections;
	private int status;

	private ArrayList<String> topics;
	private String url;	
	
	private int trust;

	
	public Node(){
		this.connectedTo = new ArrayList<Node>();
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
	
	public Node(String url, int trust, ArrayList<String> topics){
		this();
		this.url = url;
		this.trust = trust;
		this.topics = topics;
	}
	
	public int stepsToConnect(Node node){
		if (isConnectedTo(node)) {
			return 1;
		}else{
			for (Node node2 : connectedTo) {
				if (node2.isConnectedTo(node)) {
					return 2;
				}
			}
		}
		return -1;
	}
	
	public boolean isConnectedTo(Node node){
		return connectedTo.contains(node);
	}
	
	public void addConnection(Node node){
		if (!connectedTo.contains(node)) {
			connectedTo.add(node);
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
	
	public int getStatus(){
		return this.status;
	}
}
