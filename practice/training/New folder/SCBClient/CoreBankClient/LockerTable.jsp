<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="masterObject.lockers.LockerDetailsObject" %>
<%@taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>
<html>
<head>
<style type="text/css">
.alloted{
 background-color:"pink"
}
.availabel{
background-color:"lightblue"
}



</style>


<title>Insert title here</title>
 <script type="text/javascript">
 
     function sets( ter ){
    
          
           
         		  document.forms[0].focusType.value=ter;
         		  document.forms[0].selectedLock.value="";
       	 		  document.forms[0].submit();
       	 		  
         }
	
	
 	function createTable(){
 	
 	
 		
    <%
    			
    LockerDetailsObject[]    lockerDetailsObjects = (LockerDetailsObject[])request.getAttribute("TableDetail");
    		
    	
    %>
 			
 			
 			<%  if (lockerDetailsObjects != null){ 
 				
  			%>
 
 			var rows =  new Array(<%=lockerDetailsObjects.length%>);
 			
 			
 			
 		<%   for( int a =0; a<lockerDetailsObjects.length; a++) { %>
 				
 				rows[<%=a%>]= new Array( <%=lockerDetailsObjects[a].getCols()%>);
 				
 		<%}%>
 			
 			<%   LockerDetailsObject[] lockerspecific =(LockerDetailsObject[])request.getAttribute("lockerSpecific"); %> 
 			<% if( lockerspecific!=null ){ %>
 			
 			
 			
 			<% System.out.println("***Locker specific length***"+lockerspecific.length); %>
 		
 			
 			
 			
 			
 			<%! int k = 0;%>
 			
 			<% k = 0;%>
 			<% System.out.println("k on top !!!@@@!!!@@"+k); %>
 			
 			<% for (int i =0; i<lockerDetailsObjects.length; i++){   
 			
 					System.out.println("iiiiiiii valueeee"+i);
 						
 					for ( int j =0; j<	lockerDetailsObjects[i].getCols(); j++){ %>
 					
 					
 					<% System.out.println("jjjjjjjjjjjjjjjjjj"+j); %>
 					
 						rows[<%=i%>][<%=j%>] =  "<%=(lockerspecific[k].getLockerNo()+"-"+lockerspecific[k].getLockerType()+"-"+lockerspecific[k].getLockerStatus())%>";
 						<%k++;
							System.out.println("MY K valu %%%"+k); 						
 						%>
 				 			
 			<%}%>
 			
 			
 			<%}%>
 			
 			
 			
 
 				ld(rows);
 			<%} }%>
 					
 					
 				}
 	
 	function  ld(ar){
 	
 				var div_ele = document.getElementById("table");
 				
 				 var dd = "";
 	           	 var ta = "";
 				
              for (  var j= 0; j< ar.length; j++) {

                  dd="<tr>";
              for ( var k=0; k<ar[j].length ; k++  ){

                  
                  var split1=ar[j][k].split('-');
 			      var split2=split1[0]+split1[1];
 			      var status=split1[2];
 			     if(status=="T"){ 
 	              dd+="<td><input type='text' value='"+split2+"' id='"+split2+"' size='"+3+"' class='alloted' onclick='getDetalis(this)' ></td>";
 			}
 			else {
 		
 			 dd+="<td><input type='text' value='"+split2+"' id='"+split2+"' size='"+3+"' class='availabel' onclick='getDetalis(this)' ></td>";
 			}

              }

                dd+="</tr>";
               ta+=dd;
                dd="";

       }
           div_ele.innerHTML+="<table>"+ ta+"</table>";

   }
   
   
   function getDetalis(ids){
   
   		
   		document.forms[0].selectedLock.value=ids.value;
   		document.forms[0].submit();
   		
   
   }
 	
 	function generateCabs(combinedVal){
 	
 	}
 </script>


</head>
<body bgcolor="#f3f4c8"  onload="createTable()">
<center><h2 class="h2"><i><bean:message key="lable.table"></bean:message> </i></h2></center>



<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/LKTableLink?pageId=9007">
<table border="2" class="txtTable">


<tr><td>
LkTypes:<html:select property="lkTypes" styleClass="formTextFieldWithoutTransparent" onchange="return sets(1)">
	
		<c:forEach var="lktypes" items="${requestScope.lktypes}">              
 	     <html:option value="${lktypes}"> <c:out value="${lktypes}"></c:out>  </html:option>
		</c:forEach>
</html:select>
</td>
<%! int[] cabDetails=null; %>

<% cabDetails=(int[])request.getAttribute("cabDetails"); 
	System.out.println("cab detailsss"+cabDetails);
%>

<td><html:select property="cabTypes" styleClass="formTextFieldWithoutTransparent"  onblur="return sets(5)">
<% if(cabDetails!=null){
	
	for(int i=0;i<cabDetails.length;i++){
		System.out.println("AM I HERE");
		System.out.println("FETCHIN DETAILS"+cabDetails[i]);
	%>

 <html:option value="<%=Integer.toString(cabDetails[i]) %>" ><%=cabDetails[i]%> </html:option>
 
 <%}}%>

</html:select> </td>

</tr> 

</table>

<table class="txtTable">
<tr><td>
<div  id ="table" style="border: thin">


</div>
</td>
<td>
<%! LockerDetailsObject[] ldo; 
	LockerDetailsObject[] ldo2;
	String strIndexVal; 
	int intIndexVal; 
	String strIndexVal2; 
	int intIndexVal2;
%>


		<% ldo=(LockerDetailsObject[])request.getAttribute("panelDetails1");
		   ldo2=(LockerDetailsObject[])request.getAttribute("panelDetails2");
		   strIndexVal=(String)request.getAttribute("index1");
		   strIndexVal2=(String)request.getAttribute("index2");
		%>


<% if(ldo!=null && strIndexVal!=null && ldo2!=null){
	intIndexVal=Integer.parseInt(strIndexVal);
	intIndexVal2=Integer.parseInt(strIndexVal2);
	
%>
	
<table border="1" width="350" class="txtTable">

<tr><td>LockerNum</td>          <td><%=ldo[intIndexVal].getLockerNo() %></td></tr>
<tr><td>LockerType:</td>		<td><%=ldo[intIndexVal].getLockerType() %></td></tr>
<tr><td>Description:</td>		<td><%=ldo[intIndexVal].getDescription() %></td></tr>
<tr><td>KeyNum</td>			<td><%=ldo[intIndexVal].getKeyNo() %></td></tr>
<tr><td>Locker status</td>		<td><%=ldo[intIndexVal].getLockerStatus() %></td></tr>
<tr><td>SiezeInd</td>			<td><%=ldo[intIndexVal].getSiezeInd() %></td></tr>
<tr><td>Locker A/c Num</td>		<td><%=ldo[intIndexVal].getLockerAcNo() %></td></tr>
<tr><td>Locker A/c type</td>		<td><%=ldo[intIndexVal].getLockerAcType() %></td></tr>
<tr><td>Length</td>				<td><%=ldo2[intIndexVal2].getLockerLength()%></td>  </tr>
<tr><td>Depth</td>				<td><%=ldo2[intIndexVal2].getLockerDepth() %></td>  </tr>
<tr><td>Height</td>				<td><%=ldo2[intIndexVal2].getLockerHeight() %></td>  </tr>
<tr><td>Rent</td>				<td><%=String.valueOf(ldo2[intIndexVal2].getLockerRate())%></td>  </tr>

</table>
<%} %>
</td>
</tr>
</table>

<html:hidden property="selectedLock"></html:hidden>
<html:hidden property="focusType"></html:hidden>
<html:hidden property="hiddenIndex" ></html:hidden>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>