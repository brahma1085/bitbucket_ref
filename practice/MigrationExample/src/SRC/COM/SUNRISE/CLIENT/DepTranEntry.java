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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
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
import javax.swing.SwingConstants;

import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.CLIENT.ShareCertificateNo.certificateObserver;
import SRC.COM.SUNRISE.SERVER.StoreDepTran;
import SRC.COM.SUNRISE.SERVER.StoreDeposit;
import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.FloatNumListener;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotFound;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositTransactionObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ShareMasterObject;

public class DepTranEntry extends JPanel implements KeyListener,FocusListener,Observer,ActionListener {
	
	GridBagConstraints gbc;
	
		
	static DepTranEntry tran_entry;
	JComboBox combo_trn_type,combo_trn_mode,combo_details,combo_ac_type;
	
	JLabel lbl_ac_no,lbl_trn_seq,lbl_dep_amt,lbl_int_amt,lbl_rd_bal,lbl_int_date,lbl_inst_amt,lbl_trn_date;
	JPanel panel,panel_jsp;
	Personal personal;
	StoreDeposit  store_dep_mast;
	StoreDepTran store_dep_trn=null;
	private JButton btn_submit;
	private JButton btn_update;
	private JButton btn_clear;
	private JTextField txt_accno;
	private JTextField txt_trn_date;
	private JTextField txt_trn_seq;
	private JTextField txt_dep_amt;
	private JTextField txt_int_date;
	private JTextField txt_int_amt,txt_rd_bal;
	JScrollPane jsp[]=new JScrollPane[1];
	
	RDexample rd_tran;
	DepositTransactionObject deptranobj=null;
	
	DepositObject depobj=null;
	
	
	DateListener dtlist = new DateListener();
	FloatNumListener floatlis = new FloatNumListener();
	
private DepTranEntry() {
		
		setLayout(new GridBagLayout());
		gbc=new GridBagConstraints();
		
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets=new Insets(4,4,4,4);
		
		
		JLabel lbl_head;
		
		for(int i=0;i<30;i++){
			addComponent(new JLabel("   "),this,0,i,0,0,1,1);
		}
		addComponent(lbl_head=new JLabel("RD Transaction Entry"),this,1,15,0,0,20,1);
		lbl_head.setFont(new Font("Times New Roman",Font.BOLD,14));
		lbl_head.setHorizontalAlignment(SwingConstants.CENTER);
		
		addComponent(panel=new JPanel(),this,0,0,0,0,7,1);
		panel.setLayout(new GridBagLayout());
		
		addComponent(new JLabel("Account No."),this,1,2,0,0,1,1);
		//addComponent(lbl_ac_no=new JLabel(" "),this,1,1,0,0,1,1);
		addComponent(txt_accno=new JTextField(5),this,1,4,0,0,1,1);
		//lbl_ac_no.addFocusListener(this);
		txt_accno.addKeyListener(this);
		//txt_accno.addFocusListener(this);
		
		addComponent(new JLabel("Account Type"),this,1,0,0,0,1,1);
	    addComponent(combo_ac_type=new JComboBox(),this,1,1,0,0,1,1);
	     	combo_ac_type.addItem("FD");
			combo_ac_type.addItem("RD");
			combo_ac_type.addItem("TMCC");
			combo_ac_type.addItem("BLCC");
			combo_ac_type.addFocusListener(this);
			
		addComponent(new JLabel("Rd Balance"),this,2,0,0,0,1,1);
		//addComponent(lbl_rd_bal=new JLabel(""),this,2,1,0,0,1,1);
		addComponent(txt_rd_bal=new JTextField(5),this,2,1,0,0,1,1);
		txt_rd_bal.addKeyListener(this);
		txt_rd_bal.addKeyListener(this);		
		
	/*	panel_jsp=new JPanel(); 
		addComponent(panel_jsp ,this,2,10,0.0,0.0,19,21);
		panel_jsp.setLayout(new GridBagLayout());
		panel_jsp.setBorder(BorderFactory.createEtchedBorder());*/
		/*for ( int i=0; i< 15; i++) {
			
			addComponent(new JLabel("    mmmmm  "),panel_jsp,0,i,0.0,0.0,1,1);
		}	*/
	//	addComponent(new JLabel(" geetha "),panel_jsp,22,21,1.0,1.0,1,1);
					    	    
	    String[] str = new String[9];
	    str[0] = "Tran date";
	    str[1] = "Tran seq";
	    str[2] = "Deposit amt/InstallmentAmt";
	    str[3] = "Tran Type";
	    
	    str[4] = "Interest Amt";
	    str[5] = "Rd bal";
	    str[6] = "Interest date";
	    str[7] = "Tran mode";
	    str[8] = "Total interest";
	    
	    rd_tran = new RDexample(str,0);
	    rd_tran.observers.addObserver(this);
	    
	   /* if(combo_ac_type.getSelectedIndex()==0) {
	    	
	    	 str[2] = "Installment amt";
	    	 str[5] = "Rd bal";
	    	
	    	
	    }
	    
	    */
		//panel_jsp.setBackground(new Color(253,254,233));
		
		//jsp = new JScrollPane[1];
		
		
		
			
	    panel=new JPanel(); 
		addComponent(panel ,this,2,10,0.0,0.0,19,21);
		panel.setLayout(new GridBagLayout());
		/*panel.setBorder(BorderFactory.createEtchedBorder());
		for ( int i=0; i< 20; i++) {
			
			addComponent(new JLabel("   hhhh   "),panel,0,i,0.0,0.0,1,1);
		}	*/
		addComponent(new JLabel(" "),panel,22,21,1.0,1.0,1,1);
		
	   jsp = new JScrollPane[1];

		jsp[0]=new JScrollPane(rd_tran,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);   
	    addComponent(jsp[0],panel,1,0,0.0,0.0,19,19);
	//  addComponent(jsp[0],panel,1,0,0.0,0.0,19,25);
	    jsp[0].setVisible(true);
	 
	    
		
	
			btn_submit = new JButton("Submit");
			addComponent( btn_submit, this, 28, 4, 0.0, 0.0, 4, 1 );
			btn_submit.addActionListener(this);
			
			btn_update = new JButton("Update");
			addComponent( btn_update, this, 28, 8, 0.0, 0.0, 4, 1 );
			btn_update.addActionListener(this);
			
			btn_clear = new JButton("Clear");
			addComponent( btn_clear, this, 28, 12, 0.0, 0.0, 4, 1 );
			btn_clear.addActionListener(this);
			
			
			
			try {
				
				store_dep_trn=new StoreDepTran();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			panel.setVisible(true);
			clearScreen();
			
			

		
}		

public static DepTranEntry getInstance(int i) {
	
	if ( i == 0)
		return	tran_entry = new DepTranEntry();
	
	if ( tran_entry == null )
	    return	tran_entry = new DepTranEntry();
	 
	return tran_entry;
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

public void clearScreen() {
	
	
	
	txt_accno.setText("");
	txt_rd_bal.setText("");
	rd_tran.clear();
	combo_ac_type.setSelectedIndex(0);
	/*txt_dep_amt.setText("");
	txt_int_amt.setText("");
	txt_int_date.setText("");
	txt_trn_date.setText("");
	txt_trn_seq.setText("");
	txt_rd_bal.setText("");
	combo_trn_type.setSelectedIndex(0);
	combo_trn_mode.setSelectedIndex(0);
	*/
	btn_submit.setEnabled(true);
	btn_update.setEnabled(true);
	
	
	
}

/*public void actionPerformed(ActionEvent arg ) {

	if ( arg.getSource() == btn_submit || arg.getSource() == btn_update ) {
		
		if(getDepositObject()!=null) {
		
			try {
				
				if ( arg.getSource() == btn_submit  ){
					if ( store_dep_trn.StoreDepTran(deptrn)) {

						JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
						clearScreen();
					} else
						JOptionPane.showMessageDialog( null, "Inserted UnSucessfull " );
			
				} else if (arg.getSource() == btn_update ) {
					
					if ( store_dep_trn.updateDepositAccount(deptrn)) {
						
						JOptionPane.showMessageDialog( null, "Sucessfully Updated" );
						clearScreen();
						
					} else {
						JOptionPane.showMessageDialog( null, "Updation UnSucessfull " );
						
					}
				}
	
			} catch  ( RecordNotInserted rec ) {
				
				JOptionPane.showMessageDialog(null, "Inserted UnSucessfull ");
				
			} catch  ( RecordNotUpdated rec ) {
				
				JOptionPane.showMessageDialog(null, "Inserted UnSucessfull ");
				
			}catch ( Exception exc ) {
				
				exc.printStackTrace();
			}
			
			
		} else {
			
			JOptionPane.showMessageDialog(null, "Sorry !");
		}
		
	} else if ( arg.getSource() == btn_clear) {
		
		clearScreen();
	}
	
}*/
//
public void actionPerformed(ActionEvent e) {
	
	if( e.getSource() == btn_submit ){

		int a = 0;
			
			DepositTransactionObject depTran=getDepositObject();
			if ( depTran != null ) {
				
				try {
					a=store_dep_trn.StoreDepTran(depTran);
					System.out.println("i am clicked twice");
					/*throw new RecordNotInserted();*/
				} catch (RecordNotInserted e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				


				if( a==1 ){
					JOptionPane.showMessageDialog(this, "Details Inserted Successfully");
					clearScreen();
				}
				else{
					JOptionPane.showMessageDialog(this, "Details Not Inserted");
				}
			}
			
		}
	
	if(e.getSource()==btn_clear){
		clearScreen();
	}
	
	if( e.getSource() == btn_update) {
		
		if(txt_accno.getText().length()==0) {
			JOptionPane.showMessageDialog(null,"Please Enter the account no.");
		}
		else {
			
			 
			
				int a = 0;
		
			if ( getDepositObject()!= null) {
				
				try {
					a=store_dep_trn.StoreDepTran(getDepositObject());
				} catch (RecordNotInserted e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		
			if( a == 1 ){
			
				JOptionPane.showMessageDialog(this, "details Updated Successfully");
				clearScreen();
			}
			else{
				
				JOptionPane.showMessageDialog(this, "Details Not Updated");
			}
		}
				
		}
	}//else
} 


//
	
private DepositTransactionObject getDepositObject() {
	
	//DepositTransactionObject deptrn= new DepositTransactionObject();
	
	deptranobj=new DepositTransactionObject();
	
	
	
	Hashtable table = rd_tran.gettrnseq();
	
	Enumeration e=table.keys();
	while(e.hasMoreElements()) {
		System.out.println("****************************111111");
		String key=e.nextElement().toString();
		System.out.println("key>> "+key);
		DepositObject dep=(DepositObject)table.get(key);
		System.out.println(dep.getCum_int());
		System.out.println(dep.getInt_amt());
		System.out.println(dep.getInterest_date());
		System.out.println(dep.getRd_bal());
		System.out.println(dep.getTrn_seq());
		System.out.println(table.get(key));
		System.out.println("****************************222222");
	}
	deptranobj.setHash_dep(table);
	
	switch ( combo_ac_type.getSelectedIndex() ) {
	
	case 0 : deptranobj.setAccType("1003001");
	         txt_rd_bal.setEnabled(false);
	         break; 
	         
	case 1 : deptranobj.setAccType("1004001");
	         
			 
			 
	case 3 : deptranobj.setAccType("1005004");
			break;
	 
	case 4 : deptranobj.setAccType("1005005");
			break;
	
	}
	
	deptranobj.setAccNo(Integer.parseInt(txt_accno.getText()));
	
	/*Collection  en = table.values();
	Iterator it = en.iterator();*/
	
	System.out.println(" no of installments------------------");
	/*deptranobj.setTranSequence();*/
	
	
	return deptranobj;
	
	
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
		
		txt_rd_bal.setText( ar.toString());
		
	}
	
}


public void focusGained(FocusEvent arg0) {
	// TODO Auto-generated method stub
	
	if ( arg0.getSource() == txt_rd_bal) {
		
		Collection  en = (rd_tran.gettrnseq()). values();
		if ( en != null)
		{
		int sum = 0;
		Iterator it = en.iterator();
		
		while (it.hasNext()) {
			
			// rd balance has to be calculated here
			
			
		}
		txt_rd_bal.setText("");
	} else {
		
		JOptionPane.showMessageDialog(null, " Please fill the No");
	}
 }
	
}


public void focusLost(FocusEvent e) {
	
	
	if ( e.getSource() == txt_accno ) {
		
		if ( txt_accno.getText().toString().trim().length() > 0 &&  new Integer(txt_accno.getText().toString().trim()).intValue() >0 ) {
				
			DepositTransactionObject deptranobj = new DepositTransactionObject();
			
			Object[] obj;
			try {
				deptranobj = store_dep_trn.getDepositAccountInfo(combo_ac_type.getSelectedIndex() == 0 ? "1003001":"1004001", new Integer(txt_accno.getText().toString().trim()).intValue());
				
				
					 
					 txt_accno.setText(String.valueOf(deptranobj.getAccNo()));
					 
					 combo_ac_type.setSelectedItem(deptranobj.getAccType());
					 txt_rd_bal.setText(String.valueOf(deptranobj.getRDBalance()));
					 rd_tran.setTrnseq(deptranobj.getHash_dep());
					 
					 
					 
		           
				 
				
				
				
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RecordNotFound e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
		}
}


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



} 