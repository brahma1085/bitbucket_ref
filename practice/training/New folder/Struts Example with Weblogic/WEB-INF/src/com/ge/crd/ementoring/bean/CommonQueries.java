package com.ge.crd.ementoring.bean;


import javax.sql.DataSource;

import com.ge.crd.ementoring.util.MenteeQuery;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CommonQueries {

	DataSource ds = null;
	public CommonQueries() {
	     		}
	public CommonQueries(DataSource ds) {
       this.ds = ds;
    }
	   
	public ArrayList getBand() throws Exception {
	  ArrayList band = new ArrayList();
	  HashMap colsList = null;
	  Connection dbConnection = null;
	  PreparedStatement dbPreparedStatement = null;
	  ResultSet dbResultSet = null;
	  int columnCount = 0;
	  MenteeRegDTO mregDTO = null;
	  try {
		  dbConnection = ds.getConnection();
		  dbPreparedStatement = dbConnection.prepareStatement(" SELECT BAND_ID, SECURE_DATA.DECRYPTDATA(BAND_DESCRIPTION) BAND_DESCRIPTION FROM MENTOR_BAND_MASTER ");			
		  dbResultSet = dbPreparedStatement.executeQuery();
		  columnCount = dbResultSet.getMetaData().getColumnCount();
          while (dbResultSet.next()) {
        	  mregDTO = new MenteeRegDTO();
        	  mregDTO.setBandID(dbResultSet.getString("BAND_ID"));
        	  mregDTO.setBandDesc(dbResultSet.getString("BAND_DESCRIPTION"));
        	  band.add(mregDTO);
              /*colsList = new HashMap();
              for (int index = 1; index <= columnCount; index++) {
                  if (dbResultSet.getString(index) != null) {
                       colsList.put("BAND_ID",dbResultSet.getString("BAND_ID"));
                	   colsList.put("BAND_DESCRIPTION",dbResultSet.getString("BAND_DESCRIPTION"));
                       //colsList.add(dbResultSet.getString(index));
                  } else {
                      colsList.put(new Integer(index),"not provided");
                  }
              }
              band.add(colsList);*/
        	  
        	  
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
	  
	  return band;
	}
	
	
	public boolean chekMenteeStatus(String ssoId) throws Exception
	{
		String status = null;
		boolean queryStatus = false;
		Connection dbConnection = null;
		PreparedStatement dbPreparedStatement = null;
		ResultSet dbResultSet = null;
		
		try {
			dbConnection  = ds.getConnection();
			dbPreparedStatement = dbConnection.prepareStatement(MenteeQuery.APP_STATUS_ID_STATUS_QUERY);
			dbPreparedStatement.setString(1,ssoId);
			dbResultSet = dbPreparedStatement.executeQuery();
			
			if( dbResultSet.next() ) {
				status = dbResultSet.getString(1);
			if(Integer.parseInt(status)==11)
			  queryStatus = true;
			else 
				queryStatus = false;
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
		return queryStatus;
	}
 
		
	public ArrayList getSingleRowList(String strSqlQuery,int arg) throws SQLException {
        ArrayList singleRowList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int columnCount = 0;
        try {
            con = ds.getConnection();
        	pstmt = con.prepareStatement(strSqlQuery);
        	pstmt.setInt(1,arg);
            rs = pstmt.executeQuery();
            columnCount = rs.getMetaData().getColumnCount();
            if (rs.next()) {
                singleRowList = new ArrayList();
                for (int index = 1; index <= columnCount; index++) {
                    if (rs.getString(index) != null) {
                        singleRowList.add(rs.getString(index));
                    } else {
                        singleRowList.add("");
                    }
                }
            }

        } catch (SQLException se) {
            System.out.println("Query is " + strSqlQuery);
            throw se;
        } catch (Exception ex) {
            ex.printStackTrace();     
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se) {
              throw se;    
            }
        }
        return singleRowList;
    }
 
	 public ArrayList getRowsList(String strSqlQuery) throws SQLException {
	        ArrayList rowsList = new ArrayList();
	        ArrayList colsList = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
            Connection con = null;
	        int columnCount = 0;
	        try {
	        	con = ds.getConnection();
	            pstmt = con.prepareStatement(strSqlQuery);
	            rs = pstmt.executeQuery();
	            columnCount = rs.getMetaData().getColumnCount();
	            while (rs.next()) {
	                colsList = new ArrayList();
	                for (int index = 1; index <= columnCount; index++) {
	                    if (rs.getString(index) != null) {
	                        colsList.add(rs.getString(index));
	                    } else {
	                        colsList.add("");
	                    }
	                }
	                rowsList.add(colsList);
	            }

	        } catch (SQLException se) {
	            System.out.println("Query is " + strSqlQuery);
	            throw se;
	        } catch (Exception ex) {
	        } finally {
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (pstmt != null) {
	                    pstmt.close();
	                }
	            } catch (SQLException se) {
	            }
	        }
	        return rowsList;
	    }

	 public ArrayList getRowsList(String strSqlQuery,int arg) throws SQLException {
	        ArrayList rowsList = new ArrayList();
	        ArrayList colsList = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
         Connection con = null;
	        int columnCount = 0;
	        try {
	        	con = ds.getConnection();
	            pstmt = con.prepareStatement(strSqlQuery);
	            rs = pstmt.executeQuery();
	            columnCount = rs.getMetaData().getColumnCount();
	            while (rs.next()) {
	                colsList = new ArrayList();
	                for (int index = 1; index <= columnCount; index++) {
	                    if (rs.getString(index) != null) {
	                        colsList.add(rs.getString(index));
	                    } else {
	                        colsList.add("");
	                    }
	                }
	                rowsList.add(colsList);
	            }

	        } catch (SQLException se) {
	            System.out.println("Query is " + strSqlQuery);
	            throw se;
	        } catch (Exception ex) {
	        } finally {
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (pstmt != null) {
	                    pstmt.close();
	                }
	            } catch (SQLException se) {
	            }
	        }
	        return rowsList;
	    }


}
