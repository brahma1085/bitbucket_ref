package com.blobex;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TomNJerryServer2 {
	public static void main(String[] args) throws IOException {
		ServerSocket server=new ServerSocket(9090);
		System.out.println("server is waiting @ 9090 port number");
		Socket ss=server.accept();
		System.out.println("server received the request");
		OutputStream out=ss.getOutputStream();
		PrintStream ps=new PrintStream(out);
		ps.println("HTTP 200 OK");
		ps.println("<html>hello how ru?</HTML>");
		ps.close();
		ss.close();	
		System.out.println("connection closed from files");
	}
}
