package org.api.utilities;

import java.util.regex.Pattern;

public class JsonReplacement {

	public static String jsonReplace(String data, String variableName, String variableValue) {

		data = data.replaceAll(Pattern.quote("{{" + variableName + "}}"), variableValue);
		return data;
	}

}
