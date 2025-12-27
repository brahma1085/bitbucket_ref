package io;

import java.io.*;

public class NonExistFile
{
   public static void main(String args[])
   {
      File f = new File("afilethatprobablydoesntexist");
      System.out.println(f.getAbsolutePath());
      System.out.println(f.exists());
   }
}

