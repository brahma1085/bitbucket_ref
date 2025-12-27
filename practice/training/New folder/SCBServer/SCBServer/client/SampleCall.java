package client;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;

/**
 * Created by IntelliJ IDEA.
 * User: ${Sreenivas}
 * Date: Nov 17, 2007
 * Time: 10:32:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleCall {
    CustomerHome custHome;
    CustomerRemote cRemote;
    public SampleCall(){
        try{
            custHome=(CustomerHome)HomeFactory.getFactory().lookUpHome("CUSTOMER");
            System.out.println("custHome=="+custHome);
            cRemote=custHome.create();
            /*System.out.println(cRemote.getCustomerName("101"));*/
            masterObject.customer.CustomerMasterObject cmobj=cRemote.getCustomer(101);
            System.out.println(cmobj.getCustomerID());
            System.out.println(cmobj.getFirstName());
            System.out.println(cmobj.getCaste());
            System.out.println(cmobj.getCategory());
            System.out.println(cmobj.getDOB());
            System.out.println(cmobj.getFatherName());
            System.out.println(cmobj.getMaritalStatus());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] a){
        SampleCall s=new SampleCall();
    }
}
