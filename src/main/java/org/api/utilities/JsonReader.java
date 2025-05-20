package org.api.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonReader {
	
	private static String jsonFilePath = "../OST-ApiAutomation/src/test/resources/jsonData/";

	public static String readJson(String fileName) throws FileNotFoundException {

		File f = new File(jsonFilePath + fileName);
		FileReader fr = new FileReader(f);
		JSONTokener js = new JSONTokener(fr);
		JSONObject ob = new JSONObject(js);
		return ob.toString();
	}

}
