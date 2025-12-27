package com.scb.td.actions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sun.xml.registry.client.browser.TableSorter;



public class GeneralReport extends JPanel 
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String str_qry;
	public JTable table;
   	public DefaultTableModel dtm;	
	int column,row;
	public JScrollPane jsp;
	int x,y,a[];
	private boolean close_flag=false;
	public TableCellRenderer tablecellrenderer = new DefaultTableCellRenderer();  ;//CustomTableCellRenderer();
	private TableSorter table_sorter;
	
	public GeneralReport()
	{
		//JPanel cp=new JPanel();
		//cp.setOpaque(true);
		row=-1;
		column=-1;
	
		 table_sorter=new TableSorter(dtm=new DefaultTableModel()
		{
			public boolean isCellEditable(int row,int col)
		    {
		        return false;
		    }
		});
		    
		table=new JTable(table_sorter);
		//table_sorter.setTableHeader(table.getTableHeader());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jsp=new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(Colors.setMainPanelColor());
	//	table.addKeyListener(new TableViewListener());
        
		//ship......11/06/2007
        table.setSurrendersFocusOnKeystroke(true);
        table.setRequestFocusEnabled(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ////////////
		
		table.setGridColor(Color.PINK);
		table.getTableHeader().setFont(new Font("Verdana",1,11));
		table.setFont(new Font("Verdana",0,10));
		
		try
	    {
            table.setDefaultRenderer(Class.forName("java.lang.Object"),tablecellrenderer);
	    } 
		catch(ClassNotFoundException ex )  
		{
		    System.exit(0);
		}
	}

	public void setQuery(String qry)
	{
		str_qry=qry;
	}
	public String getQuery()
	{
		return str_qry;
	}
	public int[] getPos()
	{
	    a =new int[2];
	    // code added by Murugesh on 20/04/2006
	    if(this.row == dtm.getRowCount())
	    	this.row=0;
	    //
	    a[0]=this.row+1;
	    
	    a[1]=this.column;
	    return a;
	    
	}
	public int[]  find(int pos[],int noofcolumn,int searchcolumn,String value,boolean matchcase,boolean fullword,int type)	
	{
		
	    int found=0,i;
		row=pos[0];
		column=pos[1];
		
		
		
		if(!matchcase)
			value=value.toUpperCase();
		/*if(!fullword)
		  {
			
			for(int j=0;j<dtm.getRowCount();j++)
			{
				for(int k=0;k<dtm.getColumnCount();k++)
				{
				value=String.valueOf(value.indexOf(table.getValueAt(j,k).toString().trim()));	
				}
				//value=table.getValueAt(j,j).toString().trim().;
				
			}
		  }*/
	
		
		//aa:for(i=row;i<dtm.getRowCount();i++,column=0)
		//Added by puspa
		aa:for(i=row;((i>=0) && (i<dtm.getRowCount())); )
		    {
			
			
			table.scrollRectToVisible(table.getCellRect(i,i,true));
			if(type==1)
				
					i++;
				 else
					i--;
				
			//table.repaint();
	        try{
			if(searchcolumn!=-1)
			{
                boolean flag=false;
				if(table.getValueAt(i,searchcolumn)!=null)
				    flag=table.getValueAt(i,searchcolumn).toString().toUpperCase().equals(value);			
					
				int f=-1; 
				if(fullword && table.getValueAt(i,searchcolumn)!=null )	
					f=table.getValueAt(i,searchcolumn).toString().indexOf(value);
					
				if(flag || f>=0)
				{
					table.setRowSelectionInterval(i,i);
					table.scrollRectToVisible(table.getCellRect(i,i,true));
					//table.repaint();
					this.row=i-1;
					this.column=searchcolumn;
					found=1;
					
					break aa;
				}
				
			//table.setRowSelectionInterval(0,0);
			//table.scrollRectToVisible(table.getCellRect(i,i,true));
			table.setRowSelectionInterval(i,i);
			table.scrollRectToVisible(table.getCellRect(i,i,true));
			
			//table.repaint();
			}
			else
			{
	            for(int k=column;k<noofcolumn;k++)
				{				
                    
                    boolean flag=false;
					if(table.getValueAt(i,k)!=null)
					flag=table.getValueAt(i,k).toString().toUpperCase().equals(value);			
					int f=-1; 					
					if(!fullword )	
							f=table.getValueAt(i,k).toString().indexOf(value);	
								
					if(flag || f>=0)					
					{
					//	table.setRowSelectionInterval(0,0);
						table.setRowSelectionInterval(i,i);
						table.scrollRectToVisible(table.getCellRect(i,i,true));
						//table.repaint();
						this.row=i-1;
						this.column=k;
						found=1;						
						break aa;
						
					}
					table.setRowSelectionInterval(i,i);
					table.scrollRectToVisible(table.getCellRect(i,i,true));
					//table.repaint();
				}
			}
			}catch(Exception ex){ex.printStackTrace();}
			
		}

		//row=i;
		
		if(found==0)
		{
			row=0;column=0;
		}					
			
		pos[0]=row;
		System.out.println("row="+row);
		pos[1]=column+1;
		System.out.println("col="+column);
		//table.repaint();
		
		return pos;
		
	}



	
void setCloseFlag(boolean value){
	close_flag = value;
}
	
}
