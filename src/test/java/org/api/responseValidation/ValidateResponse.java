package org.api.responseValidation;

import org.api.base.BaseTest;
import org.api.reporting.ExtentReporter;
import org.testng.Assert;

import io.restassured.response.Response;

public class ValidateResponse extends BaseTest{

	public static void statusCodeValidate(Response response, int expectedStatusCode) {
		try {
			int actualStatusCode = response.statusCode();
			Assert.assertEquals(actualStatusCode, expectedStatusCode);
			ExtentReporter.logInfoDetails(
					"Assertion passed for expected status code: " + "<b>" + expectedStatusCode + "</b>");
		} catch (AssertionError ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
			Assert.fail("Assertion failed for: status code");
		}
	}

	public static void responseDataValidate(Response response, String jsonPath, String expectedResponseData) {

		try {
			String actualResponseData = response.jsonPath().getString(jsonPath);
			Assert.assertEquals(actualResponseData, expectedResponseData);
			ExtentReporter.logInfoDetails("Assertion passed for: " + "<b>" + jsonPath + "</b>" + " is: " + "<b>"
					+ expectedResponseData + "</b>");
		} catch (AssertionError ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
			Assert.fail("Assertion failed for: " + "<b>" + jsonPath + " not matched" + "</b>");
		}
	}

}
