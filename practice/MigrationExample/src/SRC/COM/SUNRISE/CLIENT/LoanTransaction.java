package SRC.COM.SUNRISE.CLIENT;

import java.awt.Color;
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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

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

import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.CLIENT.ShareCertificateNo.certificateObserver;
import SRC.COM.SUNRISE.SERVER.StoreDepTran;
import SRC.COM.SUNRISE.SERVER.StoreLoanTran;
import SRC.COM.SUNRISE.UTILITY.NumListener;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanTransactionObject;

public class LoanTransaction extends JPanel implements KeyListener,FocusListener,Observer,ActionListener,ItemListener {

	 static LoanTransaction loans;
	 GridBagConstraints gbc;
	  JComboBox combo_ac_type,combo_trn_mode,combo_mode;
	  JTextField txt_ac_no,txt_trnmode;	  
	  JButton button_submit,button_clear,button_update;
	  Personal panel_person;
	  JScrollPane[] jsp_detail = new JScrollPane[4];
	  JPanel panel_personal,panel_tabbed;
	  String modulecode=null;
	  StoreLoanTran store;
	  LoanTransactionObject lntrn = null,loantran=null;
	  LoanExamp examp;
	  JPanel panel;
	  JScrollPane jsp[]=new JScrollPane[1];
	  
	  
	  private LoanTransaction() {
		
		  setLayout(new GridBagLayout());
			gbc = new GridBagConstraints();
			
			gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(4,4,4,4);
		
			
			
			
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
			
			addComponent(new JLabel("Mode"), this, 3, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_mode = new JComboBox() , this, 3, 4, 0.0, 0.0, 2, 1);
			combo_mode.addItem("Cash");
			combo_mode.addItem("Transfer");
			combo_mode.addItemListener(this);
			
			addComponent(new JLabel("Transfer Account Type"), this, 4, 1, 0.0, 0.0, 3, 1);
			addComponent(combo_trn_mode = new JComboBox() , this, 4, 4, 0.0, 0.0, 2, 1);
			combo_trn_mode.addItem("SB");
			combo_trn_mode.addItem("CA");
			addComponent( txt_trnmode = new JTextField(5) , this, 4, 6, 0.0, 0.0, 2, 1);
			combo_trn_mode.setVisible(false);
			txt_trnmode.setVisible(false);
			
			
			
			button_submit = new JButton("Submit");
			addComponent( button_submit, this, 30, 1, 0.0, 0.0, 3, 1 );
			button_submit.addActionListener(this);
			
			
			button_clear = new JButton("Clear");
			addComponent( button_clear, this, 30, 4, 0.0, 0.0, 3, 1 );
			button_clear.addActionListener(this);
			
			button_update = new JButton("Update");
			addComponent( button_update, this, 30, 7, 0.0, 0.0, 3, 1 );
			button_update.addActionListener(this);
			
			/*panel_person = new Personal();
			
			panel_tabbed = new JPanel();
			panel_tabbed.setBackground(new Color(232,255,238));
			panel_tabbed.setBorder(BorderFactory.createEtchedBorder());
			panel_tabbed.setLayout(new GridBagLayout());
			
			for (int i= 0; i< 28; i++)
				addComponent(new JLabel("      "), panel_tabbed, 0, i, 0.0, 0.0 , 1, 1 );
			
			addComponent(new JLabel("  "), panel_tabbed, 24, 29, 1.0, 1.0 , 1, 1 );
			
			addComponent(panel_tabbed, this, 2, 10 ,0.0, 0.0, 28, 26);
			
			jsp_detail[0] = new JScrollPane(panel_person,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			addComponent( jsp_detail[0], panel_tabbed, 1, 0, 0.0, 0.0, 20, 22 );*/
			
			
			String[] str = new String[5];
		    str[0] = "Tran date";
		    str[1] = "Tran Seq";
		    str[2] = "Tran Type";
		    str[3] = "Amount";
		    str[4] = "Cash/Transfer Mode";
		   
		    
		    System.out.println("Before going to LoanExamps");
		    examp = new LoanExamp(str,0);
		    examp.observers.addObserver(this);
		    
		    panel=new JPanel(); 
			addComponent(panel ,this,8,1,0.0,0.0,21,21);
			panel.setLayout(new GridBagLayout());
			/*panel.setBorder(BorderFactory.createEtchedBorder());
			for ( int i=0; i< 20; i++) {
				
				addComponent(new JLabel("   hhhh   "),panel,0,i,0.0,0.0,1,1);
			}	*/
			addComponent(new JLabel(" "),panel,22,21,1.0,1.0,1,1);
			
		   jsp = new JScrollPane[1];

			jsp[0]=new JScrollPane(examp,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);   
		    addComponent(jsp[0],panel,1,0,0.0,0.0,21,19);
		//  addComponent(jsp[0],panel,1,0,0.0,0.0,19,25);
		    jsp[0].setVisible(true);
		 
		    
		 
		    
		    try {
					store=new StoreLoanTran();
		    	}catch(Exception e) {e.printStackTrace();}
		    	
			panel.setVisible(true);
		
	
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
		
	
	
	
public static LoanTransaction getInstance(int i) {
		
    	if ( i == 0)
    		return	loans = new LoanTransaction();
    	
    	if ( loans == null )
    	    return	loans = new LoanTransaction();
    	 
    	return loans;
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





public void focusGained(FocusEvent arg0) {
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
		
			LoanTransactionObject lntr = new LoanTransactionObject();
			System.out.println("Modulecode=====>"+modulecode+"Acno====>"+txt_ac_no.getText());
			lntr = store.getLoanTranInfo(modulecode,Integer.parseInt(txt_ac_no.getText()));
			if(lntr.getAccNo()!=0)
				txt_ac_no.setText(String.valueOf(lntr.getAccNo()));
			/*if(lntr.getTranNarration().equalsIgnoreCase("1002001"))
				combo_trn_mode.setSelectedIndex(0);
			else
				combo_trn_mode.setSelectedIndex(1);*/

			
			/*String[] str = new String[5];
		    str[0] = "Tran date";
		    str[1] = "Tran Seq";
		    str[2] = "Tran Type";
		    str[3] = "Amount";
		    str[4] = "Cash/Transfer Mode";
		   
		    
		    System.out.println("Before going to LoanExamps");
		    examp = new LoanExamp(str,0);
		    examp.observers.addObserver(this);
			*/
			txt_trnmode.setText(String.valueOf(lntr.getSbaccno()));
			if(lntr.getAccNo()!=0)
				examp.setDetails(lntr.getHash_loan());
			
			/*try {
			System.out.println("Modulecode===>"+ modulecode + "Accno=====>"+Integer.parseInt(txt_ac_no.getText()));
			int id=0;
			id=store.getCid(modulecode,Integer.parseInt(txt_ac_no.getText()));
			panel_person.setPanelData(id);
			}catch(Exception ee) {ee.printStackTrace();}*/
			
		}
		
		
	}
	
}

	public void actionPerformed(ActionEvent ap) {
		if(ap.getSource() == button_submit){
		
		int result = 0;	
		LoanTransactionObject lnobj = getLoanObject();
		if(lnobj != null){
			try{
				System.out.println(lnobj);
			result = store.storeLoanTransaction(lnobj);
			System.out.println(" *** result >> "+result);
			if(result >= 1){
				JOptionPane.showMessageDialog(this, "Details Inserted Successfully");
				clearScreen();
			}else
				JOptionPane.showMessageDialog(this, "Details Not Inserted");
			}catch (Exception e) {
                 e.printStackTrace();
			}
		}
		
	
		}
		
		if(ap.getSource()==button_clear){
			clearScreen();
		}
		
		
		if(ap.getSource() == button_update) {
			
			if(txt_ac_no.getText().length()==0) {
				JOptionPane.showMessageDialog(null,"Please Enter the account no.");
			}
			else {
					int result = 0;
					if ( getLoanObject()!= null) {
							
					try {
						LoanTransactionObject lntran=getLoanObject();
						
						result=store.updateLoanTransaction(lntran);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
			
				if( result == 1 ){
				
					JOptionPane.showMessageDialog(this, "details Updated Successfully");
					clearScreen();
				}
				else{
					
					JOptionPane.showMessageDialog(this, "Details Not Updated");
				}
			}
					
			}
		}
}


	private LoanTransactionObject getLoanObject(){
		loantran = new LoanTransactionObject();
		
		Hashtable table = examp.getDetails();
		
		Enumeration e = table.keys();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			LoanObject lob = (LoanObject)table.get(key);
			System.out.println(">> "+lob.getTrn_type());
			System.out.println(">> "+lob.getTrn_mode());
		}
		
		loantran.setHash_loan(table);
		
		switch (combo_ac_type.getSelectedIndex()) {
		
		case 0: loantran.setAccType("1010014");
			    break;
			    
		case 1: loantran.setAccType("1010006");	
		        break;
		        
		case 2: loantran.setAccType("1010017");
	    		break;
	    
		case 3: loantran.setAccType("1010015");	
				break;
        
		case 4: loantran.setAccType("1010003");	
        		break;
        
		case 5: loantran.setAccType("1010018");
				break;

		case 6: loantran.setAccType("1010019");	
				break;
				
		case 7: loantran.setAccType("1015001");
	    		break;
	    
		case 8: loantran.setAccType("1014001");	
        		break;
        
		case 9: loantran.setAccType("1010020");
				break;

		case 10: loantran.setAccType("1010021");	
				break;

		case 11: loantran.setAccType("1010022");	
				break;

		default:
			break;
		}
		
		switch (combo_trn_mode.getSelectedIndex()) {
		
		case 0: loantran.setTranNarration("1002001");
			    break;
			    
		case 1: loantran.setTranNarration("1007001");	
		        break;
		
		}
		
		loantran.setAccNo(Integer.parseInt(txt_ac_no.getText()));
		
		loantran.setSbaccno(Integer.parseInt(txt_trnmode.getText()));
		
		return loantran;
		
	}
	
	public void update(Observable arg0, Object ar) {

		
		if ( arg0 instanceof SubObserver ) {
			
			System.out.println(" SubObserver +++++++++++++++");
			
			if ( ar  instanceof Integer ) {
				
				/*if ( ((Integer)ar).intValue() > 0 ) {
					
					txt_customerid.setText(new String(ar.toString()));
					showJSP(0);
					personal.setPanelData(((Integer)ar).intValue());
					txt_appln_date.requestFocus();
					
				}*/
				
			}
		}

		else if ( arg0 instanceof certificateObserver  ) {
			
			//txt_rd_bal.setText( ar.toString());
			
		}
		
	}
	
	
	public void clearScreen() {
		
		
		
		txt_ac_no.setText("");
		combo_ac_type.setSelectedIndex(0);
		txt_trnmode.setText("");
		examp.clear();
		button_submit.setEnabled(true);
		button_clear.setEnabled(true);
		
		
		
	}



	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getSource()==combo_mode){
			if(combo_mode.getSelectedIndex()==1){
				combo_trn_mode.setVisible(true);
				txt_trnmode.setVisible(true);
			}else{
				combo_trn_mode.setVisible(false);
				txt_trnmode.setVisible(false);
			}
		}
		
	}
	
	
}
