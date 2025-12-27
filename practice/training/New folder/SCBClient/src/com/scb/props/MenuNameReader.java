package com.scb.props;

import javax.swing.*;
import java.util.Properties;
import java.util.Enumeration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 27, 2007
 * Time: 10:24:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuNameReader {
    private static Properties screenProp=null,screenProp1=null;
    /*private static Properties licenceProp = null;*/
        static
        {
            try{
                
           //   File screenFile =new File("usr\\local\\ImportantMenuScreen\\MenuScreen.properties");
            
            	File screenFile =new File("//root//ImportantMenuScreen//MenuScreen.properties");
                
            	FileInputStream fileinputstream= new FileInputStream(screenFile);
                
                
            	screenProp = new Properties();
                screenProp.load(fileinputstream);
                /*File licenceFile =new File("bin/config/License.properties");
                fileinputstream=new FileInputStream(licenceFile);
                licenceProp = new Properties();
                licenceProp.load(fileinputstream);*/
                
            }catch(IOException io){
                /*JOptionPane.showMessageDialog(null,"Error Reading license file");*/
                io.printStackTrace();
            }

        }

        public static String getScreenProperties(String key){
            return (String)screenProp.get(key);
        }

        public static boolean containsKeyScreen(String key){
            return screenProp.containsKey(key);
        }

        public static Enumeration getProperties(){
            return screenProp.propertyNames();
        }

       /* public static String getLicenceProperties(String key){
            return (String)licenceProp.get(key);
        }

        public static java.util.Hashtable  getScreenHash(){
            return screenProp;
        }

        public static java.util.Hashtable  getLicenseHash(){
            return licenceProp;
        }*/
     public static void main(String[] a){
            /*MenuNameReader m=new MenuNameReader();*/
            System.out.println("menunamereader=="+MenuNameReader.getScreenProperties("1001"));
       }
}
