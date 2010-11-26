package utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class WebUtils {

	public static String downloadURL(URL url)
	{
		URLConnection connection = null;
		try {
			connection = url.openConnection();
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
		} catch (IOException e1) {
			return null;
		}

		String line;
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			return null;
		}
		
		return builder.toString();
	}	
	
	public static void downloadURL(URL url, String path) throws IOException {
		FileWriter file = new FileWriter(path, false);
		PrintWriter writer = new PrintWriter(file);

		URLConnection connection = null;
		connection = url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);

		String line;

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			writer.append(line);
			writer.append("\n");
		}
		reader.close();

		writer.close();
		file.close();
	}
}