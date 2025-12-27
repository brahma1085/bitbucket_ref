package SRC.COM.SUNRISE.CLIENT;

import java.awt.Component;
import java.awt.Container;
import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import SRC.COM.SUNRISE.UTILITY.*;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;
import SRC.COM.SUNRISE.SERVER.*;

public class Vehicle extends JPanel implements KeyListener, ActionListener,FocusListener 
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
    static Vehicle vc;
	GridBagConstraints gbc;
	private JComboBox combo_loan_purpose;
	private JComboBox combo_vehfor,combo_type,combo_ac_type;
	public JButton button_clear,button_submit,button_modify;
	private DefaultTableModel dtm;
	private JTable income;
	private JScrollPane jsp;
	private VehicleObject vech=null;
	private JTextField txt_cost,txt_licenceno,txt_permitno,txt_area_used,txt_validity,txt_permitvalid,lnacno,txt_ac_no;
	public JTextField txt_make_type;
	private JTextArea txtarea_addrparking,txtarea_addrdealer,txtarea_other;
	private TraversalPolicy focus_policy;
	private JComponent component1;
	JLabel namel;
	LoanMasterObject lmobj=null,loanobj=null;
	StoreVehicle vcl =new StoreVehicle();
	
	private Rectangle top_view = new Rectangle(0,0,467,338);
	private Rectangle bottom_view = new Rectangle(0,214,467,338);
	public Vehicle(JComponent component){
		this();
		this.component1=component;
		/*if(txt_make_type.isEnabled())
			txt_make_type.requestFocus();
		else
			button_modify.requestFocus();*/ 
	}
	public Vehicle()
	{

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;  // components grow in both dimensions
		gbc.insets = new Insets(3,5,10,10);
		
		setBackground(Colors.setSubPanelColor());
		
		addComponent(new JLabel("VEHICLE DETAILS",SwingConstants.CENTER),this,0,0,0.0,0.0,4,1);
		
		gbc.insets = new Insets(3,5,3,5);
		for (int i= 0; i< 37; i++)
			//addComponent(new JLabel("      "), this, 0, i, 0.0, 0.0 , 1, 1 );
		
	//	addComponent(new JLabel(" "), this, 30, 32, 1.0, 1.0 , 1, 1 );
		
		//addComponent(new JLabel("Loans Data Entry"), this, 0, 15, 0.0, 0.0, 3, 1);
		
		addComponent(new JLabel("Loan Acc type"), this,1,0,0.0,0.0,2,1);
		addComponent( combo_ac_type = new JComboBox() , this, 1,1,0.0,0.0,1,1);
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
		

		addComponent(new JLabel("Loan Acc No"), this,2,0,0.0,0.0,2,1);
		addComponent( txt_ac_no = new JTextField() , this, 2,1,0.0,0.0,2,1);
		txt_ac_no.addFocusListener(this);
		NumListener numlist = new NumListener();
		txt_ac_no.addKeyListener( numlist );
		txt_ac_no.addFocusListener( this );
		
		

		addComponent(new JLabel("Make & type of vehicle"),this,3,0,0.0,0.0,2,1);
		addComponent(txt_make_type=new JTextField(10),this,3,1,0.0,0.0,1,1);

		addComponent(new JLabel("Cost of vehicle as per invoice/quotation"),this,4,0,0.0,0.0,2,1);
		addComponent(txt_cost=new JTextField(8),this,4,2,0.0,0.0,1,1);
		txt_cost.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_cost.addKeyListener(new FloatNumListener());

		addComponent(new JLabel("Name & Address of the Authorised Dealers"),this,5,0,0.0,0.0,3,1);
		addComponent(new JScrollPane(txtarea_addrdealer=new JTextArea(3,4)),this,6,0,0.0,0.0,2,1);
		//txtarea_addrdealer.addKeyListener(this);

		addComponent(new JLabel("Licence No and validity"),this,7,0,0.0,0.0,1,1);
		addComponent(txt_licenceno=new JTextField(10),this,7,1,0.0,0.0,1,1);
		txt_licenceno.addKeyListener(new AlphaNumListener(10));
		
		addComponent(txt_validity=new JTextField(5),this,7,2,0.0,0.0,1,1);
		txt_validity.addKeyListener(new DateListener());

		addComponent(new JLabel("Type of the Vehicle "),this,8,0,0.0,0.0,1,1);
		addComponent(combo_type=new JComboBox(),this,8,1,0.0,0.0,2,1);
		combo_type.addItem("Light Motor Vehicle");
		combo_type.addItem("Heavy Motor Vehicle");

		addComponent(new JLabel("Permit No and Validity"),this,9,0,0.0,0.0,1,1);
		addComponent(txt_permitno=new JTextField(10),this,9,1,0.0,0.0,1,1);
		txt_permitno.addKeyListener(new AlphaNumListener(10));
		addComponent(txt_permitvalid=new JTextField(),this,9,2,0.0,0.0,1,1);
		txt_permitvalid.addKeyListener(new DateListener());

		addComponent(new JLabel("Vehicle for "),this,10,0,0.0,0.0,1,1);
		addComponent(combo_vehfor=new JComboBox(),this,10,1,0.0,0.0,1,1);
		combo_vehfor.addItem(" own use");
		combo_vehfor.addItem(" hire");

		addComponent(new JLabel("Area where the vehicle is going to be used"),this,11,0,0.0,0.0,2,1);
		addComponent(txt_area_used=new JTextField(),this,11,2,0.0,0.0,1,1);
		txt_area_used.addKeyListener(new AlphaNumListener(25));

		addComponent(new JLabel("Address where the vehicle is parked overnight"),this,12,0,0.0,0.0,3,1);
		addComponent(new JScrollPane(txtarea_addrparking=new JTextArea(3,4)),this,13,0,0.0,0.0,2,1);
		//txtarea_addrparking.addKeyListener(this);

		addComponent(new JLabel("Applicant is owning any other vehicle..? if yes give full details"),this,14,0,0.0,0.0,3,1);
		addComponent(new JScrollPane(txtarea_other=new JTextArea(3,4)),this,15,0,0.0,0.0,2,1);
		//txtarea_other.addKeyListener(this);
		
		addComponent(button_clear=new JButton("CANCEL"),this,16,0,0.0,0.0,1,1);
		addComponent(button_submit=new JButton("SUBMIT"),this,16,1,0.0,0.0,1,1);
		addComponent(button_modify=new JButton("MODIFY"),this,16,2,0.0,0.0,1,1);
		
		button_submit.addActionListener(this);
		button_clear.addActionListener(this);
		button_modify.addActionListener(this);
		
		button_modify.addFocusListener(this);
		txtarea_addrdealer.addKeyListener(new NextFocus(txt_licenceno));
		txtarea_addrparking.addKeyListener(new NextFocus(txtarea_other));
		txtarea_other.addKeyListener(new NextFocus(button_submit));
		
		/*button_clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource()==button_submit)
				 submitValidation();
			}
		})*/
		
		focus_policy=new TraversalPolicy(this);
	}

public void clearForm()
{
	txt_make_type.setText("");
	
	txt_cost.setText("0.00");
	txt_cost.setSelectionStart(0);
	txt_cost.setSelectionEnd(1);
	txt_validity.setText("");
	txt_licenceno.setText("");
	txt_permitno.setText("");
	txt_area_used.setText("");
	txt_permitvalid.setText("");
	txtarea_addrparking.setText("");
	txtarea_addrdealer.setText("");
	txtarea_other.setText("");
	combo_vehfor.setSelectedIndex(1);
	combo_type.setSelectedIndex(1);
	makeDisabled(true);
	button_modify.setEnabled(false);
}

public void makeDisabled(boolean value)
{
	txt_make_type.setEnabled(value);
	txt_cost.setEnabled(value);
	txt_licenceno.setEnabled(value);
	txt_permitno.setEnabled(value);
	txt_area_used.setEnabled(value);
	txt_permitvalid.setEnabled(value);
	txtarea_addrparking.setEnabled(value);
	txtarea_addrdealer.setEnabled(value);
	txtarea_other.setEnabled(value);
	combo_vehfor.setEnabled(value);
	combo_type.setEnabled(value);
	txt_validity.setEnabled(value);
	
	button_clear.setEnabled(value);
	button_submit.setEnabled(value);
	button_modify.setEnabled(value);
}

public void makeDisabled()
{
	
	txt_make_type.setEnabled(false);
	txt_cost.setEnabled(false);
	txt_licenceno.setEnabled(false);
	txt_permitno.setEnabled(false);
	txt_area_used.setEnabled(false);
	txt_permitvalid.setEnabled(false);
	txtarea_addrparking.setEnabled(false);
	txtarea_addrdealer.setEnabled(false);
	txtarea_other.setEnabled(false);
	txt_validity.setEnabled(false);
	combo_vehfor.setEnabled(false);
	combo_type.setEnabled(false);
	
	button_clear.setEnabled(false);
	button_submit.setEnabled(false);
	button_modify.setEnabled(false);
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
	if(jt instanceof JButton || jt instanceof JComboBox )
		jt.setBackground(Colors.setButtonColor());
	jp.add(jt,gbc);
}

public void keyPressed(KeyEvent e)
{
	if(e.getSource()==txtarea_addrdealer)
	{	
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			txt_licenceno.requestFocus();
		}
	}
	else if(e.getSource()==txtarea_addrparking)
	{	
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			txtarea_other.requestFocus();
		}			
	}
	/*else if(e.getSource()==txtarea_other)
	{	
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			.requestFocus();
		}			
	}		*/
}


//added on 25-10-2010****************


public static Vehicle getInstance(int i) {
	
	if ( i == 0)
		return	vc = new Vehicle();
	
	if ( vc == null )
	    return	vc = new Vehicle();
	 
	return vc;
}



public void keyReleased(KeyEvent arg0) {}

public void keyTyped(KeyEvent arg0) {}

public void actionPerformed(ActionEvent e) 

{

	String fields_name[] = {"Type of Vechile","Licence Number","Licence Validity","Permit Number","Permit Validity","Vechile Area"};
	JTextField txt_fields[]={txt_make_type,txt_licenceno,txt_validity,txt_permitno,txt_permitvalid,txt_area_used};
	
	
			if(e.getSource() == button_submit){
				
				VehicleObject  vc2 = new  VehicleObject();
				switch(combo_ac_type.getSelectedIndex()){
				case 0:
					   vc2.setCombo_ac_type("1010014");//Surety Loan
				       break;
				case 1:
					   vc2.setCombo_ac_type("1010006");//Vehicle Loan
				       break;
				case 2:
					   vc2.setCombo_ac_type("1010017");//Staff Vehicle Loan
				       break;
				case 3:
					   vc2.setCombo_ac_type("1010015");//Personal Loan
				       break;
				case 4:
					   vc2.setCombo_ac_type("1010003");//Housing Loan
				       break;
				case 5:
					   vc2.setCombo_ac_type("1010018");//NSC/KVP Loan
				       break;
				case 6: 
					   vc2.setCombo_ac_type("1010019"); //BLCC Loan 
				       break;
				case 7: 
					  vc2.setCombo_ac_type("1015001"); //OverDraft Loan 
				       break;
				case 8: 
					   vc2.setCombo_ac_type("1014001"); //Cash Credit Loan 
				       break;
				case 9:
					   vc2.setCombo_ac_type("1010020"); //TMCB C/C Loan 
				       break;
				case 10:
					   vc2.setCombo_ac_type("1010021"); //Decretal Overdraft Loan 
				       break;
				case 11:
					   vc2.setCombo_ac_type("1010022"); //Decretal Cash Credit Loan 
				       break;
				   }
				
				vc2.setTxt_ac_no(txt_ac_no.getText().toString());
				vc2.setVehicleMake(txt_make_type.getText().toString());
				vc2.setVehicleCost(new Double(txt_cost.getText()));
				vc2.setAddressDealer(txtarea_addrdealer.getText());
				vc2.setLicenceNo(txt_licenceno.getText());
				vc2.setLicenceValidity(txt_validity.getText());
				vc2.setVehicleType(combo_type.getSelectedItem().toString());
				vc2.setPermitNo(txt_permitno.getText());
				vc2.setPermitValidity(txt_permitvalid.getText());
				vc2.setVehicleFor(combo_vehfor.getSelectedItem().toString());
				vc2.setArea(txt_area_used.getText());
				vc2.setAddressParking(txtarea_addrparking.getText());
				vc2.setOtherDet(txtarea_other.getText());
				
				Boolean flg= vcl.storeVehicle(vc2);
				
				if(flg==true){
					JOptionPane.showMessageDialog( null, "Sucessfully Inserted" );
				}
				else{
					JOptionPane.showMessageDialog( null, "SORRY" );
				}
			
			}
			else{
				System.out.println("plzzzz enter the propoer data........");
			}
	
	
			
		}
	
	public void focustoText(){
		if(txt_ac_no.isEnabled()){
			topView();
			txt_ac_no.requestFocus();
		}
		else {
			bottomView();
			txt_make_type.requestFocus();
		}
	}
	
	public void topView() {
		this.scrollRectToVisible(top_view);
	}
	
	public void bottomView() {
		this.scrollRectToVisible(bottom_view);
	}
	
	class TraversalPolicy extends ContainerOrderFocusTraversalPolicy{
		private TraversalPolicy(Vehicle  this_panel){
	 		this_panel.setFocusable(true);
	 		this_panel.setFocusCycleRoot(true);
	 		this_panel.setFocusTraversalPolicy(this);
	 	}
		
		public Component getDefaultComponent(Container con) {
			return txt_ac_no;
		}
		
		public Component getFirstComponent(Container con) {
			return txt_ac_no;
		}

		public Component getLastComponent(Container con) {
			return null;
		}

		public Component getComponentAfter(Container con, Component component) 
		{
			
			if(component==button_submit){
				bottomView();
				return button_clear; 
			}
			
			if(component==button_clear){
				return component1; 
			}
			
			if(component==button_modify ){
				if(txt_make_type.isEnabled()){
					topView();
					return txt_make_type;
				}
			}
			else if(component==txt_make_type){
				topView();
				return txt_cost; 
			}
			else if(component==txt_cost){
				topView();
				return txtarea_addrdealer;
			}
			else if(component==txtarea_addrdealer){
				topView();
				return txt_licenceno; 
			}
			else if(component==txt_licenceno){
				topView();
				return txt_validity;
			}
			else if(component==txt_validity){
				topView();
				return combo_type;
			}
			else if(component==combo_type){
				topView();
				return txt_permitno; 
			}
			else if(component==txt_permitno){
				topView();
				return txt_permitvalid;
			}
			else if(component==txt_permitvalid){
				bottomView();
				return combo_vehfor; 
			}
			else if(component==combo_vehfor){
				bottomView();
				return txt_area_used;
			}
			else if(component==txt_area_used){
				bottomView();
				return txtarea_addrparking; 
			}
			else if(component==txtarea_addrparking){
				bottomView();
				return txtarea_other;
			}
			else if(component==txtarea_other){
				bottomView();
				return button_submit;
			}
			else if(component==button_modify){
				topView();
				return component1;
			}
			else if(component==button_submit && button_submit.isEnabled()){
				bottomView();
				return button_clear ;
			}
			/*else if(component==button_clear){
				txt_make_type.scrollRectToVisible(txt_make_type.getBounds());
				return txt_make_type;
			}*/
			 
			
			return null;
		}
		public Component getComponentBefore(Container con, Component component){
			
			if(component==txt_make_type){
				topView();
				return component1;
			}
			else if(component==txt_cost){
				topView();
				return txt_make_type;
			}
			else if(component==txtarea_addrdealer){
				topView();
				return txt_cost; 
			}
			else if(component==txt_licenceno){
				topView();
				return txtarea_addrdealer;
			}
			else if(component==txt_validity){
				topView();
				return txt_licenceno; 
			
			}
			else if(component==combo_type){
				topView();
				return txt_validity;
			}
			else if(component==txt_permitno){
				topView();
				return combo_type;
			}
			else if(component==txt_permitvalid){
				topView();
				return txt_permitno; 
			}
			else if(component==combo_vehfor){
				topView();
				return txt_permitvalid;

			}
			else if(component==txt_area_used){
				topView();
				return combo_vehfor; 
			}
			else if(component==txtarea_addrparking){
				bottomView();
				return txt_area_used;

			}
			else if(component==txtarea_other){
				bottomView();
				return txtarea_addrparking; 
			}
			else if(component==button_submit && button_submit.isEnabled()){
				bottomView();
				return txtarea_other;
			}
			else if(component==button_clear){
				bottomView();
				return button_submit;
			}
			else if(component==button_modify && button_modify.isEnabled()){
				return component1;
			}
			else if(component==txt_ac_no){
				topView();
				return txt_ac_no;
			}
			
			return null;
		}
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void focusLost(FocusEvent e) {
		if(e.getSource()==button_modify){
			if(txt_ac_no.isEnabled())
				txt_ac_no.requestFocus();
			else
				component1.requestFocus();
		}
	}
	//Vinay
	public void makeitDisable(){
		txt_ac_no.setEnabled(false);
		txt_make_type.setEnabled(false);
		txt_cost.setEnabled(false);
		txt_licenceno.setEnabled(false);
		txt_permitno.setEnabled(false);
		txt_area_used.setEnabled(false);
		txt_permitvalid.setEnabled(false);
		txtarea_addrparking.setEnabled(false);
		txtarea_addrdealer.setEnabled(false);
		txtarea_other.setEnabled(false);
		txt_validity.setEnabled(false);
		combo_vehfor.setEnabled(false);
		combo_type.setEnabled(false);
		
		button_clear.setEnabled(false);
		button_submit.setEnabled(false);
		button_modify.setEnabled(true);
	}

}


