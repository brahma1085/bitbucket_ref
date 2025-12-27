 package SRC.COM.SUNRISE.CLIENT;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


import SRC.COM.SUNRISE.CLIENT.*;
import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.CLIENT.ShareCertificateNo.certificateObserver;
import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.*;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.*;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.*;
//import





/*import src.com.sunrise.utility.valueobject.ModuleObject;
import src.com.sunrise.utility.valueobject.NomineeObject;
import src.com.sunrise.utility.valueobject.ShareMasterObject;
*/



public class NewShare extends JPanel implements ActionListener,KeyListener,ItemListener,FocusListener,Observer {
	//Components 
	GridBagConstraints gbc;
	JTextField txt_share_number,txt_no_of_shares,txt_share_amt,txt_customerid,txt_appln_date,txt_amt,txt_certificateno,txt_div_date,txt_div_pay_mode,txt_div_ac_no,txt_close_date,txt_nom_no,txt_balance;
	public JComboBox combo_share_allotment_type,combo_share_category,combo_branch_code,combo_details;
    public JComboBox combo_share_type,combo_div_ac_type,combo_paymode;
	JLabel lbl_branch_name,lbl_per_share_value,lbl_head;
	JButton button_submit,button_update,button_clear;
	NumberFormat obj_numberformat = NumberFormat.getNumberInstance();
	JComponent component;
	NumberFormat nf =null;
	
	//panels
	JScrollPane jsp[]=new JScrollPane[4],jsp_payment=null;
	JPanel panel_jsp=null;
	JPanel panel_jp=null;
	JPanel personal_panel=null;
	JTabbedPane tabbedpane=null;
	//other classes
	ShareMasterObject sharemasterobject=null;
	//Personal personal=null;
	CIDPanel cidpanel=null;
	Nominee nominee=null;
	Personal personal;
	ShareCertificateNo sharecertificate;
	
	Storeshare storeshare=null;
	ApplicationDetails appdet=null;
	ModuleObject mobj[],mobj1[]=null;
	
	public NewShare() {
	
		System.out.println("inside the share form");
		setLayout(new GridBagLayout());	
		nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(2);
		gbc = new GridBagConstraints();
		//gbc.fill = GridBagConstraints.BOTH;  // components grow in both dimensions
		gbc.insets = new Insets(4,4,4,4);
		
		//create a panel
		
	    panel_jp=new JPanel();
		panel_jp.setLayout(new GridBagLayout());
		//addComponent(panel_jp,this,0,0,1.0,1.0,1,1);
		//panel_jp.setVisible(true);
		//adding the components to panel_jp
		
		for ( int i=0; i< 30; i++) {
			
			addComponent(new JLabel("      "),this,0,i,0.0,0.0,1,1);
		}
		
		addComponent(new JLabel(" "),this,24,31,1.0,1.0,1,1);
		
		addComponent(lbl_head=new JLabel("Share Allotment"),this,1,15,0.0,0.0,20,1);
		lbl_head.setFont(new Font("Times new roman",Font.BOLD,14));
		
		addComponent(new JLabel("Account Type"),this,2,0,0.0,0.0,3,1);
		addComponent(combo_share_type=new JComboBox(), this,2,4,0.0,0.0,4,1);
		combo_share_type.addFocusListener(this);
		
		addComponent(new JLabel("Account No"),this,3,0,0.0,0.0,3,1);
		addComponent(txt_share_number=new JTextField(10),this,3,4,0.0,0.0,4,1);
		txt_share_number.addKeyListener(new NumListener());
		txt_share_number.addFocusListener(this);
		
		addComponent(new JLabel("Customer ID"), this,4,0,0.0,0.0,3,1);
		addComponent(txt_customerid=new JTextField(),this,4,4,0.0,0.0,4,1);
		txt_customerid.addKeyListener(new NumListener());
		txt_customerid.addFocusListener(this);
		
		addComponent(new JLabel("Appln Date"),this,5,0,0.0,0.0,3,1);
		addComponent(txt_appln_date=new JTextField(MainScreen.getSysDate()),this,5,4,0.0,0.0,4,1);
		txt_appln_date.addKeyListener(new DateListener());
		txt_appln_date.setText(MainScreen.getSysDate());
		
		addComponent(new JLabel("Branch"),this,6,0,0.0,0.0,3,1);
		addComponent(combo_branch_code=new JComboBox(),this,6,4,0.0,0.0,4,1);
		combo_branch_code.addItem("1");
		
		
		
		addComponent(new JLabel("Share Type"),this,7,0,0.0,0.0,3,1);
		addComponent(combo_share_allotment_type=new JComboBox(),this,7,4,0.0,0.0,4,1);
		combo_share_allotment_type.addItem("Direct");
		combo_share_allotment_type.addItem("Suspence");
		
		
		
		
		addComponent(new JLabel("Share Category"),this,8,0,0.0,0.0,3,1);
		addComponent(combo_share_category=new JComboBox(),this,8,4,0.0,0.0,4,1);
		combo_share_category.addItem("Regular");
		combo_share_category.addItem("Associate");
		combo_share_category.addItem("Nominal");
				
		
		/*addComponent(new JLabel("Share Value"),this,9,0,0.0,0.0,3,1);
		addComponent(new JLabel("100.00"),this,9,4,0.0,0.0,4,1);*/
		
		txt_no_of_shares=new JTextField("0");
		txt_amt= new JTextField("0");
		
		/*addComponent(new JLabel("No of Shares"),this,10,0,0.0,0.0,3,1);
		addComponent(,this,10,4,0.0,0.0,2,1);
		txt_no_of_shares.addFocusListener(this);
		txt_no_of_shares.addKeyListener(new NumListener());
		double totamt=Double.parseDouble(txt_no_of_shares.getText());
		
		System.out.println("the amt======================================= "+totamt);
		addComponent(,this,10,6,0.0,0.0,3,1);
		txt_amt.setText(String.valueOf(totamt));
		txt_amt.addFocusListener(this);
		txt_amt.setEditable(false);*/
		
		
		
		/*addComponent(new JLabel("Intr Ac "),this,11,0,0.0,0.0,3,1);
		addComponent(combo_introducer_acctype=new JComboBox(),this,11,4,0.0,0.0,4,1);
		//combo_introducer_acctype.addItem("SH");
		
		addComponent(new JLabel("Intr No"),this,12,0,0.0,0.0,3,1);
		addComponent(txt_introducer_no=new JTextField(),this,12,4,0.0,0.0,4,1);*/
		
		addComponent(new JLabel("Details"),this,13,0,0.0,0.0,3,1);
		addComponent(combo_details=new JComboBox(),this,13,4,0.0,0.0,4,1);
		combo_details.addItem("Personal Details");
		combo_details.addItem("Nominee Details");
		combo_details.addItem("Certificate Detail");
		combo_details.addItemListener(this);
		
		txt_certificateno=new JTextField("0");
		
		/*addComponent(new JLabel("No Certificate"),this,14,0,0.0,0.0,3,1);
		addComponent(,this,14,4,0.0,0.0,4,1);*/
		
		addComponent(new JLabel("Div upto date"),this,15,0,0.0,0.0,3,1);
		addComponent(txt_div_date=new JTextField("31/03/2000"),this,15,4,0.0,0.0,4,1);
		txt_div_date.addKeyListener(new DateListener());
		
		
		/*addComponent(new JLabel("Share Status"),this,16,0,0.0,0.0,3,1);
		addComponent(combo_share_status=new JComboBox(),this,16,4,0.0,0.0,4,1);
		combo_share_status.addItem("Active");
		combo_share_status.addItem("Closed");
*/
		
		
		addComponent(new JLabel("Div Paymode"),this,17,0,0.0,0.0,3,1);
		addComponent(combo_paymode=new JComboBox(),this,17,4,0.0,0.0,4,1);
		combo_paymode.addItem("Cash");
		combo_paymode.addItem("Transfer");
		combo_paymode.addItem("PayOrder");
		combo_paymode.addItemListener(this);
		
		
		addComponent(new JLabel("Div pay Ac type"),this,18,0,0.0,0.0,3,1);
		addComponent(combo_div_ac_type=new JComboBox(),this,18,4,0.0,0.0,4,1);
		combo_div_ac_type.addItem("SB");
		combo_div_ac_type.addItem("CA");
		combo_div_ac_type.setEnabled(false);
		
		addComponent(new JLabel("Div pay ac no"),this,19,0,0.0,0.0,3,1);
		addComponent(txt_div_ac_no=new JTextField("0"),this,19,4,0.0,0.0,4,1);
		txt_div_ac_no.setEnabled(false);
		
		addComponent(new JLabel("Share Balance"),this,20,0,0.0,0.0,3,1);
		addComponent(txt_balance = new JTextField("0"),this,20,4,0.0,0.0,4,1);
		txt_balance.addFocusListener(this);
		
		/*addComponent(new JLabel("Mem Close date"),this,20,0,0.0,0.0,3,1);
		addComponent(txt_close_date=new JTextField(),this,20,4,0.0,0.0,4,1);
		txt_close_date.addKeyListener(new DateListener());
		*/
		//addComponent(new JLabel("Nominee No"),this,21,0,0.0,0.0,3,1);
		txt_nom_no=new JTextField("0");
		//addComponent(,this,21,4,0.0,0.0,4,1);
		
		
		
		addComponent(button_submit=new JButton("S U B M I T"),this,23,9,0.0,0.0,3,1);
		addComponent(button_update=new JButton("U P D A T E"),this,23,12,0.0,0.0,3,1);
		addComponent(button_clear=new JButton("C A N C E L"),this,23,15,0.0,0.0,3,1);
		button_submit.addActionListener(this);
		button_update.addActionListener(this);
		button_clear.addActionListener(this);
		
		// panel for tabbed pane

		
		panel_jsp=new JPanel(); 
		addComponent(panel_jsp ,this,2,10,0.0,0.0,19,21);
		panel_jsp.setLayout(new GridBagLayout());
		panel_jsp.setBorder(BorderFactory.createEtchedBorder());
		for ( int i=0; i< 20; i++) {
			
			addComponent(new JLabel("      "),panel_jsp,0,i,0.0,0.0,1,1);
		}	
		addComponent(new JLabel("  "),panel_jsp,22,21,1.0,1.0,1,1);
		
		
	    personal = new Personal();
	    cidpanel = new CIDPanel();
	    cidpanel.subObserver.addObserver(this);
	    nominee = new Nominee(null);
	    
	    String[] str = new String[2];
	    str[0] = "Certificate No";
	    str[1] = "No of Share";
	    sharecertificate = new ShareCertificateNo(str,0);
	    sharecertificate.observers.addObserver(this);
	    
		panel_jsp.setBackground(new Color(253,254,233));
		
		jsp = new JScrollPane[4];
		
		jsp[0]=new JScrollPane( personal,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[1]=new JScrollPane( nominee,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[2]=new JScrollPane( sharecertificate ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[3]=new JScrollPane( cidpanel ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		addComponent(jsp[0],panel_jsp,1,0,0.0,0.0,19,19);
		//jsp[0].setVisible(false);
		
		addComponent(jsp[1],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[1].setVisible(false);
		    
		addComponent(jsp[2],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[2].setVisible(false);
		
		addComponent(jsp[3],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[3].setVisible(false);
		
		/*
		
		for(int i=0;i<3;i++)
		{
					jsp[i].setPreferredSize(new Dimension(400,200));//400,200
					jsp[i].setBorder(BorderFactory.createRaisedBevelBorder());
					//jsp[i].setBackground(new Color(232,255,238));
					cidpanel.subObserver.addObserver(this);
					jsp[i].setVisible(false);
					if(i==2)
					{	jsp[i].setBorder(BorderFactory.createTitledBorder("Application Details"));
					    addComponent(jsp[i],panel_jsp,0,0,0.0,0.0,3,15);//4,15
					    addComponent(new JLabel("        "),panel_jsp,16,0,1.0,1.0,5,5);
					    
					}
					else
					{jsp[i].setBorder(BorderFactory.createRaisedBevelBorder());
					//jsp[i].setBackground(new Color(232,255,238));
					
					addComponent(jsp[i],panel_jsp,0,0,0.0,0.0,3,25);//4,25
					}
					addComponent(new JLabel("          "),this,9,0,1.0,1.0,1,1);
					jsp[i].setVisible(false);
		}
		
		*/

		try{
			
			storeshare=new Storeshare();
			mobj1=storeshare.getModulecode(2,"1002000,1007000,1014000,1015000,1018000");// for SB,CA module abbr
			mobj=storeshare.getModulecode(2,"1001000");			//for Share module abbr
			for(int i=0; i< mobj.length;i++)
			{
				combo_share_type.addItem(mobj[i].getModuleAbbrv());	
				//combo_introducer_acctype.addItem(mobj[i].getModuleAbbrv());

			}

			for(int j=0;j<mobj1.length;j++){
				combo_div_ac_type.addItem(mobj1[j].getModuleAbbrv());
			}


		}catch(Exception e){
		e.printStackTrace();
	} 
		
	}
	

	public void addComponent (JComponent jt,JPanel jp,int gy,int gx,double wx,double wy,int gw,int gh)
	{
			gbc.gridx=gx;
			gbc.gridy=gy;
			gbc.weightx=wx;
			gbc.weighty=wy;
			gbc.gridheight=gh;
			gbc.gridwidth=gw;
			jt.setFont(new Font("Times New Roman",Font.BOLD,13));
			jp.add(jt,gbc);
	        
	        
			
			if(jt instanceof JButton )
				{
				gbc.fill = GridBagConstraints.BOTH;}
			else if(jt instanceof JComboBox)
				{
				gbc.fill = GridBagConstraints.HORIZONTAL;}
			else if(jt instanceof JCheckBox )
				{
				gbc.fill = GridBagConstraints.BOTH;}
			else if(jt instanceof JLabel && ((JLabel) jt).getText().toString().trim().length()>0 )
				{
				gbc.fill = GridBagConstraints.HORIZONTAL;}
			else
				gbc.fill=GridBagConstraints.BOTH;
			if(!(jt instanceof JLabel))
				jt.addKeyListener(this);
			
	}
	
  public void modulecode(){
	  
	    mobj=storeshare.getModulecode(2,"1002000,1007000,1014000,1015000,1018000");
		mobj=storeshare.getModulecode(2,"1001000");			
		for(int i=0;i<mobj.length;i++)
		{
			combo_share_type.addItem(mobj[i].getModuleAbbrv());	
			//combo_introducer_acctype.addItem(mobj[i].getModuleAbbrv());	
		}
  }
	
	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == button_submit ){

			
			NomineeObject nomin = nominee.getNomineeDetail();
		
			 {
				int a = 0;
				
				if ( getShareMasterObj() != null ) {
					
					a = storeshare.storeSharedet( getShareMasterObj() ,nomin );


					if( a==1 ){
						JOptionPane.showMessageDialog(this, "Share Details Inserted Successfully");
						clearForm();
					}
					else{
						JOptionPane.showMessageDialog(this, "Share Details Not Inserted");
					}
				}
				
			}
		}
		
		if(e.getSource()==button_clear){
			clearForm();
		}
		
		if( e.getSource() == button_update) {
				
			NomineeObject nomin = nominee.getNomineeDetail();
			
			 {
				int a = 0;
			
				if ( getShareMasterObj()!= null) {
					
					a = storeshare.deleteshare( getShareMasterObj() ,nomin );
			
				if( a == 1 ){
				
					JOptionPane.showMessageDialog(this, "Share Details Updated Successfully");
					clearForm();
				}
				else{
					
					JOptionPane.showMessageDialog(this, "Share Details Not Updated");
				}
			}
					
			} 
		} 
	}
		
	private ShareMasterObject getShareMasterObj() {
		
		sharemasterobject=new ShareMasterObject();
		
		Hashtable table = sharecertificate.getCertificateNo();
		
		sharemasterobject.setCertificate(table);
		
		sharemasterobject.setShareAccType(mobj[combo_share_type.getSelectedIndex()].getModuleCode());
		
		sharemasterobject.setShareNumber(Integer.parseInt(txt_share_number.getText()));
		
		sharemasterobject.setCustomerId(Integer.parseInt(txt_customerid.getText()));
		
		sharemasterobject.setName(personal.getName());
		
		System.out.println(personal.getName()+"************************");
		
		
		if ( txt_appln_date.getText().toString().length() > 0 )
			sharemasterobject.setIssueDate(txt_appln_date.getText());
		else {
			
			JOptionPane.showMessageDialog(null, "please application date  " );
			return null;
		}

		sharemasterobject.setBranchCode( new Integer(combo_branch_code.getSelectedItem().toString()).intValue());

		sharemasterobject.setShareType(combo_share_allotment_type.getSelectedIndex());

		sharemasterobject.setMemberCategory(combo_share_category.getSelectedIndex()+1);
		
		Collection  en = table.values();
		int sum = 0;
		Iterator it = en.iterator();
		
		while (it.hasNext()) {
			sum += new Integer(it.next().toString()).intValue();
			
		}
		
		System.out.println( sum + " no of share -------------------");
		sharemasterobject.setNumberCert(0);
		sharemasterobject.setShareBalance((sum*100));
		
		if ( !txt_balance.getText().toString().equalsIgnoreCase("") && txt_balance.getText().toString().trim().length() > 0 )
			sharemasterobject.setBalance(new Double(txt_balance.getText().toString()).doubleValue());
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the Balance  " );
		}
		
		if ( !txt_div_date.getText().toString().equalsIgnoreCase("") && txt_div_date.getText().toString().trim().length() > 0 )
			
			sharemasterobject.setDivUptoDate(txt_div_date.getText());
		
	
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the Divident date  " );
			return null;
		}
		
		sharemasterobject.setPayMode(combo_paymode.getSelectedItem().toString());
		
		if(combo_div_ac_type.getSelectedIndex()==1)
		{

			if ( !txt_div_ac_no.getText().toString().equalsIgnoreCase("") && txt_div_ac_no.getText().toString().trim().length() > 0 )
			{
				sharemasterobject.setPaymentAcctype(mobj1[combo_div_ac_type.getSelectedIndex()].getModuleCode());
				sharemasterobject.setPaymentAccno(Integer.parseInt(txt_div_ac_no.getText()));
				System.out.println("the ac_no is"+Integer.parseInt(txt_div_ac_no.getText()));
			}
			else {

				JOptionPane.showMessageDialog(null, "please enter the dividend account number  " );
				return null;
			}
		}
		else {
			sharemasterobject.setPaymentAcctype(null);
		}
		
		sharemasterobject.setTrn_date(MainScreen.getSysDate());
		sharemasterobject.setDe_datetime(MainScreen.getSysDate()+MainScreen.getSysTime());
		System.out.println("The time "+MainScreen.getSysDate()+" "+MainScreen.getSysTime());
		sharemasterobject.setDe_tml(MainScreen.head_panel.getTml());
		System.out.println("tMl is"+MainScreen.head_panel.getTml());
		sharemasterobject.setDe_user(MainScreen.head_panel.getUid());
		System.out.println("UID is"+MainScreen.head_panel.getUid());
		
		
		
		return sharemasterobject;
		
	}
	

	public void clearForm()	
	{	

		txt_share_number.setText("0");
		txt_share_number.setEnabled(true);
		txt_no_of_shares.setText("");
		txt_amt.setText("");	
		//txt_introducer_no.setText("");	
		txt_customerid.setText("");
	//	txt_introducer_no.setText("");	
		txt_certificateno.setText("");
		//txt_close_date.setText("");
		txt_div_ac_no.setText("");
		txt_div_ac_no.setEnabled(false);
		txt_div_date.setText("31/03/2000");
		txt_nom_no.setText("");
		//txt_appln_date.setText("");
		txt_balance.setText("");
		
		combo_share_category.setSelectedIndex(0);
		combo_share_allotment_type.setSelectedIndex(0);
		combo_branch_code.setSelectedIndex(0);	
		combo_details.setSelectedIndex(0);
		combo_div_ac_type.setSelectedIndex(0);
		combo_paymode.setSelectedIndex(0);
	//	combo_share_status.setSelectedIndex(0);
		combo_share_type.setSelectedIndex(0);
		
		jsp[0].setVisible(true);
		jsp[1].setVisible(false);
		jsp[2].setVisible(false);
		jsp[3].setVisible(false);
		
	//	combo_introducer_acctype.setSelectedIndex(0);	
		//appdet.clearForm();
		
		System.out.println("before sharaparam");
	    
		
		System.out.println("after sharaparam");
		System.out.println("inside  clear form");
		
			button_submit.setEnabled(true);
			button_submit.setVisible(true);
			button_submit.setText("S U B M I T");
			button_submit.setActionCommand("S U B M I T");
			
			nominee.clearNominee();
			personal.clear();
			sharecertificate.clear();
		}
	

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	public void itemStateChanged(ItemEvent e) {
		
		if(e.getSource()==combo_details){
			
			System.out.println(combo_details.getSelectedIndex()+"selected  combo detail");
			showJSP(combo_details.getSelectedIndex());
		}
		
		else if(e.getSource()==combo_paymode){
			
			if(combo_paymode.getSelectedItem().equals("Transfer")){
				
				txt_div_ac_no.setEnabled(true);
				combo_div_ac_type.setEnabled(true);
				
			}
			else {
				
				txt_div_ac_no.setEnabled(false);
				combo_div_ac_type.setEnabled(false);
				
			}
			
			
		}
				
		
	}
	
	
	public void showJSP(int i) {
		
		
		for ( int j =0; j< jsp.length; j++)
			jsp[j].setVisible(false);
		
		if(i == 3) {
			if( txt_customerid.getText().toString().length() > 0 && new Integer(txt_customerid.getText().toString()).intValue() > 0)
			cidpanel.setPanelData(new Integer(txt_customerid.getText().toString()).intValue());
		}
			
		
		jsp[i].setVisible(true);
		panel_jsp.revalidate();
	}


	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
		if ( arg0.getSource() == txt_balance ) {
			
			Collection  en = (sharecertificate.getCertificateNo()). values();
			if ( en != null)
			{
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			txt_balance.setText(new Double(sum * 100.0).toString());
		} else {
			
			JOptionPane.showMessageDialog(null, " Please fill the Certificate No");
		}
		}
		
	}


	public void focusLost(FocusEvent e) {
		
		if(e.getSource()==txt_no_of_shares){
			
			if(txt_no_of_shares.getText().equals("0") || txt_no_of_shares.getText().equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(this, "Enter the number of shares");
				
			}
			
			double totamt=(Double.parseDouble(txt_no_of_shares.getText())*100.00);
			txt_amt.setText(String.valueOf(totamt));
		}
		
		else if ( e.getSource() == txt_customerid && e.getOppositeComponent() == txt_appln_date) {
			
			
			if ( txt_customerid.getText().toString().trim().length() > 0 &&  new Integer(txt_customerid.getText().toString().trim()).intValue() >0 ) {
				
				personal.setPanelData(new Integer(txt_customerid.getText().toString().trim()));
		
			} else {
				
				showJSP(3);
				cidpanel.txt_cid.requestFocus();
			}
		}
		
		else if ( e.getSource() == combo_share_type ) {
			
			txt_share_number.requestFocus();
		}
		
		else if ( e.getSource() == txt_share_number && e.getOppositeComponent() == txt_customerid ) 
		{
			
			if( txt_share_number.getText().equals("0") || txt_share_number.getText().equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(this, "Enter the share number  ");
				
			} else if (txt_share_number.getText().toString().trim().length() > 0 && new Integer( txt_share_number.getText().toString()).intValue() > 0 ) {
				
				
				int sharenum=Integer.parseInt(txt_share_number.getText().toString().trim());
				
				ShareMasterObject shobj=new ShareMasterObject();
				
				shobj = storeshare.updateshare(sharenum);
				
				//clearForm();
				if ( shobj != null)
				{
					txt_share_number.setText(String.valueOf(shobj.getShareNumber()));
					txt_appln_date.setText(shobj.getIssueDate());
					txt_customerid.setText(String.valueOf(shobj.getCustomerId()));
					//txt_amt.setText(String.valueOf(shobj.getBal()));
					txt_amt.setText(String.valueOf(shobj.getShareBalance()));
					double num_share=Double.parseDouble(txt_amt.getText())/100.00;
					txt_no_of_shares.setText(String.valueOf(num_share));
					
					txt_div_date.setText(shobj.getDivUptoDate());
				//	txt_introducer_no.setText(String.valueOf(shobj.getIntroducerAccno()));
					txt_nom_no.setText(String.valueOf(shobj.getNomineeno()));
					txt_certificateno.setText(String.valueOf(shobj.getNumberCert()));

					//System.out.println(Integer.parseInt(shobj.getPaymentAcctype())+"^^^^^^^^^^^^^^");

					

					//combo_div_ac_type.setSelectedIndex(Integer.parseInt(shobj.getPaymentAcctype()));
					if( (shobj.getPayMode()).equalsIgnoreCase("C"))
						combo_paymode.setSelectedIndex(0);
					else  if( (shobj.getPayMode()).equalsIgnoreCase("T")) {
						combo_paymode.setSelectedIndex(1);
						
						for ( int i= 0; i< mobj1.length ; i++) {

							if ( Integer.parseInt(shobj.getPaymentAcctype()) == new Integer (mobj1[i].getModuleCode()).intValue() )
								combo_div_ac_type.setSelectedIndex(i);
								txt_div_ac_no.setText(new Integer(shobj.getPaymentAccno()).toString());
						}
					}
					else 
						combo_paymode.setSelectedIndex(2);


					combo_share_category.setSelectedIndex((shobj.getMemberCategory()-1));

					/*if ( (shobj.getShareStatus()).equalsIgnoreCase("A") )
						combo_share_status.setSelectedIndex(0);
					else 
						combo_share_status.setSelectedIndex(1);
*/

					personal.setPanelData(shobj.getCustomerId());

					if ( shobj.getNominee() != null) {


						if ( shobj.getNominee().getCid() == 0) {

							System.out.println("******************************");
							nominee.txt_cid.setText("0");
							nominee.txt_dob.setText(shobj.getNominee().getDob());
							nominee.txt_name.setText(shobj.getNominee().getName());
							nominee.txt_relation.setText(shobj.getNominee().getRelation());
							nominee.textarea_add.setText(shobj.getNominee().getAddress());
							nominee.lbl_regno.setText( new Integer(shobj.getNominee().getRegno()).toString());
							if (shobj.getNominee().getSex() == 0){

								nominee.combo_sex.setSelectedIndex(1);
							} else 
								nominee.combo_sex.setSelectedIndex(0);

						} else {

							nominee.setPanelData(shobj.getNominee().getCid());

						}

					}
					
					
					sharecertificate.setCertificateNo(shobj.getCertificate());
					txt_balance.setText( new Double(shobj.getBalance()).toString());
					button_submit.setEnabled(false);
				}
			}
			
		}
		
		else if ( e.getSource()== txt_balance && (e.getOppositeComponent() == button_submit || e.getOppositeComponent() == button_update)) {
			
			if ( !txt_balance.getText().toString().equalsIgnoreCase("") && txt_balance.getText().length() > 0) {
				
				
				Collection  en = ( sharecertificate.getCertificateNo()). values();
				if ( en != null)
				{
				int sum = 0;
				Iterator it = en.iterator();
				
				while (it.hasNext()) {
					sum += new Integer(it.next().toString()).intValue();
					
				}
				if ( (new Double(txt_balance.getText().toString())).doubleValue() < (sum *100.00) ) { 
					
					JOptionPane.showMessageDialog(null, "Balance amount is less than Share Ammount");

				} 
			} else {
				
				JOptionPane.showMessageDialog(null, " Please fill the Certificate No");
			}
			}
			
		}
		
	}


	public void update(Observable arg0, Object ar) {

		
		if ( arg0 instanceof SubObserver ) {
			
			System.out.println(" SubObserver +++++++++++++++");
			
			if ( ar  instanceof Integer ) {
				
				if ( ((Integer)ar).intValue() > 0 ) {
					
					txt_customerid.setText(new String(ar.toString()));
					showJSP(0);
					personal.setPanelData(((Integer)ar).intValue());
					txt_appln_date.requestFocus();
					
				}
				
			}
		}
	
		else if ( arg0 instanceof certificateObserver  ) {
			
			txt_balance.setText( ar.toString());
			
		}
		
	}


	

}
