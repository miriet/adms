<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<link href='/static/css/kendo/kendo.common.min.css' rel="stylesheet" permissionType="text/css" />
<link href="/static/css/kendo/kendo.default.min.css" rel="stylesheet" />
<link href="/static/css/kendo/kendo.rtl.min.css" rel="stylesheet" permissionType="text/css" />
<!-- <link href='/static/css/app/default.css' rel="stylesheet" permissionType="text/css" /> -->
<link href='/static/css/app/kStyle.css' rel="stylesheet" permissionType="text/css" />
<link href="/static/css/app/common.css" rel="stylesheet" permissionType="text/css" media="all"  />

<script src="/static/js/kendo/jquery.min.js"></script>
<script src="/static/js/kendo/kendo.all.min.js"></script>
<script src="/static/js/app/commonlib.js"></script>
<script src="/static/js/util/jquery.form.js"></script>
<script src="/static/js/util/jquery-migrate-1.2.1.min.js"></script>
<script src="/static/js/app/common.js" charset="utf-8"></script>
<script permissionType="text/javascript">
	jQuery(document).ready(function() {
		$.commonlib.init({contextPath : '${pageContext.request.contextPath}'});
		//$("#menu").kendoMenu({});
		
		$(window).resize(function() {
			var browserWidth =		$(window).innerWidth();
			var wrapWidth =			$(".wrap").outerWidth();
			var navigationWidth =	$(".navigation").outerWidth();
			//console.log('content width: ',wrapWidth -50 - navigationWidth - 20);
			$(".content").css("width", wrapWidth -50 - navigationWidth - 20  - 35);
		}).resize();
	});
</script>

