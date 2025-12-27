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

public class ShareCertificateNo extends JPanel implements KeyListener,CaretListener {

	
	GridBagConstraints gbc ;
	JTable table;
	DefaultTableModel dtm;
	
	
	private Hashtable hash_cert;
	
	JTextField txt_no_of_share;
	
	int col_count;
	int type ;
	certificateObserver observers = new certificateObserver();
	public ShareCertificateNo( String[] col_name , int type) {
		
		this.type = type; 
		col_count = col_name.length;
		setLayout( new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4,4,4,4);

		
		for( int i =0; i< 22; i++) {
			
			addComponent(new JLabel("     "), this, 0, i, 0.0, 0.0, 1, 1);
			
		}
		dtm = new DefaultTableModel() {
			
			public boolean isCellEditable(int row,int column)
			{
				
					return true;
				
			}
			public Class getColumnClass(int col)
			{
			
					return ( new String("")).getClass();
			}
			
		};
		
		dtm.setColumnCount(0);
		
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
		
		TableColumn tablecolumn[]=new TableColumn[2];
		for(int i=0;i<2;i++)
		{
			tablecolumn[i]=table.getColumnModel().getColumn(i);
			tablecolumn[i].setResizable(true);
		}
		
		tablecolumn[0].setPreferredWidth(200);
		tablecolumn[1].setPreferredWidth(200);
		
		txt_no_of_share = new JTextField();
		if (type == 0)
			txt_no_of_share.addCaretListener(this);
		
		if ( type == 0 )
			tablecolumn[1].setCellEditor( new DefaultCellEditor(txt_no_of_share));
		
		Object[] obj = new Object[col_count];
		
		
		for (int i =0 ; i< col_count; i++) {
		
			if( type != 0 )
				obj[i] = new Integer(0);
			else
				obj[0] = new Integer(0);
		}
		dtm.addRow(obj);
		
		addComponent(new JScrollPane(table),this,1,1,0.0,0.0,20,3);
		
		hash_cert = new Hashtable();
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

	public void keyPressed(KeyEvent arg0) {

		if ( arg0.getSource() == table) {
		
			
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER ) {
				
				Object[] obj = new Object[col_count];
				
				
				for (int i =0 ; i< col_count; i++) {
				
					if( type != 0 )
						obj[i] = new Integer(0);
					else
						obj[0] = new Integer(0);
				}
				dtm.addRow(obj);
			}
			
			
			else if ( arg0.getKeyCode() == KeyEvent.VK_F1)
				getCertificateNo();
		}
		
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Hashtable getCertificateNo() {
		
		
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
			dtm.addRow(obj);
			
		}
		
	}
	
	public void clear() {
		
		dtm.setRowCount(0);
		Object[] obj = new Object[2];
		obj[0] = new Integer(0);
		obj[1] = new Integer(0);
		dtm.addRow(obj);
	}

	public void caretUpdate(CaretEvent arg0) {

		
		if ( arg0.getSource() == txt_no_of_share ) {
			
			
			
			
			int sum = 0;
			
			for  ( int i=0; i< dtm.getRowCount(); i++)
			{
				
				 if ( table.getValueAt(i, 1) == null) {
					 
					 if ( !txt_no_of_share.getText().equalsIgnoreCase(""))
					 sum +=new Integer(txt_no_of_share.getText().toString());
				 } else 
					   sum += new Integer(table.getValueAt(i, 1).toString()).intValue();
			
				
				
			  }
			//table.setValueAt(new Integer(sum), 0, 1);
			observers.callObservers(sum*100.00);
			
		}
		
	}
	
	 class certificateObserver extends Observable {
		 
		 certificateObserver(){
			 
		 }
		 
		 void callObservers( double sum) {
			 
			 setChanged();
			 notifyObservers(new Double(sum));
		 }
	 }
	
}
