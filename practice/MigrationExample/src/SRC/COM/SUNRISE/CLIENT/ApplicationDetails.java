package SRC.COM.SUNRISE.CLIENT; 


import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ApplicationDetails extends JPanel {
	
GridBagConstraints gbc=null;
// created 
    
   
    
    JTextField txt_addfee,txt_formfee,txt_addfrm,txt_tot;
    
    JLabel lbl_addfee,lbl_formfee,lbl_addfrm,lbl_tot;
    JButton btn_submit,bt_cancel;
    
    
    
    
	

    public ApplicationDetails() {
		
	
    	
    	gbc = new GridBagConstraints();
    	gbc.fill = GridBagConstraints.BOTH;  
		gbc.insets = new Insets(5,5,5,5);
		
		setLayout(new GridBagLayout());
    	
    	for(int i= 0; i<19; i++)
    		addComponent(new JLabel("  "+i+"  "),this, 0, i, 0.0, 0.0, 1, 1);
    	
    	
    	addComponent(new JLabel("  "),this, 17, 20, 1.0, 1.0, 1, 1);
    	
    	
    	
    	addComponent(new JLabel("Addimission fee"), this, 1, 0, 0.0, 0.0, 4, 1);
    	addComponent(txt_addfee = new JTextField(), this, 1, 3, 0.0, 0.0, 4, 1);
    	
    	
    	addComponent(new JLabel("Form fee"), this, 2, 0, 0.0, 0.0, 2, 1);
    	addComponent( txt_formfee = new JTextField(), this, 2, 3, 0.0, 0.0, 4, 1);
    	
    	addComponent(new JLabel("Additional form fee"), this, 3, 0, 0.0, 0.0, 2, 1);
    	addComponent( txt_addfrm = new JTextField(), this, 3, 3, 0.0, 0.0, 4, 1);
    	
    	addComponent(new JLabel("Total"), this, 4, 0, 0.0, 0.0, 2, 1);
    	addComponent(txt_tot=new JTextField() , this, 4, 3, 0.0, 0.0, 4, 1);
    	
    		
		addComponent( btn_submit = new JButton("Submit"), this, 6, 0, 0.0, 0.0, 3, 1);
		addComponent(bt_cancel=new JButton("Cancel"), this, 6, 1, 0.0, 0.0, 1, 1);
		
    	
    	
    }
    public void clearForm()
    {
    	txt_addfee.setText("");
    	txt_addfrm.setText("");
    	txt_formfee.setText("");
    	txt_tot.setText("");
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
    
    

}
