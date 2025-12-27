package collections;

import java.util.*; 
import java.io.*;

class Property {
   public static void main(String[] args) {
       Properties p = new Properties();
       p = System.getProperties();
       try {
           p.storeToXML(new FileOutputStream("collections/property.out"), null);
           p.loadFromXML(new FileInputStream("collections/property.out"));
           p.list(System.out);
       } 
       catch (InvalidPropertiesFormatException ie) { System.out.println("InvalidPropertiesFormatException"); }
       catch (IOException ioe) { System.out.println("IOException"); }
       catch (Exception e) { e.printStackTrace(); }
   }
}