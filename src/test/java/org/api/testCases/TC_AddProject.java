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

public class TC_AddProject extends BaseTest {

	public static String projectPk;
	public static String projectName;
	@Test
	public void addProject() throws IOException {
		try {
			projectName = "Project_Auto_" + RandomData.getRandomNumber(4);
			String projectDesc = "Project_Auto_Desc_" + RandomData.getRandomNumber(4);

			String bodyData = JsonReader.readJson("add_project.json");

			bodyData = JsonReplacement.jsonReplace(bodyData, "name", projectName);
			bodyData = JsonReplacement.jsonReplace(bodyData, "description", projectDesc);
			bodyData = JsonReplacement.jsonReplace(bodyData, "programFk", TC_AddProgram.programPk);

			Response response = postRequest(bodyData, "uri_add_project", TC_Login.access_token);
			// projectPk = JsonParsingUsingOrgJson.parseJson(response.asString(), "projectPk");
			projectPk = JsonParsingUsingJsonPath.jsonParse(response, "data.project_pk");

			ValidateResponse.statusCodeValidate(response, 200);
			ValidateResponse.responseDataValidate(response, "data.project_pk", projectPk);
		} catch (Exception ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
		}
	}
}
