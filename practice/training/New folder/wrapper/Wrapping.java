package wrapper;

class Wrapping {
     public static void main(String[] args) {
	Character[] version = {'1', '.', '5'}; // (1)
	for (Integer iRef = 0; // (2)
	     iRef < version.length; // (3)
	     ++iRef) // (4)
	   switch(iRef) { // (5)
	     case 0: 
             case 2:
		    System.out.println(iRef + ": " + version[iRef]); // (6)
	   }
     }
}