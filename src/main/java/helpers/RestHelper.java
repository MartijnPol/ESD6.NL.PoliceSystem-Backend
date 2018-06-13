package helpers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public final class RestHelper {

	private static String aasJwtToken;

	private static String Identity = "kTRGkE488lp2v3Fyp2ZN";

	public static String getAasJwtToken() throws UnirestException {
		if(aasJwtToken == null){
			HttpResponse<JsonNode> response = Unirest.get("http://192.168.25.122:8080/AccountAdministrationSystem/api/authenticate/"+Identity).asJson();
			JSONObject obj = response.getBody().getObject();
			aasJwtToken = obj.getString("Token");
		}
		return aasJwtToken;
	}
}
