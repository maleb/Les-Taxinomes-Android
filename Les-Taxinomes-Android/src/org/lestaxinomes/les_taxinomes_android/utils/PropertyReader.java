package org.lestaxinomes.les_taxinomes_android.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private static String PROPERTIES_FILE = "config.properties";

	public static String getProperty(String propertyName) {
		Properties prop = new Properties();

		try {
			// load a properties file
			prop.load(new FileInputStream(PROPERTIES_FILE));

			// get the property value and print it out
			return prop.getProperty(propertyName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
