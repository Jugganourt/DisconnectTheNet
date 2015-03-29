package stuffShaunWantButNoOneElseWantsToDo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import map.Connection;
import map.GenerateNodes;
import map.Link;
import map.Node;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShaunsShittlyLittleTextFileReader {

	public static GenerateNodes getNodes(){
		try {
			
			BufferedReader file = new BufferedReader(new FileReader(new File("Files/connections.txt")));
			JSONArray jobjArray = new JSONArray(file.readLine());
			Map<String, Node> urlToNode = new TreeMap<String, Node>();
			ArrayList<String> places = new ArrayList<String>();
			ArrayList<Node> nodes = new ArrayList<Node>();
			ArrayList<Connection> conectionsArray = new ArrayList<Connection>();
			JSONObject jobj = new JSONObject();
			for (int i = 0; i < jobjArray.length(); i++) {
				if(jobjArray.get(i).toString().contains("Thing")) {
					places.add(jobjArray.get(i).toString().substring(8));
				}else{
					jobj = new JSONObject(jobjArray.get(i).toString().substring(8));
				}
			}
			
			for (int i = 0; i < places.size(); i++) {
				String current = places.get(i);
				JSONArray nodeAgain = new JSONArray(jobj.get(current).toString());
				for (int j = 0; j < nodeAgain.length(); j++) {
					Node temp = new Node();
					temp.setLatitude(Double.parseDouble(nodeAgain.get(0).toString().substring(5)));
					temp.setLongitude((Double.parseDouble(nodeAgain.get(1).toString().substring(7))));
					temp.setTrust(Integer.parseInt(nodeAgain.get(2).toString().substring(8)));
					temp.setUrl(nodeAgain.get(2).toString().substring(6));
					nodes.add(temp);
					urlToNode.put(current, temp);
				}
			}
			JSONArray con = new JSONArray(jobj.get("Connection List").toString());
			ArrayList<Link> links = new ArrayList<Link>();
			for (int i = 0; i < con.length(); i++) {
				String from = con.get(i).toString().substring(9).substring(0, con.get(i).toString().substring(9).indexOf(":")-2);
				String to = con.get(i).toString().substring(9).substring(con.get(i).toString().substring(9).indexOf(":")+2);
				Link temp = new Link(from, to);
				links.add(temp);
			}
			
			for (Link link : links) {
				String from = link.getFrom();
				String to = link.getTo();
				Node fromNode = new Node();
				Node toNode = new Node();
				for (int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).getURL().equals(from)){
							fromNode = nodes.get(i);
					}
					if(nodes.get(i).getURL().equals(to)){
						toNode = nodes.get(i);
					}
				}
				Connection connect = new Connection(fromNode, toNode);
				conectionsArray.add(connect);
				fromNode.addConnection(connect);
				toNode.addConnection(connect);
				
			}
			GenerateNodes genNodes = new GenerateNodes();
			genNodes.setConnections(conectionsArray);
			genNodes.setUrlToNode(urlToNode);
			
			return genNodes;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
