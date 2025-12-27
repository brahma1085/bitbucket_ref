package com.ge.crd.ementoring.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ge.crd.ementoring.bean.HelperBean;
import com.ge.crd.ementoring.bean.RegisterMentee;
import com.ge.crd.ementoring.bean.Mentor.MentorBean;
import com.ge.crd.ementoring.formbean.mentee.EditInputprogressForm;
import com.ge.crd.ementoring.formbean.mentee.MenteeMentorLoadForm;
import com.ge.crd.ementoring.formbean.mentee.MenteeProfileFormBean;
import com.ge.crd.ementoring.formbean.mentee.MenteeProgressForm;
import com.ge.crd.ementoring.formbean.mentee.RegisteredMenteeForm;
import com.ge.crd.ementoring.util.MenteeQuery;
import com.ge.crd.ementoring.util.ValidateService;
import com.ge.crd.ementoring.util.Mentor.ValidationField;


public class DBInputProgress {
	
			DataSource dataSource = null;
		
		public DBInputProgress() {
			
		}
		
		public DBInputProgress(DataSource ds) {
	       this.dataSource = ds;
	    }
	
		public ArrayList menteeList() throws Exception {

			ArrayList menteeList = new ArrayList();
		    Connection dbConnection = null;
		    PreparedStatement dbPreparedstatement = null;
		    ResultSet dbResultset = null;
		    HelperBean oBean;
		    
			try {
				 dbConnection = dataSource.getConnection();
				 dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_LIST_QUERY);
				 dbResultset = dbPreparedstatement.executeQuery();
				 
	   	         while ( dbResultset.next() ) {
					 oBean = new HelperBean();
					 oBean.setFieldValue1(dbResultset.getString(1));
					 oBean.setFieldValue2(dbResultset.getString(2));
					 menteeList.add(oBean);
				 }
				 
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				try {
					 if( dbResultset != null ) {
					     dbResultset = null;
					     dbResultset.close();
					 }
					if ( dbPreparedstatement != null ) {
						 dbPreparedstatement = null;
						 dbPreparedstatement.close();
					}
					if ( dbConnection != null ) {
						 dbConnection = null;
						 dbConnection.close();
					}
				}
				catch (Exception e) {
				}
			}
			return menteeList;
	    }

		public ArrayList mentorList() throws Exception {
			
			ArrayList mentorList = new ArrayList();
			Connection dbConnection = null;
		    PreparedStatement dbPreparedstatement = null;
		    ResultSet dbResultset = null;
		    HelperBean oBean;
		   
			try {
				 dbConnection = dataSource.getConnection();
				 dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_LIST_QUERY);
				 dbResultset = dbPreparedstatement.executeQuery();
			     
				 while(dbResultset.next()) {
					 oBean = new HelperBean();
					 oBean.setFieldValue1(dbResultset.getString("SSOID"));
					 oBean.setFieldValue2(dbResultset.getString("GEHRFULLNAME"));
					 mentorList.add(oBean);
				 }
				 
			}catch(Exception ex) {
			    ex.printStackTrace();
			}finally {
				try {
					if(dbResultset != null) {
						dbResultset.close();
						dbResultset  = null;
					}
					if ( dbPreparedstatement != null ) {
						dbPreparedstatement.close();
						dbPreparedstatement = null;
					}
					if ( dbConnection != null ) {
						dbConnection.close();
						dbConnection = null;
					}
				}
				catch (Exception e) {
				}
			}
			return mentorList;
		}
		
		public ArrayList relationMenteeMentorList(String ssoId) throws Exception {
	 		
			ArrayList mappingList = new ArrayList();
			Connection dbConnection = null;
		    PreparedStatement dbPreparedstatement = null;
		    ResultSet dbResultset = null;
		    HelperBean oBean;
			
			try {
				dbConnection = dataSource.getConnection();
				dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_MENTOR_RELATION_QUERY);
				dbResultset = dbPreparedstatement.executeQuery();
			    
				while(dbResultset.next())
				{
					oBean = new HelperBean();
					oBean.setFieldValue1(dbResultset.getString(1));
					oBean.setFieldValue2(dbResultset.getString(2));
				    mappingList.add(oBean);
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				try {
					if(dbResultset !=null) {
					   dbResultset.close();
					   dbResultset= null;
					}
					if ( dbPreparedstatement != null ) {
						dbPreparedstatement.close();
						dbPreparedstatement = null;
					}
					if ( dbConnection != null ) {
						dbConnection.close();
						dbConnection = null;
					}
				}catch (Exception e) {
				e.printStackTrace();
				}
			}
			 return mappingList;
		}
		
		public boolean saveInputProgressDetails( String menteeId, String mentorId, String loginUserId, String loginUserType, MenteeMentorLoadForm objForm) 
		       throws Exception
		{
			boolean queryStatus = false;
			Connection dbConnection = null;
			PreparedStatement dbPreparedStatement = null;
			int mappingId = mappingId(menteeId, mentorId);
			
			try {
			     dbConnection = dataSource.getConnection();
			     dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_INPUT_PROGRESS_SAVEDATA_QUERY);
				 dbPreparedStatement.setInt(1,mappingId);
				 dbPreparedStatement.setString(2,loginUserType);
				 dbPreparedStatement.setString(3,loginUserId);
				 dbPreparedStatement.setString(4, objForm.getProgressDate());
				 dbPreparedStatement.setString(5, objForm.getProgressNotes());
				 dbPreparedStatement.executeUpdate();
				 queryStatus = true;
			
			}catch(Exception ex) {
				queryStatus = false;
				ex.printStackTrace();
			}finally {
				try {
					if ( dbPreparedStatement != null ) {
						dbPreparedStatement.close();
						dbPreparedStatement = null;
					}
					if ( dbConnection != null ) {
						dbConnection.close();
						dbConnection = null;
					}
				}catch (Exception e) {
				  e.printStackTrace();
				}
			}
			return queryStatus;
		}
		
		
		public int mappingId(String menteeId, String mentorId) throws Exception {
			int mappingId = 0;
			Connection dbConnection = null;
			PreparedStatement dbPreparedStatement = null;
			ResultSet dbResultSet = null;
			
			try {
				dbConnection = dataSource.getConnection();
				dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_MENTOR_MAPPING_ID_QUERY);
				dbPreparedStatement.setString(1,menteeId);
				dbPreparedStatement.setString(2,mentorId);
			 	
				dbResultSet = dbPreparedStatement.executeQuery();
				
				if(dbResultSet.next()) {
					mappingId = Integer.parseInt(dbResultSet.getString("MENTOR_MENTEE_MAPPING_ID"));
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				try {
					 if(dbResultSet != null) {
						 dbResultSet.close();
						 dbResultSet = null;
					 }
					 if ( dbPreparedStatement != null ) {
						dbPreparedStatement.close();
						dbPreparedStatement = null;
					 }
					 if ( dbConnection != null ) {
						dbConnection.close();
						dbConnection = null;
					 }
				}catch (Exception e) {
				  e.printStackTrace();
				}
			}
			return mappingId;
		}
		
	public ArrayList checkMenteeHR(String ssoId) throws Exception {
		ArrayList list = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		HelperBean hBean = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEEHRMANAGERID_LIST_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			list = new ArrayList();
			
			if(dbResultSet.next()) {
				hBean = new HelperBean();
				hBean.setFieldValue1(dbResultSet.getString("SSOID"));
			    hBean.setFieldValue2(dbResultSet.getString("GEHRMANAGERID"));
			    list.add(hBean);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				 if(dbResultSet != null) {
					 dbResultSet.close();
					 dbResultSet = null;
				 }
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return list;
	}
	
	public ArrayList checkMentorHR(String ssoId) throws Exception {
		ArrayList list = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		HelperBean hBean = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTORHRMANAGERID_LIST_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			list = new ArrayList();
			
			if(dbResultSet.next()) {
				hBean = new HelperBean();
				hBean.setFieldValue1(dbResultSet.getString("SSOID"));
			    hBean.setFieldValue2(dbResultSet.getString("GEHRMANAGERID"));
			    list.add(hBean);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				 if(dbResultSet != null) {
					 dbResultSet.close();
					 dbResultSet = null;
				 }
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return list;
	}
	
	public boolean getInputProgressDetails( String ssoid,EditInputprogressForm objForm )
	    throws Exception
	{
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		HelperBean oBean = null;
		ArrayList list  = null;
		RegisterMentee objRegMentee = new RegisterMentee();
		MentorBean mentorBean   = new MentorBean();
		try {
			dbConnection = dataSource.getConnection();
	
			if(mentorBean.isLoggedUserAdmin(ssoid,dataSource) ) {
				dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_INPUT_PROGRESS_DATA_ADMIN_QUERY);
				System.out.println(MenteeQuery.APP_INPUT_PROGRESS_DATA_ADMIN_QUERY);
			}
			else {
				dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_INPUT_PROGRESS_DATA_QUERY);
				dbPreparedStatement.setString(1,ssoid);
			    dbPreparedStatement.setString(2,ssoid);
			    System.out.println(MenteeQuery.APP_INPUT_PROGRESS_DATA_QUERY);
			}
			
			dbResultSet = dbPreparedStatement.executeQuery();
			list = new ArrayList();
			while( dbResultSet.next() ) {
				oBean = new HelperBean();
				oBean.setMappingId(dbResultSet.getString(1));
				oBean.setInputInitiatedDate(dbResultSet.getString(2));
				oBean.setInputProgressDate(dbResultSet.getString(3));
				oBean.setInputProgressDetails(dbResultSet.getString(4));
				oBean.setInputInitiatedPerson(dbResultSet.getString(5));
				oBean.setMentorId(dbResultSet.getString(6));
				oBean.setMenteeId(dbResultSet.getString(7));
				oBean.setProgressId(dbResultSet.getString(8));
				oBean.setMentorName(getName(oBean.getMentorId()));
				oBean.setMenteeName(getName(oBean.getMenteeId()));
				if(mentorBean.isLoggedUserAdmin(ssoid,dataSource))
				if(ssoid.equals(oBean.getMenteeId()) || ssoid.equals(oBean.getMentorId()) || ssoid.equals(oBean.getInputInitiatedPerson())) {
				oBean.setFieldValue1("true");	
				}else oBean.setFieldValue1("false");	
				list.add(oBean);
			}
			
			objForm.setInputProgressRecord(list);
			queryStatus = true;
		}catch(Exception ex) {
			queryStatus = false;
			ex.printStackTrace();
		}finally {
			try {
				 if(dbResultSet != null) {
					 dbResultSet.close();
					 dbResultSet = null;
				 }
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return queryStatus;
		
	}
	
	public boolean getInputDetailsByProgressId( String mappingId, String progressId, EditInputprogressForm objForm )
    throws Exception
{
	boolean queryStatus = false;
	Connection dbConnection = null;
	PreparedStatement dbPreparedStatement = null;
	ResultSet dbResultSet = null;
	
	try {
		dbConnection = dataSource.getConnection();
		dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_INPUT_PROGRESS_DATA_BY_ID_DATE_QUERY);
		dbPreparedStatement.setInt(1,Integer.parseInt(mappingId));
		dbPreparedStatement.setInt(2,Integer.parseInt(progressId));
		dbResultSet = dbPreparedStatement.executeQuery();
		
		while( dbResultSet.next() ) {
			objForm.setMappingId(dbResultSet.getString(1));
			objForm.setInputDate(dbResultSet.getString(2));
			objForm.setProgressDate(dbResultSet.getString(3));
			objForm.setProgressNotes(dbResultSet.getString(4));
			objForm.setProgressId(dbResultSet.getString(5));
			objForm.setMentorName(getName(dbResultSet.getString(7)));
			objForm.setMenteeName(getName(dbResultSet.getString(8)));
		}
		queryStatus = true;
	}catch(Exception ex) {
		queryStatus = false;
		ex.printStackTrace();
	}finally {
		try {
			 if(dbResultSet != null) {
				 dbResultSet.close();
				 dbResultSet = null;
			 }
			 if ( dbPreparedStatement != null ) {
				dbPreparedStatement.close();
				dbPreparedStatement = null;
			 }
			 if ( dbConnection != null ) {
				dbConnection.close();
				dbConnection = null;
			 }
		}catch (Exception e) {
		  e.printStackTrace();
		}
	}
	return queryStatus;
	
}
	public boolean updateInputProgressDetails(String progressId,String ssoId,EditInputprogressForm objForm)throws Exception {
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_INPUT_PROGRESS_EDIT_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbPreparedStatement.setString(2,objForm.getProgressDate());
			dbPreparedStatement.setString(3,objForm.getProgressNotes());
			dbPreparedStatement.setInt(4,Integer.parseInt(progressId));
			dbPreparedStatement.executeUpdate();
			queryStatus=true;
		}catch(Exception ex){
			queryStatus = false;
			ex.printStackTrace();
		}finally {
			try {
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return queryStatus;
	}
	
	public boolean getRegisteredMenteeDetails(String ssoId, MenteeProfileFormBean objForm) throws Exception
	{
		boolean queryResult                   = false;
		ArrayList list                        = null;
		HelperBean oBean                      = null;
		Connection dbConnection               = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet                 = null;
		
		try {
			 dbConnection = dataSource.getConnection();
			 
			 StringBuffer mainQuery = new StringBuffer();
			 mainQuery.append("SELECT A.GEHRFULLNAME,A.GEBUSINESSUNIT,A.GEHRPOSITIONTITLE,A.GEHRDAILCOM,TO_CHAR(B.REGISTRATION_DATE,'DD-MON-YY'),A.SSOID FROM MENTOR_SPODA_EMPLOYEE A, MENTOR_REGISTERED_MENTEES B");
			 if( objForm.getUserAdminFlag().equals("true")) {
				 mainQuery.append(" WHERE A.SSOID=SECURE_DATA.DECRYPTDATA(B.MENTEE_SSOID)AND B.STATUS_ID=10");
			 if(objForm.getMenteeName() != null && ! objForm.getMenteeName().equals(""))
				 mainQuery.append("AND UPPER(A.GEHRFULLNAME) LIKE '%" + objForm.getMenteeName().toUpperCase() + "%' ");
			 if(objForm.getBusinessType() != null && !objForm.getBusinessType().equals("0") )
				 mainQuery.append(" AND UPPER(A.GEBUSINESSUNIT) LIKE '%" +objForm.getBusinessType().toUpperCase() + "%'");
			 if(objForm.getLocationType() != null && ! objForm.getLocationType().equals("0"))
				 mainQuery.append(" AND A.LOCATIONID ="+objForm.getLocationType());
			 else if(objForm.getLocationType().equals("0")) {
				 mainQuery.append(" AND A.LOCATIONID IN (");
				 mainQuery.append(" SELECT LOCATION_ID FROM MENTOR_ADMIN_MASTER WHERE ADMIN_ID=?)");
			 }	 
			 if(objForm.getBusinessType().equals("0")) mainQuery.append(" ORDER BY A.GEBUSINESSUNIT ");
		     }else {  
		         mainQuery.append(" WHERE A.LOCATIONID IN(SELECT LOCATIONID FROM MENTOR_SPODA_EMPLOYEE WHERE SSOID=?) AND A.SSOID=SECURE_DATA.DECRYPTDATA(B.MENTEE_SSOID) AND B.STATUS_ID=10");
		         if(objForm.getMenteeName() != null && ! objForm.getMenteeName().equals(""))
					 mainQuery.append("AND UPPER(A.GEHRFULLNAME) LIKE '%" + objForm.getMenteeName().toUpperCase() + "%' ");
			     mainQuery.append(" AND UPPER(A.GEBUSINESSUNIT) LIKE '%"+getBusinessName(ssoId).toString().toUpperCase()+"%' ");		 
		     }
			 System.out.println(mainQuery.toString());
			 
			 dbPreparedStatement = dbConnection.prepareStatement(mainQuery.toString());
			 if(objForm.getLocationType() == null || objForm.getLocationType().equals("0")) dbPreparedStatement.setString(1,ssoId);
			 dbResultSet = dbPreparedStatement.executeQuery();
			 list = new ArrayList();
			 while(dbResultSet.next())
			 {
			    oBean = new HelperBean();
			    oBean.setFieldValue1(dbResultSet.getString(1));
			    oBean.setFieldValue2(dbResultSet.getString(2));
			    oBean.setFieldValue3(dbResultSet.getString(3));
			    oBean.setFieldValue4(dbResultSet.getString(4));
			    oBean.setFieldValue5(dbResultSet.getString(5));
			    oBean.setMenteeId(dbResultSet.getString(6));
			    oBean.setMentorName(getMentorName(oBean.getMenteeId()));
			    list.add(oBean);
			 }
			
			objForm.setArrMenteeList(list);
			queryResult = true;
			
		}catch(Exception ex){
			queryResult = false;
			ex.printStackTrace();
		}finally {
			try {
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		
		return queryResult;
	}
	
	
	public boolean getRegisteredMenteeProgressDetails(String ssoId, MenteeProgressForm objForm) throws Exception
	{
		boolean queryResult                   = false;
		ArrayList list                        = null;
		StringBuffer mainQuery                = new StringBuffer();
		HelperBean oBean                      = null;
		Connection dbConnection               = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet                 = null;
		
		try {
			dbConnection  = dataSource.getConnection();
			
			mainQuery.append("SELECT TO_CHAR(A.PROGRESS_INPUT_DATE,'DD-MON-YY'), A.PROGRESS_NOTES, SECURE_DATA.DECRYPTDATA(B.MENTEE_SSOID), SECURE_DATA.DECRYPTDATA(B.MENTOR_SSOID),B.MENTOR_MENTEE_MAPPING_ID FROM MENTOR_RELATIONSHIP_PROGRESS A,MENTOR_MENTOR_MENTEE_MAPPING B,MENTOR_SPODA_EMPLOYEE C  WHERE  B.MENTOR_MENTEE_MAPPING_ID = A.MAPPING_ID  AND C.SSOID = SECURE_DATA.DECRYPTDATA(B.MENTEE_SSOID)");
			System.out.println("Admin Flag:"+objForm.getUserAdminFlag());
			if(objForm.getUserAdminFlag().equals("true")) 
		    mainQuery.append(" AND C.LOCATIONID IN (SELECT LOCATION_ID FROM MENTOR_ADMIN_MASTER WHERE ADMIN_ID=?)");
			else
			mainQuery.append(" AND C.GEBUSINESSUNIT	= (SELECT GEBUSINESSUNIT FROM MENTOR_SPODA_EMPLOYEE WHERE SSOID=?)");	
			
			dbPreparedStatement = dbConnection.prepareStatement(mainQuery.toString());
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			list = new ArrayList();
			System.out.println("Progress Query:"+mainQuery.toString());
			while(dbResultSet.next()) {
			  oBean  = new HelperBean();
			  oBean.setFieldValue1(dbResultSet.getString(1));
			  oBean.setFieldValue2(dbResultSet.getString(2));
			  oBean.setMenteeId(dbResultSet.getString(3));
			  oBean.setMentorId(dbResultSet.getString(4));
			  oBean.setMenteeName(getName(oBean.getMenteeId()));
			  oBean.setMentorName(getName(oBean.getMentorId()));
			  oBean.setMappingId(dbResultSet.getString(5));
			  list.add(oBean);
			}
			objForm.setInputProgressRecord(list);
			queryResult = true;
			
		}catch(Exception ex){
			queryResult = false;
			ex.printStackTrace();
		}finally {
			try {
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return queryResult;
	}
	
	public String getBusinessName(String ssoId) throws Exception
	{
		String nameValue = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_BUSINESSTYPE_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			
			while( dbResultSet.next() ) nameValue = dbResultSet.getString(1);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				 if(dbResultSet != null) {
					 dbResultSet.close();
					 dbResultSet = null;
				 }
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return nameValue;
	}
	
	
	public String getName(String ssoId) throws Exception {
		String nameValue = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		
		try {
			
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_NAME_OF_THE_EMPLOYEE_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			
			while( dbResultSet.next() ) nameValue = dbResultSet.getString("GEHRFULLNAME");
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				 if(dbResultSet != null) {
					 dbResultSet.close();
					 dbResultSet = null;
				 }
				 if ( dbPreparedStatement != null ) {
					dbPreparedStatement.close();
					dbPreparedStatement = null;
				 }
				 if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
				 }
			}catch (Exception e) {
			  e.printStackTrace();
			}
		}
		return nameValue;
		
	}
	
	public String getMentorName(String menteeId) throws Exception
	{
		String mentorName = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		
		try{
			dbConnection = dataSource.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_NAME_OF_THE_MENTOR_QUERY);
			dbPreparedStatement.setString(1,menteeId);
			dbResultSet = dbPreparedStatement.executeQuery();
			
			if( dbResultSet.next() ) mentorName = getName(dbResultSet.getString(1));
			else
				mentorName = "";
			
	
	    }catch(Exception ex) {
		ex.printStackTrace();
	    }finally {
		try {
			 if(dbResultSet != null) {
				 dbResultSet.close();
				 dbResultSet = null;
			 }
			 if ( dbPreparedStatement != null ) {
				dbPreparedStatement.close();
				dbPreparedStatement = null;
			 }
			 if ( dbConnection != null ) {
				dbConnection.close();
				dbConnection = null;
			 }
		}catch (Exception e) {
		  e.printStackTrace();
		}
	   }
		return mentorName;
		
	}
	public ActionErrors validateInputProgressFormFields(EditInputprogressForm objForm)
	{
		ActionErrors errors = new ActionErrors();
		
	    if ( ValidateService.isEmpty(objForm.getProgressNotes()) ) {	    		
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes is mandatory."));	            
	    } else if ( ! ValidationField.isValid(objForm.getProgressNotes()) ) {
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes  field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } else if(objForm.getProgressNotes().length() > 1000) {
	    	   errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes should not exceed 1000 characters."));
	    }
	    
	    if ( ValidateService.isEmpty(objForm.getProgressDate()) ) {	    		
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Input Progress Date is mandatory."));	            
	     }
	    return errors;
	}    
 
	public ActionErrors validateInputProgressFormFields(MenteeMentorLoadForm objForm)
	{
		ActionErrors errors = new ActionErrors();
		
	    if ( ValidateService.isEmpty(objForm.getProgressNotes()) ) {	    		
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes is mandatory."));	            
	    } else if ( ! ValidationField.isValid(objForm.getProgressNotes()) ) {
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes  field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } else if(objForm.getProgressNotes().length() > 1000) {
	    	   errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Progress Notes should not exceed 1000 characters."));
	    }
	    
	    if ( ValidateService.isEmpty(objForm.getProgressDate()) ) {	    		
	           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Input Progress Date is mandatory."));	            
	     }
	    return errors;
	}
}
  
