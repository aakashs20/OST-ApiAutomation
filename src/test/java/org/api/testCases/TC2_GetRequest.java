package org.api.testCases;

import java.io.IOException;

import org.api.responseValidation.ValidateResponse;
import org.api.restUtils.HttpMethods;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC2_GetRequest {
	
	@Test
	public void testCase_Get() throws IOException {
		
		HttpMethods http = new HttpMethods();
		
		Response res = http.GetRequest("uri_object", TC1_PostRequest.responseValue);
		
		System.out.println(res.statusCode());
		System.out.println(res.asString());
		
		ValidateResponse.statusCodeValidate(res, 200);
		ValidateResponse.responseDataValidate(res, "data.CPU model", "Intel Core i9");
	}

}
