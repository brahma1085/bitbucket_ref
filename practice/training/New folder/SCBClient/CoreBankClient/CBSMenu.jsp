<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@page import="java.util.Vector"%>
<%@page import="javax.servlet.http.HttpSession.*" %>

<html>
<head><title>SunBank Menu</title><META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8"> 






<!--%%%%%%%%%%%% Quick Menu Script %%%%%%%%%%%*-->

	<!-- Core Menu Code -->
	<!--<script type="text/JavaScript" src="qm.js"></script>

	--><!-- Add-On Core Code (Remove when not using any add-on's) -->
	<style type="text/css">.qmfv{visibility:visible !important;}.qmfh{visibility:hidden !important;}</style><script type="text/JavaScript">var qmad = new Object();qmad.bvis="";qmad.bhide="";qmad.bhover="";</script>

		<!-- Add-On Settings -->
		<script type="text/JavaScript">
		
		function lkOperationWindow(){
		window.open("http://localhost:8080/SCBClient/LKOperationMenuLink.do?pageId=9003","Popup");
		}

              var qm_si,qm_li,qm_lo,qm_tt,qm_th,qm_ts,qm_la,qm_ic,qm_ib;var qp="parentNode";var qc="className";var qm_t=navigator.userAgent;var qm_o=qm_t.indexOf("Opera")+1;var qm_s=qm_t.indexOf("afari")+1;var qm_s2=qm_s&&qm_t.indexOf("ersion/2")+1;var qm_s3=qm_s&&qm_t.indexOf("ersion/3")+1;var qm_n=qm_t.indexOf("Netscape")+1;var qm_v=parseFloat(navigator.vendorSub);;function qm_create(sd,v,ts,th,oc,rl,sh,fl,ft,aux,l){var w="onmouseover";var ww=w;var e="onclick";if(oc){if(oc=="all"||(oc=="lev2"&&l>=2)){w=e;ts=0;}if(oc=="all"||oc=="main"){ww=e;th=0;}}if(!l){l=1;qm_th=th;sd=document.getElementById("qm"+sd);if(window.qm_pure)sd=qm_pure(sd);sd[w]=function(e){qm_kille(e)};document[ww]=qm_bo;if(oc=="main"){qm_ib=true;sd[e]=function(event){qm_ic=true;qm_oo(new Object(),qm_la,1);qm_kille(event)};document.onmouseover=function(){qm_la=null;clearTimeout(qm_tt);qm_tt=null;};}sd.style.zoom=1;if(sh)x2("qmsh",sd,1);if(!v)sd.ch=1;}else  if(sh)sd.ch=1;if(oc)sd.oc=oc;if(sh)sd.sh=1;if(fl)sd.fl=1;if(ft)sd.ft=1;if(rl)sd.rl=1;sd.style.zIndex=l+""+1;var lsp;var sp=sd.childNodes;for(var i=0;i<sp.length;i++){var b=sp[i];if(b.tagName=="A"){lsp=b;b[w]=qm_oo;if(w==e)b.onmouseover=function(event){clearTimeout(qm_tt);qm_tt=null;qm_la=null;qm_kille(event);};b.qmts=ts;if(l==1&&v){b.style.styleFloat="none";b.style.cssFloat="none";}}else  if(b.tagName=="DIV"){if(window.showHelp&&!window.XMLHttpRequest)sp[i].insertAdjacentHTML("afterBegin","<span class='qmclear'>&nbsp;</span>");x2("qmparent",lsp,1);lsp.cdiv=b;b.idiv=lsp;if(qm_n&&qm_v<8&&!b.style.width)b.style.width=b.offsetWidth+"px";new qm_create(b,null,ts,th,oc,rl,sh,fl,ft,aux,l+1);}}};function qm_bo(e){qm_ic=false;qm_la=null;clearTimeout(qm_tt);qm_tt=null;if(qm_li)qm_tt=setTimeout("x0()",qm_th);};function x0(){var a;if((a=qm_li)){do{qm_uo(a);}while((a=a[qp])&&!qm_a(a))}qm_li=null;};function qm_a(a){if(a[qc].indexOf("qmmc")+1)return 1;};function qm_uo(a,go){if(!go&&a.qmtree)return;if(window.qmad&&qmad.bhide)eval(qmad.bhide);a.style.visibility="";x2("qmactive",a.idiv);};;function qa(a,b){return String.fromCharCode(a.charCodeAt(0)-(b-(parseInt(b/2)*2)));};function qm_oo(e,o,nt){if(!o)o=this;if(qm_la==o&&!nt)return;if(window.qmv_a&&!nt)qmv_a(o);if(window.qmwait){qm_kille(e);return;}clearTimeout(qm_tt);qm_tt=null;qm_la=o;if(!nt&&o.qmts){qm_si=o;qm_tt=setTimeout("qm_oo(new Object(),qm_si,1)",o.qmts);return;}var a=o;if(a[qp].isrun){qm_kille(e);return;}if(qm_ib&&!qm_ic)return;var go=true;while((a=a[qp])&&!qm_a(a)){if(a==qm_li)go=false;}if(qm_li&&go){a=o;if((!a.cdiv)||(a.cdiv&&a.cdiv!=qm_li))qm_uo(qm_li);a=qm_li;while((a=a[qp])&&!qm_a(a)){if(a!=o[qp]&&a!=o.cdiv)qm_uo(a);else break;}}var b=o;var c=o.cdiv;if(b.cdiv){var aw=b.offsetWidth;var ah=b.offsetHeight;var ax=b.offsetLeft;var ay=b.offsetTop;if(c[qp].ch){aw=0;if(c.fl)ax=0;}else {if(c.ft)ay=0;if(c.rl){ax=ax-c.offsetWidth;aw=0;}ah=0;}if(qm_o){ax-=b[qp].clientLeft;ay-=b[qp].clientTop;}if(qm_s2&&!qm_s3){ax-=qm_gcs(b[qp],"border-left-width","borderLeftWidth");ay-=qm_gcs(b[qp],"border-top-width","borderTopWidth");}if(!c.ismove){c.style.left=(ax+aw)+"px";c.style.top=(ay+ah)+"px";}x2("qmactive",o,1);if(window.qmad&&qmad.bvis)eval(qmad.bvis);c.style.visibility="inherit";qm_li=c;}else  if(!qm_a(b[qp]))qm_li=b[qp];else qm_li=null;qm_kille(e);};function qm_gcs(obj,sname,jname){var v;if(document.defaultView&&document.defaultView.getComputedStyle)v=document.defaultView.getComputedStyle(obj,null).getPropertyValue(sname);else  if(obj.currentStyle)v=obj.currentStyle[jname];if(v&&!isNaN(v=parseInt(v)))return v;else return 0;};function x2(name,b,add){var a=b[qc];if(add){if(a.indexOf(name)==-1)b[qc]+=(a?' ':'')+name;}else {b[qc]=a.replace(" "+name,"");b[qc]=b[qc].replace(name,"");}};function qm_kille(e){if(!e)e=event;e.cancelBubble=true;if(e.stopPropagation&&!(qm_s&&e.type=="click"))e.stopPropagation();}

			/*Menu 0 Settings*/
			var a = qmad.qm0 = new Object();
		
			// Tabs (CSS Based) Add On
			a.tabscss_type = "angled";
			a.tabscss_size = 5;
			a.tabscss_left_offset = 0;
			a.tabscss_top_offset = 0;
			a.tabscss_apply_far_left = true;
			a.tabscss_apply_far_right = true;
			a.tabscss_apply_middles = true;
			a.tabscss_bg_color = "#FF9966";
			a.tabscss_border_color = "#687a54";
			a.tabscss_bg_image="menuback1.GIF";


			// Item Bullets Add On
			a.ibullets_apply_to = "parent";
			a.ibullets_main_image = "menuback1.GIF";
			a.ibullets_main_image_width = 8;
			a.ibullets_main_image_height = 6;
			a.ibullets_main_position_x = -21;
			a.ibullets_main_position_y = -5;
			a.ibullets_main_align_x = "right";
			a.ibullets_main_align_y = "middle";

			a.ibullets_sub_image = "arrow_right.gif";
			a.ibullets_sub_image_width = 6;
			a.ibullets_sub_image_height = 8;
			a.ibullets_sub_position_x = -14;
			a.ibullets_sub_position_y = -4;
			a.ibullets_sub_align_x = "right";
			a.ibullets_sub_align_y = "middle";
			
            qmad.br_ie=window.showHelp;qmad.br_mac=navigator.userAgent.indexOf("Mac")+1;qmad.br_old_safari=navigator.userAgent.indexOf("afari")+1&&!window.XMLHttpRequest;qmad.br_navigator=navigator.userAgent.indexOf("Netscape")+1;qmad.br_version=parseFloat(navigator.vendorSub);qmad.br_oldnav=qmad.br_navigator&&qmad.br_version<7.1;if(!(qmad.br_ie&&qmad.br_mac)&&!qmad.br_old_safari&&!qmad.br_oldnav&&!qmad.tabscss){qmad.tabscss=new Object();if(window.attachEvent)window.attachEvent("onload",qm_tabscss_init);else  if(window.addEventListener)window.addEventListener("load",qm_tabscss_init,1);};function qm_tabscss_init(e,spec){var z;if((z=window.qmv)&&(z=z.addons)&&(z=z.tabscss)&&(!z["on"+qmv.id]&&z["on"+qmv.id]!=undefined&&z["on"+qmv.id]!=null))return;qm_ts=1;var q=qmad.tabscss;var a;for(i=0;i<10;i++){if(!(a=document.getElementById("qm"+i))||(spec&&spec!=i))continue;var ss=qmad[a.id];if(ss&&ss.tabscss_type){q.type=ss.tabscss_type;q.h=ss.tabscss_size;if(!q.h)continue;q.border=ss.tabscss_border_color;q.background=ss.tabscss_bg_color;q.thick=ss.tabscss_thickness;if(!q.background)q.background="#ffffff";if(!q.border)q.border="#000000";if(!q.thick)q.thick=1;q.lc=ss.tabscss_apply_far_left;q.rc=ss.tabscss_apply_far_right;q.mid=ss.tabscss_apply_middles;if(!q.lc&&!q.rc&&!q.mid)q.mid=true;q.toff=ss.tabscss_top_offset;if(!q.toff)q.toff=0;q.loff=ss.tabscss_left_offset;if(!q.loff)q.loff=0;qm_tabscss_init_items(a);}i++;}};function qm_tabscss_init_items(a){var q=qmad.tabscss;var first=true;var lat=null;var at=a.childNodes;for(var i=0;i<at.length;i++){if(at[i].tagName=="A"){if((first&&q.lc)||(!first&&q.mid)){a.insertBefore(qm_tabscss_create_tabimg(at[i],first),at[i]);i++;}lat=at[i];first=false;continue;}}if(lat&&q.rc){a.insertBefore(qm_tabscss_create_tabimg(lat,false,1),lat.nextSibling);}};function qm_tabscss_create_tabimg(a,isfirst,islast){var q=qmad.tabscss;var s=document.createElement("SPAN");s.iscsstab=1;s.style.display="block";s.style.position="relative";s.style.fontSize="1px";s.style.styleFloat="left";s.style.cssFloat="left";s.style.zIndex=1;s.style.height=a.offsetHeight+"px";s.style.width="0px";s.noselect=1;var part="middle";if(isfirst)part="first";if(islast)part="last";var wt="";q.z1=0;for(var i=0;i<q.h;i++)wt+=qm_tabscss_get_span(q.h,i,part);s.innerHTML=wt;return s;};function qm_tabscss_get_span(size,i,part){var q=qmad.tabscss;var it=i;var il=0;var ih=1;var iw=1;var ml=0;var mr=0;var bl=1;var br=1;if(q.type=="angled"){ml=i;mr=i;iw=((size-i)*2)-q.thick;il=-size+(q.thick-1);it+=q.toff;il+=q.loff;ih=q.thick;if(part=="first"){iw=size-i;bl=0;ml=0;il+=size-(q.thick);}if(part=="last"){iw=size-i;br=0;mr=0;il -=1;}}else  if(q.type=="rounded"){ml=i;mr=i;iw=((size-i)*2)-1;il=-size;il+=q.loff;it+=q.toff;ih=i+1;it=q.z1;q.z1+=ih;if(part=="first"){iw=size-i;bl=0;ml=0;il+=size-1;}if(part=="last"){iw=size-i;br=0;mr=0;}}return '<span style="background-color:'+q.background+';border-color:'+q.border+';border-width:0px '+br+'px 0px '+bl+'px;border-style:solid;display:block;position:absolute;overflow:hidden;font-size:1px;line-height:0px;height:'+ih+'px;margin-left:'+ml+'px;margin-right:'+mr+'px;width:'+iw+'px;top:'+it+'px;left:'+il+'px;"></span>';}
            qmad.br_navigator=navigator.userAgent.indexOf("Netscape")+1;qmad.br_version=parseFloat(navigator.vendorSub);qmad.br_oldnav6=qmad.br_navigator&&qmad.br_version<7;if(!qmad.br_oldnav6){if(!qmad.ibullets)qmad.ibullets=new Object();if(qmad.bvis.indexOf("qm_ibullets_active(o,false);")==-1){qmad.bvis+="qm_ibullets_active(o,false);";qmad.bhide+="qm_ibullets_active(a,1);";if(window.attachEvent)window.attachEvent("onload",qm_ibullets_init);else  if(window.addEventListener)window.addEventListener("load",qm_ibullets_init,1);if(window.attachEvent)document.attachEvent("onmouseover",qm_ibullets_hover_off);else  if(window.addEventListener)document.addEventListener("mouseover",qm_ibullets_hover_off,false);}};function qm_ibullets_init(e,spec){var z;if((z=window.qmv)&&(z=z.addons)&&(z=z.item_bullets)&&(!z["on"+qmv.id]&&z["on"+qmv.id]!=undefined&&z["on"+qmv.id]!=null))return;qm_ts=1;var q=qmad.ibullets;var a,b,r,sx,sy;z=window.qmv;for(i=0;i<10;i++){if(!(a=document.getElementById("qm"+i))||(!isNaN(spec)&&spec!=i))continue;var ss=qmad[a.id];if(ss&&(ss.ibullets_main_image||ss.ibullets_sub_image)){q.mimg=ss.ibullets_main_image;if(q.mimg){q.mimg_a=ss.ibullets_main_image_active;if(!z)qm_ibullets_preload(q.mimg_a);q.mimg_h=ss.ibullets_main_image_hover;if(!z)qm_ibullets_preload(q.mimg_a);q.mimgwh=eval("new Array("+ss.ibullets_main_image_width+","+ss.ibullets_main_image_height+")");r=q.mimgwh;if(!r[0])r[0]=9;if(!r[1])r[1]=6;sx=ss.ibullets_main_position_x;sy=ss.ibullets_main_position_y;if(!sx)sx=0;if(!sy)sy=0;q.mpos=eval("new Array('"+sx+"','"+sy+"')");q.malign=eval("new Array('"+ss.ibullets_main_align_x+"','"+ss.ibullets_main_align_y+"')");r=q.malign;if(!r[0])r[0]="right";if(!r[1])r[1]="center";}q.simg=ss.ibullets_sub_image;if(q.simg){q.simg_a=ss.ibullets_sub_image_active;if(!z)qm_ibullets_preload(q.simg_a);q.simg_h=ss.ibullets_sub_image_hover;if(!z)qm_ibullets_preload(q.simg_h);q.simgwh=eval("new Array("+ss.ibullets_sub_image_width+","+ss.ibullets_sub_image_height+")");r=q.simgwh;if(!r[0])r[0]=6;if(!r[1])r[1]=9;sx=ss.ibullets_sub_position_x;sy=ss.ibullets_sub_position_y;if(!sx)sx=0;if(!sy)sy=0;q.spos=eval("new Array('"+sx+"','"+sy+"')");q.salign=eval("new Array('"+ss.ibullets_sub_align_x+"','"+ss.ibullets_sub_align_y+"')");r=q.salign;if(!r[0])r[0]="right";if(!r[1])r[1]="middle";}q.type=ss.ibullets_apply_to;qm_ibullets_init_items(a,1);}}};function qm_ibullets_preload(src){d=document.createElement("DIV");d.style.display="none";d.innerHTML="<img src="+src+" width=1 height=1>";document.body.appendChild(d);};function qm_ibullets_init_items(a,main){var q=qmad.ibullets;var aa,pf;aa=a.childNodes;for(var j=0;j<aa.length;j++){if(aa[j].tagName=="A"){if(window.attachEvent)aa[j].attachEvent("onmouseover",qm_ibullets_hover);else  if(window.addEventListener)aa[j].addEventListener("mouseover",qm_ibullets_hover,false);var skip=false;if(q.type!="all"){if(q.type=="parent"&&!aa[j].cdiv)skip=true;if(q.type=="non-parent"&&aa[j].cdiv)skip=true;}if(!skip){if(main)pf="m";else pf="s";if(q[pf+"img"]){var ii=document.createElement("IMG");ii.setAttribute("src",q[pf+"img"]);ii.setAttribute("width",q[pf+"imgwh"][0]);ii.setAttribute("height",q[pf+"imgwh"][1]);ii.style.borderWidth="0px";ii.style.position="absolute";var ss=document.createElement("SPAN");var s1=ss.style;s1.display="block";s1.position="relative";s1.fontSize="1px";s1.lineHeight="0px";s1.zIndex=1;ss.ibhalign=q[pf+"align"][0];ss.ibvalign=q[pf+"align"][1];ss.ibiw=q[pf+"imgwh"][0];ss.ibih=q[pf+"imgwh"][1];ss.ibposx=q[pf+"pos"][0];ss.ibposy=q[pf+"pos"][1];qm_ibullets_position(aa[j],ss);ss.appendChild(ii);aa[j].qmibullet=aa[j].insertBefore(ss,aa[j].firstChild);aa[j]["qmibullet"+pf+"a"]=q[pf+"img_a"];aa[j]["qmibullet"+pf+"h"]=q[pf+"img_h"];aa[j].qmibulletorig=q[pf+"img"];ss.setAttribute("qmvbefore",1);ss.setAttribute("isibullet",1);if(aa[j].className.indexOf("qmactive")+1)qm_ibullets_active(aa[j]);}}if(aa[j].cdiv)new qm_ibullets_init_items(aa[j].cdiv);}}};function qm_ibullets_position(a,b){if(b.ibhalign=="right")b.style.left=(a.offsetWidth+parseInt(b.ibposx)-b.ibiw)+"px";else  if(b.ibhalign=="center")b.style.left=(parseInt(a.offsetWidth/2)-parseInt(b.ibiw/2)+parseInt(b.ibposx))+"px";else b.style.left=b.ibposx+"px";if(b.ibvalign=="bottom")b.style.top=(a.offsetHeight+parseInt(b.ibposy)-b.ibih)+"px";else  if(b.ibvalign=="middle")b.style.top=parseInt((a.offsetHeight/2)-parseInt(b.ibih/2)+parseInt(b.ibposy))+"px";else b.style.top=b.ibposy+"px";};function qm_ibullets_hover(e,targ){e=e||window.event;if(!targ){var targ=e.srcElement||e.target;while(targ.tagName!="A")targ=targ[qp];}var ch=qmad.ibullets.lasth;if(ch&&ch!=targ){qm_ibullets_hover_off(new Object(),ch);}if(targ.className.indexOf("qmactive")+1)return;var wo=targ.qmibullet;var ma=targ.qmibulletmh;var sa=targ.qmibulletsh;if(wo&&(ma||sa)){var ti=ma;if(sa&&sa!=undefined)ti=sa;if(ma&&ma!=undefined)ti=ma;wo.firstChild.src=ti;qmad.ibullets.lasth=targ;}if(e)qm_kille(e);};function qm_ibullets_hover_off(e,o){if(!o)o=qmad.ibullets.lasth;if(o&&o.className.indexOf("qmactive")==-1){if(o.firstChild&&o.firstChild.getAttribute&&o.firstChild.getAttribute("isibullet"))o.firstChild.firstChild.src=o.qmibulletorig;}};function qm_ibullets_active(a,hide){var wo=a.qmibullet;var ma=a.qmibulletma;var sa=a.qmibulletsa;if(!hide&&a.className.indexOf("qmactive")==-1)return;if(hide&&a.idiv){var o=a.idiv;if(o&&o.qmibulletorig){if(o.firstChild&&o.firstChild.getAttribute&&o.firstChild.getAttribute("isibullet"))o.firstChild.firstChild.src=o.qmibulletorig;}}else {if(!a.cdiv.offsetWidth)a.cdiv.style.visibility="inherit";if(a.cdiv){var aa=a.cdiv.childNodes;for(var i=0;i<aa.length;i++){if(aa[i].tagName=="A"&&aa[i].qmibullet)qm_ibullets_position(aa[i],aa[i].qmibullet);}}if(wo&&(ma||sa)){var ti=ma;if(sa&&sa!=undefined)ti=sa;if(ma&&ma!=undefined)ti=ma;wo.firstChild.src=ti;}}}
		</script>


	<!-- Optional Add-On's (Must appear after core menu code) -->
	<!--<script type="text/JavaScript" src="qm_tabs_css.js"></script>
	<script type="text/javascript" src="qm_item_bullets.js"></script>


--><!--%%%%%%%%%%%% QuickMenu Styles [Keep in head for full validation!] %%%%%%%%%%%-->
<style type="text/css">

/*!!!!!!!!!!! QuickMenu Core CSS [Do Not Modify!] !!!!!!!!!!!!!*/
.qmmc .qmdivider{display:block;font-size:1px;border-width:0px;border-style:solid;} .qmmc .qmdividery{float:left;width:0px;} .qmmc .qmtitle{display:block;cursor:default;white-space:nowrap;} .qmclear {font-size:1px;height:0px;width:0px;clear:left;line-height:0px;display:block;}.qmmc {position:relative;}.qmmc a {float:left;display:block;white-space:nowrap;}.qmmc div a {float:none;}.qmsh div a{float:left;}.qmmc div {visibility:hidden;position:absolute;}


/*!!!!!!!!!!! QuickMenu Styles [Please Modify!] !!!!!!!!!!!*/


	/* Remove the comments bleow for vertical mains and change the false value to
           true in the qm_create function after the menus structure. */
	/*.qmmc a {float:none}*/

	
	
/*"""""""" (MAIN) Container""""""""*/
	#qm0
	{
		margin:0px;
		background-color:#daebd0;
		 background-image:url("s3.GIF");
        width: 900px;
    }


	/*"""""""" (MAIN) Items""""""""*/
	#qm0 a
	{
		padding:4px 5px 4px 5px;
		background-image:url("green1.JPG");
		color:#222222;
		font-family:Arial;
       /* font-family:serif;*/
        /*font-style:oblique;*/
        font-size:9px;
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
		background-image:url("green1.JPG");
		
	}


	/*"""""""" (SUB) Container""""""""*/
	#qm0 div, #qm0 ul
	{
		padding:5px;
		background-image:url("green1.JPG");
		background-color:#dcedcd;
		background-image:url("green1.JPG");
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
		background-image:url("green1.JPG");
		color:#222222;
		border-color:#687a54;
	}


	/*"""""""" (SUB) Active State""""""""*/
	body #qm0 div .qmactive, body #qm0 div .qmactive:hover
	{
		border-color:#687a54;
	}
	
	
	body{

		margin:0;
		
        background-image:url("green1.JPG");
   
        width:100%;
		height:100%;
		border:thick;

		border-bottom:#33CC66 thick solid;
		border-top:#33CC66 thick solid;

	}
	
    </style>

</head>



<body >



<!--%%%%%%%%%%%% QuickMenu Stucture %%%%%%%%%%%*-->

<%!
Object obj=null;
Vector access=null; 

HttpSession session=null;
%>
<%
session=request.getSession();
access=(Vector)session.getAttribute("usraccessvector"); 
  
   
%>



<div id="qm0" class="qmmc"> 
   
   <a href="javascript:void(0)">Customer</a>
				
		<div>
			<a><html:link action="/MenuLink?pageId=1001&value=1">New Customer</html:link></a>
        	<a><html:link action="/MenuLink?pageId=1001&value=2">Verification</html:link></a>
        	<!--<a><html:link action="/MenuLink?pageId=1001&value=3">Ammendments</html:link></a>
        	--><a><html:link action="/AddressAmmendmentsMenu?pageId=1009&value=3">Address-Ammendments</html:link></a>
        	<a><html:link action="/CustomizationMenuLink?pageId=1005">Customization</html:link></a>
        	<a><html:link action="/CustomerViewLogMenuLink?pageId=1006">Customer View Log</html:link></a>
        	<a><html:link action="/QueryOnCustomerMenuLink?pageId=1007">Query On Customer</html:link></a>
		</div>
		
	<a href="javascript:void(0)">Cash</a>
		<div>
		<a>Receipts</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/ReceiptMenu?pageId=2001&value=1">General</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/MiscellaneousMenu?pageId=2002&value=1">Miscellanous</html:link></a>
		</div>	
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/PaymentMenu?pageId=2003">Payment</html:link></a>
		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/ExchangeMenu?pageId=2005&value=1">Currency Exchange</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/ConsolidatedMenu?pageId=2006">Consolidated Scroll</html:link></a>
		<a>Closing</a>
		<div>		
		<!--<a style="border-width:1px 1px 1px 1px;"><html:link action="/ExchangeMenu?pageId=2005&value=2">Sub Cashier</html:link></a>
		--><a style="border-width:1px 1px 1px 1px;"><html:link action="/CurrencyStockMenu?pageId=2009&value=2">Main Cashier</html:link></a>
		</div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/CurrencyStockMenu?pageId=2009&value=1">Currency Stock Updation</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/RebalancingMenu?pageId=2011&value=1">Rebalancing Scroll</html:link></a>
		</div>
			
	<a href="javascript:void(0)">Verification</a>
			
		<div>
		<a>Loans On Deposit</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LoansOnDepositMenuLink?pageId=6001&value=2">LoansApplicationDE</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/AdditionalVerMenuLink?pageId=6014">AdditionalLoan</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LDRecoveryMenuLink?pageId=6004&value=2">Loan Recovery</html:link></a>
		</div>
		<a>Clearing</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/ChequeMenuDeposition?pageId=7050">ChequeDepositionVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/CombinedChequeMLink?pageId=7051">CombinedChequeVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/ackDataEntryMLink?pageId=7052">AcknowledgementVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/RecievedChequeEntryMLink?pageId=7053">RecievedChequeVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/BundledDataEntryMlink?pageId=7054">BundledEntryVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/ReceivedECSMLink?pageId=7055">RecievedECSVerify</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Clearing/OutStationChequeMLink?pageId=7056">OutStationChequeVerify</html:link></a>
		</div>
		
		<a>FrontCounter</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/FrontCounter/VerifySBopenMenu?pageId=3045">SB Opening</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/FrontCounter/VerifyODCCOpenMenu?pageId=3054">Verify ODCC</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/FrontCounter/VerifyFCChqwithdrawalMenu?pageId=3055">Cheque Withdrawal</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/FrontCounter/VerifyPOConsolMenu?pageId=3049">PO Consolidation</html:link></a>
		</div>
		
		<a>Loans</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/LoansApplicationDEVerifyMenu?pageidentity.pageId=5082">Loan Application DE</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/SanctionVerifyMenu?pageidentity.pageId=5083"> Sanction DE</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/DisbursementVerifyMenu?pageidentity.pageId=5081">Disbursement DE</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/RecoveryVerifyMenu?pageidentity.pageId=5084">Recovery</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/OtherChargesVerifyMenu?pageidentity.pageId=5085">O/C DE</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Loans/ReverseRecoveryVerifyMenu?pageidentity.pageId=5086">Reverse Recovery</html:link></a>
		</div>
		
		<a>Pygmy</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentOpeningMenu?pageid=8801&value=2">AgentOpening</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyOpeningMenu?pageid=8002&value=2">PygmyOpening</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentClosingVerMenu?pageid=8030&value=2">AgentClosing</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentChangeVerMenu?pageid=8031">AgentChanging</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyWithdrawalMenu?pageid=8003&value=2">PygmyWithdrawal</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/DailyRemittanceMenu?pageid=8022">DailyRemittance</html:link></a>
		</div>
		
</div>

		<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Share</a>
			
		<div>
			<a>Share Allotment</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/AllotmentMenu?pageId=4001">New Share</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/AdditionalAllotmentMenu?pageId=4002">Additional Share</html:link></a>
        <a>Convert Shares</a>
        <div>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ConvertTempMenu?pageId=4030">New Allotment</html:link></a>
       <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ConvertTempAddMenu?pageId=4008">Add Allotment</html:link></a>
        </div> 
        <a>Inter Branch</a>
        <div>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareDispatchMenu?pageId=4009">Share Dispatch</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/RegularizationMenu?pageId=4011">Regularisation</html:link></a>
        </div>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/CertificateMenu?pageId=4012">Certificate</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ViewLogMenu?pageId=4013">View Log</html:link></a>	
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/MasterUpdationMenu?pageId=4014">Master Updation</html:link></a>	
		</div>
		<a><html:link action="/Share/WithdrawalMenu?pageId=4005">ShareWithdrawal</html:link></a>
		<a>Reports</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareOpenReportMenu?pageId=4020">Open Report</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareCloseReportMenu?pageId=4021">Close Report</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/TempShareMenu?pageId=4022">Temporary Shares</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/PermShareMenu?pageId=4023">Permanent Shares</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/Votermenu?pageId=4024">Voter List</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareRegistryMenu?pageId=4025">Share Registry</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareAllotwithMenu?pageId=4026">Share Allotment/Withdrawal</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareRegistrySumMenu?pageId=4027">Share Registry Summary</html:link></a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/Share/ShareLedgerMenu?pageId=4028">Share Ledger</html:link></a>           		
		</div>
		<a>Dividend</a>
		<div>
		<a><html:link action="/Share/DividendCalMenu?pageId=4003">Dividend Calculation</html:link></a>
		<a><html:link action="/Share/DividendEntryMenu?pageId=4029">Dividend Data Entry</html:link></a>
		<a><html:link action="/Share/UnClaimedDividendNoticeMenu?pageId=4015">Unclaimed Dividend Notice</html:link></a>
		<a><html:link action="/Share/UnClaimedDividendMenu?pageId=4006">Unclaimed Dividend List</html:link></a>
		<a><html:link action="/Share/DividendPaymentMenu?pageId=4016">Dividend Payment</html:link></a>
		<a><html:link action="/Share/DividendRegistryMenu?pageId=4007">Dividend Registry </html:link></a>
		<a><html:link action="/Share/DividendReportMenu?pageId=4031">Dividend Report</html:link></a>
		</div>
		<a><html:link action="/Share/PassBookMenu?pageId=4004">Pass Book</html:link></a>
		<a>Share Admin</a>
		<div>
		<a><html:link action="/Share/ShareAdminMenu?pageId=4017">Share Admin</html:link></a>
		<a><html:link action="/Share/ShareDivRateMenu?pageId=4018">Share Div Rate</html:link></a>
		<a><html:link action="/Share/ChangeofModulesMenu?pageId=4019">Change of Modules</html:link></a>
		</div>

		</div>
		
<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Front Counter</a>
			
		<div>
		<a ><bean:message key="label.acOpen"></bean:message></a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/FrontCounter/SBOpeningMenu?pageId=3001"><bean:message key="label.sb"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/ODCCApplicationEntryMenu?pageId=3022"><bean:message key="label.od"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/OdccSanctionMenu?pageId=3020"><bean:message key="label.sanction"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/ViewLogMenu?pageId=3005"><bean:message key="label.view"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/CCPeriodMenu?pageId=3021"><bean:message key="label.pinsp"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/InOpProcessMenu?pageId=3023"><bean:message key="label.inop"></bean:message></html:link></a>
			</div>
		<a ><bean:message key="label.chqOperation"></bean:message></a>
			<div>
				<a><html:link action="/FrontCounter/ChqWithdrawMenu?pageId=3008"><bean:message key="label.chqWithdrwal"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/ChqIssueMenu?pageId=3009"><bean:message key="label.chqIssue"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/ChqQueryMenu?pageId=3010"><bean:message key="label.chqQry"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/ChequeInstructionMenu?pageId=3013"><bean:message key="label.chqIns"></bean:message></html:link></a>
			</div>
		<a><bean:message key="label.payOrder"></bean:message></a>
			<div>
				<a><html:link action="/FrontCounter/PoDeMenu?pageId=3018"><bean:message key="label.payOrdDe"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/PoCoMenu?pageId=3024"><bean:message key="label.payOrdCon"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/PoInsMenu?pageId=3019"><bean:message key="label.payOrdIns"></bean:message></html:link></a>
				<a><bean:message key="label.rep"></bean:message></a>
				<div>
					<a><html:link action="/FrontCounter/ListPOUncashMenu?pageId=3050"><bean:message key="label.listOfUnPo"></bean:message></html:link></a>
					<a><html:link action="/FrontCounter/ListPOCrMenu?pageId=3051"><bean:message key="label.listOfCrtdPo"></bean:message></html:link></a>
					<a><html:link action="/FrontCounter/ListPOConsolReportMenu?pageId=3052"><bean:message key="label.listOfConsPo"></bean:message></html:link></a>
					<a><html:link action="/FrontCounter/ListPOCashedMenu?pageId=3053"><%="List Of Cashed PayOrder's"%></html:link></a>
				
				</div>
			</div>
		<a><bean:message key="label.passBk"></bean:message></a>
			<div>
				<a><html:link action="/FrontCounter/PassbookMenu?pageId=3014"><bean:message key="label.passBk"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/PassSheetMenu?pageId=3015"><bean:message key="label.passSht"></bean:message></html:link></a>
			</div>
		<a><bean:message key="label.int"></bean:message></a>
			<div>
				<a><html:link action="/FrontCounter/InterestCalcMenu?pageId=3016"><bean:message key="label.intCal"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/InterestPostingMenu?pageId=3017"><bean:message key="label.intPost"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/InterestRegisterMenu?pageId=3048"><bean:message key="label.intReg"></bean:message></html:link></a>
				
			</div>
			<a>Account Closing</a>
			<div>
				<a><html:link action="/FrontCounter/AccountCloseMenu?pageId=3007">Account Closing</html:link></a>
				
			</div>
		<a><bean:message key="label.mastUpdate"></bean:message></a>
		<div>
				<a><html:link action="/FrontCounter/SBUpdationMenu?pageId=3041"><bean:message key="label.sbcaUp"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/JointHoldersUpdationMenu?pageId=3044"><bean:message key="label.jntHoldUp"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/OdccUpdationMenu?pageId=3046"><bean:message key="label.odccMastUp"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/EmpDetailsMenu?pageId=3047">Employment Details</html:link></a>
				<a><html:link action="/FrontCounter/OdccSbcaUpdationMenu?pageId=3057">OD/CC/SB/CA Updation</html:link></a>
			</div>
		
			
		<a><bean:message key="label.admin"></bean:message></a>
			<div>
				<a><html:link action="/FrontCounter/SbCaAdminMenu?pageId=3025"><bean:message key="label.sbcaAdmin"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/OdCcAdminMenu?pageId=3026"><bean:message key="label.odccAdmin"></bean:message></html:link></a>
				<a><html:link action="/FrontCounter/PayOrdAdminMenu?pageId=3027"><bean:message key="label.payOrdAdmin"></bean:message></html:link></a>
				
			</div>
			
		<a></a>
			<div>
				<a><html:link action="/GL/GLTrayListMenu?pageId=12034"><%="Tray List"%></html:link></a>
				
			</div>	
		</div>

		<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Term Deposit</a>
			
		<div>
		
		<a>Account Opening </a>
		  <div>
		   <a> <html:link action="/TermDeposit/AccountOpeningMenu?pageId=13001&value=1">Deposit Account Opening</html:link></a>
		   <a><html:link action="/TermDeposit/AmmendmentsMenu?pageId=13009">Ammendments</html:link></a> 
		   <a><html:link action="/TermDeposit/DepositViewLogMenu?pageId=13004">DepositViewLog</html:link> </a>
		 </div>
		 
		 <a>Deposit Receipts </a>
		 <div>
		    <a><html:link action="/TermDeposit/QueryOnReceiptNoMenu?pageId=13005">QueryOnReceiptNo</html:link></a>
			<a><html:link action="/TermDeposit/ReceiptPrintingMenu?pageId=13006">ReceiptPrinting</html:link></a>
			<a><html:link action="/TermDeposit/ReceiptUpdationMenu?pageId=13007">ReceiptUpdation</html:link></a>
				 
		 </div>	 
			<a>Interest</a>
			<div>
				<a><html:link action="/TermDeposit/InterestCalculationMenu?pageId=13002">Interest Payment</html:link></a>
				<a><html:link action="/TermDeposit/QuarterlyInterestMenu?pageId=13008">QuarterlyInterest</html:link></a>
			</div>
			<a>Deposit Maturity</a>
			<div>
				<a><html:link action="/TermDeposit/TDClosureMenu?pageId=13003">TDClosure</html:link></a>
				<a><html:link action="/TermDeposit/AccountRenewalMenu?pageId=13010">AccountRenewal</html:link></a>
				<a><html:link action="/TermDeposit/AccountRenewalMenu?pageId=13022">AutoRenewal</html:link></a>
				<a><html:link action="/TermDeposit/VoucherPaymentMenu?pageId=13011">VoucherPayment</html:link></a>
				</div>
			 <a>Reports</a>
			<div>
				<a><html:link action="/TermDeposit/TermDepositRenewalNoticeMenu?pageId=13012">TermDepositRenewalNotice</html:link></a>
				<a><html:link action="/TermDeposit/LedgerMenu?pageId=13023">Ledger</html:link></a>
				<a><html:link action="/TermDeposit/PeriodwiseReportMenu?pageId=13013">PeriodWise Report</html:link></a>
			    <a><html:link action="/TermDeposit/QuantumwiseReportMenu?pageId=13014">Quantum Wise Report</html:link></a>
			    <a><html:link action="/TermDeposit/OpenClosedAccountsMenu?pageId=13015">Open Closed Accounts</html:link></a>
			   <a><html:link action="/TermDeposit/AutoRenewalListMenu?pageId=13016">AutoRenewalList</html:link></a>
			   
			   <a><html:link action="/TermDeposit/TDPassbookMenu?pageId=13017">TDPassbook</html:link></a>
			   <a><html:link action="/TermDeposit/MISReportMenu?pageId=13018">MISReport</html:link></a>
			   <a><html:link action="/TermDeposit/MaturityLedgerReportMenu?pageId=13019">MaturityLedgerReport</html:link></a>
			   <a><html:link action="/TermDeposit/InterestAccruedReportMenu?pageId=13020">InterestAccruedReport</html:link></a>
			   <a><html:link action="/TermDeposit/AdminMenu?pageId=13021">Admin</html:link></a>
			</div>
			
		</div>		
		
	<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Loans</a>
			
		<div>
		<a>Granting</a>
		<div>
		<a><html:link action="/Loans/LoanApplicationMenu?pageidentity.pageId=5001">Loan Application DE</html:link></a>
		<a><html:link action="/Loans/SanctioningMenu?pageidentity.pageId=5040">Sanctioning DE</html:link></a>
		<a><html:link action="/Loans/DisbursementMenu?pageidentity.pageId=5099">Disbursement DE</html:link></a>
		<a><html:link action="/Loans/ReschedulingMenu?pageidentity.pageId=5014">Rescheduling</html:link></a>
		
		</div>
		<a>Recovery</a>
		<div>
			<a><html:link action="/Loans/LoanRecoveryMenu?pageidentity.pageId=5017">Query On Loan Status</html:link></a>
			<a><html:link action="/Loans/LoanDailyPostingMenu?pageidentity.pageId=5010">LoanDailyPosting</html:link></a>
			<a><html:link action="/Loans/LoanPassBookMenu?pageidentity.pageId=5042">Loan PassBook</html:link></a>
		</div>
		
		<a>Loan Activities</a>
		<div>
		<a><html:link action="/Loans/OtherChargesMenu?pageidentity.pageId=5012">O/C DE</html:link> </a>
		<a><html:link action="/Loans/LoanDocsUpdationMenu?pageidentity.pageId=5026">Loan Documentation Updation</html:link></a>		
		<a><html:link action="/Loans/LoanLeadgerMenu?pageidentity.pageId=5028">Loan Leadger</html:link></a>
		<a><html:link action="/Loans/LoanSplIntRateUpdnMenu?pageidentity.pageId=5031">Spl Int Rate Updation</html:link></a>
		<a><html:link action="/Loans/LoanMeetingAgendaMenu?pageidentity.pageId=5030">Loan Meeting Agenda Printing</html:link></a>
		<a><html:link action="/Loans/ReverseRecoveryMenu?pageidentity.pageId=5011">ReverseRecovery</html:link></a>
		</div>
		<a>Process</a>
		<div>
		<a><html:link action="/Loans/NPAProcessMenu?pageidentity.pageId=5043">NPAProcess</html:link></a>
		<a>DCB Process</a>
		<div>
			<a><html:link action="/Loans/DCBProcessMenu?pageidentity.pageId=5041">DCBProcess</html:link></a>
			<a><html:link action="/Loans/DCBSchedulingMenu?pageidentity.pageId=5038">DCBScheduling</html:link></a>
			<a><html:link action="/Loans/DCBSummaryMenu?pageidentity.pageId=5039">DCBSummary</html:link></a>
			<a><html:link action="/Loans/DCBMaintenanceMenu?pageidentity.pageId=5040">DCBMaintenance</html:link></a>
		</div>
		</div>
		<a>Reports</a>
		<div>
		<a><html:link action="/Loans/InterestAccuredReportMenu?pageidentity.pageId=5015">InterestAccuredReport</html:link></a>
		<a><html:link action="/Loans/OpenClosedStatMenu?pageidentity.pageId=5016">Open-ClosedStat</html:link></a>
		<a><html:link action="/Loans/PSWSProcessingMenu?pageidentity.pageId=5035">PSWSProcessing</html:link></a>
		<a><html:link action="/Loans/PSWSScheduleMenu?pageidentity.pageId=5036">PSWSScheduling</html:link></a>
		<a><html:link action="/Loans/JewelReportMenu?pageidentity.pageId=5037">JewelReport</html:link></a>
        <a><html:link action="/Loans/NPAReportMenu?pageidentity.pageId=5047">NPAReport</html:link></a>		
        <a><html:link action="/Loans/NPALoanSummaryMenu?pageidentity.pageId=5025">NPASummary</html:link></a>
		<a><html:link action="/Loans/OCReportMenu?pageidentity.pageId=5023">OtherChargesReport</html:link></a>
		<a><html:link action="/Loans/ODReportMenu?pageidentity.pageId=5024">OverDueReport</html:link></a>
		</div>
		
		<a>Loan Monitor</a>
		<div>
		<a><html:link action="/Loans/PenalIntFixMenu?pageidentity.pageId=5033">Penal Int Fix</html:link></a>		
		<a><html:link action="/Loans/ActionDueReportMenu?pageidentity.pageId=5034">ActionDueReport</html:link></a>
		<a><html:link action="/Loans/LoanDefaultProcessingMenu?pageidentity.pageId=5044">LoanDefaulterProcessing</html:link></a>
		<a><html:link action="/Loans/NPAAdminMenu?pageidentity.pageId=5092">NPA Admin</html:link></a>
		</div>
		
		
		
		
		<a>Loan Admin</a>
		<div>
		<a>
			<html:link action="/Loans/LoanAdminMenu?pageidentity.pageId=5045">Loan Admin</html:link>
		</a>
		<a>
			<html:link action="/Loans/LoanAdminNewMenu?pageidentity.pageId=40001">Loan Admin New</html:link>
		</a>		
		</div>
		
		
		<a>Interest Calculation</a>
		<div>
	 	<a><html:link action="/Loans/AutomaticRecoveryMenu?pageidentity.pageId=5046">Automatic Recovery</html:link></a>
		<a><html:link action="/Loans/QuarterlyInterestCalcMenu?pageidentity.pageId=5048">Quarterly Interest Calculation</html:link></a>
		
		</div>
		
		
		
			
		</div>	
<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Clearing</a>
			
		<div>
		<a>Outward</a>
		
		<div>
		<a><html:link action="/Clearing/ChequeDeposition?pageId=7001"> ChequeDeposition </html:link></a>
		<a><html:link action="/Clearing/CombinedChequeMLink?pageId=7002">CombinedCheque</html:link> </a>
		<a><html:link action="/Clearing/ClearingIdentificationM?pageId=7003">ChequeIdentify</html:link> </a>
		<a><html:link action="/Clearing/ChequeDiscountingMLink?pageId=7004">ChequeDiscounting</html:link></a>
		<a><html:link action="/Clearing/OutStationChequeMLink?pageId=7005">OutStationCheque</html:link></a>
		<a><html:link action="/Clearing/ClearingDispatchMLink?pageId=7006">ChequeDispatch</html:link>   </a>
		<a><html:link action="/Clearing/SelectivePostMLink?pageId=7008">SelectivePost</html:link> </a>
		<a><html:link action="/Clearing/ClearingPostMLink?pageId=7010">ClearingPost</html:link> </a>
		
		</div>
		
		
		<a>Inward</a>
		<div>
		<a><html:link action="/Clearing/RecievedChequeEntryMLink?pageId=7007">ReceivedChequeEntry</html:link></a>
		<a><html:link action="/Clearing/BouncedChequePostMLink?pageId=7011">ReceivedChequePost</html:link></a>
		</div>	
		
		<a>Bounce</a>
		<div>
		<a><html:link action="/Clearing/LateReturnsMLink?pageId=7012">LateReturns</html:link></a>		
		<a><html:link action="/Clearing/MailingChargesMLink?pageId=7013">MailingCharges</html:link></a>
		</div>
		
		<a>InterBranch</a>
		<div>
		<a><html:link action="/Clearing/ackDataEntryMLink?pageId=7015">AcknowledgementEntry</html:link></a>
		<a><html:link action="/Clearing/BundledDataEntryMlink?pageId=7016">BundledEntry</html:link></a>
		<a><html:link action="/Clearing/AckReconciliationMlink?pageId=7017">AcknowledgementReconciliation</html:link></a>
		<a><html:link action="/Clearing/unIdentifyMlink?pageId=7018">ChequeUnidentify</html:link> </a>
		<a><html:link action="/Clearing/ChequeWithdrawalMlink?pageId=7019">ChequeWithdrawal</html:link> </a>
		</div>	
		
		
		<a>Ecs</a>
		<div>
		<a><html:link action="/Clearing/ReceivedECSMLink?pageId=7027">ReceivedEcs</html:link></a>		
		</div>
		
		
		<html:link action="/Clearing/AdminMlink?pageId=7020">Admin</html:link>
		
		
		<a>Reports</a>
		<div>
		<a><html:link action="/Clearing/InwardstmtMlink?pageId=7022">InwardStatments</html:link></a>
		<a><html:link action="/Clearing/Outwardstmtlink?pageId=7023">OutwardStatments</html:link></a>
		<a><html:link action="/Clearing/Bouncedreglink?pageId=7024">BouncedRegister</html:link></a>
		<a><html:link action="/Clearing/ControlnoDetail?pageId=7026">ControlNoDetails</html:link></a>
		<a><html:link action="/Clearing/BouncedletterLink?pageId=7028">BouncedLetter</html:link></a>
		<a><html:link action="/Clearing/OutStationLetterMLink?pageId=7030">OutStationLetter</html:link></a>
		
		</div>	
		
		</div>
<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Pygmy Deposit</a>
			
		<div>
			<a>Pygmy Open</a>
			<div>
			    <a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentOpeningMenu?pageid=8801&value=1">AgentOpening</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyOpeningMenu?pageid=8002&value=1">PygmyOpening</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentClosingMenu?pageid=8010&value=1">AgentClosing</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentChangeMenu?pageid=8826">AgentChanging</html:link></a>
				
			</div>
			<a>Remittance</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/DailyRemittanceMenu?pageid=8016">DailyRemittance</html:link></a>
			</div>
			<a>Withdrawal</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyWithdrawalMenu?pageid=8003&value=1">PygmyWithdrawal</html:link></a>
			</div>
			<a>Interest</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyInterestCalculationMenu?pageid=8011">Pygmy Interest Calculation</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/InterestRegisteredMenu?pageid=8004">Interest Registered Report</html:link></a>
			</div>
			<a>Reports</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/OpenCloseMenu?pageid=8005">Open/Close Report</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AcknowledgementMenu?pageid=8013">Acknowledgement Printing</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentRemittanceMenu?pageid=8006">Agent Remittance Report</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/MonthlyRemittanceMenu?pageid=8007">Monthly Remittance Report</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PassBookMenu?pageid=8008">Pass Book</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/RegisterPrintingMenu?pageid=8009">Register Printing</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/LedgerPrintingMenu?pageid=8012">Ledger Printing</html:link></a>
				
				
			</div>  
			
			<a>Admin</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentCommissionMenu?pageid=8014">Agent Commission Rates</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PygmyIntMenu?pageid=8015">Pygmy Int Rate</html:link></a>
			</div>
			<a>Master Updation</a>
			<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/AgentUpdateMenu?pageid=8017">Agent Master Update</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Pygmy/PUpdateMenu?pageid=8090">Pygmy Master Update</html:link></a>
				
			</div>		
		</div>	

	<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Loans on Deposit</a>
				
		<div>
			<a>Granting Of LD</a>
		
		<div>
			<html:link action="/LoansOnDepositMenuLink?pageId=6001&value=1">LoansApplicationDE</html:link>
		</div>
		
		<a>Reports</a>
		
		<div>
            <html:link action="/LDPassbookMenuLink?pageId=6008">PassBook</html:link>
            <html:link action="/LDOpenClosedMenuLink?pageId=6009">OpenClosedAccounts</html:link>
            <html:link action="/LDIntrestAccruedReportMenuLink?pageId=6011">IntrestAccruedReport</html:link>
            <html:link action="/LDMaturityListMenuLink?pageId=6012">MaturityList</html:link>
            <html:link action="/LDMaturityExcedingMatListMenuLink?pageId=6013">LDExcedMaturityList</html:link>
          
		</div>
		
		<a>  <html:link action="/LDRecoveryMenuLink?pageId=6004&value=1">Recovery</html:link></a>
			</div>	
	
<!--<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Lockers</a>
			
		<div>
		<a>Lockers</a>
		
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LKIssueMenuLink?pageId=9001">LockerIssue</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LKSurrenderMenuLink?pageId=9004">LockerSurrender</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/LKTableMenuLink?pageId=9007">LockerTable</html:link> </a>		
		</div>
		
		<a>Others</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"> <html:link action="/LKOperationMenuLink?pageId=9003">LockerOperations</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/LKExtensionMenuLink?pageId=9002">LockerExtension</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/LKAutoExtensionMenuLink?pageId=9006">LockerAutoExtension</html:link></a>		
		
		</div>	
		
		
		
		<a>Admin</a>
		<div>
	 	<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerDEMenuLink?pageId=00">LockerDataEntry</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerDataEntryMenuLink?pageId=9010">LockerDataEntry</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/LKTypesMenuLink?pageId=9005">LockerTypes</html:link> </a>		
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/RateAdminMLink?pageId=9008" >LockerRateDefine</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LKMaserUpdateMLink?pageId=9020">LockerMasterUpdate</html:link></a>
		
		</div>
		
		<a>Reports</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LKPassBookMLink?pageId=9011">LockerPassBook</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerOwnerReportMLink?pageId=9012">LockerOwnerReport</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerOperationReportMLink?pageId=9013">LockerOperationReport</html:link> </a>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerOpenClosedReportMLink?pageId=9014">LockerOpenClosedReport</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerRentDueReportMLink?pageId=9015">LockerRentDueReport</html:link></a>	
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LockerRentCollectedReportMLink?pageId=9016">LockerRentCollectedReport</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/LKRemainderNoticeMLink?pageId=9017">LockerRemainderNoticeReport</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/NotSurrenderedReportMLink?pageId=9018">NotSurrender</html:link> </a>
		
		</div>
		
		
		
		
		<a>Verification</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"> <html:link action="/lockers/LockerSurrenderVerificationMLink?pageId=9033">LockerSurrenderVerify </html:link>   </a>
		<a style="border-width:1px 1px 1px 1px;"> <html:link action="/LKExtensionMenuLink?pageId=9034">LockerExteVerify </html:link>   </a>
		<a style="border-width:1px 1px 1px 1px;"> <html:link action="/lockers/LockerOperationVerificationMLink?pageId=9031">LockerOperationVerify </html:link>   </a>
		<a style="border-width:1px 1px 1px 1px;"> <html:link action="/lockers/LockerIssueVerificationMLink?pageId=9030">LockerIssueVerify </html:link>   </a>
		
		</div>
	
		
		</div>			 
		  
--><a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Back Office</a>
			
	<div>	
	    <a>Voucher Creation</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/PaymentVoucherMenu?pageId=11016">Payment Voucher</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/TransferVoucherMenu?pageId=11018">Transfer Voucher</html:link> </a>
				
		</div>
	
		<a>Miscellaneous Receipts</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/UnPresentedChequesMenu?pageId=11003">UnPresentedCheques</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/ChequeRegisterPrintingReportMenu?pageId=11004">ChequeRegisterPrintingReport</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/OpenedClosedAccountsPrintingReportMenu?pageId=11005">OpenedClosedAccountsPrintingReport</html:link> </a>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/OdccbalMenu?pageId=11017">ODCC Balance</html:link> </a>		
		</div>		
		
		<a>Transaction Printing</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/VoucherScheduleMenu?pageId=11009">VoucherSchedule</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/TransactionSummaryMenu?pageId=11010">TransactionSummary</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SBCALedgerMenu?pageId=11011">SBCALedger</html:link> </a>		
				
		</div>	
		
		
		<a>Standing Instructions</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIEntryMenu?pageId=11001">SIEntry</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIDeleteMenu?pageId=11002">SIDeletion</html:link> </a>
        <a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIQueryMenu?pageId=11006">SIQuery</html:link> </a>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIRegisterMenu?pageId=11007">SIRegister</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIDueDoneMenu?pageId=11008">SIDueDone</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIParametersMenu?pageId=11014">SIParameters</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/SIExecutionMenu?pageId=11015">SIExecution</html:link></a>	
		
		</div>		
		<a>Closing Balance</a>
		<div>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/ClosingBalanceReportMenu?pageId=11012">ClosingBalanceReport</html:link> </a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/BackOffice/ClosingBalanceSummaryMenu?pageId=11013">ClosingBalanceSummary</html:link> </a>
        				
		</div>	
	</div>	
		
<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">Administrator</a>
			
		<div>
		<a >User</a>
			<div>
			<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/CreateUsersMenu?pageId=10008">Create User</html:link></a>
		    <a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/ChangePwdMenu?pageId=10009">Change Password</html:link></a>
		
				<!--<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/CreateUserMenu?pageId=10001">Create User</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/ChangePasswordMenu?pageId=10002">Change Password</html:link></a>
				
				
				--><a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/ViewUsersMenu?pageId=10003">View User's</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/IpConfMenu?pageId=10004">IP Configuration</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AccessToTerminalsMenu?pageId=10005">Access to Terminals</html:link></a>
				
			</div>
	<!--	<a>GL & Module Admin</a>
				<div>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmShareModuleMenu?pageId=10012">Share Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmSBModuleMenu?pageId=10013">Savings Account Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmFDModuleMenu?pageId=10014">Fixed Deposit Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmRDModuleMenu?pageId=10015">Recurring Deposit Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmRIModuleMenu?pageId=10016">Re-Investment Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmPygmyModuleMenu?pageId=10017">Pygmy Deposit Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmCAModuleMenu?pageId=10018">Current Account Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmClearingModuleMenu?pageId=10019">Clearing Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmODModuleMenu?pageId=10020">OverDraw Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmPOModuleMenu?pageId=10021">Pay Order Module</html:link></a>
				<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AdmCCModuleMenu?pageId=10022">Cash Credit Module</html:link></a>
				</div>
		--><a>Access Rights</a>
		<div>
		<!--<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/AccessRightsforModulesMenu?pageId=10030">Access Rights For Modules</html:link></a>
		--><a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/NewAccessRightsMenu?pageId=10023">Access Rights</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/FormsDetailMenu?pageId=10026">Forms Details Dataentry</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/UserRoleAssignmentMenu?pageId=10025">User Role Assignment</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/NewSecurityandAdminMenu?pageId=10024">Security And Admin</html:link></a>
		
		<!--<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/TerminalsDetailsMenu?pageId=10011">Terminals Details</html:link></a>
		--></div>
		
		
				
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/HolidayMasterMenu?pageId=10007">Holiday Master</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/Administrator/UserActivityMenu?pageId=10006">User Activity</html:link></a>
		
		</div>			
<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">General Ledger</a>
			
		<div>
		<a>Daily Routine</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLDailyPostingMenu?pageId=12001">Daily Posting</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLDayCloseMenu?pageId=12002">Day Close</html:link></a>
		</div>
       	<a>Monthly Routine</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyPostingMenu?pageId=12003">Monthly Posting</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyCloseMenu?pageId=12004">Monthly Close</html:link></a>
       	</div>
       	<a>YearClosing</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLYearCloseMenu?pageId=12005">PL Posting & YearClose(HO)</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLYearClosingCFMenu?pageId=12006">Carry Forward of Bal</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLBranchPLPostingMenu?pageId=12007">PL Posting & YearClose(BR)</html:link></a>
       	</div>
       	<a>Day Wise Reports</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLDayBookMenu?pageId=12008">Day Book</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLScheduleMenu?pageId=12009">GL Schedule</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLTrfScrollPrintingMenu?pageId=12010">Trf Scroll Printing</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLVoucherSlipPrintingMenu?pageId=12011">Voucher Slip Printing</html:link></a>
       	</div>
       	<a>Month Wise Reports</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyGlScheduleMenu?pageId=12012">Monthly GL Schedule</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyBalanceSheetMenu?pageId=12013">Balance Sheet</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyProfitLossMenu?pageId=12014">Profit & Loss</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyTrialBalanceMenu?pageId=12015">Trial Balance</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyRecieptsPaymentsMenu?pageId=12016">Reciepts & Payments</html:link></a>
       	</div>
       	<a>Day Wise Consolidated Reports</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLConsolidatedDayBookMenu?pageId=12017">Consolidated Day Book</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLConsolidatedVoucherSlipMenu?pageId=12018">Consolidated Voucher Slip</html:link></a>
       	</div>
       	<a>Month Wise Consolidated Reports</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyConsolidatedGlScheduleMenu?pageId=12019">Consolidated GL Schedule</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyConsolidatedTrialBalanceMenu?pageId=12020">Consolidated Trial Balance</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyConsolidatedRPMenu?pageId=12021">Consolidated R&P</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyConsolidatedPLMenu?pageId=12022">Consolidated P&L a\c</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLMonthlyConsolidatedBalanceSheetMenu?pageId=12023">Consolidated Balance Sheet</html:link></a>
       	</div>
       
       	<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/AdminMenu?pageId=12033">GLAdmin</html:link></a>
       	<a>RBI Returns</a>
		<div>		
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm1MasterMenu?pageId=12025">RBI Form 1 Master</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIMarkingDateEntryMenu?pageId=12026">RBI Marking Date Entry</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLForm1PostingMenu?pageId=12040">Form 1 Posting</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm1LinkUpdationMenu?pageId=12027">Form1Link Updation</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm1ReportsMenu?pageId=12028">Form1 Reports</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBICRRSLRReportMenu?pageId=12029">CRSLSR Report</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBICRRSLRSubScheduleMenu?pageId=12030">CRSLSR Sub Schedule</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm9PostingMenu?pageId=12035">Form IX Posting</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm9ReportMenu?pageId=12032">Form IX Report</html:link></a>
		<a style="border-width:1px 1px 1px 1px;"><html:link action="/GL/GLRBIForm9InputUpdationMenu?pageId=12031">Form IX Input Updation</html:link></a>
		
		
       	</div>

			
<span class="qmclear"></span></div>

<!--<a href="javascript:void(0)" style="border-width:1px 1px 1px 1px;">LogOut</a>
		
		<div>
			<a style="border-width:1px 1px 1px 1px;"><html:link action="/logout.do">LogOut</html:link></a>
		
		</div>	

--><!--%%%%%%%%%%%% QuickMenu Create Menu (menu id, is vertical, show timer (ms), hide timer (ms), on click) %%%%%%%%%%%*-->
<script type="text/JavaScript">qm_create(0,false,0,500,false)</script>



</body>
</html>
