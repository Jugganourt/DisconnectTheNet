package stuffShaunWantButNoOneElseWantsToDo;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import map.Connection;
import map.GenerateNodes;
import map.Node;

public class ShaunsShittlyLittleTextFileMaker {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		JSONArray objArray = new JSONArray();
		JSONObject obj = new JSONObject();
        
		GenerateNodes nodes = new GenerateNodes("mit.edu", 110);

        JSONArray connectionArray = new JSONArray();
        for (Connection con : nodes.getConnections()) {
			connectionArray.put("values : "+con.toString());
		}
        obj.put("Connection List", connectionArray);

        JSONArray nodeArray= new JSONArray();
        Map<String, Node> urlToNode = nodes.getUrlToNode();
        Iterator<Map.Entry<String, Node>> iterator = urlToNode.entrySet().iterator();
        while (iterator.hasNext()) {
        	Map.Entry<String, Node> employee = (Map.Entry<String, Node>) iterator.next();
        	String temp = employee.getKey();
        	Node temp2 = employee.getValue();
        	nodeArray.put("Lat: "+temp2.getLatitude());
        	nodeArray.put("Long: "+temp2.getLongitude());
        	nodeArray.put("Trust: "+temp2.getTrust());
        	nodeArray.put("URL: "+temp2.getURL());
        	nodeArray.put("Status: "+temp2.getStatus());
        	nodeArray.put("NumOfCon: "+temp2.getNumberOfConnections());
        	objArray.put("Thing : "+temp);
        	obj.put(temp, nodeArray);
        	nodeArray= new JSONArray();
		}
      
        objArray.put("FINAL : "+obj);
        FileWriter file = new FileWriter("Files/connections.txt");
        try {
            file.write(objArray.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
	}
}
