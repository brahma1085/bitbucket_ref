class Conversion3 {
    /* these two can work  together
    static int m(int a, int b) { return a+b; }     //m(12,2)  uses this 
    static int m(Integer a, Integer b) { return a-b; }
    */

    /* only this one is ok
    static int m(Integer a, Integer b) { return a-b; }   //m(12,2) uses this
    */

    /* these two don't work for m(12,2). The compiler doesn't know which method it should use. 
    static int m(Integer a, int b) { return a+b; }    
    static int m(int a, Integer b) { return a-b; }
    */

    static int m(float a, byte b) { return (int)(a+b); }    

    //static int m(int a, int b) { return a+b; }      // works for m(new Integer(12), new Integer(2))
    //static float m(float a, float b) { return a-b; } // also works for m(new Integer(12), new Integer(2))

    public static void main(String[] args) {
           System.out.println(m(12,2));   
           System.out.println(m(new Integer(12), new Integer(2)));  
     }
}