import java.util.Properties;

class ObjectCreation2 {
        Properties myProps = new Properties( );  
        //myProps has to be declared before the following initializer block

        // set up myProps
        {
            myProps.put("foo", "bar");
            myProps.put("boo", "gee");
        }

        int a = 5;

        public static void main(String[] args) { 
          
               ObjectCreation2 obj = new ObjectCreation2();
        }
}