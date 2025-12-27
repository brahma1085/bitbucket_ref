class Animal
{
    void method  throws IOException {}    // compile-err: missing () after method
}

class dog extends Animal
{
     void method  throws FileNotFoundException {}  // compile-err: missing () after method
}
