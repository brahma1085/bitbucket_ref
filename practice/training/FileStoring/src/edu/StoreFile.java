package edu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class StoreFile {

	public static void main(String[] args) throws Exception {
		File file = new File("C:/");
		URL url = null;
		try {
			url = file.toURL();
			String string = url.getFile();
			System.out.println(string);
			file = new File(string);
			int i;
			InputStream stream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder builder = new StringBuilder();
			do {
				i = stream.read();
				builder.append((char)i);
			} while (i != -1);
			System.out.println(builder);
			stream.close();
		} catch (MalformedURLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}

}
