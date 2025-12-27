package enums;

enum Meal {
      BREAKFAST(8),
      
      // (1) Insert code here.
      LUNCH(0) { public int getHour() { return (BREAKFAST.hh + DINNER.hh)/2; } },   //ok
      //LUNCH(0) { hh = 13; },             // compiler error
      //LUNCH(0) { this.hh = 13; },        // compiler error
      //LUNCH(0) { this.setHour(13); },    // compiler error

      DINNER(18);
      Meal(int hh) { this.hh = hh; }
      private int hh;
      public int getHour() { return this.hh; }
      public void setHour(int hh) { this.hh = hh; }
}

public class Enum4 {
      public static void main(String[] args) {
      	    System.out.println(Meal.LUNCH + " served at " + Meal.LUNCH.getHour());
      }
}


/*
Which code when inserted at (1) will result in the program to compile and print "LUNCH served at 13"?
Select the one correct answer.
(a) LUNCH(0) { hh = 13; },
(b) LUNCH(0) { this.hh = 13; },
(c) LUNCH(0) { this.setHour(13); },
(d) LUNCH(0) { public int getHour() { return (BREAKFAST.hh + DINNER.hh)/2; } },    
(e) None of the above code will produce the specified output.
*/