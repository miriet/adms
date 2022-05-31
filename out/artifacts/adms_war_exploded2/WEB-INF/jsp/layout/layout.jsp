<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title"/></title>
<tiles:insertAttribute name="header"/>
</head>
<style>
#panelbar p img {
    margin-left:10px;margin-right:3px;
}
</style>
<script>
	function fnLogOut(){
		$("#mainForm").attr("action","/logout");
		$("#mainForm").submit();
	}
	
	$(document).ready(function(){
		$("#logout").click(function(){
			fnLogOut();
		});
	});
</script>
<body>
<spring:eval expression="T(postech.adms.common.context.CmsRequestContext).getCmsRequestContext().getMenuPath()" var="menuPath" />
<div id="errorLayer" class="layerPop" style="display:none;">
	<div class="layerContent">
		<div class="erorrBox" style="height:200px">
			<p id="errorMessage" class="title"></p>
			<span class="btnError btnLarge blue"><button>Detail View</button></span>
		</div>
		<div id="errorTrace" class="detailView">
			
		</div>
	</div>
</div>
<form name="mainForm" id="mainForm" method="post" ></form>
<!-- Header top-->
	<div class="header">
		<div class="inner">
			<h1><img src="/static/images/postech_logo.png" width="220" height="65" alt="postech"><%-- <sec:authentication property="principal.name"/> --%></h1>
			<%--<h1><img src="/static/images/postech_logo.png" width="65" height="65" alt="postech">&lt;%&ndash; <sec:authentication property="principal.name"/> &ndash;%&gt;</h1>--%>
			<!-- Information Box -->
			<div class="infor">
				<p class="memberInfor"><img src="/static/images/img_user.png" alt=""><sec:authentication property="principal.name"/>(<sec:authentication property="principal.userName"/>)</p>
				<span class="logout"><button id="logout" style="outline:none;">Log-out</button></span>
			</div>
			<!-- //Information Box -->
		</div>
		<!-- Location Information -->
		<div class="pageLoc">
			<p class="pageLocation">${menuPath}</p>
		</div>
		<!-- //Location Information -->
	</div>	
	<!-- //Header -->
	
	<div class="wrap">
		<!-- Navigation Area left menu-->
		<div class="">
			<tiles:insertAttribute name="menu"/>
		</div>
		<!-- //Navigation Area -->
		
		<!-- Content Area -->
		<div class="content">
			<tiles:insertAttribute name="body"/>
			

		</div>
		<!-- //Content Area -->

		
	</div>







<iframe name="ifDownloadHidden" style="display:none;"></iframe>
<iframe name="ifExcel" 	style="display:none;"></iframe>
</body>
</html>