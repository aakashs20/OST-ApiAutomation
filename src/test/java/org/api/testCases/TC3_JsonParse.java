package org.api.testCases;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.api.reporting.ExtentReporter;
import org.api.utilities.JsonPathUtility;
import org.api.utilities.JsonReader;
import org.testng.annotations.Test;

public class TC3_JsonParse {

	@Test
	public void jsonParseTest() throws FileNotFoundException {

		String bodyData = JsonReader.readJson("newJsonWithViolation.json");

		// Initialize the utility
		JsonPathUtility jsonUtil = new JsonPathUtility(bodyData);

		// Get all data for series index 0
		List<Object[]> seriesData = jsonUtil.getSeriesDataByIndex(0);
		// Iterating through all arrays in the series
		for (Object[] array : seriesData) {
			// Print each array's details
			System.out.println("Array Contents:");
			ExtentReporter.logInfoDetails("Array Contents:");
			for (Object item : array) {
				System.out.print(item + " | ");
				ExtentReporter.logInfoDetails(item.toString());
			}
			System.out.println(); // New line after each array
		}

		// Get a specific value from series
		Object specificValue = jsonUtil.getSeriesValue(0, 1, 1);
		System.out.println("specificValue: " + specificValue);
		ExtentReporter.logInfoDetails(specificValue.toString());

		// Filter series data
		List<Object[]> filteredData = jsonUtil.filterSeriesData(2, 4, "ECU_2");
		// Iterating through all arrays in the series
		for (Object[] filteredArray : filteredData) {
			// Print each array's details
			System.out.println("filteredArray Contents:");
			ExtentReporter.logInfoDetails("filteredArray Contents:");
			for (Object item : filteredArray) {
				System.out.print(item + " | ");
				ExtentReporter.logInfoDetails(item.toString());
			}
			System.out.println(); // New line after each array
		}

		// Get legend names
		List<String> legendNames = jsonUtil.getLegendNames();
		System.out.println("legendNames: " + legendNames);
		ExtentReporter.logInfoDetails(legendNames.get(0));

	}

	@Test
	public void jsonParseTestAssert() throws FileNotFoundException {

		String bodyData = JsonReader.readJson("newJsonWithViolation.json");

		// Initialize the utility
		JsonPathUtility utility = new JsonPathUtility(bodyData);

		// Get all values from series 0
		List<List<Object>> allValues = utility.getAllSeriesValues(0, null);
		System.out.println(allValues.get(0));

		// Create expected values
		List<List<Object>> expectedValues = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		values.add(0);
		values.add(1720755525.668651540);
		values.add(0);
		values.add(0);
		values.add("ECU_2");
		values.add("FD_CAN_2");
		values.add(0.138445618);
		values.add("First Frame");
		values.add("Start Indication");
		expectedValues.add(values);

		// Assert all values match
		boolean matches = utility.assertSeriesValues(0, expectedValues);
		if (matches) {
			System.out.println("All values match expected values!");
		} else {
			System.out.println("Some values don't match expected values. Check error log for details.");
		}

	}
}