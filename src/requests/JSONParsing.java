package requests;

import org.json.JSONObject;

public class JSONParsing {
	private JSONObject jsonobj = new JSONObject();
	
	public JSONParsing(){

		
		JSONObject o = new JSONObject();
	}
	
	public static JSONObject getJson(String url, String resultCount){
		
		String requestString = "http://api.majestic.com/api/json?app_api_key=A1491C07BAB12F365BA1E073C69200DF&"
				+ "cmd=GetRefDomains&item0="+url+"&Count="+resultCount+"&datasource=fresh";
		
		
		
		return ;
	}
	
}
