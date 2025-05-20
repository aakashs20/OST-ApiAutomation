package org.api.utilities;

import java.io.FileNotFoundException;

import org.api.restUtils.HttpMethods;

import io.restassured.response.Response;

public class tokenGenerator {

	public static String access_token;
	
	public static void loginToken() throws FileNotFoundException
	{
	String uri = PropertiesReader.getConfigData("uri_login");
	String requestPayload = JsonReader.readJson("login.json");
	Response res = HttpMethods.postRequest(requestPayload, uri);
	access_token = JsonParsingUsingJsonPath.jsonParse(res, "data.access_token");
	}
}
