package io;

import java.io.*;
public class DirTest{
public final static void main(String args[]){
       File f = new File("xyz");
       if(f.isDirectory())
       {
              String[] s=f.list();
              System.out.println("No of files or subdirectories is "+s.length);
              //For (String str : s)   //For is not keyword
              for (String str : s)
                     System.out.println(str);
       }
       else if(f.isFile())
              System.out.println("File");
       }
}
 
/* doesn't compile:  " For (String str : s) " at the place of str shows  needs ')' */