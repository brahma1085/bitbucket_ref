package edu.test;

import java.io.*;
import java.net.*;

public class FileNameTest {
	public static void main(String[] args) {
		File file = new File("E:/");
		URL url = null;
		try {
			// The file may or may not exist
			url = file.toURL(); // file:/C:/work/chandan/deepak.txt
			System.out.println("The url is" + url);

			// change the URL to a file object
			file = new File(url.getFile()); // c:/work/chandan/deepak.txt
			System.out.println("The file name is " + file);
			int i;

			// opens an input stream
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			do {
				i = br.read();
				System.out.print((char) i);
			} while (i != -1);
			is.close();
		} catch (MalformedURLException e) {
			System.out.println("Don't worry,exception has been caught" + e);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}