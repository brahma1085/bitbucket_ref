package collections;

import java.util.*;

public class ListFeatures {
     public static void main(String[] args) {
          List<String> list = new ArrayList<String>();
          //Collections.addAll(list, "Rat", "Manx", "Cymric", "Mutt", "Pug", "Cymric", "Pug");    
          //list.addAll("Rat", "Manx", "Cymric", "Mutt", "Pug", "Cymric", "Pug");   // no such method
          list.addAll(Arrays.asList("Rat", "Manx", "Cymric", "Mutt", "Pug", "Cymric", "Pug"));
          List<String> sublist = list.subList(1, 4);
          sublist.add(2, "Added");
          sublist.set(0, "Seted");
          System.out.println(list);
     }

}