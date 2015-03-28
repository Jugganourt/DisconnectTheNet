package requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParsing {
	
	private JSONObject jsonobj = new JSONObject();
	
	private JSONObject json;
	
	public JSONParsing(String url, int resultCount){	
		
		json = getJson(url, "" + resultCount);
		
		JSONArray results = json.getJSONArray("Data");
		
		for(int i = 0; i < results.length(); i++){
			System.out.println(results.getJSONObject(i).getString("Domain"));
		}
		
	}
	
	public static JSONObject getJson(String url, String resultCount){
		
		String requestString = "http://api.majestic.com/api/json?app_api_key=A1491C07BAB12F365BA1E073C69200DF&"
				+ "cmd=GetRefDomains&item0="+url+"&Count="+resultCount+"&datasource=fresh";
		
		
		
		return ;
	}
	
}
