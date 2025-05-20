package org.api.testCases;

import java.io.FileNotFoundException;

import org.api.base.BaseTest;
import org.api.responseValidation.ValidateResponse;
import org.api.restUtils.HttpMethods;
import org.api.utilities.JsonParsingUsingJsonPath;
import org.api.utilities.JsonReader;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_Login extends BaseTest{
	
public static String access_token;
	

	@Test
	public static void loginToken() throws FileNotFoundException
	{
	//String uri = PropertiesReader.getConfigData();
	String requestPayload = JsonReader.readJson("login.json");
	Response res = HttpMethods.postRequest(requestPayload, "uri_login");
	access_token = JsonParsingUsingJsonPath.jsonParse(res, "data.access_token");
	System.out.println("Authorization token is : "+access_token);
	System.out.println("Token type is : "+ JsonParsingUsingJsonPath.jsonParse(res, "data.token_type"));
	ValidateResponse.statusCodeValidate(res, 200);
	ValidateResponse.responseDataValidate(res, "data.token_type", "Bearer");
	}

}
