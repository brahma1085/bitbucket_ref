package com.edu.clients;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;


class OwnClient2 extends JFrame {
	private static final long serialVersionUID = 1061955859158044361L;
	JTextField addressbar;
	JLabel urlas=null;
	JButton get, post, put, head, delete, options, trace;
	JTextArea ta;
	JScrollPane sp;

	public OwnClient2() {
		addressbar = new JTextField(80);
		urlas = new JLabel("url:");
		get = new JButton("get request");
		post = new JButton("post request");
		put = new JButton("put request");
		head = new JButton("head request");
		delete = new JButton("delete request");
		options = new JButton("options request");
		trace = new JButton("trace request");
		ta = new JTextArea(100, 100);
		sp = new JScrollPane(ta);
	}

	public void launch() {
		setTitle("A SIMPLE BROWSER");
		setLayout(new FlowLayout());
		add(urlas);
		add(addressbar);		
		add(get);
		add(post);
		add(put);
		add(head);
		add(delete);
		add(options);
		add(trace);
		add(ta);
		add(sp);
		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String urladdress = addressbar.getText();
				URL url = null;
				if(urladdress!= null)
					try {
						url = new URL(urladdress);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
				String method = "";
				Object obj=e.getSource();
				if(obj==get)
					method="GET";
				if(obj==post)
					method="POST";
				if(obj==put)
					method="PUT";
				if(obj==head)
					method="HEAD";
				if(obj==delete)
					method="DELETE";
				if(obj==options)
					method="OPTIONS";
				if(obj==trace)
					method="TRACE";
				HttpURLConnection connection=null;
				try {
					connection = (HttpURLConnection)url.openConnection();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				connection.setDoOutput(true);
				try {
					connection.setRequestMethod(method);
				} catch (ProtocolException e1) {
					e1.printStackTrace();
				}
				try {
					connection.connect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				InputStream in=null;
				try {
					in = connection.getInputStream();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				int ch = 0;
				try {
					ch = in.read();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				while(ch!=-1){
					ta.append((char)ch+"");
					try {
						ch=in.read();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				connection.disconnect();
			}
		};
		get.addActionListener(a);
		post.addActionListener(a);
		put.addActionListener(a);
		head.addActionListener(a);
		delete.addActionListener(a);
		options.addActionListener(a);
		trace.addActionListener(a);
		setSize(1024,700);
		setVisible(true);
	}
}
public class OwnClient1{
	public static void main(String args[]){
		OwnClient2 owl = new OwnClient2();
		owl.launch();
	} 
}
