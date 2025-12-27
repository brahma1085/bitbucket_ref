package development;

import java.util.*;
import development.foo.*;   //ok
import classes.*;   //ok

class SysProperties {
      public static void main(String[] args) {
             Properties p = System.getProperties();
             p.setProperty("myProp", "myValue");
             p.list(System.out);
             p.remove("myProp");
             p.remove("cmdVal");
             p.list(System.out);
      }
}