package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import util.Keys;

public class GetInfo {
	
	private double longitude,latitude;
	private int trust;
	
	private JSONArray results;
	public GetInfo(String url) {
		JSONObject json = getJson(url);
		results = json.getJSONObject("DataTables").getJSONObject("DomainsInfo").getJSONArray("Data");
		longitude = Double.parseDouble(results.getJSONObject(0).getString("Longitude").toString());
		latitude = Double.parseDouble(results.getJSONObject(0).getString("Latitude").toString());
		trust = results.getJSONObject(0).getInt("TrustFlow");
	}
	
	private static JSONObject getJson(String url) {
		JSONObject jsonobj = new JSONObject();
		String requestString = "http://api.majestic.com/api/json?app_api_key=" + Keys.KEY +"&cmd=GetRefDomainInfo&items=1&item0="+url+"&datasource=fresh";
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
	
	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public int getTrust(){
		return trust;
	}


}
