package edu.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8888);
			Socket socket = server.accept();
			InputStream inputStream = socket.getInputStream();
			inputStream.read();
			inputStream.read();
			inputStream.read();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write('b');
			outputStream.write('y');
			outputStream.write('e');
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}
}
