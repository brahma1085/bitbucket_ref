package com.scb.td.actions;



import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class PrintEmpty extends JPanel implements Printable,Cloneable {
	PrinterJob pjob;
	PageFormat pageFormat;
	Paper paper;
	String report_heading;
	int pageWidth,pageHeight,pageWidth_inch,pageHeight_inch,start,end,page_index=1,start_count,row=0,inc=0,cnt=0,max_inc=0;
	int nameColumn,columnNum;
	JTable tab[];
	PrintEmpty pe;
	Book book;
	DefaultTableModel dtm[];
	int length[];
	Graphics2D g;

	public int getPage_index() {
		return page_index;
	}
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}
	public PrintEmpty(JTable table[],DefaultTableModel dtmodel[]){
		this.tab=table;
		this.dtm=dtmodel;
		if(dtm[0].getRowCount()>0||tab[0].getRowCount()>0){
			pjob = PrinterJob.getPrinterJob();
			pageFormat = new PageFormat();//pjob.defaultPage();
			paper = new Paper();
			this.pageWidth = (int) (pageWidth * 72); // 4 inches
			this.pageHeight = (int) (pageHeight * 72);
     		paper.setSize(this.pageWidth, this.pageHeight);
     		paper.setImageableArea(1 / 6 * 72, 1 / 5 * 72, this.pageWidth - 1 / 4 * 72, this.pageHeight );
     		pageFormat.setPaper(paper);
     		this.setPrinterJob(pjob);
     		this.setPageFormat(pageFormat);
     	}
		else{
			JOptionPane.showMessageDialog(null,"No records to Print","Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
   		
	}
	public void setPrinterEmpty(PrintEmpty p){
		this.pe=p;
	}
	
	public void setPrinterJob(PrinterJob pjob){
		this.pjob=pjob;
	}
	public PrinterJob getPrinterJob(){
		return this.pjob;
	}
	public void setPageFormat(PageFormat pageFormat){
		this.pageFormat=pageFormat;
	}
	public PageFormat getPageFormat(){
		return this.pageFormat;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setFont(new Font("Book Antiqua",Font.PLAIN,8));;
		//g.drawString(MainScreen.head.getBankName(),72*(this.pageWidth_inch/2-1),12*4);
		//g.drawString(MainScreen.head.getBankLocation(),72*(this.pageWidth_inch/2),12*5);
		g.drawString("Page: "+this.getPage_index(),72*(this.pageWidth_inch-2),12*5);
		g.drawString(this.getReport_heading(),12*(this.getStart_count()),12*6);
		printSubHeading(g);
		printData(g);
	
	}
	
	public void printData(Graphics g){
		
		int r=this.start;
		System.out.println("start >> "+r);
		this.row=11;
		cnt=this.getStart_count();
		checkMaxLength();
		g.setFont(new Font("Book Antiqua",Font.PLAIN,8));
		
		 for(;r<this.end;r++){
			 cnt=this.getStart_count();
			 for(int i=0;i<tab[0].getColumnCount();i++){
				 this.setColumnNum(i);
				 if(length[i]<5&&length[i]!=0){
				 	try{
				 		int z=Integer.parseInt(tab[0].getValueAt(r,i).toString());
			 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,this.row,1,4);
			 			this.cnt=cnt+3;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(r,i)!=null)
				 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,2,4);
				 		this.cnt=cnt+3;
				 	}
				 }
				 else if(length[i]>=5&&length[i]<10){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(r,i).toString());
			 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,1,10);
			 			this.cnt=cnt+4;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(r,i)!=null)
				 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,2,10);
				 		this.cnt=cnt+4;
				 	}
				   
				 }
				 else if(length[i]>=10&&length[i]<20){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(r,i).toString());
			 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,1,20);
			 			this.cnt=cnt+5;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(r,i)!=null)
				 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,2,20);
				 		this.cnt=cnt+5;
				 	}
				 }
				 else if(length[i]>=20){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(r,i).toString());
				 		printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,1,21);
				 		cnt=cnt+6;
					}
					catch(Exception e){
						if(tab[0].getValueAt(r,i)!=null)
							printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,2,21);
					 	cnt=cnt+6;
					}
				 }
				 else{
					try{
						int z=Integer.parseInt(tab[0].getValueAt(r,i).toString());
			 			printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,1,0);
			 			cnt=cnt+3;
					}
					catch(Exception e){
						if(tab[0].getValueAt(r,i)!=null)
							printCash(g,tab[0].getValueAt(r,i).toString().trim(),cnt,row,2,0);
						 cnt=cnt+3;
					
					}
				 }
				
					
			 }
			 System.out.println("row=="+row+" and inc="+inc);
			 if(this.getMax_inc()<=inc)
				 this.row=row+inc+1;
			 else if(this.getMax_inc()>inc)
				 this.row=row+this.getMax_inc()+1;
			System.out.println("row=="+row);
			inc=0;
			this.setMax_inc(0);
			if(this.row>55){
				g.drawLine(0, this.row, this.cnt, this.row);
				break;
			}
		 		
		 }	
	}
	public int getRowNumbers(int s,int en){
		int max=42;
		checkMaxLength();
		int j=0;
		j=s;
		this.rows=8;
	//	System.out.println("j="+j+"end="+en);
		for(;j<en;j++){
			for(int i=0;i<tab[0].getColumnCount();i++){
			//	System.out.println(j+" "+i);
				if(length[i]<5&&length[i]!=0){
					try{
				 		int z=Integer.parseInt(tab[0].getValueAt(j,i).toString());
			 			printCash(tab[0].getValueAt(j,i).toString(),cnt+1,this.rows,1,4);
			 			cnt=cnt+3;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(j,i)!=null)
				 			printCash(tab[0].getValueAt(j,i).toString(),cnt,rows,2,4);
				 		cnt=cnt+3;
				 	}
				}
				else if(length[i]>=5&&length[i]<10){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(j,i).toString());
			 			printCash(tab[0].getValueAt(j,i).toString(),cnt+1,rows,1,10);
			 			cnt=cnt+4;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(j,i)!=null)
				 			printCash(tab[0].getValueAt(j,i).toString(),cnt,rows,2,10);
				 		cnt=cnt+4;
				 	}
				}
				else if(length[i]>=10&&length[i]<20){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(j,i).toString());
			 			printCash(tab[0].getValueAt(j,i).toString(),cnt+1,rows,1,20);
			 			cnt=cnt+5;
				 	}
				 	catch(Exception e){
				 		if(tab[0].getValueAt(j,i)!=null)
				 			printCash(tab[0].getValueAt(j,i).toString(),cnt,rows,2,20);
				 		cnt=cnt+5;
				 	}
				}
				else if(length[i]>=20){
					try{
						int z=Integer.parseInt(tab[0].getValueAt(j,i).toString());
				 		printCash(tab[0].getValueAt(j,i).toString(),cnt+1,rows,1,21);
				 		cnt=cnt+6;
					}
					catch(Exception e){
						if(tab[0].getValueAt(j,i)!=null)
							printCash(tab[0].getValueAt(j,i).toString(),cnt,rows,2,21);
					 	cnt=cnt+6;
					}
				}
				else{
					try{
						int z=Integer.parseInt(tab[0].getValueAt(j,i).toString());
			 			printCash(tab[0].getValueAt(j,i).toString(),cnt+1,rows,1,0);
			 			cnt=cnt+3;
					}
					catch(Exception e){
						if(tab[0].getValueAt(j,i)!=null)
							printCash(tab[0].getValueAt(j,i).toString(),cnt,rows,2,0);
						 cnt=cnt+3;
					
					}
				}
			}
		
			this.rows=rows+inc+1;
			if(rows>55){
				break;
			}
		}
		return j;
	}
	public int print(Graphics gg, PageFormat p, int index) throws PrinterException {
		g=(Graphics2D)gg;
		paint(gg);
		gg.translate((int)(p.getImageableX()), (int)(p.getImageableY()));
		return PAGE_EXISTS;
	}
	
public void printCash(Graphics g, String cash,int col,int r,int flag,int count_flag){
	  int space=5;
	  int	siz=0;
	  String nam="";
	  siz=cash.length();
	  nam=cash;
	  int col_count=0;
	  if(count_flag==0||count_flag==4){
		  col_count=3;  
	  }if(count_flag==10){
		  col_count=4;
	  }if(count_flag==20){
		  col_count=5;
	  }if(count_flag==21){
		  col_count=6;
	  }
	  if(flag==1){
		  System.out.println("row1=="+row+" col1=="+col+ " cash"+cash);
		  for(int z=siz-1;z>=0;z--){
			  g.drawString(new Character(nam.charAt(z)).toString(),12*(col+col_count)-space,12*row);
			  space=space+5;
		  }
		  g.drawString(" ",12*(col)+10+10,12*(row));
	  }
	  else{
		  try{
			  System.out.println("row2=="+row+" col2=="+col+" cash"+cash);
			  double f=Double.parseDouble(nam);
			  for(int z=siz-1;z>=0;z--){
				  g.drawString(new Character(nam.charAt(z)).toString(),12*(col+col_count)-space,12*row);
				  space=space+5;
			  }
		  }catch(Exception e){
			  if(cash!=null && !cash.equals("null")){
				 StringTokenizer stk=new StringTokenizer(cash," ");
				 try{
					 int size=stk.countTokens();
					 if(size!=0){
						 while(stk.hasMoreTokens()){
							 String data=stk.nextToken();
							 System.out.println("--------------------");
							 System.out.println(data);;
							 System.out.println(data.length());
							 System.out.println("--------------------");
							 
						 }
					 }else{
						 
					 }
				 }catch(Exception ee){
					 
				 }
			  }
			  if(col_count==5||col_count==6){
				  if(nam.length()>=10&&nam.length()<20){
					  inc=0;
					  System.out.println("row3=="+row+" col3=="+col+" cash"+cash);
					  g.drawString(nam.substring(0,10),12*(col)+10,12*(row+inc));
				  
					  g.drawString(nam.substring(10,nam.length()),12*(col)+10,12*(row+(++inc)));
					  System.out.print("  inc ="+inc);
				  }
				  else if(nam.length()>=20){
					  inc=0;
					  System.out.println("row4=="+row+" col4=="+col+" cash"+cash);
					  g.drawString(nam.substring(0,10),12*(col)+10,12*(row+inc));
					  g.drawString(nam.substring(10,20),12*(col)+10,12*(row+(++inc)));
					  g.drawString(nam.substring(20,nam.length()),12*(col)+10,12*(row+(++inc)));
					  this.setMax_inc(inc);
					  System.out.print("  inc ="+inc);
				  }
				  else{
					  System.out.println("row5=="+row+" col5=="+col+" cash"+cash);
					  g.drawString(nam.substring(0,nam.length()),12*(col)+10,12*row);
					  System.out.print("  inc ="+inc);
				  }
			  }
			  else {
				  if(nam.length()>=7&&nam.length()<15){
					  inc=0;
					  System.out.println("row6=="+row+" col6=="+col);
					  g.drawString(nam.substring(0,7),12*(col)+10,12*(row+inc));
					  g.drawString(nam.substring(7,nam.length()),12*(col)+10,12*(row+(++inc)));
					  System.out.print("  inc ="+inc);
				  }
				  else if(nam.length()>=15){
					  inc=0;
					  System.out.println("row7=="+row+" col7=="+col+" cash"+cash);
					  g.drawString(nam.substring(0,7),12*(col)+10,12*(row+inc));
					  g.drawString(nam.substring(7,15),12*(col)+10,12*(row+(++inc)));
					  g.drawString(nam.substring(15,nam.length()),12*(col)+10,12*(row+(++inc)));
					  this.setMax_inc(inc);
					  System.out.print("  inc ="+inc);
				  }
				  else{
					  System.out.println("row8=="+row+" col8=="+col+" cash"+cash);
					  g.drawString(nam.substring(0,nam.length()),12*(col)+10,12*row);
					  System.out.print("  inc ="+inc);
				  }
			  	}
		  	}
	  	}
	}
	public void checkMaxLength(){
		 int col_count=tab[0].getColumnCount();
		 length=new int[col_count+3];
		 for(int col=0;col<col_count;col++){
			  int maxlength=0;
			  int firstlen=0;
			  int lastlen=0;
			  for(int r=0;r<tab[0].getRowCount();r++){
				  if(tab[0].getValueAt(r,col)!=null)
					  firstlen=tab[0].getValueAt(r,col).toString().length();
				  else
					  continue;
				  //	  System.out.println("row="+r+" col=="+col+"   tab[0].getValueAt("+r+","+col+")=="+tab[0].getValueAt(r,col));
				  if(r==tab[0].getRowCount()-1)
					  break;
					// lastlen=tab[0].getValueAt(r+1,col).toString().length();
				  else
					  if(firstlen>lastlen)
						  if(maxlength<firstlen)
							  maxlength= firstlen;
				  	  else
				  		  maxlength=maxlength;
				  else
					  if(maxlength<lastlen)
						  maxlength= lastlen;
					  else
						  maxlength=maxlength;
			  }
			  length[col]=maxlength;
			  
			  System.out.println("length["+col+"]=="+length[col]);
		  }
	  }
	  int rows=1;
	  public void printCash(String value, int col, int r,int flag,int count_flag){
			int space=0;
			int	siz=0,inc=0;
			String nam="";
			siz=value.length();
			nam=value;
			this.rows=r;
			int col_count=0;
			  if(count_flag==0||count_flag==4){
				  col_count=3;  
			  }if(count_flag==10){
				  col_count=4;
			  }if(count_flag==20){
				  col_count=5;
			  }if(count_flag==21){
				  col_count=6;
			  }
			  //System.out.println("cash==="+cash);
			if(flag==1){
				 for(int z=siz-1;z>=0;z--){
					 new Character(nam.charAt(z)).toString();
				 }
			}
			else{
				try{
					double f=Double.parseDouble(nam);
					for(int z=siz-1;z>=0;z--){
						 new Character(nam.charAt(z)).toString();
					 space=space+5;
					}
				}catch(Exception e){
					if(col_count==5||col_count==6){
						if(nam.length()>=10&&nam.length()<22){
							inc=0;
							nam.substring(0,10);
							int b=row+inc;
							nam.substring(10,nam.length());
							this.inc=inc+1;
						}
						else if(nam.length()>22){
							inc=0;
							nam.substring(0,10);
							nam.substring(10,21);
							this.inc=inc+1;
							nam.substring(21,nam.length());
							this.inc=inc+2;;
						}
						else{
							nam.substring(0,nam.length());
						}
					}
					else{
						if(nam.length()>=7&&nam.length()<15){
							inc=0;
							nam.substring(0,7);
							int b=row+inc;
							nam.substring(7,nam.length());
							this.inc=inc+1;
						}
						else if(nam.length()>=15){
							inc=0;
							nam.substring(0,7);
							nam.substring(7,15);
							this.inc=inc+1;
							nam.substring(15,nam.length());
							this.inc=inc+2;;
						}
						else{
							nam.substring(0,nam.length());
						}
					}
				 }
			  }
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}

	public void setToPrint(Book b){
		if(dtm[0].getRowCount()>0||tab[0].getRowCount()>0){
			this.pageWidth = (int) (this.getPageWidth() * 72); // 4 inches
			this.pageHeight = (int) (this.getPageHeight()* 72);
     		paper.setSize(this.pageWidth, this.pageHeight);
			paper.setImageableArea(1 / 6 * 72, 1 / 5 * 72, this.pageWidth - 1 / 4 * 72, this.pageHeight );
			pageFormat.setPaper(paper);
			this.book=b;
			if(pjob.getPrintService()!=null){
				pjob.setPageable(this.book);
				try{
					if(pjob.printDialog())
						pjob.print();
				
				}
				catch(Exception e){
					e.printStackTrace();
					pjob.cancel();
					JOptionPane.showMessageDialog(null,"Printing error ! Could not print properly","Info",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null,"Check the Printer ! Check the connection","Info",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"No records to Print","Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		
		}
	}
		
	public void printSubHeading(Graphics g){
		int ro=8;
		int col_count=tab[0].getColumnCount();
		g.setFont(new Font("Book Antiqua",Font.PLAIN,10));
		int cnt=this.getStart_count();
		g.drawLine(this.getStart_count(),12*(ro-1) ,72*(this.pageWidth_inch), 12*(ro-1));
		for(int i=0;i<col_count;i++){
			if(length[i]<5&&length[i]!=0){
				try{		
					if(dtm[0].getColumnName(i).trim().length()<=5){
						g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						cnt=cnt+3;
					}
					else{
						g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						cnt=cnt+3;
					}
				}
				catch(Exception e){
					g.drawString(dtm[0].getColumnName(i),12*(cnt)+15,12*ro);
					cnt=cnt+3;
				}
					  
			}
			else if(length[i]>=5&&length[i]<10){
				try{
					if(dtm[0].getColumnName(i).trim().length()>=6){
						g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						cnt=cnt+4;
					}
					else{
						g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						cnt=cnt+4;
					}
				}catch(Exception e){
						  g.drawString(dtm[0].getColumnName(i),12*(cnt)+15,12*ro);
						  cnt=cnt+4;
				}
			}
			else if(length[i]>=10&&length[i]<20){
					try{
						  g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						  g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						  cnt=cnt+5;
					 }catch(Exception e){
					  	g.drawString(dtm[0].getColumnName(i).trim(),12*(cnt)+15,12*ro);
					  	cnt=cnt+5;
					  }
				  }
				  else if(length[i]>=20){
					  try{
						  g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						  g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						  cnt=cnt+6;
					  }catch(Exception e){
						  g.drawString(dtm[0].getColumnName(i).trim(),12*(cnt)+15,12*ro);
						  cnt=cnt+6;
					  }
				  }
				  else{
					  try{
						  g.drawString(dtm[0].getColumnName(i).trim().substring(0,dtm[0].getColumnName(i).indexOf(" ")),12*(cnt)+15,12*ro);
						  g.drawString(dtm[0].getColumnName(i).trim().substring(dtm[0].getColumnName(i).indexOf(" "),dtm[0].getColumnName(i).trim().length()),12*(cnt)+15,12*(ro+1));
						  cnt=cnt+3;
					  }catch(Exception e){
						  g.drawString(dtm[0].getColumnName(i).trim(),12*(cnt)+15,12*ro);
						  cnt=cnt+3;
					 }
				  }
			}
			g.drawLine(this.getStart_count(),12*((ro+2)) ,72*(this.pageWidth_inch), 12*(ro+2));
			  
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageHeight) {
		this.pageHeight_inch=pageHeight;
		this.pageHeight = pageHeight;
	}
	public int getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(int pageWidth) {
		this.pageWidth_inch=pageWidth;
		this.pageWidth = pageWidth;
	}
	public String getReport_heading() {
		return report_heading;
	}
	public void setReport_heading(String report_heading) {
		this.report_heading = report_heading;
	}  
	public void printAnalyser(){
		try{
		if(tab[0].getRowCount()>0){
			if(tab[0].getColumnCount()>15){
				JOptionPane.showMessageDialog(null,"Insert 15 X 12 size paper please","Info",JOptionPane.INFORMATION_MESSAGE);
				setPageWidth(15);
				setPageHeight(12);
				this.setStart_count(0);
			}
			else{
				if(tab[0].getColumnCount()<10&&tab[0].getColumnCount()>7){
					this.setStart_count(5);
				}
				else if(tab[0].getColumnCount()<10&&tab[0].getColumnCount()<7){
					this.setStart_count(8);
				}
				else{
					this.setStart_count(3);
				}
				JOptionPane.showMessageDialog(null,"Insert 10 X 12 size paper please","Info",JOptionPane.INFORMATION_MESSAGE);
			
				setPageWidth(10);
				setPageHeight(12);
			}
	//	System.out.println(p.getRowNumbers());
			book=new Book();
			int rowend=getRowNumbers(0,tab[0].getRowCount());
			setStart(0);
			setEnd(rowend);
			System.out.println("start >>"+getStart()+" end >>"+getEnd() );
			book.append((PrintEmpty)this.clone(),this.getPageFormat());
			int rowend1=0;
			int page_index=1;
			if(rowend<tab[0].getRowCount()){
				//setToPrint(book);
				setPage_index(page_index);
				setStart(rowend);
				
				for(;rowend1<tab[0].getRowCount()-1;){
					rowend1=getRowNumbers(rowend,tab[0].getRowCount()-1);
					this.setEnd(rowend1);
				//	book=new Book();
				//	book.append(this,this.getPageFormat());
					book.append((PrintEmpty)this.clone(),this.getPageFormat());
					setPage_index(page_index++);
					//setToPrint(book);
					setStart(rowend1);
					rowend=rowend1;
					System.out.println("start >>"+getStart()+" end >>"+getEnd() );
				}
				setToPrint(book);
//				setToPrint(book);
			}
			else{
				setPage_index(page_index);
				setToPrint(book);
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"No Records","Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getStart_count() {
		return start_count;
	}
	public void setStart_count(int start_count) {
		this.start_count = start_count;
	}
	public int getMax_inc() {
		return max_inc;
	}
	public void setMax_inc(int max_inc) {
		this.max_inc = max_inc;
	}
	/**
	 * @return the columnNum
	 */
	public int getColumnNum() {
		return columnNum;
	}
	/**
	 * @param columnNum the columnNum to set
	 */
	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	/**
	 * @return the nameColumn
	 */
	public int getNameColumn() {
		return nameColumn;
	}
	/**
	 * @param nameColumn the nameColumn to set
	 */
	public void setNameColumn(int nameColumn) {
		this.nameColumn = nameColumn;
	}
}
