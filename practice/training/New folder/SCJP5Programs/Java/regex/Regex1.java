package regex;

import java.util.regex.*;

class Regex1 {
    public static void main(String[] args) {
         Pattern p = Pattern.compile(args[0]);
         Matcher m = p.matcher(args[1]);
         boolean b = false;

         while(b = m.find()) {
            System.out.print(m.start() + m.group());
         }
    }
}

//java Regex2 "\d*" ab34ef
// result: 01234456