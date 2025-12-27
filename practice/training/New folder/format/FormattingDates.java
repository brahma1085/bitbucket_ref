   package format;

   import java.util.Calendar;

   public class FormattingDates  {

     public static void main(String[] args) {
       System.out.printf("Right now it is %tr on " +
                         "%<tA, %<tB %<te, %<tY.%n",
                         Calendar.getInstance());
     }
   }
