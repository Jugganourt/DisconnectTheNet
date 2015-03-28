package map;

public class Link {
	private String from;
	private String to;

	public Link(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	@Override
	public boolean equals(Object o) {
		Link link = (Link) o;
		return (from.equals(link.getTo()) && from.equals(link.getTo())) || (from.equals(link.getFrom()) && to.equals(link.getTo()));

	}
	
	@Override
	public String toString(){
		return "(" + from + ", " + to + ")";
	}
}
