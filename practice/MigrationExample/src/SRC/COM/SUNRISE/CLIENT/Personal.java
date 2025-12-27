package SRC.COM.SUNRISE.CLIENT;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.*;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.*;


public class Personal extends JPanel implements FocusListener {

	private JTextArea textarea_address;
	private JLabel lbl_name,lbl_sign,lbl_photo,lbl_occupation,lbl_sex,lbl_scst,lbl_dob,lbl_age, lbl_addr_type;
	private JLabel lbl_category,lbl_sub_category,lbl_cid;
	private JComboBox combo_address_types;
	
	GridBagConstraints gbc;
	public CustomerMasterObject customer_obj;
    
    /*public Personal(JComponent component)
    {

    	this.component=component;
    	setLayout(new GridBagLayout());
    }*/
	
	Personal(){
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;  // components grow in both dimensions
		gbc.insets = new Insets(5,5,5,5);
		
		setLayout(new GridBagLayout());
		for( int i =0; i< 20; i++)
			addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0, 1, 1);
		
		addComponent(new JLabel("  "), this, 17, 19, 1.0, 1.0, 1, 1);
		
		
		JLabel customer_id=new JLabel("Customer ID :");
		customer_id.setHorizontalAlignment(SwingConstants.LEFT);
		addComponent(customer_id,this,1,0,0.0,0.0,3,1);
		
		addComponent(lbl_cid=new JLabel("     "),this,1,4,0.0,0.0,4,1);
		//lbl_cid.addKeyListener(new NumListener());
		lbl_cid.setForeground(new Color(253,50,100));//253,50,100
		
		
		addComponent(new JLabel("Name :"),this,1,9,0.0,0.0,2,1);
		
		addComponent( lbl_name = new JLabel(), this, 1, 11, 0.0, 0.0, 8, 1);
		//lbl_name.setFont(new Font("Times New Roman",Font.BOLD,11));
		
		addComponent(new JLabel("Address type:"),this,2,9,0.0,0.0,4,1);
		
		addComponent( lbl_addr_type = new JLabel(), this, 2, 13, 0.0, 0.0, 5, 1);
		
		JLabel category = new JLabel("Category:");
		addComponent(category,this,2,0,0.0,0.0,3,1);
		
		lbl_category = new JLabel();
		addComponent(lbl_category,this,2,4,0.0,0.0,3,1);
		
		
		JScrollPane jsp_guardian_address=new JScrollPane(textarea_address = new JTextArea(2,4));
        jsp_guardian_address.setPreferredSize(new Dimension(250,70));
		addComponent(jsp_guardian_address, this, 4, 0, 0.0, 0.0, 10, 4);
		
		
		addComponent(new JLabel("Photo"),this,9,0,0.0,0.0,2,1);
		JScrollPane jsp_ph = new JScrollPane(lbl_photo=new JLabel());
		addComponent(jsp_ph,this,10,0,0.0,0.0,6,4);
		jsp_ph.setPreferredSize(new Dimension(60,120));
		
		lbl_photo.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_photo.setBackground(new Color(253,254,239));
	
		
		JScrollPane js_sign = new JScrollPane(lbl_sign=new JLabel(""));
		addComponent(js_sign,this,10,7,0.0,0.0,6,4);
		js_sign.setPreferredSize(new Dimension(60,120));
		lbl_sign.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_sign.setBackground(new Color(253,254,239));

		
		addComponent(new JLabel("DOB :"),this,15,0,0.0,0.0,2,1);
		addComponent(lbl_dob=new JLabel(),this,15,3,0.0,0.0,3,1);
		lbl_dob.setFont(new Font("Times New Roman",Font.BOLD,11));
		lbl_dob.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_dob.setForeground(Color.BLUE);
		
		
		addComponent(new JLabel(" Sex :"),this,16,0,0.0,0.0,2,1);
		addComponent(lbl_sex=new JLabel(),this,16,3,0.0,0.0,1,1);
		lbl_sex.setForeground(Color.BLUE);
		lbl_sex.setFont(new Font("Times New Roman",Font.BOLD,11));
		
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
	/*	if(!(jt instanceof JLabel))
			jt.addFocusListener(this);*/
		jp.add(jt,gbc);
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setCustid(int cid ) {
		
		lbl_cid.setText(""+cid);
	}
	
	public void setCategory(String cat) {
		
		lbl_category.setText(cat);
	}

	public void setAddress(String str) {
		
		textarea_address.setText(str);
	}
	
	public void setphoto(byte[] str ) {
		
		if( str!= null){
			 lbl_photo.setIcon(new ImageIcon(str,"photo"));
            
        } else {
        	
        	System.out.println("str id null,,,,,,,,,,,,,,,,,,,,,,,");
        }
		
		
	}
	
	public void setSign(byte[] str ) {
		
		if(str!=null){
			 lbl_sign.setIcon(new ImageIcon(str,"Sign"));
           
       }
	}
	
	
	public void setDOB(String dat) {
		
		lbl_dob.setText(dat);
	}
	
	public void setSex(String sex) {
		
		lbl_sex.setText(sex);
	}
	
	public void setName(String str) {
		
		lbl_name.setText(str);
	}
	
	public int getCustomerID() {
		
		if ( lbl_cid.getText().toString().length() > 0 && !lbl_cid.getText().toString().equalsIgnoreCase("0") ) {
			
			return new Integer(lbl_cid.getText().toString()).intValue();
		}
		
			return 0;
	}
	
	public int getAddrType() {
		
		if ( lbl_addr_type.getText().toString().length() >0 ) 
				return new Integer(lbl_addr_type.getText().toString()).intValue();
		else 
		    return 0;		
	}
	
	public String getName() {
		
		
		if ( lbl_name.getText().toString().length() >0 ) 
			return (lbl_name.getText().toString());
	else 
	    return "";		
	}
	
	public Character getSex(){
		if(lbl_sex.getText().toString().length()>0)
			return(lbl_sex.getText().trim().charAt(0));
		else
			return 'O';
	}
	
	public void setPanelData(int cid ) {
		
		 StoreCidData getCId = new  StoreCidData();
		 
		 CustomerMasterObject cm = getCId.getCustomerID(cid);
		 
		 if ( cm != null ) {
			 
			 lbl_cid.setText(new Integer(cm.getCustomerID()).toString());
			 lbl_category.setText(cm.getCategory() == 0 ? "Individual" :"Institute");
			 lbl_name.setText(cm.getName());
			 lbl_addr_type.setText((new Integer(cm.getAdd_type()).toString()));
			 textarea_address.setText(cm.getAddress());
			 
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
			 
             lbl_dob.setText(cm.getDOB());
             lbl_sex.setText(cm.getSex());
			 
			 
		 }
		
	}
	
	public void clear() {
		
		lbl_cid.setText("");
		lbl_category.setText("");
		lbl_name.setText("");
		lbl_photo.setIcon(null);
		lbl_sign.setIcon(null);
		lbl_sex.setText("");
		lbl_dob.setText("");
		textarea_address.setText("");
		lbl_addr_type.setText("");
		
		
	}
	
}
