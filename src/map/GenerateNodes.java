package map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import requests.GetLinks;
import requests.GetLocation;

public class GenerateNodes {

	private Map<String, Node> urlToNode = new TreeMap<String, Node>();
	private Set<Link> linksFound = new HashSet<Link>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();

	private final int upperChance = 100;
	private final int linkChance = 15;

	public GenerateNodes(String url, int numResults) {

		GetLinks start = new GetLinks(url, numResults);

		Random r = new Random();

		for (String site : start.getField("Domain")) {
			if (r.nextInt(upperChance) < linkChance)
				linksFound.add(new Link(url, site));
		}

		Node temp = new Node(url);
		GetLocation loc = new GetLocation(url);
		temp.setLatitude(loc.getLatitude());
		temp.setLongitude(loc.getLongitude());
		urlToNode.put(url, temp);

		for (String site : start.getField("Domain")) {
			GetLinks siteLinks = new GetLinks(site, numResults);

			for (String site2 : siteLinks.getField("Domain")) {
				if (r.nextInt(upperChance) < linkChance)
					linksFound.add(new Link(site, site2));

				if (!urlToNode.containsKey(site)) {
					Node node = new Node(site);
					loc = new GetLocation(site);
					node.setLatitude(loc.getLatitude());
					node.setLongitude(loc.getLongitude());
					urlToNode.put(site, node);
				}
				if (!urlToNode.containsKey(site2)) {
					Node node = new Node(site2);
					loc = new GetLocation(site);
					node.setLatitude(loc.getLatitude());
					node.setLongitude(loc.getLongitude());
					urlToNode.put(site2, node);

				}
			}

		}

		for (Link link : linksFound) {
			Node n1 = urlToNode.get(link.getFrom());
			Node n2 = urlToNode.get(link.getTo());

			Connection conn = new Connection(n1, n2);
			if (!connections.contains(conn)) {
				connections.add(conn);
				n1.addConnection(conn);
				n2.addConnection(conn);
			}

		}

	}

	public Map<String, Node> getUrlToNode() {
		return urlToNode;
	}

	public ArrayList<Connection> getConnections(){
		return connections;
	}
	
	public static void main(String[] args) {
		new GenerateNodes("mit.edu", 110);

	}

}
