package com.ge.crd.ementoring.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.ge.crd.ementoring.bean.HelperBean;
import com.ge.crd.ementoring.bean.MenteeDTO;
import com.ge.crd.ementoring.bean.MenteeRegDTO;
import com.ge.crd.ementoring.bean.Mentor.MentorBean;
import com.ge.crd.ementoring.formbean.mentee.MenteeEditForm;
import com.ge.crd.ementoring.formbean.mentee.MenteeFindingMentorForm;
import com.ge.crd.ementoring.formbean.mentee.MenteeProfileFormBean;
import com.ge.crd.ementoring.formbean.mentee.MenteeRegistrationForm;
import com.ge.crd.ementoring.formbean.mentee.MentorProfileForm;
import com.ge.crd.ementoring.formbean.mentor.NominateMentorsFormBean;
import com.ge.crd.ementoring.util.MenteeQuery;
import com.ge.crd.ementoring.util.ValidateService;
import com.ge.crd.ementoring.util.Mentor.ValidationField;
import com.ge.crd.ementoring.util.Mentor.MonthList;
public class DBMenteeForm {

	DataSource dataSource = null;
	
	public DBMenteeForm() {
		
	}
	
	public DBMenteeForm(DataSource ds) {
       this.dataSource = ds;
    }
	
	
	public ArrayList getLocationList(String ssoId) throws Exception {
		  ArrayList locationList = null;
		  HelperBean oBean      = null; 	  
		  Connection dbConnection = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		
		  try {
			  locationList = new ArrayList();
			  dbConnection = dataSource.getConnection();
			  dbPreparedStatement = dbConnection.prepareStatement(" SELECT LOCATION_ID,LOCATION_NAME FROM MENTOR_LOCATION_MASTER WHERE LOCATION_ID IN (SELECT LOCATION_ID FROM MENTOR_ADMIN_MASTER WHERE STATUS='ACTIVE' AND ADMIN_ID=?) ");			
			  dbPreparedStatement.setString(1,ssoId);
			  dbResultSet = dbPreparedStatement.executeQuery();
			  while (dbResultSet.next()) {
	                oBean = new HelperBean();
	                oBean.setFieldValue1(dbResultSet.getString(1));
	                oBean.setFieldValue2(dbResultSet.getString(2));
	                locationList.add(oBean);
			  }
	           
		  }catch(Exception ex) {
		         ex.printStackTrace();
		  }finally {
		      try {
			       if (dbResultSet != null) {
			       dbResultSet.close();
			       }
			       if (dbPreparedStatement != null) {
			       dbPreparedStatement.close();
			       }
			       if (dbConnection != null) {
			       dbConnection.close();
			       }
	          }catch(SQLException sqlExp) {
	        	  throw sqlExp;
	          }
	      }
		  return locationList;
		}
	
	public ArrayList getBusinessType(int locId) throws Exception {
		  ArrayList businessType = new ArrayList();
	      HelperBean oBean      = null; 	  
		  Connection dbConnection = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  
		  try {
			  dbConnection = dataSource.getConnection();
			  dbPreparedStatement = dbConnection.prepareStatement(" SELECT DISTINCT GEBUSINESSUNIT FROM MENTOR_SPODA_EMPLOYEE WHERE LOCATIONID= ? ORDER BY GEBUSINESSUNIT ");			
			  dbPreparedStatement.setInt(1,locId);
			  dbResultSet = dbPreparedStatement.executeQuery();
			  while (dbResultSet.next()) {
	                oBean = new HelperBean();
	                oBean.setFieldValue1(dbResultSet.getString(1));
	                businessType.add(oBean);
			  }
	           
		  }catch(Exception ex) {
		         ex.printStackTrace();
		  }finally {
		      try {
			       if (dbResultSet != null) {
			       dbResultSet.close();
			       }
			       if (dbPreparedStatement != null) {
			       dbPreparedStatement.close();
			       }
			       if (dbConnection != null) {
			       dbConnection.close();
			       }
	          }catch(SQLException sqlExp) {
	        	  throw sqlExp;
	          }
	      }
		  return businessType;
		}

	public boolean getMenteeInformation(String ssoId, MenteeEditForm objForm) throws Exception{
		
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedstatement = null;
		ResultSet dbResultSet = null;
	
		try {
			
			dbConnection = dataSource.getConnection();
			dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_EDIT_QUERY);			
			dbPreparedstatement.setString(1, ssoId);
			dbResultSet = dbPreparedstatement.executeQuery();
			if ( dbResultSet.next() ) {
		         
				objForm.setMenteeBand(dbResultSet.getString("BAND_DESCRIPTION")); 
				objForm.setMenteeElement(dbResultSet.getString("ELEMENTS_DESIRED_IN_MEN_REL"));
				objForm.setMenteeInterests(dbResultSet.getString("AREA_OF_INTEREST"));
				objForm.setMenteeMeetingCount(dbResultSet.getString("DESIRED_FRE_OF_MEET_WITH_MENT"));
				objForm.setMenteeDetails(dbResultSet.getString("PERSONAL_DETAILS"));
				queryStatus = true;
				
			}			
			
		  } catch (Exception ex) {
			System.out.println("Error while getting Mentee Information");
			queryStatus = false;
			ex.printStackTrace();
		}
		finally {
			try  {
				if (dbResultSet != null ) {
					dbResultSet.close();
					dbResultSet = null;
				}
				if ( dbPreparedstatement != null ) {
					 dbPreparedstatement.close(); 
					 dbPreparedstatement = null;
				}
				if ( dbConnection != null ) {
					 dbConnection.close();
					 dbConnection = null;
				}
			} catch (Exception sqlEx) {
				
			}
		}
		
		return queryStatus;
	}
	
public boolean getMenteePositionsList(String ssoId, MenteeEditForm objForm) throws Exception {
		
		boolean queryStatus                   = false;
		ArrayList list                        = null;
		Connection dbConnection               = null;
		PreparedStatement dbPreparedstatement = null;
		ResultSet dbResultSet                 = null;
		MenteeRegDTO mregDTO                  = null;
		
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_ROLES_LIST_QUERY);			
			dbPreparedstatement.setString(1, ssoId);
			dbResultSet = dbPreparedstatement.executeQuery();
		 	list = new ArrayList();
		    System.out.println(MenteeQuery.APP_MENTEE_ROLES_LIST_QUERY);
			
		    while( dbResultSet.next()) {
		    	mregDTO = new MenteeRegDTO();	
		    	mregDTO.setFromMonth(dbResultSet.getString("FROM_MONTH"));
		    	mregDTO.setFromYear(dbResultSet.getString("FROM_YEAR"));
		    	mregDTO.setToMonth(dbResultSet.getString("TO_MONTH"));
		    	mregDTO.setToYear(dbResultSet.getString("TO_YEAR"));
		    	mregDTO.setOrganization(dbResultSet.getString("ORGANIZATION"));
		    	mregDTO.setDesignation(dbResultSet.getString("DESIGNATION"));
		    	mregDTO.setLocation(dbResultSet.getString("LOCATION"));
		    	mregDTO.setFromDate(dbResultSet.getString("FROM_DATE"));
		    	mregDTO.setToDate(dbResultSet.getString("TO_DATE"));
               	if(mregDTO.getFromDate()!=null) {					 
					 mregDTO.setFromMonthOptionList(MonthList.getMonthList(Integer.parseInt(mregDTO.getFromMonth())));
					 mregDTO.setFromYearOptionsList(MonthList.getYearList(Integer.parseInt(mregDTO.getFromYear())));
					 
                } else {
					 mregDTO.setFromMonthOptionList(MonthList.getMonthList());
					 mregDTO.setFromYearOptionsList(MonthList.getYearList());					 
                }

		    	if(mregDTO.getToDate()!=null) {					 
					 mregDTO.setToMonthOptionList(MonthList.getMonthList(Integer.parseInt(mregDTO.getToMonth())));
					 mregDTO.setToYearOptionsList(MonthList.getYearList(Integer.parseInt(mregDTO.getToYear())));
					 
               } else {
					 mregDTO.setToMonthOptionList(MonthList.getMonthList());
					 mregDTO.setToYearOptionsList(MonthList.getYearList());					 
               }
		       list.add(mregDTO);
		    }
		    objForm.setArrMenteeRoles(list);
		    System.out.println("list.size():"+list.size());
		    //objForm.setRowCount(list.size()==0 ? "1" :list.size()+"");
		    objForm.setRowCount(list.size()+"");
		    queryStatus = true;
		}catch (Exception ex) {
			System.out.println("Error while getting Mentee Information");
			queryStatus = false;
			ex.printStackTrace();
		}
		finally {
			try  {
				if (dbResultSet != null ) {
					dbResultSet.close();
					dbResultSet = null;
				}
				if ( dbPreparedstatement != null ) {
					 dbPreparedstatement.close(); 
					 dbPreparedstatement = null;
				}
				if ( dbConnection != null ) {
					 dbConnection.close();
					 dbConnection = null;
				}
			} catch (Exception sqlEx) {
				
			}
		}
		return queryStatus;
	}

	
	public boolean getMenteeSavedInformation(String ssoId, MenteeRegistrationForm objForm) throws Exception 
	{
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedstatement = null;
		ResultSet dbResultSet = null;
	
		try {
			
			dbConnection = dataSource.getConnection();
			dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_SAVE_LIST_QUERY);			
			dbPreparedstatement.setString(1, ssoId);
			dbResultSet = dbPreparedstatement.executeQuery();
			System.out.println(MenteeQuery.APP_MENTEE_SAVE_LIST_QUERY);
			if ( dbResultSet.next() ) {
				objForm.setMenteeBand(dbResultSet.getString("MENTEE_BAND")); 
				objForm.setMenteeElement(dbResultSet.getString("ELEMENTS_DESIRED_IN_MEN_REL"));
				objForm.setMenteeInterests(dbResultSet.getString("AREA_OF_INTEREST"));
				objForm.setMenteeMeetingCount(dbResultSet.getString("DESIRED_FRE_OF_MEET_WITH_MENT"));
				objForm.setMenteeDetails(dbResultSet.getString("PERSONAL_DETAILS"));
				queryStatus = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error while getting Mentee Information");
			queryStatus = false;
			ex.printStackTrace();
		}
		finally {
			try  {
				if (dbResultSet != null ) {
					dbResultSet.close();
					dbResultSet = null;
				}
				if ( dbPreparedstatement != null ) {
					 dbPreparedstatement.close(); 
					 dbPreparedstatement = null;
				}
				if ( dbConnection != null ) {
					 dbConnection.close();
					 dbConnection = null;
				}
			} catch (Exception sqlEx) {
				
			}
		}
		
		return queryStatus;
	}
	
	public boolean getMenteePositionsList(String ssoId, MenteeRegistrationForm objForm) throws Exception {
		
		boolean queryStatus = false;
		ArrayList list = null;
		Connection dbConnection = null;
		PreparedStatement dbPreparedstatement = null;
		ResultSet dbResultSet = null;
		MenteeRegDTO mregDTO = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_ROLES_LIST_QUERY);			
			dbPreparedstatement.setString(1, ssoId);
			dbResultSet = dbPreparedstatement.executeQuery();
		 	list = new ArrayList();

		 	while( dbResultSet.next()) {
		    	mregDTO = new MenteeRegDTO();	
		    	mregDTO.setFromMonth(dbResultSet.getString("FROM_MONTH"));
		    	mregDTO.setFromYear(dbResultSet.getString("FROM_YEAR"));
		    	mregDTO.setToMonth(dbResultSet.getString("TO_MONTH"));
		    	mregDTO.setToYear(dbResultSet.getString("TO_YEAR"));
		    	mregDTO.setOrganization(dbResultSet.getString("ORGANIZATION"));
		    	mregDTO.setDesignation(dbResultSet.getString("DESIGNATION"));
		    	mregDTO.setLocation(dbResultSet.getString("LOCATION"));
		    	mregDTO.setFromDate(dbResultSet.getString("FROM_DATE"));
		    	mregDTO.setToDate(dbResultSet.getString("TO_DATE"));
               	if(mregDTO.getFromDate()!=null) {					 
					 mregDTO.setFromMonthOptionList(MonthList.getMonthList(Integer.parseInt(mregDTO.getFromMonth())));
					 mregDTO.setFromYearOptionsList(MonthList.getYearList(Integer.parseInt(mregDTO.getFromYear())));
					 
                } else {
					 mregDTO.setFromMonthOptionList(MonthList.getMonthList());
					 mregDTO.setFromYearOptionsList(MonthList.getYearList());					 
                }

		    	if(mregDTO.getToDate()!=null) {					 
					 mregDTO.setToMonthOptionList(MonthList.getMonthList(Integer.parseInt(mregDTO.getToMonth())));
					 mregDTO.setToYearOptionsList(MonthList.getYearList(Integer.parseInt(mregDTO.getToYear())));
					 
               } else {
					 mregDTO.setToMonthOptionList(MonthList.getMonthList());
					 mregDTO.setToYearOptionsList(MonthList.getYearList());					 
               }
		       list.add(mregDTO);
		    }
		    objForm.setArrMenteeRoles(list);
		    System.out.println("list.size():"+list.size());
		    objForm.setRowCount(list.size()+"");
		    //objForm.setRowCount(list.size()==0 ? "1":list.size()+"");
		    queryStatus = true;
		}catch (Exception ex) {
			System.out.println("Error while getting Mentee Information");
			queryStatus = false;
			ex.printStackTrace();
		}
		finally {
			try  {
				if (dbResultSet != null ) {
					dbResultSet.close();
					dbResultSet = null;
				}
				if ( dbPreparedstatement != null ) {
					 dbPreparedstatement.close(); 
					 dbPreparedstatement = null;
				}
				if ( dbConnection != null ) {
					 dbConnection.close();
					 dbConnection = null;
				}
			} catch (Exception sqlEx) {
				
			}
		}
		return queryStatus;
	}
	public boolean updateMenteeDetails(String ssoId, MenteeEditForm objForm) throws Exception {
		
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedstatement = null;
		
		try {
			dbConnection = dataSource.getConnection();
			dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_UPDATE_REGISTER_MENTEE_QUERY);
			dbPreparedstatement.setString(1, objForm.getMenteeElement());
			dbPreparedstatement.setString(2, objForm.getMenteeMeetingCount());
			dbPreparedstatement.setString(3, objForm.getMenteeInterests());
			dbPreparedstatement.setString(4, objForm.getMenteeDetails());
			dbPreparedstatement.setString(5, ssoId);
			dbPreparedstatement.executeUpdate();		
			queryStatus = true;
		}
		catch (Exception ex) {
			System.out.println("Error while updating Mentee Information");
			queryStatus = false;
			ex.printStackTrace();
		}
		finally {
			try {
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
		return queryStatus;
	}
	
	
	  public boolean getMenteeFullDetails(String menteeId,String mappingId,MenteeProfileFormBean objForm) throws Exception
	  {
	      boolean queryStatus = false;
	      Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      try {
	    	  
	    	   dbConnection = dataSource.getConnection();
	    	   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_DETAILS_LIST_QUERY);
	    	   dbPreparedStatement.setInt(1,Integer.parseInt(mappingId));
	    	   dbPreparedStatement.setString(2,menteeId);
	    	   dbPreparedStatement.setInt(3,Integer.parseInt(mappingId));
	    	   dbResultSet = dbPreparedStatement.executeQuery();
	    	   
	    	  
	    	   while(dbResultSet.next()) {
	    		   objForm.setMenteeName(dbResultSet.getString("GEHRFULLNAME"));
	    		   objForm.setBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	    		   objForm.setDesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	    		   objForm.setMenteeRegSerialNo(dbResultSet.getString("MENTEE_REGISTERED_SLNO"));
	    		   objForm.setElementDesired(dbResultSet.getString("ELEMENTS_DESIRED_IN_MEN_REL"));
	    		   objForm.setFrequenceOfMeeting(dbResultSet.getString("DESIRED_FRE_OF_MEET_WITH_MENT"));
	    		   objForm.setAreaOfInterest(dbResultSet.getString("AREA_OF_INTEREST"));
	    		   objForm.setPersonalDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	    		   objForm.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	    		   objForm.setApproveOrRejectionDate(dbResultSet.getString("APPROVAL_REJECTION_DATE"));
	    		   objForm.setInitiatedDate(dbResultSet.getString("MENTEE_INITIATED_DATE"));
                 //	    		 Get the list of Rols and position hold in/outside GE
		 	 	   ArrayList arrRPList = getMenteeRolsPositionInGE(menteeId);
		 	 	   objForm.setArrRPList(arrRPList);
		 	 	   
	     	 }
		     queryStatus = true;
	    	  
	      }catch(Exception ex) {
	    	  queryStatus = false;
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return queryStatus;
	  }
	  
	  public boolean getMenteeDetails( String menteeId,MenteeProfileFormBean objForm ) throws Exception
	  {
	      boolean queryStatus = false;
	      Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      try {
	    	  
	    	   dbConnection = dataSource.getConnection();
	    	   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_DETAILS_LIST);
	    	   dbPreparedStatement.setString(1,menteeId);
	    	   dbResultSet = dbPreparedStatement.executeQuery();
	    	  
	    	   while(dbResultSet.next()) {
	    		   objForm.setMenteeName(dbResultSet.getString("GEHRFULLNAME"));
	    		   objForm.setBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	    		   objForm.setDesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	    		   objForm.setMenteeRegSerialNo(dbResultSet.getString("MENTEE_REGISTERED_SLNO"));
	    		   objForm.setElementDesired(dbResultSet.getString("ELEMENTS_DESIRED_IN_MEN_REL"));
	    		   objForm.setFrequenceOfMeeting(dbResultSet.getString("DESIRED_FRE_OF_MEET_WITH_MENT"));
	    		   objForm.setAreaOfInterest(dbResultSet.getString("AREA_OF_INTEREST"));
	    		   objForm.setPersonalDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	    		   //objForm.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	    		   //objForm.setApproveOrRejectionDate(dbResultSet.getString("APPROVAL_REJECTION_DATE"));
	    		   //objForm.setInitiatedDate(dbResultSet.getString("MENTEE_INITIATED_DATE"));
                   //System.out.println("Slno:"+objForm.getMenteeRegSerialNo());
	    		   //	    		 Get the list of Rols and position hold in/outside GE
		 	 	   ArrayList arrRPList = getMenteeRolsPositionInGE(menteeId);
		 	 	   objForm.setArrRPList(arrRPList);
	     	 }
		     queryStatus = true;
	    	  
	      }catch(Exception ex) {
	    	  queryStatus = false;
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return queryStatus; 
	  }
	  
	 public ArrayList getMenteeRolsPositionInGE(String menteeId) throws Exception {
		    ArrayList arrEBLst   = null;
			Connection con 		 = null;
			ResultSet rs 		 = null;
			PreparedStatement ps = null;		
			MenteeRegDTO mregDto = null;
			try {
				 arrEBLst = new ArrayList();
				 con = dataSource.getConnection();
				 ps = con.prepareStatement(MenteeQuery.APP_MENTEE_ROLES_LIST_QUERY);
				 ps.setString(1,menteeId);
				 rs = ps.executeQuery();
				 while (rs.next()) {				 
					 mregDto = new MenteeRegDTO();
					 if(rs.getString("FROM_DATE") != null)
					 mregDto.setFromDate(rs.getString("FROM_MONTH")+"/"+rs.getString("FROM_YEAR"));
					 else mregDto.setFromDate("");
					 if(rs.getString("TO_DATE") != null)	 
					 mregDto.setToDate(rs.getString("TO_MONTH")+"/"+rs.getString("TO_YEAR"));
					 else mregDto.setToDate("");
					 mregDto.setOrganization(rs.getString("ORGANIZATION"));
					 mregDto.setDesignation(rs.getString("DESIGNATION"));
					 mregDto.setLocation(rs.getString("LOCATION"));
					 arrEBLst.add(mregDto);
				 }	
			} catch(Exception e) {
				throw e;
			} finally {
				try {
					  if(rs!=null){
						  rs.close();
						  rs = null;
					  }
					  if(ps!=null){
						  ps.close();
						  ps = null;
					  }
					  if(con!=null){
						  con.close();
						  con = null;
					  }				  
					} catch(SQLException sqlex) {
						throw sqlex;
					}			
			}
			return arrEBLst;
		}
	
	 public ActionErrors validateMenteeSaveFormFields(MenteeRegistrationForm mregFb) 
	 {
		 int maxLimit = 1000;
			ActionErrors errors = new ActionErrors();
			
			if (! ValidateService.isEmpty(mregFb.getMenteeBand()) ) 	    		
	         if ( ! ValidateService.isNumeric(mregFb.getMenteeBand()) ) {
	    	       errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Band Id should come in Numeric value."));
	        }        
	        
		    if ( ! ValidateService.isEmpty(mregFb.getMenteeElement()) ) {	    		
		      if ( ! ValidationField.isValid(mregFb.getMenteeElement()) ) {
		        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		    } else if(mregFb.getMenteeElement().length() > maxLimit) {
		    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field should not exceed 1000 characters.")); 
		    } }  
	         
		    if ( ! ValidateService.isEmpty(mregFb.getMenteeInterests()) ) {
			    if ( ! ValidationField.isValid(mregFb.getMenteeInterests()) ) {
			     	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
			    } else if(mregFb.getMenteeMeetingCount().length() > maxLimit) {
			    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field should not exceed 1000 characters."));
			    }
		    }
		    
		    if ( ! ValidateService.isEmpty(mregFb.getMenteeDetails()) ) {
		      if ( (! ValidationField.isValid(mregFb.getMenteeDetails())) ) {
		     	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		      } else if(mregFb.getMenteeDetails().length() > maxLimit) {
		    	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field should not exceed 1000 characters.")); 
		      }
		    }
			return errors;
		}
		
	 public ActionErrors validateMenteeFormFields(MenteeRegistrationForm mregFb)
	{
		int maxLimit = 1000;
		ActionErrors errors = new ActionErrors();
		
		if ( ValidateService.isEmpty(mregFb.getMenteeBand()) ) {	    		
               errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", "Band should be mandatory. please select a value."));
        } else if ( ! ValidateService.isNumeric(mregFb.getMenteeBand()) ) {
    	       errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Band Id should come in Numeric value."));
        }        
        
	    
	    if ( ValidateService.isEmpty(mregFb.getMenteeElement()) ) {	    		
	            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field is mandatory."));	            
	    } else if ( ! ValidationField.isValid(mregFb.getMenteeElement()) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } else if(mregFb.getMenteeElement().length() > maxLimit) {
	    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field should not exceed 1000 characters.")); 
	    }
        
	    if ( ValidateService.isEmpty(mregFb.getMenteeMeetingCount()) ) {	    		
	            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Desired frequency of meeting with mentor field is mandatory."));	            
	    } else if ( ! ValidateService.isAlphaB(mregFb.getMenteeMeetingCount()) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Desired frequency of meeting with mentor field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } 
	     
	    if ( ! ValidateService.isEmpty(mregFb.getMenteeInterests()) ) {
		    if ( ! ValidationField.isValid(mregFb.getMenteeInterests()) ) {
		     	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		    } else if(mregFb.getMenteeMeetingCount().length() > maxLimit) {
		    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field should not exceed 1000 characters."));
		    }
	    }
	    
	    if ( ! ValidateService.isEmpty(mregFb.getMenteeDetails()) ) {
	      if ( (! ValidationField.isValid(mregFb.getMenteeDetails())) ) {
	     	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	      } else if(mregFb.getMenteeDetails().length() > maxLimit) {
	    	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field should not exceed 1000 characters.")); 
	      }
	    }
		
		return errors;
		
	}
	
	
	public ActionErrors validateRolesFormFields(int rowCount, MenteeRegDTO mregDTO)
	{
		ActionErrors errors = new ActionErrors();
		
		String orgVals []         = mregDTO.getOrgVals();
		String designationVals [] = mregDTO.getDesignationVals();
		String locationVals []    = mregDTO.getLocationVals();
		String fromMonthVals []   = mregDTO.getFromMonthVals();
		String fromYearVals  []   = mregDTO.getFromYearVals();
		String toMonthVals []     = mregDTO.getToMonthVals();
		String toYearVals []      = mregDTO.getToYearVals();
	    String userAction         = mregDTO.getUserAction();
		
		int maxLimit = 1000;
		
		if(userAction != null && userAction.equals("Save")) {
			for(int i=0;i <rowCount;i++) {
			 
			if(rowCount > 1) {
				if ( ValidateService.isEmpty(orgVals[i])  && ValidateService.isEmpty(designationVals[i]) && ValidateService.isEmpty(locationVals[i]) && fromMonthVals[i].equals("0") && fromYearVals[i].equals("0") && toMonthVals[i].equals("0") && toYearVals[i].equals("0") ) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Roles/Positions held in and outside GE Recored should not be empty.Please enter either data in atleast one field or delete the empty row"));
			}
			}	
			
			if ( ! ValidateService.isEmpty(orgVals[i]) ) {	    		
		       if ( ! ValidationField.isValid(orgVals[i]) ) {
		        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Organization field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		       } else if(orgVals[i].length() > maxLimit) {
		    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Organization field should not exceed 1000 characters.")); 
		       }
			 }       
			
			if ( ! ValidateService.isEmpty(designationVals[i]) ) {	    		
	         if ( ! ValidationField.isValid(designationVals[i]) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Designation field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	         } else if(designationVals[i].length() > maxLimit) {
	    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Designation field should not exceed 1000 characters.")); 
	         }
			}  
			
			if ( ! ValidateService.isEmpty(locationVals[i]) ) {	    		
	         if ( ! ValidationField.isValid(locationVals[i]) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Location field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	         } else if(locationVals[i].length() > maxLimit) {
	    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Location field should not exceed 1000 characters.")); 
	         }
			} 
			
			if((!fromMonthVals[i].equals("0"))) {
	              if(!ValidationField.validateNuemeric(fromMonthVals[i])) {
	                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","From Month can contain only valid numeric values !"));
	              }
	          }
	        if((!fromYearVals[i].equals("0"))) {
	              if(!ValidationField.validateNuemeric(fromYearVals[i])) {
	                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","From Year can contain only valid numeric values !"));
	              }
	          }                  
			  /////////////////////////////////////////////////////////////////////
	        if((!toMonthVals[i].equals("0"))) {
	              if(!ValidationField.validateNuemeric(toMonthVals[i])) {
	                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","To Month can contain only valid numeric values !"));
	              }
	          }
	        if((!toYearVals[i].equals("0"))) {
	              if(!ValidationField.validateNuemeric(toYearVals[i])) {
	            	  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","To Year can contain only valid numeric values !"));
	              }
	          }                     			  
			  /////////////////////////////////////////////////////////////////////////
	          
			
			if( ! fromMonthVals[i].equals("0")) {
				if( fromYearVals[i].equals("0")) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select year in From Date field"));
				}
			}else if(!fromYearVals[i].equals("0")) { 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select Month in From Date field"));
			}
		
			if( ! toMonthVals[i].equals("0")) {
				if( toYearVals[i].equals("0")) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select year in To date field"));
				}
			}else if(!toYearVals[i].equals("0")) { 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select Month in From Date field"));
			}

			if( !fromYearVals[i].equals("0") && ! toYearVals[i].equals("0") && !fromMonthVals[i].equals("0") && ! toMonthVals[i].equals("0")) {
				if(Integer.parseInt(fromYearVals[i]) == Integer.parseInt(toYearVals[i])) {
					if(Integer.parseInt(fromMonthVals[i]) > Integer.parseInt(toMonthVals[i])) {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","From Date should not be greater than to Date"));
					}
				}
			}
		 }
		}else {

		for(int i=0;i <rowCount;i++) {
			
		if ( ValidateService.isEmpty(orgVals[i]) ) {	    		
	            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Organization field is mandatory."));	            
	    } else if ( ! ValidationField.isValid(orgVals[i]) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Organization field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } else if(orgVals[i].length() > maxLimit) {
	    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Organization field should not exceed 1000 characters.")); 
	    }
		
		if ( ValidateService.isEmpty(designationVals[i]) ) {	    		
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Designation field is mandatory."));	            
        } else if ( ! ValidationField.isValid(designationVals[i]) ) {
        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Designation field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
        } else if(designationVals[i].length() > maxLimit) {
    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Designation field should not exceed 1000 characters.")); 
        }
		
		if ( ! ValidateService.isEmpty(locationVals[i]) ) {	    		
         if ( ! ValidationField.isValid(locationVals[i]) ) {
        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Location field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
         } else if(locationVals[i].length() > maxLimit) {
    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Location field should not exceed 1000 characters.")); 
         }
		} 
		
		if((!fromMonthVals[i].equals("0"))) {
              if(!ValidationField.validateNuemeric(fromMonthVals[i])) {
                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","From Month can contain only valid numeric values !"));
              }
          }
        if((!fromYearVals[i].equals("0"))) {
              if(!ValidationField.validateNuemeric(fromYearVals[i])) {
                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","From Year can contain only valid numeric values !"));
              }
          }                  
		  /////////////////////////////////////////////////////////////////////
        if((!toMonthVals[i].equals("0"))) {
              if(!ValidationField.validateNuemeric(toMonthVals[i])) {
                  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","To Month can contain only valid numeric values !"));
              }
          }
        if((!toYearVals[i].equals("0"))) {
              if(!ValidationField.validateNuemeric(toYearVals[i])) {
            	  errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.database.error","To Year can contain only valid numeric values !"));
              }
          }                     			  
		  /////////////////////////////////////////////////////////////////////////
          
		
		if( ! fromMonthVals[i].equals("0")) {
			if( fromYearVals[i].equals("0")) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select year in From Date field"));
			}
		}else if(!fromYearVals[i].equals("0")) { 
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select Month in From Date field"));
		}
	
		if( ! toMonthVals[i].equals("0")) {
			if( toYearVals[i].equals("0")) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select year in To date field"));
			}
		}else if(!toYearVals[i].equals("0")) { 
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","You should select Month in From Date field"));
		}

		if( !fromYearVals[i].equals("0") && ! toYearVals[i].equals("0") && !fromMonthVals[i].equals("0") && ! toMonthVals[i].equals("0")) {
			if(Integer.parseInt(fromYearVals[i]) == Integer.parseInt(toYearVals[i])) {
				if(Integer.parseInt(fromMonthVals[i]) > Integer.parseInt(toMonthVals[i])) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("erros.database.error","From Date should not be greater than to Date"));
				}
			}
		}
	}}
		return errors;
	}
	
	public ActionErrors validateMenteeFormFields(MenteeEditForm mregFb)
	{
		ActionErrors errors = new ActionErrors();
		int maxLimit = 1000;
		
	   
	    if ( ValidateService.isEmpty(mregFb.getMenteeElement()) ) {	    		
	            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field is mandatory."));	            
	    } else if ( ! ValidationField.isValid(mregFb.getMenteeElement()) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } else if(mregFb.getMenteeElement().length() > maxLimit) {
	    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Elements desired in mentoring relationship field should not exceed 1000 characters.")); 
	    }
     
	    if ( ValidateService.isEmpty(mregFb.getMenteeMeetingCount()) ) {	    		
	            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Desired frequency of meeting with mentor field is mandatory."));	            
	    } else if ( ! ValidateService.isAlphaB(mregFb.getMenteeMeetingCount()) ) {
	        	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Desired frequency of meeting with mentor field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	    } 
	     
	    if ( ! ValidateService.isEmpty(mregFb.getMenteeInterests()) ) {	    		
	        if ( ! ValidationField.isValid(mregFb.getMenteeInterests()) ) {
		     	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		    } else if(mregFb.getMenteeMeetingCount().length() > maxLimit) {
		    	    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Areas of interest field should not exceed 1000 characters."));
		    }
	    }
	    
	    if ( ! ValidateService.isEmpty(mregFb.getMenteeDetails()) ) {
	      if ( (! ValidationField.isValid(mregFb.getMenteeDetails())) ) {
	     	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
	      } else if(mregFb.getMenteeDetails().length() > maxLimit) {
	    	     errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Personal Details field should not exceed 1000 characters.")); 
	      }
	    }
		
		return errors;
		
	}
	
	public ActionErrors validateMentorSearchFormFields(MenteeFindingMentorForm objForm)
	{
		ActionErrors errors = new ActionErrors();
		
	    if( ! ValidateService.isEmpty(objForm.getSearchBymentorName())) {
			if ( ! ValidationField.isValid(objForm.getSearchBymentorName()) ) 
		           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Mentor Name can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		    }
	    
	    if( ! ValidateService.isEmpty(objForm.getSearchBymentorStrength())) {
		    if ( ! ValidationField.isValid(objForm.getSearchBymentorStrength()) ) 
			       errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error"," Strengths/Expertise can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		    }
	    
		if( ! ValidateService.isEmpty(objForm.getSearchBymentorBusiness())) {
		    if ( ! ValidateService.isAlpha(objForm.getSearchBymentorBusiness()) ) 
				   errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error"," Mentor Business can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
			}	
		return errors;
	}	
	
	public ActionErrors validatessoId(String mentorId, String statusId,String mappingId,String personId) throws Exception
	{
		ActionErrors errors = new ActionErrors();
		
		if ( ! ValidateService.isEmpty(mentorId)) {
			if( ! ValidateService.isAlphaNumeric(mentorId)) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","ssoId is a numeric field."));
		}
		
		if ( ! ValidateService.isEmpty(statusId)) {
			if( ! ValidateService.isNumeric(statusId)) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","statusId is a numeric field."));
		}
		
		if ( ! ValidateService.isEmpty(mappingId)) {
			if( ! ValidateService.isNumeric(mappingId)) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","mappingId is a numeric field."));
		}
		
		if ( ! ValidateService.isEmpty(personId)) {
			if( ! ValidateService.isAlphaNumeric(personId)) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","personId is a numeric field."));
		}
		return errors;
	}
	
	public ActionErrors validateMentorSearchStatusFormFields(MentorProfileForm objForm) {
		ActionErrors errors  = new ActionErrors();
		
		if ( ! ValidateService.isEmpty(objForm.getMentorName())) {
			if( ! ValidationField.isValid(objForm.getMentorName())) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Mentor Name can accept the following characters like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		}
		
		return errors;
	}
	
	public ActionErrors validateMenteeSearchStatusFormFields(MenteeProfileFormBean objForm) {
		ActionErrors errors  = new ActionErrors();
		
		if ( ! ValidateService.isEmpty(objForm.getMenteeName())) {
			if( ! ValidationField.isValid(objForm.getMenteeName())) 
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error","Mentor Name can accept the following charactes like [a-zA-Z0-9.()-/\\_&#':;=$?|!@%,{}]."));
		}
		
		return errors;
	}
	
	public boolean getMentorDetails(MenteeFindingMentorForm objForm, DataSource ds, String ssoId)
	     throws Exception
    {	     
		
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedstatement = null;
		ResultSet dbResultSet = null;
	    ArrayList list = new ArrayList();
		String sMentorName = null;
		String sMentorPath = null;
		String sMentorBusiness = null;
		String sMentorStrengths = null;
		
		StringBuffer query = new StringBuffer();
		StringBuffer mainQuery = new StringBuffer();
		MenteeDTO menteeDto = null;
		try {			
			dbConnection = dataSource.getConnection();
			
			sMentorName      = objForm.getSearchBymentorName();
			sMentorPath      = objForm.getSearchBymentorPath();
			sMentorBusiness  = objForm.getSearchBymentorBusiness();
			sMentorStrengths = objForm.getSearchBymentorStrength();
			
			if(sMentorName !=null && !sMentorName.equals("")) {
			   query.append(" AND UPPER(A.GEHRFULLNAME) LIKE '%" + sMentorName.toUpperCase() + "%' ");
			}
			
			if(sMentorPath !=null && !sMentorPath.equals("All")){
			     //query.append(" AND B.PATH_ID= "+sMentorPath);
				query.append(" AND B.PATH_ID IN("+sMentorPath+",3)");
			}
			
			if(sMentorBusiness !=null && !sMentorBusiness.equals("All")){
			   query.append(" AND UPPER(A.GEBUSINESSUNIT) LIKE '%" + sMentorBusiness.toUpperCase() + "%' ");
			}
			
			
			if(sMentorStrengths != null && !sMentorStrengths.equals("")){
				query.append(" AND UPPER(B.STRENGHS_COMPETENCIES) LIKE '%" + sMentorStrengths.toUpperCase() + "%'");
			}
				
			if(sMentorPath.equals("All")&&(sMentorBusiness.equals("All"))){
				query.append(" ORDER BY B.PATH_ID,A.GEBUSINESSUNIT ");
			}else if(sMentorPath.equals("All")) {
				query.append(" ORDER BY B.PATH_ID ");
			}else if(sMentorBusiness.equals("All")) {
				query.append(" ORDER BY A.GEBUSINESSUNIT ");
			}   
			
			if(!sMentorName.equals("") || !sMentorPath.equals("") || ! sMentorBusiness.equals("") || ! sMentorStrengths.equals("") ) {
				mainQuery.append("SELECT A.SSOID, A.GEHRTITLE,A.GEHRFULLNAME, A.GEBUSINESSUNIT,"); 
		        mainQuery.append(" A.GEHRDAILCOM, A.MAIL, B.STRENGHS_COMPETENCIES,C.PATH_DESCRIPTION FROM MENTOR_SPODA_EMPLOYEE A, MENTOR_REGISTERED_MENTORS B");
		        mainQuery.append(" ,MENTOR_MENTORING_PATH_MASTER C WHERE C.PATH_ID = B.PATH_ID AND A.SSOID=  secure_data.DECRYPTDATA(B.MENTOR_SSOID) ");
		        mainQuery.append(" AND B.BAND_ID IN (" );
				mainQuery.append(mentorBands(ssoId)+")");
				mainQuery.append(" AND A.LOCATIONID IN (");
				mainQuery.append(mentorLocationIds(ssoId)+")");
				mainQuery.append( " AND secure_data.DECRYPTDATA(B.MENTOR_SSOID) <> ? AND B.STATUS_ID=2 ");
		        mainQuery.append(query.toString()+" ");
			    System.out.println(mainQuery.toString());
			
			dbPreparedstatement = dbConnection.prepareStatement(mainQuery.toString());
			
			dbPreparedstatement.setString(1, ssoId);
			
			dbResultSet = dbPreparedstatement.executeQuery();
			
			while ( dbResultSet.next() ) {
			  	menteeDto = new MenteeDTO();
			  	menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
			  	menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
			  	menteeDto.setMentorMail(dbResultSet.getString("MAIL"));
			  	menteeDto.setMentorDialCom(dbResultSet.getString("GEHRDAILCOM"));
			  	menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
			  	menteeDto.setMentoringPath(dbResultSet.getString("PATH_DESCRIPTION"));
			  	menteeDto.setMentorId(dbResultSet.getString("SSOID"));
			    menteeDto.setMentorhasEnoughMentees(isMentorHavingEnoughMentees(menteeDto.getMentorId()));
		        menteeDto.setMentorStatusDesc(menteeSelectedMentor(ssoId,menteeDto.getMentorId()));
		        menteeDto.setStatusId(getStatusId(ssoId,menteeDto.getMentorId()));
		        list.add(menteeDto);
			}
			
		 }    
		 objForm.setMentorRecord(list);
		 objForm.setMenteeHasMentor(isMenteeHasMentor(ssoId));
		 queryStatus = true;
	    
       }catch(Exception ex) {
          ex.printStackTrace();
       }finally {
    	   try {
    		    if ( dbResultSet != null ) {
   			         dbResultSet.close(); 
    			     dbResultSet = null;
				}
    		 
    		   if ( dbPreparedstatement != null ) {
    			    dbPreparedstatement.close();
    			    dbPreparedstatement = null;
			   }
				
			   if ( dbConnection != null ) {
					dbConnection.close();
					dbConnection = null;
			   }
    	   }catch(Exception ex) {
    		   ex.printStackTrace();
    	   }
       }       
       return queryStatus;
    }
	
	public boolean updateMentorMenteeRelation(String ohrid,String mentorOhrId, MenteeFindingMentorForm objForm)
	      throws Exception
	      {
		   Connection dbConnection = null;
		   PreparedStatement dbPreparedstatement = null;
		   boolean queryStatus = false;
		  
		   try {
			    dbConnection = dataSource.getConnection();
		        dbPreparedstatement = dbConnection.prepareStatement(MenteeQuery.APP_MENOR_MENTEE_MAPPING_QUERY);			        
			    dbPreparedstatement.setString(1,ohrid);
			    dbPreparedstatement.setString(2,mentorOhrId);
			    dbPreparedstatement.setString(3,"4");
				dbPreparedstatement.executeUpdate();		
				queryStatus = true;
		   }catch(Exception ex) {
			    System.out.println("Error while inserting mentor_mentee_mapping Information");
				queryStatus = false;
				ex.printStackTrace();  
		  }finally {
			  try {
				  if ( dbPreparedstatement != null ) {
					   dbPreparedstatement.close(); 
					   dbPreparedstatement = null;
					 }
					
					if ( dbConnection != null ) {
						 dbConnection.close(); 
						 dbConnection = null;
					} 
				  
			  }catch(Exception ex) {
				  ex.printStackTrace();
			  }
		  }
		    
		 return queryStatus;
	     }
	
	  
	public boolean isMenteeHasMentor(String menteeId) throws Exception {
		
		   Connection dbConnection = null;
		   PreparedStatement dbPreparedStatement = null;
		   ResultSet dbResultSet = null;
		   boolean queryStatus = false;
		   
		   try {
			   dbConnection = dataSource.getConnection();
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTEE_SELECTED_MENTOR_QUERY);
			   dbPreparedStatement.setString(1,menteeId);
			   dbResultSet = dbPreparedStatement.executeQuery();
		       if(dbResultSet.next())  queryStatus = true;
		       else queryStatus = false;
		   }catch(Exception ex) {
			   queryStatus = false;
			   ex.printStackTrace();
		   }finally {
			   try {
				   
				   if ( dbResultSet != null ) {
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
				   
			   }catch(Exception ex) {
				   ex.printStackTrace();
			   }
		   }
	      return queryStatus;
	}
	
	public String menteeSelectedMentor(String menteeId,String mentorId) throws Exception {
	  
		   
		   Connection dbConnection = null;
		   PreparedStatement dbPreparedStatement = null;
		   ResultSet dbResultSet = null;
		   String queryStatus = null;
		   
		   try {
			   dbConnection = dataSource.getConnection();
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENOR_MENTEE_MAPPING_EXISTANCE_QUERY);
			   dbPreparedStatement.setString(1,menteeId);
			   dbPreparedStatement.setString(2,mentorId);
			   dbResultSet = dbPreparedStatement.executeQuery();
		       
			   if(dbResultSet.next()) {
				   queryStatus = dbResultSet.getString("STATUS_DESCRIPTION"); 
		       }
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   try {
				   
				   if ( dbResultSet != null ) {
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
				   
			   }catch(Exception ex) {
				   ex.printStackTrace();
			   }
		   }
	   
		  if(queryStatus != null && !queryStatus.equals("")) 
		   return queryStatus;
		  else return "";
	  }
	  
	  public boolean isMentorHavingEnoughMentees(String mentorId) throws Exception {
		
		 Connection dbConnection = null;
		 PreparedStatement dbPreparedStatement = null;
		 ResultSet dbResultSet = null;
		 int count = 0;
		 boolean queryStatus = false;
		 try {
			  dbConnection  = dataSource.getConnection();
			  dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_EXISTANCE_MENTEES_QUERY);
			  dbPreparedStatement.setString(1,mentorId);
			  dbResultSet = dbPreparedStatement.executeQuery();
			  
			  if(dbResultSet.next()) {
				  count = dbResultSet.getInt("MENTEES");
			  }
			  
			  if(count==requiredNumberofMentees(mentorId)) queryStatus = true;
			   else queryStatus = false;
				  
		 }catch(Exception ex) {}
		finally {
			 try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		 }
		 return queryStatus;
	  }
	  
	  
	  public ArrayList mentorBusinessList(int locId) throws Exception
	  {
		  ArrayList businessList = null;
		  Connection dbConnection = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  MenteeDTO menteeDto = null;
		  try {
			   businessList = new ArrayList();
			   dbConnection  = dataSource.getConnection();			   
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_MENTEE_BUSINESSUNIT_LIST_QUERY);
			   dbPreparedStatement.setInt(1,locId);
			   dbResultSet = dbPreparedStatement.executeQuery();
			   while(dbResultSet.next())  {
				  menteeDto = new MenteeDTO();
				  menteeDto.setBusinessname(dbResultSet.getString(1));				  
				  businessList.add(menteeDto);				  
			   }
			   
		  } catch(Exception ex) {
			  ex.printStackTrace();			  
		  } finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return businessList;
	  }
	  
	  
	  public ArrayList mentorPathList() throws Exception
	  {
		  ArrayList pathList = null;
		  Connection dbConnection = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  MenteeDTO menteeDto = null;
		  
		  try {
			    dbConnection = dataSource.getConnection();
			    dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_MENTEE_PATH_LIST_QUERY);
			    dbResultSet = dbPreparedStatement.executeQuery();
			    pathList = new ArrayList();
			    
			    while(dbResultSet.next()) {
			    	
			    	menteeDto = new MenteeDTO();
			    	menteeDto.setMentorPathID(dbResultSet.getString(1));
			    	menteeDto.setMentorPathDesc(dbResultSet.getString(2));
			        pathList.add(menteeDto);
			        
			    }
			      
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  
		  return pathList;
	  }
	  
	  public ArrayList getMentorFullDetails(String mentorId,String mappingId) throws Exception {
		  Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      MenteeDTO menteeDto = null;
	      ArrayList mentorDetails = null;
	      try {
	    	  
	    	   dbConnection = dataSource.getConnection();
	    	   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_DETAILS_LIST_QUERY_BY_SSOID);
	    	   dbPreparedStatement.setInt(1,Integer.parseInt(mappingId));
	    	   dbPreparedStatement.setString(2,mentorId);
	    	   dbPreparedStatement.setInt(3,Integer.parseInt(mappingId));
	    	   dbResultSet = dbPreparedStatement.executeQuery();
	    	   mentorDetails = new ArrayList();
	    	   
	    	   while(dbResultSet.next()) {
	    		   menteeDto = new MenteeDTO();
	    		   
	    		   menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
	    		   menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	    		   menteeDto.setMentordesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	    		   menteeDto.setMentorEducationDetails(dbResultSet.getString("EDUCATIONAL_BACKGROUND"));
	    		   menteeDto.setMentorInterests(dbResultSet.getString("AREA_OF_INTEREST"));
	    		   menteeDto.setMentorPositionsheld(dbResultSet.getString("POSITIONS_HELD_IN_OUTSIDE_GE"));
	    		   menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
	    		   menteeDto.setMentorDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	    		   menteeDto.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	    		   menteeDto.setInitiatedDate(dbResultSet.getString("MENTEE_INITIATED_DATE"));
	    		   menteeDto.setApproveOrRejectionDate(dbResultSet.getString("APPROVAL_REJECTION_DATE"));
	    		   mentorDetails.add(menteeDto);
	    	   }
	      }catch(Exception ex) {
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return mentorDetails;
	  }
	  
	  
	  public ArrayList getMentorFullDetails(String mentorId,String menteeId,String statusId) throws Exception
	  {
	      
	      Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      MenteeDTO menteeDto = null;
	      ArrayList mentorDetails = null;
	      try {
	    	  
	    	   dbConnection = dataSource.getConnection();
	    	   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_DETAILS_LIST_QUERY);
	    	   dbPreparedStatement.setString(1,menteeId);
		       dbPreparedStatement.setString(2,mentorId);
		       dbPreparedStatement.setInt(3,Integer.parseInt(statusId));
		       dbPreparedStatement.setString(4,mentorId);
		       dbResultSet = dbPreparedStatement.executeQuery();
	    	   mentorDetails = new ArrayList();
	    	   
	    	   while(dbResultSet.next()) {
	    		   menteeDto = new MenteeDTO();
	    		   menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
	    		   menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	    		   menteeDto.setMentordesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	    		   menteeDto.setMentorMail(dbResultSet.getString("MAIL"));
	    		   menteeDto.setMentorDialCom(dbResultSet.getString("GEHRDAILCOM"));
	    		   menteeDto.setMentorEducationDetails(dbResultSet.getString("EDUCATIONAL_BACKGROUND"));
	    		   menteeDto.setMentorInterests(dbResultSet.getString("AREA_OF_INTEREST"));
	    		   menteeDto.setMentorPositionsheld(dbResultSet.getString("POSITIONS_HELD_IN_OUTSIDE_GE"));
	    		   menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
	    		   menteeDto.setMentorDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	    		   menteeDto.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	    		   menteeDto.setInitiatedDate(dbResultSet.getString("MENTEE_INITIATED_DATE"));
	    		   menteeDto.setApproveOrRejectionDate(dbResultSet.getString("APPROVAL_REJECTION_DATE"));
	    		   menteeDto.setMentoringPath(dbResultSet.getString("PATH_DESCRIPTION"));
	    		   //menteeDto.setStatusId(getStatusId(menteeId,mentorId));
	      		   //menteeDto.setStatusIdList(getStatusDetails(mentorId,menteeId));
	      		  // if( menteeDto.getStatusIdList().size() == 1)
	      		   menteeDto.setStatusDescription(getStatusDescription(menteeDto.getStatusId())); 
	       		   mentorDetails.add(menteeDto);
	    	   }
	    	   
	      }catch(Exception ex) {
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return mentorDetails;
	  }
	  
	  public ArrayList getMentorFullDetailsByMappingId(String ssoId,String mappingId,String mentorId) throws Exception
	  {
	      Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      MenteeDTO menteeDto = null;
	      MentorBean mentorBean = null;
	      ArrayList mentorDetails = null;
	      try {
	    	  
	    	   dbConnection = dataSource.getConnection();
	    	   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_DETAILS_LIST_BY_MAPPING_ID);
	    	   dbPreparedStatement.setString(1,mentorId);
	    	   dbPreparedStatement.setInt(2,Integer.parseInt(mappingId));
	    	   dbResultSet = dbPreparedStatement.executeQuery();
	    	   mentorDetails = new ArrayList();
	    	   mentorBean = new MentorBean();   
	    	   while(dbResultSet.next()) {
	    		   menteeDto = new MenteeDTO();
	    		   menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
	    		   menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	    		   menteeDto.setMentordesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	    		   menteeDto.setMentorSerialNo(dbResultSet.getString("MENTOR_REGISTERED_SLNO"));
	    		   menteeDto.setMentorInterests(dbResultSet.getString("AREA_OF_INTEREST"));
	    		   menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
	    		   menteeDto.setMentorDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	    		   menteeDto.setStatusId(dbResultSet.getString("STATUS_ID"));
	    		   menteeDto.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	    		   menteeDto.setInitiatedDate(dbResultSet.getString("MENTEE_INITIATED_DATE"));
	    		   menteeDto.setApproveOrRejectionDate(dbResultSet.getString("APPROVAL_REJECTION_DATE"));
	    		   menteeDto.setMentorDialCom(dbResultSet.getString("GEHRDAILCOM"));
	    		   menteeDto.setMentoringPath(dbResultSet.getString("PATH_DESCRIPTION"));
	    		   menteeDto.setComments(dbResultSet.getString("COMMENTS"));
	    		   menteeDto.setMentorMail(dbResultSet.getString("MAIL"));
	    		   menteeDto.setStatusIdList(getDate(Integer.parseInt(mappingId),Integer.parseInt(menteeDto.getStatusId())));
	    		   menteeDto.setStatusId(getStatusId(ssoId,mentorId));
	      		   menteeDto.setStatusIdList(getStatusDetails(menteeDto, mentorId,ssoId));
	      		   if( menteeDto.getStatusIdList().size() == 1)
	        	   menteeDto.setStatusDescription(getStatusDescription(menteeDto.getStatusId())); 
	         	   //Get the list of education background 
	 	 		  ArrayList arrEBList = mentorBean.getEducationBackground(Integer.parseInt(menteeDto.getMentorSerialNo()),dataSource);
	 	 		  menteeDto.setArrEBList(arrEBList);
	 	 		 // menteeDto.setEducationRowCount(arrEBList.size()+"");
	 	 		  System.out.println("arrEBList.size() :" + arrEBList.size());
	 	 	  // Get the list of Rols and position hold in/outside GE
	 	 		  ArrayList arrRPList = mentorBean.getMentorRolsPositionInGE(Integer.parseInt(menteeDto.getMentorSerialNo()),dataSource);
	 	 		  menteeDto.setArrRPList(arrRPList);
	 	 		  //menteeDto.setRoleRowCount(arrRPList.size()+"");
	 	 		  System.out.println("arrRPList.size() :" + arrRPList.size());
	   			  mentorDetails.add(menteeDto);
	    	   }
	      }catch(Exception ex) {
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		  }
		  return mentorDetails;
	  }
	  public boolean getMenteeUnderMentorDetails(String ssoId,MentorProfileForm objForm) throws Exception
	  {
		  ArrayList mentorList = null;
		  Connection dbConnection = null;
	      PreparedStatement dbPreparedStatement = null;
	      ResultSet dbResultSet = null;
	      MenteeDTO menteeDto = null;
	      boolean queryStatus = false;
	      
	      String mentorName = null;
	      String mentorStatus = null;
	      StringBuffer mainQuery = new StringBuffer();
	      
	   
	      try{
	    	  dbConnection = dataSource.getConnection();
	          
	    	  mentorName = objForm.getMentorName();
	          mentorStatus = objForm.getMentorStatus();
	    	  
	          mainQuery.append("SELECT A.SSOID, A.GEHRFULLNAME,A.GEBUSINESSUNIT,A.GEHRPOSITIONTITLE,");
	          mainQuery.append("B.STRENGHS_COMPETENCIES, ");
	          mainQuery.append("B.PERSONAL_DETAILS,B.AREA_OF_INTEREST,C.PATH_DESCRIPTION,D.STATUS_DESCRIPTION,E.MENTOR_MENTEE_MAPPING_ID, E.STATUS_ID FROM MENTOR_SPODA_EMPLOYEE A, "); 
	          mainQuery.append("MENTOR_REGISTERED_MENTORS B,MENTOR_MENTORING_PATH_MASTER C,MENTOR_STATUS_MASTER D,MENTOR_MENTOR_MENTEE_MAPPING E WHERE A.SSOID "); 
	          mainQuery.append(" IN(SELECT SECURE_DATA.DECRYPTDATA(MENTOR_SSOID) FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE SECURE_DATA.DECRYPTDATA(MENTEE_SSOID) = ? )"); 
	          mainQuery.append("AND E.MENTOR_MENTEE_MAPPING_ID = (SELECT MAX(MENTOR_MENTEE_MAPPING_ID) FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE SECURE_DATA.DECRYPTDATA(MENTEE_SSOID) = ? AND SECURE_DATA.DECRYPTDATA(MENTOR_SSOID) =A.SSOID) "); 
	          mainQuery.append(" AND C.PATH_ID = B.PATH_ID AND SECURE_DATA.DECRYPTDATA(B.MENTOR_SSOID) =A.SSOID");
	          if(mentorStatus!=null && !mentorStatus.equals("") && !mentorStatus.equals("0")) {
	          mainQuery.append(" AND E.STATUS_ID ="+mentorStatus+" ");
	          }
	          
	         // mainQuery.append(") AND SECURE_DATA.DECRYPTDATA(B.MENTOR_SSOID) IN(SELECT SECURE_DATA.DECRYPTDATA(MENTOR_SSOID) FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE SECURE_DATA.DECRYPTDATA(MENTEE_SSOID) = ? "); 
	          
	         // if(mentorStatus!=null && !mentorStatus.equals("") && !mentorStatus.equals("0")) {
		     //     mainQuery.append(" AND STATUS_ID ="+mentorStatus+" ");
	         // }
	          
	          //mainQuery.append(")AND A.SSOID = SECURE_DATA.DECRYPTDATA(B.MENTOR_SSOID) "); 
	       
	          mainQuery.append(" AND D.STATUS_ID = E.STATUS_ID AND SECURE_DATA.DECRYPTDATA(E.MENTEE_SSOID)=? ");
	           
	           if(mentorStatus.equals("0"))
	              mainQuery.append( "AND D.STATUS_ID IN(SELECT STATUS_ID FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE SECURE_DATA.DECRYPTDATA(MENTEE_SSOID)=? AND SECURE_DATA.DECRYPTDATA(MENTOR_SSOID)=SECURE_DATA.DECRYPTDATA(B.MENTOR_SSOID))"); 
	          
	          if(mentorName !=null && !mentorName.equals("")) {
	            mainQuery.append(" AND UPPER(A.GEHRFULLNAME) LIKE '%" + mentorName.toUpperCase().trim() +"%' ");
	          }
	          
	          if(mentorStatus.equals("0")) {
	        	  mainQuery.append(" ORDER BY D.STATUS_ID");
	          }
	          
	         //dbPreparedStatement =dbConnection.prepareStatement(MenteeQuery.APP_CURRENT_MENTOR_LIST_QUERY);
	          System.out.println(mainQuery.toString());
	          dbPreparedStatement =dbConnection.prepareStatement(mainQuery.toString());
	          dbPreparedStatement.setString(1,ssoId);
	          dbPreparedStatement.setString(2,ssoId);
	          dbPreparedStatement.setString(3,ssoId);
	          if(mentorStatus.equals("0"))
	           dbPreparedStatement.setString(4,ssoId);
	          
	          dbResultSet = dbPreparedStatement.executeQuery();
	          mentorList = new ArrayList();
	        
	          while( dbResultSet.next() ) {
	        	menteeDto = new MenteeDTO();
	           	
	        	menteeDto.setMentorId(dbResultSet.getString("SSOID"));
	        	menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
	           	menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
	           	menteeDto.setMentordesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
	           	//menteeDto.setMentorEducationDetails(dbResultSet.getString("EDUCATIONAL_BACKGROUND"));
	           	menteeDto.setMentorDetails(dbResultSet.getString("PERSONAL_DETAILS"));
	           	menteeDto.setMentorInterests(dbResultSet.getString("AREA_OF_INTEREST"));
	           	menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
	           	menteeDto.setMentorPathDesc(dbResultSet.getString("PATH_DESCRIPTION")) ;
	           	menteeDto.setStatusDescription(dbResultSet.getString("STATUS_DESCRIPTION"));
	           	menteeDto.setMappingId(dbResultSet.getString("MENTOR_MENTEE_MAPPING_ID"));
	           	menteeDto.setStatusId(dbResultSet.getString("STATUS_ID"));
	           	mentorList.add(menteeDto);
	        	  
	          }
	          objForm.setArrMentorList(mentorList);
	          queryStatus = true;
	          
	      }catch(Exception ex){
	    	  queryStatus = false;
	    	  ex.printStackTrace();
	      }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }	 
	      return queryStatus;
	  }
	  
	  public String mentorBands(String ssoId) throws Exception
	  {
		  String queryResult = null;
		  Connection dbConnection  = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  
		  try {
			  
			   dbConnection = dataSource.getConnection();
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_BANDS_LIST_QUERY);
			   dbPreparedStatement.setString(1,ssoId);
			   dbResultSet = dbPreparedStatement.executeQuery();
			   
			   while(dbResultSet.next()) {
				   queryResult = dbResultSet.getString("MENTOR_BANDS");
			   }
			   
		  }catch(Exception ex) {
			  ex.printStackTrace();
		  }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }	 
		  
		  return queryResult;
	  }
	
	  public String mentorLocationIds(String ssoId) throws Exception {
		  
		  String queryResult = null;
		  Connection dbConnection  = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  
		  try {
			  
			   dbConnection = dataSource.getConnection();
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_LOCATION_LIST_QUERY);
			   dbPreparedStatement.setString(1,ssoId);
			   dbPreparedStatement.setString(2,ssoId);
			   dbResultSet = dbPreparedStatement.executeQuery();
			   
			   while(dbResultSet.next()) {
				   queryResult = dbResultSet.getString("MENTOR_FROM_LOCATION_ID");
			   }
			   
		  }catch(Exception ex) {
			  ex.printStackTrace();
		  }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }	 
		  
		  return queryResult;
	  }
	  
	  public int requiredNumberofMentees(String mentorId) throws Exception {
		  int queryResult = 0;
		  Connection dbConnection  = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  
		  try {
			  
			   dbConnection = dataSource.getConnection();
			   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_REQUIRED_MENTEES_QUERY);
			   dbPreparedStatement.setString(1,mentorId);
			   dbResultSet = dbPreparedStatement.executeQuery();
			   
			   while(dbResultSet.next()) {
				   queryResult = Integer.parseInt(dbResultSet.getString("NO_OF_MENTEES_REQUIRED"));
			   }
			   
		  }catch(Exception ex) {
			  ex.printStackTrace();
		  }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }	 
		  
		  return queryResult;
	  }
	  
	  public ArrayList  getStatusDescription() throws Exception {
          ArrayList list                        = null;
          Connection dbConnection               = null;
          PreparedStatement dbPreparedStatement = null;
          ResultSet dbResultSet                 = null;
          HelperBean oBean                      = null;
          
          try {
        	  dbConnection = dataSource.getConnection();
        	  list = new ArrayList();
        	  dbPreparedStatement =dbConnection.prepareStatement(MenteeQuery.APP_STATUSDESCRIPTION_LIST_QUERY);
        	  dbResultSet = dbPreparedStatement.executeQuery();
        	  
        	  while(dbResultSet.next()) {
        		  oBean = new HelperBean();
        		  oBean.setFieldValue1(dbResultSet.getString("STATUS_ID"));
        		  oBean.setFieldValue2(dbResultSet.getString("STATUS_DESCRIPTION"));
        		  list.add(oBean);
        	  }
          }catch(Exception ex) {
        	ex.printStackTrace();
          }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }	 
          
          return list;
	  }
	  
	  public String getStatusId(String ssoId, String mentorId) throws Exception
	  {
		  String statusId = null;
		  
		  Connection dbConnection = null;
		  PreparedStatement dbPreparedStatement = null;
		  ResultSet dbResultSet = null;
		  
		  try {
			    dbConnection = dataSource.getConnection();
 			    dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_STATUS_ID_QUERY);
 			    dbPreparedStatement.setString(1,ssoId);
 			    dbPreparedStatement.setString(2,mentorId);
 			    dbResultSet = dbPreparedStatement.executeQuery();
 			    
 			    if(dbResultSet.next()) {
 			    	statusId = dbResultSet.getString("STATUS_ID");
 			    }
 			    else statusId = "";
 			    
			    
		  }catch(Exception ex) {
			  ex.printStackTrace();
		  }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	      }
		  return statusId;
	  }

   public ArrayList getMentorProfile(String ssoId,String mentorId) throws Exception {
	 ArrayList mentorList = null;
	 Connection dbConnection = null;
	 PreparedStatement dbPreparedStatement = null;
	 ResultSet dbResultSet = null;
	 MenteeDTO menteeDto = null; 
	 MentorBean  mentorBean = null;

	 try {
		 
		 mentorList  = new ArrayList();
		 mentorBean = new MentorBean();
		 
		 dbConnection = dataSource.getConnection();
		 dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_MENTOR_DETAILS_LIST_QUERY1);
		 dbPreparedStatement.setString(1,mentorId);
		 dbResultSet = dbPreparedStatement.executeQuery();
		 System.out.println("Query:"+MenteeQuery.APP_MENTOR_DETAILS_LIST_QUERY1);
		 
		 while(dbResultSet.next()) {
		   menteeDto = new MenteeDTO();
  		   menteeDto.setMentorName(dbResultSet.getString("GEHRFULLNAME"));
  		   menteeDto.setMentorBusiness(dbResultSet.getString("GEBUSINESSUNIT"));
  		   menteeDto.setMentordesignation(dbResultSet.getString("GEHRPOSITIONTITLE"));
  		   menteeDto.setMentorDialCom(dbResultSet.getString("GEHRDAILCOM"));
  		   menteeDto.setMentorMail(dbResultSet.getString("MAIL"));
  		   menteeDto.setMentorSerialNo(dbResultSet.getString("MENTOR_REGISTERED_SLNO"));
  		   menteeDto.setMentorInterests(dbResultSet.getString("AREA_OF_INTEREST"));
  		   menteeDto.setMentorStrengths(dbResultSet.getString("STRENGHS_COMPETENCIES"));
  		   menteeDto.setMentorDetails(dbResultSet.getString("PERSONAL_DETAILS"));
  		   menteeDto.setMentoringPath(dbResultSet.getString("PATH_DESCRIPTION"));
  		   menteeDto.setStatusId(getStatusId(ssoId,mentorId));
  		   menteeDto.setStatusIdList(getStatusDetails(menteeDto, mentorId,ssoId));
  		   if( menteeDto.getStatusIdList().size() == 1)
  		   menteeDto.setStatusDescription(getStatusDescription(menteeDto.getStatusId())); 
  		  
  		   //Get the list of education background 
	 		  ArrayList arrEBList = mentorBean.getEducationBackground(Integer.parseInt(menteeDto.getMentorSerialNo()),dataSource);
	 		  menteeDto.setArrEBList(arrEBList);
	 		 // menteeDto.setEducationRowCount(arrEBList.size()+"");
	 		  System.out.println("arrEBList.size() :" + arrEBList.size());
	 	  // Get the list of Rols and position hold in/outside GE
	 		  ArrayList arrRPList = mentorBean.getMentorRolsPositionInGE(Integer.parseInt(menteeDto.getMentorSerialNo()),dataSource);
	 		  menteeDto.setArrRPList(arrRPList);
	 		  //menteeDto.setRoleRowCount(arrRPList.size()+"");
	 		  System.out.println("arrRPList.size() :" + arrRPList.size());
  		      mentorList.add(menteeDto);
		 }
	 }catch(Exception ex) {
		  ex.printStackTrace();
	  }finally {
		  try {
			  if(dbResultSet!=null) {
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
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
     }
	 return mentorList; 
   }
   
   public boolean isMentorExist( String mentorId) throws Exception
   {
	   boolean queryStatus = false;
	   
	   Connection dbConnection = null;
	   PreparedStatement dbPreparedStatement = null;
	   ResultSet dbResultSet = null;
	   
	   try {
		    dbConnection = dataSource.getConnection();
		    dbPreparedStatement = dbConnection.prepareStatement("SELECT SECURE_DATA.DECRYPTDATA(MENTOR_SSOID) FROM MENTOR_REGISTERED_MENTORS WHERE SECURE_DATA.DECRYPTDATA(MENTOR_SSOID)=?");
		    dbPreparedStatement.setString(1,mentorId);
		    dbResultSet = dbPreparedStatement.executeQuery();
		    
		    if( dbResultSet.next() )  queryStatus = true;
	   }catch(Exception ex) {
		   ex.printStackTrace();
		   queryStatus = false;
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   
	   return queryStatus;
   }
  
   public boolean isStatusIdExist( String statusId) throws Exception
   {
	   boolean queryStatus = false;
	   
	   Connection dbConnection = null;
	   PreparedStatement dbPreparedStatement = null;
	   ResultSet dbResultSet = null;
	   
	   try {
		    dbConnection = dataSource.getConnection();
		    dbPreparedStatement = dbConnection.prepareStatement("SELECT STATUS_ID FROM MENTOR_STATUS_MASTER WHERE STATUS_ID=?");
		    dbPreparedStatement.setString(1,statusId);
		    dbResultSet = dbPreparedStatement.executeQuery();
		    
		    if( dbResultSet.next() )  queryStatus = true;
	   }catch(Exception ex) {
		   ex.printStackTrace();
		   queryStatus = false;
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   
	   return queryStatus;
   }
   
   public boolean isMappingIdExist( String mappingId) throws Exception
   {
	   boolean queryStatus = false;
	   
	   Connection dbConnection = null;
	   PreparedStatement dbPreparedStatement = null;
	   ResultSet dbResultSet = null;
	   
	   try {
		    dbConnection = dataSource.getConnection();
		    dbPreparedStatement = dbConnection.prepareStatement("SELECT MENTOR_MENTEE_MAPPING_ID FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE MENTOR_MENTEE_MAPPING_ID=?");
		    dbPreparedStatement.setString(1,mappingId);
		    dbResultSet = dbPreparedStatement.executeQuery();
		    
		    if( dbResultSet.next() )  
		     	queryStatus = true;
	   }catch(Exception ex) {
		   ex.printStackTrace();
		   queryStatus = false;
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   
	   return queryStatus;
   }
   
   public ArrayList getStatusDetails(MenteeDTO menteeDto, String mentorId,String ssoId) throws Exception
   {
	   Connection dbConnection = null;
	   PreparedStatement dbPreparedStatement = null;
	   ResultSet dbResultSet = null;
	   ArrayList list = null;
	   HelperBean oBean = null;
	   
	   try {
		   list  = new ArrayList();
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_STATUS_DETAILS_OF_MENTEE);
		   dbPreparedStatement.setString(1,ssoId);
		   dbPreparedStatement.setString(2,mentorId);
		   dbResultSet = dbPreparedStatement.executeQuery();
		   
		   while(dbResultSet.next()) {
			   oBean =new HelperBean();
			   oBean.setFieldValue1(dbResultSet.getString(1));
			   oBean.setFieldValue2(dbResultSet.getString(2));
			   oBean.setFieldValue3(dbResultSet.getString(3));
			   oBean.setFieldValue4(dbResultSet.getString(4));
			   oBean.setFieldValue5(dbResultSet.getString(5));
			   oBean.setMappingId(dbResultSet.getString(6));
			   
			   if(oBean.getFieldValue5().equals("6") || oBean.getFieldValue5().equals("5")) {
			   oBean.setActionName(getStatusDescription("4"));
			   oBean.setMenteeInitiatedDate(setRelationInitiatedList(oBean.getMappingId()));
			   }	   
			   if(oBean.getFieldValue5().equals("7")) {
				   oBean.setSunsetInitiatedList(getSunsetDetails(ssoId,oBean.getMappingId()));
			   }
			   if(oBean.getFieldValue5().equals("8")) {
				   oBean.setActionName(getStatusDescription("7"));
				   oBean.setSunsetInitiatedList(getSunsetDetails(ssoId,oBean.getMappingId())); 
				   oBean.setSunsetAcceptedList(getSunsetStatusDetails(ssoId,oBean.getMappingId())) ;
			   }
			   list.add(oBean);
			   menteeDto.setStatusDescription(oBean.getFieldValue1());
		   }
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return list;
	    
   }
   
   public String getStatusDescription(String statusId) throws Exception {
	   String statusDesc = null;
	   Connection dbConnection = null;
	   PreparedStatement dbPreparedStatement = null;
	   ResultSet dbResultSet = null;
	   
	   try {
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_STATUS_DESC_QUERY);
		   dbPreparedStatement.setInt(1,Integer.parseInt(statusId));
		   dbResultSet = dbPreparedStatement.executeQuery();
		   
		   if( dbResultSet.next() )  statusDesc = dbResultSet.getString(1);
		   
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return statusDesc;
   }

   public ArrayList getDate(int mappingid,int StatusId) throws Exception {
		Connection con 		  = null;
		ResultSet rs 		  = null;
		PreparedStatement ps  = null;
		ArrayList arrDateList = null;
		HelperBean Obj     = null;
		
		try {
			 con = dataSource.getConnection();			 
			 System.out.println("StatusId  at BEAN Class :  "  +StatusId);
			 System.out.println("mappingid at BEAN Class :  " + mappingid);
			 arrDateList = new ArrayList();			 
			 
			 for(int i = 4; i <= StatusId;i++ ) {
				 System.out.println("Value of I - (Status ID) :" + i );
				 String sql	= " ";
				 if(i == 4) {
					  sql = "select STATUS_DESCRIPTION  from MENTOR_STATUS_MASTER where status_id  = " + i;
					  System.out.println(sql);
						 ps = con.prepareStatement(sql);				 
						 rs = ps.executeQuery();
						 if(rs.next()) {
							 Obj = new HelperBean();					 
							 Obj.setActionName(rs.getString(1));
							 System.out.println("Action Name 	 : " + rs.getString(1));
							 sql = " select TO_CHAR(MENTEE_INITIATED_DATE,'DD-Mon-YY')" +
						     " from MENTOR_MENTOR_MENTEE_MAPPING where MENTOR_MENTEE_MAPPING_ID="+mappingid;
							 System.out.println(sql);
							 ps = con.prepareStatement(sql);				 
							 rs = ps.executeQuery();
							 if(rs.next()) {
								 Obj.setActionDate(rs.getString(1));
								 Obj.setActionComments("");								 
								 System.out.println("Action Name 	 : " + rs.getString(1));
								 System.out.println("Action Comments : ");
							 }							 
							 arrDateList.add(Obj);
						 }
				 } else if((i==5 && i==StatusId)|| (i == 6 && i==StatusId) || (i==5 && i !=6  && StatusId > 6)) {
					  sql = "select STATUS_DESCRIPTION  from MENTOR_STATUS_MASTER where status_id  = " + i;
					  System.out.println(sql);
						 ps = con.prepareStatement(sql);				 
						 rs = ps.executeQuery();
						 if(rs.next()) {
							 Obj = new HelperBean();					 
							 Obj.setActionName(rs.getString(1));
							 System.out.println("Action Name 	 : " + rs.getString(1));
							 sql = " select TO_CHAR(APPROVAL_REJECTION_DATE,'DD-Mon-YY'),comments" +
						     " from MENTOR_MENTOR_MENTEE_MAPPING where MENTOR_MENTEE_MAPPING_ID="+mappingid;
							 System.out.println(sql);
							 ps = con.prepareStatement(sql);				 
							 rs = ps.executeQuery();
							 if(rs.next()) {
								 Obj.setActionDate(rs.getString(1));
								 Obj.setActionComments(rs.getString(2));								 
								 System.out.println("Action Name 	 : " + rs.getString(1));
								 System.out.println("Action Comments : " + rs.getString(2));								 
							 }							 
							 arrDateList.add(Obj);
						 }
				 } else if(i== 7 ) {
					  sql = "select STATUS_DESCRIPTION  from MENTOR_STATUS_MASTER where status_id  = " + i;
					  System.out.println(sql);
						 ps = con.prepareStatement(sql);				 
						 rs = ps.executeQuery();
						 if(rs.next()) {
							 Obj = new HelperBean();					 
							 Obj.setActionName(rs.getString(1));
							 System.out.println("Action Name 	 : " + rs.getString(1));
							 sql = " select TO_CHAR(SUNSET_INITIATED_DATE,'DD-Mon-YY'),COMMENTS_BY_MENTEE " +
							 		" from MENTOR_SUNSET_RELATIONSHIP  WHERE SUNSET_RELATIONSHIP_ID = " + mappingid;
							 System.out.println(sql);
							 ps = con.prepareStatement(sql);				 
							 rs = ps.executeQuery();
							 if(rs.next()) {
								 Obj.setActionDate(rs.getString(1));
								 Obj.setActionComments(rs.getString(2));								 
								 System.out.println("Action Name 	 : " + rs.getString(1));
								 System.out.println("Action Comments : " + rs.getString(2));								 
							 }							 
							 arrDateList.add(Obj);
						 }
				 } else if(i==8 || i == 9) {
					  sql = "select STATUS_DESCRIPTION  from MENTOR_STATUS_MASTER where status_id  = " + i;
					  System.out.println(sql);
						 ps = con.prepareStatement(sql);				 
						 rs = ps.executeQuery();
						 if(rs.next()) {
							 Obj = new HelperBean();					 
							 Obj.setActionName(rs.getString(1));
							 System.out.println("Action Name 	 : " + rs.getString(1));
							 sql = " select TO_CHAR(MENTOR_SUNSET_DATE,'DD-Mon-YY'),COMMENTS_BY_MENTOR " +
							 		" from MENTOR_SUNSET_RELATIONSHIP WHERE SUNSET_RELATIONSHIP_ID = " + mappingid;
							 System.out.println(sql);	
							 ps = con.prepareStatement(sql);				 
							 rs = ps.executeQuery();
							 if(rs.next()) {
								 Obj.setActionDate(rs.getString(1));
								 Obj.setActionComments(rs.getString(2));								 
								 System.out.println("Action Name 	 : " + rs.getString(1));
								 System.out.println("Action Comments : " + rs.getString(2));								 
							 }							 
							 arrDateList.add(Obj);
						 }
			    }				
			 }
			 
			 System.out.println("arrDateList.size() :" + arrDateList.size());		 
	
			 
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				  if(rs!=null){
					  rs.close();
					  rs = null;
				  }
				  if(ps!=null){
					  ps.close();
					  ps = null;
				  }
				  if(con!=null){
					  con.close();
					  con = null;
				  }				  
				} catch(SQLException sqlex) {
					throw sqlex;
				}			
		}
		return arrDateList;
	}
   
   
   public ArrayList relationLocationBusinessList() throws Exception {
	   ArrayList list                        =  null;
	   HelperBean oBean                      =  null;
	   Connection dbConnection               =  null;
	   PreparedStatement dbPreparedStatement =  null;
	   ResultSet dbResultSet                 =  null;
	   
	   try {
		   list = new ArrayList();
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement("SELECT LOCATION_ID,BUSINESS_NAME FROM MENTOR_SPODA_BUSINESS_MAPPING GROUP BY BUSINESS_NAME,LOCATION_ID ");
		   dbResultSet = dbPreparedStatement.executeQuery();
		   
		   while(dbResultSet.next()) {
			   oBean = new HelperBean();
			   oBean.setFieldValue1(dbResultSet.getString(1));
			   oBean.setFieldValue2(dbResultSet.getString(2));
			   list.add(oBean);
		   }
		   
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return list;
   }
   
   public ArrayList getSunsetDetails(String ssoId,String mappingId) throws Exception {
	   ArrayList  list                           =  null;
	   HelperBean oBean                      =  null;
	   Connection dbConnection               =  null;
	   PreparedStatement dbPreparedStatement =  null;
	   ResultSet dbResultSet                 =  null;
	   
	   try {
		   list      = new ArrayList();
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement("SELECT TO_CHAR(A.SUNSET_INITIATED_DATE,'DD-MON-YY'), A.COMMENTS_BY_MENTEE FROM MENTOR_SUNSET_RELATIONSHIP A WHERE SECURE_DATA.DECRYPTDATA(A.SUNSET_INITIATED_BY)= ? AND A.SUNSET_RELATIONSHIP_ID= ?");
		   dbPreparedStatement.setString(1,ssoId);
		   dbPreparedStatement.setInt(2,Integer.parseInt(mappingId));
		   dbResultSet  = dbPreparedStatement.executeQuery();
		   
		   while(dbResultSet.next()) {
			   oBean  = new HelperBean();
		       oBean.setSunsetInitiatedDate(dbResultSet.getString(1));
		       oBean.setActionComments(dbResultSet.getString(2));
		       list.add(oBean);
		   }    
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return list;
   }
   public ArrayList getSunsetStatusDetails(String ssoId,String mappingId)throws Exception {
	   ArrayList list                        =  null;
	   HelperBean oBean                      =  null;
	   Connection dbConnection               =  null;
	   PreparedStatement dbPreparedStatement =  null;
	   ResultSet dbResultSet                 =  null;
	   
	   try {
		   list        = new ArrayList();
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement("SELECT TO_CHAR(A.MENTOR_SUNSET_DATE,'DD-MON-YY'), A.COMMENTS_BY_MENTOR FROM MENTOR_SUNSET_RELATIONSHIP A WHERE SECURE_DATA.DECRYPTDATA(A.SUNSET_INITIATED_BY)= ? AND A.SUNSET_RELATIONSHIP_ID= ?");
		   dbPreparedStatement.setString(1,ssoId);
		   dbPreparedStatement.setInt(2,Integer.parseInt(mappingId));
		   dbResultSet  = dbPreparedStatement.executeQuery();
		   
		   while(dbResultSet.next()) {
			   oBean  = new HelperBean();
		       oBean.setSunsetAcceptedDate(dbResultSet.getString(1));
		       oBean.setActionComments(dbResultSet.getString(2));
		       list.add(oBean);
		   }
		   
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return list;
   }
   
   public String setRelationInitiatedList(String mappingId) throws Exception {
	   String mentee_initiatedDate           =  null;
	   Connection dbConnection               =  null;
	   PreparedStatement dbPreparedStatement =  null;
	   ResultSet dbResultSet                 =  null;
	   
	   try {
		
		   dbConnection = dataSource.getConnection();
		   dbPreparedStatement = dbConnection.prepareStatement("SELECT  TO_CHAR(MENTEE_INITIATED_DATE,'DD-MON-YY') FROM MENTOR_MENTOR_MENTEE_MAPPING WHERE MENTOR_MENTEE_MAPPING_ID =?");
		   dbPreparedStatement.setInt(1,Integer.parseInt(mappingId));
		   dbResultSet  = dbPreparedStatement.executeQuery();
		   
		   while(dbResultSet.next()) {
		
		       mentee_initiatedDate  = dbResultSet.getString(1);
		       
		   }
		   
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return mentee_initiatedDate;
   }
   
   public boolean getMenteesList(int mentorSlno,NominateMentorsFormBean objForm) throws Exception {
	   boolean queryResult                      = false;
	   ArrayList list                           = null;
	   HelperBean oBean                         = null;
	   Connection dbConnection                  = null;
	   PreparedStatement dbPreparedStatement    = null;
	   ResultSet dbResultSet                    = null;
	   try {
		       list                             = new ArrayList();
		       dbConnection                     = dataSource.getConnection();
		       dbPreparedStatement              = dbConnection.prepareStatement(MenteeQuery.APP_LIST_MENTEES_QUERY);
		       dbPreparedStatement.setInt(1,mentorSlno);
		       dbResultSet                      = dbPreparedStatement.executeQuery();
		       
		       while( dbResultSet.next() ) {
		    	   oBean  = new HelperBean();
		    	   oBean.setMenteeId(dbResultSet.getString(1));
		    	   oBean.setFieldValue1(getStatusDescription(dbResultSet.getString(2)));
		    	   oBean.setFieldValue2(dbResultSet.getString(3));
		    	   oBean.setFieldValue3(dbResultSet.getString(4));
		    	   oBean.setFieldValue4(dbResultSet.getString(5));
		    	   oBean.setFieldValue5(dbResultSet.getString(6));
		    	   list.add(oBean);
		       }
		       objForm.setArrMenteeList(list);
		       queryResult = true;
       }catch(Exception ex){
		   ex.printStackTrace();
		   queryResult = false;
	   }finally {
			  try {
				  if(dbResultSet!=null) {
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
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
	     }
	   return queryResult;
   }
   
}