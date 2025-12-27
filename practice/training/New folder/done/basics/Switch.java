package basics;

enum Color {red, green, blue}

class Switch {
        final static int x2 = 7;
        final static Integer x4 = 8;

        public static void main(String[] args) {
              Integer x1 = 5;
              String s = "a";
              if(x1 < 9) s += "b";
              switch(x1) {
                  case 5: s += "c";
                  case x2: s += "d";
                  //case x4: s += "e";   // compile-time err: x4 needs to be compile-time constant
              }
              System.out.println(s);

              //switch(new Long(10)) {  // won't compile
              //switch (5l) {   // won't compile
              //switch ((int)5l) { //ok
              switch ((byte)5l) { //ok
              }
 
              switch (new Integer(4)) {   //ok
              //case  new Integer(4):   // error: need compile-time constant
              }

              switch ((byte)4) {  
                case 10:   break;
                //case 128:  break;  //compile-error
                case -128:  break;   //ok
              }

              switch ('c') {   //ok
                case 10: break;
                case 1000-1: break;
                case 'd': break;
              }

              //Color c = green; //error: cannot find green
              Color c = Color.green;
              switch(c) {
                 case red: System.out.print("red "); break;
                 case green: System.out.print("green "); break;
                 //case Color.blue: System.out.print("blue "); break;  //Compiler error:cannot use Color.blue
                 case blue: System.out.print("blue "); break;   //ok
                 default: System.out.println("done"); break;
              }
       }
}
