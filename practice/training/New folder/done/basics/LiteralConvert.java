package basics;

class LiteralConvert {
        public static void main(String[] args) {
              char a = (char) -89;    
              System.out.println(a); //output is '?'

              char b1 = (char)70000; 
              System.out.println(b1); //output is '?'

              char c = '\"';
              System.out.println(c); //output is ''

              char c1 = '\n';
              System.out.println(c1); //output is empty line

              //byte b2=256;  // compile-err
              byte b2 = 7; //ok;

              /****************************/
              float f1=4.2F; 
              Float f2=new Float(4.2F); 
              Double d=new Double(4.2); 

              System.out.println(f1==f2);     //true
              System.out.println(f2==f2);     //true
              System.out.println(f2.equals(f1));  //true
              System.out.println(f2.equals(4.2f));  //true
              System.out.println(f2.equals(4.2)); //false

              System.out.println(d==f1);   //false
              System.out.println(d.equals(f1));  //false
              System.out.println(d.equals(f2));  //false
              System.out.println(d.equals(4.2)); //true
              System.out.println();

              /****************************/
		Integer i3 = new Integer (42);
		Long l3 = new Long (42);
		Double d3 = new Double (42.0);
		//System.out.println(i3 == l3);  //compiler-err: not comparable
		//System.out.println(i3 == d3);  //compiler-err: not comparable
		//System.out.println(d3 == l3);  //compiler-err: not comparable
		System.out.println(i3.equals (d3));  //false
		System.out.println(d3.equals (i3));  //false
		System.out.println(i3.equals (42));  //true
                System.out.println();

              /****************************/
              int i1 = 2000;
              int i2 = 2000;
              int i4 = 2;
              int i5 = 2;
              Integer Ithree = new Integer(2); 
              Integer Ifour = new Integer(2); 
              System.out.println( Ithree == Ifour );    //****false
              method( i4 , i5 );     //true
              method( i1 , i2 );     //false
	}

	static void method( Integer i , Integer eye ){
              System.out.println(i == eye );
	}
}