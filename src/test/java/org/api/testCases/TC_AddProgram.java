package org.api.testCases;

import java.io.IOException;

import org.api.base.BaseTest;
import org.api.reporting.ExtentReporter;
import org.api.responseValidation.ValidateResponse;
import org.api.utilities.JsonParsingUsingJsonPath;
import org.api.utilities.JsonReader;
import org.api.utilities.JsonReplacement;
import org.api.utilities.RandomData;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_AddProgram extends BaseTest {

	public static String programPk;

	@Test
	public void addProgram() throws IOException {
		try {
			
			String programName = "Program_Auto_" + RandomData.getRandomNumber(4);
			String programDesc = "Program_Auto_Desc_" + RandomData.getRandomNumber(4);

			String bodyData = JsonReader.readJson("add_program.json");

			bodyData = JsonReplacement.jsonReplace(bodyData, "name", programName);
			bodyData = JsonReplacement.jsonReplace(bodyData, "description", programDesc);

			Response response = postRequest(bodyData, "uri_add_program", TC_Login.access_token);
//			programPk = JsonParsingUsingOrgJson.parseJson(response.asString(), "programPk");
			programPk = JsonParsingUsingJsonPath.jsonParse(response, "data.programPk");
			ExtentReporter.logInfoDetails(programPk);

			ValidateResponse.statusCodeValidate(response, 200);
			ValidateResponse.responseDataValidate(response, "data.programPk", programPk);
		} catch (Exception ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
		}
	}
}
