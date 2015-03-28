package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

import util.Keys;

public class GetLinks {

	private JSONArray results;

	public GetLinks(String url, int resultCount) {
		JSONObject json = getJson(url, "" + resultCount);
		results = json.getJSONObject("DataTables").getJSONObject("Results").getJSONArray("Data");

		for (int i = 0; i < results.length(); i++) {
			if (!results.getJSONObject(i).getString("Domain").contains(".edu")) {
				results.remove(i);
				i--;
			}
		}
	}

	private static JSONObject getJson(String url, String resultCount) {
		JSONObject jsonobj = new JSONObject();
		String requestString = "http://api.majestic.com/api/json?app_api_key=" + Keys.KEY + "&" + "cmd=GetRefDomains&item0=" + url + "&Count=" + resultCount
				+ "&datasource=fresh";
		try {
			URL obj = new URL(requestString);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			jsonobj = new JSONObject(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	public ArrayList<String> getField(String s){
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < results.length(); i++) {
			list.add(results.getJSONObject(i).getString(s));
		}
		
		return list;
	}
	
	public void printDomains() {
		System.out.println("Number of domains: " + results.length());
		
		for (int i = 0; i < results.length(); i++) {
			System.out.println(results.getJSONObject(i).getString("Domain"));
		}
	}

	public static void main(String[] args) {
		GetLinks j = new GetLinks("bham.ac.uk", 100);
		j.printDomains();
	}

}
