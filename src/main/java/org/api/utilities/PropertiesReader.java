package org.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private static Properties properties;
	private static File file;
	private static FileInputStream fileInputStream;

	private static String propertyFilePath = "//src//test//resources//configuration//";
	private static String testConfigFile = "uri.properties";

	public static void loadPropertyFile() throws IOException {
		try {
			properties = new Properties();
			file = new File(System.getProperty("user.dir") + propertyFilePath + testConfigFile);
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			try {
				properties.load(fileInputStream);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} finally {
			fileInputStream.close();
		}
	}

	public static String getConfigData(String property) {
		String dataFromPropFile = null;
		try {
			dataFromPropFile = properties.getProperty(property).trim();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataFromPropFile;
	}

	public static Properties readProperties(String path) throws IOException {

		File f = new File(path);
		FileReader fr = new FileReader(f);
		Properties pr = new Properties();
		pr.load(fr);
		return pr;
	}

}