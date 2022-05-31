<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<% 
	//request.setCharacterEncoding("UTF-8");  //한글깨지면 주석제거
	String inputYn = request.getParameter("inputYn"); 
	String roadFullAddr = request.getParameter("roadFullAddr"); 
	String roadAddrPart1 = request.getParameter("roadAddrPart1"); 
	String roadAddrPart2 = request.getParameter("roadAddrPart2"); 
	String engAddr = request.getParameter("engAddr"); 
	String jibunAddr = request.getParameter("jibunAddr"); 
	String zipNo = request.getParameter("zipNo"); 
	String addrDetail = request.getParameter("addrDetail"); 
	String admCd    = request.getParameter("admCd");
	String rnMgtSn = request.getParameter("rnMgtSn");
	String bdMgtSn  = request.getParameter("bdMgtSn");
	
	////////////////////////////////////////////////////////////
	String siNm = request.getParameter("siNm"); 			//시도명
	String sggNm = request.getParameter("sggNm");			//군구
	String rn = request.getParameter("rn");					//도로명1
	String buldMnnm = request.getParameter("buldMnnm");		//도로명2
	String emdNm = request.getParameter("emdNm");			//도로명3
	String roadAddr = rn +" "+buldMnnm+"("+emdNm+")";		//도로명1+ 도로명2+도로명3
	
%>
</head>
<script language="javascript">
function init(){
	var url = location.href;
	var confmKey = "bnVsbDIwMTUwMTA3MTc0NjQ2";
	var inputYn= "<%=inputYn%>";
	if(inputYn != "Y"){
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망
		//document.form.action="http://10.182.60.22/addrlink/addrLinkUrl.do"; //내부행망
		document.form.submit();
	}else{
		opener.jusoCallBack("<%=siNm%>","<%=sggNm%>","<%=roadAddr%>","<%=zipNo%>","<%=addrDetail%>");
		window.close();
	}
}
</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>
</body>
</html>