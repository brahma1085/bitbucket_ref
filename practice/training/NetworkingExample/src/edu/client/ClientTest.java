package edu.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			OutputStream stream = socket.getOutputStream();
			stream.write('h');
			stream.write('a');
			stream.write('i');
			InputStream inputStream = socket.getInputStream();
			inputStream.read();
			inputStream.read();
			inputStream.read();
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}
}