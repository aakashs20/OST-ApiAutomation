package org.api.utilities;

import java.util.List;

import org.api.reporting.ExtentReporter;
import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public class JsonParsingUsingOrgJson {

	public static String parseJson(String bodyData, String extractKey) {
		try {
			if (bodyData.startsWith("{")) {
				JSONObject js = new JSONObject(bodyData);
				return js.getString(extractKey);
			}
			return extractKey;
		} catch (Exception ex) {
			ex.printStackTrace();
			ExtentReporter.logFailureDetails(ex.getMessage());
		}
		return extractKey;
	}

	public static String parseComplexJson(String bodyData, String extractKey) {
		try {

			JSONObject js = new JSONObject(bodyData);

			// Check if the key contains a dot (indicates nested object)
			if (extractKey.contains(".")) {
				String[] keys = extractKey.split("\\.");
				JSONObject nestedObj = js;

				// Traverse through nested objects
				for (int i = 0; i < keys.length - 1; i++) {
					nestedObj = nestedObj.getJSONObject(keys[i]);
				}

				// Return the value of the last key
				return nestedObj.getString(keys[keys.length - 1]);
			}

			// Simple key parsing
			return js.getString(extractKey);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return extractKey;
	}

	public static List<String> parseJsonArray(Response response, String extractKey) {

		List<String> allKeys = null;
		JSONArray arr = new JSONArray(response.asString());

		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			JSONObject data = obj.getJSONObject(extractKey);
			allKeys.add(data.toString());
		}
		return allKeys;
	}

}
