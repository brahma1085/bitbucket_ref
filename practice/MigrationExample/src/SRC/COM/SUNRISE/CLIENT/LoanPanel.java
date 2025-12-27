package SRC.COM.SUNRISE.CLIENT;


import java.awt.Color;
import java.awt.Dimension;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sun.org.apache.bcel.internal.generic.LMUL;

import SRC.COM.SUNRISE.CLIENT.MainScreen;

import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.CLIENT.DepositPanel.QueryNamePanel;
import SRC.COM.SUNRISE.CLIENT.SBCAPanel.CIDDetails;
import SRC.COM.SUNRISE.SERVER.GetDBConnection;
import SRC.COM.SUNRISE.SERVER.StoreDeposit;
import SRC.COM.SUNRISE.SERVER.StoreLoan;
import SRC.COM.SUNRISE.SERVER.getCustDetails;
import SRC.COM.SUNRISE.UTILITY.Colors;
import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.FloatNumListener;
import SRC.COM.SUNRISE.UTILITY.NumListener;
import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotFound;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanTransactionObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;
import java.sql.*;


public class LoanPanel extends JPanel implements FocusListener,KeyListener,ItemListener,Observer,ActionListener
{

	GridBagConstraints gbc;
	static LoanPanel loans;
	
	JLabel lbl_pay_acctype,lbl_pay_accno;
	JTextField txt_ac_no ,txt_cid,txt_coborrowers, txt_loanappln_dt,txt_surities,txt_req_amt,txt_td_accno;
	JTextField txt_sanction_dt,txt_sanction_amt,txt_int_rate ,txt_shareno,txt_inst_amt,txt_trndt,txt_payaccno,txt_disbleft,txt_int_paid_dt;
	JTextField txt_no_inst,txt_intupto_dt,txt_lst_trn_dt,txt_due_date;
	
	
	JComboBox combo_ac_type,combo_paymode,combo_inttype;
	JComboBox combo_pay_acctype,combo_ln_ac_type,combo_detail,combo_td_acctype;
	
	// New Edition for Ramnagar
	JComboBox combo_purpose_Loan,priority,Weaker_Section,sc_st_women,details;
	JTextField insurance_particulars,surity_1,surity_2,surity_3,surity_4,surity_5;
	// for Gold
	JLabel lbl_description,lbl_gross_wt,lbl_net_wt,lbl_rate,lbl_value,lbl_surity_1,lbl_surity_2,lbl_surity_3,lbl_surity_4,lbl_surity_5;
	JTextField description,gross_wt,net_wt,rate,value;
	// for surity
	int temp;
	
	
	DateListener dtlist = new DateListener();
	FloatNumListener floatlis = new FloatNumListener();
	
	JPanel panel_tabbed;
	JButton btn_submit, btn_update, btn_clear;
	
	Personal panel_person ;
	Nominee nominee;
	JointHolder joint ;
	LoanMasterObject lmobj=null,loanobj=null;
	JScrollPane[] jsp_detail = new JScrollPane[4];
	
	CIDPanel cid_panel;
	CIDDetails help_cid;
	
	Object data[][];
	
	QueryNamePanel query;
	
	TreeMap map_names ;
	
	StoreLoan store_loaninfo;
	
	private LoanPanel() {
		
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
		addComponent( txt_ac_no = new JTextField() , this, 2, 4, 0.0, 0.0, 4, 1);
		txt_ac_no.addFocusListener(this);
		NumListener numlist = new NumListener();
		txt_ac_no.addKeyListener( numlist );
		txt_ac_no.addFocusListener( this );
		
		
		addComponent(new JLabel("Cid"), this, 3, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_cid = new JTextField() , this, 3, 4, 0.0, 0.0, 4, 1);
		txt_cid.addFocusListener(this);
		txt_cid.addKeyListener(this);
		
		
		addComponent(new JLabel("Co Borrowers(Enter number of Coborrowers)"), this, 4, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_coborrowers = new JTextField() , this, 4, 4, 0.0, 0.0, 4, 1);
		txt_coborrowers.addKeyListener(numlist);
		
		
		addComponent(new JLabel("Surities(Enter number of Surities)"), this,  5, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_surities = new JTextField() , this,  5, 4, 0.0, 0.0, 4, 1);
		txt_surities.setText("0");
		txt_surities.addKeyListener(numlist);
		
		addComponent(new JLabel("Loan Application Date"), this, 6, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_loanappln_dt = new JTextField() , this, 6, 4, 0.0, 0.0, 4, 1);
		txt_loanappln_dt.addKeyListener(dtlist);
		
		addComponent(new JLabel("Limit"), this, 7, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_req_amt = new JTextField() , this, 7, 4, 0.0, 0.0, 4, 1);
		txt_req_amt.addKeyListener(floatlis);
		txt_req_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		
		addComponent(new JLabel("Share Number"), this,  8, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_shareno = new JTextField() , this,  8, 4, 0.0, 0.0, 4, 1);
		txt_shareno.addKeyListener(numlist);
		txt_shareno.setHorizontalAlignment(SwingConstants.RIGHT);
		
		addComponent(new JLabel("Deposit Acc Type"), this,  9, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_td_acctype = new JComboBox() , this,  9, 4, 0.0, 0.0, 4, 1);
		combo_td_acctype.addItem("Select");
		combo_td_acctype.addItem("FD");
		combo_td_acctype.addItem("RD");
		combo_td_acctype.addItem("CA");
		combo_td_acctype.addItem("SB");
		
		addComponent(new JLabel("Deposit Acc No"), this, 10, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_td_accno = new JTextField() , this, 10, 4, 0.0, 0.0, 4, 1);
		txt_td_accno.addKeyListener(numlist);
		
		addComponent(new JLabel("Interest Rate"), this, 11, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_int_rate = new JTextField() , this, 11, 4, 0.0, 0.0, 4, 1);
		txt_int_rate.addKeyListener(floatlis);
		
		addComponent(new JLabel("Sanctioned Date"), this,  12, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_sanction_dt = new JTextField() , this, 12, 4, 0.0, 0.0, 4, 1 );
		txt_sanction_dt.addKeyListener(dtlist);
		
		addComponent(new JLabel("Sanctioned Amount"), this, 13, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_sanction_amt = new JTextField() , this, 13, 4, 0.0, 0.0, 4, 1);
		txt_sanction_amt.addKeyListener(floatlis);
		
		addComponent(new JLabel("Disb Left"), this, 14, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_disbleft = new JTextField() , this, 14, 4, 0.0, 0.0, 4, 1);
		txt_disbleft.addKeyListener(floatlis);
		
		addComponent(new JLabel("Number Of Installments(in Months)"), this, 15, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_no_inst = new JTextField() , this, 15, 4, 0.0, 0.0, 4, 1);
		txt_no_inst.addKeyListener(numlist);
		
		addComponent(new JLabel("Interest Type"), this, 16, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_inttype = new JComboBox() , this, 16, 4, 0.0, 0.0, 4, 1);
		combo_inttype.addItem("EMI");
		combo_inttype.addItem("Reducing Balance");
		
		addComponent(new JLabel("Installment Amount"), this, 17, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_inst_amt = new JTextField() , this, 17, 4, 0.0, 0.0, 4, 1);
		txt_inst_amt.addKeyListener(floatlis);
		
		/*addComponent(new JLabel("Interest upto Date "), this, 17, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_intupto_dt = new JTextField() , this, 17, 4, 0.0, 0.0, 4, 1);
		
		addComponent(new JLabel("Last Transaction Date"), this, 18, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_lst_trn_dt = new JTextField() , this, 18, 4, 0.0, 0.0, 4, 1);
		*/
		
		addComponent(new JLabel("Due Date"), this, 19, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_due_date = new JTextField() , this, 19, 4, 0.0, 0.0, 4, 1);
		txt_due_date.addKeyListener(dtlist);
		
		addComponent(new JLabel("Pay Mode"), this, 20, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_paymode = new JComboBox() , this, 20, 4, 0.0, 0.0, 4, 1);
		combo_paymode.addItem("Cash");
		combo_paymode.addItem("Transfer");
		combo_paymode.addItemListener(this);
		
		addComponent(lbl_pay_acctype = new JLabel("Pay Acc Type"), this, 21, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_pay_acctype = new JComboBox() , this, 21, 4, 0.0, 0.0, 4, 1);
		combo_pay_acctype.addItem("SB");
		combo_pay_acctype.addItem("CA");
		lbl_pay_acctype.setVisible(false);
		combo_pay_acctype.setVisible(false);
		
		
		/*addComponent(new JLabel("Ln ac type "), this, 21, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_ln_ac_type = new JComboBox() , this, 21, 4, 0.0, 0.0, 4, 1);
		combo_ln_ac_type.addItem("SB");
		combo_ln_ac_type.addItem("CA");*/
		
		addComponent(lbl_pay_accno = new JLabel("Pay Acc No."), this, 22, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_payaccno = new JTextField() , this, 22, 4, 0.0, 0.0, 4, 1);
		txt_payaccno.addKeyListener(numlist);
		txt_payaccno.setVisible(false);
		lbl_pay_accno.setVisible(false);
		// for Ramnagar
		
		
		addComponent(new JLabel("Purpose Of Loan"),this,23,1,0.0,0.0,3,1);
		try{
		addComponent(combo_purpose_Loan=new JComboBox(),this,23,4,0.0,0.0,4,1);
		store_loaninfo = new StoreLoan();
		ResultSet rs=store_loaninfo.LoanPurposes();
		combo_purpose_Loan.addItem("SELECT");
		while(rs.next())
			combo_purpose_Loan.addItem(rs.getString("pps_desc"));	
           }
	     catch(Exception e){e.printStackTrace();}
	     combo_purpose_Loan.addItemListener(this);
	     
	    /*
	     addComponent(new JLabel("Insurance Particulars"),this,28,1,0.0,0.0,3,1);
	     addComponent(insurance_particulars=new JTextField(),this,28,4,0.0,0.0,4,1);
	     insurance_particulars.addFocusListener(this);
	     insurance_particulars.addKeyListener(this);
	    */
	     
	     
	     addComponent(new JLabel("Priority"),this,29,1,0.0,0.0,3,1);
	     addComponent(priority=new JComboBox(),this,29,4,0.0,0.0,4,1);
	     priority.addItem("SELECT");
	     try{
	    ResultSet rs1=store_loaninfo.getting_priority();
	     while(rs1.next())
	     {
	    	 System.out.println(rs1.getString(1));
	    priority.addItem(rs1.getString(1));
	    }
	     }
	     catch(Exception e){e.printStackTrace();}
		
	     
	     addComponent(new JLabel("Weaker Section"),this,30,1,0.0,0.0,3,1);
	     addComponent(Weaker_Section=new JComboBox(),this,30,4,0.0,0.0,4,1);
	     Weaker_Section.addItem("SELECT");
	     Weaker_Section.addItem("YES");
	     Weaker_Section.addItem("NO");
	     Weaker_Section.addItemListener(this);
	     
	     addComponent(new JLabel("SC/ST/WOMEN"),this,31,1,0.0,0.0,3,1);
	     addComponent(sc_st_women=new JComboBox(),this,31,4,0.0,0.0,4,1);
	     sc_st_women.addItem("SELECT");
	     sc_st_women.addItem("SC");
	     sc_st_women.addItem("ST");
	     sc_st_women.addItem("WOMEN");
	     sc_st_women.addItemListener(this);
	     
	     
	     
	     addComponent(new JLabel("Details "),this,32,1,0.0,0.0,3,1);
	     addComponent(details=new JComboBox(),this,32,4,0.0,0.0,4,1);
	     details.addItem("SELECT");
	     details.addItem("GOLD");
         details.addItem("SURITY");
	     details.addItemListener(this);
	     
	     
	     addComponent(lbl_description=new JLabel("Description(gold)"),this,33,1,0.0,0.0,3,1);
	     addComponent(description=new JTextField(),this,33,4,0.0,0.0,4,1);
	     lbl_description.setVisible(false);
	     description.setVisible(false);
	     
	     addComponent(lbl_gross_wt=new JLabel("Gross weight(Mg)"),this,34,1,0.0,0.0,3,1);
	     addComponent(gross_wt=new JTextField(),this,34,4,0.0,0.0,4,1);
	     lbl_gross_wt.setVisible(false);
	     gross_wt.setVisible(false);
	     
	     addComponent(lbl_net_wt=new JLabel("Net Weight (Mg)"),this,35,1,0.0,0.0,3,1);
	     addComponent(net_wt=new JTextField(),this,35,4,0.0,0.0,4,1);
	     lbl_net_wt.setVisible(false);
	     net_wt.setVisible(false);
	     
	     addComponent(lbl_rate=new JLabel("Rate"),this,36,1,0.0,0.0,3,1);
	     addComponent(rate=new JTextField(),this,36,4,0.0,0.0,4,1);
	     lbl_rate.setVisible(false);
	     rate.setVisible(false);
	     
	     addComponent(lbl_value=new JLabel("Value"),this,37,1,0.0,0.0,3,1);
	     addComponent(value=new JTextField(),this,37,4,0.0,0.0,4,1);
	     lbl_value.setVisible(false);
	     value.setVisible(false);
	    
	     addComponent(lbl_surity_1=new JLabel("SURITY 1 (share ac. no.)"),this,33,1,0.0,0.0,3,1);
	     addComponent(surity_1=new JTextField(),this,33,4,0.0,0.0,4,1);
	     lbl_surity_1.setVisible(false);
	     surity_1.setVisible(false);
	     
	     
	     addComponent(lbl_surity_2=new JLabel("SURITY 2(share ac. no.)"),this,34,1,0.0,0.0,3,1);
	     addComponent(surity_2=new JTextField(),this,34,4,0.0,0.0,4,1);
	     lbl_surity_2.setVisible(false);
	     surity_2.setVisible(false);
	     
	     addComponent(lbl_surity_3=new JLabel("SURITY 3(share ac. no.)"),this,35,1,0.0,0.0,3,1);
	     addComponent(surity_3=new JTextField(),this,35,4,0.0,0.0,4,1);
	     lbl_surity_3.setVisible(false);
	     surity_3.setVisible(false);
	     
	     addComponent(lbl_surity_4=new JLabel("SURITY 4(share ac. no.)"),this,36,1,0.0,0.0,3,1);
	     addComponent(surity_4=new JTextField(),this,36,4,0.0,0.0,4,1);
	     lbl_surity_4.setVisible(false);
	     surity_4.setVisible(false);
	     
	     addComponent(lbl_surity_5=new JLabel("SURITY 5(share ac. no.)"),this,37,1,0.0,0.0,3,1);
	     addComponent(surity_5=new JTextField(),this,37,4,0.0,0.0,4,1);
	     lbl_surity_5.setVisible(false);
	     surity_5.setVisible(false);
	     
	     
	     /*addComponent(new JLabel("Transaction Date"), this, 24, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_trndt = new JTextField() , this, 24, 4, 0.0, 0.0, 4, 1);
		txt_trndt.addKeyListener(dtlist);
		txt_array[9] = txt_trndt;
		
		addComponent(new JLabel("Transaction Amount"), this, 25, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_int_paid_dt = new JTextField() , this, 25, 4, 0.0, 0.0, 4, 1);
		txt_int_paid_dt.addKeyListener(dtlist);
		txt_array[11] = txt_int_paid_dt;
		
		addComponent(new JLabel("Appl Detail "), this, 26, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_detail = new JComboBox() , this, 26, 4, 0.0, 0.0, 4, 1);
		combo_detail.addItem("Personal");
		combo_detail.addItem("Joint Holder");
		combo_detail.addItem("Nominee");

		combo_detail.addItemListener(this);*/

		
		panel_tabbed = new JPanel();
		panel_tabbed.setBackground(new Color(232,255,238));
		panel_tabbed.setBorder(BorderFactory.createEtchedBorder());
		panel_tabbed.setLayout(new GridBagLayout());
		
		for (int i= 0; i< 28; i++)
			addComponent(new JLabel("      "), panel_tabbed, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel("  "), panel_tabbed, 24, 29, 1.0, 1.0 , 1, 1 );
		
		addComponent(panel_tabbed, this, 2, 10 ,0.0, 0.0, 28, 26);
		
		panel_person = new Personal();
		jsp_detail[0] = new JScrollPane(panel_person,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[0], panel_tabbed, 1, 0, 0.0, 0.0, 20, 22 );
		
		jsp_detail[1] = new JScrollPane(joint =new JointHolder(),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[1], panel_tabbed, 1, 0, 0.0, 0.0, 20, 22);
		jsp_detail[1].setVisible(false);
		
		jsp_detail[2] = new JScrollPane(nominee = new Nominee(btn_submit),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[2], panel_tabbed, 1, 0, 0.0, 0.0, 20, 22 );
		jsp_detail[2].setVisible(false);
		
		jsp_detail[3] = new JScrollPane(cid_panel = new CIDPanel(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		cid_panel.subObserver.addObserver(this);
		addComponent( jsp_detail[3], panel_tabbed, 1, 0, 0.0, 0.0, 28, 22 );
		jsp_detail[3].setVisible(false);
		
		btn_submit = new JButton("Submit");
		addComponent( btn_submit, this, 34, 8, 0.0, 0.0, 4, 1 );
		btn_submit.addActionListener(this);
		
		btn_update = new JButton("Update");
		addComponent( btn_update, this, 34, 12, 0.0, 0.0, 4, 1 );
		btn_update.addActionListener(this);
		
		btn_clear = new JButton("Clear");
		addComponent( btn_clear, this, 34, 16, 0.0, 0.0, 4, 1 );
		btn_clear.addActionListener(this);
		//clearScreen();
		
		showJSP(0);
		
		map_names =  getCustDetails.getInstance().getCustomerNames();
		
		store_loaninfo = new StoreLoan();
	}
	
	 public static LoanPanel getInstance(int i) {
			
	    	if ( i == 0)
	    		return	loans = new LoanPanel();
	    	
	    	if ( loans == null )
	    	    return	loans = new LoanPanel();
	    	 
	    	return loans;
		}
	 
	 public void showJSP(int i) {
			
			
			for ( int j =0; j< jsp_detail.length; j++)
				jsp_detail[j].setVisible(false);
			
			if(i == 3) {
				if( txt_cid.getText().toString().length() > 0 && new Integer(txt_cid.getText().toString()).intValue() > 0)
				cid_panel.setPanelData(new Integer(txt_cid.getText().toString()).intValue());
			}
				
			
			jsp_detail[i].setVisible(true);
			panel_tabbed.revalidate();
		}
	 
	 public void clearScreen() {
			
		    combo_ac_type.setSelectedIndex(0);
		    txt_ac_no.setText("");
		    txt_cid.setText("");
		    txt_coborrowers.setText("");
		    txt_surities.setText("");
		    txt_loanappln_dt.setText("");
		    txt_req_amt.setText("");
		    txt_shareno.setText("");
		    combo_td_acctype.setSelectedIndex(0);
		    txt_td_accno.setText("");
		    txt_int_rate.setText("");
		    txt_sanction_dt.setText("");
		    txt_sanction_amt.setText("");
		    txt_disbleft.setText("");
		    txt_no_inst.setText("");
		    combo_inttype.setSelectedIndex(0);
			txt_inst_amt.setText("");
			txt_due_date.setText("");
			combo_paymode.setSelectedIndex(0);
			combo_pay_acctype.setSelectedIndex(0);
			txt_payaccno.setText("");
			
			btn_submit.setEnabled(true);
			btn_update.setEnabled(true);
			nominee.clearNominee();
			panel_person.clear();
			joint.clearJointHolder();
			combo_detail.setSelectedIndex(0);
			showJSP(0);
			
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

		}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent fe) {
		
		lmobj = new LoanMasterObject();
		//LoanTransactionObject ltnobj = new LoanTransactionObject();
		switch (combo_ac_type.getSelectedIndex()) {
		case 0:
			   lmobj.setAccType("1010014");//Surety Loan
		       break;
		case 1:
			   lmobj.setAccType("1010006");//Vehicle Loan
		       break;
		case 2:
			   lmobj.setAccType("1010017");//Staff Vehicle Loan
		       break;
		case 3:
			   lmobj.setAccType("1010015");//Personal Loan
		       break;
		case 4:
			   lmobj.setAccType("1010003");//Housing Loan
		       break;
		case 5:
			   lmobj.setAccType("1010018");//NSC/KVP Loan
		       break;
		case 6: 
			   lmobj.setAccType("1010019"); //BLCC Loan 
		       break;
		case 7: 
			   lmobj.setAccType("1015001"); //OverDraft Loan 
		       break;
		case 8: 
			   lmobj.setAccType("1014001"); //Cash Credit Loan 
		       break;
		case 9:
			   lmobj.setAccType("1010020"); //TMCB C/C Loan 
		       break;
		case 10:
			   lmobj.setAccType("1010021"); //Decretal Overdraft Loan 
		       break;
		case 11:
			   lmobj.setAccType("1010022"); //Decretal Cash Credit Loan 
		       break;
		}
		if ( fe.getSource() == txt_cid  && fe.getOppositeComponent() == txt_coborrowers) {


			if ( txt_cid.getText().toString().trim().length() > 0 &&  new Integer(txt_cid.getText().toString().trim()).intValue() >0 ) {

				panel_person.setPanelData(new Integer(txt_cid.getText().toString().trim()));
			}
			else 
				showJSP(3);

			//combo_details.setSelectedIndex(0);
		}else if(txt_ac_no.getText().toString().trim().length() > 0 &&  new Integer(txt_ac_no.getText().toString().trim()).intValue() >0){
			System.out.println(lmobj.getAccType());
			loanobj = store_loaninfo.getLoanMaster(lmobj.getAccType(),Integer.parseInt(txt_ac_no.getText().trim()));
			txt_cid.requestFocus();
			if(loanobj!=null){
				
			}
		}

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

	public void itemStateChanged(ItemEvent ie) {
		if(ie.getSource()==combo_paymode && combo_paymode.getSelectedIndex()==1 ){
			lbl_pay_accno.setVisible(true);
			lbl_pay_acctype.setVisible(true);
			combo_pay_acctype.setVisible(true);
			txt_payaccno.setVisible(true);
			
		}
		if(ie.getSource()==details )
		{
			if(details.getSelectedIndex()==1)
		
		    {
				lbl_surity_1.setVisible(false);
			     surity_1.setVisible(false);
				lbl_surity_2.setVisible(false);
				 surity_2.setVisible(false);
				lbl_surity_3.setVisible(false);
				 surity_3.setVisible(false);
				lbl_surity_4.setVisible(false);
				 surity_4.setVisible(false);
				lbl_surity_5.setVisible(false);
				 surity_5.setVisible(false);
								
				
			 lbl_description.setVisible(true);
		     description.setVisible(true);
		     lbl_gross_wt.setVisible(true);
		     gross_wt.setVisible(true);
		     lbl_net_wt.setVisible(true);
		     net_wt.setVisible(true);
		     lbl_rate.setVisible(true);
		     rate.setVisible(true);
		     lbl_value.setVisible(true);
		     value.setVisible(true);
	        }
			if(details.getSelectedIndex()==2)
			{
				lbl_description.setVisible(false);
			     description.setVisible(false);
			     lbl_gross_wt.setVisible(false);
			     gross_wt.setVisible(false);
			     lbl_net_wt.setVisible(false);
			     net_wt.setVisible(false);
			     lbl_rate.setVisible(false);
			     rate.setVisible(false);
			     lbl_value.setVisible(false);
			     value.setVisible(false);
		        
				
			     temp=Integer.parseInt(txt_surities.getText());
			     System.out.println(temp+"temperatureeeeeeeeeeee");
			     if(temp==1)
			     {
			     lbl_surity_1.setVisible(true);
			     surity_1.setVisible(true);
			     }
			     else if(temp==2)
			     {
			    	 lbl_surity_1.setVisible(true);
				     surity_1.setVisible(true);
				     lbl_surity_2.setVisible(true);
				     surity_2.setVisible(true);
			     }
			     else if(temp==3)
			     {
			    	 lbl_surity_1.setVisible(true);
				     surity_1.setVisible(true);
				     lbl_surity_2.setVisible(true);
				     surity_2.setVisible(true); 
				     lbl_surity_3.setVisible(true);
				     surity_3.setVisible(true);	 
			     }	 
			     else if(temp==4) 
			     {
			    	 lbl_surity_1.setVisible(true);
				     surity_1.setVisible(true);
				     lbl_surity_2.setVisible(true);
				     surity_2.setVisible(true); 
				     lbl_surity_3.setVisible(true);
				     surity_3.setVisible(true);	
				     lbl_surity_4.setVisible(true);
				     surity_4.setVisible(true);	 
			     }
			     
			     else if(temp==5) 
			     {
			    	 lbl_surity_1.setVisible(true);
				     surity_1.setVisible(true);
				     lbl_surity_2.setVisible(true);
				     surity_2.setVisible(true); 
				     lbl_surity_3.setVisible(true);
				     surity_3.setVisible(true);	
				     lbl_surity_4.setVisible(true);
				     surity_4.setVisible(true);	 
				     lbl_surity_5.setVisible(true);
				     surity_5.setVisible(true);	 
			     }
			     
			     
			     
			     
			}
		}
		
		
	}
		
			
			
		
	

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==btn_submit){
			/*data = null;
			double amtsanc=Double.parseDouble(txt_disbleft.getText());
			double irate =Double.parseDouble(txt_int_rate.getText());
			int installments = Integer.parseInt(txt_no_inst.getText());
			if(combo_inttype.getSelectedIndex()==0)
				data = calculateReducingInterest(amtsanc,irate,installments);
			else
				data = calculateEMIInterest(amtsanc,irate,installments);
			
			*/
			
			lmobj = new LoanMasterObject();
			//LoanTransactionObject ltnobj = new LoanTransactionObject();
			switch (combo_ac_type.getSelectedIndex()) {
			case 0:
				   lmobj.setAccType("1010014");//Surety Loan
			       break;
			case 1:
				   lmobj.setAccType("1010006");//Vehicle Loan
			       break;
			case 2:
				   lmobj.setAccType("1010017");//Staff Vehicle Loan
			       break;
			case 3:
				   lmobj.setAccType("1010015");//Personal Loan
			       break;
			case 4:
				   lmobj.setAccType("1010003");//Housing Loan
			       break;
			case 5:
				   lmobj.setAccType("1010018");//NSC/KVP Loan
			       break;
			case 6: 
				   lmobj.setAccType("1010019"); //BLCC Loan 
			       break;
			case 7: 
				   lmobj.setAccType("1015001"); //OverDraft Loan 
			       break;
			case 8: 
				   lmobj.setAccType("1014001"); //Cash Credit Loan 
			       break;
			case 9:
				   lmobj.setAccType("1010020"); //TMCB C/C Loan 
			       break;
			case 10:
				   lmobj.setAccType("1010021"); //Decretal Overdraft Loan 
			       break;
			case 11:
				   lmobj.setAccType("1010022"); //Decretal Cash Credit Loan 
			       break;
			}
			
			lmobj.setAccNo(new Integer(txt_ac_no.getText()));
			lmobj.setCustomerId(new Integer( txt_cid.getText().toString().trim()));
			lmobj.setMailingAddress(panel_person.getAddrType());
			lmobj.setName(panel_person.getName());
			lmobj.setNoOfCoBorrowers(new Integer(txt_coborrowers.getText()));
			lmobj.setNoOfSurities(new Integer(txt_surities.getText()));
			lmobj.setApplicationDate(txt_loanappln_dt.getText());
			lmobj.setRequiredAmount(new Double(txt_req_amt.getText()));
			lmobj.setShareAccNo(new Integer(txt_shareno.getText()));
			lmobj.setShareAccType("1001001");
			
			//Depsoit Module code
			if(combo_td_acctype.getSelectedIndex()>0){
			switch (combo_td_acctype.getSelectedIndex()) {
			case 1:
				lmobj.setDepositAccType("1003001");//FD	   
				break;
			case 2:
				lmobj.setDepositAccType("1004001");//RD	   
				break;
			case 3:
				lmobj.setDepositAccType("1007001");//CA	   
				break;
			case 4:
				lmobj.setDepositAccType("1002001");//SB	   
				break;
			}
	      }else
	    	  lmobj.setDepositAccType(null);
			
			
			lmobj.setDepositAccNo(new Integer(txt_td_accno.getText()));
			lmobj.setInterestRate(new Double(txt_int_rate.getText()));
			lmobj.setSexInd(panel_person.getSex());
			lmobj.setSanctionDate(txt_sanction_dt.getText());
			lmobj.setSanctionedAmount(new Double(txt_sanction_amt.getText()));
			lmobj.setNoOfInstallments(new Integer(txt_no_inst.getText()));
			
			if(combo_inttype.getSelectedIndex()==0)
				lmobj.setInterestType(0);
			else
				lmobj.setInterestRateType(1);
				
			lmobj.setInstallmentAmt(new Double(txt_inst_amt.getText()));
			//lmobj.setInterestUpto(txt_intupto_dt.getText());
			//lmobj.setLastTrndt(txt_lst_trn_dt.getText());
			lmobj.setClosedt(txt_due_date.getText());
			
			if(combo_paymode.getSelectedIndex()==0){//Cash
				lmobj.setPayMode("C");
				lmobj.setPaymentAcctype("null");
				lmobj.setPaymentAccno(0);
				
			}else if(combo_paymode.getSelectedIndex()==1){  //Transfer
				lmobj.setPayMode("T");
				lbl_pay_accno.setVisible(true);
				lbl_pay_acctype.setVisible(true);
				combo_pay_acctype.setVisible(true);
				txt_payaccno.setVisible(true);
				
       			if ( combo_pay_acctype.getSelectedIndex() == 0){
       				 lmobj.setPaymentAccno(new Integer(txt_payaccno.getText()));
				     lmobj.setPaymentAcctype("1002001");
       	     		}	
			    else{ 
			    	lmobj.setPaymentAcctype("1007001");
                    lmobj.setPaymentAccno(new Integer(txt_payaccno.getText()));
			        }	
	     	}
			
			lmobj.setDisbursementLeft(Double.parseDouble((txt_disbleft.getText())));


			//LOanTran Entries
			
			//ltnobj.setTransactionDate(txt_trndt.getText());
			lmobj.setPurposeCode((combo_purpose_Loan.getSelectedIndex())+1);	   
					
			lmobj.setPrior((priority.getSelectedIndex())+1);
			
			
			
			if(Weaker_Section.getSelectedIndex()>0){
				switch (Weaker_Section.getSelectedIndex()) {
				case 1:
					lmobj.setDepositAccType("Y");//Yes	   
					break;
				case 2:
					lmobj.setDepositAccType("N");//No	   
					break;
				
				}
		      }else
		    	  lmobj.setDepositAccType("Not set");
		
			//for inserting into Gold and surity
			Boolean temp_bool=false;
			
			try{
			Connection conn=GetDBConnection.getConnection();
			PreparedStatement ps1 = conn.prepareStatement("insert into GoldDetMaster values(?,?,?,?,?,?,?,?,?,?)");
			ps1.setInt(1,lmobj.getAccNo());
			ps1.setString(2,lmobj.getAccType());
			ps1.setInt(3,1);
			ps1.setInt(4,0);
			ps1.setString(5,description.getText());
			ps1.setString(6,gross_wt.getText());
			ps1.setString(7,net_wt.getText());
			ps1.setString(8,value.getText());
			ps1.setString(9,"00");
			ps1.setString(10,"00");
			int temp=ps1.executeUpdate();
			if (temp>0);
			 temp_bool=true;
			for(int i=0;i<Integer.parseInt(txt_surities.getText());i++)
			{
				String var=null;
				if(i==0)
					var=surity_1.getText();
			if(i==1)
				var=surity_2.getText();
			if(i==2)
				var=surity_3.getText();
			if(i==3)
				var=surity_4.getText();
			if(i==4)
				var=surity_5.getText();
			 PreparedStatement ps2 = conn.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
			ps2.setString(1,lmobj.getAccType());
			ps2.setInt(2,lmobj.getAccNo());
			ps2.setString(3,"S");
			ps2.setString(4,"1001001");
			ps2.setString(5,var);
			ps2.setString(6,"N/A");
			int tmpp=ps2.executeUpdate();
			}
			}
			catch(Exception e){e.printStackTrace();}
			
			if((store_loaninfo.storeLoanMaster(lmobj))&&temp_bool){
				JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
				clearScreen();
			}else 
				JOptionPane.showMessageDialog(null,"Sorry! Not inserted");
		}
		
		
		
		

		
	}
	
	Object [][] calculateReducingInterest(double prn,double rate,int period)
	{
		Object data[][]=null;
		data = new Object[period+1][6];
		try{
		String str1=null,str=null;
		GregorianCalendar g=null;
		
		
		//g=new GregorianCalendar();	
		str1=Validation.addNoOfMonths(MainScreen.head_panel.getSysDate(),Integer.parseInt(txt_no_inst.getText().trim()));
		StringTokenizer st = new StringTokenizer(str1,"/");
		int int_day = Integer.parseInt(st.nextToken());
		int int_month = Integer.parseInt(st.nextToken())-1;
		int int_year = Integer.parseInt(st.nextToken());
		g = new GregorianCalendar(int_year,int_month,int_day);
		
		str1 = MainScreen.head_panel.getSysDate();
		
		double sumi=0.0,sumt=0.0,sump=0.0;
		double install=Math.round(prn/period);
		double bal=Math.round(prn-(period*install));

		int i=0;
		for(i=0;i<period;i++)
		{
			g.add(Calendar.MONTH,1);
			str=Validation.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
			double interest=Math.round((prn*rate*(Validation.dayCompare(str1,str)))/36500);
			str1=str;
			if(i==(period-1))
				install=install+bal;

			prn=prn-install;


			data[i][0]=String.valueOf(i+1);
			data[i][1]=str;
			data[i][2]=String.valueOf(install);
			data[i][3]=String.valueOf(interest);
			data[i][4]=String.valueOf(interest+install);
			data[i][5]=String.valueOf(prn);	

			sump += install;
			sumi+=interest;
			sumt+=(interest+install);

		}
		data[i][0]="";
		data[i][1]="";
		data[i][2]=String.valueOf(sump);
		data[i][3]=String.valueOf(sumi);
		data[i][4]=String.valueOf(sumt);
		data[i][5]="";	


		}catch(Exception ex){System.out.println(ex);}
		return data;
	}
	
	
	Object [][] calculateEMIInterest(double prn,double rate,int period)
	{
		
			
	    Object data[][]=null;
	   
	   
	    try{
	    	 double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0,sump=0.0;
	    	 data=new Object[period+1][6];
	   /* GregorianCalendar g=new GregorianCalendar();	
	    String str1=MainScreen.head.getSysDate();*/
	    String str1=Validation.addNoOfMonths(MainScreen.head_panel.getSysDate(),Integer.parseInt(txt_no_inst.getText().trim()));
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

	public class QueryNamePanel  implements KeyListener,FocusListener, WindowListener ,CaretListener {
		
		private JDialog frame_window;
		private JPanel panel;
		private JTable table;
		private JScrollPane jsp_table;
		private DefaultTableModel dtm_bank_details;
		JTextField txt_name;
		GridBagConstraints gbc;
		
		QueryNamePanel(){
			
			frame_window = new JDialog((JFrame)MainScreen.getInstance(),"Query on Name",true);
			
			
			panel = new JPanel(new GridBagLayout());
			gbc = new GridBagConstraints();
			
			frame_window.getContentPane().add(panel);
			
			dtm_bank_details = new DefaultTableModel()
			{
				public boolean isCellEditable(int row,int column)
				{
					  return false;
				}
			};
			
			
				dtm_bank_details.addColumn("Name");
				dtm_bank_details.addColumn("Cid");
				dtm_bank_details.addColumn("Address");
				
				table = new JTable(dtm_bank_details);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
				TableColumn[] col = new TableColumn[3];
				
				for ( int i = 0; i< 3; i++) {
					
					col[i] = table.getColumnModel().getColumn(i);
				}
				col[0].setPreferredWidth(200);
				col[1].setPreferredWidth(80);
				col[2].setPreferredWidth(300);
				
			jsp_table = new JScrollPane(table , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsp_table.setPreferredSize(new Dimension(200,290));
			jsp_table.setAutoscrolls(true);
			table.getTableHeader().setReorderingAllowed(false);
			table.getTableHeader().setBackground(Colors.setMainPanelColor());
			table.addKeyListener(this);
			
			Object[] obj = new Object[3];
			obj[0] ="";
			obj[1] = "";
			obj[2] = "";
			
			for (int i= 0; i< 15; i++)
				dtm_bank_details.addRow(obj);
			
			jsp_table.setVisible(true);
			jsp_table.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			
			txt_name = new JTextField(10);
			txt_name.addCaretListener(this);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridy=0;
			gbc.weightx=0.0;
			gbc.weighty=0.0;
			gbc.gridheight=1;
			gbc.gridwidth=1;
			
			for (int i=0; i< 25; i++) {
				
				gbc.gridx = i;
				panel.add(new JLabel("        "), gbc );
			}
			gbc.gridx = 0;
			for (int i=0; i< 25; i++) {
				
				gbc.gridy = i;
				panel.add(new JLabel("      "), gbc );
			}
			
				gbc.gridx=26;
				gbc.gridy=25;
				gbc.weightx=1.0;
				gbc.weighty=1.0;
				gbc.gridheight=1;
				gbc.gridwidth=1;	
				
				//panel.add(new JLabel("I"), gbc );		
				
				gbc.gridy = 2;
				gbc.gridx = 3;
				gbc.weightx=0.0;
				gbc.weighty=0.0;
				gbc.gridheight = 1;
				gbc.gridwidth = 11;
				
				panel.add( new JLabel("Name :"),gbc);
				
				gbc.gridy = 2;
				gbc.gridx = 6;
				gbc.gridwidth = 12;
				panel.add(txt_name , gbc);
				
				gbc.fill = GridBagConstraints.BOTH;
				
				gbc.gridy = 4;
				gbc.gridx = 3;
				gbc.gridheight = 18;
				gbc.gridwidth = 21;
				panel.add(jsp_table,gbc);
				
			frame_window.setBounds(270,180,600,450);
			frame_window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			
			frame_window.setVisible(true);
			
			
		}

		public void keyPressed(KeyEvent a ) {
			
			if ( a.getSource() == table ) {
				
				if ( a.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String str = new StringTokenizer(table.getValueAt(table.getSelectedRow() , 1).toString(),",").nextToken();
					txt_cid.setText(str) ;
				
					help_cid = null;
					frame_window.setVisible(false);
					//help_cid.dispose();
				}
			}
			
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

		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void caretUpdate(CaretEvent arg ) {

			if ( arg.getSource() == txt_name ) {
				
				dtm_bank_details.setRowCount(0);
				
				String entr = txt_name.getText().toString().toLowerCase();
				 
				Set ss = map_names.keySet();
				Iterator it = ss.iterator();
				Object[] obj = new Object[3];
				
				while ( it.hasNext())
				{
					
					
					
					Object s = it.next();
					
					if ( ((String)s).toLowerCase().startsWith(entr) ) {
					
						Object s1 = map_names.get(s);
						StringTokenizer str = new StringTokenizer(s1.toString(),",");
						obj[0] = s; 
						obj[1] = str.nextToken();
						obj[2] = str.nextToken();
						
						dtm_bank_details.addRow(obj);
						
					}
					
					
				}
				
			}
			
		}
		
	}
	
}	
	
	