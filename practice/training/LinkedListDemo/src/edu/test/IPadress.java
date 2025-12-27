package edu.test;

import java.net.Socket;
import java.net.SocketAddress;

public class IPadress {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("0.0.0.0", 49205);
		System.out.println(socket.getInetAddress());
		SocketAddress endpoint=socket.getLocalSocketAddress();
		socket.connect(endpoint);
	}
}