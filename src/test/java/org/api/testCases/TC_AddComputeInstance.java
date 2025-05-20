package org.api.testCases;

import org.api.base.BaseTest;
import org.api.reporting.ExtentReporter;
import org.api.responseValidation.ValidateResponse;
import org.api.utilities.JsonParsingUsingJsonPath;
import org.api.utilities.JsonReader;
import org.api.utilities.JsonReplacement;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_AddComputeInstance extends BaseTest {

	public static String sysModelDesignVersionPk;

	@Test
	public void addComputeInstance() {
		try {
			
			String bodyData1 = JsonReader.readJson("get_object_hierarchy.json");
			bodyData1 = JsonReplacement.jsonReplace(bodyData1, "projectFk", TC_AddProject.projectPk);
			bodyData1 = JsonReplacement.jsonReplace(bodyData1, "projectName", TC_AddProject.projectName);
			
			Response response1 = postRequest(bodyData1, "uri_get_object_hierarchy");
			sysModelDesignVersionPk = JsonParsingUsingJsonPath.jsonParse(response1, "data[0].designversionFk");
			
			String bodyData = JsonReader.readJson("add_compute_instance.json");
			bodyData = JsonReplacement.jsonReplace(bodyData, "projectPk", TC_AddProject.projectPk);
			bodyData = JsonReplacement.jsonReplace(bodyData, "sysModelDesignVersionPk", sysModelDesignVersionPk);

			Response response = postRequest(bodyData, "uri_add_coumpute_instance");

			ValidateResponse.statusCodeValidate(response, 200);
		} catch (Exception ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
		}
	}
}
