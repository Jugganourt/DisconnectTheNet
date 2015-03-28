package map;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import requests.GetLinks;

public class GenerateNodes {

	Map<String, Node> urlToNode = new TreeMap<String, Node>();
	Set<Link> linksFound = new HashSet<Link>();

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
		urlToNode.put(url, temp);

		for (String site : start.getField("Domain")) {
			GetLinks siteLinks = new GetLinks(site, numResults);

			for (String site2 : siteLinks.getField("Domain")) {
				if (r.nextInt(upperChance) < linkChance)
					linksFound.add(new Link(site, site2));
				
					urlToNode.put(site, new Node(site));
					urlToNode.put(site2, new Node(site2));
			}

			
		}
		
		for (Link link : linksFound) {
			Node n1 = urlToNode.get(link.getFrom());
			Node n2 = urlToNode.get(link.getTo());
			n1.addConnection(n2);
			
		}

		System.out.println(urlToNode.get("mit.edu"));
		
//		System.out.println("Number of links: " + linksFound.size());
//		System.out.println("Number of nodes: " + urlToNode.size());
//		for (Link link : linksFound) {
//			System.out.println(link);
//		}
		
	}

	public static void main(String[] args) {
		new GenerateNodes("mit.edu", 110);

	}

}
