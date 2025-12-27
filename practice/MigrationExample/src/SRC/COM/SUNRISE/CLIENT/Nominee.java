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
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;

import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.*;
import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.*;






public class Nominee extends JPanel implements ItemListener,ActionListener,FocusListener {
	
	GridBagConstraints gbc=null;
    
    JCheckBox check_has_account;
    
    JTextField txt_cid,txt_name,txt_dob,txt_relation,txt_age;
    JTextArea textarea_add;
    JLabel lbl_photo,lbl_sign,lbl_cid,lbl_regno;
    JButton btn_submit;
    JComboBox combo_sex;
    
    NomineeObject nomi_obj;
    
    JScrollPane js_photo,js_sign;
    JComponent component1;
    
    public Nominee(JComponent component) {
    	this.component1=component;
    	gbc = new GridBagConstraints();
    	gbc.fill = GridBagConstraints.BOTH;  
		gbc.insets = new Insets(5,5,5,5);
		
		setLayout(new GridBagLayout());
		
    	for(int i= 0; i<19; i++)
    		addComponent(new JLabel("      "),this, 0, i, 0.0, 0.0, 1, 1);
    	
    	
    	addComponent(new JLabel("  "),this, 16, 20, 1.0, 1.0, 1, 1);
    	
    	addComponent(check_has_account = new JCheckBox("has CID"), this, 1, 0, 0.0, 0.0, 4, 1);
    	check_has_account.addItemListener(this);
    	
    	addComponent( lbl_cid = new JLabel("Customer ID"), this, 1, 5, 0.0, 0.0, 4, 1);
    	addComponent(txt_cid = new JTextField(), this, 1, 9, 0.0, 0.0, 4, 1);
    	lbl_cid.setVisible(false);
    	txt_cid.setVisible(false);
    	txt_cid.addFocusListener(this);
    	
    	addComponent(new JLabel("Reg No"), this, 1, 14, 0.0, 0.0, 2, 1);
    	addComponent(lbl_regno = new JLabel(" "), this, 1, 16, 0.0, 0.0, 3, 1);
    	
    	addComponent(new JLabel("Name"), this, 2, 0, 0.0, 0.0, 2, 1);
    	addComponent( txt_name = new JTextField(), this, 2, 3, 0.0, 0.0, 7, 1);
    	
    	addComponent(new JLabel("Age"), this, 3, 0, 0.0, 0.0, 2, 1);
    	addComponent( txt_age = new JTextField(), this, 3, 3, 0.0, 0.0, 4, 1);
    	
    	/*txt_dob.addKeyListener(new DateListener());*/
    	
    	txt_age.addFocusListener(this);
    	addComponent(new JLabel("DOB"), this, 4, 0, 0.0, 0.0, 2, 1);
    	addComponent( txt_dob = new JTextField(), this, 4, 3, 0.0, 0.0, 4, 1);
    	txt_dob.addKeyListener(new SRC.COM.SUNRISE.UTILITY.DateListener());
    	
    	
    	addComponent(new JLabel("Sex"), this, 5, 0, 0.0, 0.0, 2, 1);
    	addComponent( combo_sex = new JComboBox(), this, 5, 3, 0.0, 0.0, 4, 1);
    	combo_sex.addItem("M");
    	combo_sex.addItem("F");
    	
    	addComponent(new JLabel("Relation"), this, 6, 0, 0.0, 0.0, 3, 1);
    	addComponent( txt_relation = new JTextField(), this, 6, 3, 0.0, 0.0, 4, 1);
    	
    	addComponent(new JLabel("Address"), this, 7, 0, 0.0, 0.0, 3, 1);
    	
    	JScrollPane js_txt = new JScrollPane( textarea_add = new JTextArea());
    	js_txt.setPreferredSize(new Dimension(250,70));
    	addComponent( js_txt, this, 8, 0, 0.0, 0.0, 9, 3);
    	
    	
    	lbl_photo = new JLabel();
    	js_photo = new JScrollPane(lbl_photo);
    	
    	lbl_photo.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_photo.setBackground(new Color(253,254,239));
		lbl_photo.setVisible(false);
		addComponent(new JLabel("Photo"), this, 2, 10, 0.0, 0.0, 2, 1);
		js_photo.setPreferredSize(new Dimension(60,120));
		addComponent(js_photo, this, 3, 10, 0.0, 0.0, 6, 5);
		js_photo.setVisible(false);
		
		addComponent(new JLabel("Signature"), this, 8, 10, 0.0, 0.0, 3, 1);
		
		lbl_sign = new JLabel();
		js_sign = new JScrollPane(lbl_sign);
		lbl_sign.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_sign.setBackground(new Color(253,254,239));
		lbl_sign.setVisible(false);
		
		js_sign.setPreferredSize(new Dimension(60,120));
		addComponent(js_sign, this, 9, 10, 0.0, 0.0, 6, 6);
		js_sign.setVisible(false);
		addComponent( btn_submit = new JButton("Submit"), this, 11, 0, 0.0, 0.0, 3, 1);
		btn_submit.addActionListener(this);
		
		
    	
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

	public void itemStateChanged(ItemEvent arg0) {

		
		if ( arg0.getSource() == check_has_account ) {
			
			if ( check_has_account.isSelected()) {
				
				lbl_cid.setVisible(true);
		    	txt_cid.setVisible(true);
		    	js_photo.setVisible(true);
		    	js_sign.setVisible(true);
		    	
			} else {
				
				lbl_cid.setVisible(false);
		    	txt_cid.setVisible(false);
		    	js_photo.setVisible(false);
		    	js_sign.setVisible(false);
			}
			
		}
		
	}

	
	public void actionPerformed(ActionEvent arg0) {
		
		if ( arg0.getSource() == btn_submit ) {
			
			 if ( JOptionPane.showConfirmDialog(null,"Data Ok "," Nominee",JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ){
			
			nomi_obj = new NomineeObject();
			
			if ( txt_cid.isVisible()) {
				
				if (txt_cid.getText().length()>0) {
					
					nomi_obj.setCid(new Integer(txt_cid.getText().toString().trim()).intValue());
				}
				
			}
			
			if ( !lbl_regno.getText().toString().equalsIgnoreCase("") && lbl_regno.getText().toString().trim().length() > 0) {
				
				nomi_obj.setRegno( new Integer (lbl_regno.getText().toString().trim()).intValue());
			} else 
				nomi_obj.setRegno(0);
			
			nomi_obj.setName(txt_name.getText().toString().trim());
			nomi_obj.setDob(txt_dob.getText().toString().trim());
			nomi_obj.setSex(  ((String)combo_sex.getSelectedItem()).equalsIgnoreCase("M")?1:0 );
			nomi_obj.setRelation(txt_relation.getText().toString().trim());
			nomi_obj.setAddress(textarea_add.getText().toString());
			
			
			JOptionPane.showMessageDialog(null , "Submitted");
			if(this.component1!=null) {
				this.component1.setEnabled(true);
				this.component1.requestFocus();
			}
			System.out.println(nomi_obj.getCid()+"=============");
			System.out.println(nomi_obj.getName()+"=============");
			System.out.println(nomi_obj.getDob()+"=============");
			System.out.println(nomi_obj.getSex()+"=============");
			System.out.println(nomi_obj.getRelation()+"=============");
			System.out.println(nomi_obj.getAddress()+"=============");
			
		}
		
		}
	}
	
	public NomineeObject getNomineeDetail() {
		
		return nomi_obj;
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent arg0) {

		
		if ( arg0.getSource() == txt_cid && arg0.getOppositeComponent() == txt_name) {
			
			if ( txt_cid.getText().toString().trim().length() > 0 && new Integer(txt_cid.getText().toString()).intValue() > 0) {
				
				setPanelData(new Integer(txt_cid.getText().toString().trim()).intValue());
				
			} else {
				JOptionPane.showMessageDialog(null, "Please Enter the CID");
			}
			
		}
		if(arg0.getSource()==txt_age && (txt_age.getText().length()!=0)) {
			
			int age = new Integer(txt_age.getText().toString().trim()).intValue() ;
			Calendar cal = Calendar.getInstance();
			String date = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR); 
            txt_dob.setText(Validation.subyear(date, age));
		
		}
		
	}
	
	
	public void setPanelData( int cid ) {
		
		StoreCidData getCId = new  StoreCidData();
		 
		 CustomerMasterObject cm = getCId.getCustomerID(cid);
		 
		 if ( cm != null ) {
			 
			 txt_name.setText(cm.getName());
			 txt_dob.setText(cm.getDOB());
			 combo_sex.setSelectedIndex(cm.getSex().equalsIgnoreCase("M")?0:1);
			 textarea_add.setText(cm.getAddress());
			 
			 if(cm.getBinaryImage()!=null){
				 lbl_photo.setIcon(new ImageIcon(cm.getBinaryImage(),"photo"));
             }
             else {
             	lbl_photo.setIcon(null);
             	System.out.println("photo is null");
             }
             
             if(cm.getBinarySignImage()!=null){
               lbl_sign.setIcon(new ImageIcon(cm.getBinarySignImage(),"Sign"));
             }
             else
             	lbl_sign.setIcon(null);
			 
		 }
		
	}
    
    public void clearNominee() {
    	
    	txt_cid.setText("");
    	txt_dob.setText("");
    	txt_age.setText("");
    	txt_name.setText("");
    	txt_relation.setText("");
    	textarea_add.setText("");
    	lbl_photo.setIcon(null);
    	lbl_sign.setIcon(null);
    	lbl_regno.setText("");
    }
}
