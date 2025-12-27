package com.scb.common.log4j;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;



public class LogDetails {
	private  WriterAppender appender=null;
	
	private static LogDetails instance;
	
	public static LogDetails getInstance()
	    {    
	        if(instance==null)
	            instance = new LogDetails();
	        
	        return instance;
	    }
	
	private LogDetails() {
		System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		
	
		        }
	
	public  Logger getLoggerObject(String name){
		
		Logger logger=null;
		HTMLLayout layout= new HTMLLayout();
		String jboss_path=System.getenv("JBOSS_HOME");
		jboss_path=jboss_path+"/server/default/deploy/LogFiles.war";
		File logFile=new File(jboss_path);
		File web_inf=new File(jboss_path+"/WEB-INF");
		if(!logFile.exists()){
			logFile.mkdir();
		}
		if(!web_inf.exists()){
			web_inf.mkdir();
		}
		if(name.equalsIgnoreCase("Pygmy")){
			try{
				FileOutputStream output=new FileOutputStream(jboss_path+"/Pygmy.html");
				appender=new WriterAppender(layout,output);
			    }
			 	catch(Exception e){
			 		
			 	}
			logger=Logger.getLogger(name);
		 	logger.addAppender(appender);
		 	logger.setLevel((Level)Level.DEBUG);
		 	logger.debug("here is some debug");
		 	logger.error("here is some error");
		}
		if(name.equalsIgnoreCase("TD")){
			try{
				FileOutputStream output=new FileOutputStream(jboss_path+"/TD.html");
				appender=new WriterAppender(layout,output);
			    }
			 	catch(Exception e){
			 		
			 	}
			logger=Logger.getLogger(name);
		 	logger.addAppender(appender);
		 	logger.setLevel((Level)Level.DEBUG);
		 	logger.debug("here is some debug");
		 	logger.error("here is some error");
		}	
		else{
			try{
				FileOutputStream output=new FileOutputStream(jboss_path+"/Server.html");
				appender=new WriterAppender(layout,output);
			    }
			 	catch(Exception e){
			 		
			 	}
			logger=Logger.getLogger(name);
		 	logger.addAppender(appender);
		 	logger.setLevel((Level)Level.DEBUG);
		 	logger.debug("here is some debug");
		 	logger.error("here is some error");
		}
		 	
	 	
		 	return logger;
	}

	}

