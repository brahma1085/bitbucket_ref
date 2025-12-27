package edu;

import java.rmi.Naming;

public class MyRemoteTest {

	public static void main(String[] args) {
		try {
			MyRemote service = new MyRemoteImpl();
			Naming.rebind("remoteservice", service);
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}

}
