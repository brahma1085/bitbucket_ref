package io;

import java.io.*;

class Collar {
                  int size;
                  Collar(int size) { this.size = size; }
                  int getCollarSize() { return size; }
}

class Animal { // not serializable !
	 String name;
}

class Dog extends Animal implements Serializable {
	transient private Collar theCollar; // we can't serialize this
	int dogSize;

	public Dog(Collar collar, int size, String name) {
	        theCollar = collar;
	        dogSize = size;          // not inherited
                          this.name = name;     // inherited
	}

	public Collar getCollar() { return theCollar; }
	
	private void writeObject(ObjectOutputStream os) {
	        // throws IOException { // 1
	        try {
	              os.defaultWriteObject(); // 2
	              os.writeInt(theCollar.getCollarSize()); // 3
	        } catch (Exception e) { e.printStackTrace(); }
	}

	private void readObject(ObjectInputStream is) {
	        // throws IOException, ClassNotFoundException { // 4
	        try {
	              is.defaultReadObject(); // 5
	              theCollar = new Collar(is.readInt()); // 6
	        } catch (Exception e) { e.printStackTrace(); }
	}
}


public class SuperNotSerial {
        public static void main(String [] args) {
	Dog d = new Dog(new Collar(10), 35, "Fido");
	System.out.println("before: " + d.name + " "+ d.dogSize);
	try {
	        FileOutputStream fs = new FileOutputStream("testSer.ser");
	        ObjectOutputStream os = new ObjectOutputStream(fs);
	        os.writeObject(d);
	        os.close();
	} catch (Exception e) { e.printStackTrace(); }

	try {
	        FileInputStream fis = new FileInputStream("testSer.ser");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        d = (Dog) ois.readObject();
	        ois.close();
	} catch (Exception e) { e.printStackTrace(); }

	System.out.println("after: " + d.name + " "+ d.dogSize);
        }
}

