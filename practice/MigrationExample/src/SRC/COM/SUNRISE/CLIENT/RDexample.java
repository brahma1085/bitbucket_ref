package SRC.COM.SUNRISE.CLIENT;




import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TabExpander;

import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositObject;

public class RDexample extends JPanel implements KeyListener,CaretListener{



	GridBagConstraints gbc ;
	JTable table;
	DefaultTableModel dtm;
	
	private Hashtable hash_deposit;
	static RDDataEntry rd_tran;
	
	JTextField txt_ac_no,trn_date,int_date;
	
	int col_count;
	int type ;
	certificateObserver observers = new certificateObserver();
	public RDexample( String[] col_name , int type) {
		
		this.type = type; 
		col_count = col_name.length;
		setLayout( new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4,4,4,4);

	/*	
		for( int i =0; i< 22; i++) {
			
			addComponent(new JLabel("   hhhh  "), this, 0, i, 0.0, 0.0, 1, 1);
			
		}*/
		txt_ac_no = new JTextField();
		trn_date = new JTextField();
		int_date =new JTextField();
		trn_date.setText(MainScreen.head_panel.getSysDate());
		int_date.setText(MainScreen.head_panel.getSysDate());
		DateListener dtlist = new DateListener();
		dtm = new DefaultTableModel() {
			
			public boolean isCellEditable(int row,int column)
			{
				
					return true;
				
			}
			public Class getColumnClass(int col)
			{
					if(col==1)
						return trn_date.getClass();
					if(col==7)
						return int_date.getClass();
					return ( new String("")).getClass();
			}
			
		};
		
		dtm.setColumnCount(0);
		System.out.println("colname.length="+col_name.length);
		for ( int i =0; i<col_name.length; i++)
			dtm.addColumn(col_name[i]);
		
		
		
		
		table = new JTable(dtm);
		table.setFont(new Font("verdana",0,10));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setGridColor(Color.orange);
		table.setRowHeight(23);
		table.setBorder(BorderFactory.createRaisedBevelBorder());
		table.addKeyListener(this);
		table.setRowSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		TableColumn tablecolumn[]=new TableColumn[9];
		for(int i=0;i<9;i++)
		{
			tablecolumn[i]=table.getColumnModel().getColumn(i);
			tablecolumn[i].setResizable(true);
		}
		
		tablecolumn[0].setPreferredWidth(100);
		
		tablecolumn[1].setPreferredWidth(100);
		tablecolumn[2].setPreferredWidth(100);
		tablecolumn[3].setPreferredWidth(100);
		tablecolumn[4].setPreferredWidth(100);
		tablecolumn[5].setPreferredWidth(100);
		tablecolumn[6].setPreferredWidth(100);
		tablecolumn[7].setPreferredWidth(100);
		tablecolumn[8].setPreferredWidth(100);
		
		
		
		
		if (type == 0)
			txt_ac_no.addCaretListener(this);
		
		if ( type == 0 )
			tablecolumn[1].setCellEditor( new DefaultCellEditor(txt_ac_no));
		
	    	tablecolumn[1].setCellEditor( new DefaultCellEditor(txt_ac_no));
	    	String array_string_trn_type[] = {"D","I","P"};
	    	tablecolumn[3].setCellEditor(new MyComboBoxEditor(array_string_trn_type));
		
	    	String array_string_trn_mode[] = {"C","T","G"};
	    	tablecolumn[7].setCellEditor(new MyComboBoxEditor(array_string_trn_mode));
	    	
	    	tablecolumn[0].setCellEditor(new DefaultCellEditor(trn_date));
	    	trn_date.setText(Validation.getSysDate());
	    	tablecolumn[6].setCellEditor(new DefaultCellEditor(int_date));
	    	
	        trn_date.addKeyListener(dtlist);	
	        int_date.addKeyListener(dtlist);
	       
	    	
	   
            		
		
		Object[] obj = new Object[col_count];
		
		obj[1] = trn_date.getText();
		obj[1] = new Integer(0);
		obj[2] = new Integer(0);
		obj[3] = new Integer(0);
		obj[4] = new Integer(0);
		obj[5] = new Integer(0);
		obj[6] = int_date.getText();
		obj[7] = new Integer(0);
		obj[8] = new Integer(0);
		
		for (int i =0 ; i< col_count; i++) {
		
			/*if( type != 0 )
				obj[i] = new Integer(0);*/
			
			/*else
				obj[0] = trn_date;*/
				/*obj[1] = new Integer(0);
				obj[2] = new Integer(0);
				obj[3] = new Integer(0);
				obj[4] = new Integer(0);
				obj[5] = new Integer(0);
				obj[6] = new Integer(0);
				obj[7] = new Integer(0);
				obj[8] = new Integer(0);*/
		}
		dtm.addRow(obj);
		
		addComponent(new JScrollPane(table),this,1,1,0.0,0.0,20,3);
		
		hash_deposit = new Hashtable();
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

/*public Hashtable getCertificateNo() {
		
		
		hash_cert.clear();
		
		for ( int i=0; i< dtm.getRowCount(); i++ ) {
			
			if ( table.getValueAt(i, 0)!= null && table.getValueAt(i, 1)!= null) {
				
				hash_cert.put(table.getValueAt(i, 0).toString().trim(), table.getValueAt(i, 1).toString().trim());
			}
		}
	
		System.out.println( hash_cert.toString());
		return hash_cert;
	
	
	
}

public void setCertificateNo(Hashtable has_tab) {
	
	
	Enumeration enn = has_tab.keys();
	dtm.setRowCount(0);
	
	
	while( enn.hasMoreElements() ) {
		
		 
		Object[] obj = new Object[2];
		
		Object obj_val = enn.nextElement();
		
		obj[0] = (Integer)obj_val;
		obj[1] = has_tab.get(obj_val);
		obj[2] =has_tab.get(obj_val);
		dtm.addRow(obj);
		
	}
	
}
	*/
	
	public void keyPressed(KeyEvent arg0) {

		if ( arg0.getSource() == table) {
		
			
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER ) {
				
				Object[] obj = new Object[col_count];
				
				
				for (int i =0 ; i< col_count; i++) {
				
					//if( type != 0 )
					//	obj[i] = new Integer(0);
					/*else
						obj[0] = new Integer(0);*/
				}
				dtm.addRow(obj);
			}
			
			
			else if ( arg0.getKeyCode() == KeyEvent.VK_F1)
				gettrnseq();
		}
		
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

			
		
	public Hashtable gettrnseq() {
		
		DepositObject dep=null;
		hash_deposit.clear();
		for ( int i=0; i< dtm.getRowCount(); i++ ) {
			
			if ( table.getValueAt(i, 0)!= null && table.getValueAt(i, 1)!= null) {
				
				dep=new DepositObject();
				dep.setTrn_date(table.getValueAt(i, 0).toString().trim());
				dep.setTrn_seq(Integer.parseInt(table.getValueAt(i, 1).toString().trim()));
				dep.setDep_amt(Double.parseDouble(table.getValueAt(i, 2).toString().trim()));
				dep.setTrn_type(table.getValueAt(i, 3).toString().trim());
				
				dep.setInt_amt(Double.parseDouble(table.getValueAt(i, 4).toString().trim()));
				dep.setRd_bal(Double.parseDouble(table.getValueAt(i, 5).toString().trim()));
				dep.setInterest_date(table.getValueAt(i, 6).toString().trim());
				dep.setTrn_mode(table.getValueAt(i, 7).toString().trim());
				dep.setCum_int(Double.parseDouble(table.getValueAt(i, 8).toString().trim()));
				hash_deposit.put(table.getValueAt(i, 1).toString().trim(),dep);
			}
		}
	
		System.out.println( hash_deposit.toString());
		return hash_deposit;
	
	
	
}

	public void setTrnseq(Hashtable has_tab) {
		
		
		Enumeration enn = has_tab.keys();
		dtm.setRowCount(0);
		
		DepositObject dep;
		while( enn.hasMoreElements() ) {
			
			String key=enn.nextElement().toString();
			System.out.println("key >> "+key);
			dep=(DepositObject)has_tab.get(key); 
			Object[] obj = new Object[9];
			System.out.println(dep);
			obj[0] = dep.getTrn_date();
			obj[1] = ""+dep.getTrn_seq();
			obj[2]= ""+dep.getDep_amt();
			obj[3] = dep.getTrn_type();
			obj[4] = ""+dep.getInt_amt();
			obj[5] = ""+dep.getRd_bal();
			obj[6]= dep.getInterest_date();
			obj[7] = dep.getTrn_mode();
			obj[8] = ""+dep.getCum_int();
			dtm.addRow(obj);
			
		}
		
	}
	
	public void clear() {
		
		dtm.setRowCount(0);
		Object[] obj = new Object[9];
		/*obj[0] = new Integer(0);
		obj[1] = new Integer(0);*/
		dtm.addRow(obj);
	}

	/*public void caretUpdate(CaretEvent arg0) {

		
		if ( arg0.getSource() == txt_ac_no ) {
			
			
			
			
			int bal= 0;
			
			for  ( int i=0; i< dtm.getRowCount(); i++)
			{
				
				 if ( table.getValueAt(i, 1) == null) {
					 
					 if ( !txt_ac_no.getText().equalsIgnoreCase(""))
					 bal +=new Double(txt_ac_no.getText().toString());
					 System.out.println("balllll==="+bal);
					 
				 } else 
					   bal += new Double(table.getValueAt(i, 5).toString()).doubleValue();
				 	System.out.println("balance in else lopp==="+bal);
			            
				
				
			  }
			table.setValueAt(new Integer(bal), 0, 5);
			
		}
		
	}*/
	
	 class certificateObserver extends Observable {
		 
		 certificateObserver(){
			 
		 }
		 
		 void callObservers( double sum) {
			 
			 setChanged();
			 notifyObservers(new Double(sum));
		 }
	 }

	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	 
}

