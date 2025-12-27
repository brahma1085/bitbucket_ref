package varargs;

class Varargs {
    public static void main(String[] args) {
        Varargs v = new Varargs();
        String s = new String();
        String[] arr = {"1", "ere"};  
        //v.f(s, arr);  //compile-err
        v.f(s);   //ok
        v.f(s, s);   //ok
        v.f(arr);  //ok

        //------------------
        doIt(1);
        doIt(1,2);
      
        //------------------
     }

    //void f(String... var) {  //ok
    //void f(String ... var) {  //ok
    //void f(String ...var) { //ok
    void f(String...var) {  //ok
    }

    //static void doIt(int j, int... params) { }
    //static void doIt(int[] params) { }  //compiler error, see remark at the end
    static void doIt(int... params) { }
    //static void doIt(int j, int... params[]) { }  //compile-err
}


/*  compiler error for
    static void doIt(int[] params) { } 
1. int[] is different from int; it cannot be used for doIt(1) and doIt(1,2)
2. It cannot be defined together with "static void doIt(int... params) {}". They are considered to be the same.
*/
