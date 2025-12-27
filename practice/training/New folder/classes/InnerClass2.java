package classes;

interface Foo{ void foof(); }

class Cookable{
	public Foo f = null;
	void cook(Foo f){
		this.f = f;
	}}

public class InnerClass2 {
	 Cookable c =new Cookable();
	 {
	      c.cook(new Foo() {
	          public void foof(){
	           System.out.println("foofy");
	          };
                        });
 	 }

	public static void main(String[] args) {
		InnerClass2 myObject=new InnerClass2();  
  		Cookable inner=myObject.c;
		inner.f.foof();
                 } 
}