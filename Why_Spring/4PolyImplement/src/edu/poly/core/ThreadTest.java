package edu.poly.core;

class StudentThread extends Thread {
	// public synchronized void start() {
	// start0;
	// }
	// private native void start0();==>this method calls run()==>if we override
	// it,our thread class run() is called
	@Override
	public void run() {
		super.run();
		System.out.println("student thread==>run()");
	}
}

public class ThreadTest {

	public static void main(String[] args) {
		try {
			// A a=new B();
			// normally we have to pass the class name through properties or XML
			Runnable runnable = (Runnable) Class.forName(
					"edu.poly.core.StudentThread").newInstance();
			// a.x();
			runnable.run();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
