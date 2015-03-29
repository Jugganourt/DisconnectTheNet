package map;

public class Connection {

	private Node n1,n2;
	private State state;
	
	public Connection(Node n1, Node n2){
		this.n1 = n1;
		this.n2 = n2;
		setState(State.Active);
	}
	
	public Node getNode(int node){
		if (node == 1) {
			return n1;
		} else {
			return n2;
		}
	}
	
	public State getState(){
		return state;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public String toString(){
		return n1.getURL() + " : " + n2.getURL();
	}
	
	@Override
	public boolean equals(Object o){
		Connection c = (Connection) o;
		
		return (this.n1.equals(c.n1) && this.n2.equals(c.n2)) || (this.n1.equals(c.n2) && this.n2.equals(c.n1));
		
	}
	
	public enum State{
		Active, Unactive;
	}
}
