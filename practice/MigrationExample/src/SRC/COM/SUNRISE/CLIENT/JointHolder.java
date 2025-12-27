package SRC.COM.SUNRISE.CLIENT;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.*;



public class JointHolder  extends JPanel implements ActionListener ,FocusListener,Observer {

	GridBagConstraints gbc=null;
	JButton btn_add ,btn_delete;
	JTextField txt_cust_id;
	JTabbedPane tabbed_pane = new JTabbedPane();
	Vector vector_cid = new Vector();
	Hashtable hash_cidname  = new Hashtable(); 
	Vector vec_panel;
	
	Travesal traversal ;
	
	public JointHolder() {
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
    	gbc.fill = GridBagConstraints.BOTH;  
		gbc.insets = new Insets(5,5,5,5);
		
		for(int i= 0; i<19; i++)
    		addComponent(new JLabel("      "),this, 0, i, 0.0, 0.0, 1, 1);
		
		addComponent(new JLabel("  "),this, 9, 19, 1.0, 1.0, 1, 1);
		
		btn_add = new JButton("Create CID");
		btn_add.addActionListener(this);
		
		addComponent(btn_add, this, 1, 0, 0.0, 0.0, 3, 1);
		addComponent(btn_delete = new JButton("Delete"), this, 1, 4, 0.0, 0.0, 3, 1);
		btn_delete.addActionListener(this);
		
		addComponent(new JLabel("Cust ID"), this, 2, 0, 0.0, 0.0, 2, 1);
		addComponent(txt_cust_id = new JTextField(), this, 2, 3, 0.0, 0.0, 3, 1);
		txt_cust_id.addFocusListener(this);
		
		addComponent(tabbed_pane , this, 3, 0, 0.0, 0.0, 18, 8);
		
		//tabbed_pane.setVisible(false);
	
		//traversal = new Travesal(this);
	
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
	
	
	public void actionPerformed(ActionEvent arg) {
		
		if ( arg.getSource() == btn_add) {
			
			SBCAPanel sc = SBCAPanel.getInstance(1);
			sc.showJSP(4);
		}
		
		else if (arg.getSource() == btn_delete) {
			
		
			if (tabbed_pane.getSelectedIndex() >= 0)
				tabbed_pane.remove(tabbed_pane.getSelectedIndex());
			
		}
		
		
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent arg0) {

		if ( arg0.getSource() == txt_cust_id )//&& arg0.getOppositeComponent() == btn_delete ) 
		{
			
			if ( txt_cust_id.getText().toString().length() > 0 &&  !txt_cust_id.getText().toString().trim().equalsIgnoreCase("0") ) 
			{
				
				showCustDetail(Integer.parseInt(txt_cust_id.getText().toString()));
				
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Please Enter the Customer ID");
			}
			
			
		}
		
		System.out.println(vector_cid.size());
		
		
		
	}
	
	public void showCustDetail(int cid ) {
		
		StoreCidData getCId = new  StoreCidData();
		 
		 CustomerMasterObject cm = getCId.getCustomerID(cid);
		 
		 if ( cm != null ) {
			 
			 Personal per = new Personal();
			 
			 vector_cid.add(new Integer(cm.getCustomerID()));
			 per.setCustid(cm.getCustomerID());
			 per.setCategory(  (cm.getCategory()== 0) ? "Individual" : "Institute" );
			 per.setAddress(cm.getAddress());
			 per.setphoto(cm.getBinaryImage());
			 per.setSign(cm.getBinarySignImage());
			 per.setDOB(cm.getDOB());
			 per.setSex(cm.getSex());
			 per.setName(cm.getName());
			 hash_cidname.put(new Integer(cm.getCustomerID()),cm.getName());
			 System.out.println("-----------" + hash_cidname.get(new Integer(cm.getCustomerID())));
			 
			 tabbed_pane.addTab("nominee", per);
		 }
		 txt_cust_id.setText("");
		 txt_cust_id.setEnabled(false);
		
	}
	
	public Vector getJointholdersCid() {
		
		return vector_cid;
	}

	public Hashtable getJointholdersCidName() {
		
		return hash_cidname;
	}
	class Travesal extends FocusTraversalPolicy {

		
		Travesal(JointHolder this_panel){
			
			this_panel.setFocusable(true);
	 		this_panel.setFocusCycleRoot(true);
	 		this_panel.setFocusTraversalPolicy(this);
		}
		
		@Override
		public Component getComponentAfter(Container arg0, Component arg1) {

			if ( arg0 == txt_cust_id)
			  return btn_delete;
			
			else if ( arg0 == btn_add)
				return txt_cust_id;
			
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

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void clearJointHolder() {
		
		txt_cust_id.setText("");
		
		tabbed_pane.removeAll();
		
	}
	
	
}
