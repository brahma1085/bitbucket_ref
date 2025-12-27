
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
<head>
    <!--%%%%%%%%%%%% QuickMenu Styles [Keep in head for full validation!] %%%%%%%%%%%-->



    <!--<link rel="stylesheet" href="menu.css">
    --><!-- Core QuickMenu Code --><!--
    <script  type="text/javascript"  src="menu.js">
        </script>
-->
     <%-- <link rel="stylesheet" href="menu.css" type="text/css">--%>
   <%-- <h6 style="font:small-caps; font-style:normal;"> </h6>--%>
    <style type="text/css">

body{

		margin:0;
		/*background:url(goldfade.JPG);
		background-image:url(goldfade.JPG);*/
		/*//ckground-repeat:repeat;*/
        background-image:url("goldfade1.JPG");
        background:url("goldfade1.JPG");
        width:100%;
		height:100%;
		border:thick;

		border-bottom:#33CC66 thick solid;
		border-top:#33CC66 thick solid;

	}

	div#header{
		position:absolute;
		top:0;
		left:0;
		width:100%;
	}
	div#footer{
		position:absolute;
		top:90%;
		left:0;
		width:100%;
	}
	@media screen{
		body>div#header{
			position:fixed;
		}
		body>div#header{
			position:fixed;
		}
	}



/*!!!!!!!!!!! QuickMenu Core CSS [Do Not Modify!] !!!!!!!!!!!!!*/
.qmmc .qmdivider{display:block;font-size:1px;border-width:0px;border-style:solid;position:relative;z-index:1;}.qmmc .qmdividery{float:left;width:0px;}.qmmc .qmtitle{display:block;cursor:default;white-space:nowrap;position:relative;z-index:1;}.qmclear {font-size:1px;height:0px;width:0px;clear:left;line-height:0px;display:block;float:none !important;}.qmmc {position:relative;z-index:10;}.qmmc a, .qmmc li {float:left;display:block;white-space:nowrap;position:relative;z-index:1;}.qmmc div a, .qmmc ul a, .qmmc ul li {float:none;}.qmsh div a {float:left;}.qmmc div{visibility:hidden;position:absolute;}.qmmc li {z-index:auto;}.qmmc ul {left:-10000px;position:absolute;z-index:10;}.qmmc, .qmmc ul {list-style:none;padding:0px;margin:0px;}.qmmc li a {float:none}li:hover>ul{left:auto;}#qm0 ul {top:100%;}#qm0 ul li:hover>ul{top:0px;left:100%;}


/*!!!!!!!!!!! QuickMenu Styles [Please Modify!] !!!!!!!!!!!*/


	/* QuickMenu 0 */

	/*"""""""" (MAIN) Container""""""""*/
	#qm0
	{
		margin:0px;
		background-color:#daebd0;
        width: 900px;
    }


	/*"""""""" (MAIN) Items""""""""*/
	#qm0 a
	{
		padding:4px 5px 4px 5px;
		background-color:#cadab7;
		color:#222222;
		font-family:Arial;
       /* font-family:serif;*/
        /*font-style:oblique;*/
        font-size:10px;
       /* font-weight:bold;*/

        text-decoration:none;
		text-align:left;
		border-width:1px 0px 1px 1px;
		border-style:solid;
		border-color:#687a54;

    }


	/*"""""""" (MAIN) Hover State""""""""*/
	#qm0 a:hover
	{
		color:#222222;
	}


	/*"""""""" (MAIN) Active State""""""""*/
	body #qm0 .qmactive, body #qm0 .qmactive:hover
	{
		background-color:#c2e0a9;
	}


	/*"""""""" (SUB) Container""""""""*/
	#qm0 div, #qm0 ul
	{
		padding:5px;
		background-color:#dcedcd;
		border-width:1px;
		border-style:solid;
		border-color:#687a54;
	}


	/*"""""""" (SUB) Items""""""""*/
	#qm0 div a, #qm0 ul a
	{
		padding:3px 20px 3px 5px;
		color:#222222;
        /*color: #f5ab62*/
        font-size:9px;
		text-align:left;
		border-width:1px 0px;
		border-color:#dcedcd;
	}


	/*"""""""" (SUB) Hover State""""""""*/
	#qm0 div a:hover, #qm0 ul a:hover
	{
		background-color:#c2e0a9;
		color:#222222;
		border-color:#687a54;
	}


	/*"""""""" (SUB) Active State""""""""*/
	body #qm0 div .qmactive, body #qm0 div .qmactive:hover
	{
		border-color:#687a54;
	}
    </style>
    <%--<script type="text/javascript"
            src="menu.js"></script>
--%>


    <script language="JavaScript">
         var qm_si,qm_li,qm_lo,qm_tt,qm_th,qm_ts,qm_la,qm_ic,qm_ib;var qp="parentNode";var qc="className";var qm_t=navigator.userAgent;var qm_o=qm_t.indexOf("Opera")+1;var qm_s=qm_t.indexOf("afari")+1;var qm_s2=qm_s&&qm_t.indexOf("ersion/2")+1;var qm_s3=qm_s&&qm_t.indexOf("ersion/3")+1;var qm_n=qm_t.indexOf("Netscape")+1;var qm_v=parseFloat(navigator.vendorSub);;function qm_create(sd,v,ts,th,oc,rl,sh,fl,ft,aux,l){var w="onmouseover";var ww=w;var e="onclick";if(oc){if(oc=="all"||(oc=="lev2"&&l>=2)){w=e;ts=0;}if(oc=="all"||oc=="main"){ww=e;th=0;}}if(!l){l=1;qm_th=th;sd=document.getElementById("qm"+sd);if(window.qm_pure)sd=qm_pure(sd);sd[w]=function(e){qm_kille(e)};document[ww]=qm_bo;if(oc=="main"){qm_ib=true;sd[e]=function(event){qm_ic=true;qm_oo(new Object(),qm_la,1);qm_kille(event)};document.onmouseover=function(){qm_la=null;clearTimeout(qm_tt);qm_tt=null;};}sd.style.zoom=1;if(sh)x2("qmsh",sd,1);if(!v)sd.ch=1;}else  if(sh)sd.ch=1;if(oc)sd.oc=oc;if(sh)sd.sh=1;if(fl)sd.fl=1;if(ft)sd.ft=1;if(rl)sd.rl=1;sd.style.zIndex=l+""+1;var lsp;var sp=sd.childNodes;for(var i=0;i<sp.length;i++){var b=sp[i];if(b.tagName=="A"){lsp=b;b[w]=qm_oo;if(w==e)b.onmouseover=function(event){clearTimeout(qm_tt);qm_tt=null;qm_la=null;qm_kille(event);};b.qmts=ts;if(l==1&&v){b.style.styleFloat="none";b.style.cssFloat="none";}}else  if(b.tagName=="DIV"){if(window.showHelp&&!window.XMLHttpRequest)sp[i].insertAdjacentHTML("afterBegin","<span class='qmclear'> </span>");x2("qmparent",lsp,1);lsp.cdiv=b;b.idiv=lsp;if(qm_n&&qm_v<8&&!b.style.width)b.style.width=b.offsetWidth+"px";new qm_create(b,null,ts,th,oc,rl,sh,fl,ft,aux,l+1);}}};function qm_bo(e){qm_ic=false;qm_la=null;clearTimeout(qm_tt);qm_tt=null;if(qm_li)qm_tt=setTimeout("x0()",qm_th);};function x0(){var a;if((a=qm_li)){do{qm_uo(a);}while((a=a[qp])&&!qm_a(a))}qm_li=null;};function qm_a(a){if(a[qc].indexOf("qmmc")+1)return 1;};function qm_uo(a,go){if(!go&&a.qmtree)return;if(window.qmad&&qmad.bhide)eval(qmad.bhide);a.style.visibility="";x2("qmactive",a.idiv);};;function qa(a,b){return String.fromCharCode(a.charCodeAt(0)-(b-(parseInt(b/2)*2)));};function qm_oo(e,o,nt){if(!o)o=this;if(qm_la==o&&!nt)return;if(window.qmv_a&&!nt)qmv_a(o);if(window.qmwait){qm_kille(e);return;}clearTimeout(qm_tt);qm_tt=null;qm_la=o;if(!nt&&o.qmts){qm_si=o;qm_tt=setTimeout("qm_oo(new Object(),qm_si,1)",o.qmts);return;}var a=o;if(a[qp].isrun){qm_kille(e);return;}if(qm_ib&&!qm_ic)return;var go=true;while((a=a[qp])&&!qm_a(a)){if(a==qm_li)go=false;}if(qm_li&&go){a=o;if((!a.cdiv)||(a.cdiv&&a.cdiv!=qm_li))qm_uo(qm_li);a=qm_li;while((a=a[qp])&&!qm_a(a)){if(a!=o[qp]&&a!=o.cdiv)qm_uo(a);else break;}}var b=o;var c=o.cdiv;if(b.cdiv){var aw=b.offsetWidth;var ah=b.offsetHeight;var ax=b.offsetLeft;var ay=b.offsetTop;if(c[qp].ch){aw=0;if(c.fl)ax=0;}else {if(c.ft)ay=0;if(c.rl){ax=ax-c.offsetWidth;aw=0;}ah=0;}if(qm_o){ax-=b[qp].clientLeft;ay-=b[qp].clientTop;}if(qm_s2&&!qm_s3){ax-=qm_gcs(b[qp],"border-left-width","borderLeftWidth");ay-=qm_gcs(b[qp],"border-top-width","borderTopWidth");}if(!c.ismove){c.style.left=(ax+aw)+"px";c.style.top=(ay+ah)+"px";}x2("qmactive",o,1);if(window.qmad&&qmad.bvis)eval(qmad.bvis);c.style.visibility="inherit";qm_li=c;}else  if(!qm_a(b[qp]))qm_li=b[qp];else qm_li=null;qm_kille(e);};function qm_gcs(obj,sname,jname){var v;if(document.defaultView&&document.defaultView.getComputedStyle)v=document.defaultView.getComputedStyle(obj,null).getPropertyValue(sname);else  if(obj.currentStyle)v=obj.currentStyle[jname];if(v&&!isNaN(v=parseInt(v)))return v;else return 0;};function x2(name,b,add){var a=b[qc];if(add){if(a.indexOf(name)==-1)b[qc]+=(a?' ':'')+name;}else {b[qc]=a.replace(" "+name,"");b[qc]=b[qc].replace(name,"");}};function qm_kille(e){if(!e)e=event;e.cancelBubble=true;if(e.stopPropagation&&!(qm_s&&e.type=="click"))e.stopPropagation();};function qm_pure(sd){if(sd.tagName=="UL"){var nd=document.createElement("DIV");nd.qmpure=1;var c;if(c=sd.style.cssText)nd.style.cssText=c;qm_convert(sd,nd);var csp=document.createElement("SPAN");csp.className="qmclear";csp.innerHTML=" ";nd.appendChild(csp);sd=sd[qp].replaceChild(nd,sd);sd=nd;}return sd;};function qm_convert(a,bm,l){if(!l){bm.className=a.className;bm.id=a.id;}var ch=a.childNodes;for(var i=0;i<ch.length;i++){if(ch[i].tagName=="LI"){var sh=ch[i].childNodes;for(var j=0;j<sh.length;j++){if(sh[j]&&(sh[j].tagName=="A"||sh[j].tagName=="SPAN"))bm.appendChild(ch[i].removeChild(sh[j]));if(sh[j]&&sh[j].tagName=="UL"){var na=document.createElement("DIV");var c;if(c=sh[j].style.cssText)na.style.cssText=c;if(c=sh[j].className)na.className=c;na=bm.appendChild(na);new qm_convert(sh[j],na,1)}}}}}
    </script>
    
       



   

    <style type="text/css">.qmfv {
        visibility: visible !important;
    }

    .qmfh {
        visibility: hidden !important;
    }</style>

</head>
<body>

<table height="50px" width="50%">
<%--
<table border="1" cellpadding="0" spry:hover="qmactive" style="border-left:#FF9900 medium solid" style="border-right:#FF9900 medium solid">
	<tr style="border-bottom:#CCCCCC thick solid" style="border-left:#FF9900 medium solid">
		<td background="greenmist.JPG" width="100%"  align="center" style="border-top:#CCCCCC thick solid" style="border-bottom:#CCCCCC thin solid"><font color="#003366"  face="Verdana, Arial, Helvetica, 								                 sans-serif" size="+1" spry:hover="qmdivider" >Sunrise IT Solutions Pvt Ltd</font>       </td>
   </tr>
  <tr style="border-bottom:thin #FF9966 solid">
      <th><marquee bgcolor="#FF9900" behavior="scroll" direction="right" ><font color="#993366" face="Arial, Helvetica, sans-serif" size="-1">Enjoy core banking with us</font></marquee></th>
   </tr>
--%> 
 <tr>
  	<td> 
		<ul id="qm0" class="qmmc">

		<li><a class="qmparent" href="javascript:void(0)">Customer</a>

		<ul>    
		<li><html:link action="/MenuLink?pageId=1001&value=1">New Customer</html:link>
        <li><html:link action="/MenuLink?pageId=1001&value=2">Verification</html:link></li>
        <li><html:link action="/MenuLink?pageId=1001&value=3">Ammendments</html:link></li>
        <li><html:link action="/CustomizationMenuLink?pageId=1005">Customization</html:link></li>
        <li><html:link action="/CustomerViewLogMenuLink?pageId=1006">Customer View Log</html:link></li>
        <li><html:link action="/QueryOnCustomerMenuLink?pageId=1007">Query On Customer</html:link></li>
        <li><html:link action="/PhotoMenuLink?pageId=1008">Photo</html:link></li>
 <%--  <li><html:link action="/CustomerViewMenuLink?pageId=/CustomerWebPages/CustomerViewLog.jsp">Customer View Log</html:link></li>--%>
        </ul></li> 

	

	<li><a class="qmparent" href="javascript:void(0)">Cash</a> 

		<ul>
		<li><html:link action="/ReceiptMenu?pageId=2001&value=1">General</html:link></li>
		<li><html:link action="/MiscellaneousMenu?pageId=2002">Miscellanous</html:link></li>
		<li><html:link action="/PaymentMenu?pageId=2003">Payment</html:link></li>
		<li><html:link action="/TransferMenu?pageId=2004">Inter Counter</html:link></li>
		<li><html:link action="/ExchangeMenu?pageId=2005">Currency Exchange</html:link></li>
		<li><html:link action="/ConsolidatedMenu?pageId=2006">Consolidated Scroll</html:link></li>
		<li><html:link action="/ClosingMenu?pageId=2007">Closing</html:link></li>
		<li><html:link action="/CurrencyStockMenu?pageId=2009">Currency Stock Updation</html:link></li>
		<li><html:link action="/RebalancingMenu?pageId=2011&value=1">Rebalancing Scroll</html:link></li>
		</ul></li>



	<li><a class="qmparent" href="javascript:void(0)">Verification</a>

		<ul>
		<li><html:link action="/LoansOnDepositMenuLink?pageId=6001&value=2">LoansApplicationDE</html:link></li>
		<li><html:link action="/ReceiptMenu?pageId=2004&value=2">Receipts</html:link></li>
		<li><a href="javascript:void(0)">Inline Loading</a></li>
		<li><a href="javascript:void(0)">Pure CSS Mains</a></li>
		<li><a href="javascript:void(0)">Scalability</a></li>
		<li><a href="javascript:void(0)">Strict Validation</a></li>
		</ul></li>

	<li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Share</a>

		<ul>
		<li><html:link action="/Share/AllotmentMenu?pageId=4001">Share Allotment</html:link></li>
		<li><html:link action="/Share/AdditionalAllotmentMenu?pageId=4002">Additional Allotment</html:link></li>
		<li><html:link action="/Share/DividendCalMenu?pageId=4003">Dividend Calculation</html:link></li>
		<li><html:link action="/Share/PassBookMenu?pageId=4004">Pass Book</html:link></li>
		<li><html:link action="/Share/WithdrawalMenu?pageId=4005">Share Withdrawal</html:link></li>
		<li><html:link action="/Share/ShareOpenReportMenu?pageId=4020">Share Open Reoprt</html:link></li>
		<li><html:link action="/Share/ShareCloseReportMenu?pageId=4021">Share Close Report</html:link></li>
		<li><html:link action="/Share/TempShareMenu?pageId=4022">Temporary Shares</html:link></li>
		<li><html:link action="/Share/PermShareMenu?pageId=4023">Permenant Shares</html:link></li>
		<li><html:link action="/Share/Votermenu?pageId=4024">Voter List</html:link></li>
		<li><html:link action="/Share/ShareRegistryMenu?pageId=4025">Share Registry</html:link></li>
		</ul></li>


    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Front Counter</a>

		<ul>
		<li>AccountOpening
            <li>
                 <html:link action="/FrontCounter/SBOpeningMenu?pageId=3001">SBAccountOpening</html:link>
            </li>
        </li>
		<li><html:link action="/FrontCounter/TabbedMenu?pageID=3003&tabNum=0">TabbedPane</html:link></li>
		<li><a href="javascript:void(0)">Pure CSS Mains</a></li>
		<li><a href="javascript:void(0)">Scalability</a></li>
		<li><a href="javascript:void(0)">Strict Validation</a></li>
		</ul>

   


 <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Term Deposits</a>
  <ul>
		<li><html:link action="/TermDeposit/AccountOpeningMenu?pageId=13001">Deposit Account Opening</html:link></li>
		<li><html:link action="/TermDeposit/InterestCalculationMenu?pageId=13002">Interest Calculation</html:link></li>
		
		<li><html:link action="/TermDeposit/TDClosureMenu?pageId=13003">TDClosure</html:link></li>
		
		<li><html:link action="/TermDeposit/DepositViewLogMenu?pageId=13004">DepositViewLog</html:link></li>
		
		<li><html:link action="/TermDeposit/QueryOnReceiptNoMenu?pageId=13005">QueryOnReceiptNo</html:link></li>
		<li><html:link action="/TermDeposit/ReceiptPrintingMenu?pageId=13006">ReceiptPrinting</html:link></li>
		<li><html:link action="/TermDeposit/ReceiptUpdationMenu?pageId=13007">ReceiptUpdation</html:link></li>
		<li><html:link action="/TermDeposit/QuarterlyInterestMenu?pageId=13008">QuarterlyInterest</html:link></li>
		<li><html:link action="/TermDeposit/AmmendmentsMenu?pageId=13009">Ammendments</html:link></li>
		<li><html:link action="/TermDeposit/AccountRenewalMenu?pageId=13010">AccountRenewal</html:link></li>
		<li><html:link action="/TermDeposit/VoucherPaymentMenu?pageId=13011">VoucherPayment</html:link></li>
		<li><html:link action="/TermDeposit/TermDepositRenewalNoticeMenu?pageId=13012">TermDepositRenewalNotice</html:link></li>
		<li><html:link action="/TermDeposit/TDPassbookMenu?pageId=13017">TDPassbook</html:link></li>
		
		</ul></li>


  

    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Loans</a>

		<ul>
		<li><html:link action="/Loan/LoanApplicationMenu?pageidentity.pageId=5001">Loan Application DE</html:link>></li>
		<li><a href="javascript:void(0)">Sanction/Disbursement</a></li>
		<li><html:link action="/Loans/LoanRecoveryMenu?pageidentity.pageId=5017">Recovery</html:link></li>
		<li><html:link action="/Loans/LoanDailyPostingMenu?pageidentity.pageId=5010">LoanDailyPosting</html:link></li>
		<li><html:link action="/Loans/ReverseRecoveryMenu?pageidentity.pageId=5011">ReverseRecovery</html:link></li>
		<li><html:link action="/Loans/OtherChargesMenu?pageidentity.pageId=5012">OtherCharges</html:link> </li>
		<li><html:link action="/Loans/ReschedulingMenu?pageidentity.pageId=5014">Rescheduling</html:link></li>
		<li><html:link action="/Loans/InterestAccuredMenu?pageidentity.pageId=5015">InterestAccuredReport</html:link></li>
		<li><html:link action="/Loans/OpenClosedStatMenu?pageidentity.pageId=5016">Open-ClosedStat</html:link></li>
		<li><html:link action="/Loans/OCReportMenu?pageidentity.pageId=5023">OtherChargesReport</html:link></li>
		<li><html:link action="/Loans/ODReportMenu?pageidentity.pageId=5024">OverDueReport</html:link></li>
		<li><html:link action="/Loans/NPALoanSummaryMenu?pageidentity.pageId=5025">NPALoanSummary</html:link></li>
		
		
		</ul></li>

    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Clearing</a>

		<ul>
		<li> <html:link action="/Clearing/ChequeDeposition?pageId=7001"> Cheque Deposition </html:link></li>
		<li><html:link action="/Clearing/ChequeMenuDeposition?pageId=7002"> Recieved Cheque Deposition </html:link></li>
		<li><html:link action="/Clearing/ChequeMenuDeposition?pageId=7003"> Clearing Identification </html:link></li>
		<li><html:link action="/Clearing/ChequeMenuDeposition?pageId=7004"> Cheque Discounting </html:link></li>
		<li><html:link action="/Clearing/ChequeMenuDeposition?pageId=7005"> Clearing Dispatch </html:link></li>
		<li><a href="javascript:void(0)">Strict Validation</a></li>
		</ul></li>

    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Pygmy Deposit</a>

		<ul>
		<li><html:link action="/Pygmy/AgentOpeningMenu?pageid=8001">AgentOpening</html:link></li>
		<li><html:link action="/Pygmy/PygmyOpeningMenu?pageid=8002">PygmyOpening</html:link></li>
		<li><html:link action="/Pygmy/PygmyWithdrawalMenu?pageid=8003">PygmyWithdrawal</html:link></li>
		<li><html:link action="/Pygmy/InterestRegisteredMenu?pageid=8004">Interest Registered Report</html:link></li>
		<li><html:link action="/Pygmy/OpenCloseMenu?pageid=8005">Open/Close Report</html:link></li>
		<li><html:link action="/Pygmy/AgentRemittanceMenu?pageid=8006">Agent Remittance Report</html:link></li>
		<li><html:link action="/Pygmy/MonthlyRemittanceMenu?pageid=8007">Monthly Remittance Report</html:link></li>
		<li><html:link action="/Pygmy/PassBookMenu?pageid=8008">Pass Book</html:link></li>
		<li><a href="javascript:void(0)">Pure CSS Mains</a></li>
		<li><a href="javascript:void(0)">Scalability</a></li>
		<li><a href="javascript:void(0)">Strict Validation</a></li>
		</ul></li>


    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Loans on Deposit</a>
     
            <ul>                       
            <li><html:link action="/LoansOnDepositMenuLink?pageId=6001&value=1">LoansApplicationDE</html:link></li>
            <li><html:link action="/LDRecoveryMenuLink?pageId=6004">Recovery</html:link></li>
            <li><html:link action="/LDPassbookMenuLink?pageId=6008">PassBook</html:link></li>
            <li><html:link action="/LDOpenClosedMenuLink?pageId=6009">OpenClosedAccounts</html:link></li>
            <li><html:link action="/LDIntrestAccruedReportMenuLink?pageId=6011">IntrestAccruedReport</html:link></li>
            <li><html:link action="/LDMaturityListMenuLink?pageId=6012">MaturityList</html:link></li>
            <li><html:link action="/LDMaturityExcedingMatListMenuLink?pageId=6013">LDExcedMaturityList</html:link></li>
            </ul></li> 

        <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Lockers</a>

            <ul>
            
            <li><html:link action="/LKSurrenderMenuLink?pageId=9004">LockerSurrender</html:link> </li>
            <li><html:link action="/LKTypesMenuLink?pageId=9005">LockerTypes</html:link> </li>
            <li><html:link action="/LKOperationMenuLink?pageId=9003">LockerOperation</html:link> </li>
 			<li><html:link action="/LKExtensionMenuLink?pageId=9002">LockerExtension</html:link> </li>           
 			<li><html:link action="/LKIssueMenuLink?pageId=9001">LockerIssue</html:link> </li>
 			<li><html:link action="/LKAutoExtensionMenuLink?pageId=9006">LockerAutoExtension</html:link> </li>
			
			<li><html:link action="/LKTableMenuLink?pageId=9007">LockerTable</html:link></li>
			
			<li><html:link action="/LockerDEMenuLink?pageId=00">LockerDE</html:link></li>
		
		
			<li><html:link action="/LKPassBookMLink?pageId=9011">LockerPassBook</html:link></li>
			<li><html:link action="/LockerOwnerReportMLink?pageId=9012">LockerOwnerReport</html:link></li>
			<li><html:link action="/LockerOperationReportMLink?pageId=9013">LockerOperationReport</html:link></li>
			<li><html:link action="/LockerOpenClosedReportMLink?pageId=9014">LockerOpenClosedReport</html:link></li>
			<li><html:link action="/LockerRentDueReportMLink?pageId=9015">LockerRentDueReport</html:link></li>
			<li><html:link action="/LockerRentCollectedReportMLink?pageId=9016">LockerRentCollectedReport</html:link></li>
			<li><html:link action="/LKRemainderNoticeMLink?pageId=9017">LockerRemainderNoticeReport</html:link></li>
			<li><html:link action="/NotSurrenderedReportMLink?pageId=9018">NotSurrenderedReport</html:link></li>
			
			
			<li><a href="javascript:void(0)">Pure CSS Mains</a></li>
			</ul></li>
			
			
			

    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Back Office</a>

            <ul>
            <li><html:link action="/BackOffice/SIEntryMenu?pageId=11001">SIEntry</html:link></li>
            <li><html:link action="/BackOffice/SIDeleteMenu?pageId=11002">SIDeletion</html:link></li>
            <li><html:link action="/BackOffice/UnPresentedChequesMenu?pageId=11003">UnPresentedCheques</html:link></li> 
            <li><html:link action="/BackOffice/ChequeRegisterPrintingReportMenu?pageId=11004">ChequeRegisterPrintingReport</html:link></li>
            <li><html:link action="/BackOffice/OpenedClosedAccountsPrintingMenu?pageId=11005">OpenedClosedAccountsPrintingReport</html:link></li> 
            <li><html:link action="/BackOffice/SIQueryMenu?pageId=11006">SIQuery</html:link></li>
            <li><html:link action="/BackOffice/SIRegisterMenu?pageId=11007">SIRegister</html:link></li> 
            <li><html:link action="/BackOffice/SIDueDoneMenu?pageId=11008">SIDueDone</html:link></li>
            <li><html:link action="/BackOffice/VoucherScheduleMenu?pageId=11009">VoucherSchedule</html:link></li>
            <li><html:link action="/BackOffice/TransactionPrintingMenu?pageId=11010">TransactionPrinting</html:link></li>  
            <li><html:link action="/BackOffice/SBCALedgerMenu?pageId=11011">SBCALedger</html:link></li>
            <li><html:link action="/BackOffice/ClosingBalanceReportMenu?pageId=11012">ClosingBalanceReport</html:link></li>
             
            </ul></li>

        <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">Administrator</a>

            <ul>
            <li><a href="javascript:void(0)">HTML Structure</a></li>
            <li><a href="javascript:void(0)">Inline Loading</a></li>
            <li><a href="javascript:void(0)">Pure CSS Mains</a></li>
            <li><a href="javascript:void(0)">Scalability</a></li>
            <li><a href="javascript:void(0)">Strict Validation</a></li>
            </ul></li>

    <li><a class="qmparent" href="javascript:void(0)" style="border-width:1px;">General Ledger</a>


                <ul>
                <li><a href="javascript:void(0)">HTML Structure</a></li>
                <li><a href="javascript:void(0)">Inline Loading</a></li>
                <li><a href="javascript:void(0)">Pure CSS Mains</a></li>
                <li><a href="javascript:void(0)">Scalability</a></li>
                <li><a href="javascript:void(0)">Strict Validation</a></li>
                </ul></li>


<li class="qmclear">&nbsp;</li>  
  
    <tr>
    <td>
    	<table  align="left" background="goldfade1.JPG" width="500" >
			<tr style="border-left:medium #FFCC00 solid"><td style="border-left:medium #FFCC00 solid"></td></tr>
          </table>
     </td>          
    </tr>

    <%--<tr style="border-left:medium #FFCC00 solid">
    	<th background="orangeheader.JPG" width="100%" ><marquee direction="right" >User: Role: </marquee></th>
    </tr>
    <tr><th background="bluemist.JPG" width="100%" >@Sunrise IT Solutions Pvt Ltd. Malleswaram</th></tr>--%>
</table>


<!-- Create Menu Settings: (Menu ID, Is Vertical, Show Timer, Hide Timer, On Click ('all' or 'lev2'), Right to Left, Horizontal Subs, Flush Left, Flush Top) -->
<script type="text/javascript">qm_create(0,false,0,500,false,false,false,false,false);</script>
</body>
</html>
