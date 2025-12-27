package format;

import java.util.Formatter;

public class UsingFormatter {

   public static void main(String[] args) {
       if (args.length != 1) {
         System.err.println("usage: " +
           "java format/UsingFormatter ");
         System.exit(0);
       }
       String format = args[0];

       StringBuilder stringBuilder = new StringBuilder();
       Formatter formatter = new Formatter(stringBuilder);
       formatter.format("Pi is approximately " + format +
         ", and e is about " + format, Math.PI, Math.E);
       System.out.println(stringBuilder);
   }
}
