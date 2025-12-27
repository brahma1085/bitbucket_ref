import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import java.io.*;
import javax.rmi.*;

public class BookClient
{
  public static void main(String[] arg)
  {
    Enumeration enum1;
    Vector bookList;
    Book shoppingCart;
 
    try
    {
     Properties p=new Properties();
     p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
p.put(Context.PROVIDER_URL,"t3://localhost:7001");
Context ctx=new InitialContext(p);
Object ob=ctx.lookup("MyBook");

BookHome h=(BookHome)PortableRemoteObject.narrow(ob,BookHome.class);
shoppingCart=h.create("Lohith kumar","12345");

BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
System.out.println("welcome to Lord Krishna's Book paradise");
System.out.println("\n\n");
System.out.println("Enter the Book name that u want to add into the shopping cart...");

String book1=br.readLine();
shoppingCart.addBook(book1);



System.out.println("Enter the next book name");
String book2=br.readLine();
shoppingCart.addBook(book2);


System.out.println("Enter the next book name");
String book3=br.readLine();
shoppingCart.addBook(book3);


System.out.println("\n\n Selected Books are listed below....");
bookList=new Vector();
bookList=shoppingCart.getContents();
enum1=bookList.elements();
while(enum1.hasMoreElements())
 {
    String title=(String)enum1.nextElement();
    System.out.println(title);
 }


System.out.println("\n\n Do u want to remove any book from the cart? (y/n)");
String ch=br.readLine();
if(ch.equals("y"))
 {
   try
     {
       System.out.println("Enter the Book name...");
       String rembook=br.readLine();
       shoppingCart.removeBook(rembook);
       System.out.println("\n Book has been removed from the cart.");

       
       System.out.println("\n Selected Books are listed below...");
       bookList=shoppingCart.getContents();
       enum1=bookList.elements();
       while(enum1.hasMoreElements())
       {
          String title1=(String)enum1.nextElement();
          System.out.println(title1);
       }
        
      System.out.println("\n Thanks for dealing with us");
     }
    catch(Exception e) {
     System.err.println("caught a BookException: "+e.getMessage());
     }
  }
else
   System.out.println("\n Thanks for dealing with us");
   shoppingCart.remove();
}
 catch(Exception e1) 
 {
     System.err.println("Caught an unexpected exception!");
     e1.printStackTrace();
  }
}
}