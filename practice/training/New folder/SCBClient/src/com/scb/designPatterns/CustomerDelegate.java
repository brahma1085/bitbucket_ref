package com.scb.designPatterns;

/**
 * Created by IntelliJ IDEA.
 * User: ${Sreenivas}
 * Date: Nov 19, 2007
 * Time: 12:20:59 PM
 * To change this template use File | Settings | File Templates.
 */
import javax.ejb.CreateException;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AddressTypesObject;

import java.rmi.RemoteException;
import java.util.Calendar;

import com.scb.designPatterns.exceptions.SystemException;
import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.ServiceLocatorException;

import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import general.Validations;



public class CustomerDelegate { 
    private ServiceLocator _instance;
    private CustomerHome custHome;
    private CustomerRemote cRemote; 
    private CommonRemote commonRemote;
    private CommonHome commHome;
    public CustomerDelegate() throws ApplicationException, SystemException {
        custHome =(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
        commHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
        System.out.println("custHome=="+custHome);
        try{
        	if(commHome!=null){
        		commonRemote=commHome.create();
        		System.out.println("commonRemote=="+commonRemote);
        	}
        }catch(Exception e){
        	 throw new SystemException("SystemException:NamingException Occured"+e);	
        }
        try {
            if(custHome !=null){  
                cRemote = custHome.create();
                System.out.println("cRemote=="+cRemote);
            }
            else
                System.out.println("Ya its null here");
        } catch (RemoteException e) {
            throw new SystemException("SystemException:NamingException Occured"+e);
        } catch (CreateException ex) {
            throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
        }
    }

    public CustomerDelegate(String id)throws ApplicationException {
        // reconnect to the session bean for the given id
        reconnect(id);
    }

    public String getID() throws ApplicationException {
        try {
            return ServiceLocator.getId(cRemote);
        } catch (ServiceLocatorException e) {
            throw new ApplicationException("ApplicationException unable to get Session Bean"+e);
        }
    }
    public CustomerRemote getCRemote(){
        if(cRemote!=null){
            return cRemote;
        }
        else{
            return null;
        }
    }
    public void reconnect(String id) throws ApplicationException {
        try {
            cRemote = (CustomerRemote) ServiceLocator.getService(id);
        } catch(ServiceLocatorException ex){
            throw new ApplicationException("ApplicationException unable to get ServiceLocator"+ex);
        }



    }
      
     public String[] getComboitems(int type)throws RemoteException,SystemException{
        String[]  string_custtypes,string_address_types,string_occupation=null,string_sub_occupation,string_salutation,string_marital_status,string_address_proof,string_name_proof,string_caste,string_countries=null,string_states=null,CustomerParams=null;
        if(type==1){
         string_custtypes=cRemote.getIndivisual_Institute("CustomerType");
         return string_custtypes;
        }

         if(type==2){
            string_address_types=cRemote.getCustomerParams("AddrTypes");
            return string_address_types;
         }

         if(type==3){
             string_occupation=cRemote.getCustomerParams("Occupation");
             return string_occupation;
         }

          if(type==4){
            string_occupation=getComboitems(3);  
            string_sub_occupation=cRemote.getCustomerParams("Salaried");
            return string_sub_occupation;
           }
        
           if(type==5){
            string_salutation=cRemote.getCustomerParams("Salutation");
            return string_salutation;
           }
           if(type==6){
            string_marital_status=cRemote.getCustomerParams("MaritalStat");
            return string_marital_status;
           }
           if(type==7){
            string_address_proof=cRemote.getCustomerParams("AddressProof");
            return string_address_proof;
           }
           if(type==8){
            string_name_proof=cRemote.getCustomerParams("NameProof");
            return string_name_proof;
           }
           if(type==9){
            string_caste=cRemote.getCustomerParams("Castes");
            return string_caste;
           }
           if(type==10){
            string_countries=cRemote.getCustomerParams("Country");
            return string_countries;
           }
           if(type==11){
           string_countries=getComboitems(10);    
           string_states=cRemote.getCustomerParams("State$$$"+string_countries[0]);
           return string_states;    
           }
           if(type==12){
           CustomerParams=cRemote.getCustomerParams("CustomerParameters");
           
           }
         return CustomerParams;
    }


/*====================== methods for getting details of customer from database========== */
     public CustomerMasterObject getCustomerdetails(int cid)throws CustomerNotFoundException,RemoteException
     {
     CustomerMasterObject mastobj=cRemote.getCustomer(cid);	
         
     return mastobj;
     }
      
/*===================== method used for getting detail of customer from Address table*====================*/
     
     public java.util.HashMap getAddress(int cid)throws RemoteException,CustomerNotFoundException{
    	 java.util.HashMap address=new java.util.HashMap();
    	 address=cRemote.getAddress(cid);
    	 System.out.println("address values in customer deligate" +address); 
    	 return address;
     }
     
/*==================================method used for fetching values for customization form=========================*/
     
     public String[] Customizationparam(String str1)throws RemoteException{
    	String str[]=null;
    	str=cRemote.getCustomerParams(str1);
    	return str;
     }
      
 /*================================code used to store customer param values=============================*/
     public int StoreCustomParams(String str,String arr)throws RemoteException{
    	 int a=0;
    	 a=cRemote.storeCustomerParams(str, arr);
    	 return a;
     }
     
     
/*==================================method for fetching addess type=====================================*/
    public AddressTypesObject[] CustomerAddress()throws RemoteException{
    	AddressTypesObject address[]=null;
    	address=commonRemote.getAddressTypes();
    	
    	return address;
    }
/*==================================methods view-log=================================*/
    public String[] CustomerViewLog(String tablename)throws RemoteException{
    	String str[]=null;
    	str=commonRemote.getColumns(tablename);
    	return str; 
    }
  public Object[][] Customerviewlogcoloum(String str,String column,int no)throws RemoteException{
	  Object[][] obj=null;
	  obj=commonRemote.getRows(str, column, no);
	  return obj;
  }
  
  public int storeCustomerDetail(CustomerMasterObject cust_mast_obj)throws RemoteException{
   
	  System.out.println("The religion Here in Deligate is===>"+cust_mast_obj.getReligion());
	  
	  int i=cRemote.storeCustomer(cust_mast_obj);	  
 	  
  return i;	  
  }
  
  public String CustomerName(String Cid)throws RemoteException{
  String str=null;
  
  str=cRemote.getCustomerName(Cid);
  
  return str;	  
  }
  
//******************code for verifying customer***************************//  
  
  public int VerifyCustomer(int cid,String uid,String tml)throws RemoteException{
	  int a=0;
	  
	  a=cRemote.verifyCustomer(cid, uid, tml);
	  
	  return a;
  }
  
  public CustomerMasterObject UnverifiedCustomer(int cid)throws RemoteException{
	  CustomerMasterObject mastobj=null;
	  mastobj= cRemote.customer_verify(cid);
	  
	  return mastobj;
  }
 
  public String[] getcustomerIds(String cum_name)throws RemoteException{
  String[]	str=null;
  str=cRemote.getCustomerIds(cum_name);
  return str;
  }
  public int removeData(String table,String col,String value)throws RemoteException{
  int a=cRemote.removeData(table, col, value);	  
  return a;
  }
  public boolean checkAvailability(String table,String col,String value)throws RemoteException{
	  boolean flag=cRemote.checkAvailability(table, col, value);
	  return flag;
  }
  
  public AccSubCategoryObject[] getAccSubCategories(int no) throws RemoteException
  {
	      
	  AccSubCategoryObject[] accsub=commonRemote.getAccSubCategories(no);
	  return accsub;
  }
	public static String getSysDate() {
		
        Calendar c = Calendar.getInstance();
        try {
            return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
        } catch (DateFormatException e) {
                e.printStackTrace();
        }
        return null;
    }	
  
}
