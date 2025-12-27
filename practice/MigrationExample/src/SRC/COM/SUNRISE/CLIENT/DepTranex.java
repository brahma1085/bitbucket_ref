package SRC.COM.SUNRISE.CLIENT;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Collections;
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
import javax.swing.border.BevelBorder;

import com.mysql.jdbc.Connection;


import SRC.COM.SUNRISE.SERVER.GetDBConnection;
import SRC.COM.SUNRISE.SERVER.StoreDepTran;
import SRC.COM.SUNRISE.SERVER.StoreDeposit;
import SRC.COM.SUNRISE.SERVER.Storeshare;
import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.FloatNumListener;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositTransactionObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ModuleObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ShareMasterObject;

public class DepTranex extends JPanel implements FocusListener,KeyListener, ActionListener, Observer{
	
	GridBagConstraints gbc;
	static DepTranex trn_entry;
	JComboBox combo_trn_type,combo_trn_mode,combo_details;
	
	JLabel lbl_ac_no,lbl_trn_seq,lbl_dep_amt,lbl_int_amt,lbl_rd_bal,lbl_int_date,lbl_inst_amt,lbl_trn_date;
	JPanel panel;
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
	JScrollPane jsp[]=new JScrollPane[2];
	
	DateListener dtlist = new DateListener();
	FloatNumListener floatlis = new FloatNumListener();
	
	RDexample rd_tran;
	ModuleObject mobj[],mobj1[]=null;
	//private Object combo_ac_type;
	
	
	private DepTranex() {
		
		setLayout(new GridBagLayout());
		gbc=new GridBagConstraints();
		
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets=new Insets(4,4,4,4);
		
		
		JLabel lbl_head;
		addComponent(lbl_head=new JLabel("RD Transaction Entry"),this,0,0,0,0,4,1);
		lbl_head.setFont(new Font("Times New Roman",Font.BOLD,14));
		lbl_head.setHorizontalAlignment(SwingConstants.CENTER);
		
		addComponent(panel=new JPanel(),this,2,0,0,0,7,1);
		panel.setLayout(new GridBagLayout());
		
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		panel.setPreferredSize(new Dimension(500,400));
		
		panel.setBackground(new Color(233,254,253));
		
		
		
		//panel.setBackground(new Color(253,254,233));
		
		
		addComponent(new JLabel("Account No."),this,0,0,0,0,1,1);
		addComponent(lbl_ac_no=new JLabel(" "),this,0,1,0,0,1,1);
		addComponent(txt_accno=new JTextField(20),this,0,2,0,0,1,1);
		lbl_ac_no.addFocusListener(this);
		txt_accno.addKeyListener(this);
		
		addComponent(new JLabel("Transaction Date"),this,1,0,0,0,1,1);
		addComponent(lbl_trn_date=new JLabel(" "),this,1,1,0,0,1,1);
		addComponent(txt_trn_date=new JTextField(10),this,1,2,0,0,1,1);
		lbl_trn_date.addKeyListener(this);
		txt_trn_date.addKeyListener(this);
		txt_trn_date.addKeyListener(new DateListener());
		
		addComponent(new JLabel("Transaction Seq."),this,2,0,0,0,1,1);
		addComponent(lbl_trn_seq=new JLabel(" "),this,2,1,0,0,1,1);
		addComponent(txt_trn_seq=new JTextField(10),this,2,2,0,0,1,1);
		lbl_trn_seq.addKeyListener(this);
		txt_trn_seq.addKeyListener(this);
		
		addComponent(new JLabel("Deposit Amount"),this,3,0,0,0,1,1);
		addComponent(lbl_dep_amt=new JLabel(" "),this,3,1,0,0,1,1);
		addComponent(txt_dep_amt=new JTextField(10),this,3,2,0,0,1,1);
		lbl_dep_amt.addKeyListener(this);
		txt_dep_amt.addKeyListener(this);
		
		//txt_dep_amt.addKeyListener(panel);
		
		addComponent(new JLabel("Interest Date"),this,4,0,0,0,1,1);
		addComponent(lbl_int_date=new JLabel(" "),this,4,1,0,0,1,1);
		addComponent(txt_int_date=new JTextField(10),this,4,2,0,0,1,1);
		lbl_int_date.addKeyListener(this);
		txt_int_date.addKeyListener(this);
		txt_int_date.addKeyListener(new DateListener());
		
		addComponent(new JLabel("Interest Amt"),this,6,0,0,0,1,1);
		addComponent(lbl_int_amt=new JLabel(" "),this,6,1,0,0,1,1);
		addComponent(txt_int_amt=new JTextField(10),this,6,2,0,0,1,1);
		
		lbl_int_amt.addKeyListener(this);
		txt_int_amt.addKeyListener(this);
		txt_int_amt.addKeyListener(floatlis);
		
		
		addComponent(new JLabel("Account Type"),this,9,0,0,0,1,1);
	/*	addComponent(combo_ac_type=new JComboBox(),this,9,1,0,0,1,1);
		combo_ac_type.addItem("FD");
		combo_ac_type.addItem("RD");
		combo_ac_type.addItem("RI");
		combo_ac_type.addFocusListener(this);*/
		
		
		addComponent(new JLabel("Transaction Type"),this,10,0,0,0,1,1);
		addComponent(combo_trn_type=new JComboBox(),this,10,1,0,0,1,1);
		combo_trn_type.addItem("D");
		combo_trn_type.addItem("I");
		combo_trn_type.addItem("P");
		
		combo_trn_type.addFocusListener(this);
		
		addComponent(new JLabel("Details"), this, 11, 0, 0, 0, 1, 1);
		addComponent(combo_details=new JComboBox(),this,11,1,0,0,1,1);
		combo_details.addItem("Personal");
		combo_details.addItem("Transaction");
		combo_details.addFocusListener(this);
		
		txt_accno=new JTextField("0");
		
		
		combo_trn_type.addFocusListener(this);
		
		
		addComponent(new JLabel("Transaction Mode"),this,12,0,0,0,1,1);
		addComponent(combo_trn_mode=new JComboBox(),this,12,1,0,0,1,1);
			
		combo_trn_mode.addItem("T");
		combo_trn_mode.addItem("C");
		combo_trn_mode.addKeyListener(this);
		
		addComponent(new JLabel("Transaction Mode"),this,12,0,0,0,1,1);
		addComponent(combo_trn_mode=new JComboBox(),this,12,1,0,0,1,1);
		combo_trn_mode.addKeyListener(this);
		
		  String[] str = new String[2];
		  str[0] = "Trn seq";
		  str[1] = "No of Installments";
		    rd_tran = new RDexample(str,0);
		    rd_tran.observers.addObserver(this);
		    
		    
		    panel=new JPanel(); 
			addComponent(panel ,this,2,10,0.0,0.0,19,21);
			panel.setLayout(new GridBagLayout());
			panel.setBorder(BorderFactory.createEtchedBorder());
			for ( int i=0; i< 20; i++) {
				
				addComponent(new JLabel("      "),panel,0,i,0.0,0.0,1,1);
			}	
			addComponent(new JLabel("  "),panel,22,21,1.0,1.0,1,1);
			
		   jsp = new JScrollPane[2];

			jsp[0]=new JScrollPane( personal,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp[1]=new JScrollPane( rd_tran,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);   
		    

			addComponent(jsp[0],panel,1,0,0.0,0.0,19,19);
			//jsp[0].setVisible(false);
			
			addComponent(jsp[1],panel,1,0,0.0,0.0,19,19);
			jsp[1].setVisible(false);
		
					
			
		btn_submit = new JButton("Submit");
		addComponent( btn_submit, this, 28, 4, 0.0, 0.0, 4, 1 );
		btn_submit.addActionListener(this);
		
		btn_update = new JButton("Update");
		addComponent( btn_update, this, 28, 8, 0.0, 0.0, 4, 1 );
		btn_update.addActionListener(this);
		
		btn_clear = new JButton("Clear");
		addComponent( btn_clear, this, 28, 12, 0.0, 0.0, 4, 1 );
		btn_clear.addActionListener(this);
	
try{
			
			store_dep_trn=new StoreDepTran();
			mobj1=store_dep_trn.getModulecode(2,"1002000,1007000,1014000,1015000,1018000");// for SB,CA module abbr
			mobj=store_dep_trn.getModulecode(2,"1003000");			//for Share module abbr
			/*for(int i=0; i< mobj.length;i++)
			{
				combo_ac_type.add(mobj[i].getModuleAbbrv());	
				//combo_introducer_acctype.addItem(mobj[i].getModuleAbbrv());

			}

			for(int j=0;j<mobj1.length;j++){
				combo_ac_type.addItem(mobj1[j].getModuleAbbrv());
			}*/


		}catch(Exception e){
		e.printStackTrace();
	} 
		
		
		panel.setVisible(true);
		clearScreen();
		
		
		
		
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
	
/*	public ModuleObject[] getModulecode(int a,String str){
		Connection conn = GetDBConnection.;
		ResultSet rs=null;
		ModuleObject[] mobj=null;
		try {
		
			Statement stmt = conn.createStatement();
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(a==2){
			rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+str+")  order by modulecode");
			}
			
			if(rs.next())
			{	
				rs.last();
				mobj=new ModuleObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				mobj[i]=new ModuleObject();
				mobj[i].setModuleCode(rs.getString("modulecode"));
				mobj[i].setModuleDesc(rs.getString("moduledesc"));
				mobj[i].setModuleAbbrv(rs.getString("moduleabbr"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mobj;
	}
	*/
	
public static DepTranex getInstance(int i) {
			
	    	if ( i == 0)
	    		return	trn_entry = new DepTranex();
	    	
	    	if ( trn_entry == null )
	    	    return	trn_entry = new DepTranex();
	    	 
	    	return trn_entry;
}

public void clearScreen() {
	
	
		
	txt_accno.setText("");
	txt_dep_amt.setText("");
	txt_int_amt.setText("");
	txt_int_date.setText("");
	txt_trn_date.setText("");
	txt_trn_seq.setText("");
	
	btn_submit.setEnabled(true);
	btn_update.setEnabled(true);
	combo_trn_type.setSelectedIndex(0);
	
	
}

public void actionPerformed(ActionEvent arg ) {

	if ( arg.getSource() == btn_submit || arg.getSource() == btn_update ) {
		
		DepositTransactionObject deptrn = getPanelInfo();
		
		if ( deptrn != null ) {
			try {
				
				if ( arg.getSource() == btn_submit  ){
					if ( store_dep_trn.StoreDepTran(deptrn)>0) {

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
	
}

private DepositTransactionObject getPanelInfo() {
	
	int count = 0;
	DepositMasterObject dep_obj = null ;
	DepositTransactionObject deptrn =null;
		
	dep_obj=new DepositMasterObject();
	deptrn=new DepositTransactionObject();
	
	Hashtable table = rd_tran.gettrnseq();	
	deptrn.setTrn_seq(table);
	
	
	
	
		dep_obj = new DepositMasterObject();
		deptrn = new DepositTransactionObject();
		
	/*	switch ( combo_ac_type.getSelectedIndex() ) {
		
		case 0 : deptrn.setAccType("1003001");
		         break; 
		case 1 : deptrn.setAccType("1004001");
				 break;
		
		}
		
		deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
		
		switch ( combo_ac_type.getSelectedIndex() ) {
		
		case 0 : deptrn.setAccType("1003001");
		         break; 
		case 1 : deptrn.setAccType("1004001");
				 break;
		
		}*/
		
		deptrn.setTranDate(txt_trn_date.getText().toString().trim());
		
		deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
		deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
		
	
		//deptrn.setInterestDate(new Double( txt_int_date.getText().toString().trim()));
		deptrn.setInterestDate(txt_int_date.getText().toString().trim());
		
		Collection  en = table.values();
		int sum = 0;
		Iterator it = en.iterator();
		
		while (it.hasNext()) {
			sum += new Integer(it.next().toString()).intValue();
			
		}
		
		System.out.println( sum + " GEEEE-------------------");
		deptrn.getTrn_seq();
		deptrn.setRDBalance((sum*100));
		
		
		if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
			deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the Balance  " );
		}

		dep_obj.setDep_mths(0);
		dep_obj.setDep_yrs(0);
		dep_obj.setMat_post("N");
		dep_obj.setExtra_rate_type(0);
		dep_obj.setLst_trn_seq(0);
		dep_obj.setLst_pr_seq(0);
		dep_obj.setDep_renewed("F");
		dep_obj.setNew_rct("F");
		dep_obj.setRct_prtd("F");
		dep_obj.setRct_sign("F");

	
	return deptrn;
}


public void focusGained(FocusEvent arg0) {
	// TODO Auto-generated method stub
	
	if ( arg0.getSource() == txt_rd_bal ) {
		
		Collection  en = (rd_tran.gettrnseq()). values();
		if ( en != null)
		{
		int sum = 0;
		Iterator it = en.iterator();
		
		while (it.hasNext()) {
			sum += new Integer(it.next().toString()).intValue();
			
		}
		txt_rd_bal.setText(new Double(sum * 100.0).toString());
	} else {
		
		JOptionPane.showMessageDialog(null, " Please fill the Trnsaction No");
	}
	}
	
}


	
	public void focusLost(FocusEvent e) {
		
		
	  if (txt_accno.getText().toString().trim().length() > 0 && new Integer( txt_accno.getText().toString()).intValue() > 0 ) {
			
		  DepositTransactionObject dep = getPanelInfo();
		  if ( e.getSource() == btn_submit  ){
				try {
					if ( store_dep_trn.StoreDepTran(dep)>0) {

						JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
						clearScreen();
					} else
						JOptionPane.showMessageDialog( null, "Inserted UnSucessfull " );
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RecordNotInserted e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
			} 
						

				
				
				rd_tran.setTrnseq(dep.getTrn_seq());
				txt_rd_bal.setText( new Double(dep.getRDBalance()).toString());
				btn_submit.setEnabled(false);
			}
	

		
	}


	

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
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


/*package SRC.COM.SUNRISE.CLIENT;
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






public class DepTranEntry extends JPanel implements ActionListener,KeyListener,ItemListener,FocusListener,Observer {
	//Components 
	GridBagConstraints gbc;
JComboBox combo_ac_type,combo_trn_type,combo_trn_mode,combo_details;
	
	JLabel lbl_ac_no,lbl_trn_seq,lbl_dep_amt,lbl_int_amt,lbl_rd_bal,lbl_int_date,lbl_inst_amt,lbl_trn_date,lbl_head;
	JPanel panel;
	//Personal personal;
	StoreDeposit  store_dep_mast;
	StoreDepTran store_dep_trn=null;
	private JTextField txt_accno,txt_customerid;
	private JTextField txt_trn_date,txt_cum_int;
	private JTextField txt_trn_seq;
	private JTextField txt_dep_amt;
	private JTextField txt_int_date;
	private JTextField txt_int_amt,txt_rd_bal;
	
	private JButton button_submit,button_clear,button_update;
	//JScrollPane jsp[]=new JScrollPane[2];
	
	DateListener dtlist = new DateListener();
	FloatNumListener floatlis = new FloatNumListener();
	
	RDexample rd_tran;
	

	
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
	DepositTransactionObject deptran_obj=null;
	//ShareMasterObject sharemasterobject=null;
	//Personal personal=null;
	CIDPanel cidpanel=null;
	Nominee nominee=null;
	Personal personal;
	RDexample rd_entry;
	
	Storeshare storeshare=null;
	ApplicationDetails appdet=null;
	ModuleObject mobj[],mobj1[]=null;
	static DepTranEntry trn_entry;
	
	public DepTranEntry() {
	
	
		setLayout(new GridBagLayout());	
		nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(2);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4,4,4,4);
		
			
	    panel_jp=new JPanel();
		panel_jp.setLayout(new GridBagLayout());
		//addComponent(panel_jp,this,0,0,1.0,1.0,1,1);
		//panel_jp.setVisible(true);
		//adding the components to panel_jp
		
		for ( int i=0; i< 30; i++) {
			
			addComponent(new JLabel("   jp panel   "),this,0,i,0.0,0.0,1,1);
		}
		
		addComponent(new JLabel(" "),this,24,31,1.0,1.0,1,1);
		
		addComponent(lbl_head=new JLabel("Transaction Entries"),this,1,15,0.0,0.0,20,1);
		lbl_head.setFont(new Font("Times new roman",Font.BOLD,14));
		
		addComponent(new JLabel("Dep Account Type"),this,2,0,0.0,0.0,3,1);
		addComponent(combo_ac_type=new JComboBox(), this,2,4,0.0,0.0,4,1);
		combo_ac_type.addFocusListener(this);
		
		addComponent(new JLabel("Account No"),this,3,0,0.0,0.0,3,1);
		addComponent(txt_accno=new JTextField(10),this,3,4,0.0,0.0,4,1);
		txt_accno.addKeyListener(new NumListener());
		txt_accno.addFocusListener(this);
		
		addComponent(new JLabel("Customer ID"), this,4,0,0.0,0.0,3,1);
		addComponent(txt_customerid=new JTextField(),this,4,4,0.0,0.0,4,1);
		txt_customerid.addKeyListener(new NumListener());
		txt_customerid.addFocusListener(this);
		
		addComponent(new JLabel("Transaction Date"),this,5,0,0.0,0.0,3,1);
		addComponent(txt_trn_date=new JTextField(MainScreen.getSysDate()),this,5,4,0.0,0.0,4,1);
		txt_trn_date.addKeyListener(new DateListener());
		txt_trn_date.setText(MainScreen.getSysDate());
		
		addComponent(new JLabel("Transaction seq"),this,6,0,0.0,0.0,3,1);
		addComponent(txt_trn_seq=new JTextField(),this,6,4,0.0,0.0,4,1);
		txt_trn_seq.addFocusListener(this);
		
		
		
		addComponent(new JLabel("Transaction Type"),this,7,0,0.0,0.0,3,1);
		addComponent(combo_trn_type=new JComboBox(),this,7,4,0.0,0.0,4,1);
		combo_trn_type.addItem("D");
		combo_trn_type.addItem("I");
		combo_trn_type.addItem("P");
		
		
		
		
		addComponent(new JLabel("Transaction Mode"),this,8,0,0.0,0.0,3,1);
		addComponent(combo_trn_mode=new JComboBox(),this,8,4,0.0,0.0,4,1);
		combo_trn_mode.addItem("T");
		combo_trn_mode.addItem("C");
		combo_trn_mode.addItem("P");
				
		
		
		
		txt_trn_seq=new JTextField("0");
		txt_int_amt= new JTextField("0");
	
		
		addComponent(new JLabel("Details"),this,13,0,0.0,0.0,3,1);
		addComponent(combo_details=new JComboBox(),this,13,4,0.0,0.0,4,1);
		combo_details.addItem("Personal Details");
		combo_details.addItem("Nominee Details");
		combo_details.addItem("Certificate Detail");
		combo_details.addItemListener(this);
		
		txt_rd_bal=new JTextField("0");
		
		
		addComponent(new JLabel("Interest date"),this,15,0,0.0,0.0,3,1);
		addComponent(txt_int_date=new JTextField("31/03/2000"),this,15,4,0.0,0.0,4,1);
		txt_int_date.addKeyListener(new DateListener());
		
		
			
		addComponent(new JLabel("Cumm.Interest"),this,17,0,0.0,0.0,3,1);
		addComponent(txt_cum_int=new JTextField(),this,17,4,0.0,0.0,4,1);
		
		txt_cum_int.addKeyListener(this);
		
		
		;
		
		addComponent(new JLabel("Div pay ac no"),this,18,0,0.0,0.0,3,1);
		addComponent(txt_rd_bal=new JTextField("0"),this,18,4,0.0,0.0,4,1);
		txt_rd_bal.setEnabled(false);
		
		addComponent(new JLabel("Interest Amt."),this,20,0,0.0,0.0,3,1);
		addComponent(txt_int_amt = new JTextField("0"),this,20,4,0.0,0.0,4,1);
		txt_int_amt.addFocusListener(this);
		
	
		
		
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
	    rd_entry = new RDexample(str,0);
	    rd_entry.observers.addObserver(this);
	    
		panel_jsp.setBackground(new Color(253,254,233));
		
		jsp = new JScrollPane[4];
		
		jsp[0]=new JScrollPane( personal,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[1]=new JScrollPane( nominee,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[2]=new JScrollPane( rd_entry ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp[3]=new JScrollPane( cidpanel ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		addComponent(jsp[0],panel_jsp,1,0,0.0,0.0,19,19);
		//jsp[0].setVisible(false);
		
		addComponent(jsp[1],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[1].setVisible(false);
		    
		addComponent(jsp[2],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[2].setVisible(false);
		
		addComponent(jsp[3],panel_jsp,1,0,0.0,0.0,19,19);
		jsp[3].setVisible(false);
		
		
		
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
		
		

		try{
			
			
			store_dep_trn=new StoreDepTran();
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
	
	private DepositTransactionObject getPanelInfo() {
		
		int count = 0;
		DepositMasterObject dep_obj = null ;
		DepositTransactionObject deptrn =null;
			
		dep_obj=new DepositMasterObject();
		deptrn=new DepositTransactionObject();
		
		Hashtable table = rd_tran.gettrnseq();	
		deptrn.setTrn_seq(table);
		
		
		
		
			dep_obj = new DepositMasterObject();
			deptrn = new DepositTransactionObject();
			
			switch ( combo_ac_type.getSelectedIndex() ) {
			
			case 0 : deptrn.setAccType("1003001");
			         break; 
			case 1 : deptrn.setAccType("1004001");
					 break;
			
			}
			
			deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
			
			switch ( combo_ac_type.getSelectedIndex() ) {
			
			case 0 : deptrn.setAccType("1003001");
			         break; 
			case 1 : deptrn.setAccType("1004001");
					 break;
			
			}
			
			deptrn.setTranDate(txt_trn_date.getText().toString().trim());
			
			deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
			deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
			
		
			
			deptrn.setInterestDate(txt_int_date.getText().toString().trim());
			
			Collection  en = table.values();
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			
			System.out.println( sum + " GEEEE-------------------");
			deptrn.getTrn_seq();
			deptrn.setRDBalance((sum*100));
			
			
			if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
				deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
			else {
				
				JOptionPane.showMessageDialog(null, "please enter the Balance  " );
			}

			dep_obj.setDep_mths(0);
			dep_obj.setDep_yrs(0);
			dep_obj.setMat_post("N");
			dep_obj.setExtra_rate_type(0);
			dep_obj.setLst_trn_seq(0);
			dep_obj.setLst_pr_seq(0);
			dep_obj.setDep_renewed("F");
			dep_obj.setNew_rct("F");
			dep_obj.setRct_prtd("F");
			dep_obj.setRct_sign("F");

		
		return deptrn;
	}	
	
	
	public void actionPerformed(ActionEvent e) {
		
		DepositTransactionObject deptrn=new DepositTransactionObject();
		
		if( e.getSource() == button_submit ){
			
				DepositMasterObject dep_obj = null ;
		
				
				dep_obj=new DepositMasterObject();
		//		deptrn=new DepositTransactionObject();
			
				Hashtable table = rd_tran.gettrnseq();	
				deptrn.setTrn_seq(table);
			
			
			
			
					dep_obj = new DepositMasterObject();
					deptrn = new DepositTransactionObject();
				
					switch ( combo_ac_type.getSelectedIndex() ) {
				
					case 0 : deptrn.setAccType("1003001");
				         break; 
					case 1 : deptrn.setAccType("1004001");
						 break;
				
					}
				
				deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
				
				switch ( combo_ac_type.getSelectedIndex() ) {
				
				case 0 : deptrn.setAccType("1003001");
				         break; 
				case 1 : deptrn.setAccType("1004001");
						 break;
				
				}
				
				deptrn.setTranDate(txt_trn_date.getText().toString().trim());
				
				deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
				deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
				
			
				
				deptrn.setInterestDate(txt_int_date.getText().toString().trim());
				
				Collection  en = table.values();
				int sum = 0;
				Iterator it = en.iterator();
				
				while (it.hasNext()) {
					sum += new Integer(it.next().toString()).intValue();
					
				}
				
				System.out.println( sum + " GEEEE-------------------");
				deptrn.getTrn_seq();
				deptrn.setRDBalance((sum*100));
				
				
				if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
					deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
				else {
					
					JOptionPane.showMessageDialog(null, "please enter the Balance  " );
				}

				dep_obj.setDep_mths(0);
				dep_obj.setDep_yrs(0);
				dep_obj.setMat_post("N");
				dep_obj.setExtra_rate_type(0);
				dep_obj.setLst_trn_seq(0);
				dep_obj.setLst_pr_seq(0);
				dep_obj.setDep_renewed("F");
				dep_obj.setNew_rct("F");
				dep_obj.setRct_prtd("F");
				dep_obj.setRct_sign("F");

			
				boolean a = false;
				
				if ( getDepositTransactionObject() != null ) {
					
					try {
						a=store_dep_trn.StoreDepTran(deptrn);
					} catch (RecordNotInserted e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					if( a==true){
						JOptionPane.showMessageDialog(this, "Share Details Inserted Successfully");
						clearForm();
					}
					else{
						JOptionPane.showMessageDialog(this, "Share Details Not Inserted");
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
		
	private DepositTransactionObject getDepositTransactionObject() {
		
		deptran_obj=new DepositTransactionObject();
		
		Hashtable table = rd_entry.getCertificateNo();
		
		deptran_obj.setTrn_seq(table);
		
		deptran_obj.setAccType(mobj[combo_ac_type.getSelectedIndex()].getModuleCode());
		
		deptran_obj.setAccNo(Integer.parseInt(txt_accno.getText()));
		
		//deptran_obj.setCdind(Integer.parseInt(txt_customerid.getText()));
		
		//deptran_obj.setName(personal.getName());
		
		System.out.println(personal.getName()+"************************");
		
		
		if ( txt_trn_date.getText().toString().length() > 0 )
			deptran_obj.setTranDate(txt_trn_date.getText());
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the transaction  date  " );
			return null;
		}

	//	deptran_obj.setTranMode(combo_trn_type.getSelectedItem());

		deptran_obj.setInterestAmt(new Double(txt_dep_amt.getText()));

		
		Collection  en = table.values();
		int sum = 0;
		Iterator it = en.iterator();
		
		while (it.hasNext()) {
			sum += new Integer(it.next().toString()).intValue();
			
		}
		
		System.out.println( sum + " rd balance is -------------------");
		deptran_obj.setAccNo(0);
		deptran_obj.setRDBalance((sum*100));
		
		if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
			deptran_obj.setRDBalance(new Double(txt_rd_bal.getText().toString()).doubleValue());
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the Balance  " );
		}
		
		if ( !txt_trn_date.getText().toString().equalsIgnoreCase("") && txt_trn_date.getText().toString().trim().length() > 0 )
			
			deptran_obj.setTranDate(txt_trn_date.getText());
		
	
		else {
			
			JOptionPane.showMessageDialog(null, "please enter the Transaction..... date  " );
			return null;
		}
		
	
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
		
		deptran_obj.setTranDate(MainScreen.getSysDate());
		//deptran_obj.setDe_datetime(MainScreen.getSysDate()+MainScreen.getSysTime());
		System.out.println("The time "+MainScreen.getSysDate()+" "+MainScreen.getSysTime());
	//	deptran_obj.setDe_tml(MainScreen.head_panel.getTml());
		System.out.println("tMl is"+MainScreen.head_panel.getTml());
		//deptran_obj.setde(MainScreen.head_panel.getUid());
		System.out.println("UID is"+MainScreen.head_panel.getUid());
		
		
		
		return deptran_obj;
		
	}
	

	public void clearForm()	
	{	

		txt_cum_int.setText("0");
		txt_accno.setEnabled(true);
		txt_dep_amt.setText("");
		txt_int_amt.setText("");	
		//txt_introducer_no.setText("");	
		txt_customerid.setText("");
	//	txt_introducer_no.setText("");	
		txt_int_date.setText("");
		//txt_close_date.setText("");
		txt_rd_bal.setText("");
		txt_trn_date.setEnabled(false);
		//txt_trn_seq.setText("31/03/2000");
		txt_trn_seq.setText("");
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
		
		//System.out.println("before sharaparam");
	    
		
		System.out.println("after sharaparam");
		System.out.println("inside  clear form");
		
			button_submit.setEnabled(true);
			button_submit.setVisible(true);
			button_submit.setText("S U B M I T");
			button_submit.setActionCommand("S U B M I T");
			
			//nominee.clearNominee();
		//	personal.clear();
			rd_entry.clear();
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

	public static DepTranEntry getInstance(int i) {
		
    	if ( i == 0)
    		return	trn_entry = new DepTranEntry();
    	
    	if ( trn_entry == null )
    	    return	trn_entry = new DepTranEntry();
    	 
    	return trn_entry;
}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
		if ( arg0.getSource() == txt_rd_bal) {
			
			Collection  en = (rd_entry.gettrnseq()). values();
			if ( en != null)
			{
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			txt_rd_bal.setText(new Double(sum * 100.0).toString());
		} else {
			
			JOptionPane.showMessageDialog(null, " Please fill this");
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
		
		 if ( e.getSource() == txt_accno)// && e.getOppositeComponent() == txt_customerid ) 
		{
			
			if( txt_accno.getText().equals("0") || txt_accno.getText().equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(this, "Enter the ac_no number  ");
				
			} else if (txt_accno.getText().toString().trim().length() > 0 && new Integer( txt_accno.getText().toString()).intValue() > 0 ) {
				
				
				int accno=Integer.parseInt(txt_accno.getText().toString().trim());
				
				ShareMasterObject shobj=new ShareMasterObject();
				
				DepositTransactionObject depTransactionObject =new DepositTransactionObject();
				dep = store_dep_trn.StoreDepTran(deptran);
				
				//clearForm();
				if ( shobj != null)
				{
					txt_accno.setText(String.valueOf(deptran_obj.getAccNo()));
					txt_trn_date.setText(deptran_obj.getTranDate()));
					txt_customerid.setText(String.valueOf(shobj.getCustomerId()));
					//txt_amt.setText(String.valueOf(shobj.getBal()));
					txt_rd_bal.setText(String.valueOf(deptran_obj.getRDBalance()));
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

					if ( (shobj.getShareStatus()).equalsIgnoreCase("A") )
						combo_share_status.setSelectedIndex(0);
					else 
						combo_share_status.setSelectedIndex(1);


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
					
					
					rd_entry.setTrnseq(deptran_obj.getTrn_seq());
					txt_rd_bal.setText( new Double(shobj.getBalance()).toString());
					button_submit.setEnabled(false);
				}
			}
			
		}
		
		else if ( e.getSource()== txt_rd_bal && (e.getOppositeComponent() == button_submit || e.getOppositeComponent() == button_update)) {
			
			if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().length() > 0) {
				
				
				Collection  en = ( rd_entry.getCertificateNo()). values();
				if ( en != null)
				{
				int sum = 0;
				Iterator it = en.iterator();
				
				while (it.hasNext()) {
					sum += new Integer(it.next().toString()).intValue();
					
				}
				if ( (new Double(txt_rd_bal.getText().toString())).doubleValue() < (sum *100.00) ) { 
					
					JOptionPane.showMessageDialog(null, "Balance amount is less than  Ammount");

				} 
			} else {
				
				JOptionPane.showMessageDialog(null, " Please fill the  No");
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
					txt_trn_date.requestFocus();
					
				}
				
			}
		}
	
		else if ( arg0 instanceof certificateObserver  ) {
			
			txt_rd_bal.setText( ar.toString());
			
		}
		
	}


	

}	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.HeadlessException;
	import java.awt.Insets;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.FocusEvent;
	import java.awt.event.FocusListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.util.Collection;
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
	import javax.swing.border.BevelBorder;
	
	import SRC.COM.SUNRISE.SERVER.GetDBConnection;
	import SRC.COM.SUNRISE.SERVER.StoreDepTran;
	import SRC.COM.SUNRISE.SERVER.StoreDeposit;
	import SRC.COM.SUNRISE.SERVER.Storeshare;
	import SRC.COM.SUNRISE.UTILITY.DateListener;
	import SRC.COM.SUNRISE.UTILITY.FloatNumListener;
	import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
	import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
	import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
	import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositTransactionObject;
	import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ModuleObject;
	import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ShareMasterObject;
	
	public class DepTranEntry extends JPanel implements FocusListener,KeyListener, ActionListener, Observer{
		
		GridBagConstraints gbc;
		static DepTranEntry trn_entry;
		JComboBox combo_ac_type,combo_trn_type,combo_trn_mode,combo_details;
		
		JLabel lbl_ac_no,lbl_trn_seq,lbl_dep_amt,lbl_int_amt,lbl_rd_bal,lbl_int_date,lbl_inst_amt,lbl_trn_date;
		JPanel panel;
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
		JScrollPane jsp[]=new JScrollPane[2];
		
		DateListener dtlist = new DateListener();
		FloatNumListener floatlis = new FloatNumListener();
		
		RDexample rd_tran;
		ModuleObject mobj[],mobj1[]=null;
		
		
		private DepTranEntry() {
			
			setLayout(new GridBagLayout());
			gbc=new GridBagConstraints();
			
			gbc.fill=GridBagConstraints.BOTH;
			gbc.insets=new Insets(4,4,4,4);
			
			
			JLabel lbl_head;
			addComponent(lbl_head=new JLabel("RD Transaction Entry"),this,0,0,0,0,4,1);
			lbl_head.setFont(new Font("Times New Roman",Font.BOLD,14));
			lbl_head.setHorizontalAlignment(SwingConstants.CENTER);
			
			addComponent(panel=new JPanel(),this,2,0,0,0,7,1);
			panel.setLayout(new GridBagLayout());
			
			panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			panel.setPreferredSize(new Dimension(500,400));
			
			panel.setBackground(new Color(233,254,253));
			
			
			
			//panel.setBackground(new Color(253,254,233));
			
			
			addComponent(new JLabel("Account No."),this,0,0,0,0,1,1);
			addComponent(lbl_ac_no=new JLabel(" "),this,0,1,0,0,1,1);
			addComponent(txt_accno=new JTextField(20),this,0,2,0,0,1,1);
			lbl_ac_no.addFocusListener(this);
			txt_accno.addKeyListener(this);
			
			addComponent(new JLabel("Transaction Date"),this,1,0,0,0,1,1);
			addComponent(lbl_trn_date=new JLabel(" "),this,1,1,0,0,1,1);
			addComponent(txt_trn_date=new JTextField(10),this,1,2,0,0,1,1);
			lbl_trn_date.addKeyListener(this);
			txt_trn_date.addKeyListener(this);
			txt_trn_date.addKeyListener(new DateListener());
			
			addComponent(new JLabel("Transaction Seq."),this,2,0,0,0,1,1);
			addComponent(lbl_trn_seq=new JLabel(" "),this,2,1,0,0,1,1);
			addComponent(txt_trn_seq=new JTextField(10),this,2,2,0,0,1,1);
			lbl_trn_seq.addKeyListener(this);
			txt_trn_seq.addKeyListener(this);
			
			addComponent(new JLabel("Deposit Amount"),this,3,0,0,0,1,1);
			addComponent(lbl_dep_amt=new JLabel(" "),this,3,1,0,0,1,1);
			addComponent(txt_dep_amt=new JTextField(10),this,3,2,0,0,1,1);
			lbl_dep_amt.addKeyListener(this);
			txt_dep_amt.addKeyListener(this);
			
			addComponent(new JLabel("Interest Date"),this,4,0,0,0,1,1);
			addComponent(lbl_int_date=new JLabel(" "),this,4,1,0,0,1,1);
			addComponent(txt_int_date=new JTextField(10),this,4,2,0,0,1,1);
			lbl_int_date.addKeyListener(this);
			txt_int_date.addKeyListener(this);
			txt_int_date.addKeyListener(new DateListener());
			
			addComponent(new JLabel("Interest Amt"),this,6,0,0,0,1,1);
			addComponent(lbl_int_amt=new JLabel(" "),this,6,1,0,0,1,1);
			addComponent(txt_int_amt=new JTextField(10),this,6,2,0,0,1,1);
			
			lbl_int_amt.addKeyListener(this);
			txt_int_amt.addKeyListener(this);
			txt_int_amt.addKeyListener(floatlis);
			
			
			addComponent(new JLabel("Account Type"),this,9,0,0,0,1,1);
			addComponent(combo_ac_type=new JComboBox(),this,9,1,0,0,1,1);
			combo_ac_type.addItem("FD");
			combo_ac_type.addItem("RD");
			combo_ac_type.addItem("RI");
			combo_ac_type.addFocusListener(this);
			
			
			addComponent(new JLabel("Transaction Type"),this,10,0,0,0,1,1);
			addComponent(combo_trn_type=new JComboBox(),this,10,1,0,0,1,1);
			combo_trn_type.addItem("D");
			combo_trn_type.addItem("I");
			combo_trn_type.addItem("P");
			
			combo_trn_type.addFocusListener(this);
			
			addComponent(new JLabel("Details"), this, 11, 0, 0, 0, 1, 1);
			addComponent(combo_details=new JComboBox(),this,11,1,0,0,1,1);
			combo_details.addItem("Personal");
			combo_details.addItem("Transaction");
			combo_details.addFocusListener(this);
			
			txt_accno=new JTextField("0");
			
			
			combo_trn_type.addFocusListener(this);
			
			
			addComponent(new JLabel("Transaction Mode"),this,12,0,0,0,1,1);
			addComponent(combo_trn_mode=new JComboBox(),this,12,1,0,0,1,1);
				
			combo_trn_mode.addItem("T");
			combo_trn_mode.addItem("C");
			combo_trn_mode.addKeyListener(this);
			
			addComponent(new JLabel("Transaction Mode"),this,12,0,0,0,1,1);
			addComponent(combo_trn_mode=new JComboBox(),this,12,1,0,0,1,1);
			combo_trn_mode.addKeyListener(this);
			
			  String[] str = new String[2];
			  str[0] = "Trn seq";
			  str[1] = "No of Installments";
			    rd_tran = new RDexample(str,0);
			    rd_tran.observers.addObserver(this);
			    
			    
			    panel=new JPanel(); 
				addComponent(panel ,this,2,10,0.0,0.0,19,21);
				panel.setLayout(new GridBagLayout());
				panel.setBorder(BorderFactory.createEtchedBorder());
				for ( int i=0; i< 20; i++) {
					
					addComponent(new JLabel("      "),panel,0,i,0.0,0.0,1,1);
				}	
				addComponent(new JLabel("  "),panel,22,21,1.0,1.0,1,1);
				
			   jsp = new JScrollPane[2];
	
				jsp[0]=new JScrollPane( personal,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				jsp[1]=new JScrollPane( rd_tran,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);   
			    
	
				addComponent(jsp[0],panel,1,0,0.0,0.0,19,19);
				//jsp[0].setVisible(false);
				
				addComponent(jsp[1],panel,1,0,0.0,0.0,19,19);
				jsp[1].setVisible(false);
			
						
				
			btn_submit = new JButton("Submit");
			addComponent( btn_submit, this, 28, 4, 0.0, 0.0, 4, 1 );
			btn_submit.addActionListener(this);
			
			btn_update = new JButton("Update");
			addComponent( btn_update, this, 28, 8, 0.0, 0.0, 4, 1 );
			btn_update.addActionListener(this);
			
			btn_clear = new JButton("Clear");
			addComponent( btn_clear, this, 28, 12, 0.0, 0.0, 4, 1 );
			btn_clear.addActionListener(this);
		
	try{
				
				store_dep_trn=new StoreDepTran();
				mobj1=store_dep_trn.getModulecode(2,"1002000,1007000,1014000,1015000,1018000");// for SB,CA module abbr
				mobj=store_dep_trn.getModulecode(2,"1003000");			//for Share module abbr
				for(int i=0; i< mobj.length;i++)
				{
					combo_ac_type.addItem(mobj[i].getModuleAbbrv());	
					//combo_introducer_acctype.addItem(mobj[i].getModuleAbbrv());
	
				}
	
				for(int j=0;j<mobj1.length;j++){
					combo_ac_type.addItem(mobj1[j].getModuleAbbrv());
				}
	
	
			}catch(Exception e){
			e.printStackTrace();
		} 
			
			
			panel.setVisible(true);
			clearScreen();
			
			
			
			
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
		
		public ModuleObject[] getModulecode(int a,String str){
			Connection conn = GetDBConnection.getConnection();
			ResultSet rs=null;
			ModuleObject[] mobj=null;
			try {
			
				Statement stmt = conn.createStatement();
				Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				if(a==2){
				rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+str+")  order by modulecode");
				}
				
				if(rs.next())
				{	
					rs.last();
					mobj=new ModuleObject[rs.getRow()];
					rs.beforeFirst();
				}
				int i=0;
				while(rs.next())
				{
					mobj[i]=new ModuleObject();
					mobj[i].setModuleCode(rs.getString("modulecode"));
					mobj[i].setModuleDesc(rs.getString("moduledesc"));
					mobj[i].setModuleAbbrv(rs.getString("moduleabbr"));
					i++;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return mobj;
		}
		
		
	public static DepTranEntry getInstance(int i) {
				
		    	if ( i == 0)
		    		return	trn_entry = new DepTranEntry();
		    	
		    	if ( trn_entry == null )
		    	    return	trn_entry = new DepTranEntry();
		    	 
		    	return trn_entry;
	}
	
	public void clearScreen() {
		
		
			
		txt_accno.setText("");
		txt_dep_amt.setText("");
		txt_int_amt.setText("");
		txt_int_date.setText("");
		txt_trn_date.setText("");
		txt_trn_seq.setText("");
		
		btn_submit.setEnabled(true);
		btn_update.setEnabled(true);
		combo_trn_type.setSelectedIndex(0);
		
		
	}
	
	public void actionPerformed(ActionEvent arg ) {
	
		if ( arg.getSource() == btn_submit || arg.getSource() == btn_update ) {
			
			DepositTransactionObject deptrn = getPanelInfo();
			
			if ( deptrn != null ) {
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
		
	}
	
	private DepositTransactionObject getPanelInfo() {
		
		int count = 0;
		DepositMasterObject dep_obj = null ;
		DepositTransactionObject deptrn =null;
			
		dep_obj=new DepositMasterObject();
		deptrn=new DepositTransactionObject();
		
		Hashtable table = rd_tran.gettrnseq();	
		deptrn.setTrn_seq(table);
		
		
		
		
			dep_obj = new DepositMasterObject();
			deptrn = new DepositTransactionObject();
			
			switch ( combo_ac_type.getSelectedIndex() ) {
			
			case 0 : deptrn.setAccType("1003001");
			         break; 
			case 1 : deptrn.setAccType("1004001");
					 break;
			
			}
			
			deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
			
			switch ( combo_ac_type.getSelectedIndex() ) {
			
			case 0 : deptrn.setAccType("1003001");
			         break; 
			case 1 : deptrn.setAccType("1004001");
					 break;
			
			}
			
			deptrn.setTranDate(txt_trn_date.getText().toString().trim());
			
			deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
			deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
			
		
			//deptrn.setInterestDate(new Double( txt_int_date.getText().toString().trim()));
			deptrn.setInterestDate(txt_int_date.getText().toString().trim());
			
			Collection  en = table.values();
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			
			System.out.println( sum + " GEEEE-------------------");
			deptrn.getTrn_seq();
			deptrn.setRDBalance((sum*100));
			
			
			if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
				deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
			else {
				
				JOptionPane.showMessageDialog(null, "please enter the Balance  " );
			}
	
			dep_obj.setDep_mths(0);
			dep_obj.setDep_yrs(0);
			dep_obj.setMat_post("N");
			dep_obj.setExtra_rate_type(0);
			dep_obj.setLst_trn_seq(0);
			dep_obj.setLst_pr_seq(0);
			dep_obj.setDep_renewed("F");
			dep_obj.setNew_rct("F");
			dep_obj.setRct_prtd("F");
			dep_obj.setRct_sign("F");
	
		
		return deptrn;
	}
	
	
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
		if ( arg0.getSource() == txt_rd_bal ) {
			
			Collection  en = (rd_tran.gettrnseq()). values();
			if ( en != null)
			{
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			txt_rd_bal.setText(new Double(sum * 100.0).toString());
		} else {
			
			JOptionPane.showMessageDialog(null, " Please fill the Trnsaction No");
		}
		}
		
	}
	
	
		
		public void focusLost(FocusEvent e) {
			
			
		  if (txt_accno.getText().toString().trim().length() > 0 && new Integer( txt_accno.getText().toString()).intValue() > 0 ) {
				
			  DepositTransactionObject dep = getPanelInfo();
			  if ( e.getSource() == btn_submit  ){
					try {
						if ( store_dep_trn.StoreDepTran(dep)) {
	
							JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
							clearScreen();
						} else
							JOptionPane.showMessageDialog( null, "Inserted UnSucessfull " );
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RecordNotInserted e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				} 
							
	
					
					
					rd_tran.setTrnseq(dep.getTrn_seq());
					txt_rd_bal.setText( new Double(dep.getRDBalance()).toString());
					btn_submit.setEnabled(false);
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
	
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			
		}
	
		
	}
	
	
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
	
	
	
	
	
	
	public class DepTranEntry extends JPanel implements ActionListener,KeyListener,ItemListener,FocusListener,Observer {
		//Components 
		GridBagConstraints gbc;
	JComboBox combo_ac_type,combo_trn_type,combo_trn_mode,combo_details;
		
		JLabel lbl_ac_no,lbl_trn_seq,lbl_dep_amt,lbl_int_amt,lbl_rd_bal,lbl_int_date,lbl_inst_amt,lbl_trn_date,lbl_head;
		JPanel panel;
		//Personal personal;
		StoreDeposit  store_dep_mast;
		StoreDepTran store_dep_trn=null;
		private JTextField txt_accno,txt_customerid;
		private JTextField txt_trn_date,txt_cum_int;
		private JTextField txt_trn_seq;
		private JTextField txt_dep_amt;
		private JTextField txt_int_date;
		private JTextField txt_int_amt,txt_rd_bal;
		
		private JButton button_submit,button_clear,button_update;
		//JScrollPane jsp[]=new JScrollPane[2];
		
		DateListener dtlist = new DateListener();
		FloatNumListener floatlis = new FloatNumListener();
		
		RDexample rd_tran;
		
	
		
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
		DepositTransactionObject deptran_obj=null;
		//ShareMasterObject sharemasterobject=null;
		//Personal personal=null;
		CIDPanel cidpanel=null;
		Nominee nominee=null;
		Personal personal;
		RDexample rd_entry;
		
		Storeshare storeshare=null;
		ApplicationDetails appdet=null;
		ModuleObject mobj[],mobj1[]=null;
		static DepTranEntry trn_entry;
		
		public DepTranEntry() {
		
		
			setLayout(new GridBagLayout());	
			nf = NumberFormat.getInstance(Locale.ENGLISH);
			nf.setMinimumFractionDigits(2);
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(4,4,4,4);
			
				
		    panel_jp=new JPanel();
			panel_jp.setLayout(new GridBagLayout());
			//addComponent(panel_jp,this,0,0,1.0,1.0,1,1);
			//panel_jp.setVisible(true);
			//adding the components to panel_jp
			
			for ( int i=0; i< 30; i++) {
				
				addComponent(new JLabel("   jp panel   "),this,0,i,0.0,0.0,1,1);
			}
			
			addComponent(new JLabel(" "),this,24,31,1.0,1.0,1,1);
			
			addComponent(lbl_head=new JLabel("Transaction Entries"),this,1,15,0.0,0.0,20,1);
			lbl_head.setFont(new Font("Times new roman",Font.BOLD,14));
			
			addComponent(new JLabel("Dep Account Type"),this,2,0,0.0,0.0,3,1);
			addComponent(combo_ac_type=new JComboBox(), this,2,4,0.0,0.0,4,1);
			combo_ac_type.addFocusListener(this);
			
			addComponent(new JLabel("Account No"),this,3,0,0.0,0.0,3,1);
			addComponent(txt_accno=new JTextField(10),this,3,4,0.0,0.0,4,1);
			txt_accno.addKeyListener(new NumListener());
			txt_accno.addFocusListener(this);
			
			addComponent(new JLabel("Customer ID"), this,4,0,0.0,0.0,3,1);
			addComponent(txt_customerid=new JTextField(),this,4,4,0.0,0.0,4,1);
			txt_customerid.addKeyListener(new NumListener());
			txt_customerid.addFocusListener(this);
			
			addComponent(new JLabel("Transaction Date"),this,5,0,0.0,0.0,3,1);
			addComponent(txt_trn_date=new JTextField(MainScreen.getSysDate()),this,5,4,0.0,0.0,4,1);
			txt_trn_date.addKeyListener(new DateListener());
			txt_trn_date.setText(MainScreen.getSysDate());
			
			addComponent(new JLabel("Transaction seq"),this,6,0,0.0,0.0,3,1);
			addComponent(txt_trn_seq=new JTextField(),this,6,4,0.0,0.0,4,1);
			txt_trn_seq.addFocusListener(this);
			
			
			
			addComponent(new JLabel("Transaction Type"),this,7,0,0.0,0.0,3,1);
			addComponent(combo_trn_type=new JComboBox(),this,7,4,0.0,0.0,4,1);
			combo_trn_type.addItem("D");
			combo_trn_type.addItem("I");
			combo_trn_type.addItem("P");
			
			
			
			
			addComponent(new JLabel("Transaction Mode"),this,8,0,0.0,0.0,3,1);
			addComponent(combo_trn_mode=new JComboBox(),this,8,4,0.0,0.0,4,1);
			combo_trn_mode.addItem("T");
			combo_trn_mode.addItem("C");
			combo_trn_mode.addItem("P");
					
			
			
			
			txt_trn_seq=new JTextField("0");
			txt_int_amt= new JTextField("0");
		
			
			addComponent(new JLabel("Details"),this,13,0,0.0,0.0,3,1);
			addComponent(combo_details=new JComboBox(),this,13,4,0.0,0.0,4,1);
			combo_details.addItem("Personal Details");
			combo_details.addItem("Nominee Details");
			combo_details.addItem("Certificate Detail");
			combo_details.addItemListener(this);
			
			txt_rd_bal=new JTextField("0");
			
			
			addComponent(new JLabel("Interest date"),this,15,0,0.0,0.0,3,1);
			addComponent(txt_int_date=new JTextField("31/03/2000"),this,15,4,0.0,0.0,4,1);
			txt_int_date.addKeyListener(new DateListener());
			
			
				
			addComponent(new JLabel("Cumm.Interest"),this,17,0,0.0,0.0,3,1);
			addComponent(txt_cum_int=new JTextField(),this,17,4,0.0,0.0,4,1);
			
			txt_cum_int.addKeyListener(this);
			
			
			;
			
			addComponent(new JLabel("Div pay ac no"),this,18,0,0.0,0.0,3,1);
			addComponent(txt_rd_bal=new JTextField("0"),this,18,4,0.0,0.0,4,1);
			txt_rd_bal.setEnabled(false);
			
			addComponent(new JLabel("Interest Amt."),this,20,0,0.0,0.0,3,1);
			addComponent(txt_int_amt = new JTextField("0"),this,20,4,0.0,0.0,4,1);
			txt_int_amt.addFocusListener(this);
			
		
			
			
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
		    rd_entry = new RDexample(str,0);
		    rd_entry.observers.addObserver(this);
		    
			panel_jsp.setBackground(new Color(253,254,233));
			
			jsp = new JScrollPane[4];
			
			jsp[0]=new JScrollPane( personal,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp[1]=new JScrollPane( nominee,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp[2]=new JScrollPane( rd_entry ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp[3]=new JScrollPane( cidpanel ,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			
			addComponent(jsp[0],panel_jsp,1,0,0.0,0.0,19,19);
			//jsp[0].setVisible(false);
			
			addComponent(jsp[1],panel_jsp,1,0,0.0,0.0,19,19);
			jsp[1].setVisible(false);
			    
			addComponent(jsp[2],panel_jsp,1,0,0.0,0.0,19,19);
			jsp[2].setVisible(false);
			
			addComponent(jsp[3],panel_jsp,1,0,0.0,0.0,19,19);
			jsp[3].setVisible(false);
			
			
			
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
			
			
	
			try{
				
				
				store_dep_trn=new StoreDepTran();
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
		
		private DepositTransactionObject getPanelInfo() {
			
			int count = 0;
			DepositMasterObject dep_obj = null ;
			DepositTransactionObject deptrn =null;
				
			dep_obj=new DepositMasterObject();
			deptrn=new DepositTransactionObject();
			
			Hashtable table = rd_tran.gettrnseq();	
			deptrn.setTrn_seq(table);
			
			
			
			
				dep_obj = new DepositMasterObject();
				deptrn = new DepositTransactionObject();
				
				switch ( combo_ac_type.getSelectedIndex() ) {
				
				case 0 : deptrn.setAccType("1003001");
				         break; 
				case 1 : deptrn.setAccType("1004001");
						 break;
				
				}
				
				deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
				
				switch ( combo_ac_type.getSelectedIndex() ) {
				
				case 0 : deptrn.setAccType("1003001");
				         break; 
				case 1 : deptrn.setAccType("1004001");
						 break;
				
				}
				
				deptrn.setTranDate(txt_trn_date.getText().toString().trim());
				
				deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
				deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
				
			
				
				deptrn.setInterestDate(txt_int_date.getText().toString().trim());
				
				Collection  en = table.values();
				int sum = 0;
				Iterator it = en.iterator();
				
				while (it.hasNext()) {
					sum += new Integer(it.next().toString()).intValue();
					
				}
				
				System.out.println( sum + " GEEEE-------------------");
				deptrn.getTrn_seq();
				deptrn.setRDBalance((sum*100));
				
				
				if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
					deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
				else {
					
					JOptionPane.showMessageDialog(null, "please enter the Balance  " );
				}
	
				dep_obj.setDep_mths(0);
				dep_obj.setDep_yrs(0);
				dep_obj.setMat_post("N");
				dep_obj.setExtra_rate_type(0);
				dep_obj.setLst_trn_seq(0);
				dep_obj.setLst_pr_seq(0);
				dep_obj.setDep_renewed("F");
				dep_obj.setNew_rct("F");
				dep_obj.setRct_prtd("F");
				dep_obj.setRct_sign("F");
	
			
			return deptrn;
		}	
		
		
		public void actionPerformed(ActionEvent e) {
			
			DepositTransactionObject deptrn=new DepositTransactionObject();
			
			if( e.getSource() == button_submit ){
				
					DepositMasterObject dep_obj = null ;
			
					
					dep_obj=new DepositMasterObject();
			//		deptrn=new DepositTransactionObject();
				
					Hashtable table = rd_tran.gettrnseq();	
					deptrn.setTrn_seq(table);
				
				
				
				
						dep_obj = new DepositMasterObject();
						deptrn = new DepositTransactionObject();
					
						switch ( combo_ac_type.getSelectedIndex() ) {
					
						case 0 : deptrn.setAccType("1003001");
					         break; 
						case 1 : deptrn.setAccType("1004001");
							 break;
					
						}
					
					deptrn.setAccNo( new Integer( txt_accno.getText().toString().trim()));
					
					switch ( combo_ac_type.getSelectedIndex() ) {
					
					case 0 : deptrn.setAccType("1003001");
					         break; 
					case 1 : deptrn.setAccType("1004001");
							 break;
					
					}
					
					deptrn.setTranDate(txt_trn_date.getText().toString().trim());
					
					deptrn.setTranSequence(new Integer(txt_trn_seq.getText().toString().trim()));
					deptrn.setDepositAmt(new Double( txt_dep_amt.getText().toString().trim()));
					
				
					
					deptrn.setInterestDate(txt_int_date.getText().toString().trim());
					
					Collection  en = table.values();
					int sum = 0;
					Iterator it = en.iterator();
					
					while (it.hasNext()) {
						sum += new Integer(it.next().toString()).intValue();
						
					}
					
					System.out.println( sum + " GEEEE-------------------");
					deptrn.getTrn_seq();
					deptrn.setRDBalance((sum*100));
					
					
					if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
						deptrn.setRDBalance((new Double(txt_rd_bal.getText().toString()).doubleValue()));
					else {
						
						JOptionPane.showMessageDialog(null, "please enter the Balance  " );
					}
	
					dep_obj.setDep_mths(0);
					dep_obj.setDep_yrs(0);
					dep_obj.setMat_post("N");
					dep_obj.setExtra_rate_type(0);
					dep_obj.setLst_trn_seq(0);
					dep_obj.setLst_pr_seq(0);
					dep_obj.setDep_renewed("F");
					dep_obj.setNew_rct("F");
					dep_obj.setRct_prtd("F");
					dep_obj.setRct_sign("F");
	
				
					boolean a = false;
					
					if ( getDepositTransactionObject() != null ) {
						
						try {
							a=store_dep_trn.StoreDepTran(deptrn);
						} catch (RecordNotInserted e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						if( a==true){
							JOptionPane.showMessageDialog(this, "Share Details Inserted Successfully");
							clearForm();
						}
						else{
							JOptionPane.showMessageDialog(this, "Share Details Not Inserted");
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
			
		private DepositTransactionObject getDepositTransactionObject() {
			
			deptran_obj=new DepositTransactionObject();
			
			Hashtable table = rd_entry.getCertificateNo();
			
			deptran_obj.setTrn_seq(table);
			
			deptran_obj.setAccType(mobj[combo_ac_type.getSelectedIndex()].getModuleCode());
			
			deptran_obj.setAccNo(Integer.parseInt(txt_accno.getText()));
			
			//deptran_obj.setCdind(Integer.parseInt(txt_customerid.getText()));
			
			//deptran_obj.setName(personal.getName());
			
			System.out.println(personal.getName()+"************************");
			
			
			if ( txt_trn_date.getText().toString().length() > 0 )
				deptran_obj.setTranDate(txt_trn_date.getText());
			else {
				
				JOptionPane.showMessageDialog(null, "please enter the transaction  date  " );
				return null;
			}
	
		//	deptran_obj.setTranMode(combo_trn_type.getSelectedItem());
	
			deptran_obj.setInterestAmt(new Double(txt_dep_amt.getText()));
	
			
			Collection  en = table.values();
			int sum = 0;
			Iterator it = en.iterator();
			
			while (it.hasNext()) {
				sum += new Integer(it.next().toString()).intValue();
				
			}
			
			System.out.println( sum + " rd balance is -------------------");
			deptran_obj.setAccNo(0);
			deptran_obj.setRDBalance((sum*100));
			
			if ( !txt_rd_bal.getText().toString().equalsIgnoreCase("") && txt_rd_bal.getText().toString().trim().length() > 0 )
				deptran_obj.setRDBalance(new Double(txt_rd_bal.getText().toString()).doubleValue());
			else {
				
				JOptionPane.showMessageDialog(null, "please enter the Balance  " );
			}
			
			if ( !txt_trn_date.getText().toString().equalsIgnoreCase("") && txt_trn_date.getText().toString().trim().length() > 0 )
				
				deptran_obj.setTranDate(txt_trn_date.getText());
			
		
			else {
				
				JOptionPane.showMessageDialog(null, "please enter the Transaction..... date  " );
				return null;
			}
			
		
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
			
			deptran_obj.setTranDate(MainScreen.getSysDate());
			//deptran_obj.setDe_datetime(MainScreen.getSysDate()+MainScreen.getSysTime());
			System.out.println("The time "+MainScreen.getSysDate()+" "+MainScreen.getSysTime());
		//	deptran_obj.setDe_tml(MainScreen.head_panel.getTml());
			System.out.println("tMl is"+MainScreen.head_panel.getTml());
			//deptran_obj.setde(MainScreen.head_panel.getUid());
			System.out.println("UID is"+MainScreen.head_panel.getUid());
			
			
			
			return deptran_obj;
			
		}
		
	
		public void clearForm()	
		{	
	
			txt_cum_int.setText("0");
			txt_accno.setEnabled(true);
			txt_dep_amt.setText("");
			txt_int_amt.setText("");	
			//txt_introducer_no.setText("");	
			txt_customerid.setText("");
		//	txt_introducer_no.setText("");	
			txt_int_date.setText("");
			//txt_close_date.setText("");
			txt_rd_bal.setText("");
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
				rd_entry.clear();
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
	
		public static DepTranEntry getInstance(int i) {
			
	    	if ( i == 0)
	    		return	trn_entry = new DepTranEntry();
	    	
	    	if ( trn_entry == null )
	    	    return	trn_entry = new DepTranEntry();
	    	 
	    	return trn_entry;
	}
	
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
			if ( arg0.getSource() == txt_balance ) {
				
				Collection  en = (rd_entry.getCertificateNo()). values();
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
	
						if ( (shobj.getShareStatus()).equalsIgnoreCase("A") )
							combo_share_status.setSelectedIndex(0);
						else 
							combo_share_status.setSelectedIndex(1);
	
	
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
						
						
						rd_entry.setCertificateNo(shobj.getCertificate());
						txt_balance.setText( new Double(shobj.getBalance()).toString());
						button_submit.setEnabled(false);
					}
				}
				
			}
			
			else if ( e.getSource()== txt_balance && (e.getOppositeComponent() == button_submit || e.getOppositeComponent() == button_update)) {
				
				if ( !txt_balance.getText().toString().equalsIgnoreCase("") && txt_balance.getText().length() > 0) {
					
					
					Collection  en = ( rd_entry.getCertificateNo()). values();
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







*/