package org.api.testCases;

import java.util.Optional;

import org.api.base.BaseTest;
import org.api.reporting.ExtentReporter;
import org.api.responseValidation.ValidateResponse;
import org.api.utilities.JsonReader;
import org.api.utilities.JsonReplacement;
import org.api.utilities.RandomData;
import org.testng.annotations.Test;

import org.api.utilities.JsonExtractorUtil;
import io.restassured.response.Response;

public class TC_ImportArxml extends BaseTest {

	public static String jobID;
	public static String designversionPk;
	public static String hardwareUnitFk;
	public static String sysDesignVersionFk;

	@Test
	public void importArxml() {
		try {

			jobID = "ARXML" + RandomData.getRandomNumber(16);

			String bodyData1 = JsonReader.readJson("get_object_hierarchy.json");
			bodyData1 = JsonReplacement.jsonReplace(bodyData1, "projectFk", TC_AddProject.projectPk);
			bodyData1 = JsonReplacement.jsonReplace(bodyData1, "projectName", TC_AddProject.projectName);

			Response response1 = postRequest(bodyData1, "uri_get_object_hierarchy");

			Optional<String> designversionFk = JsonExtractorUtil.extractValueFromJsonArray(response1, "data",
					"designversionFk", "designversionName", "CII_Auto");
			designversionPk = designversionFk.get();

			Optional<String> ciPk = JsonExtractorUtil.extractValueFromJsonArray(response1, "data", "pk",
					"designversionName", "CII_Auto");
			hardwareUnitFk = ciPk.get();

			String bodyData = JsonReader.readJson("import_arxml.json");
			bodyData = JsonReplacement.jsonReplace(bodyData, "projectPk", TC_AddProject.projectPk);
			bodyData = JsonReplacement.jsonReplace(bodyData, "projectName", TC_AddProject.projectName);
			bodyData = JsonReplacement.jsonReplace(bodyData, "jobID", jobID);
			bodyData = JsonReplacement.jsonReplace(bodyData, "jobName", jobID);
			bodyData = JsonReplacement.jsonReplace(bodyData, "design_version_pk", designversionPk);
			bodyData = JsonReplacement.jsonReplace(bodyData, "hardware_unit_fk", hardwareUnitFk);
			bodyData = JsonReplacement.jsonReplace(bodyData, "sysDesignVersionFk",
					TC_AddComputeInstance.sysModelDesignVersionPk);

			Response response = postRequestMultiPart(bodyData, "uri_import_arxml");

			ValidateResponse.statusCodeValidate(response, 200);

		} catch (Exception ex) {
			ExtentReporter.logFailureDetails(ex.getMessage());
		}
	}

}
