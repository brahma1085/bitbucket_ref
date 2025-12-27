package SRC.COM.SUNRISE.CLIENT;



import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


import SRC.COM.SUNRISE.CLIENT.MainScreen;

import SRC.COM.SUNRISE.SERVER.StoreLoanTran;
import SRC.COM.SUNRISE.UTILITY.NumListener;
import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;

public class LoanTran  extends JPanel implements KeyListener,FocusListener,Observer,ActionListener{
  static LoanTran loans;
  GridBagConstraints gbc;
  JComboBox combo_ac_type;
  JTextField txt_ac_no;
  JLabel lbl_sancamt,lbl_noinst,lbl_period,lbl_intrate;
  JButton button_submit,button_clear;
  Personal panel_person;
  JScrollPane[] jsp_detail = new JScrollPane[4];
  JPanel panel_personal,panel_tabbed;
  String modulecode=null;
  StoreLoanTran store;
  LoanMasterObject lmobj =new LoanMasterObject();
  
  
  
  private LoanTran() {
	  setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(4,4,4,4);
	
		
		store = new StoreLoanTran();
		
		for (int i= 0; i< 37; i++)
			addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel(" "), this, 30, 32, 1.0, 1.0 , 1, 1 );
		
		addComponent(new JLabel("Loans Data Entry"), this, 0, 15, 0.0, 0.0, 3, 1);
		
		addComponent(new JLabel("Loan Acc type"), this, 1, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_ac_type = new JComboBox() , this, 1, 4, 0.0, 0.0, 4, 1);
		combo_ac_type.addItem("Surety Loan");
		combo_ac_type.addItem("Vehicle Loan");
		combo_ac_type.addItem("Staff Vehicle Loan");
		combo_ac_type.addItem("Personal Loan");
		combo_ac_type.addItem("Housing Loan");
		combo_ac_type.addItem("NSC/KVP Loan");
		combo_ac_type.addItem("BLCC Loan");
		combo_ac_type.addItem("Overdraft Loan");
		combo_ac_type.addItem("Cash Credit Loan");
		combo_ac_type.addItem("TMCB Cash Credit Loan");
		combo_ac_type.addItem("Decretal Overdraft Loan");
		combo_ac_type.addItem("Decretal Cash Credit Loan");
		
		
		addComponent(new JLabel("Loan Acc No"), this, 2, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_ac_no = new JTextField(5) , this, 2, 4, 0.0, 0.0, 4, 1);
		txt_ac_no.addFocusListener(this);
		NumListener numlist = new NumListener();
		txt_ac_no.addKeyListener( numlist );
		txt_ac_no.addFocusListener( this );
		
		addComponent(new JLabel("Sanctioned Amount"), this, 3, 1, 0.0, 0.0, 3, 1);
		addComponent( lbl_sancamt = new JLabel() , this, 3, 4, 0.0, 0.0, 4, 1);
		
		addComponent(new JLabel("No. of Installments"), this, 4, 1, 0.0, 0.0, 3, 1);
		addComponent( lbl_noinst = new JLabel() , this, 4, 4, 0.0, 0.0, 4, 1);
		
		
		
		addComponent(new JLabel("Monthly Installment"), this, 5, 1, 0.0, 0.0, 3, 1);
		addComponent( lbl_period = new JLabel() , this, 5, 4, 0.0, 0.0, 4, 1);
		
		addComponent(new JLabel("Interest Rate"), this, 6, 1, 0.0, 0.0, 3, 1);
		addComponent( lbl_intrate = new JLabel() , this, 6, 4, 0.0, 0.0, 4, 1);
		
		button_submit = new JButton("Submit");
		addComponent( button_submit, this, 7, 1, 0.0, 0.0, 3, 1 );
		button_submit.addActionListener(this);
		
		
		button_clear = new JButton("Clear");
		addComponent( button_clear, this, 7, 4, 0.0, 0.0, 3, 1 );
		button_clear.addActionListener(this);
		
		panel_person = new Personal();
		
		panel_tabbed = new JPanel();
		panel_tabbed.setBackground(new Color(232,255,238));
		panel_tabbed.setBorder(BorderFactory.createEtchedBorder());
		panel_tabbed.setLayout(new GridBagLayout());
		
		for (int i= 0; i< 28; i++)
			addComponent(new JLabel("      "), panel_tabbed, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel("  "), panel_tabbed, 24, 29, 1.0, 1.0 , 1, 1 );
		
		addComponent(panel_tabbed, this, 2, 10 ,0.0, 0.0, 28, 26);
		
		jsp_detail[0] = new JScrollPane(panel_person,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[0], panel_tabbed, 1, 0, 0.0, 0.0, 20, 22 );
		
  }
	
  
  
  
  
  
  
  void addComponent (JComponent jt,JPanel jp,int gy,int gx,double wx,double wy,int gw,int gh)
  {
  			gbc.gridx=gx;
  			gbc.gridy=gy;
  			gbc.weightx=wx;
  			gbc.weighty=wy;
  			gbc.gridheight=gh;
  			gbc.gridwidth=gw;
  			
  			
  			jp.add(jt,gbc);
  }
	
	
	
	
	public static LoanTran getInstance(int i) {
		
    	if ( i == 0)
    		return	loans = new LoanTran();
    	
    	if ( loans == null )
    	    return	loans = new LoanTran();
    	 
    	return loans;
	}




	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}




	public void focusLost(FocusEvent e) {
		if(e.getSource() == txt_ac_no) {
			
			
			if(txt_ac_no.getText().toString().trim().length() > 0 && new Integer(txt_ac_no.getText().toString().trim()).intValue() >0 ) {
				
				switch (combo_ac_type.getSelectedIndex()) {
				case 0:
					   modulecode = "1010014";//Surety Loan
				       break;
				case 1:
					   modulecode = "1010006";//Vehicle Loan
				       break;
				case 2:
						modulecode = "1010017";//Staff Vehicle Loan
				        break;
				case 3:
						modulecode= "1010015";//Personal Loan
				        break;
				case 4:
					    modulecode = "1010003";//Housing Loan
				        break;
				case 5:
					   modulecode = "1010018";//NSC/KVP Loan
				       break;
				case 6: 
					   modulecode = "1010019"; //BLCC Loan 
				       break;
				case 7: 
					   modulecode = "1015001"; //OverDraft Loan 
				       break;
				case 8: 
					   modulecode = "1014001"; //Cash Credit Loan 
				       break;
				case 9:
					   modulecode = "1010020"; //TMCB C/C Loan 
				       break;
				case 10:
					   modulecode = "1010021"; //Decretal Overdraft Loan 
				       break;
				case 11:
					   modulecode = "1010022"; //Decretal Cash Credit Loan 
				       break;
				}
				
				try {
				System.out.println("Modulecode===>"+ modulecode + "Accno=====>"+Integer.parseInt(txt_ac_no.getText()));
				int id=0;
				id=store.getCid(modulecode,Integer.parseInt(txt_ac_no.getText()));
				panel_person.setPanelData(id);
				lmobj = store.getLoanMaster(modulecode,Integer.parseInt(txt_ac_no.getText()));
				if(lmobj!=null) {
					lbl_sancamt.setText(String.valueOf(lmobj.getSanctionedAmount()));
					lbl_noinst.setText(String.valueOf(lmobj.getNoOfInstallments()));
					lbl_period.setText(String.valueOf(lmobj.getInstallmentAmt()));
					lbl_intrate.setText(String.valueOf(lmobj.getInterestRate()));
				}else
					JOptionPane.showMessageDialog(null,"Account Number not Found");
				}catch(Exception ee) {ee.printStackTrace();}
				
			}
		}
		
		
		
		
	}




	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}




	public void actionPerformed(ActionEvent e) {

		  if(e.getSource() == button_submit){
			  switch (combo_ac_type.getSelectedIndex()) {
				case 0:
					   modulecode = "1010014";//Surety Loan
				       break;
				case 1:
					   modulecode = "1010006";//Vehicle Loan
				       break;
				case 2:
						modulecode = "1010017";//Staff Vehicle Loan
				        break;
				case 3:
						modulecode= "1010015";//Personal Loan
				        break;
				case 4:
					    modulecode = "1010003";//Housing Loan
				        break;
				case 5:
					   modulecode = "1010018";//NSC/KVP Loan
				       break;
				case 6: 
					   modulecode = "1010019"; //BLCC Loan 
				       break;
				case 7: 
					   modulecode = "1015001"; //OverDraft Loan 
				       break;
				case 8: 
					   modulecode = "1014001"; //Cash Credit Loan 
				       break;
				case 9:
					   modulecode = "1010020"; //TMCB C/C Loan 
				       break;
				case 10:
					   modulecode = "1010021"; //Decretal Overdraft Loan 
				       break;
				case 11:
					   modulecode = "1010022"; //Decretal Cash Credit Loan 
				       break;
				}
			  int ac_no=0,no_instal=0;
			  double amtsanc=0.0,monthly_instal=0.0,intrate=0.0;
			  Object[][] data=null;
			  ac_no = Integer.parseInt(txt_ac_no.getText());
			  amtsanc = Double.parseDouble(lbl_sancamt.getText());
			  no_instal = Integer.parseInt(lbl_noinst.getText());
			  monthly_instal = Double.parseDouble(lbl_period.getText());
			  intrate = Double.parseDouble(lbl_intrate.getText());
			  
			  data = calculateInterest(amtsanc,intrate,no_instal);
			  
			  int result = store.disburseLoan(modulecode,Integer.parseInt(txt_ac_no.getText()),amtsanc,data,MainScreen.head_panel.getSysDate());
			  if(result>=1)
				  JOptionPane.showMessageDialog(null,"Successfully Inserted");
			  else
				  JOptionPane.showMessageDialog(null,"Not Inserted,Sorry");
			  
		  }
		
	}
	
	Object [][] calculateInterest(double prn,double rate,int period)
	{
		
			
	    Object data[][]=null;
	   
	   
	    try{
	    	 double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0,sump=0.0;
	    	 data=new Object[period+1][6];
	   /* GregorianCalendar g=new GregorianCalendar();	
	    String str1=MainScreen.head.getSysDate();*/
	    String str1=Validation.addNoOfMonths(MainScreen.head_panel.getSysDate(),0);
		StringTokenizer st = new StringTokenizer(str1,"/");
		int int_day = Integer.parseInt(st.nextToken());
		int int_month = Integer.parseInt(st.nextToken())-1;
		int int_year = Integer.parseInt(st.nextToken());
		GregorianCalendar g = new GregorianCalendar(int_year,int_month,int_day);
		
		str1 = MainScreen.head_panel.getSysDate();
		System.out.println("/////////////////// ++++++++++++++++++++++++ date:"+str1);
		
	    rate=rate/1200;
	    
	    double pow=1;
	    for(int j=0;j<period;j++)
	        pow=pow*(1+rate);
	    
	    double install=Math.round(0.01*Math.round(100*(prn*pow*rate)/(pow-1)));
	    double bal=Math.round(prn-(period*install));
	    int i=0;
	    
	    for(i=0;i<period;i++)
	    {
	        g.add(Calendar.MONTH,1);
	        String str=Validation.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
	        System.out.println(" the date is:"+str);
	        double interest=Math.round((prn*cprate*(Validation.dayCompare(str1,str)))/36500.00);
	        
	        str1=str;
	        cin=(install-interest);
	        if(i==(period-1))
	        {
	            /*cin=(interest+prn);			
	            install=cin;*/ // Code changed by Murugesh on 10/2/2006
	        	
	        	install = (interest+prn);
	        	cin = prn;
	        }
	        data[i][0]=String.valueOf(i+1);
	        data[i][1]=str;
	       // data[i][2]=String.valueOf(install); // Code changed by Murugesh on 23/12/2005
	        data[i][2]=String.valueOf(cin); // Code added by Murugesh on 23/12/2005
	        data[i][3]=String.valueOf(interest);
	      //  data[i][4]=String.valueOf(cin); // Code changed by Murugesh on 23/12/2005
	        data[i][4]=String.valueOf(install); // Code added by Murugesh on 23/12/2005
	        prn=prn-(install-interest);
	        data[i][5]=String.valueOf(prn);	
	       // prn=prn-(install-interest);
	        
	        
	        sump += cin;
	        sumi+=interest;
	        sumt=sumt+(install-interest); 
	        
	    }
	    data[i][0]="";
	    data[i][1]="";
	    data[i][2]=String.valueOf(sump);
	    data[i][3]=String.valueOf(sumi);
	   // data[i][4]=String.valueOf(sumt);// Code changed by Murugesh on 26/12/2005
	    data[i][4]=String.valueOf(sumt+sumi);  // Code added by Murugesh on 26/12/2005
	    data[i][5]="";	
	    }
	    catch(Exception e){e.printStackTrace();}
	    
	    return data;
	}
	
	
}
