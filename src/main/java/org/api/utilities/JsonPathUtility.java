package org.api.utilities;

import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonPathUtility {
	private JsonPath jsonPath;
	private JSONObject jsonObject;

	/**
	 * Constructor to initialize JsonPath and JSONObject
	 * 
	 * @param jsonResponse The JSON response as a string
	 */
	public JsonPathUtility(String jsonResponse) {
		this.jsonPath = new JsonPath(jsonResponse);
		this.jsonObject = new JSONObject(jsonResponse);
	}

	/**
	 * Extract series data for a specific index
	 * 
	 * @param seriesIndex Index of the series array
	 * @return List of arrays representing the series data
	 */
	public List<Object[]> getSeriesDataByIndex(int seriesIndex) {
		try {
			JSONArray seriesArray = jsonObject.getJSONObject("data").getJSONArray("series");

			if (seriesIndex < 0 || seriesIndex >= seriesArray.length()) {
				throw new IndexOutOfBoundsException("Series index out of bounds");
			}

			JSONArray specificSeries = seriesArray.getJSONArray(seriesIndex);

			List<Object[]> seriesData = new ArrayList<>();
			for (int i = 0; i < specificSeries.length(); i++) {
				JSONArray innerArray = specificSeries.getJSONArray(i);
				Object[] arrayData = new Object[innerArray.length()];
				for (int j = 0; j < innerArray.length(); j++) {
					arrayData[j] = innerArray.get(j);
				}
				seriesData.add(arrayData);
			}

			return seriesData;
		} catch (Exception e) {
			System.err.println("Error extracting series data: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Extract a specific value from a series array by index and position
	 * 
	 * @param seriesIndex Index of the series array
	 * @param arrayIndex  Index within the specific series array
	 * @param valueIndex  Position of the value within the inner array
	 * @return The value at the specified position
	 */
	public Object getSeriesValue(int seriesIndex, int arrayIndex, int valueIndex) {
		try {
			JSONArray seriesArray = jsonObject.getJSONObject("data").getJSONArray("series");

			if (seriesIndex < 0 || seriesIndex >= seriesArray.length()) {
				throw new IndexOutOfBoundsException("Series index out of bounds");
			}

			JSONArray specificSeries = seriesArray.getJSONArray(seriesIndex);

			if (arrayIndex < 0 || arrayIndex >= specificSeries.length()) {
				throw new IndexOutOfBoundsException("Array index out of bounds");
			}

			JSONArray innerArray = specificSeries.getJSONArray(arrayIndex);

			if (valueIndex < 0 || valueIndex >= innerArray.length()) {
				throw new IndexOutOfBoundsException("Value index out of bounds");
			}

			return innerArray.get(valueIndex);
		} catch (Exception e) {
			System.err.println("Error extracting series value: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Get all values at a specific position from all arrays in a series
	 * 
	 * @param seriesIndex The index of the series (0,1,2,etc.)
	 * @param valueIndex  The position of the value in each array (0,1,2,etc.)
	 * @return List of values from that position
	 */
	public List<Object> getSeriesValues(int seriesIndex, int valueIndex) {
		List<Object> values = new ArrayList<>();

		try {
			// Get the series array from JSON
			JSONArray seriesArray = jsonObject.getJSONObject("data").getJSONArray("series");

			// Check if series index is valid
			if (seriesIndex >= 0 && seriesIndex < seriesArray.length()) {
				// Get the specific series
				JSONArray specificSeries = seriesArray.getJSONArray(seriesIndex);

				// Loop through each array in the series
				for (int i = 0; i < specificSeries.length(); i++) {
					JSONArray innerArray = specificSeries.getJSONArray(i);

					// Check if value index is valid for this array
					if (valueIndex >= 0 && valueIndex < innerArray.length()) {
						// Add the value to our list
						values.add(innerArray.get(valueIndex));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error getting values: " + e.getMessage());
		}

		return values;
	}

	/**
	 * Get all values at a specific position from all arrays in the series
	 * 
	 * @param valueIndex The position of the value in each array (0,1,2,etc.)
	 * @return List of values from that position
	 */
	public List<Object> getSeriesValues(int valueIndex) {
		List<Object> values = new ArrayList<>();

		try {
			// Get the series array from JSON
			JSONArray seriesArray = jsonObject.getJSONObject("data").getJSONArray("series");

			// Loop through each series
			for (int i = 0; i < seriesArray.length(); i++) {
				JSONArray specificSeries = seriesArray.getJSONArray(i);

				// Loop through each array in the series
				for (int j = 0; j < specificSeries.length(); j++) {
					JSONArray innerArray = specificSeries.getJSONArray(j);

					// Check if value index is valid for this array
					if (valueIndex >= 0 && valueIndex < innerArray.length()) {
						// Add the value to our list
						values.add(innerArray.get(valueIndex));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error getting values: " + e.getMessage());
		}

		return values;
	}

	/**
	 * Filter series data based on a specific condition
	 * 
	 * @param seriesIndex Index of the series array
	 * @param filterIndex Index to filter on
	 * @param filterValue Value to match for filtering
	 * @return Filtered list of arrays
	 */
	public List<Object[]> filterSeriesData(int seriesIndex, int filterIndex, Object filterValue) {
		List<Object[]> seriesData = getSeriesDataByIndex(seriesIndex);

		return seriesData.stream().filter(array -> array.length > filterIndex && array[filterIndex].equals(filterValue))
				.collect(Collectors.toList());
	}

	/**
	 * Get legend names
	 * 
	 * @return List of legend names
	 */
	public List<String> getLegendNames() {
		return jsonPath.getList("data.legend.legendName");
	}

	/**
	 * Get fill colors
	 * 
	 * @return List of fill colors
	 */
	public List<String> getFillColors() {
		return jsonPath.getList("data.fillColors");
	}

	/**
	 * Get simulation start and end times
	 * 
	 * @return Array with [startSim, endSim]
	 */
	public double[] getSimulationTimeRange() {
		return new double[] { jsonPath.getDouble("data.xaxis.startSim"), jsonPath.getDouble("data.xaxis.endSim") };
	}

	/**
	 * Extract and validate all values from a specific series
	 * 
	 * @param seriesIndex    Index of the series array
	 * @param expectedValues List of expected values to validate against (optional)
	 * @return List of all values in the series
	 */
	public List<List<Object>> getAllSeriesValues(int seriesIndex, List<List<Object>> expectedValues) {
		try {
			JSONArray seriesArray = jsonObject.getJSONObject("data").getJSONArray("series");

			if (seriesIndex < 0 || seriesIndex >= seriesArray.length()) {
				throw new IndexOutOfBoundsException("Series index out of bounds");
			}

			JSONArray specificSeries = seriesArray.getJSONArray(seriesIndex);
			List<List<Object>> allValues = new ArrayList<>();

			// Extract all values from the series
			for (int arrayIndex = 0; arrayIndex < specificSeries.length(); arrayIndex++) {
				JSONArray innerArray = specificSeries.getJSONArray(arrayIndex);
				List<Object> rowValues = new ArrayList<>();

				for (int valueIndex = 0; valueIndex < innerArray.length(); valueIndex++) {
					rowValues.add(innerArray.get(valueIndex));
				}

				allValues.add(rowValues);

				// Validate against expected values if provided
				if (expectedValues != null && arrayIndex < expectedValues.size()) {
					List<Object> expectedRow = expectedValues.get(arrayIndex);
					for (int i = 0; i < Math.min(rowValues.size(), expectedRow.size()); i++) {
						if (!rowValues.get(i).equals(expectedRow.get(i))) {
							System.err.println(String.format(
									"Value mismatch at series %d, array %d, position %d: Expected %s, Got %s",
									seriesIndex, arrayIndex, i, expectedRow.get(i), rowValues.get(i)));
						}
					}
				}
			}

			return allValues;
		} catch (Exception e) {
			System.err.println("Error extracting all series values: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Assert all values in a series match expected values
	 * 
	 * @param seriesIndex    Index of the series array
	 * @param expectedValues List of expected values to validate against
	 * @return true if all values match, false otherwise
	 */
	public boolean assertSeriesValues(int seriesIndex, List<List<Object>> expectedValues) {
		try {
			List<List<Object>> actualValues = getAllSeriesValues(seriesIndex, null);

			if (actualValues.size() != expectedValues.size()) {
				System.err.println(String.format("Size mismatch for series %d: Expected %d arrays, Got %d arrays",
						seriesIndex, expectedValues.size(), actualValues.size()));
				return false;
			}

			boolean allMatch = true;
			for (int i = 0; i < actualValues.size(); i++) {
				List<Object> actualRow = actualValues.get(i);
				List<Object> expectedRow = expectedValues.get(i);

				if (actualRow.size() != expectedRow.size()) {
					System.err.println(String.format(
							"Array size mismatch at series %d, array %d: Expected %d values, Got %d values",
							seriesIndex, i, expectedRow.size(), actualRow.size()));
					allMatch = false;
					continue;
				}

				for (int j = 0; j < actualRow.size(); j++) {
					if (!actualRow.get(j).equals(expectedRow.get(j))) {
						System.err.println(
								String.format("Value mismatch at series %d, array %d, position %d: Expected %s, Got %s",
										seriesIndex, i, j, expectedRow.get(j), actualRow.get(j)));
						allMatch = false;
					}
				}
			}

			return allMatch;
		} catch (Exception e) {
			System.err.println("Error asserting series values: " + e.getMessage());
			return false;
		}
	}

}