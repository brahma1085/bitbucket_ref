package enums.test;

public enum Enum2 { 
       BIG ( 10 ),
       SMALL ( 1 ),
       MED ( 5 );       // if missing ";", there will be compile-err. 
 
       int mySize = 0;	
	
       Enum2 ( int size )  {
	mySize = size;
        }		
}
