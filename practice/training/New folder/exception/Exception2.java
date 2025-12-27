class Level1Exception extends Exception {}
class Level2Exception extends Level1Exception {}
class Level3Exception extends Level2Exception {}
class Level4Exception extends Level3Exception {}

class Exception2 {
   public static void main(String args[]) {
        int a,b,c,d,f,g,x;
        a = b = c = d = f = g = 0;
        x = 3;

        try {
           try {
              switch (x) {
                  case 1: throw new Level1Exception();
                  case 2: throw new Level2Exception();
                  case 3: throw new Level3Exception();
                  case 4: throw new Level4Exception();
             } 
             a++; 
          }
          //catch (Level3Exception e) {b++;} // (1)
          //catch (Level2Exception e) {b++;} // (2)
          //catch (Level1Exception e) {b++;} // (3)
          //catch (Exception e) {b++;} // (4)
         finally {c++;}
      }
      catch (Level4Exception e) { d++;}
      catch (Exception e) {f++;}
      finally {g++;}
      System.out.print(a+","+b+","+c+","+d+","+f+","+g);
   }
}
