/*
 * Created on May 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.general;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * @author Swapna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileObject implements Serializable 
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String columns[],head,filename,end_str,pageend_str,pagestart_str;
	int col_size[],no_of_chars,align[],no_of_lines,no_of_cols_page,cols_repeat[],filetype,colspan[],bold_array[],pagetotal[],width[];
	int boldrow[][],bolcol[][],sub_align[];
	FileOutputStream fout=null;
	PrintStream ps1=null;
	JTable subtable;
	DefaultTableModel dtm_sub;
	String sub_caption;

	
	
	public String[] getCols(){return this.columns;}
	public void setCols(String columns[]){this.columns=columns;}
	
	public String getHead(){return this.head;}
	public void setHead(String head){this.head=head;}
	
	public int[] getColSize(){return this.col_size;}
	public void setColSize(int col_size[]){this.col_size=col_size;}
	
	public int getNumChars(){return this.no_of_chars;}
	public void setNumChars(int no_of_chars){this.no_of_chars=no_of_chars;}
	
	public FileOutputStream getFileStream(){return this.fout;}
	public void setFileStream(FileOutputStream fout){this.fout=fout;}
	
	public int[] getAlign(){return align;}
	public void setAlign(int align[]){this.align=align;}
	
	public int getNumLines(){return no_of_lines;}
	public void setNumLines(int no_of_lines){this.no_of_lines=no_of_lines;}
	
	public int getNumColsPerPage(){return no_of_cols_page;}
	public void  setNumColsPerPage(int no_of_cols_page){this.no_of_cols_page=no_of_cols_page;}
	
	public int[] getColsRepeat(){return cols_repeat;}
	public void setColsRepeat(int []cols_repeat){this.cols_repeat=cols_repeat;}
	
	public int getFileType(){return filetype;}
	public void setFileType(int filetype){this.filetype=filetype;}
	
	public int[] getColSpan(){return colspan;}
	public void setColSpan(int []colspan){this.colspan=colspan;}
	
	public int[] getBoldArray(){return this.bold_array;}
	public void setBoldArray(int bold_array[]){this.bold_array=bold_array;}
	
	public String getTmpFileName(){return this.filename;}
	public void setTmpFileName(String filename){this.filename =filename;}
	
	public int[] getPageTotal(){return this.pagetotal;}
	public void setPageTotal(int[] pagetotal ){this.pagetotal=pagetotal;}
	
	public int[][] getBoldRows(){return this.boldrow;}
	public void setBoldRows(int[][] boldrow){this.boldrow=boldrow;}
	
	public int[][] getBoldCols(){return this.bolcol;}
	public void setBoldCols(int[][] boldcol){this.bolcol=boldcol;} 
	
	public int[] getWidth(){return this.width;}
	public void setWidth(int [] width){this.width=width;}
	
	public String  getStringAtEnd(){return this.end_str;}
	public void setStringAtEnd(String end_str){this.end_str=end_str ;}
	
	public String  getStringAtPageEnd(){return this.pageend_str;}
	public void setStringAtPageEnd(String pageend_str){this.pageend_str=pageend_str ;}
	
	public String  getStringAtPageStart(){return this.pagestart_str;}
	public void setStringAtPageStart(String pagestart_str){this.pagestart_str=pagestart_str;}
	public DefaultTableModel getDtm_sub() {
		return dtm_sub;
	}
	public void setDtm_sub(DefaultTableModel dtm_sub) {
		this.dtm_sub = dtm_sub;
	}
	public String getSub_caption() {
		return sub_caption;
	}
	public void setSub_caption(String sub_caption) {
		this.sub_caption = sub_caption;
	}
	public JTable getSubtable() {
		return subtable;
	}
	public void setSubtable(JTable subtable) {
		this.subtable = subtable;
	}
	public int[] getSub_align() {
		return sub_align;
	}
	public void setSub_align(int[] sub_align) {
		this.sub_align = sub_align;
	}
	
	
	
	

}
