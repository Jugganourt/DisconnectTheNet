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
		
		System.out.println(start.getField("Domain").size());
		
		Random r = new Random();

		for (String site : start.getField("Domain")) {
			if (r.nextInt(upperChance) < linkChance)
				linksFound.add(new Link(url, site));
		}

		Node temp = new Node(url);
		urlToNode.put(url, temp);

		for (String site : start.getField("Domain")) {
			System.out.println("Loop: " + site);
			GetLinks siteLinks = new GetLinks(site, numResults);

			for (String site2 : siteLinks.getField("Domain")) {
				if (r.nextInt(upperChance) < linkChance)
					linksFound.add(new Link(site, site2));
			}

			temp = new Node(url);
			urlToNode.put(url, temp);
		}

		for (Link link : linksFound) {
			System.out.println(link);
		}

	}

	public static void main(String[] args) {
		new GenerateNodes("mit.edu", 110);

	}

}
