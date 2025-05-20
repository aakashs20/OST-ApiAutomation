package org.api.testCases;

import java.io.IOException;

import org.api.restUtils.HttpMethods;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC3_GetAllRequest {
	
	@Test
	public void testCase_GetAll() throws IOException {

		HttpMethods http = new HttpMethods();
		
		Response res = http.GetRequest("uri_object");
		
		System.out.println(res.statusCode());
		System.out.println(res.asString());
	}

}
