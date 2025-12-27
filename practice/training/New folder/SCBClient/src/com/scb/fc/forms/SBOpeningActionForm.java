package com.scb.fc.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

    /**
     * Created by IntelliJ IDEA.
     * User: user
     * Date: Dec 3, 2007
     * Time: 10:50:35 AM
     * To change this template use File | Settings | File Templates.
     */
    public class SBOpeningActionForm extends ActionForm implements Serializable {
       String pageId,defSIndex,forward,scrollNum,date,amount,name,custname,chqbook,nom;
       String acType,acNum,cid,introAcType,introAcNum,detailsCombo,submit,cancel,tabPaneHeading,tabPaneSelect,defaultTab,signIndex,defaultSignIndex,valalert;
       String check,nomcid,nomname,nomdob,nomage,nomsex,nomaddress,nomrelation,nompercentage;
       String rscroll,rname,ramount,rdate,jointh,jointcid,beforesub,nomcidrelation,dohere;
       String sysdate;

       public String getSysdate() {
       		return sysdate;
       	}

       	public void setSysdate(String sysdate) {
       		this.sysdate = sysdate;
       	}
       
       public String getCheck() {
		return check;
       }

       public void setCheck(String check) {
		this.check = check;
		}

	public String getNomcid() {
		return nomcid;
	}

	public void setNomcid(String nomcid) {
		this.nomcid = nomcid;
	}

	public String getAcType() {
            return acType;
       }

       public void setAcType(String acType) {
    	   this.acType = acType;
       }

       public String getAcNum() {
    	   return acNum;
       }

       public void setAcNum(String acNum) {
    	   this.acNum = acNum;
       }

       public String getCid() {
    	   return cid;
       }

       public void setCid(String cid) {
    	   this.cid = cid;
       }

       public String getIntroAcType() {
    	   return introAcType;
       }

       public void setIntroAcType(String introAcType) {
    	   this.introAcType = introAcType;
       }

       public String getIntroAcNum() {
    	   return introAcNum;
       }

       public void setIntroAcNum(String introAcNum) {
    	   this.introAcNum = introAcNum;
       }

       public String getDetailsCombo() {
    	   return detailsCombo;
       }

       public void setDetailsCombo(String detailsCombo) {
    	   this.detailsCombo = detailsCombo;
       }

       public String getPageId() {
    	   return pageId;
       }

       public void setPageId(String pageId) {
    	   this.pageId = pageId;
       }

       public String getSubmit() {
    	   return submit;
       }

       public void setSubmit(String submit) {
		System.out.println("hi i a m");
		this.submit = submit;
       }

       public String getCancel() {
		return cancel;
       }

       public void setCancel(String cancel) {
		System.out.println("hi i a m");
		this.cancel = cancel;
       }

	public String getTabPaneHeading() {
		return tabPaneHeading;
	}

	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}

	public String getTabPaneSelect() {
		return tabPaneSelect;
	}

	public void setTabPaneSelect(String tabPaneSelect) {
		this.tabPaneSelect = tabPaneSelect;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getSignIndex() {
		return signIndex;
	}

	public void setSignIndex(String signIndex) {
		this.signIndex = signIndex;
	}

	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		System.out.println("defaultSignIndex=="+defaultSignIndex);
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getDefSIndex() {
		return defSIndex;
	}

	public void setDefSIndex(String defSIndex) {
		this.defSIndex = defSIndex;
	}

	public String getValalert() {
		return valalert;
	}

	public void setValalert(String valalert) {
		this.valalert = valalert;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getScrollNum() {
		return scrollNum;
	}

	public void setScrollNum(String scrollNum) {
		this.scrollNum = scrollNum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getChqbook() {
		return chqbook;
	}

	public void setChqbook(String chqbook) {
		this.chqbook = chqbook;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomname() {
		return nomname;
	}

	public void setNomname(String nomname) {
		this.nomname = nomname;
	}

	public String getNomdob() {
		return nomdob;
	}

	public void setNomdob(String nomdob) {
		this.nomdob = nomdob;
	}

	public String getNomage() {
		return nomage;
	}

	public void setNomage(String nomage) {
		this.nomage = nomage;
	}

	public String getNomsex() {
		return nomsex;
	}

	public void setNomsex(String nomsex) {
		this.nomsex = nomsex;
	}

	public String getNomaddress() {
		return nomaddress;
	}

	public void setNomaddress(String nomaddress) {
		this.nomaddress = nomaddress;
	}

	public String getNomrelation() {
		return nomrelation;
	}

	public void setNomrelation(String nomrelation) {
		this.nomrelation = nomrelation;
	}

	public String getNompercentage() {
		return nompercentage;
	}

	public void setNompercentage(String nompercentage) {
		this.nompercentage = nompercentage;
	}

	public String getRscroll() {
		return rscroll;
	}

	public void setRscroll(String rscroll) {
		this.rscroll = rscroll;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRamount() {
		return ramount;
	}

	public void setRamount(String ramount) {
		this.ramount = ramount;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getJointh() {
		return jointh;
	}

	public void setJointh(String jointh) {
		this.jointh = jointh;
	}

	public String getJointcid() {
		return jointcid;
	}

	public void setJointcid(String jointcid) {
		this.jointcid = jointcid;
	}

	public String getBeforesub() {
		return beforesub;
	}

	public void setBeforesub(String beforesub) {
		this.beforesub = beforesub;
	}

	public String getNomcidrelation() {
		return nomcidrelation;
	}

	public void setNomcidrelation(String nomcidrelation) {
		this.nomcidrelation = nomcidrelation;
	}

	public String getDohere() {
		return dohere;
	}

	public void setDohere(String dohere) {
		this.dohere = dohere;
	}


}
