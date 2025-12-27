package com.blobex;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TomServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server=new ServerSocket(9090);
		System.out.println("TomServer is waiting for the request @9090 port=");
		Socket s=server.accept();
		System.out.println("TomServer is received a request");
		System.out.println("the received request is=");
		InputStream in=s.getInputStream();
		int ch=in.read();
		while(ch!=-1)
		{
			System.out.print((char)ch);
			ch=in.read();
		}
		in.close();s.close();
	}
}
