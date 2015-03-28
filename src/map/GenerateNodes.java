package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import requests.GetLinks;

public class GenerateNodes {

	Map<String, Node> urlToNode = new HashMap<String,Node>(); 
	Set<Link> linksFound = new HashSet<Link>();
	
	public GenerateNodes(String url, int numResults){
		
		GetLinks start = new GetLinks(url, numResults);
		
		for (String site : start.getField("Domain")) {
			linksFound.add(new Link(url, site));
		}
		
		Node temp = new Node(url);	
		urlToNode.put(url, temp);
		
		linksFound.add(new Link("wisc.edu", "mit.edu"));
		
		for (Link link : linksFound) {
			System.out.println(link);
		}
		
	}
	
	
	public static void main(String[] args) {
		new GenerateNodes("mit.edu", 100);
		
	}

}
