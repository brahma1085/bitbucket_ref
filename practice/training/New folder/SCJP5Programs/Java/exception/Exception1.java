class Level1Exception extends Exception{}
class Level2Exception extends Level1Exception{} 
class Exception1 {    
    public static void main(String args[])    {
        int x = 4;
        try {
            try {
                switch (x) {
                    case 1:
                        throw new Level1Exception();
                    case 2:
                        throw new Level2Exception();
                }
            } 
            catch (Level1Exception e)   {}
          }
           // Here we are trying to handle something which is never occuring, the compiler scolds us.
          catch (Level1Exception e)   {}  
          catch (Exception e)  { }
   }
}