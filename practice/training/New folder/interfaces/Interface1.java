package interfaces;

//interface A extends A {}  // compile-err: cannot extends itself

interface A extends B {}
interface B extends A {}   // compile-err