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

public class StartupUpChartTest extends BaseTest {

	private JsonPathUtility jsonUtil;
	private ExcelReader excelReader = new ExcelReader();

	@BeforeMethod
	public void setup() throws FileNotFoundException {

		String bodyData = JsonReader.readJson("newJsonWithViolation.json");

		// Initialize JsonPathUtility
		jsonUtil = new JsonPathUtility(bodyData);
	}

	@DataProvider(name = "testDataMap")
	public Object[][] getExcelDataMap() throws IOException {
		try {
			Map<String, String> data = excelReader.testDataMap(
					System.getProperty("user.dir") + PropertiesReader.getConfigData("excel.path") + "Startup.xlsx",
					"Startup", "iteration", "2.0"); // Example filter on "Status" column for "Active" value

			// Convert Map to Object[][] for DataProvider
			Object[][] dataProvider = new Object[1][1];
			dataProvider[0][0] = data;
			return dataProvider;
		} catch (IOException e) {
			e.printStackTrace();
			return new Object[0][0]; // Return empty array on error
		}
	}

	@DataProvider(name = "testDataMapEvent")
	public Object[][] getExcelDataMapEvent() throws IOException {
		try {
			Map<String, String> data = excelReader.testDataMap(
					System.getProperty("user.dir") + PropertiesReader.getConfigData("excel.path") + "xil_series.xlsx",
					"xil_series", "sender", "FRONT"); // Example filter on "Status" column for "Active" value

			// Convert Map to Object[][] for DataProvider
			Object[][] dataProvider = new Object[1][1];
			dataProvider[0][0] = data;
			return dataProvider;
		} catch (IOException e) {
			e.printStackTrace();
			return new Object[0][0]; // Return empty array on error
		}
	}

	@Test(priority = 1, dataProvider = "testDataMap")
	public void testGetSeriesValues(Map<String, String> dataCI) {
		try {
			// Get all ECU names (at index 4)
			List<Object> ecuNames = jsonUtil.getSeriesValues(0, 4);
			System.out.println("ECU Names:");
			for (Object name : ecuNames) {
				System.out.println(name);
			}

			System.out.println("");

			// Get all Indication names (at index 6)
			List<Object> indicationNames = jsonUtil.getSeriesValues(0, 6);
			System.out.println("Indication Names:");
			for (Object name : indicationNames) {
				System.out.println(name);
			}

			System.out.println("");

			// Get all timestamps (at index 1)
			List<Object> timestamps = jsonUtil.getSeriesValues(0, 1);
			System.out.println("Timestamps:");
			for (Object timestamp : timestamps) {
				System.out.println(timestamp);
			}

			System.out.println("");

			// Get all Relative Time (at index 5)
			List<Object> relativeTime = jsonUtil.getSeriesValues(0, 5);
			System.out.println("Relative Time:");
			for (Object relTime : relativeTime) {
				System.out.println(relTime);
			}

			System.out.println("");

			// Get all Budget (at index 2)
			List<Object> budget = jsonUtil.getSeriesValues(2, 2);
			System.out.println("Budget:");
			for (Object bud : budget) {
				System.out.println(bud);
				Assert.assertEquals(bud.toString(), dataCI.get("max_delta_time"));
				System.out.println("Budget matched");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	// public void testSeriesDataExtraction() {
	// // Test extracting series data for a specific index
	// List<Object[]> seriesData = jsonUtil.getSeriesDataByIndex(0);
	//
	// Assert.assertNotNull(seriesData, "Series data should not be null");
	// Assert.assertEquals(seriesData.size(), 20, "Should have 20 arrays in first
	// series");
	//
	// // Verify specific values in the first series
	// Object[] firstArray = seriesData.get(0);
	// Assert.assertEquals(firstArray[4], "ECU_2", "Fifth element should be ECU_2");
	// Assert.assertEquals(firstArray[8], "Start Indication", "Last element should
	// be Start Indication");
	// }
	//
	// @Test
	// public void testSeriesValueExtraction() {
	// // Test extracting a specific value from series
	// Object specificValue = jsonUtil.getSeriesValue(0, 1, 1);
	//
	// Assert.assertEquals(specificValue, 1720755625.8070972, "Specific value should
	// match");
	// }
	//
	// @Test
	// public void testSeriesDataFiltering() {
	// // Filter series data based on specific conditions
	// List<Object[]> filteredData = jsonUtil.filterSeriesData(2, 4, "ECU_2");
	//
	// Assert.assertNotNull(filteredData, "Filtered data should not be null");
	// Assert.assertEquals(filteredData.size(), 2, "Should have 2 matching array");
	//
	// Object[] filteredArray = filteredData.get(0);
	// Assert.assertEquals(filteredArray[4], "ECU_2", "Filtered array should contain
	// ECU_2");
	// }
	//
	// @Test
	// public void testLegendAndColorExtraction() {
	// // Test extracting legend names
	// List<String> legendNames = jsonUtil.getLegendNames();
	//
	// Assert.assertEquals(legendNames.size(), 5, "Should have 5 legend names");
	// Assert.assertEquals(legendNames.get(0), "Indications", "First legend name
	// should match");
	//
	// // Test extracting fill colors
	// List<String> fillColors = jsonUtil.getFillColors();
	//
	// Assert.assertEquals(fillColors.size(), 5, "Should have 5 fill colors");
	// Assert.assertEquals(fillColors.get(0), "#0B5789", "First fill color should
	// match");
	// }
	//
	// @Test
	// public void testSimulationTimeRange() {
	// // Test extracting simulation time range
	// double[] timeRange = jsonUtil.getSimulationTimeRange();
	//
	// Assert.assertEquals(timeRange.length, 2, "Should have start and end times");
	// Assert.assertEquals(timeRange[0], 1720755525.6686516, "Start simulation time
	// should match");
	// Assert.assertEquals(timeRange[1], 1720755625.8070972, "End simulation time
	// should match");
	// }
	//
	// @Test(expectedExceptions = IndexOutOfBoundsException.class)
	// public void testInvalidSeriesIndexHandling() {
	// // Test handling of invalid series index
	// jsonUtil.getSeriesDataByIndex(10);
	// }
	//
	// @Test
	// public void testEmptySeriesHandling() {
	// // Test handling of empty series (last series in original JSON)
	// List<Object[]> emptySeries = jsonUtil.getSeriesDataByIndex(3);
	//
	// Assert.assertNotNull(emptySeries, "Empty series should return an empty
	// list");
	// Assert.assertTrue(emptySeries.isEmpty(), "Empty series should have no
	// elements");
	// }
}
