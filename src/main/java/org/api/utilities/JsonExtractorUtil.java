package org.api.utilities;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonExtractorUtil {

	public static List<String> extractListFromJsonArray(Response response, String jsonPath, String key) {

		return response.jsonPath().getList(jsonPath + "." + key, String.class);
	}

	public static Optional<String> extractValueFromJsonArray(Response response, String jsonPath, String key,
			String filterKey, String filterValue) {
		JSONArray jsonArray = new JSONArray(response.jsonPath().getList(jsonPath));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.has(filterKey) && jsonObject.getString(filterKey).equals(filterValue)) {

				// Check if the key exists and convert to string safely
				if (jsonObject.has(key)) {
					Object value = jsonObject.get(key);
					return Optional.of(value.toString());
				}

				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	public static List<JSONObject> filterJsonObjects(Response response, String jsonPath, String filterKey,
			String filterValue) {

		JSONArray jsonArray = new JSONArray(response.jsonPath().getList(jsonPath));

		List<JSONObject> filteredObjects = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.getString(filterKey) != null && jsonObject.getString(filterKey).equals(filterValue)) {
				filteredObjects.add(jsonObject);
			}
		}

		return filteredObjects;
	}
}