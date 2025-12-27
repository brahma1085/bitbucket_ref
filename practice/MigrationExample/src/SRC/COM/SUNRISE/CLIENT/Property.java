package SRC.COM.SUNRISE.CLIENT;

import java.awt.Component;
import java.awt.Container;
import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import SRC.COM.SUNRISE.SERVER.StoreProperty;
import SRC.COM.SUNRISE.UTILITY.FloatNumListener;
import SRC.COM.SUNRISE.UTILITY.NumListener;
import SRC.COM.SUNRISE.UTILITY.PropertyObject;

/**
 * Property class is used to capture the details about the property submitted
 * by the Customer for opening OD/CC Accoounts
 * Here details like property no/house no/site no,address,mesuments of site
 * market value of the property,how property acquired,tenants details are captured
 * This class is used in ODCCApplnDE.java
 */


public class Property extends JPanel implements KeyListener, ActionListener
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
    
    static Property prop;
    
	private GridBagConstraints gbc;
	//private DefaultTableModel dtm;
	//private JTable table_tenants_details;
	private JScrollPane jsp,jsp_list,jsp_list_nature;
	private PropertyObject propertyobject=null;
	private JTextField txt_east_by,txt_west_by,txt_south_by,txt_north_by,txt_property_value,txt_east_west,txt_north_south;
	public JTextField txt_property_no;
	private JTextArea textarea_address;
	private JList list_prperty_acquired_by;
	private Object array_obj_data[]=new Object[3];
	public JButton button_clear,button_submit,button_modify;
	private JComponent component;
	private TraversalPolicy  focus_policy;
	private JTextField acc_no,acc_type;
	public StoreProperty strprprt;
	public JTextField Text_tenant,Text_rent,Text_type;   
	public JComboBox nature_prop;
	public Property()
	{
          
		setLayout(new GridBagLayout());
		setBackground(Colors.setSubPanelColor());
		this.component=component;
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;  // components grow in both dimensions
		gbc.insets = new Insets(3,5,10,10);
		
		//addComponent(new JLabel("PROPERTY DETAILS",SwingConstants.CENTER),this,0,0,0.0,0.0,4,1);
		addComponent(new JLabel("Account Type"),this,0,0,0.0,0.0,3,1);
		addComponent(acc_type=new JTextField(),this,0,2,0.0,0.0,1,1);
		acc_type.addKeyListener(new NumListener());
		

		addComponent(new JLabel("Account Number"),this,0,3,0.0,0.0,3,1);
		addComponent(acc_no=new JTextField(),this,0,4,0.0,0.0,1,1);
		acc_no.addKeyListener(new NumListener());

		
		
		
		
		addComponent(new JLabel("Property No./House No./Site No."),this,1,0,0.0,0.0,3,1);
		addComponent(txt_property_no=new JTextField(),this,1,3,0.0,0.0,1,1);
		txt_property_no.addKeyListener(new NumListener());

		addComponent(new JLabel("Address"),this,2,0,0.0,0.0,1,1);
		addComponent(new JScrollPane(textarea_address=new JTextArea(3,4)),this,2,1,0.0,0.0,3,1);
		textarea_address.addKeyListener(this);

		addComponent(new JLabel("Measurements"),this,3,0,0.0,0.0,1,1);	
		
		addComponent(new JLabel("East-West"),this,3,1,0.0,0.0,1,1);				
		addComponent(txt_east_west=new JTextField(5),this,3,2,0.0,0.0,1,1);	
		//textarea_address.addKeyListener(new NextFocus(txt_east_west));
		addComponent(new JLabel("North-South"),this,3,3,0.0,0.0,1,1);
		addComponent(txt_north_south=new JTextField(5),this,3,4,0.0,0.0,1,1);

		addComponent(new JLabel("Boundaries"),this,5,2,0.0,0.0,3,1);
		
		addComponent(new JLabel("East By"),this,7,0,0.0,0.0,1,1);
		addComponent(txt_east_by=new JTextField(),this,7,1,0.0,0.0,1,1);
		addComponent(new JLabel("West By  "),this,7,2,0.0,0.0,1,1);
		addComponent(txt_west_by=new JTextField(),this,7,3,0.0,0.0,1,1);
		addComponent(new JLabel("North By  "),this,8,0,0.0,0.0,1,1);
		addComponent(txt_north_by=new JTextField(),this,8,1,0.0,0.0,1,1);
		addComponent(new JLabel("South By  "),this,8,2,0.0,0.0,1,1);
		addComponent(txt_south_by=new JTextField(5),this,8,3,0.0,0.0,1,1);	
		addComponent(new JLabel(),this,8,4,0.0,0.0,1,1);

		addComponent(new JLabel("Appx Market value of the Property(Rs)"),this,9,0,0.0,0.0,3,1);
		addComponent(txt_property_value=new JTextField(),this,9,3,0.0,0.0,2,1);
		txt_property_value.addKeyListener(new FloatNumListener());
		txt_property_value.setHorizontalAlignment(SwingConstants.RIGHT);

		addComponent(new JLabel("How the property is acquired"),this,10,0,0.0,0.0,3,3);
		Object data[]=new Object[5];
		data[1]="Ancestral";
		data[2]="Partitioned Property";
		data[3]="Self Acquired";
		data[4]="Joint Family Property";

	
		addComponent(jsp_list=new JScrollPane(list_prperty_acquired_by=new JList(data)),this,10,3,0.0,0.0,2,3);	
		jsp_list.setPreferredSize(new Dimension(80,80));

		
		addComponent(new JLabel("Nature of property"),this,14,0,0.0,0.0,3,1);
	     addComponent(nature_prop=new JComboBox(),this,14,3,0.0,0.0,2,1);
	     nature_prop.addItem("Site");
	     nature_prop.addItem("Site with building");
	     nature_prop.addItem("Residential");
	     nature_prop.addItem("Commercial");
	     nature_prop.addItem("Corner site");
	     nature_prop.addItem("Partly residential and partly commercial");
	     nature_prop.addKeyListener(this);
	     
		
	     
		
		
		
		
		
		addComponent(new JLabel("TENANT/LESSOR NAME"),this,15,0,0.0,0.0,3,1);
		addComponent(Text_tenant=new JTextField(),this,15,3,0.0,0.0,2,1);
		Text_tenant.addKeyListener(this);
		
		
		addComponent(new JLabel("MONTHLY RENT"),this,16,0,0.0,0.0,3,1);
		addComponent(Text_rent=new JTextField(),this,16,3,0.0,0.0,2,1);
		Text_rent.addKeyListener(new NumListener());
		
		
		
		addComponent(new JLabel("Appx Market value of the Property(Rs)"),this,17,0,0.0,0.0,3,1);
		addComponent(Text_type=new JTextField(),this,17,3,0.0,0.0,2,1);
		Text_type.addKeyListener(new FloatNumListener());
		
		
		
		
		
			
		addComponent(button_submit=new JButton("SUBMIT"),this,21,1,0.0,0.0,1,1);
		button_submit.addActionListener(this);

		addComponent(button_clear=new JButton("CANCEL"),this,21,2,0.0,0.0,1,1);
		button_clear.addActionListener(this);
		
		addComponent(button_modify=new JButton("MODIFY"),this,21,3,0.0,0.0,1,1);
		button_modify.addActionListener(this);


		//table_tenants_details.addKeyListener(new NextFocus(button_submit));
		
		if(false)
		{
			makeDisabled(false);
			setEnabled(false);
		}
		else
		{
			if(txt_property_no.getText().trim().length()>0)
			{	
				setEnabled(false);
				button_modify.setEnabled(true);
				makeDisabled(false);
			}
			else
			{
				setEnabled(true);
				button_modify.setEnabled(false);				
			}
		}
		clearForm();
		focus_policy=new TraversalPolicy(this);
}	
	
public void setEnabled(boolean flag)
{
	button_submit.setEnabled(flag);
	button_clear.setEnabled(flag);
	button_modify.setEnabled(flag);
}

public void keyPressed(KeyEvent keyevent)
{
/**
 * option is provided to insert as many required tenants details or 
 * to delete the entered details
 */
	
	
	
	/*else if(keyevent.getSource()==textarea_address)
	{
	    if(keyevent.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
	        txt_east_west.requestFocus();
		}
	}*/
	
	if(keyevent.getKeyCode()==KeyEvent.VK_ESCAPE)
	{	
	    System.out.println("KeyPressed ESCAPE..");
	    propertyobject=getDetails(1);
		if(propertyobject!=null)
		{
	
			setEnabled(false);
			button_modify.setEnabled(true);
			makeDisabled(false);
			if(component!=null)
				component.requestFocus();
		}
		else
		{
			if(component!=null)
				component.requestFocus();
		}
	}
	
	
}
public void setFocusToPropertyNo()
{
	if(txt_property_no.isEnabled()){
		Property.this.scrollRectToVisible(txt_property_no.getBounds());
		txt_property_no.requestFocus();
	}
	else{
		Property.this.scrollRectToVisible(button_modify.getBounds());
		button_modify.requestFocus();
	}
}

//added on 25-10-2010
public static Property getInstance(int i) {
	
	if ( i == 0)
		return	prop = new Property();
	
	if ( prop == null )
	    return	prop = new Property();
	 
	return prop;
}






public void keyReleased(KeyEvent e){} 
public void keyTyped(KeyEvent e){}

public void clearForm()
{
	txt_property_no.setText("");
	txt_east_by.setText("");
	txt_west_by.setText("");
	txt_south_by.setText("");
	txt_north_by.setText("");
	
	txt_property_value.setText("0.00");
	txt_property_value.setSelectionStart(0);
	txt_property_value.setSelectionEnd(1);
	
	txt_east_west.setText("");
	txt_north_south.setText("");
	textarea_address.setText("");
	
	Object data[]=new Object[3];
	for(int i=0;i<data.length;i++)
	    data[i]="";
	
	// New Code added by Murugesh 12/11/2005
	makeDisabled(true);
	list_prperty_acquired_by.setSelectedIndex(0);

	button_submit.setEnabled(true);
	button_clear.setEnabled(true);
	button_modify.setEnabled(false);
	//
}

public void makeDisabled(boolean flag)
{
	txt_property_no.setEnabled(flag);
	txt_east_by.setEnabled(flag);
	txt_west_by.setEnabled(flag);
	txt_south_by.setEnabled(flag);
	txt_north_by.setEnabled(flag);
	txt_property_value.setEnabled(flag);
	txt_east_west.setEnabled(flag);
	txt_north_south.setEnabled(flag);
	textarea_address.setEnabled(flag);
	
	list_prperty_acquired_by.setEnabled(flag);
	
	
	if(!flag)
	{
		setEnabled(false);
		button_modify.setEnabled(true);
	} 
}


public PropertyObject getDetails(int flag)
{
	propertyobject=null;
	Object array_obj_data[][]=null;
	
	if((flag==1)?txt_property_no.getText().trim().length()>0:true)
	{
		JComponent[] array_jt={txt_property_no,textarea_address,txt_east_west,txt_north_south,txt_east_by,txt_west_by,txt_north_by,txt_south_by,txt_property_value};
		String[] fieldnames={" Property No "," Address "," East-West Mesurement ","  North-South Mesurement"," East By Boundary"," West By Boundary"," North By Boundary"," South By Boundary"," Property Value"};

		for(int i=0;i<(array_jt.length-2);i++)
			/*if((i==1)?(((JTextArea)array_jt[i]).getText().trim().length()==0):(((JTextField)array_jt[i]).getText().trim().length()==0))
			{
							
				JOptionPane.showMessageDialog(this,"Enter "+fieldnames[i]);
				array_jt[i].requestFocus();
				return null;
			}*/
			
		if(list_prperty_acquired_by.getSelectedIndex()==-1)	
		{
			JOptionPane.showMessageDialog(this,"Select Property Acquired By");
			list_prperty_acquired_by.requestFocus();
				return null;
		}
		
		
		
		propertyobject=new PropertyObject();
		propertyobject.setAc_no(acc_no.getText().trim());
		propertyobject.setAc_type(acc_type.getText().trim());
		propertyobject.setPropertyNo(txt_property_no.getText().trim());	
		propertyobject.setAddress(textarea_address.getText().trim());	
		propertyobject.setMeasurementEW(txt_east_west.getText());	
		propertyobject.setMeasurementNS(txt_north_south.getText());	
		System.out.println("Inside getdetails111111"+txt_north_south.getText());
		propertyobject.setEastBy(txt_east_by.getText().trim());	
		propertyobject.setWestBy(txt_west_by.getText().trim());	
		propertyobject.setNorthBy(txt_north_by.getText().trim());	
		propertyobject.setSouthBy(txt_south_by.getText().trim());	
		propertyobject.setPropertyValue(Double.parseDouble(txt_property_value.getText().trim()));
		propertyobject.setTenant(Text_tenant.getText().trim());
		propertyobject.setRent(Text_rent.getText().trim());
		propertyobject.setType(Text_type.getText().trim());
		
		if(nature_prop.getSelectedIndex()>0){
			switch (nature_prop.getSelectedIndex()) {
			case 1:
				propertyobject.setPropertyNature("Site");//FD	   
				break;
			case 2:
				propertyobject.setPropertyNature("Site with building");//RD	   
				break;
			case 3:
				propertyobject.setPropertyNature("Residential");//TMCC	   
				break;
			case 4:
				propertyobject.setPropertyNature("Commercial");//BLCC	   
				break;
			case 5:
				propertyobject.setPropertyNature("Corner site");//BLCC	   
				break;
			case 6:
				propertyobject.setPropertyNature("Partly residential and partly commercial");//BLCC	   
				break;
			}
	      }
		
		
		
		
		
		
		propertyobject.setPropertyAqdBy(list_prperty_acquired_by.getSelectedValue().toString());	
			
		
		
		
}
	return (propertyobject);
}

public void focusLost(FocusEvent fe)
{
	if(fe.getSource()==acc_type){
	try{
	ResultSet rs=null;
	if(acc_type.getText().toString().trim().length()>0&& acc_no.getText().toString().trim().length()>0)
	{	int acno=Integer.parseInt(acc_no.getText().toString().trim());
	int ac_typ=Integer.parseInt(acc_type.getText().toString().trim());
	
		 rs=strprprt.accesssProperty(acno,ac_typ);
		}
	acc_no.setText(rs.getString(1));
	acc_type.setText(rs.getString(2));
	txt_property_no.setText(rs.getString(3));	
	textarea_address.setText(rs.getString(4));	
	txt_east_west.setText(rs.getString(5));	
	txt_north_south.setText(rs.getString(6));	
	txt_east_by.setText(rs.getString(7));	
	txt_west_by.setText(rs.getString(8));	
	txt_north_by.setText(rs.getString(9));
	System.out.println(rs.getString(1));
	txt_south_by.setText(rs.getString(10));	
	txt_property_value.setText(rs.getString(11));	
	
	
	}
	catch(Exception e){e.printStackTrace();}
}
}


public void setDetails(PropertyObject prop)
{
	if(prop!=null)
	{
	clearForm();
	System.out.println("Property Details Set values"+prop);
	txt_property_no.setText(String.valueOf(prop.getPropertyNo()));	
	textarea_address.setText(prop.getAddress());	
	txt_east_west.setText(prop.getMeasurementEW());	
	txt_north_south.setText(prop.getMeasurementNS());	
	txt_east_by.setText(prop.getEastBy());	
	txt_west_by.setText(prop.getWestBy());	
	txt_north_by.setText(prop.getNorthBy());
	System.out.println("Inside setdetails111111"+prop.getNorthBy());
	txt_south_by.setText(prop.getSouthBy());	
	txt_property_value.setText(String.valueOf(prop.getPropertyValue()));	
	list_prperty_acquired_by.setSelectedValue(prop.getPropertyAqdBy(),true);	
	
	System.out.println("Inside setdetails22222"+prop.getPropertyNature());
	
	
	}
	System.out.println("outside setdetails33333");
}

public void addComponent (JComponent jt,JPanel jp,int gy,int gx,double wx,double wy,int gw,int gh)
{
	gbc.gridx=gx;
	gbc.gridy=gy;
	gbc.weightx=wx;
	gbc.weighty=wy;
	gbc.gridheight=gh;
	gbc.gridwidth=gw;
	//jt.setFont(new Font("Times New Roman",Font.BOLD,11));
	if(!(jt instanceof JLabel))
		jt.addKeyListener(this);

	if(jt instanceof JButton )
		jt.setBackground(Colors.setButtonColor());
	jp.add(jt,gbc);
}

public void actionPerformed(ActionEvent e) 
{
	if(e.getSource()==button_clear)
	{
		clearForm();
		txt_property_no.requestFocus();
		this.scrollRectToVisible(txt_property_no.getBounds());
	}
	else if(e.getSource()==button_submit)
	{
		if(submitValidations())	{
		propertyobject=null;
		propertyobject=getDetails(0);
		if(propertyobject!=null){
			int confirm=JOptionPane.showConfirmDialog(null,"Data Ok ?",null,JOptionPane.YES_NO_OPTION);
			if(confirm==JOptionPane.YES_OPTION){	
				setEnabled(false);
				button_modify.setEnabled(true);
				makeDisabled(false);
				//if(!component.isEnabled())
				button_modify.requestFocus();
				/*if(component!=null)
					component.requestFocus();*/
			}
			else
				txt_property_no.requestFocus();
		}
		}
		
		strprprt=new StoreProperty();
		Boolean flg=strprprt.storePropertyMaster(propertyobject);
		if(flg==true)
			JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
		else
			JOptionPane.showMessageDialog( null, "Data Not Inserted" );
			
		
		
		
	}
	else if(e.getSource()==button_modify)
	{
		makeDisabled(true);
		setEnabled(true);
		button_modify.setEnabled(false);
		txt_property_no.requestFocus();
		
	}	
}

boolean submitValidations(){
	
	boolean flag_not_set=true;
	String field_name[] ={"Property No./House No./Site No.","East-West","North-South","East By","West By","North By","South By","Appx Market value of the Property"};
	JTextField field_components[]={txt_property_no,txt_east_west,txt_north_south,txt_east_by,txt_west_by,txt_north_by,txt_south_by,txt_property_value};
	
	for(int i=0;i<field_components.length;i++){
		if(field_components[i].getText().trim().length()==0){
			System.out.println("Fieldnames"+field_name[i]+"Pos"+i);
			if((i==1)||(i==2)||(i==3)||(i==4)||(i==5)||(i==6))
				break;
			JOptionPane.showMessageDialog(this,"Enter "+field_name[i]+" field");
			flag_not_set=false;
			break;
		}
		//if(i==7 && Integer.parseInt(field_components[i].getText().trim())==0)
		if(i==7 && Double.parseDouble(field_components[i].getText().trim())==0)
		{
			JOptionPane.showMessageDialog(this,"Market value of the Property cannot be zero");
			flag_not_set=false;
			break;
		}
	}
	if(flag_not_set){
		if(textarea_address.getText().trim().length()==0){
			JOptionPane.showMessageDialog(this,"Enter Address");
			flag_not_set=false;
		}
		else if(list_prperty_acquired_by.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(this,"Select Property Acquired By");
				flag_not_set=false;
			}
		
		/*else{
			int table_tenants_details_flag=0;
			for(int i=0;i<table_tenants_details.getRowCount();i++)
				for (int j=0;j<2;j++){
					if(table_tenants_details.getValueAt(i,j).toString().trim().length()==0)
						table_tenants_details_flag=1;
				}
			if(table_tenants_details_flag==1){
				JOptionPane.showMessageDialog(this,"Enter the Tenant Table correctly");
				flag_not_set=false;
			}
		}*/
	}
	return flag_not_set;
}
class TraversalPolicy extends ContainerOrderFocusTraversalPolicy{
	private TraversalPolicy(Property this_panel){
 		this_panel.setFocusable(true);
 		this_panel.setFocusCycleRoot(true);
 		this_panel.setFocusTraversalPolicy(this);
 	}
	
	public Component getDefaultComponent(Container con) {
		return txt_property_no;
	}
	
	public Component getFirstComponent(Container con) {
		return txt_property_no;
	}

	public Component getLastComponent(Container con) {
		return null;
	}

	public Component getComponentAfter(Container con, Component component1) 
	{
		if(component1==txt_property_no){
			Property.this.scrollRectToVisible(textarea_address.getBounds());
			return textarea_address;
		}
		else if(component1==textarea_address){
			Property.this.scrollRectToVisible(txt_east_west.getBounds());
			return txt_east_west;
		}
		else if(component1==txt_east_west){
			Property.this.scrollRectToVisible(txt_north_south.getBounds());
			return txt_north_south;
		}
		else if(component1==txt_north_south){
			Property.this.scrollRectToVisible(txt_east_by.getBounds());
			return txt_east_by;
		}
		else if(component1==txt_east_by){
			Property.this.scrollRectToVisible(txt_west_by.getBounds());
			return txt_west_by;
		}
		else if(component1==txt_west_by){
			Property.this.scrollRectToVisible(txt_north_by.getBounds());
			return txt_north_by;
		}
		else if(component1==txt_north_by){
			Property.this.scrollRectToVisible(txt_south_by.getBounds());
			return txt_south_by;
		}
		else if(component1==txt_south_by){
			Property.this.scrollRectToVisible(txt_property_value.getBounds());
			return txt_property_value;
		}
		else if(component1==txt_property_value){
			Property.this.jsp_list.scrollRectToVisible(list_prperty_acquired_by.getBounds());
			return list_prperty_acquired_by;
		}
		
		
		
		else if(component1==button_submit && button_submit.isEnabled()){
			Property.this.scrollRectToVisible(button_clear.getBounds());
			return button_clear;
		}
		else if(component1==button_submit && !(button_submit.isEnabled())){
			return component;
		}
		else if(component1==button_modify && txt_property_no.isEnabled()){
			Property.this.scrollRectToVisible(txt_property_no.getBounds());
			return txt_property_no;
		}
		else if(component1==button_modify  && !(txt_property_no.isEnabled())){
			return component;
		}
		else if(component1==button_clear){
			Property.this.scrollRectToVisible(txt_property_no.getBounds());
			return Property.this.component;
		}
		else
			return null;
	
		}
	public Component getComponentBefore(Container con, Component component1){
		
		
		 
		 if(component1==list_prperty_acquired_by){
			Property.this.scrollRectToVisible(txt_property_value.getBounds());
			return txt_property_value;
		}
		else if(component1==txt_property_value){
			Property.this.scrollRectToVisible(txt_south_by.getBounds());
			return txt_south_by;
		}
		else if(component1==txt_south_by){
			Property.this.scrollRectToVisible(txt_north_by.getBounds());
			return txt_north_by;
		}
		else if(component1==txt_north_by){
			Property.this.scrollRectToVisible(txt_west_by.getBounds());
			return txt_west_by;
		}
		else if(component1==txt_west_by){
			Property.this.scrollRectToVisible(txt_east_by.getBounds());
			return txt_east_by;
		}
		else if(component1==txt_east_by){
			Property.this.scrollRectToVisible(txt_north_south.getBounds());
			return txt_north_south;
		}
		else if(component1==txt_north_south){
			Property.this.txt_east_west.scrollRectToVisible(txt_east_west.getBounds());
			return txt_east_west;
		}
		else if(component1==txt_east_west){
			Property.this.scrollRectToVisible(textarea_address.getBounds());
			return textarea_address;
		}
		else if(component1==textarea_address){
			Property.this.scrollRectToVisible(txt_property_no.getBounds());
			return txt_property_no; 
		}
		else if(component1==txt_property_no){
			return component;
		}
		else if(component1==button_modify && button_modify.isEnabled()){
			return component;
		}
		else if(component1==button_clear){
			Property.this.scrollRectToVisible(button_submit.getBounds());
			return button_submit;
		}
		
		else 
			return null;
		
				
		
		
		
		
	}
	
	}

public void makeitDisable(){
	txt_property_no.setEnabled(false);
	txt_east_by.setEnabled(false);
	txt_west_by.setEnabled(false);
	txt_south_by.setEnabled(false);
	txt_north_by.setEnabled(false);
	txt_property_value.setEnabled(false);
	txt_east_west.setEnabled(false);
	txt_north_south.setEnabled(false);
	textarea_address.setEnabled(false);
	
	list_prperty_acquired_by.setEnabled(false);
	
	button_submit.setEnabled(false);
	button_clear.setEnabled(false);
	button_modify.setEnabled(true);
	
}
}

