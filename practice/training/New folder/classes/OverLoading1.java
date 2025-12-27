package classes;

class Animal { 
        public void eat() { System.out.println("Animal eat"); }
}

class Horse extends Animal { 
        public void eat() { System.out.println("Horse eat"); }
        public void eat(String grass) { System.out.println("Horse eat grass"); }
}

public class OverLoading1 {
        public static void main(String[] args) {
              Animal a = new Horse();
              a.eat();
              //a.eat("grass");  // compile-err
              ((Horse)a).eat("grass");

	      OverLoading1 over = new OverLoading1();
              over.testOverloading();
        }

        void testOverloading() {  //all have compiler error: ambigious matching
              /*
		method ( 1 , 1 , 1);
		method( new Integer(1) , new Integer(2) , new Integer(3) );
		method ( 1 , new Integer(5) );
		method ( new Integer(10) , 1 );
              */
	}

	void method( Integer... I ){
		System.out.println("Eye in the sky");
	}

	void method( int... i ){
		System.out.println("Fly in the pie");
	}
}