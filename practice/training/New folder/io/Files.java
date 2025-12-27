package io;

import java.io.*;

class Files {
    public static void main(String[] args) {
         try{
              File f = new File("test1.txt");
              BufferedReader brf = new BufferedReader(new FileReader(f));
              String s = brf.readLine();
         }catch(java.io.IOException e){}

         try {
               //---------------------------------------
               File myDir = new File("io", "mydir");
               System.out.println("myDir is a directory? " + myDir.isDirectory());
               System.out.println("Create io\\myDir " + myDir.mkdir());

               File myFile1 = new File(myDir, "myFile1.txt");
               System.out.println("myFile1 is a File? " + myFile1.isFile());
               System.out.println("Create myFile1.txt " + myFile1.createNewFile());
           
               File myFile2 = new File(myDir, "myFile2.txt");
               System.out.println("myFile2 is a File? " + myFile2.isFile());
               System.out.println("Create myFile2.txt " + myFile2.createNewFile());

               System.out.println("Delete myFile1  "+myFile1.delete());
               System.out.println("Delete myDir  "+myDir.delete());

               File myNewFile2 = new File(myDir, "myNewFile2.txt");
               System.out.println("Rename myFile2.txt to myNewFile2.txt " +myFile2.renameTo(myNewFile2));
 
               File myNewDir = new File("io", "mynewdir");
               System.out.println("Rename io\\myDir to io\\myNewDir " +myDir.renameTo(myNewDir));
               
               System.out.println("Files in io\\mynewdir: ");
               String[] list = myNewDir.list();
               for (String s : list) 
                     System.out.println("   " +s);
               System.out.println();

               //----------------------------------------
               String dirName = myDir.getParent();
               String dirName1 = myDir.getParentFile().getName();
               System.out.println("This file is in directory "+dirName + "  "+dirName1);
               System.out.println();

               //----------------------------------------
               System.out.println("getName():  " + myDir.getName());
               System.out.println("getParent():  " + myDir.getParent());
               System.out.println("getPath():  " + myDir.getPath());
               System.out.println("getAbsolutePath():  " + myDir.getAbsolutePath());
               System.out.println("isAbsolute():  " + myDir.isAbsolute());   //false
               System.out.println();

               //----------------------------------------
               FileInputStream fin1 = new FileInputStream("myinput.txt");
               FileInputStream fin2 = new FileInputStream(FileDescriptor.in);
               FileInputStream fin3 = new FileInputStream(new File("myinput.txt"));

         }catch (IOException e) {e.printStackTrace();}
    }
}