<html>
<head>
<title>Enterprise Resource Information System</title>
</head>
<body>
<form method="POST" action="./updResumeServlet">


<div style="background-color: #C0C0C0">
  <p align="center"><b>Enterprise Resource Information System</b></div>
  <p align="center"> <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    <u>UPDATE RESUME </u></b>
  <p align="center"> APPLICANT ID&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
    <input type="text" name="T1" size="20" value='<%= session.getAttribute("appid").toString() %>' style="border-style: solid; border-width: 1">
  <p align="center"> ATTACH RESUME 
    <input type="file" name="skills" size="20" style="border-style: solid; border-width: 1">
  </p>
  <p align="center"> 
    <input type="submit" value="add" name="B4"   style="border-style: solid; border-width: 1">
  </p>
  <p align="center"><a href="updskill-resume.html">BACK</a></p>
</form>
</body>

</html>
