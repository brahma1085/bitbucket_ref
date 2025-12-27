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
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

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

import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.CLIENT.SBCAPanel.CIDDetails;
import SRC.COM.SUNRISE.SERVER.StoreDeposit;
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
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;

public class RDDataEntry extends JPanel implements FocusListener,KeyListener,Observer,ItemListener,ActionListener{
	
	
		GridBagConstraints gbc;
		static RDDataEntry rd_de;
		
		JTextField txt_ac_no ,txt_cid,txt_deposit_dt, txt_matur_dt,txt_deposit_days,txt_deposit_amt,txt_matur_amt;
		JTextField txt_next_pay_dt,txt_intro_ac_no ,txt_intrest_rt,txt_rcvd_ac_no,txt_int_upto_dt,txt_ln_ac_no,txt_reciept,txt_int_paid_dt;
		JTextField txt_pay_ac_no;
		
		JTextField[] txt_array = new JTextField[10]; //{ txt_ac_no ,txt_cid, txt_deposit_dt, txt_matur_dt,txt_deposit_days,txt_deposit_amt,txt_matur_amt,txt_next_pay_dt ,txt_intrest_rt ,txt_int_upto_dt,txt_reciept,txt_int_paid_dt } ;
		String[] txt_names = { "Account No", "Cid","Deposit Date","Maturity Date","No of Installment","Installment Amount","Next pay date "," Reciept No","Interest Paid Date"};
		
		JComboBox combo_ac_type,combo_intro_ac_type,combo_rcvd_by,combo_rcvd_ac_type,combo_freq_mod,combo_pay_mode,combo_pay_ac_type;
		JComboBox combo_ln_avld,combo_ln_ac_type,combo_detail;
		
		
		
		DateListener dtlist = new DateListener();
		FloatNumListener floatlis = new FloatNumListener();
		
		JPanel panel_tabbed;
		JButton btn_submit, btn_update, btn_clear;
		
		Personal panel_person ;
		Nominee nominee;
		JointHolder joint ;
		JScrollPane[] jsp_detail = new JScrollPane[4];
		
		CIDPanel cid_panel;
		CIDDetails help_cid;
		
		QueryNamePanel query;
		
		TreeMap map_names ;
		
		StoreDeposit store_dep;
		
		private RDDataEntry() {
			
			
			setLayout(new GridBagLayout());
			gbc = new GridBagConstraints();
			
			gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(4,4,4,4);
		
		
			for (int i= 0; i< 37; i++)
				addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0 , 1, 1 );
			
			addComponent(new JLabel(" "), this, 30, 32, 1.0, 1.0 , 1, 1 );
			
			
			addComponent(new JLabel("Acc type"), this, 1, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_ac_type = new JComboBox() , this, 1, 4, 0.0, 0.0, 4, 1);
			combo_ac_type.addItem("RD");
			
			
			
			addComponent(new JLabel("Acc No"), this, 2, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_ac_no = new JTextField() , this, 2, 4, 0.0, 0.0, 4, 1);
			txt_ac_no.addFocusListener(this);
			NumListener numlist = new NumListener();
			txt_ac_no.addKeyListener( numlist );
			txt_ac_no.addFocusListener( this );
			txt_array[0] = txt_ac_no;
			
			addComponent(new JLabel("Cid"), this, 3, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_cid = new JTextField() , this, 3, 4, 0.0, 0.0, 4, 1);
			txt_cid.addFocusListener(this);
			txt_cid.addKeyListener(this);
			txt_array[1] = txt_cid;
			
			addComponent(new JLabel("Deposit Date"), this, 4, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_deposit_dt = new JTextField() , this, 4, 4, 0.0, 0.0, 4, 1);
			txt_deposit_dt.addKeyListener(dtlist);
			txt_array[2] = txt_deposit_dt;
			
			addComponent(new JLabel("No of Installment"), this,  5, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_deposit_days = new JTextField() , this,  5, 4, 0.0, 0.0, 4, 1);
			txt_array[4] = txt_deposit_days;
			txt_deposit_days.addFocusListener(this);
			
			
			
			addComponent(new JLabel("Maturity Date"), this, 6, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_matur_dt = new JTextField() , this, 6, 4, 0.0, 0.0, 4, 1);
			txt_matur_dt.addKeyListener(dtlist);
			txt_array[3] = txt_matur_dt;
			txt_matur_dt.setEnabled(false);
			
			addComponent(new JLabel("Installment Amt"), this, 7, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_deposit_amt = new JTextField() , this, 7, 4, 0.0, 0.0, 4, 1);
			txt_deposit_amt.addKeyListener(floatlis);
			txt_deposit_amt.setHorizontalAlignment(SwingConstants.RIGHT);
			txt_array[5] = txt_deposit_amt;
			
			
		
			
			
			addComponent(new JLabel("Interest Rate"), this,  8, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_intrest_rt = new JTextField() , this,  8, 4, 0.0, 0.0, 4, 1);
			txt_intrest_rt.addKeyListener(floatlis);
			txt_intrest_rt.setHorizontalAlignment(SwingConstants.RIGHT);
			//txt_array[8] = txt_intrest_rt;
			
			addComponent(new JLabel("Maturity Amt"), this,  9, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_matur_amt = new JTextField() , this,  9, 4, 0.0, 0.0, 4, 1);
			txt_matur_amt.setHorizontalAlignment(SwingConstants.RIGHT);
			txt_matur_amt.addKeyListener(floatlis);
			txt_array[6] = txt_matur_amt;
			
			
			
			
			
			addComponent(new JLabel("Intr Acc type"), this, 10, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_intro_ac_type = new JComboBox() , this, 10, 4, 0.0, 0.0, 4, 1);
			combo_intro_ac_type.addItem("None");
			combo_intro_ac_type.addItem("SH");
			combo_intro_ac_type.addItem("SB");
			combo_intro_ac_type.addItem("CA");
			
			addComponent(new JLabel("Intro AC no"), this, 11, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_intro_ac_no = new JTextField() , this, 11, 4, 0.0, 0.0, 4, 1);
			
			
			
			addComponent(new JLabel("Next pay dt"), this,  12, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_next_pay_dt = new JTextField() , this, 12, 4, 0.0, 0.0, 4, 1 );
			txt_next_pay_dt.addKeyListener(dtlist);
			txt_array[7] = txt_next_pay_dt;
			
			
			/*txt_ac_no ,txt_cid, txt_deposit_dt, txt_matur_dt,txt_deposit_days,txt_deposit_amt,txt_matur_amt,
			 * txt_next_pay_dt ,txt_intrest_rt ,txt_int_upto_dt,txt_reciept,txt_int_paid_dt*/
			
			addComponent(new JLabel("Recieved By "), this, 13, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_rcvd_by = new JComboBox() , this, 13, 4, 0.0, 0.0, 4, 1);
			combo_rcvd_by.addItem("Cash");
			combo_rcvd_by.addItem("Transfer");
			
			addComponent(new JLabel("Rec Ac type"), this, 14, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_rcvd_ac_type = new JComboBox() , this, 14, 4, 0.0, 0.0, 4, 1);
			combo_rcvd_ac_type.addItem("SB");
			combo_rcvd_ac_type.addItem("CA");
			
			addComponent(new JLabel("Rec Ac no"), this, 15, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_rcvd_ac_no = new JTextField() , this, 15, 4, 0.0, 0.0, 4, 1);
			
			
			addComponent(new JLabel("Int Freq"), this, 16, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_freq_mod = new JComboBox() , this, 16, 4, 0.0, 0.0, 4, 1);
			//combo_freq_mod.addItem("Monthly");
			combo_freq_mod.addItem("quaterly");
			//combo_freq_mod.addItem("half yearly");
			//combo_freq_mod.addItem("yearly");
			//combo_freq_mod.addItem("maturity");
			
			addComponent(new JLabel("Pay Mode"), this, 17, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_pay_mode = new JComboBox() , this, 17, 4, 0.0, 0.0, 4, 1);
			combo_pay_mode.addItem("Cash");
			combo_pay_mode.addItem("Transfer");
			
			addComponent(new JLabel("Pay Ac ty "), this, 18, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_pay_ac_type = new JComboBox() , this, 18, 4, 0.0, 0.0, 4, 1);
			combo_pay_ac_type.addItem("SB");
			combo_pay_ac_type.addItem("CA");
			
			addComponent(new JLabel("Pay Ac no"), this, 19, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_pay_ac_no = new JTextField() , this, 19, 4, 0.0, 0.0, 4, 1);
			
			addComponent(new JLabel("loan Avalid"), this, 20, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_ln_avld = new JComboBox() , this, 20, 4, 0.0, 0.0, 4, 1);
			combo_ln_avld.addItem("F");
			combo_ln_avld.addItem("T");
			
			addComponent(new JLabel("Ln ac type "), this, 21, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_ln_ac_type = new JComboBox() , this, 21, 4, 0.0, 0.0, 4, 1);
			combo_ln_ac_type.addItem("SB");
			combo_ln_ac_type.addItem("CA");
			
			addComponent(new JLabel("Ln Ac no"), this, 22, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_ln_ac_no = new JTextField() , this, 22, 4, 0.0, 0.0, 4, 1);
			
			addComponent(new JLabel("Receipt No"), this, 23, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_reciept = new JTextField() , this, 23, 4, 0.0, 0.0, 4, 1);
			//txt_array[9] = txt_reciept;
			
			addComponent(new JLabel("interest Upto"), this, 24, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_int_upto_dt = new JTextField() , this, 24, 4, 0.0, 0.0, 4, 1);
			txt_int_upto_dt.addKeyListener(dtlist);
			txt_array[8] = txt_int_upto_dt;
			
			addComponent(new JLabel("int Paid dt"), this, 25, 1, 0.0, 0.0, 3, 1);
			addComponent( txt_int_paid_dt = new JTextField() , this, 25, 4, 0.0, 0.0, 4, 1);
			txt_int_paid_dt.addKeyListener(dtlist);
			txt_array[9] = txt_int_paid_dt;
			
			addComponent(new JLabel("Appl Detail"), this, 26, 1, 0.0, 0.0, 3, 1);
			addComponent( combo_detail = new JComboBox() , this, 26, 4, 0.0, 0.0, 4, 1);
			combo_detail.addItem("Personal");
			combo_detail.addItem("Joint Holder");
			combo_detail.addItem("Nominee");

			combo_detail.addItemListener(this);
			
			
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
			addComponent( btn_submit, this, 28, 4, 0.0, 0.0, 4, 1 );
			btn_submit.addActionListener(this);
			
			btn_update = new JButton("Update");
			addComponent( btn_update, this, 28, 8, 0.0, 0.0, 4, 1 );
			btn_update.addActionListener(this);
			
			btn_clear = new JButton("Clear");
			addComponent( btn_clear, this, 28, 12, 0.0, 0.0, 4, 1 );
			btn_clear.addActionListener(this);
		
					
			
			clearScreen();
			
			showJSP(0);
			
			map_names =  getCustDetails.getInstance().getCustomerNames();
			
			store_dep = new StoreDeposit();
		}
		
		
		
	    public static RDDataEntry getInstance(int i) {
			
	    	if ( i == 0)
	    		return	rd_de = new RDDataEntry();
	    	
	    	if ( rd_de == null )
	    	    return	rd_de = new RDDataEntry();
	    	 
	    	return rd_de;
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

		public void focusLost(FocusEvent ar ) {

			if ( ar.getSource() == txt_ac_no  ) {
				
				if ( txt_ac_no.getText().toString().trim().length() > 0 &&  new Integer(txt_ac_no.getText().toString().trim()).intValue() >0 ) {
					
					try {
					
						
						
						Object[] obj = store_dep.getDepositAccount("1004001", new Integer(txt_ac_no.getText().toString().trim()).intValue());
						
						if ( obj != null ) {
							
							DepositMasterObject depobj = (DepositMasterObject)obj[0];
							txt_cid.setText(new Integer(depobj.getCid()).toString());
							txt_deposit_dt.setText(depobj.getDep_date());
							txt_matur_dt.setText(depobj.getMat_date());
							
							txt_deposit_days.setText( new Integer(depobj.getDep_days()).toString());
							txt_deposit_amt.setText( new Double(depobj.getDep_amt()).toString());
							txt_matur_amt.setText(new Double(depobj.getMat_amt()).toString());
							txt_next_pay_dt.setText(depobj.getNext_pay_date());
							
							if ( depobj.getIntr_ac_type() != null ) { 
								
								
								if (depobj.getIntr_ac_type().equalsIgnoreCase("1001001"))
									combo_intro_ac_type.setSelectedIndex(1);
								else if (depobj.getIntr_ac_type().equalsIgnoreCase("1002001"))
									combo_intro_ac_type.setSelectedIndex(2);
								else if (depobj.getIntr_ac_type().equalsIgnoreCase("1007001"))
									combo_intro_ac_type.setSelectedIndex(3);
								
								txt_intro_ac_no.setText( new Integer(depobj.getIntr_ac_no()).toString());
							} else {
								
								combo_intro_ac_type.setSelectedIndex(0);
								txt_intro_ac_no.setText("0");
							}
							                                        
							txt_intrest_rt.setText( new Double( depobj.getInt_rate()).toString());
							
							if ( depobj.getRcvd_by().equalsIgnoreCase("T")) {
								
								combo_rcvd_by.setSelectedIndex(1);
								
								if ( depobj.getRcvd_ac_type().equalsIgnoreCase("1002001"))
									combo_rcvd_ac_type.setSelectedIndex(0);
								else 
									combo_rcvd_ac_type.setSelectedIndex(1);
								
								txt_rcvd_ac_no.setText( new Integer( depobj.getRcvd_ac_no()).toString());
								
							}else 
								combo_rcvd_by.setSelectedIndex(0);
							
							switch ( depobj.getInt_freq().charAt(0) )  {
							
							/*case 'M': combo_freq_mod.setSelectedIndex(0);
									  break;*/
							case 'Q': combo_freq_mod.setSelectedIndex(1);
							          break;
							/*case 'H': combo_freq_mod.setSelectedIndex(2);
							          break;
							case 'Y': combo_freq_mod.setSelectedIndex(3);
							          break;
							case 'O': combo_freq_mod.setSelectedIndex(4);
									  break;
*/
							}
							
							if ( depobj.getInt_mode() != null && depobj.getInt_mode().equalsIgnoreCase("T")) {
								
								combo_pay_mode.setSelectedIndex(1);
								if ( depobj.getTrf_ac_type().equalsIgnoreCase("1002001") )
									 combo_pay_ac_type.setSelectedIndex(0);
								else 
									combo_pay_ac_type.setSelectedIndex(1);
								
								txt_pay_ac_no.setText( new Integer ( depobj.getTrf_ac_no()).toString());
								
							} else 
								combo_pay_mode.setSelectedIndex(0);
							
							if ( depobj.getLn_availed() != null && depobj.getLn_availed().equalsIgnoreCase("T")) {
								
								combo_ln_avld.setSelectedIndex(1);
								 
								if ( depobj.getLn_ac_type().equalsIgnoreCase("1002001")) {
									
									combo_ln_ac_type.setSelectedIndex(0);
								}else 
									combo_ln_ac_type.setSelectedIndex(1);
								
								txt_ln_ac_no.setText( new Integer ( depobj.getLn_ac_no()).toString());
								
								
							} else {
								
								combo_ln_avld.setSelectedIndex(0);
							}
							
							txt_reciept.setText( new Integer ( depobj.getRct_no()).toString());
							txt_int_upto_dt.setText(depobj.getInt_upto_date());
							txt_int_paid_dt.setText(depobj.getInt_paid_date());
							
							panel_person.setPanelData( depobj.getCid());
							
							if ( depobj.getVec_jointholder() != null && depobj.getVec_jointholder().size() > 0) {
								
								for  ( int i =0 ; i < depobj.getVec_jointholder().size() ; i++) {
									
									joint.showCustDetail( (Integer)depobj.getVec_jointholder().get(i));
								}
								
							}
							
							if ( obj[1] != null ) {
								
								NomineeObject nomi = (NomineeObject)obj[1];
								if ( nomi.getCid() == 0) {
									
									
									nominee.txt_cid.setText("0");
									nominee.txt_dob.setText(nomi.getDob());
									nominee.txt_name.setText(nomi.getName());
									nominee.txt_relation.setText(nomi.getRelation());
									nominee.textarea_add.setText(nomi.getAddress());
									nominee.lbl_regno.setText( new Integer(nomi.getRegno()).toString());
									if (nomi.getSex() == 0){
										
										nominee.combo_sex.setSelectedIndex(1);
									} else 
										nominee.combo_sex.setSelectedIndex(0);
									
								} else {
									
									nominee.setPanelData(nomi.getCid());
									
								}
								
								
								
							}
							
							btn_submit.setEnabled(false);
							btn_update.setEnabled(true);
							btn_update.requestFocus();	
						} else {
							
							txt_cid.requestFocus();
						}
						                   
					} catch ( RecordNotFound rec ) {
						
						JOptionPane.showMessageDialog(null , rec);
					}
					
					
					
				} else  if ( txt_ac_no.getText().toString().equalsIgnoreCase("")) {
					
					txt_ac_no.setText("0");
					btn_update.setEnabled(false);
					txt_cid.requestFocus();
					
				} else {
					btn_update.setEnabled(false);
					txt_cid.requestFocus();
				}
				
			}

			else if ( ar.getSource() == txt_cid  && ar.getOppositeComponent() == txt_deposit_dt) {


				if ( txt_cid.getText().toString().trim().length() > 0 &&  new Integer(txt_cid.getText().toString().trim()).intValue() >0 ) {

					panel_person.setPanelData(new Integer(txt_cid.getText().toString().trim()));
				}
				else 
					showJSP(3);

				//combo_details.setSelectedIndex(0);
			}
			
			else if ( ar.getSource() == txt_deposit_days  && ar.getOppositeComponent() == txt_deposit_amt ) {
				
				if ( txt_deposit_days.getText().toString().trim().equalsIgnoreCase("") ) {
				
					JOptionPane.showMessageDialog( null , "please enter the period" );
					
				}	
				else if ( txt_deposit_days.getText().toString().length() > 0 && new Integer(txt_deposit_days.getText().toString()).intValue() > 0){	
					
					try {
					if ( combo_ac_type.getSelectedIndex() == 0 ) {

						

							txt_matur_dt.setText( Validation.getFutureDayDate( txt_deposit_dt.getText().toString().trim(),new Integer (txt_deposit_days.getText().toString()).intValue()));
						

					} else {
						
						txt_matur_dt.setText( Validation.getFutureMonthDate( txt_deposit_dt.getText().toString().trim(),new Integer (txt_deposit_days.getText().toString()).intValue()));
					}
					} catch ( Exception ec ) {

						ec.printStackTrace();
					}
				}
				
			}
			

		}

		public void keyPressed(KeyEvent arg0) {

			if ( arg0.getSource() == txt_cid ) {
				
				if ( arg0.getKeyCode() == KeyEvent.VK_F1) {
					query = null;
					query = new QueryNamePanel();
				}
			}
			
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void itemStateChanged(ItemEvent arg) {

			if ( arg.getSource() == combo_detail ) {
				
				
				showJSP(combo_detail.getSelectedIndex());

			} 
			
			
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
			
			for ( int i = 0; i< txt_array.length; i++) {
				
				txt_array[i].setText("");
			}
				
			txt_intro_ac_no.setText("");
			txt_rcvd_ac_no.setText("");
			txt_pay_ac_no.setText("");
			txt_intrest_rt.setText("");
			
			btn_submit.setEnabled(true);
			btn_update.setEnabled(true);
			nominee.clearNominee();
			panel_person.clear();
			joint.clearJointHolder();
			combo_detail.setSelectedIndex(0);
			showJSP(0);
			
		}

		public void update(Observable arg0, Object ar) {

			{		
				if ( arg0 instanceof SubObserver ) {
					
					System.out.println(" SubObserver +++++++++++++++");
				}
			
				JOptionPane.showMessageDialog(null, "observabel is called");
			if ( ar  instanceof Integer ) {
				
				if ( ((Integer)ar).intValue() > 0 ) {
					
					txt_cid.setText(new String(ar.toString()));
					showJSP(0);
					panel_person.setPanelData(((Integer)ar).intValue());
					
					
				}
				
			}
		}
			
		}



		public void actionPerformed(ActionEvent arg ) {

			if ( arg.getSource() == btn_submit || arg.getSource() == btn_update ) {
				
				DepositMasterObject dep = getPanelDetails();
				
				if ( dep != null ) {
					try {
						
						if ( arg.getSource() == btn_submit  ){
							if ( store_dep.storeDepositMaster( nominee.getNomineeDetail() , dep)) {

								JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
								clearScreen();
							} else
								JOptionPane.showMessageDialog( null, "Inserted UnSucessfull " );
					
						} else if (arg.getSource() == btn_update ) {
							
							if ( store_dep.updateDepositAccount( nominee.getNomineeDetail() , dep )) {
								
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
		
		
		private DepositMasterObject getPanelDetails() {
			
			int count = 0;
			DepositMasterObject dep_obj = null ;
			
			
			
			for ( int i = 0; i< txt_array.length ; i++) {
				
				if ( (checkField(txt_array[i])) == 1) {
					count++;
				} else {
					
					System.out.println(txt_array[i].getText());
					JOptionPane.showMessageDialog(null , " Enter the proper Value in the" +txt_names[i] );
					break;
				}
			}
				
			
			System.out.println(" txt fields length "+ count+ " length of array "+ txt_array.length);
			
			if ( count == txt_array.length ) {

				dep_obj = new DepositMasterObject();	
				
				if( combo_ac_type.getSelectedIndex()==0) {
					dep_obj.setAc_type("1004001");
					
				}
				/*switch ( combo_ac_type.getSelectedIndex() ) {
				
				case 0 : dep_obj.setAc_type("1003001");
				         break; 
				case 1 : dep_obj.setAc_type("1004001");
						 break;
				
				}*/
				
				dep_obj.setAc_no( new Integer( txt_ac_no.getText().toString().trim()));
				dep_obj.setCid(new Integer( txt_cid.getText().toString().trim()));
				dep_obj.setAdd_type(panel_person.getAddrType());
				dep_obj.setName(panel_person.getName());
				dep_obj.setDep_date(txt_deposit_dt.getText().toString().trim());
				dep_obj.setMat_date(txt_matur_dt.getText().toString().trim());
				dep_obj.setDep_days( new Integer( txt_deposit_days.getText().toString().trim()));
				dep_obj.setDep_amt(new Double( txt_deposit_amt.getText().toString().trim()));
				dep_obj.setMat_amt(new Double( txt_matur_amt.getText().toString().trim()));
				dep_obj.setNext_pay_date(txt_next_pay_dt.getText().toString().trim());
				dep_obj.setInt_rate(new Double( txt_intrest_rt.getText().toString().trim()));
				dep_obj.setRct_no(new Integer ( txt_reciept.getText().toString().trim()));
				dep_obj.setInt_paid_date(txt_int_paid_dt.getText().toString().trim());
				dep_obj.setInt_upto_date(txt_int_upto_dt.getText().toString().trim());

				dep_obj.setVec_jointholder(joint.getJointholdersCid());



				if ( combo_intro_ac_type .getSelectedIndex() > 0 ) {

					switch( combo_ln_ac_type.getSelectedIndex()) {

					case 1 : dep_obj.setIntr_ac_type("1001001");
					break;
					case 2 : dep_obj.setIntr_ac_type("1002001");
					break;
					case 3 : dep_obj.setIntr_ac_type("1007001");
					break;		 

					}

					dep_obj.setIntr_ac_no(new Integer( txt_intro_ac_no.getText().toString().trim()));
				}


				if ( combo_rcvd_by .getSelectedIndex() == 1 ) {

					dep_obj.setRcvd_by("T");

					if ( combo_rcvd_ac_type.getSelectedIndex() == 0)
						dep_obj.setRcvd_ac_type("1002001");
					else 
						dep_obj.setRcvd_ac_type("1007001");

					dep_obj.setRcvd_ac_no(new Integer( txt_rcvd_ac_no.getText().toString().trim()));



				} else 
					dep_obj.setRcvd_by("C");


				switch ( combo_freq_mod.getSelectedIndex()) {

				case 0 : dep_obj.setInt_freq("M");
				break;
				case 1 : dep_obj.setInt_freq("Q");
				break;
				case 2 : dep_obj.setInt_freq("H");
				break;
				case 3 : dep_obj.setInt_freq("Y");
				break;
				case  4: dep_obj.setInt_freq("O");
				break;

				}


				if ( combo_pay_mode.getSelectedIndex() == 1) {

					if ( combo_pay_ac_type.getSelectedIndex() == 0)
						dep_obj.setTrf_ac_type("1002001");
					else 
						dep_obj.setTrf_ac_type("1007001");

					dep_obj.setTrf_ac_no(new Integer( txt_pay_ac_no.getText().toString().trim()));


					if ( combo_ln_avld.getSelectedIndex() == 1) {

						dep_obj.setLn_availed("T");
						dep_obj.setLn_ac_type("1008001");
						dep_obj.setLn_ac_no(new Integer( txt_ln_ac_no.getText().toString().trim()));
					} else {

						dep_obj.setLn_availed("F");
						dep_obj.setLn_ac_type("null");
						dep_obj.setLn_ac_no( 0 );

					}

				} else 
					dep_obj.setInt_mode("C");


				dep_obj.setAdd_type( panel_person.getAddrType());
				if ( joint.getJointholdersCid().size() > 0 )
					dep_obj.setNo_jt_hldr(joint.getJointholdersCid().size());
				else 
					dep_obj.setNo_jt_hldr(0);

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

			}
			return dep_obj;
		}
		
		private int checkField(JComponent comp) {
			
			System.out.println("inside the checkfeild ");
			
			JTextField txt_feild = (JTextField)comp;
			
			System.out.println(txt_feild.getText());
			if ( comp instanceof JTextField ) {
				
				
				
				if ( txt_feild.getText().toString().equalsIgnoreCase("") ) {
					 
					return -1;
				}
				else if (  txt_feild.getText().toString().length() > 0 )// && new Integer(txt_feild.getText().toString()).intValue() >0 ) 
				{
					 
					System.out.println("txt field......"+txt_feild.getText());
					return 1;
				} 
				
				
			}
			
			return 0;
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

			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

		}
}
