package SRC.COM.SUNRISE.CLIENT;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import SRC.COM.SUNRISE.CLIENT.RDexample.certificateObserver;
import SRC.COM.SUNRISE.UTILITY.DateListener;
import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanTransactionObject;

public class LoanExamp extends JPanel implements KeyListener,CaretListener {
	
	GridBagConstraints gbc ;
	JTable table;
	DefaultTableModel dtm;
	JTextField txt_ac_no,trn_date,trn_seq;
	Hashtable hash_loans=null;
	
	
	certificateObserver observers = new certificateObserver();
	
	int col_count;
	int type ;
	public LoanExamp( String[] col_name , int type) {
		
		this.type = type; 
		col_count = col_name.length;
		setLayout( new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4,4,4,4);
		
		txt_ac_no = new JTextField();
		trn_date = new JTextField();
		trn_seq = new JTextField();
		
		System.out.println("In LoanExamp");
	
		
		trn_date.setText(MainScreen.head_panel.getSysDate());
		
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
					/*if(col==5)
						return int_date.getClass();*/
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
		
		TableColumn tablecolumn[]=new TableColumn[6];
		for(int i=0;i<5;i++)
		{
			tablecolumn[i]=table.getColumnModel().getColumn(i);
			tablecolumn[i].setResizable(true);
		}
		
		tablecolumn[0].setPreferredWidth(100);
		
		tablecolumn[1].setPreferredWidth(100);
		tablecolumn[2].setPreferredWidth(100);
		tablecolumn[3].setPreferredWidth(100);
		tablecolumn[4].setPreferredWidth(100);
		//tablecolumn[5].setPreferredWidth(100);
		
		
		
		
		if (type == 0)
			txt_ac_no.addCaretListener(this);
		
		if ( type == 0 )
			tablecolumn[1].setCellEditor( new DefaultCellEditor(txt_ac_no));
		
		tablecolumn[1].setCellEditor( new DefaultCellEditor(txt_ac_no));
		
		
		tablecolumn[0].setCellEditor(new DefaultCellEditor(trn_date));
    	trn_date.setText(Validation.getSysDate());
    	
    	
    	String array_string_trn_type[] = {"Recovery","Interest"};
    	tablecolumn[2].setCellEditor(new MyComboBoxEditor(array_string_trn_type));
    	
    	String array_string_trn_mode[] = {"Cash","Transfer","Clearing"};
    	tablecolumn[4].setCellEditor(new MyComboBoxEditor(array_string_trn_mode));
    	
    	/*tablecolumn[5].setCellEditor(new DefaultCellEditor(int_date));
    	int_date.setText(Validation.getSysDate());*/
    	
        trn_date.addKeyListener(dtlist);	
       // int_date.addKeyListener(dtlist);
       
		
		
		
		Object[] obj = new Object[col_count];
		
		obj[0] = trn_date.getText();
		obj[1] = new Integer(0);
		obj[2] = new Integer(0);
		obj[3] = new Double(0.00);
		obj[4] = new String();
		///obj[5] = int_date.getText();
		
		
		
		dtm.addRow(obj);
		
		addComponent(new JScrollPane(table),this,1,1,0.0,0.0,20,3);
		
		hash_loans = new Hashtable();
		
		
		
		
		
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
				
					//if( type != 0 )
					//	obj[i] = new Integer(0);
					/*else
						obj[0] = new Integer(0);*/
				}
				dtm.addRow(obj);
			}
			
			
			else if ( arg0.getKeyCode() == KeyEvent.VK_F1)
				getDetails();
		}
		
	
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class certificateObserver extends Observable {
		 
		 certificateObserver(){
			 
		 }
		 
		 void callObservers( double sum) {
			 
			 setChanged();
			 notifyObservers(new Double(sum));
		 }
	 }

	public void caretUpdate(CaretEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public Hashtable getDetails(){
		
		LoanObject loantrn = null;
		Hashtable hash_ln = new Hashtable();
		for ( int i=0; i< table.getRowCount(); i++ ) {
			if ( table.getValueAt(i, 0)!= null && table.getValueAt(i, 1)!= null) {
				loantrn = new LoanObject();
				
				loantrn.setTrn_date(table.getValueAt(i, 0).toString().trim());
				loantrn.setTrn_seq(Integer.parseInt(table.getValueAt(i, 1).toString().trim()));
				loantrn.setTrn_type(table.getValueAt(i, 2).toString().trim());
				loantrn.setAmt(Double.parseDouble(table.getValueAt(i, 3).toString().trim()));
				loantrn.setTrn_mode(table.getValueAt(i, 4).toString().trim());
				System.out.println("Table====>"+table.getValueAt(i, 2).toString().trim());
				hash_ln.put(table.getValueAt(i, 1).toString().trim(),loantrn);
			}
		}
		
		return hash_ln;
	}
	
public void setDetails(Hashtable has_tab) {
		
		
		Enumeration enn = has_tab.keys();
		dtm.setRowCount(0);
		
		LoanObject lob;
		while( enn.hasMoreElements() ) {
			
			String key=enn.nextElement().toString();
			System.out.println("key >> "+key);
			lob=(LoanObject)has_tab.get(key); 
			Object[] obj = new Object[5];
		
			obj[0] = lob.getTrn_date();
			obj[1] = ""+lob.getTrn_seq();
			obj[2] = lob.getTrn_type();
			obj[3] = ""+lob.getAmt();
			obj[4] = ""+lob.getTrn_mode();
			System.out.println("TrnType=====>"+lob.getTrn_type());
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
	
}
