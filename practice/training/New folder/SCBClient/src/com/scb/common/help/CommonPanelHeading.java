package com.scb.common.help;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 5, 2007
 * Time: 12:24:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonPanelHeading {
    private static String personal="Personal Details";
    private static String application="Application Details";
    private static String introucer="Introducer Details";
    private static String Signature="SignatuteInstruction Details";
    private static String SignatureTab="SignatuteInstruction Details";
    private static String JointHolder="JointHolder Details";
    private static String Nominee="Nominee";
    private static String AgentDetails="Agent Details";
    private static String fromAccInfo="From AccountInfo";
    private static String toAccInfo="To AccountInfo";
    private static String depositDetails="Deposit Details";
    private static String loanDetails="Loan Details";
    private static String Transfer="Transfer Details";
    private static String property="Property Details";
    private static String vechicle="Vehicle Details";
    public static void setVechicle(String vehicle){
    	CommonPanelHeading.vechicle=vehicle;
    }
    public static String getVechicle(){
    	return CommonPanelHeading.vechicle;
    }
    public static void setProperty(String property){
    	CommonPanelHeading.property=property;
    }
    public static String getProperty(){
    	return CommonPanelHeading.property;
    } 
    public static void setApplication(String application){
    	CommonPanelHeading.application=application;
    }
    public static String getApplication(){
    	return CommonPanelHeading.application;
    }
    public static String getTransfer() {
		return Transfer;
	}

	public static void setTransfer(String transfer) {
		Transfer = transfer;
	}

	public static String getSignatureTab(){
		return SignatureTab;
	}
    
	public static String getSignature(){
		return Signature;
	}
    
	public static String getJointHolder(){
		return JointHolder;
	}
	
    public static String getPersonal() {
        return personal;
    }

    public static String getIntroucer() {
        return introucer;
    }
    
    public static String getNominee() {
        return Nominee;
    }
    
    public static String getfromAccInfo(){
    	return fromAccInfo;
    }
    
    public static String gettoAccInfo(){
    	return toAccInfo;
    }

	public static String getDepositDetails() {
		return depositDetails;
	}

	public static void setDepositDetails(String depositDetails) {
		CommonPanelHeading.depositDetails = depositDetails;
	}

	public static String getLoanDetails() {
		return loanDetails;
	}

	public static void setLoanDetails(String loanDetails) {
		CommonPanelHeading.loanDetails = loanDetails;
	}

	public static String getAgentDetails() {
		return AgentDetails;
	}

	public static void setAgentDetails(String agentDetails) {
		AgentDetails = agentDetails;
	}


}
