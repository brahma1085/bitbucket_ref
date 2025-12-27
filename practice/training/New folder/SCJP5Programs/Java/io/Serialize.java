package io;

import java.io.*;

class Keyboard { }

class SpecialSerial implements Serializable {
          transient int y = 7;
          static int z = 9;
}

public class Serialize implements Serializable {
           private Keyboard k = new Keyboard();

           public static void main(String[] args) {
	Serialize s0 = new Serialize();
	s0.storeIt(s0);

	SpecialSerial s = new SpecialSerial();
	try {
	      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("myFile"));
	      os.writeObject(s); os.close();
	      System.out.print(++s.z + " ");

	      ObjectInputStream is = new ObjectInputStream( new FileInputStream("myFile"));
	      SpecialSerial s2 = (SpecialSerial)is.readObject();
	      is.close();
	      System.out.println(s2.y + " " + s2.z);     // final out put: 10  0  10
	} catch (Exception x) {System.out.println("exc"); }
           }
           
           void storeIt(Serialize s) {
	try {
	           ObjectOutputStream os = new ObjectOutputStream(
	           new FileOutputStream("myFile"));
	           os.writeObject(s);
	           os.close();
	           System.out.println("done");
	} catch (Exception x) {System.out.println("exc"); }   //output exc
           }
}