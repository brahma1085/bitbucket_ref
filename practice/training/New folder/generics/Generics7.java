package generics;

class Generics7 {
	private static <T> void genericStaticMethod() { }
	private <T> void genericInstanceMethod() { }

	public static void main(String[] args) {                
                new Generics7().doCalling(); 
        }

	void doCalling() {
	        // (1) Insert code here.
                Generics7.genericStaticMethod();
                //<String>genericStaticMethod();          //Compiler error
                Generics7.<String>genericStaticMethod ();
                genericInstanceMethod();
                this.genericInstanceMethod();
                //<String>genericInstanceMethod();        //Compiler error
                this.<String>genericInstanceMethod ();
	}
}


/*
Which method declaration when inserted at (1) will result in the above code to compile without errors and warnings?
Select the one answer.
(a) GMI.genericStaticMethod();
(b) <String>genericStaticMethod();
(c) GMI.<String>genericStaticMethod ();
(d) genericInstanceMethod();
(e) this.genericInstanceMethod();
(f) <String>genericInstanceMethod();
(g) this.<String>genericInstanceMethod ();
*/