package edu.test;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {

	public static void main(String[] args) throws Throwable {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out
				.println("my server is waiting for the request @ 8888 port number");
		Socket socket = serverSocket.accept();
		System.out.println("server received a request");
		System.out.println("the request is : ");
		InputStream stream = socket.getInputStream();
		int ch = stream.read();
		while (ch != -1) {
			System.out.print((char) ch);
		}
		stream.close();
		socket.close();
	}

}
