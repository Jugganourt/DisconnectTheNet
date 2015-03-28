package map;

import java.util.ArrayList;

import requests.GetLinks;

public class GenerateNodes {

	ArrayList<Node> nodes;
	
	public GenerateNodes(String url, int numResults){
		GetLinks start = new GetLinks(url, numResults);
		
		System.out.println(start.getField("Domain"));
		
		for (String site : start.getField("Domain")) {
			System.out.println(new GetLinks(site, numResults).getField("Domain").toString());
		}
		
		//nodes = new ArrayList<Node>();
		
		//Node node = new N
		
	}
	
	
	public static void main(String[] args) {
		new GenerateNodes("mit.edu", 100);
		
	}

}
