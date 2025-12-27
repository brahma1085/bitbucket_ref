package edu.test;

import java.io.IOException;
import java.net.SocketAddress;

public class ServerSocket {

	public static void main(String[] args) throws IOException {
		java.net.ServerSocket serverSocket = new java.net.ServerSocket();
		SocketAddress endpoint = serverSocket.getLocalSocketAddress();
		serverSocket.bind(endpoint);
		System.out.println(serverSocket.isBound());
		System.out.println(serverSocket.getInetAddress());
		System.out.println(serverSocket.getLocalPort());
		serverSocket.accept();
	}
}