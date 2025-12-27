import java.io.*;
import java.text.Collator;
import java.util.*;

class Compare {
          private static int lastLineScanned = 0; 

          public static void main(String[] args) {
                 File f1 = new File("f:Regedit Backup 2006-7-31_CleanMMSAssistCompletely1.reg");
                 File f2 = new File("f:Regedit Backup 2006-7-31_test.reg");

                 if (!f1.exists() || !f2.exists()) {
	        System.out.println("Files not exist");
                          return;
                 }                
           
                 BufferedReader bf1 = null;
                 BufferedReader bf2 = null;
                 boolean same = true;
                 String s1 = "";
                 String s2 = "";
                 int line = 0;
                 //Collator collator = Collator.getInstance();

                // Identify the default file encoding
                OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
                String encoding = out.getEncoding();
                System.out.println(out.getEncoding());

                 try {
                      //bf1 = new BufferedReader(new InputStreamReader(new FileInputStream(f1),  "GBK"));
                     // bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2),  "GBK"));
                      bf1 = new BufferedReader(new FileReader(f1));
                      bf2 = new BufferedReader(new FileReader(f2));

                      while (same && (s1=bf1.readLine())!=null && (s2=bf2.readLine())!=null ) {
                                 ++line;
                                 if (!s1.equals(s2)) {
                                 //if (collator.compare(s1, s2) != 0)  {
                                      same = false;
                                      System.out.println("lineNo:   " + line);
                                      System.out.println("     1:   " + s1);
                                      System.out.println("     2:   " + s2);
                                 }
                           }
                    }
                    catch (IOException e) { e.printStackTrace(); }
                    finally {
                          try {
                             if (bf1 != null)
                                  bf1.close();
                             if (bf2 != null)
                                  bf2.close();
                          }  catch (IOException e) { e.printStackTrace(); }
                    }
          } 
}