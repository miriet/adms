<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<script>
	function goADMS() {
	    location.href = "/members";
	}
	
	$(document).ready(function(){
	
	});
</script>

</head>
<div id="result"></div>
<body>
    <!-- search start -->
    <div class="searchBox">
        <div class="optionBox">
            <label class="pl40" style="width:100%;">
                <c:if test="${result eq '100'}">
                    [SAP => ADMS] 회원정보가 저장되었습니다.
                </c:if>
                <c:if test="${result eq '109'}">
                    [SAP => ADMS] 회원정보 처리대상이 없습니다.
                </c:if>
                <c:if test="${result eq '199'}">
                    [SAP => ADMS] 회원정보 저장처리중, 오류가 발생했습니다.
                </c:if>
                <c:if test="${result eq '200'}">
                    [ADMS => SAP] 회원정보가 전송되었습니다.
                </c:if>
                <c:if test="${result eq '209'}">
                    [ADMS => SAP] 회원정보 전송대상이 없습니다.
                </c:if>
                <c:if test="${result eq '299'}">
                    [ADMS => SAP] 회원정보 전송처리중, 오류가 발생했습니다.
                </c:if>
                <a href="javascript:void(0)" onclick="javascript:goADMS()" class="btnLarge blue" style="text-align:center;color:#fff;width:100px;height:35px">Go ADMS</a>
            </label>
        </div>
    </div>
    <!-- search end -->

</body>
</html>
