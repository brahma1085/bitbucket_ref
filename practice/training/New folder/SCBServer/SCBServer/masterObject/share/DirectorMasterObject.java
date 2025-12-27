package masterObject.share;

import java.io.Serializable;

/**
 * @author Karthi
 *
 */
public class DirectorMasterObject implements Serializable
{
 int rel_code,did,cid;
 String fdate,tdate,relation_type,dir_name;
 
 public void setFromDate(String fr_date){this.fdate=fr_date;}
 public String getFromDate(){return fdate;}
 
 public void setToDate(String to_date){this.tdate=to_date;}
 public String getToDate(){return tdate;}
 
 public void setDirectorId(int dir_id){this.did=dir_id;}
 public int getDirectorId(){return did;}
 
 public void setDirectorCustomerId(int dir_cus_id){this.cid=dir_cus_id;}
 public int getDirectorCustomerId(){return cid;}
 
 public void setDirectorRelationType(String dir_rel_type){this.relation_type=dir_rel_type;}
 public String getDirectorRelationType(){return relation_type;}
 
 public void setDirectorRelationCode(int dir_rel_code){this.rel_code=dir_rel_code;}
 public int getDirectorRelationCode(){return rel_code;}
 
 public void setDirectorName(String name){this.dir_name=name;}
 public String getDirectorName(){return dir_name;}
 
 
}
