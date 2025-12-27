package io;

import java.util.*;
import java.text.*;

class Dates {
        public static void main(String [] args) {
               Date d = new Date(1123631685981L);
               //DateFormat df = new DateFormat();  // compile-err
               DateFormat df = DateFormat.getDateInstance();
               System.out.println(df.format(d));   // output: 2005-8-9
        }
}