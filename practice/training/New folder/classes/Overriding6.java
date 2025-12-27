package classes;

class BaseClass{ 
     private float x=1.0f; 
     private float getVar() { return x; } 
} 

class Overriding6 extends BaseClass{ 
     private float x=2.0f; 

     //insert code 
     //float getVar() { return x; }   //ok
     //public double getVar() { return x; }   //ok
     //public double getVar(){ return x; } //ok
     //protected float getVar(){ return x; } //ok
     public float getVar(float f){ return x; } //ok

     public static void main(String[] args) {
          Overriding6 o = new Overriding6();
          BaseClass b = o;
          System.out.println(o.getVar(2.4f));
          //System.out.println(b.getVar());  //compile-err:getVar() is private in BaseClass
     }
} 

/*
what are true to override getVar()? 

A.float getVar(){ 
B.public float getVar(){ 
C.public double getVar(){ 
D.protected float getVar(){ 
E.public float getVar(float f){ 

*/