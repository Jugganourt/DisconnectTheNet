package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.HTTP;
import org.json.JSONObject;

public class JSONParsing<HttpResponse> {

	public static void main(String[] args) throws MalformedURLException {
		getJson("google.com", "10");
	}
	public JSONParsing(){

		
		JSONObject o = new JSONObject();
	}
	
	public static JSONObject getJson(String url, String resultCount){
		JSONObject jsonobj = new JSONObject();
		String requestString = "http://api.majestic.com/api/json?app_api_key=A1491C07BAB12F365BA1E073C69200DF&"
				+ "cmd=GetRefDomains&item0="+url+"&Count="+resultCount+"&datasource=fresh";
		 		
		try {
			URL obj = new URL(requestString);
		
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			//con.setRequestProperty("User-Agent", USER_AGENT);
	 
			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + requestString);
			//System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			jsonobj = new JSONObject(response.toString()); 
						
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonobj;
	}
	
}
