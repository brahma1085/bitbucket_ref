/*
 * Created on Apr 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.generalLedger;

/**
 * @author Riswan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MonthlyConsObject implements java.io.Serializable{

    
    int br_code,gl_code,yr_mth;
    String record_type,gl_type,de_tml,de_user,de_dt_tm,ve_tml,ve_user,ve_dt_tm;
    double csh_dr,cg_dr,trf_dr,csh_cr,cg_cr,trf_cr;
    String[] columns;
    
    /**
     * @return Returns the columns.
     */
    public String[] getColumns() {
        return columns;
    }
    /**
     * @param columns The columns to set.
     */
    public void setColumns(String[] columns) {
        this.columns = columns;
    }
    /**
     * @return Returns the br_code.
     */
    public int getBr_code() {
        return br_code;
    }
    /**
     * @param br_code The br_code to set.
     */
    public void setBr_code(int br_code) {
        this.br_code = br_code;
    }
    /**
     * @return Returns the cg_cr.
     */
    public double getCg_cr() {
        return cg_cr;
    }
    /**
     * @param cg_cr The cg_cr to set.
     */
    public void setCg_cr(double cg_cr) {
        this.cg_cr = cg_cr;
    }
    /**
     * @return Returns the cg_dr.
     */
    public double getCg_dr() {
        return cg_dr;
    }
    /**
     * @param cg_dr The cg_dr to set.
     */
    public void setCg_dr(double cg_dr) {
        this.cg_dr = cg_dr;
    }
    /**
     * @return Returns the csh_cr.
     */
    public double getCsh_cr() {
        return csh_cr;
    }
    /**
     * @param csh_cr The csh_cr to set.
     */
    public void setCsh_cr(double csh_cr) {
        this.csh_cr = csh_cr;
    }
    /**
     * @return Returns the csh_dr.
     */
    public double getCsh_dr() {
        return csh_dr;
    }
    /**
     * @param csh_dr The csh_dr to set.
     */
    public void setCsh_dr(double csh_dr) {
        this.csh_dr = csh_dr;
    }
    /**
     * @return Returns the de_dt_tm.
     */
    public String getDe_dt_tm() {
        return de_dt_tm;
    }
    /**
     * @param de_dt_tm The de_dt_tm to set.
     */
    public void setDe_dt_tm(String de_dt_tm) {
        this.de_dt_tm = de_dt_tm;
    }
    /**
     * @return Returns the de_tml.
     */
    public String getDe_tml() {
        return de_tml;
    }
    /**
     * @param de_tml The de_tml to set.
     */
    public void setDe_tml(String de_tml) {
        this.de_tml = de_tml;
    }
    /**
     * @return Returns the de_user.
     */
    public String getDe_user() {
        return de_user;
    }
    /**
     * @param de_user The de_user to set.
     */
    public void setDe_user(String de_user) {
        this.de_user = de_user;
    }
    /**
     * @return Returns the gl_code.
     */
    public int getGl_code() {
        return gl_code;
    }
    /**
     * @param gl_code The gl_code to set.
     */
    public void setGl_code(int gl_code) {
        this.gl_code = gl_code;
    }
    /**
     * @return Returns the gl_type.
     */
    public String getGl_type() {
        return gl_type;
    }
    /**
     * @param gl_type The gl_type to set.
     */
    public void setGl_type(String gl_type) {
        this.gl_type = gl_type;
    }
    /**
     * @return Returns the record_type.
     */
    public String getRecord_type() {
        return record_type;
    }
    /**
     * @param record_type The record_type to set.
     */
    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }
    /**
     * @return Returns the trf_cr.
     */
    public double getTrf_cr() {
        return trf_cr;
    }
    /**
     * @param trf_cr The trf_cr to set.
     */
    public void setTrf_cr(double trf_cr) {
        this.trf_cr = trf_cr;
    }
    /**
     * @return Returns the trf_dr.
     */
    public double getTrf_dr() {
        return trf_dr;
    }
    /**
     * @param trf_dr The trf_dr to set.
     */
    public void setTrf_dr(double trf_dr) {
        this.trf_dr = trf_dr;
    }
    /**
     * @return Returns the ve_dt_tm.
     */
    public String getVe_dt_tm() {
        return ve_dt_tm;
    }
    /**
     * @param ve_dt_tm The ve_dt_tm to set.
     */
    public void setVe_dt_tm(String ve_dt_tm) {
        this.ve_dt_tm = ve_dt_tm;
    }
    /**
     * @return Returns the ve_tml.
     */
    public String getVe_tml() {
        return ve_tml;
    }
    /**
     * @param ve_tml The ve_tml to set.
     */
    public void setVe_tml(String ve_tml) {
        this.ve_tml = ve_tml;
    }
    /**
     * @return Returns the ve_user.
     */
    public String getVe_user() {
        return ve_user;
    }
    /**
     * @param ve_user The ve_user to set.
     */
    public void setVe_user(String ve_user) {
        this.ve_user = ve_user;
    }
    /**
     * @return Returns the yr_mth.
     */
    public int getYr_mth() {
        return yr_mth;
    }
    /**
     * @param yr_mth The yr_mth to set.
     */
    public void setYr_mth(int yr_mth) {
        this.yr_mth = yr_mth;
    }
}
