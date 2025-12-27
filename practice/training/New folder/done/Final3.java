class  Final3{ 
    static final int i;   
    static {  i = 10; }  //without it, there will be a compile-time err: i is not initialized
    final int j;

    Final3() { j=5;}   //ok

    public static void main(String[] args) { 
        // i = 10;  // it is not valid to initialize i here
        // j = 10;  // it is not valid to initialize i here
  } 
} 
