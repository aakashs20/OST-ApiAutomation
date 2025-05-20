package org.api.utilities;

import io.restassured.response.Response;

public class JsonParsingUsingJsonPath {
	
	public static String jsonParse(Response response, String jsonPath) {
		
		return response.jsonPath().getString(jsonPath);
	}

}
