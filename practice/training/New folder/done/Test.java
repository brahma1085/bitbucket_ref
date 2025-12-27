class A {
public int number = 100;
public int getNumber() {
return number;
}
}

class B extends A {
public int number = 200;
public int getNumber() {
return number;
}
}

class Test {
public static void main (String ... args) {
A a = new A();
A b1 = new B();
B b2 = new B();
System.out.println("a = " + a.getNumber());
System.out.println("b1 = " + b1.getNumber());
System.out.println("b2 = " + b2.getNumber());
}
}
