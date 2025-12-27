package edu.nit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GettingFileInfo {
	public static void main(String[] args) {
		File file = new File("E:/");
		URL url = null;
		try {
			url = file.toURL();
			System.out.println("the file URL is==>" + url);
			String string = url.getFile();
			System.out.println("just file name==>" + string);
			file = new File(string);
			int i;
			InputStream stream = url.openStream();
			System.out.println("stream object is at==>" + stream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			System.out.println("reader object is at==>" + reader);
			do {
				i = stream.read();
				System.out.print((char) i);
			} while (i != -1);
			stream.close();
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}
}
