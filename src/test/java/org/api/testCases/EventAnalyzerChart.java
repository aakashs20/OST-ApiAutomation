package org.api.testCases;

import org.api.base.BaseTest;
import org.api.utilities.ExcelReader;
import org.api.utilities.JsonPathUtility;
import org.api.utilities.JsonReader;
import org.api.utilities.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EventAnalyzerChart extends BaseTest {

	private JsonPathUtility jsonUtil;
	private ExcelReader excelReader = new ExcelReader();

	@BeforeMethod
	public void setup() throws FileNotFoundException {

		String bodyData = JsonReader.readJson("NetworkEventResponse.json");

		// Initialize JsonPathUtility
		jsonUtil = new JsonPathUtility(bodyData);
	}

	@DataProvider(name = "testDataMapEvent")
	public Object[][] getExcelDataMapEvent() throws IOException {
		try {
			Map<String, String> data = excelReader.testDataMap(
					System.getProperty("user.dir") + PropertiesReader.getConfigData("excel.path") + "xil_series.xlsx",
					"xil_series", "sender", ""); // Example filter on "Status" column for "Active" value

			// Convert Map to Object[][] for DataProvider
			Object[][] dataProvider = new Object[1][1];
			dataProvider[0][0] = data;
			return dataProvider;
		} catch (IOException e) {
			e.printStackTrace();
			return new Object[0][0]; // Return empty array on error
		}
	}

	@Test(priority = 1, dataProvider = "testDataMapEvent")
	public void testGetEventValues(Map<String, String> dataCI) {
		try {
			// Get all Budget (at index 2)
			List<Object> signal = jsonUtil.getSeriesValues(5);
			System.out.println(signal.size());
			System.out.println("Signal:");
			for (Object sig : signal) {
				System.out.println(sig);
				Assert.assertEquals(sig.toString(), dataCI.get("signal"));
				System.out.println("Signal matched");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
