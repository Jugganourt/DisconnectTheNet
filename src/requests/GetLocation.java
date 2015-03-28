package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

import util.Keys;

public class GetLocation {
	
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

}
