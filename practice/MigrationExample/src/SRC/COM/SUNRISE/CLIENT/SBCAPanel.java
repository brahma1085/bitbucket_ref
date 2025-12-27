package SRC.COM.SUNRISE.CLIENT;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
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

import SRC.COM.SUNRISE.CLIENT.*;
import SRC.COM.SUNRISE.CLIENT.CIDPanel.SubObserver;
import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.*;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.*;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.*;



public class SBCAPanel extends JPanel implements ItemListener,ActionListener,FocusListener,Observer,KeyListener {
	
	
	private GridBagConstraints gbc;
	
	
	JTextField txt_ac_no, txt_cid, txt_intro_ac_no, txt_ac_op_date,txt_chk_book_no,txt_seq_from,txt_seq_to,txt_balance;
	JComboBox combo_ac_type, combo_ch_bk_issue, combo_intro_ac_type, combo_details,combo_freeze,combo_active ;
	
	JPanel panel_tabbed;
	JButton btn_submit, btn_update, btn_clear;
	
	Personal panel_person ;
	Nominee nominee;
	JointHolder joint ;
	JScrollPane[] jsp_detail = new JScrollPane[5];
	
	CIDPanel cid_panel;
	CIDDetails help_cid;
	
	QueryNamePanel query;
	Traversalpolicy traversal ;
	
	SBCAObject sbcaobj ;
	
	StoreSBCAMaster store;
	
	ShareCertificateNo check_book;
	
	TreeMap map_names ;
	
	public static SBCAPanel sbcapanel;
	
	public static SBCAPanel getInstance(int i) {
		
		if ( i == 0)
			return sbcapanel = new SBCAPanel();
			
		if ( sbcapanel == null)
			return sbcapanel = new SBCAPanel();
		
		return sbcapanel;
	}
	
	private  SBCAPanel() {
		
		setBackground(Colors.setMainPanelColor());
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4,4,4,4);
		
		
		for (int i= 0; i< 32; i++)
			addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel(" "), this, 25, 32, 1.0, 1.0 , 1, 1 );
		
		addComponent(new JLabel("Acc type"), this, 1, 1, 0.0, 0.0, 2, 1);
		addComponent( combo_ac_type = new JComboBox() , this, 1, 4, 0.0, 0.0, 5, 1);
		combo_ac_type.addItem("SB");
		combo_ac_type.addItem("CA");
		
		addComponent(new JLabel("Acc No"), this, 2, 1, 0.0, 0.0, 2, 1);
		addComponent( txt_ac_no = new JTextField() , this, 2, 4, 0.0, 0.0, 4, 1);
		txt_ac_no.addFocusListener(this);
		NumListener numlist = new NumListener();
		txt_ac_no.addKeyListener(numlist);
		
		addComponent(new JLabel("Customer ID"), this, 3, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_cid = new JTextField() , this, 3, 4, 0.0, 0.0, 4, 1);
		txt_cid.setText("0");
		txt_cid.addFocusListener(this);
		txt_cid.addKeyListener(numlist);
		txt_cid.addKeyListener(this);
		
		addComponent(new JLabel("Check Issue"), this, 4, 1, 0.0, 0.0, 3, 1);
		addComponent( combo_ch_bk_issue = new JComboBox() , this, 4, 4, 0.0, 0.0, 5, 1);
		combo_ch_bk_issue.addItem("F");
		combo_ch_bk_issue.addItem("T");
		combo_ch_bk_issue.addFocusListener(this);
		
		txt_chk_book_no = new JTextField();
		txt_seq_from = new JTextField();
		txt_seq_to = new JTextField();
		
		addComponent(new JLabel("Book No"), this, 5, 1, 0.0, 0.0, 2, 1);
		addComponent( txt_chk_book_no = new JTextField() , this, 5, 4, 0.0, 0.0, 4, 1);
		txt_chk_book_no.addKeyListener(numlist);
		
		addComponent(new JLabel("Seq from "), this, 6, 1, 0.0, 0.0, 2, 1);
		addComponent( txt_seq_from = new JTextField() , this, 6, 4, 0.0, 0.0, 4, 1);
		txt_seq_from.addKeyListener(numlist);
		
		addComponent(new JLabel("Seq to"), this, 7, 1, 0.0, 0.0, 2, 1);
		addComponent( txt_seq_to = new JTextField() , this, 7, 4, 0.0, 0.0, 4, 1);
		txt_seq_to.addKeyListener(numlist);
		
		addComponent(new JLabel("Acc Open date"), this, 8, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_ac_op_date = new JTextField() , this, 8, 4, 0.0, 0.0, 3, 1);
		txt_ac_op_date.addKeyListener(new DateListener());
		txt_ac_op_date.setText( MainScreen.getSysDate() );
		
		addComponent(new JLabel("Intro Acc type"), this, 9, 1, 0.0, 0.0, 3, 1);
		addComponent(combo_intro_ac_type = new JComboBox(), this, 9, 4, 0.0, 0.0, 5, 1);
		combo_intro_ac_type.addItem("None");
		combo_intro_ac_type.addItem("SB");
		combo_intro_ac_type.addItem("CA");
		combo_intro_ac_type.addItem("OD");
		combo_intro_ac_type.addItem("CC");
		combo_intro_ac_type.addFocusListener(this);
		
		addComponent(new JLabel("Intro Acc No"), this, 10, 1, 0.0, 0.0, 3, 1);
		addComponent( txt_intro_ac_no = new JTextField(), this, 10, 4, 0.0, 0.0, 3, 1);
		txt_intro_ac_no.addKeyListener(numlist);
		txt_intro_ac_no.setText("0");
		
		addComponent(new JLabel("Acc act/inac"), this, 11, 1, 0.0, 0.0, 2, 1);
		addComponent( combo_active = new JComboBox() , this, 11, 4, 0.0, 0.0, 5, 1);
		combo_active.addItem("O");
		combo_active.addItem("I");
		
		addComponent(new JLabel("Acc freeze"), this, 12, 1, 0.0, 0.0, 2, 1);
		addComponent( combo_freeze = new JComboBox() , this, 12, 4, 0.0, 0.0, 5, 1);
		combo_freeze.addItem("F");
		combo_freeze.addItem("T");
		
		
		addComponent(new JLabel("Details"), this, 13, 1, 0.0, 0.0, 2, 1);
		addComponent( combo_details = new JComboBox() , this, 13, 4, 0.0, 0.0, 5, 1);
		combo_details.addItem("Personal");
		combo_details.addItem("Joint Holder");
		combo_details.addItem("Nominee");
		combo_details.addItem("Check Detail");
		combo_details.addItemListener(this);
		
		addComponent(new JLabel("Balance"), this, 14, 1, 0.0, 0.0, 2, 1);
		addComponent( txt_balance = new JTextField() , this, 14, 4, 0.0, 0.0, 5, 1);
		txt_balance.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_balance.addKeyListener(new FloatNumListener() );
		
		btn_submit = new JButton("Submit");
		addComponent(btn_submit, this, 15, 1, 0.0, 0.0, 3, 1);
		btn_submit.addActionListener(this);
		
		btn_update = new JButton("Update");
		addComponent(btn_update, this, 15, 4, 0.0, 0.0, 3, 1);
		btn_update.addActionListener(this);
		
		btn_clear = new JButton("Clear");
		addComponent(btn_clear, this, 15, 7, 0.0, 0.0, 3, 1);
		btn_clear.addActionListener(this);
		
		
		
		panel_tabbed = new JPanel();
		panel_tabbed.setBackground(new Color(232,255,238));
		panel_tabbed.setBorder(BorderFactory.createEtchedBorder());
		panel_tabbed.setLayout(new GridBagLayout());
		
		for (int i= 0; i< 20; i++)
			addComponent(new JLabel("      "), panel_tabbed, 0, i, 0.0, 0.0 , 1, 1 );
		
		//addComponent(new JLabel("  "), panel_tabbed, 9, 21, 1.0, 1.0 , 1, 1 );
		
		addComponent(panel_tabbed, this, 2, 12, 0.0, 0.0, 19, 17);
		
		
		panel_person = new Personal();
		jsp_detail[0] = new JScrollPane(panel_person,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[0], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9 );
		
		jsp_detail[1] = new JScrollPane(joint =new JointHolder(),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[1], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9);
		jsp_detail[1].setVisible(false);
		
		jsp_detail[2] = new JScrollPane(nominee = new Nominee(btn_submit),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[2], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9 );
		jsp_detail[2].setVisible(false);
		
		String[] col_name = new String[2];
		col_name[0] = "Check No from";
		col_name[1] = "Check No to";
		
		jsp_detail[3] = new JScrollPane(check_book  = new ShareCertificateNo(col_name,1),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent( jsp_detail[3], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9);
		jsp_detail[3].setVisible(false);
		
		jsp_detail[4] = new JScrollPane(cid_panel = new CIDPanel(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		cid_panel.subObserver.addObserver(this);
		addComponent( jsp_detail[4], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9 );
		jsp_detail[4].setVisible(false);
		
		
		jsp_detail[4] = new JScrollPane(cid_panel = new CIDPanel());
		CIDPanel.SubObserver sub = cid_panel.new SubObserver();
		sub.addObserver(this);
		cid_panel.subObserver.addObserver(this);
		addComponent( jsp_detail[4], panel_tabbed, 1, 0, 0.0, 0.0, 20, 9 );
		jsp_detail[4].setVisible(false);
		//jsp_detail[4].setPreferredSize(new Dimension(600,600));
		
		//traversal = new Traversalpolicy(this);
		 store = new StoreSBCAMaster();
		 clearScreen();
		 
		 map_names =  getCustDetails.getInstance().getCustomerNames();
		
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
	
	public void itemStateChanged(ItemEvent arg) {
		/*server call*/	
		if ( arg.getSource() == combo_details ) {
			
			
			showJSP(combo_details.getSelectedIndex());
			/*server call*/
		} 
		
	}
	
	public void showJSP(int i) {
		
		
		for ( int j =0; j< jsp_detail.length; j++)
			jsp_detail[j].setVisible(false);
		
		if(i == 4) {
			if( txt_cid.getText().toString().length() > 0 && new Integer(txt_cid.getText().toString()).intValue() > 0)
			cid_panel.setPanelData(new Integer(txt_cid.getText().toString()).intValue());
		}
			
		
		jsp_detail[i].setVisible(true);
		panel_tabbed.revalidate();
	}
	

	public void actionPerformed(ActionEvent arg0) {

		if ( arg0.getSource() == btn_submit || arg0.getSource() == btn_update ) {
		
			if ( getPanelDate() == null ) {
			
			sbcaobj.setVec_jointholder(joint.getJointholdersCid());
			sbcaobj.setHash_jointholder(joint.getJointholdersCidName());
			
			
			
			try {
					if ( arg0.getSource() == btn_submit) {

						store.storeSBCAMaster( nominee.getNomineeDetail(), sbcaobj);
						JOptionPane.showMessageDialog(null, "Successfully Inserted");
						
					} else if ( arg0.getSource() == btn_update) {
						
						store.updateSBCAMaster(nominee.getNomineeDetail(), sbcaobj);
						JOptionPane.showMessageDialog(null, "Successfully Updated");
					}
					clearScreen();
					
			} catch (RecordNotInserted rec) {
				
				rec.printStackTrace();
				JOptionPane.showMessageDialog(null, rec.toString());
			}
			
			
		} else {
			
			JOptionPane.showMessageDialog(null, "Please fill all the feilds ");
		}
		
	}
		
		else if ( arg0.getSource() == btn_update) {
			
			
		} else if ( arg0.getSource() == btn_clear ) {
			
			clearScreen();
		}
		
		
	}
	
	public void clearScreen() {
		
		
		txt_ac_no.setText("");
		txt_chk_book_no.setText("");
		txt_cid.setText("");
		txt_intro_ac_no.setText("0");
		txt_seq_from.setText("");
		txt_seq_to.setText("");
		btn_submit.setEnabled(true);
		btn_update.setEnabled(false);
		txt_balance.setText("00.00");
		
		nominee.clearNominee();
		panel_person.clear();
		joint.clearJointHolder();
		check_book.clear();
		combo_details.setSelectedIndex(0);
		
	}
	
	private JComponent getPanelDate() {
		
		
		sbcaobj = new SBCAObject();
		
		ae:{

			sbcaobj.setAc_type(   combo_ac_type.getSelectedIndex()== 0 ? "1002001" : "1007001");

			if ( txt_ac_no.getText().toString().trim() .length() > 0 && new Integer(txt_ac_no.getText().toString().trim()).intValue() > 0) {

				sbcaobj.setAc_no(new Integer(txt_ac_no.getText().toString().trim()).intValue());

			} else {

				return txt_ac_no;
			}

			if ( txt_cid.getText().toString().trim() .length() > 0 && new Integer(txt_cid.getText().toString().trim()).intValue() > 0){

				sbcaobj.setCid(new Integer(txt_cid.getText().toString().trim()).intValue());

			} else {

				return txt_cid;
			}

			if ( combo_ch_bk_issue.getSelectedIndex() == 1) {

				sbcaobj.setCheck_book_issue("T");

			} else {

				sbcaobj.setCheck_book_issue("F");
			}

			sbcaobj.setAc_open_date(txt_ac_op_date.getText());

			{
				
				//sbcaobj.setIntro_ac_type(combo_intro_ac_type.getSelectedItem().toString());


				switch(combo_intro_ac_type.getSelectedIndex()) {

				case 0: sbcaobj.setIntro_ac_type("Null");
				break;
				case 1: sbcaobj.setIntro_ac_type("1002001");
				break;
				case 2:	sbcaobj.setIntro_ac_type("1007001");
				break;
				case 3:	sbcaobj.setIntro_ac_type("1015001");
				break;
				case 4:	sbcaobj.setIntro_ac_type("1014001");
				break;

				}
			
				if ( !txt_intro_ac_no.getText().toString().equalsIgnoreCase("") && txt_intro_ac_no.getText().toString().length() > 0)
				sbcaobj.setIntro_ac_no(new Integer(txt_intro_ac_no.getText().toString().trim()).intValue());
				else {
					sbcaobj.setIntro_ac_no(0);
				}
				
			}

			sbcaobj.setAddr_type(panel_person.getAddrType());
			sbcaobj.setName(panel_person.getName());

			sbcaobj.setFreeze(combo_freeze.getSelectedItem().toString());
			sbcaobj.setActive(combo_active.getSelectedItem().toString() );
			sbcaobj.setDe_datetime(MainScreen.getSysDate()+""+MainScreen.getSysTime());
			
			if ( !txt_balance.getText().toString().equalsIgnoreCase("") && txt_balance.getText().toString().length() > 0)
				sbcaobj.setBalanceAmount(new Double(txt_balance.getText().toString().trim()).doubleValue());
			else 
				return txt_balance;
			
			sbcaobj.setCkeckno(check_book.getCertificateNo());
			
			sbcaobj.setDe_tml(MainScreen.head_panel.getTml());
			sbcaobj.setDe_user(MainScreen.head_panel.getUid());
			
			
			return null;
		}
	}
	
	
	class Traversalpolicy extends FocusTraversalPolicy{

		
		Traversalpolicy(SBCAPanel this_panel){
			
			this_panel.setFocusable(true);
	 		this_panel.setFocusCycleRoot(true);
	 		this_panel.setFocusTraversalPolicy(this);
		}
		
		
		@Override
		public Component getComponentAfter(Container arg0, Component ar) {

			if ( ar == combo_ac_type )
				return txt_ac_no;
			
			else if ( ar == txt_ac_no)
				return txt_cid;
			
			else if ( ar == txt_cid)
				return combo_ch_bk_issue;
			
			//else if ( ar == txt_chk_book_no ) 
			
			
			return null;
		}

		@Override
		public Component getComponentBefore(Container arg0, Component arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getDefaultComponent(Container arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFirstComponent(Container arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getLastComponent(Container arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent ar) {
		
		
		if ( ar.getSource() == txt_cid && ar.getOppositeComponent() == combo_ch_bk_issue ) {
			
			//String str = new StringTokenizer()
			
			if ( txt_cid.getText().toString().trim().length() > 0 &&  new Integer(txt_cid.getText().toString().trim()).intValue() >0 ) {
				
				panel_person.setPanelData(new Integer(txt_cid.getText().toString().trim()));
			}
			else 
				showJSP(4);
			
			//combo_details.setSelectedIndex(0);
		}
		
		else if ( ar.getSource() == txt_ac_no ) {
			
			Object[] obj = null ;
			if ( txt_ac_no.getText().toString().equalsIgnoreCase("")) {
				
				JOptionPane.showMessageDialog(null, "Entet Account No ");
				//txt_ac_no.requestFocus();
			}
			else if ( txt_ac_no.getText().toString().trim().length() > 0 ) {
				try {
					
					String code = null ;	
					if ( combo_ac_type.getSelectedIndex() == 0)
						code = "1002001";
					else 
						code = "1007001";
					
					obj = store.getSBCADetail( code, new Integer(txt_ac_no.getText().toString().trim()).intValue());
					
					if (obj != null ){
						
						//JOptionPane.showMessageDialog(null, ( (SBCAObject)obj[0]).getCid());
						
						SBCAObject sbca = (SBCAObject)obj[0];
						
						txt_cid.setText(new Integer(sbca.getCid()).toString());
						
						if ( sbca.getCheck_book_issue().equalsIgnoreCase("T")) {
							
							combo_ch_bk_issue.setSelectedIndex(1);
							check_book.setCertificateNo(sbca.getCkeckno());
							
							/*txt_chk_book_no.setText( new Integer(sbca.getBook_no()).toString());
							 * txt_seq_from.setText(new Integer(sbca.getSeq_from()).toString());
							txt_seq_to.setText(new Integer(sbca.getSeq_to()).toString());*/
							
							
						}
					
						switch( new Integer (sbca.getIntro_ac_type()).intValue()){
						
							
							case 0 : combo_intro_ac_type.setSelectedIndex(0);
											break;
							case 1002001 : combo_intro_ac_type.setSelectedIndex(1);
											break;
							case 1007001 : combo_intro_ac_type.setSelectedIndex(2);
											break;
							case 1014001 : combo_intro_ac_type.setSelectedIndex(3);
											break;
							case 1015001 : combo_intro_ac_type.setSelectedIndex(4);
											break;
											
							}
							
							txt_ac_op_date.setText(sbca.getAc_open_date());
							txt_intro_ac_no.setText( new Integer (sbca.getIntro_ac_no()).toString());
							combo_active.setSelectedIndex( sbca.getActive().equalsIgnoreCase("O") ?0:1 );
							combo_freeze.setSelectedIndex( sbca.getFreeze().equalsIgnoreCase("F")?0:1);
							
							txt_balance.setText(new Double (sbca.getBalanceAmount()).toString());
							
							if ( sbca.getVec_jointholder() != null && sbca.getVec_jointholder().size() > 0) {
								
								for  ( int i =0 ; i < sbca.getVec_jointholder().size() ; i++) {
									
									joint.showCustDetail( (Integer)sbca.getVec_jointholder().get(i));
								}
								
							}
							
							panel_person.setPanelData(new Integer(sbca.getCid()).intValue());
							
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
						
					} 
					
					
				} catch (RecordNotFound rec) {
					
					rec.printStackTrace();
					
					txt_cid.requestFocus();
				}
				
			}
			
			txt_cid.requestFocus();
		}
		
		else if ( ar.getSource() == combo_intro_ac_type ) {
			
			if ( combo_intro_ac_type.getSelectedIndex() == 0 ) {
				
				txt_intro_ac_no.setText("0");
			
			} else {
				
				txt_intro_ac_no.setText("");
			}
		}
		
		
	}

	public void update(Observable arg0, Object ar ) {
		
			System.out.println("inside the Obse.......................");
			
			if ( combo_details.getSelectedIndex() == 1 ) {
				
				if ( ar  instanceof Integer ) {
					
					if ( ((Integer)ar).intValue() > 0 ) {
						
						joint.showCustDetail(((Integer)ar).intValue());
						showJSP(1);
						
						
					}
					
				}
				
				
			}
			else {		
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

	public void keyPressed(KeyEvent arg0) {

		if ( arg0.getKeyCode() == KeyEvent.VK_F1  ) {
			
			//HelpWnidow help = new HelpWnidow();
			//help_cid = null;
			query = null;
			 //help_cid = new CIDDetails();
			 
			 query = new QueryNamePanel();
	
		}
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class CIDDetails extends JFrame implements KeyListener,FocusListener, WindowListener ,CaretListener 
	{
		
		
		Object[] array_object=null;
		JPanel panel_bank_details;
		JTable table_bank_details;
		JTextField txt_name;
		
		GridBagConstraints gb = null ;
		DefaultTableModel dtm_bank_details;
		private JScrollPane jsp;

		public CIDDetails()
		{
			addWindowListener(this);
			
			gb = new GridBagConstraints();
						
			panel_bank_details=(JPanel)getContentPane();
			panel_bank_details.setLayout(new GridBagLayout());
			
			dtm_bank_details=new DefaultTableModel()
			{
				public boolean isCellEditable(int row,int column)
				{
					  return false;
				}
			};
			
			try
			{
				dtm_bank_details.addColumn("Name");
				dtm_bank_details.addColumn("Cid");
				
				table_bank_details = new JTable(dtm_bank_details);
				table_bank_details.addKeyListener(this);
				
				
				
				Object[] obj = new Object[2];
				obj[0] ="";
				obj[1] = "";
				
				for (int i= 0; i< 15; i++)
					dtm_bank_details.addRow(obj);
				
				
				jsp = new JScrollPane(table_bank_details , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
				
				
				for (int i= 0; i< 15; i++)
					addComponent(new JLabel("      "), panel_bank_details, 0, i, 0.0, 0.0 , 1, 1 );
				
				for (int i= 0; i< 15; i++)
					addComponent(new JLabel("      "), panel_bank_details, i, 0, 0.0, 0.0 , 1, 1 );
				
				addComponent(new JLabel("Name "),panel_bank_details ,1,1, 0.0, 0.0, 3, 1);
				txt_name = new JTextField();
				txt_name.addCaretListener(this);
				addComponent(txt_name,panel_bank_details ,1,4, 0.0, 0.0, 10, 1);
				
				addComponent(jsp,panel_bank_details,2,1,0.0,0.0,14,11);
				jsp.setPreferredSize(new Dimension(30,180));

				
				addComponent(new JLabel(" "),panel_bank_details ,16,16, 1.0, 1.0, 1, 1);
				
				setTitle("Help Window");
				setLocation(355,175);
				setSize(500,500);
				setVisible(true);

			}catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,ex+"(in show Scroll frame)");}
		}

		public void focusLost(FocusEvent fl){}
		
		public void focusGained(FocusEvent fg)
		{}
		public void keyReleased(KeyEvent z){}
		
		public void keyTyped(KeyEvent e)
		{}
		
		public void keyPressed(KeyEvent a)
		{
			
			if ( a.getSource() == table_bank_details ) {
				
				if ( a.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String str = new StringTokenizer(table_bank_details.getValueAt(table_bank_details.getSelectedRow() , 1).toString(),",").nextToken();
					txt_cid.setText(str) ;
				
					this.setVisible(false);
					//help_cid.dispose();
				}
			}
			
		}
		public void windowActivated(WindowEvent e){}
		public void windowClosed(WindowEvent e){}
		public void windowClosing(WindowEvent e){
			
			
		}
		public void windowDeactivated(WindowEvent e){}
		public void windowDeiconified(WindowEvent e){}
		public void windowIconified(WindowEvent e){}
		public void windowOpened(WindowEvent e){}

		public void caretUpdate(CaretEvent arg) {

			if ( arg.getSource() == txt_name ) {
				
				dtm_bank_details.setRowCount(0);
				
				String entr = txt_name.getText().toString().toLowerCase();
				 
				Set ss = map_names.keySet();
				Iterator it = ss.iterator();
				Object[] obj = new Object[2];
				
				//for ( int i =0; i< 1000 ; i++ ) 
				while ( it.hasNext())
				{
					
					
					
					Object s = it.next();
					
					if ( ((String)s).toLowerCase().startsWith(entr) ) {
					
						Object s1 = map_names.get(s);
						
						obj[0] = s; 
						obj[1] = s1;
						dtm_bank_details.addRow(obj);
						
					}
					
					
				}
				
			}
			
		}
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
					frame_window.setVisible(false);
					query = null;
					
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
