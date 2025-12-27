package exception;

class Exceptions {
    static String s = "-";
    public static void main(String[] args) {
        try {
	    throw new Exception();  } 
        catch (Exception e) {
	    try {
	       try { throw new Exception(); } 
               catch (Exception ex) { s += "ic "; }

	       throw new Exception(); }
	    catch (Exception x) { s += "mc "; }
	    finally { s += "mf "; } } 
        finally { 
            try {  throw new Exception(); } 
            catch (Exception ex) { }
            s += "of ";  }

        System.out.println(s);

        try { return; } // if using "return 1;", then compiler error.
        finally { System.out.println("Finally");  }  // output: "Finally"
    }
}

/*compile succeed, output is:
-ic mc mf of

Finally
*/