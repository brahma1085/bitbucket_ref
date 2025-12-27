package com.scb.td.forms;

import java.util.LinkedHashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.struts.action.ActionForm;

public class PrintAllReportObject extends ActionForm{
	private int totalColNum[],nameCol[];
	private int pageWidth,pageHeight,pageWidth_inch,pageHeight_inch;
	private int fontSize=10,initCharSpace=7;
	private LinkedHashMap map=new LinkedHashMap();
	private LinkedHashMap subHeadCol,incrMap;
	private LinkedHashMap<Integer,Double> totalColumns;
	private JTable tab[];
	private DefaultTableModel dtm[];
	private DefaultTableModel dtm_grand_total;
	private String date;
	private String acType;
	private String[] heading;
	private String[] labelValues;
	private String[] noteString;
	private boolean doubleColExpReqd=false;
	private boolean colTotIsReqd=true,doYouNeedToExpandNameColumn=true;
	private boolean zeroColumnExpansion=true,grandTotalReqd=false,pageDialogReqd=true;
	public int[] getTotalColNum() {
		return totalColNum;
	}
	public void setTotalColNum(int[] totalColNum) {
		this.totalColNum = totalColNum;
	}
	public int[] getNameCol() {
		return nameCol;
	}
	public void setNameCol(int[] nameCol) {
		this.nameCol = nameCol;
	}
	public int getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}
	public int getPageWidth_inch() {
		return pageWidth_inch;
	}
	public void setPageWidth_inch(int pageWidth_inch) {
		this.pageWidth_inch = pageWidth_inch;
	}
	public int getPageHeight_inch() {
		return pageHeight_inch;
	}
	public void setPageHeight_inch(int pageHeight_inch) {
		this.pageHeight_inch = pageHeight_inch;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getInitCharSpace() {
		return initCharSpace;
	}
	public void setInitCharSpace(int initCharSpace) {
		this.initCharSpace = initCharSpace;
	}
	public LinkedHashMap getMap() {
		return map;
	}
	public void setMap(LinkedHashMap map) {
		this.map = map;
	}
	public LinkedHashMap getSubHeadCol() {
		return subHeadCol;
	}
	public void setSubHeadCol(LinkedHashMap subHeadCol) {
		this.subHeadCol = subHeadCol;
	}
	public LinkedHashMap getIncrMap() {
		return incrMap;
	}
	public void setIncrMap(LinkedHashMap incrMap) {
		this.incrMap = incrMap;
	}
	public LinkedHashMap<Integer, Double> getTotalColumns() {
		return totalColumns;
	}
	public void setTotalColumns(LinkedHashMap<Integer, Double> totalColumns) {
		this.totalColumns = totalColumns;
	}
	public JTable[] getTab() {
		return tab;
	}
	public void setTab(JTable[] tab) {
		this.tab = tab;
	}
	public DefaultTableModel[] getDtm() {
		return dtm;
	}
	public void setDtm(DefaultTableModel[] dtm) {
		this.dtm = dtm;
	}
	public DefaultTableModel getDtm_grand_total() {
		return dtm_grand_total;
	}
	public void setDtm_grand_total(DefaultTableModel dtm_grand_total) {
		this.dtm_grand_total = dtm_grand_total;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String[] getHeading() {
		return heading;
	}
	public void setHeading(String[] heading) {
		this.heading = heading;
	}
	public String[] getLabelValues() {
		return labelValues;
	}
	public void setLabelValues(String[] labelValues) {
		this.labelValues = labelValues;
	}
	public String[] getNoteString() {
		return noteString;
	}
	public void setNoteString(String[] noteString) {
		this.noteString = noteString;
	}
	public boolean isDoubleColExpReqd() {
		return doubleColExpReqd;
	}
	public void setDoubleColExpReqd(boolean doubleColExpReqd) {
		this.doubleColExpReqd = doubleColExpReqd;
	}
	public boolean isColTotIsReqd() {
		return colTotIsReqd;
	}
	public void setColTotIsReqd(boolean colTotIsReqd) {
		this.colTotIsReqd = colTotIsReqd;
	}
	public boolean isDoYouNeedToExpandNameColumn() {
		return doYouNeedToExpandNameColumn;
	}
	public void setDoYouNeedToExpandNameColumn(boolean doYouNeedToExpandNameColumn) {
		this.doYouNeedToExpandNameColumn = doYouNeedToExpandNameColumn;
	}
	public boolean isZeroColumnExpansion() {
		return zeroColumnExpansion;
	}
	public void setZeroColumnExpansion(boolean zeroColumnExpansion) {
		this.zeroColumnExpansion = zeroColumnExpansion;
	}
	public boolean isGrandTotalReqd() {
		return grandTotalReqd;
	}
	public void setGrandTotalReqd(boolean grandTotalReqd) {
		this.grandTotalReqd = grandTotalReqd;
	}
	public boolean isPageDialogReqd() {
		return pageDialogReqd;
	}
	public void setPageDialogReqd(boolean pageDialogReqd) {
		this.pageDialogReqd = pageDialogReqd;
	}

}
