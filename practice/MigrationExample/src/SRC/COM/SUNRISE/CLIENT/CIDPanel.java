package SRC.COM.SUNRISE.CLIENT;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.TabExpander;

import SRC.COM.SUNRISE.SERVER.StoreCidData;
import SRC.COM.SUNRISE.SERVER.getCustDetails;
import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.ImageFileView;
import SRC.COM.SUNRISE.UTILITY.ImageFilter;
import SRC.COM.SUNRISE.UTILITY.ImagePreview;
import SRC.COM.SUNRISE.UTILITY.NumListener;
import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.CustomerMasterObject;








public class CIDPanel extends JPanel  implements ActionListener,FocusListener,KeyListener,CaretListener {

	private GridBagConstraints gbc;
	
	JTextField txt_cid,txt_names;
	JComboBox combo_inst_indi , combo_names;
	
	JPanel panel_primary_details, panel_addr_detail, panel_gaurdian;
	JTextField p1_txt_name, p1_txt_caste_detail, p1_txt_dob, p1_txt_intro_id, p1_txt_intro_name,p1_txt_age,p1_txt_occupation,p1_txt_spouseName;
	
	JComboBox p1_combo_salution, p1_combo_scst, p1_combo_sex, p1_combo_martial, p1_combo_categories, p1_combo_occupation ;
	
	JTextField  pg_txt_name, pg_txt_relation;
	JComboBox pg_combo_sex;
	
	JTextArea textarea_address,pg_textarea_guardian_address;
	
	JTextField p2_txt_city, p2_txt_pincode, p2_txt_mobile, p2_txt_phone;
	JButton p2_btn_photo,p2_btn_sign;
	JComboBox p2_combo_addprf;
	
	JList list_address_types;
	
	JLabel lbl_photo,lbl_sign;
	String string_photo,string_sign;
	
	ImageIcon imageicon_photo,imageicon_sign;
	
	JButton btn_submit, btn_update, btn_delete, btn_clear;
	
	Hashtable  hash_add_type_;
	TreeMap hash_cust_type;
	String[] str_occ, str_salution, str_martial, str_add_pf, str_castes;
	
	getCustDetails cust_det ;
	
	Vector list_addr ;
	
	
	
	CustomerMasterObject cm ;
	
	
	
	byte photo_array[]=null,sign_array[]=null;
	
	SubObserver subObserver = new SubObserver();
	
	public CIDPanel(){
		
		System.out.println("inside the CID Panel COnst");
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4,4,4,4);
		
		
		for (int i= 0; i< 32; i++)
			addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel(" "), this, 25, 32, 1.0, 1.0 , 1, 1 );
		
		addComponent( new JLabel("Customer ID "), this, 1, 1, 0.0, 0.0, 4, 1);
		
		txt_cid = new JTextField();
		addComponent(txt_cid, this, 1, 5, 0.0, 0.0, 4, 1);
		txt_cid.addFocusListener(this);
		//txt_cid.addCaretListener(this);
		txt_cid.addKeyListener(this);
		
		addComponent( new JLabel("Customer Type "), this, 1, 10, 0.0, 0.0, 4, 1);
		
		combo_inst_indi = new JComboBox();
		addComponent(combo_inst_indi, this, 1, 15, 0.0, 0.0, 4, 1);
		combo_inst_indi.addFocusListener(this);
		
		/*txt_names = new JTextField();
		addComponent(txt_names, this, 1, 20, 0.0, 0.0, 4, 1);
		txt_names.addCaretListener(this);
		
		combo_names = new JComboBox();
		addComponent(combo_names, this, 2, 20, 0.0, 0.0, 4, 1);*/
			
		
		
		panel_primary_details = new JPanel();
		addComponent(panel_primary_details, this, 2, 1, 0.0, 0.0, 31, 5);
		panel_primary_details.setBorder(BorderFactory.createEtchedBorder(1));
		
		panel_primary_details.setBackground(new Color(253,254,233) );
		panel_primary_details.setLayout(new GridBagLayout());
		
		for (int i= 0; i< 31; i++)
			addComponent(new JLabel("      "), panel_primary_details, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent( new JLabel("Name"), panel_primary_details, 1, 0, 0.0, 0.0, 2, 1);
		
		p1_txt_name = new JTextField();
		addComponent(p1_txt_name, panel_primary_details, 1, 3, 0.0, 0.0, 8, 1);
		p1_txt_name.addCaretListener(this);
		
		
		
		addComponent( new JLabel("Salution"), panel_primary_details, 1, 12, 0.0, 0.0, 2, 1);
		addComponent(p1_combo_salution = new JComboBox(), panel_primary_details, 1, 14, 0.0, 0.0, 2, 1);
		
		/*addComponent( new JLabel("Categories"), panel_primary_details, 1, 17, 0.0, 0.0, 3, 1);
		addComponent(p1_combo_categories = new JComboBox(), panel_primary_details, 1, 19, 0.0, 0.0, 3, 1);*/
		
		//1, 22, 0.0, 0.0, 3, 1 
		//1, 25, 0.0, 0.0, 3, 1
		
		addComponent( new JLabel("Martial Status"), panel_primary_details, 1, 17, 0.0, 0.0, 3, 1);
		addComponent(p1_combo_martial = new JComboBox(), panel_primary_details, 1, 20, 0.0, 0.0, 3, 1);
		p1_combo_martial.addFocusListener(this);
		
		addComponent( new JLabel("Spouse Name"), panel_primary_details, 1, 24, 0.0, 0.0, 3, 1 );
		addComponent( p1_txt_spouseName = new JTextField(), panel_primary_details, 1, 27, 0.0, 0.0, 3, 1);
		p1_txt_spouseName.setEnabled(false);
		
		addComponent( new JLabel("Sex"), panel_primary_details, 2, 0, 0.0, 0.0, 2, 1);
		addComponent(p1_combo_sex = new JComboBox(), panel_primary_details, 2, 3, 0.0, 0.0, 2, 1);
		p1_combo_sex.addItem("Male");
		p1_combo_sex.addItem("Female");
		
		addComponent( new JLabel("SC/ST Details") , panel_primary_details, 2, 5, 0.0, 0.0, 3, 1);
		addComponent(p1_combo_scst= new JComboBox(), panel_primary_details , 2, 8, 0.0, 0.0, 3, 1);
		
		addComponent(new JLabel("Intro ID"), panel_primary_details, 2, 12, 0.0, 0.0, 2, 1);
		addComponent(p1_txt_intro_id = new JTextField(), panel_primary_details, 2, 14, 0.0, 0.0, 3, 1);
		p1_txt_intro_id.addFocusListener(this);
		
		addComponent(new JLabel("Intro Name"), panel_primary_details, 2, 18, 0.0, 0.0, 2, 1);
		addComponent(p1_txt_intro_name = new JTextField(), panel_primary_details, 2, 21, 0.0, 0.0, 8, 1);
		
		addComponent(new JLabel("Occupation"), panel_primary_details, 3, 0, 0.0, 0.0, 2, 1 );
		//addComponent(p1_txt_occupation = new JTextField(), panel_primary_details, 3, 3, 0.0, 0.0, 5, 1);
		addComponent(p1_combo_occupation = new JComboBox(), panel_primary_details, 3, 3, 0.0, 0.0, 5, 1);
		
		addComponent( new JLabel("Date of Birth"), panel_primary_details, 3, 15, 0.0, 0.0, 1, 1);
		addComponent(p1_txt_dob = new JTextField(), panel_primary_details, 3, 16, 0.0, 0.0, 3, 1);
		p1_txt_dob.addFocusListener(this);
		p1_txt_dob.addKeyListener(new DateListener());
		
		addComponent(new JLabel("Age"), panel_primary_details, 3, 9, 0.0, 0.0, 2, 1);
		addComponent(p1_txt_age = new JTextField(), panel_primary_details, 3, 11, 0.0, 0.0, 2, 1);
		p1_txt_age.addFocusListener(this);
		panel_gaurdian = new JPanel();
		
		panel_gaurdian.setLayout(new GridBagLayout());
		panel_gaurdian.setBackground(new Color(253,254,233) );
		panel_gaurdian.setBorder(BorderFactory.createTitledBorder("Guardian Detail"));
		addComponent(panel_gaurdian, panel_primary_details, 4, 0, 0.0, 0.0, 25, 3);
		
		for (int i= 0; i< 24; i++)
			addComponent(new JLabel("      "), panel_gaurdian, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel("Name"), panel_gaurdian, 1, 0, 0.0, 0.0, 2 , 1 ) ;
		addComponent(pg_txt_name = new JTextField(), panel_gaurdian, 1, 2, 0.0, 0.0, 8, 1);
		
		
		addComponent(new JLabel("Address"), panel_gaurdian, 1, 11, 0.0, 0.0, 2, 1 );
		
		JScrollPane jsp_guardian_address=new JScrollPane(pg_textarea_guardian_address = new JTextArea(2,4));
        jsp_guardian_address.setPreferredSize(new Dimension(250,50));

        addComponent( jsp_guardian_address, panel_gaurdian, 1, 13, 0.0, 0.0, 9, 2);
		
        addComponent(new JLabel("Sex"), panel_gaurdian, 2, 0, 0.0, 0.0, 2, 1 );
		addComponent(pg_combo_sex = new JComboBox(), panel_gaurdian, 2, 2, 0.0, 0.0, 3, 1 );
		pg_combo_sex.addItem("Male");
		pg_combo_sex.addItem("Female");

		addComponent(new JLabel("RelationShip"), panel_gaurdian, 3, 0, 0.0, 0.0, 3, 1 );
		addComponent(pg_txt_relation = new JTextField(), panel_gaurdian, 3, 4, 0.0, 0.0, 6, 1);

		addComponent(new JLabel(" "), panel_gaurdian, 4, 18, 1.0, 1.0, 1 , 1 ) ;
		
		panel_addr_detail = new JPanel(); 
		
		addComponent(panel_addr_detail, this, 8, 1, 0.0, 0.0, 31,6 );
		panel_addr_detail.setBorder(BorderFactory.createEtchedBorder(1));
		
		panel_addr_detail.setBackground(new Color(253,254,233) );
		panel_addr_detail.setLayout(new GridBagLayout());
		
		for (int i= 0; i< 31; i++)
			addComponent(new JLabel("      "), panel_addr_detail, 0, i, 0.0, 0.0 , 1, 1 );
		
		addComponent(new JLabel("Addr type"), panel_addr_detail, 1, 0, 0.0, 0.0, 3, 1 );
		
		 cust_det = getCustDetails.getInstance();
		
		 hash_add_type_ = cust_det.getAddrType();
		 list_addr = new Vector();
			
			if ( hash_add_type_ != null) {
				
				Enumeration enu1 = hash_add_type_.keys();
				
				while( enu1.hasMoreElements())
					list_addr.addElement(enu1.nextElement());
				
			}
		 
		list_address_types = new JList(list_addr);
		JScrollPane js_list = new JScrollPane(list_address_types); 
		addComponent( js_list, panel_addr_detail, 2, 0, 0.0, 0.0, 6, 3);
		js_list.setPreferredSize(new Dimension(60,80));
		list_address_types.setAutoscrolls(true);
        
        textarea_address=new JTextArea(1,1);
        textarea_address.setLineWrap(true);

        JScrollPane jsp_address=new JScrollPane(textarea_address);
        jsp_address.setPreferredSize(new Dimension(60,80));
        
        addComponent(new JLabel("Address"), panel_addr_detail, 1, 6, 0.0, 0.0, 3, 1 );
		addComponent(jsp_address, panel_addr_detail, 2, 6, 0.0, 0.0, 8, 4);
		
		
		addComponent(new JLabel("Mobile"), panel_addr_detail, 7, 0, 0.0, 0.0, 2, 1);
		addComponent(p2_txt_mobile = new JTextField(), panel_addr_detail, 7, 2, 0.0, 0.0, 6, 1);
		NumListener numlis = new NumListener();
		p2_txt_mobile.addKeyListener(numlis);
		
		addComponent(new JLabel("Phone"), panel_addr_detail, 8, 0, 0.0, 0.0, 2, 1);
		addComponent(p2_txt_phone = new JTextField(), panel_addr_detail, 8, 2, 0.0, 0.0, 6, 1);
		p2_txt_phone.addKeyListener(numlis);
		
		addComponent(new JLabel("Pin"), panel_addr_detail, 9, 0, 0.0, 0.0, 2, 1);
		addComponent(p2_txt_pincode = new JTextField(), panel_addr_detail, 9, 2, 0.0, 0.0, 6, 1);
		p2_txt_pincode.addKeyListener(numlis);
		p2_txt_pincode.setText("562159");
		
		p2_btn_photo = new JButton("Photo");
		addComponent(p2_btn_photo, panel_addr_detail, 1, 17, 0.0, 0.0, 2, 1);
		p2_btn_photo.addActionListener(this);
		
		
		lbl_photo = new JLabel(); 
        JScrollPane js_photo = new JScrollPane(lbl_photo);
        addComponent(js_photo, panel_addr_detail, 2, 17, 0.0, 0.0,  6 , 6 );
        js_photo.setPreferredSize(new Dimension(60,120));

        p2_btn_sign = new JButton("Signature");
		addComponent(p2_btn_sign, panel_addr_detail, 1, 26, 0.0, 0.0, 3, 1);
		p2_btn_sign.addActionListener(this);
        
        
        lbl_sign=new JLabel();
        JScrollPane js_sign = new JScrollPane(lbl_sign);
        addComponent(js_sign, panel_addr_detail, 2, 24, 0.0, 0.0, 6, 6);
        js_sign.setPreferredSize(new Dimension(60,120));
        
        addComponent(new JLabel("Addr proof"), panel_addr_detail, 8, 17, 0.0, 0.0, 3, 1);
        p2_combo_addprf = new JComboBox();
        addComponent(p2_combo_addprf, panel_addr_detail, 8, 20, 0.0, 0.0, 4, 1);
        
        btn_submit = new JButton("Submit");
        addComponent(btn_submit, this, 16, 10, 0.0, 0.0, 3, 1);
        btn_submit.addActionListener(this);
        
        btn_update = new JButton("Update");
        addComponent( btn_update, this , 16, 14, 0.0, 0.0, 3, 1);
        btn_update.addActionListener(this);
        
        btn_delete = new JButton("Delete");
        addComponent( btn_delete, this , 16, 18, 0.0, 0.0, 3, 1);
        btn_delete.addActionListener(this);
        
        btn_clear = new JButton("Clear");
        addComponent( btn_clear, this , 16, 22, 0.0, 0.0, 3, 1);
        btn_clear.addActionListener(this);
        	
        fillFormData() ;
        
        System.out.println(" end of const CID");
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

	private void fillFormData() {
		
		//getCustDetails cust_det = new getCustDetails();
		
		hash_cust_type = cust_det.getCustomerType();
		
		if( hash_cust_type != null) {
			
			Collection  enu = hash_cust_type.values();
			
			Iterator itr = enu.iterator();
			
			while(itr.hasNext())
				combo_inst_indi.addItem((itr.next()));
		}
		
		
		str_occ =  cust_det.getOccupation();
		
		if (str_occ != null) {
			for(int i =0; i<str_occ.length; i++ )
			p1_combo_occupation.addItem(str_occ[i]);
			
		}
		                 
		 str_salution = cust_det.getSalution();

		 if ( str_salution != null ) {
			 
			 for ( int j = 0; j< str_salution.length; j++)
				 p1_combo_salution.addItem(str_salution[j]);
		
		 }
		 
		 
		 
		 str_martial = cust_det.getMartialstatus();
		 
		 if ( str_martial != null) {
			 
			 for ( int i = 0; i< str_martial.length; i++) {
				 p1_combo_martial.addItem(str_martial[i]);
			 }
			 
		 }
		
		 
		 str_add_pf = cust_det.getAddressProof();
		 
		 if ( str_add_pf != null) {
			 
			 for( int i =0 ; i< str_add_pf.length; i++)
				 p2_combo_addprf.addItem(str_add_pf[i]);
			 
		 }
		 
		 str_castes  = cust_det.getCastes();
		 
		 if ( str_castes != null) {
			 
			 for(int i=0 ; i< str_castes.length; i++)
				 p1_combo_scst.addItem(str_castes[i]);
			 
		 }
		 
		 
		
		
	}
	
	public void actionPerformed(ActionEvent ar) {

		if (ar.getSource() == p2_btn_photo || ar.getSource() == p2_btn_sign ) {
			
			JFileChooser filechooser_photo = null;
            
            if (filechooser_photo == null )
            {
                filechooser_photo = new JFileChooser();			
                filechooser_photo.addChoosableFileFilter( new ImageFilter());
               filechooser_photo.addChoosableFileFilter ( new ImageFilter() );
               filechooser_photo.setAcceptAllFileFilterUsed(false);
                filechooser_photo.setFileView(new ImageFileView());
                filechooser_photo.setAccessory(new ImagePreview(filechooser_photo));
            }
            filechooser_photo.showDialog(this,"Attach");
            if ( ar.getSource() == p2_btn_photo) {
                if(filechooser_photo.getSelectedFile()!=null)
                {
                    string_photo=filechooser_photo.getSelectedFile().toString();
                    imageicon_photo=new ImageIcon(string_photo);	
                    lbl_photo.setIcon(imageicon_photo);
					//subObserver.callObservers(cid_num);
                }	
            }
            else {
            	
            	if(filechooser_photo.getSelectedFile()!=null)
                {
                    string_sign=filechooser_photo.getSelectedFile().toString();
                    imageicon_sign=new ImageIcon(string_sign);
                    lbl_sign.setIcon(imageicon_sign);
                }
            }
            
            filechooser_photo.setSelectedFile(null);
		}
		
		else if ( ar.getSource() == btn_submit) {
			
			storecid();
			if (cm != null) {
				
				 if (JOptionPane.showConfirmDialog(null,"Confirm the data entered !!","Confirmation",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
					 StoreCidData store = new StoreCidData();
					 
					 int cid_num  = store.storeCId(cm);
					 subObserver.callObservers(cid_num);
					JOptionPane.showMessageDialog(null, cid_num );
					clear();
					
				 }
			}
			
		}
		
		else if ( ar.getSource() == btn_clear) {
			
			clear();
		}
	
		else if ( ar.getSource() == btn_update) {
				
			storecid();
			if (cm != null) {
				
				 if (JOptionPane.showConfirmDialog(null,"Confirm the data entered !!","Confirmation",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
					 StoreCidData store = new StoreCidData();
					 int cid_num  = store.storeCId(cm);
					JOptionPane.showMessageDialog(null, "Customer Id: "+cid_num);
					subObserver.callObservers(cid_num);
					clear();
				 }
			}
			
		}
		
	}

	
	public void storecid() {
		
		
		
		cm = new CustomerMasterObject();
		if ( txt_cid.getText().trim().length() > 0)
			cm.setCustomerID( Integer.parseInt(txt_cid.getText().trim().toString()));
		else 
			cm.setCustomerID(0);
		
		System.out.println(combo_inst_indi.getSelectedItem()+" selected cats");
		
		//cm.setCategory((Integer)hash_cust_type.get(combo_inst_indi.getSelectedItem().toString()));
		cm.setCategory(combo_inst_indi.getSelectedIndex());
		
		cm.setIntroducerId(p1_txt_intro_id.getText());
		cm.setFirstName(p1_txt_name.getText());
		cm.setMiddleName("");
		cm.setLastName("");
		cm.setMotherName("");
		cm.setFatherName("");
		cm.setSalute(p1_combo_salution.getSelectedItem().toString());
		cm.setDOB(p1_txt_dob.getText().toString().trim());
		if ( p1_combo_sex.getSelectedItem().toString().equalsIgnoreCase("Male"))
			cm.setSex("M");
		else 
			cm.setSex("F");
		
		cm.setMaritalStatus(p1_combo_martial.getSelectedItem().toString());
		
		 if ( p1_txt_spouseName.isEnabled() ) {
			 
			 cm.setSpousename(p1_txt_spouseName.getText().toString());
		
		 } else {
			 
			 cm.setSpousename("");
		 }
		cm.setSubCategory(0);
	    try{
            if(string_photo!=null )
            { 
                if(string_photo.length()>0)
                {
                    FileInputStream fileinputstream_photo=new FileInputStream(string_photo);
                    File file_photo=new File(string_photo);
                    byte byte_photo[]=new byte[(int)file_photo.length()];
                    fileinputstream_photo.read( byte_photo);
                    cm.setBinaryImage(byte_photo);
                    System.out.println(" inside set photo*******************");
                }			
            }
            else{
                cm.setBinaryImage(photo_array);
                System.out.println("inside else set photo");
            }
            
            if(string_sign!=null )
            {
                if(string_sign.length()>0)
                {
                    FileInputStream fileinputstream_sign=new FileInputStream(string_sign);
                    File file_sign=new File(string_sign);
                    byte byte_sign[]=new byte[(int)file_sign.length()];
                    fileinputstream_sign.read( byte_sign);
                    cm.setBinarySignImage(byte_sign);
                    System.out.println(" inside set sign*******************");
                }			
            }
            else{
                cm.setBinarySignImage(sign_array);
                System.out.println("inside else set sign");
            }
        }catch(Exception exception_io){exception_io.printStackTrace();}
        
        
        
        cm.setNameProof("");
        cm.setAddressProof(p2_combo_addprf.getSelectedItem().toString());
        cm.setPanno("");
        cm.setOccupation(p1_combo_occupation.getSelectedItem().toString());
        cm.setSubOccupation("");
        cm.setScSt(p1_combo_scst.getSelectedItem().toString());
        cm.setCaste("");
        cm.setNationality("Indian");
        cm.setPassPortNumber("");
        cm.setReligion("");
        cm.setPPIssueDate("");
        cm.setPPExpiryDate("");
        
        if ( panel_gaurdian.isVisible()) {
        	cm.setGuardType("NATURAL");
        	cm.setGuardName(pg_txt_name.getText().toString());
        	cm.setGuardSalute("");
        	cm.setGuardSex(pg_combo_sex.getSelectedItem().toString());
        	cm.setGuardRelationship(pg_txt_relation.getText().toString());
        	cm.setGuardAddress(pg_textarea_guardian_address.getText());
        } else {
        	
        	cm.setGuardType("");
        	cm.setGuardName("");
        	cm.setGuardSalute("");
        	cm.setGuardSex("");
        	cm.setGuardRelationship("");
        	cm.setGuardAddress("");
        	
        	
        }
        
        
        
        cm.setCourtDate("");
        
        if ( !p2_txt_mobile.getText().toString().trim().equalsIgnoreCase("") )
        	cm.setMobile(Long.parseLong((p2_txt_mobile.getText().toString().trim())));
        else 
        	cm.setMobile(0);
        
        if ( !p2_txt_phone.getText().toString().trim().equalsIgnoreCase("") )
        	cm.setPhone(Long.parseLong(p2_txt_phone.getText().toString().trim()));
        else 
        	cm.setPhone(0);
        
        if ( !p2_txt_pincode.getText().toString().trim().equalsIgnoreCase("") )
        	cm.setPin(Long.parseLong(p2_txt_pincode.getText().toString().trim()));
        else 
        	cm.setPin(0);

        if ( !textarea_address.getText().toString().trim().equalsIgnoreCase("") )
        	cm.setAddress(textarea_address.getText());
        else 
        	cm.setAddress("");
        
        cm.setAdd_type(list_address_types.getSelectedIndex());
        
        cm.setUdatetime(MainScreen.getSysDate()+""+MainScreen.getSysTime());
		cm.setUid(MainScreen.head_panel.getUid());
		cm.setUtml_no(MainScreen.head_panel.getTml());
		
		cm.setVdatetime(MainScreen.getSysDate()+""+MainScreen.getSysTime());
		cm.setVid(MainScreen.head_panel.getUid());
		cm.setVtml_no(MainScreen.head_panel.getTml());	
        
        
        
        
		
		
		
		
	}

	public void focusGained(FocusEvent arg) {

			/*if (arg.getSource() == p1_txt_dob ) {
				
				panel_gaurdian.setVisible(true);
			}
		*/
		
	}

	public void focusLost(FocusEvent ar) {

		if ( ar.getSource() == txt_cid && ar.getOppositeComponent() == combo_inst_indi) {
			
			if ( txt_cid.getText().length() != 0 ) {
				
				setPanelData(Integer.parseInt(txt_cid.getText().toString()));
				
	
		}else 
			txt_cid.setText("0");
			
		}
		
		else if ( ar.getSource() == p1_txt_age && ar.getOppositeComponent() == p1_txt_dob ) {
			
			if ( !p1_txt_age.getText().toString().equalsIgnoreCase("") && p1_txt_age.getText().toString().length() >  0) {
				
				
				int age = new Integer(p1_txt_age.getText().toString()).intValue() ;
				
				
 				Calendar cal = Calendar.getInstance();
 				String date = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR); 
 				try{
 				
 				p1_txt_dob.setText(Validation .subyear(Validation.checkDate(date), age));
                 
                 if ( age > 18 ) { 
                	 
                	 panel_gaurdian.setVisible(false);             	 
                	 
                 } else 
                	 panel_gaurdian.setVisible(true);
                 System.out.println(p1_txt_dob.getText());
 				}catch (Exception e) {
					// TODO: handle exception
				}}
			
		}
		
		else if ( ar.getSource() == p1_txt_intro_id && ar.getOppositeComponent() == p1_txt_intro_name)
		{
			if ( !p1_txt_intro_id.getText().toString().trim().equalsIgnoreCase("") && p1_txt_intro_id.getText().toString().trim().length() > 0) {
				
				String str = cust_det.getCustomerName(Integer.parseInt(p1_txt_intro_id.getText().toString().trim()));
				
				if ( str == null ) {
					
					p1_txt_intro_id.requestFocus();
					JOptionPane.showMessageDialog(null , "Introducer Name not Found");
					
				} else 
					p1_txt_intro_name.setText(str);
				
			} else {
				
				p1_txt_intro_id.setText("0");
				p1_txt_intro_name.setText("");
			}
			
		}
	
		else if ( ar.getSource() == p1_combo_martial )//&& ar.getOppositeComponent() == p1_txt_spouseName )
		{
			
			if ( ((String)p1_combo_martial.getSelectedItem()).equalsIgnoreCase("Married") ) {
				
				p1_txt_spouseName.setEnabled(true);
				p1_txt_spouseName.requestFocus();
			
			} else {
				
				p1_txt_spouseName.setEnabled(false);
			}
			
		}
		
	}
	
	public void setPanelData( int cid ) {
		
		
		 StoreCidData getCId = new  StoreCidData();
		 
		 CustomerMasterObject cm = getCId.getCustomerID(cid);
		 
		 if ( cm != null) {
			 
			 txt_cid.setText(new Integer(cid).toString());
			 
			 combo_inst_indi.setSelectedIndex(cm.getCategory());
			 
			 
			 p1_txt_name.setText(cm.getName());
			 
			 if ( str_salution != null && str_salution.length > 0 && (cm.getSalute() != null && !(cm.getSalute().equalsIgnoreCase(""))) ) {
				 
				 for(int i =0 ;i< str_salution.length; i++) {
					 
					 if ( str_salution[i].equalsIgnoreCase(cm.getSalute()))
						 p1_combo_salution.setSelectedIndex(i);
				 }
				 
			 }
			 
			 if ( str_martial != null && ( cm.getMaritalStatus() != null && !(cm.getMaritalStatus().equalsIgnoreCase(""))) ) {
				System.out.println("str_martial="+str_martial); 
				 for ( int i =0; i< str_martial.length; i++) {
					 
					 if( str_martial[i].equalsIgnoreCase(cm.getMaritalStatus()))
						 p1_combo_martial.setSelectedIndex(i);
				 }

			 }
			 
			 if ( ((String)p1_combo_martial.getSelectedItem()).equalsIgnoreCase("Married") ) {
				 
				 p1_txt_spouseName.setEnabled(true);
				 p1_txt_spouseName.setText(cm.getSpousename());
				 
			 } else {
			
				 p1_txt_spouseName.setEnabled(false);
			 
			 }
			
			if ( cm.getSex() != null)	 
			 p1_combo_sex.setSelectedIndex(cm.getSex().equals("M")?0:1);
			 
			 if ( str_castes!= null && (cm.getCaste()!= null  && !cm.getCaste().equalsIgnoreCase("")) ) {
				 
				 for(int i= 0; i< str_castes.length ; i++) {
					 
					 if ( str_castes[i].equalsIgnoreCase(cm.getCaste()))
						 p1_combo_scst.setSelectedIndex(i);
				 }
			 }
			 
			 if ( str_occ!= null && ( cm.getOccupation()!= null && !cm.getOccupation().equalsIgnoreCase("")) ) {
				 
				 for(int i= 0; i< str_occ.length ; i++) {
					 
					 if ( str_occ[i].equalsIgnoreCase(cm.getOccupation()))
						 p1_combo_occupation.setSelectedIndex(i);
				 }
			 }
			 
			 
			 p1_txt_intro_id.setText(cm.getIntroducerId());
			 p1_txt_dob.setText(cm.getDOB());
			 
			 if(cm.getBinaryImage()!=null){
				 lbl_photo.setIcon(new ImageIcon(cm.getBinaryImage(),"photo"));
                 photo_array= cm.getBinaryImage();
             }
             else {
             	lbl_photo.setIcon(null);
             	System.out.println("photo is null");
             }
             
             if(cm.getBinarySignImage()!=null){
               lbl_sign.setIcon(new ImageIcon(cm.getBinarySignImage(),"Sign"));
               sign_array=cm.getBinarySignImage();
             }
             else
             	lbl_sign.setIcon(null);
             
             
             
             if(cm.getDOB()!=null && !cm.getDOB().equals("00/00/0000") && cm.getDOB().length()>0)
 			{
 				StringTokenizer d=new StringTokenizer(cm.getDOB(),"/");
 				d.nextToken(); d.nextToken();
                 
 				int yy=Integer.parseInt(d.nextToken());
 	
 				Calendar cal=Calendar.getInstance();
 				int a = cal.get(Calendar.YEAR)-yy;
                 p1_txt_age.setText(String.valueOf(a));
                 
                 if ( a < 18){
                	 panel_gaurdian.setVisible(true);
                 }
 			}	
             
             
             
             
             if(cm.getGuardName()!=null)
             {					
                 if(cm.getGuardName().length()>0)
                 {
                     
                     pg_txt_name.setText(cm.getGuardName());
                     pg_combo_sex.setSelectedIndex((cm.getGuardSex()=="M")?0:1);
                     pg_textarea_guardian_address.setText(cm.getGuardAddress());
                     pg_txt_relation.setText(cm.getGuardRelationship());
                     
                 }
			 
		 }
             
          p2_txt_mobile.setText(Long.toString(cm.getMobile()));
          p2_txt_phone.setText(Long.toString(cm.getPhone()));
          p2_txt_pincode.setText(Long.toString(cm.getPin()));
          textarea_address.setText(cm.getAddress());
          list_address_types.setSelectedIndex(cm.getAdd_type());
             
          btn_submit.setEnabled(false);
		
	} 

		
	}
	
	private void clear() {
		
		txt_cid.setText("0");
		combo_inst_indi.setSelectedIndex(0);
		p1_txt_age.setText("");
		p1_txt_dob.setText("");
		p1_txt_intro_id.setText("");
		p1_txt_intro_name.setText("");
		p1_txt_name.setText("");
		p1_txt_spouseName.setText("");
		p1_txt_spouseName.setEnabled(false);
		
		
		pg_textarea_guardian_address.setText("");
		pg_txt_name.setText("");
		pg_txt_relation.setText("");
		
		textarea_address.setText("");
		p2_txt_mobile.setText("");
		p2_txt_phone.setText("");
		p2_txt_pincode.setText("");
		lbl_photo.setIcon(null);
		lbl_sign.setIcon(null);
		list_address_types.setSelectedIndex(0);
		btn_submit.setEnabled(true);
		
	}

	public void keyPressed(KeyEvent arg0) {

		
		
		
	}

	public void keyReleased(KeyEvent arg0) {
		
		//System.out.println("key Released ......."+ arg0.getKeyCode());
		
	}

	public void keyTyped(KeyEvent arg0) {
		
		System.out.println("key typeed ......."+ arg0.getKeyCode()+"----" + "----------"+arg0.getKeyChar());		
	}

	
	class SubObserver extends Observable {
		
		
		SubObserver(){
			
			
			
		}
		
		public void callObservers(int cid){
			
			System.out.println("inside the call observer method");
			setChanged();
			notifyObservers(new Integer(cid));
		}
		
	}


	public void caretUpdate(CaretEvent arg0) {
		
		
		if ( arg0.getSource() == txt_cid ) {
			
			p1_txt_name.setText(txt_cid.getText()+"-----");
			System.out.println(txt_cid.getText()+ "-------");
			
		}
		
		
		if ( arg0.getSource() == p1_txt_name ) {
			
			
			
			
			
		}
		
		
	}


	
}
