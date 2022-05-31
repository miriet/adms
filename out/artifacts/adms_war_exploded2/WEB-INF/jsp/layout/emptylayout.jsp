<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

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
<body >
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
<div style="padding:10px">
	<tiles:insertAttribute name="body"/>
</div>

</body>
</html>