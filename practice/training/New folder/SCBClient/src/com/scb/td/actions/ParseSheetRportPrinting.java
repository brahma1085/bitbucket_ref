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
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import exceptions.DateFormatException;


import com.scb.td.actions.*;
import com.scb.td.forms.*;

import general.Validations;

public class ParseSheetRportPrinting extends JPanel implements Printable,Cloneable  {
	//private  PrintAllReport printAllRep=null;
	private PrinterJob pjob;
	private PageFormat pageFormat;
	private Paper paper;
	private Book book;
	private Graphics2D g;
	
	private int pageWidth,pageHeight,pageWidth_inch,pageHeight_inch,start,end,page_index=1,start_count,row=0,inc=0,cnt=0,max_inc=0;
	private int startRow;;
	private int totalRows;
	private int increment=0,maxIncrement=1;
	private int nColInc=0,lastRowPrinted,dtmNumber;
	private int headingCount,headColNum;
	private int length[];
	private int totalColNum[],nameCol[];
	 
	private LinkedHashMap map=new LinkedHashMap();
	private LinkedHashMap subHeadCol,incrMap;
	private Hashtable<Integer,Double> totalColumns;
	private Hashtable adjustment_hash=new Hashtable();
	
	private JTable tab[];
	private DefaultTableModel dtm[];
	private JFrame f;
	
	/**
	 * 
	 */
	private String date;
	private String heading[];
	private PrintAllReportObject printObj;
	private String label_values[];
	private String labels[];
	private String modcode;
	/*
	 * priivate Constructor In order to maintain single object. There is getInstance method to 
	 * acquire the object 
	 */
	//public PrintAllReport(String heading[],String date) {
	public ParseSheetRportPrinting() {
		f=new JFrame();
		//pjob.defaultPage();
		
		totalColumns=new Hashtable<Integer,Double>();
		
	}
	
	/*
	 * Method exposed to client where client needs to send dtm and jtable array along with that client need to mention
	 * which are the name columns and which are all the columns need total and a linked hash map,which is needed if and only if 
	 * GUI table has to headings. 1st set of heading will be for certain number of columns. These first set of headings detail need
	 * to be specified in linked hash map. Key will be starting column and value will be ending column.
	 *  
	 */
	//public void setDetailsToPrint(DefaultTableModel[] dtm,JTable[] table,int nameColumn[],int total[],LinkedHashMap subHeadCol) {
	public void setDetailsToPrint(PrintAllReportObject printObj) {
		try {
			this.printObj=printObj;
			this.tab=printObj.getTab();
			this.dtm=printObj.getDtm();
			this.totalColNum=printObj.getTotalColNum();
			this.nameCol=printObj.getNameCol();
			this.subHeadCol=printObj.getSubHeadCol();
			this.heading=printObj.getHeading();
			this.date=printObj.getDate();
			this.label_values=printObj.getLabelValues();
			this.modcode=printObj.getAcType();
			this.totalRows=58;
			totalColumns.clear();
			
			setMaxIncrement(1);
			checkMaxLength(0);
			
			if(tab[0].getColumnCount()>14) {
				this.setPageHeight(12);
				this.setPageWidth(15);
				this.setPageWidth_inch(15);
				this.setPageHeight_inch(12);
			
			}else {
				this.setPageHeight(12);
				this.setPageWidth(10);
			
				this.setPageWidth_inch(10);
				this.setPageHeight_inch(12);
			}
			nColInc=totalLengthReqd(0);
			int totalSize=this.getPageWidth_inch()*12*6;
			int calSize=12*(Integer)adjustment_hash.get(dtm[0].getColumnCount()-1);
			System.out.println("totalSize >> "+totalSize+" calSize>>"+calSize);
			if(calSize>(10*12*6-20)) {
				this.setPageHeight(12);
				this.setPageWidth(15);
				this.setPageWidth_inch(15);
				this.setPageHeight_inch(12);
				JOptionPane.showMessageDialog(null,"Insert 132 column paper");
			
			}else {
				this.setPageHeight(12);
				this.setPageWidth(10);
				this.setPageWidth_inch(10);
				this.setPageHeight_inch(12);
				JOptionPane.showMessageDialog(null,"Insert 80 column paper");
			}
		
		
			f.setSize(this.getPageWidth()*12*6,this.getPageHeight()*12*6);
			pjob = PrinterJob.getPrinterJob();
			pageFormat = new PageFormat();
		//	//System.out.println("pageWidth   >> "+pageWidth);
			this.pageWidth = (int) (pageWidth * 72); // 4 inches
			this.pageHeight = (int) (pageHeight * 72);
		//	//System.out.println("pageWidth   >> "+pageWidth);
			paper = new Paper();
			paper.setSize(this.pageWidth, this.pageHeight);
			paper.setImageableArea(1 / 6 * 72, 1 / 5 * 72, this.pageWidth - 1 / 4 * 72, this.pageHeight );
			pageFormat.setPaper(paper);
			try {
				printAnalyser();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * this function checks the maximum data length of each individual column
	 * column length will be decided based on this calculation
	 */
	private int[] checkMaxLength(int dtmNum){
		 int col_count=dtm[dtmNum].getColumnCount();
		 ////System.out.println("col_count >> "+col_count);
		 length=new int[col_count+3];
		 for(int col=0;col<col_count;col++){
			  int maxlength=0;
			  int firstlen=0;
			  int lastlen=0;
			  for(int r=0;r<dtm[dtmNum].getRowCount();r++){
				  if(dtm[dtmNum].getValueAt(r,col)!=null)
					  firstlen=dtm[dtmNum].getValueAt(r,col).toString().length();
				  else
					  continue;
				  if(r==dtm[dtmNum].getRowCount()-1)
					  break;
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
		  }
		 return length;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 *
	 * 
	 */
	public void paint(Graphics g)
	{
		synchronized (this) {
			
		
		super.paint(g);
		g.setFont(new Font("Sans Serif 10cpi",Font.PLAIN,10));
		/*g.drawString(MainScreen.head.getBankName(),(this.getPageWidth_inch()/2-3)*12*6,12*2);
		g.drawString(MainScreen.head.getBankLocation(),(this.getPageWidth_inch()/2-2)*12*6+20,12*3);
		g.drawString("Page: "+this.getPage_index(),72*(this.pageWidth_inch-2),12*3);
		//g.drawString("Date: "+date,12*3,12*4);
		g.drawString(""+this.heading[0]+" As on "+date,(this.getPageWidth_inch()/2-3)*12*6,12*4);*/
		int rows=4;
		/*for(int n=1;n<heading.length;n++)
			g.drawString(""+heading[n],12*1,12*(++rows));*/
		
		////System.out.println("this.getDtmNumber() > "+this.getDtmNumber()+" start > "+this.start);
		//g.drawString(MainScreen.head.getBankName(),(this.getPageWidth_inch()/2-3)*12*6,12*2);//MainScreen.head.getBankName(),72*(10/2-1),12*4);
		//g.drawString(MainScreen.head.getBankLocation(),(this.getPageWidth_inch()/2-2)*12*6+20,12*3);//MainScreen.head.getBankLocation(),72*(10/2),12*5);
		
		g.drawString("Account Statement as On: "+printObj.getDate(),(this.getPageWidth_inch()/2-3)*12*6,12*4);
		printAccountDetails(g,this.modcode,label_values);
		int ro=printHeading(g,this.getDtmNumber());
		
		printData(g,ro,this.getDtmNumber());
		f.getContentPane().add(this);
	//s	f.setVisible(true);
		}
   	}
	/*
	 * 
	 */
	private void printAnalyser() throws Exception{
	
		this.setDtmNumber(0);
		int pag_ind=1;
		book=new Book();
		int rowend=getRowNumber(0,tab[0].getRowCount(),0,17);
		setStart(0);
		setEnd(rowend);
		System.out.println("start=="+this.getStart()+" end->"+this.getEnd());
	//	//System.out.println("start >>"+getStart()+" end >>"+getEnd() );
		this.setPage_index(pag_ind);
		book.append((ParseSheetRportPrinting)this.clone(),this.pageFormat);
		
				
		int rowend1=0;
		int page_index=1;
		if(rowend<tab[0].getRowCount()){
			setStart(rowend);
			for(;rowend1<tab[0].getRowCount();){
				rowend1=getRowNumber(rowend,tab[0].getRowCount(),0,17);
				this.setEnd(rowend1);
				this.setPage_index(++pag_ind);
				book.append((ParseSheetRportPrinting)this.clone(),this.pageFormat);
				
				setStart(rowend1);
				rowend=rowend1;
				////System.out.println("start >>"+getStart()+" end >>"+getEnd() );
			}
			
		}
		if(dtm.length>1)
		if(dtm[1]!=null) {
			if(this.subHeadCol==null) {
				////System.out.println("-sum-->"+(this.getLastRowPrinted()+dtm[1].getRowCount()+2)+" this.getLastRowPrinted()-->"+this.getLastRowPrinted());
			//	if((this.getLastRowPrinted()+dtm[1].getRowCount()+2)>58) {
					this.nameCol=null;
					adjustment_hash.clear();
					this.setDtmNumber(1);
					this.setStart(0);
					this.setEnd(dtm[this.getDtmNumber()].getRowCount());
					getRowNumber(0,dtm[this.getDtmNumber()].getRowCount(),this.getDtmNumber(),11);
					this.setPage_index(++pag_ind);
					book.append((ParseSheetRportPrinting)this.clone(),this.pageFormat);
			//	}
			}
		}
	
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
	/*
	 * Function helps in printing word by word and if length is not sufficient then will shift the word to next line
	 */
	
	
	private String toCamelCase(String s,int maxSize) {
		String camel="";
		String token="";
		try{
		//	//System.out.println("s-1.0->"+s.substring(0,(s.length()-3)));
			Double.parseDouble(s.trim().substring(0,(s.trim().length()-3)));
				return s;
		}catch(Exception e) {
			
		}
		try {
			////System.out.println("s-1->"+s.trim().substring(2,(s.trim().length())));
			Integer.parseInt(s.trim().substring(2,(s.trim().length())));
			return s;
		}catch(Exception e1) {
			
		}
		if(s.length()<=2) {
			return s;
		}
		StringTokenizer stk=new StringTokenizer(s.trim(),"' '");
		if(stk.countTokens()>0) {
			while(stk.hasMoreTokens()) {
				token=stk.nextToken();
				if(token.length()<=2) {
					camel=camel+token+" ";
				}else
					camel=camel+token.substring(0,1).toUpperCase()+token.substring(1,token.length()).toLowerCase()+" ";
			}
		}else
			camel=s.substring(0,1).toUpperCase()+s.substring(2,s.length()).toLowerCase();
		if(camel.length()>maxSize)
			return camel.substring(0,maxSize);
		return camel;
	}
	private int display(Graphics g,int col_no,int row_no,String str,int maxLength,int secondMaxLen,int subColno,int colLength) {
		increment=0;
	 	int row=row_no;
	 	String printString="";
	 	boolean printFlag=true;
	 	String prvs="";
	 	////System.out.println("max >> "+maxLength);
	 	////System.out.println("sec max >> "+secondMaxLen);
	 	
		try {
			StringTokenizer stk;
			try {
				Validations.checkDate(str);
				stk=new StringTokenizer(str.trim(),"' ',");
			}catch(Exception e) {
				stk=new StringTokenizer(str.trim(),"' ',/");
			}
			String printable="";
			int tokenSize=stk.countTokens();
			int count=0;
			int remainingTokens=tokenSize;
			String tokensArray[]=new String[tokenSize];
			while(stk.hasMoreTokens()) {
				printString=stk.nextToken();
				if(printString==null||printString.equals("null")) {
					printString="";
					tokensArray[count]=printString;
					count++;
					continue;
				}	
				tokensArray[count]=printString;
				count++;
				prvs=printable;
				printable+=" "+printString;
				if(printable.trim().length()>maxLength) {
					printable=prvs;
					remainingTokens=tokenSize-(count-1);
					g.drawString(" "+printable,12*col_no,12*row);
					if(tokenSize==2) {
						row=row+2;
						increment=increment+2;
					}
					else {
						row++;
						increment++;
					}
					maxLength=secondMaxLen;
					col_no=subColno;
					prvs="";
					printFlag=false;
					printable=printString.trim();
				}else {
					prvs=printString;
				}
			}
			if(remainingTokens!=0) {
				int i=tokenSize-remainingTokens;
				printable="";
				while(i<tokenSize) {
					printable+=" "+tokensArray[i];
					i++;
				}
				if(!printable.equals(""))
					increment++;
			}
		
			g.drawString(" "+printable,12*col_no,12*row);
			row++;
			if(increment>getMaxIncrement()) {
				setMaxIncrement(increment);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	/*
	 * Function is used to print group or first set of headings
	 */
	private void printSubHeading(Graphics g,int ro) {
		//int nColInc=totalLengthReqd();
		
		Set colSet=this.subHeadCol.keySet();
		Iterator keyEn=colSet.iterator();
		int i=0;
		String printHead,printArHead="";
		g.drawLine(0,(ro-1)*12, ((Integer)adjustment_hash.get(dtm[0].getColumnCount()-1))*12, (ro-1)*12);
		String attach="-";
		int key,val,length, pixel=0;
		
		while(i<dtm[1].getColumnCount()) {
			printHead=dtm[1].getColumnName(i);
			if(!printHead.equals("")) {
			if(keyEn.hasNext()) {
			key=(Integer)keyEn.next();
			val=(Integer)subHeadCol.get(key);
			length=12*((Integer)adjustment_hash.get(val))-12*((Integer)adjustment_hash.get(key));
			////System.out.println("length-->"+length);
			pixel=printHead.length()*5;
			while(pixel<length) {
				printHead=attach+printHead+attach;
				pixel=printHead.length()*5;
			}
			if(key==0)
				g.drawString(" "+printHead, 0, 12*ro);
			else
				g.drawString(" "+printHead, 12*((Integer)adjustment_hash.get(key)), 12*ro);
			}}
			//g.drawLine(12*((Integer)adjustment_hash.get(key)),12*(startRow-1), 12*((Integer)adjustment_hash.get(key)), 12*(startRow+4));
		//	g.drawString(""+(Integer)adjustment_hash.get(key), (Integer)adjustment_hash.get(key)*12,12*2);
			i++;
		}
	}
	/*
	 * Function is used to print all individual column heading
	 */
	private int printHeading(Graphics g,int dtmNum) {
		int length[]=checkMaxLength(dtmNum);
		int headingCnt=0;
		this.startRow=13;
		if(heading!=null) {
		if(heading.length>1)
			this.startRow=8;
		else
			this.startRow=6;
		}
		int endRow;
		int nColInc=totalLengthReqd(dtmNum);
		////System.out.println("nColInc>>"+nColInc);
		boolean nameColFlag=false;
		g.setFont(new Font("Sans Serif 10cpi",Font.PLAIN,10));
		int ro=this.startRow;
		int ro1=this.startRow-1;
		if(dtm.length>1) {
			if(this.subHeadCol!=null)
				printSubHeading(g,ro1);
			else
				g.drawLine(0,(ro-1)*12, ((Integer)adjustment_hash.get(dtm[dtmNum].getColumnCount()-1))*12, (ro-1)*12);
		}else {
			g.drawLine(0,(ro-1)*12, ((Integer)adjustment_hash.get(dtm[dtmNum].getColumnCount()-1))*12, (ro-1)*12);
		}
		
		g.drawLine(0,(ro-1)*12, headingCnt*12, (ro-1)*12);
		for(int col=0;col<dtm[dtmNum].getColumnCount();col++){
			nameColFlag=false;
			if(nameCol!=null) {
				for(int k=0;k<nameCol.length;k++) {
					if(nameCol[k]==col) {
						nameColFlag=true;
						break;
					}
				}
			}
			if(length[col]==0) {
				display(g, headingCnt, ro,dtm[dtmNum].getColumnName(col).trim(), 5,5, headingCnt,(headingCnt+3));
				headingCnt=headingCnt+3;
			}
			if(length[col]<=2&&length[col]!=0){
				if(nameColFlag) {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 8,8, headingCnt,(headingCnt+nColInc));
					headingCnt=headingCnt+nColInc;
					
				}else {
					if(dtm[dtmNum].getColumnCount()>15) {
						endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 4,4, headingCnt,(headingCnt+2));
						headingCnt=headingCnt+2;
					}
					else {
						endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 6,6, headingCnt,(headingCnt+3));
						headingCnt=headingCnt+3;
					}
				}
			}else if(length[col]>=3&&length[col]<6) {
				if(nameColFlag) {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 8,8, headingCnt,(headingCnt+nColInc));
					headingCnt=headingCnt+nColInc;
				}else {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 5,5, headingCnt,(headingCnt+3));
					headingCnt=headingCnt+3;
				}
				
			}else if(length[col]>=6&&length[col]<=10) {
				if(nameColFlag) {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 8,8, headingCnt,(headingCnt+nColInc));
					headingCnt=headingCnt+nColInc;
				}else {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 5,5, headingCnt,(headingCnt+4));
					headingCnt=headingCnt+4;
				}
				
			}else if(length[col]>10&&length[col]<20) {
				if(nameColFlag) {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 10,10, headingCnt,(headingCnt+nColInc));
					headingCnt=headingCnt+nColInc;
				}else {
					endRow=display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 10,10, headingCnt,(headingCnt+5));
					headingCnt=headingCnt+6;
				}
				
			}else if(length[col]>20) {
				if(nameColFlag) {
					display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 15,15, headingCnt,(headingCnt+nColInc));
					headingCnt=headingCnt+nColInc;
				}else {
					display(g, headingCnt, ro, dtm[dtmNum].getColumnName(col).trim(), 6,6, headingCnt,(headingCnt+6));
					headingCnt=headingCnt+7;
				}
				
			}
			
			//g.drawLine(headingCnt*12,0, headingCnt*12, this.getPageHeight_inch()*12*6);
		//	g.drawString(col+"-"+headingCnt, headingCnt*12,12*1);
		}
		ro=ro+getMaxIncrement();;
		g.drawLine(0,ro*12, headingCnt*12, ro*12);
		return ro;
	}
	private int getRowNumber(int start,int end,int dtmNum,int row) {
		int length[]=checkMaxLength(dtmNum);
		int ro=row+1;
		int r=start;
		//if(this.getDtmNumber()==1)
		totalLengthReqd(this.getDtmNumber());
		if(dtmNum==0) {
			for(;r<end;r++){
				setMaxIncrement(1);
				for(int c=0;c<dtm[dtmNum].getColumnCount();c++) {
					if(dtm[dtmNum].getValueAt(r,c)!=null&&dtm[dtmNum].getValueAt(r,c).toString().equals("")) {
						continue;
					}
					if(length[c]==0){
						try {
							printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
						}catch(Exception e) {
							
						}
					}
					if(length[c]<=2&&length[c]!=0){
						try {
							printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>=3&&length[c]<=6){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>6&&length[c]<=10){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>10&&length[c]<20){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>20) {
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}
			}
			ro=ro+getMaxIncrement();
			setLastRowPrinted(ro);
			////System.out.println("getMaxIncrement() >> "+getMaxIncrement()+" ro >> "+ro);
				if(ro>58) {
				break;
				}
			}
			/*if(this.getEnd()==tab[0].getRowCount()) {
				if(dtm.length>1)
				if((this.getLastRowPrinted()+dtm[1].getRowCount()+2)<=58) {
					this.setDtmNumber(1);
					this.nameCol=null;
					adjustment_hash.clear();
					this.setDtmNumber(1);
					this.setStart(0);
					this.setEnd(dtm[this.getDtmNumber()].getRowCount());
					checkMaxLength(this.getDtmNumber());
					totalLengthReqd(this.getDtmNumber());
					this.startRow=this.getLastRowPrinted();
					ro=printHeading(g,this.getDtmNumber());
					printData(g, ro, this.getDtmNumber());
					getRowNumber(0, dtm[dtmNum].getRowCount(), dtmNum, getLastRowPrinted());
					this.setEnd(tab[0].getRowCount()+1);
					
				}
			}*/
			return r;
		
		}else {
			for(;r<end;r++){
				setMaxIncrement(1);
				for(int c=0;c<dtm[dtmNum].getColumnCount();c++) {
					if(dtm[dtmNum].getValueAt(r,c)!=null&&dtm[dtmNum].getValueAt(r,c).toString().equals("")) {
						continue;
					}
					if(length[c]==0){
						try {
							printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
						}catch(Exception e) {
							
						}
					}
					if(length[c]<=2&&length[c]!=0){
						try {
							printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>=3&&length[c]<=6){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>6&&length[c]<=10){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>10&&length[c]<20){
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>20) {
					try {
						printCashAndString(c,ro,dtm[dtmNum].getValueAt(r,c).toString(),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()),Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}
			}
			ro=ro+getMaxIncrement();
			setLastRowPrinted(ro);
			////System.out.println("getMaxIncrement() >> "+getMaxIncrement()+" ro >> "+ro);
			
			if(ro>58) {
				break;
			}
			
			}
			System.out.println("r=="+r);
			return r;
		}
	}
	
	private void printData(Graphics g,int row,int dtmNum) {
		int ro=row+1;
		boolean pagTotal=true;
		for(int i=0;i<this.dtm[dtmNum].getColumnCount();i++)
			totalColumns.put(i,0.0);
		for(int r=this.getStart();r<this.getEnd();r++){
			setMaxIncrement(1);
			for(int c=0;c<dtm[dtmNum].getColumnCount();c++) {
				if(tab[dtmNum].getValueAt(r,c)!=null&&tab[dtmNum].getValueAt(r,c).toString().equals("")||tab[dtmNum].getValueAt(r,c)==null) {
					continue;
				}
				if(length[c]==0) {
					try {
						printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
					
					}
				}
				if(length[c]<=2&&length[c]!=0){
					try {
					printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>=3&&length[c]<=6) {
					try {
					printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>6&&length[c]<=10) {
					try {
					printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>10&&length[c]<20) {
					try {
					printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}else if(length[c]>20) {
					try {
					printCashAndString(g,c,ro,tab[dtmNum].getValueAt(r,c).toString(),0,0,Integer.parseInt(adjustment_hash.get(c).toString()));
					}catch(Exception e) {
						
					}
				}
			//	g.drawString(c+"-"+adjustment_hash.get(c), (Integer)adjustment_hash.get(c)*12,12*3);
			}
			ro=ro+getMaxIncrement();
			/*if(r==this.dtm[dtmNum].getRowCount()-1) {
				System.out.println("this.getStart() -->> "+this.getStart()+" this.getEnd() >> "+this.getEnd());
				if(this.getDtmNumber()==0)
				for(int z=this.getStart();z<this.getEnd();z++) {
					if((Double)totalColumns.get(z)!=0) {
						if(pagTotal) {
							g.drawString("Pg.Total= ",0,(ro+1)*12);
							pagTotal=false;
						}
						////System.out.println("totalColumns.get(z) >> "+totalColumns.get(z));
						printCashOrNumber(g,z,(ro+1),""+Double.parseDouble(totalColumns.get(z).toString()),1);
					}
						
				}
			}*/
			setLastRowPrinted(ro);
			System.out.println("ro >> "+ro);
			if(ro>58) {
				System.out.println("this.getStart() >> "+this.getStart()+" this.getEnd() >> "+this.getEnd());
				/*if(this.getDtmNumber()==0)
				for(int n=this.getStart();n<this.getEnd();n++) {
					System.out.println("n-->"+n+" "+totalColumns.get(n));
					if((Double)totalColumns.get(n)!=0) {
						if(pagTotal) {
							g.drawString("Pg.Total= ",0,(ro+1)*12);
							pagTotal=false;
						}
						System.out.println();
						printCashOrNumber(g,n,(ro+1),""+Double.parseDouble(totalColumns.get(n).toString()),1);
					}
						
				}*/
				break;
			}
		}
		System.out.println(">>>-->"+this.printObj.isColTotIsReqd());
		if(this.printObj.isColTotIsReqd()) {
		if(this.getDtmNumber()==0)
			for(int n=0;n<this.dtm[dtmNum].getColumnCount();n++) {
				System.out.println("n-->"+n+" "+totalColumns.get(n));
				if((Double)totalColumns.get(n)!=0) {
					if(pagTotal) {
						g.drawString("Pg.Total= ",0,(ro+1)*12);
						pagTotal=false;
					}
					System.out.println();
					printCashOrNumber(g,n,(ro+1),""+DoubleFormat.doubleToString(Double.parseDouble(totalColumns.get(n).toString()),2),1);
				}
					
			}
		}
		g.drawLine(0,(ro+2)*12, ((Integer)adjustment_hash.get(dtm[dtmNum].getColumnCount()-1))*12, (ro+2)*12);
		
		ro=ro+3;
		g.drawString("Note:",12*2,12*ro);
		if(printObj.getNoteString()!=null) {
			for(int k=0;k<printObj.getNoteString().length;k++) {
				display(g, 4, ro, printObj.getNoteString()[k], 100, 100, 4, 100);
				ro=ro+getMaxIncrement();
			}
		}
		
		System.out.println("this.getStart() ->->> "+this.getStart()+" this.getEnd() >> "+this.getEnd());
	}
	private int printCashAndString(int col,int row,String print,int maxLength,int secMaxLen,int subCol) {
		int logicFlag=3;
		int curRow;
		String decAftr="";
		int len;
		//////System.out.println("adjustment_hash.get 0("+col+")>> "+adjustment_hash.get(col));
		try {
			if(print.trim().contains(".")) {
				try {
					double printAmt=Double.parseDouble(print);

					decAftr=print.substring((print.indexOf('.')+1),print.length());
					len=decAftr.length();
					if(len<2&&len>0) 
						print=print+"0";
					logicFlag=1;
				}catch(NumberFormatException e) {
					logicFlag=3;
					try{
						Double.parseDouble(print.trim().substring(0,(print.trim().length()-3)));
						logicFlag=4;
					}catch(Exception ee) {
						logicFlag=3;
					}
				}
			}else {
				try {
					int printNo=Integer.parseInt(print);
					logicFlag=2;
				}catch(NumberFormatException e) {
					logicFlag=3;
					
				}
			}
			if(logicFlag==4) {
				curRow=printCashAndString(col,row,print,logicFlag);
			}
			if(logicFlag==1||logicFlag==2) {
				curRow=printCashOrNumber(col,row,print,logicFlag);
			}
			if(logicFlag==3) {
				if(col==0)
					displayDummyData(0,row, print,(Integer.parseInt(adjustment_hash.get(col).toString())*10/6) , (Integer.parseInt(adjustment_hash.get(col).toString())*10/6), (Integer.parseInt(adjustment_hash.get(col).toString())), (Integer.parseInt(adjustment_hash.get(col).toString())));
				else {
					displayDummyData((Integer.parseInt(adjustment_hash.get(col-1).toString())),row, print,((Integer.parseInt(adjustment_hash.get(col).toString()))-Integer.parseInt(adjustment_hash.get(col-1).toString()))*10/6 +2, ((Integer.parseInt(adjustment_hash.get(col).toString()))-Integer.parseInt(adjustment_hash.get(col-1).toString()))*10/6+2, (Integer.parseInt(adjustment_hash.get(col-1).toString())), (Integer.parseInt(adjustment_hash.get(col-1).toString())));
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	private int printCashAndString(Graphics g,int col,int row,String print,int maxLength,int secMaxLen,int subCol) {
		int logicFlag=3;
		int curRow;
		double amt=0.00F;
		double printAmt=0.00F;
		double amtInMap=0.00F;
		String decAftr="";
		int len=0;
		String amtInMapS; 
		try {
			if(print.trim().contains(".")) {
				try {
					
					printAmt=Double.parseDouble(print);
					
					decAftr=print.substring((print.indexOf('.')+1),print.length());
					len=decAftr.length();
					if(len==1) { 
						print=print+"0";
					}
					logicFlag=1;
					try {
						System.out.println(col+" -> "+totalColumns.containsKey(col));
						if(totalColumns.containsKey(col))
							amtInMap=Double.parseDouble(totalColumns.get(col).toString().substring(0, (totalColumns.get(col).toString().indexOf('.')+3)));
					}catch(Exception e) {
						if(totalColumns.containsKey(col))
							amtInMap=Double.parseDouble(totalColumns.get(col).toString().substring(0, (totalColumns.get(col).toString().indexOf('.')+2)));
					}
					if(totalColumns.containsKey(col)) {
						System.out.println(col+"true");
						amtInMapS =""+DoubleFormat.doubleToString(amtInMap, 2);
						decAftr=amtInMapS.substring((amtInMapS.indexOf('.')+1),amtInMapS.length());
						len=decAftr.length();
						if(len==1) { 
							amtInMapS=amtInMapS+"0";
						}
						amt=DoubleFormat.doublePrecision(amtInMap,2)+DoubleFormat.doublePrecision(printAmt,2);
						System.out.println("amt >> "+amt);
						
						totalColumns.put(col,amt);
					}else {
						totalColumns.put(col,0.0);
					}
				}catch(NumberFormatException e) {
					logicFlag=3;
					try{
						////System.out.println("s-1.0->"+print.substring(0,(print.length()-3)));
						Double.parseDouble(print.trim().substring(0,(print.trim().length()-3)));
						logicFlag=4;
					}catch(Exception ee) {
						logicFlag=3;
					}
				}
			}else {
				try {
					int printNo=Integer.parseInt(print);
					logicFlag=2;
				}catch(NumberFormatException e) {
					logicFlag=3;
				}
				
			}
			if(logicFlag==4) {
				curRow=printCashWithString(g,col,row,print,logicFlag);
			}
			if(logicFlag==1||logicFlag==2) {
				curRow=printCashOrNumber(g,col,row,print,logicFlag);
			}
			if(logicFlag==3) {
				if(col==0)
					displayData(g,0,row, print,(Integer.parseInt(adjustment_hash.get(col).toString())*10/5) , (Integer.parseInt(adjustment_hash.get(col).toString())*10/5), (Integer.parseInt(adjustment_hash.get(col).toString())), (Integer.parseInt(adjustment_hash.get(col).toString())));
				else {
					displayData(g,(Integer.parseInt(adjustment_hash.get(col-1).toString())),row, print,((Integer.parseInt(adjustment_hash.get(col).toString()))-Integer.parseInt(adjustment_hash.get(col-1).toString()))*10/6 +2, ((Integer.parseInt(adjustment_hash.get(col).toString()))-Integer.parseInt(adjustment_hash.get(col-1).toString()))*10/6+2, (Integer.parseInt(adjustment_hash.get(col-1).toString())), (Integer.parseInt(adjustment_hash.get(col-1).toString())));
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	/*
	 * Function is used to print double primitive or int primitive
	 */
	private int printCashWithString(Graphics g,int col,int row,String cash,int logicFlag) {
		
		int space=5;
		int len=0;
		String decAftr="";
		int siz=cash.length();
		int initCharSpace=7;
		for(int z=siz-1;z>=0;z--){
			if(logicFlag==2) {
				if(z==siz-1) {
					if(Integer.parseInt(adjustment_hash.get(col).toString())==3&&cash.length()<=2) {
						space=space+10;
					}else {
						space=space+5;
					}
				}
			}
			
			g.drawString(""+cash.charAt(z),12*(Integer.parseInt(adjustment_hash.get(col).toString()))+initCharSpace-space,12*row);
			////System.out.println("space==="+space);
			if(z==siz-1||z==siz-2) {
				//initCharSpace=0;
				space=space+7;
			}else
				space=space+5;
			
		}
		
		return row;
	}
	private int printCashAndString(int col,int row,String cash,int logicFlag) {
		int space=5;
		int len=0;
		String decAftr="";
		int siz=cash.length();
		for(int z=siz-1;z>=0;z--){
			if(logicFlag==2) {
				if(z==siz-1) {
					if(Integer.parseInt(adjustment_hash.get(col).toString())==3&&cash.length()<=2) {
						space=space+10;
					}else {
						space=space+5;
					}
				}
			}
			space=space+5;
		}
		
		return row;
	}
	
	
	private int printCashOrNumber(Graphics g,int col,int row,String cash,int logicFlag) {
		int space=5;
		int len=0;
		String decAftr="";
		////System.out.println("adjustment_hash.get(col) >> "+adjustment_hash.get(col)+"  9-->"+col);
		if(logicFlag==1) {
			
			decAftr=cash.substring((cash.indexOf('.')+1),cash.length());
			len=decAftr.length();
			if(len==1) {
				cash=cash.concat("0");
				////System.out.println("cash-->"+cash);
			}else if(len>2) {
				cash=cash.substring(0,cash.indexOf('.'))+"."+decAftr.substring(0,2);
			}
		}
		int siz=cash.length();
		for(int z=siz-1;z>=0;z--){
			if(logicFlag==2) {
				if(z==siz-1) {
					if(Integer.parseInt(adjustment_hash.get(col).toString())==3&&cash.length()<=2) {
						space=space+10;
					}else {
						space=space+5;
					}
				}
			}
			
			g.drawString(""+cash.charAt(z),12*(Integer.parseInt(adjustment_hash.get(col).toString()))-space,12*row);
			space=space+5;
		}
		return row;
	}
	private int printCashOrNumber(int col,int row,String cash,int logicFlag) {
		int siz=cash.length();
		int space=5;
		if(cash!=null&&!cash.equals("null")) {
			for(int z=siz-1;z>=0;z--){
				//System.out.println(">>col "+col+" >>"+adjustment_hash.get(col));
				space=space+5;
			}
		}
		
		return row;
	}
	private int displayDummyData(int col_no,int row_no,String str,int maxLength,int secondMaxLen,int subColno,int colLength) {
		increment=0;
	 	int row=row_no;
	 	String printString="";
	 	boolean printFlag=true;
	 	String prvs="";
	 	try {
			StringTokenizer stk;//=new StringTokenizer(str.trim(),"' ',");
			String printable="";
			try {
				String date=Validations.checkDate(str.trim());
				stk=new StringTokenizer(checkDate(str.trim()),"' ',");
			}catch(Exception e) {
				stk=new StringTokenizer(str.trim(),"' ',/");
			}
			int tokenSize=stk.countTokens();
			int count=0;
			int remainingTokens=tokenSize;
			String tokensArray[]=new String[tokenSize];
			while(stk.hasMoreTokens()) {
				printString=stk.nextToken();
				if(printString==null||printString.equals("null")) {
					printString="";
					tokensArray[count]=printString;
					count++;
					continue;
				}	
				tokensArray[count]=printString;
				count++;
				prvs=printable;
				printable+=" "+printString;
				//System.out.println("printable 00->"+printable+" maxLength >> "+maxLength+" printable.len >> "+printable.length());
				if(printable.trim().length()>maxLength) {
					printable=prvs;
					remainingTokens=tokenSize-(count-1);
					printable=toCamelCase(printable,maxLength);
					row++;
					increment++;
					maxLength=secondMaxLen;
					col_no=subColno;
					prvs="";
					printFlag=false;
					printable=printString.trim();
				//	System.out.println("printable --> "+printable);
//					/return row;
					
				}else {
					prvs=printString;
				}
			}
			if(remainingTokens!=0) {
				int i=tokenSize-remainingTokens;
				printable="";
				while(i<tokenSize) {
					printable+=" "+tokensArray[i];
					i++;
				}
				System.out.println("printable >> > "+printable);
				if(!printable.equals(""))
					increment++;
				System.out.println("increment >> > "+increment);
				
				System.out.println("getMaxIncrement >> > "+getMaxIncrement());
			}
			
			toCamelCase(printable,maxLength);
			//g.drawString(" "+toCamelCase(printable),12*col_no,12*row);
			row++;
			if(increment>getMaxIncrement()) {
				setMaxIncrement(increment);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	private int displayData(Graphics g,int col_no,int row_no,String str,int maxLength,int secondMaxLen,int subColno,int colLength) {
		increment=0;
	 	int row=row_no;
	 	String printString="";
	 	boolean printFlag=true;
	 	String prvs="";
	 	//System.out.println("max >> "+maxLength);
	 	////System.out.println("sec max >> "+secondMaxLen);
		try {
			StringTokenizer stk;//=new StringTokenizer(str.trim(),"' ',");
			String printable="";
			try {
				String date=Validations.checkDate(str.trim());
				//System.out.println("col >> "+col_no+" date-"+date);
				stk=new StringTokenizer(checkDate(str.trim()),"' ',");
				//System.out.println("*********************************");
			}catch(Exception e) {
				stk=new StringTokenizer(str.trim(),"' ',/");
			}
			
			int tokenSize=stk.countTokens();
			int count=0;
			int remainingTokens=tokenSize;
			String tokensArray[]=new String[tokenSize];
			while(stk.hasMoreTokens()) {
				printString=stk.nextToken();
				if(printString==null||printString.equals("null")) {
					printString="";
					tokensArray[count]=printString;
					count++;
					continue;
				}	
				tokensArray[count]=printString;
				count++;
				prvs=printable;
				printable+=" "+printString;
			//	System.out.println("printable 1 00->"+printable+" maxLength >> "+maxLength+" printable.len >> "+printable.length());
				if(printable.trim().length()>maxLength) {
					printable=prvs;
					remainingTokens=tokenSize-(count-1);
					g.drawString(" "+toCamelCase(printable,maxLength),12*col_no,12*row);
					//System.out.println("printable====>"+printable);
					row++;
					increment++;
					maxLength=secondMaxLen;
					col_no=subColno;
					prvs="";
					printFlag=false;
					printable=printString.trim();
					//prinreturn row;
					
				}else {
					prvs=printString;
				}
			}
			if(remainingTokens!=0) {
				int i=tokenSize-remainingTokens;
				printable="";
				while(i<tokenSize) {
					printable+=" "+tokensArray[i];
					i++;
				}
				if(!printable.equals(""))
					increment++;
				//System.out.println("printable====>"+printable);
			}
			
			g.drawString(" "+toCamelCase(printable,maxLength),12*col_no,12*row);
			row++;
			if(increment>getMaxIncrement()) {
				setMaxIncrement(increment);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	private int display(int col_no,int row_no,String str,int maxLength,int secondMaxLen,int subColno,int colLength) {
		increment=0;
		int row=row_no;
	 	String printString="";
	 	boolean printFlag=true;
	 	String prvs="";
	 	//System.out.println("str >> "+str);
	 	////System.out.println("sec max >> "+secondMaxLen);
		try {
			StringTokenizer stk=new StringTokenizer(str.trim(),"' ',");
			String printable="";
			int tokenSize=stk.countTokens();
			int count=0;
			int remainingTokens=tokenSize;
			String tokensArray[]=new String[tokenSize];
			while(stk.hasMoreTokens()) {
				printString=stk.nextToken();
				if(printString==null||printString.equals("null")) {
					printString="";
					tokensArray[count]=printString;
					count++;
					continue;
				}	
				tokensArray[count]=printString;
				count++;
				prvs=printable;
				printable+=" "+printString;
				if(printable.trim().length()>maxLength) {
					printable=prvs;
					remainingTokens=tokenSize-(count-1);
					//g.drawString(" "+printable,12*col_no,12*row);
					if(tokenSize==2) {
						row=row+2;
						increment=increment+2;
					}
					else {
						row++;
						increment++;
					}
					maxLength=secondMaxLen;
					col_no=subColno;
					prvs="";
					printFlag=false;
					printable=printString.trim();
				}else {
					prvs=printString;
				}
			}
			if(remainingTokens!=0) {
				int i=tokenSize-remainingTokens;
				printable="";
				while(i<tokenSize) {
					printable+=" "+tokensArray[i];
					i++;
				}
				if(!printable.equals(""))
					increment++;
			}
			
			//g.drawString(" "+printable,12*col_no,12*row);
			if(increment>getMaxIncrement()) {
				setMaxIncrement(increment);
			}
			row++; 
		}catch(Exception e) {
			e.printStackTrace();
			return row;
		}
		return row;
	}
	
	/*
	 * checks the length and adjusts each
	 */
private int totalLengthReqd(int dtmNum) {
		
		int totalLengh=0;
		int headingCnt=0; 
		int row=10;
		int nColInc=0;
		
		incrMap=new LinkedHashMap();
		if(tab[0].getColumnCount()>14) {
			nColInc=8;
		}else {
			nColInc=10;
		}
		boolean nameColFlag=false;
		//System.out.println("dtm[dtmNum].getColumnCount() >> "+dtm[dtmNum].getColumnCount()+" dtmNum=="+dtmNum);
		for(int col=0;col<dtm[dtmNum].getColumnCount();col++){
			nameColFlag=false;
			if(nameCol!=null) {
				for(int k=0;k<nameCol.length;k++) {
					if(nameCol[k]==col) {
						nameColFlag=true;
						break;
					}
				}
			}
			if(length[col]==0) {
				headingCnt=headingCnt+3;
				incrMap.put(col,3);
			}else if(length[col]<=2&&length[col]!=0){
				if(nameColFlag) {
					headingCnt=headingCnt+nColInc;
					incrMap.put(col,nColInc);
				}else {
					if(dtm[dtmNum].getColumnCount()>15) {
						headingCnt=headingCnt+2;
						incrMap.put(col,2);
					}
					else {
						headingCnt=headingCnt+3;
						incrMap.put(col,3);
					}
				}
			}else if(length[col]>=3&&length[col]<=6) {
				if(nameColFlag) {
					headingCnt=headingCnt+nColInc;
					incrMap.put(col,nColInc);
				}else {
					headingCnt=headingCnt+3;
					incrMap.put(col,3);
				}
				
			}else if(length[col]>6&&length[col]<=10) {
				if(nameColFlag) {
					headingCnt=headingCnt+nColInc;
					incrMap.put(col,nColInc);
				}else {
					headingCnt=headingCnt+4;
					incrMap.put(col,4);
				}
				
			}else if(length[col]>10&&length[col]<20) {
				if(nameColFlag) {
					headingCnt=headingCnt+nColInc;
					incrMap.put(col,nColInc);
				}else {
					headingCnt=headingCnt+6;
					incrMap.put(col,6);
				}
				
			}else if(length[col]>20) {
				if(nameColFlag) {
					headingCnt=headingCnt+nColInc;
					incrMap.put(col,nColInc);
				}else {
					headingCnt=headingCnt+7;
					incrMap.put(col,7);
				}
				
			}else
				incrMap.put(col,0);
			adjustment_hash.put(col,headingCnt);
		}
		int totalSize=this.getPageWidth_inch()*12*6;
		int calSize=12*headingCnt;
		System.out.println("calSize >>> "+calSize+"  total >> "+totalSize+" headingCnt >> "+headingCnt);
		headingCnt=headingCnt-nColInc;
		
		int oldInc=nColInc;
		if((totalSize-20)<calSize) {
			System.out.println("this.getPageWidth_inch() >> "+this.getPageWidth_inch()); 
			if(this.getPageWidth_inch()>=15) {
				Hashtable adjustment_hash_temp=	adjustment_hash;
				while(calSize>(totalSize-20)) {
					if(nameCol!=null) {
						adjustment_hash_temp.put(nameCol[0],((Integer)adjustment_hash_temp.get(nameCol[0]-1)+nColInc));
						for(int col=nameCol[0]+1;col<dtm[0].getColumnCount();col++){
							nameColFlag=false;
							for(int k=0;k<nameCol.length;k++) {
								if(nameCol[k]==col) {
									nameColFlag=true;
									adjustment_hash_temp.put(col,((Integer)adjustment_hash_temp.get(col-1)+nColInc));
									break;
								}
							}
							if(!nameColFlag) {
								adjustment_hash_temp.put(col,((Integer)adjustment_hash_temp.get(col-1)+(Integer)incrMap.get(col)));
							}	
						}
					}
				calSize=(Integer)adjustment_hash_temp.get(adjustment_hash_temp.size()-1)*12;
				System.out.println("totalSize >> "+totalSize+" calSize >> "+calSize+" nColInc >> "+nColInc +" ");
				if(calSize<=(totalSize-20))
					break;
				nColInc--;
				}
			}else {
				nColInc++;
			}
			if(nameCol!=null) {
					adjustment_hash.put(nameCol[0],((Integer)adjustment_hash.get(nameCol[0]-1)+nColInc));
					for(int col=nameCol[0]+1;col<dtm[0].getColumnCount();col++){
						nameColFlag=false;
						for(int k=0;k<nameCol.length;k++) {
							if(nameCol[k]==col) {
								nameColFlag=true;
								adjustment_hash.put(col,((Integer)adjustment_hash.get(col-1)+nColInc));
								break;
								
							}
						}
						if(!nameColFlag) {
							//System.out.println("incrMap >> "+incrMap+"  ");
							adjustment_hash.put(col,((Integer)adjustment_hash.get(col-1)+(Integer)incrMap.get(col)));
						}	
						//System.out.println("col >>"+col+"-->"+((Integer)adjustment_hash.get(col-1)+"-oldInc"+oldInc+" -"+nColInc));
						
					}
			}
			return nColInc;
		}else {
			if(dtmNum!=1) {
			int temp_count=0;
			Hashtable adjustment_hash_temp=	adjustment_hash;
			if(dtm[0].getColumnCount()>14&&this.getPageWidth_inch()==15||dtm[0].getColumnCount()<14&&this.getPageWidth_inch()==10) {
				while(calSize<(totalSize-40)) {
					nColInc++;
					if(nameCol!=null) {
						adjustment_hash_temp.put(nameCol[0],((Integer)adjustment_hash_temp.get(nameCol[0]-1)+nColInc));
						for(int col=nameCol[0]+1;col<dtm[0].getColumnCount();col++){
							nameColFlag=false;
							for(int k=0;k<nameCol.length;k++) {
								if(nameCol[k]==col) {
									nameColFlag=true;
									adjustment_hash_temp.put(col,((Integer)adjustment_hash_temp.get(col-1)+nColInc));
									break;
								}
							}
							if(!nameColFlag) {
							//System.out.println("incrMap >> "+incrMap+"  ");
								adjustment_hash_temp.put(col,((Integer)adjustment_hash_temp.get(col-1)+(Integer)incrMap.get(col)));
							}	
						}
					}
					calSize=(Integer)adjustment_hash_temp.get(adjustment_hash_temp.size()-1)*12;
					System.out.println("calSize >->"+calSize+" total >->"+totalSize);
					temp_count++;
					if(calSize>=(totalSize-40)) {
						--nColInc;
						break;
					}
					if(dtm[0].getColumnCount()<7) {
						if(temp_count>4)
							break;
					}
					else 
						if(temp_count >3) {
							break;
						}
					
				}
			}else {
				nColInc=nColInc+4;
			}
			if(nameCol!=null) {
				System.out.println("nColInc-->"+nColInc);
				adjustment_hash.put(nameCol[0],((Integer)adjustment_hash.get(nameCol[0]-1)+nColInc));
				for(int col=nameCol[0]+1;col<dtm[0].getColumnCount();col++){
					nameColFlag=false;
					for(int k=0;k<nameCol.length;k++) {
						if(nameCol[k]==col) {
							nameColFlag=true;
							if(length[col]!=0) 
								adjustment_hash.put(col,((Integer)adjustment_hash.get(col-1)+nColInc));
							else
								adjustment_hash.put(col,((Integer)adjustment_hash.get(col-1)+3));	
							System.out.println("adjustment_hash.get(col-1)-->"+(Integer)adjustment_hash.get(col-1));
							break;
						}
					}
					if(!nameColFlag) {
						adjustment_hash.put(col,((Integer)adjustment_hash.get(col-1)+(Integer)incrMap.get(col)));
					}	
				}
			}
		}
		System.out.println("nColInc >> "+nColInc);
			return nColInc;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 * main method. it gets called when we hit on the print button present in the print dialog box
	 */
	public int print(Graphics gg, PageFormat p, int index) throws PrinterException {
		g=(Graphics2D)gg;
		paint(gg);
		gg.translate((int)(p.getImageableX()), (int)(p.getImageableY()));
		return PAGE_EXISTS;
	}
	private String checkDate(String date) throws DateFormatException
	{
	    try {
		String fdate="";
		StringTokenizer stfdate=new StringTokenizer(date,"/");
		String dd=stfdate.nextToken();
		String mm=stfdate.nextToken();
		String yyyy=stfdate.nextToken();
		int mmi=Integer.parseInt(mm);

		if(Integer.parseInt(dd)==0 || dd.length()>2 || (Integer.parseInt(dd))>((mmi==2?((Integer.parseInt(yyyy)%4)==0?29:28):((mmi==1 ||mmi==3||mmi==5 ||mmi==7 ||mmi==8 ||mmi==10||mmi==12)?31:30))))
		{
			throw new DateFormatException("Invalid Date Format");
		}
		else if(mm.length()>2 || Integer.parseInt(mm)>12 || Integer.parseInt(mm)==0)
		{
			throw new DateFormatException("Invalid Month Format");
		}
		else if((yyyy.length()==1 || yyyy.length()==3 ||yyyy.length()>=5 || Integer.parseInt(yyyy)==0) && !(yyyy.equalsIgnoreCase("10000")))
		{
			throw new DateFormatException("Invalid Year Format");
		}		
		else
		{
			if(yyyy.length()==2)
			{
			  Calendar c=Calendar.getInstance();
			  int y=c.get(Calendar.YEAR);
			  y=y/100;
			  yyyy=String.valueOf(y)+yyyy;

			}
			if(dd.length()==1)
				dd="0"+dd;

			if(mm.length()==1)
				mm="0"+mm;

			fdate=dd+"/"+mm+"/"+yyyy.substring(2,4);
			return fdate;
		}	
	    }catch(NoSuchElementException e) {
	       throw new DateFormatException("Date Problem"); 
	    }catch(NumberFormatException nf ) {
	    	throw new DateFormatException("Invalid Date Format"); 
	    }
	}
	public String[] getLabels(String modcode){
		String labels[]=null;
		if(modcode.equals("1001000")){
			labels=new String[7];
			labels[0]="A/C Type   		 ";
			labels[1]="A/C No.    		 ";
			//labels[2]="Name       		 ";
			labels[2]="Category   		 ";
			labels[3]="Loan Availed 	 ";
			labels[4]="Branch			 ";
			labels[5]="Dividend Paid Upto";
			labels[6]="A/C Status 		 ";
			
		}
		else if(modcode.equals("1002000")){
			labels=new String[8];
			labels[0]	="A/C Type   		 ";
			labels[1]	="Mobile No.";
			labels[2]	="A/C No.			 ";
			labels[3]	="Phone No.";
			labels[4]	="Name       		 ";
			labels[5]	="Fax No.       	 ";
			labels[6]	="Address       	 ";
			labels[7]	="              	 ";
		
		}
		else if(modcode.equals("1003000")){
			labels=new String[14];
			labels[0]	="A/C Type   		 ";
			labels[1]	="A/C No.    		 ";
			labels[2]	="Name       		 ";
			labels[3]	="Period       		 ";
			labels[4]	="Deposit Date		 ";
			labels[5]	="Maturity Date 	 ";
			labels[6]	="Deposit Amount	 ";
			labels[7]	="Maturity Amount 	 ";
			labels[8]	="Interest Rate		 ";
			labels[9]	="Interest Frequency ";
			labels[10]	="Interest Mode	     ";
			labels[11]	="Interest Upto	     ";
			labels[12]	="Receipt No.	     ";
			labels[13]	="Share lf.No	     ";
			
			
		}
		else if(modcode.equals("1010000")){
			labels=new String[12];
			labels[0]	="A/C Type   		 ";
			labels[1]	="A/C No.    		 ";
			labels[2]	="Name       		 ";
			labels[3]	="SB A/C No.		 ";
			labels[4]	="Purpose		 	 ";
			labels[5]	="Share No.			 ";
			labels[6]	="Interest Rate		 ";
			labels[7]	="Interest Paid Upto ";
			labels[8]	="Sanction Date	 	 ";
			labels[9]	="Sanction Amount	 ";
			labels[10]	="No. of Installments";
			labels[11]	="Installed Amount	 ";
			
			
		}
		else if(modcode.equals("1008000")){
			labels=new String[14];
			labels[0]	="A/C Type   		 ";
			labels[1]	="A/C No.    		 ";
			labels[2]	="Name       		 ";
			labels[3]	="Period In Days	 ";
			labels[4]	="Deposit Date		 ";
			labels[5]	="Maturity Date 	 ";
			labels[6]	="Deposit Amount	 ";
			labels[7]	="Maturity Amount 	 ";
			labels[8]	="Sanction Date	 	 ";
			labels[9]	="Sanction Amount	 ";
			labels[10]	="Deposit A/C Type   ";
			labels[11]	="Deposit A/C No.	 ";
			labels[12]	="Interest Rate      ";
			labels[13]	="Loan Interest Rate ";
			
			
		}
		else if(modcode.equals("1006000")){
			labels=new String[9];
			labels[0]="A/C Type   		 ";
			labels[1]="A/C No.    		 ";
			labels[2]="Name       		 ";
			labels[3]="Open Date   		 ";
			labels[4]="AgentNo/Name 	 ";
			labels[5]="Pay Mode			 ";
			labels[6]="Intr Upto		 ";
			labels[7]="Joint Holders	 ";
			labels[8]="Loan Availed	 ";
			
		}
		else if(modcode.equals("1009000")){
			labels=new String[7];
			labels[0]="A/C Type   		 ";
			labels[1]="A/C No.    		 ";
			labels[2]="Name       		 ";
			labels[3]="Locker Type		 ";
			labels[4]="Locker No. 	 	 ";
			labels[5]="From Date		 ";
			labels[6]="To Date   		 ";
			
			
		}
		
		return labels;
	}
	public int printAccountDetails(Graphics g, String modcode,String label_values[]){
		this.label_values=label_values;
		labels=this.getLabels(modcode);
		cnt=1;
		this.row=6;
		try{
			for(int i=0;i<labels.length-1;){
				g.drawString(this.labels[i], 12*(cnt), 12*row);
				displayData(g, cnt+8, row, ": "+this.label_values[i], 100, 100, cnt+8, 100);
			//	g.drawString(": "+this.label_values[i], 12*(cnt+8), 12*row);
				//g.drawString(this.label_values[i], 12*(cnt+12)+10, 12*row);
				g.drawString(this.labels[i+1], 12*(cnt+28), 12*row);
				//g.drawString(": "+this.label_values[i+1], 12*(cnt+28+8), 12*row);
				displayData(g,(cnt+28+8), row, ": "+this.label_values[i+1], 100, 100, cnt+28+8, 100);
				//g.drawString(this.label_values[i+1], 12*(cnt+30+12)+10, 12*row);
				i=i+2;
				this.row++;
			}
		}catch(Exception e){e.printStackTrace();}
		return row;
		
	}
	public int getHeadColNum() {
		return headColNum;
	}
	public void setHeadColNum(int headColNum) {
		this.headColNum = headColNum;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}
	public int getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}
	public int getPageHeight_inch() {
		return pageHeight_inch;
	}
	public void setPageHeight_inch(int pageHeight_inch) {
		this.pageHeight_inch = pageHeight_inch;
	}
	public int getPageWidth_inch() {
		return pageWidth_inch;
	}
	public void setPageWidth_inch(int pageWidth_inch) {
		this.pageWidth_inch = pageWidth_inch;
	}
	public int getMaxIncrement() {
		return maxIncrement;
	}
	public void setMaxIncrement(int maxIncrement) {
		this.maxIncrement = maxIncrement;
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

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getLastRowPrinted() {
		return lastRowPrinted;
	}

	public void setLastRowPrinted(int lastRowPrinted) {
		this.lastRowPrinted = lastRowPrinted;
	}

	public int getDtmNumber() {
		return dtmNumber;
	}

	public void setDtmNumber(int dtmNumber) {
		this.dtmNumber = dtmNumber;
	}

}

