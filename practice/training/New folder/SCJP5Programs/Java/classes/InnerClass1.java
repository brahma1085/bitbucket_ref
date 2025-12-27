package classes;

class Cookable{
     void cook(Foo f){}
}

class InnerClass1 {
       Cookable c =new Cookable();
       c.cook(new Foo() {//line1 *
           public void foof(){//we need to overide abstract method here
               System.out.println("foofy");
           }});

public static void main(String[] args){
     InnerClass1 myObject=new InnerClass1();
     InnerClass1 inner=myObject.c;
     inner.foof();
     }   //line2
}

interface Foo{
     void foof();
}

//****line1 compile error: need Identifier, check InnerClass2