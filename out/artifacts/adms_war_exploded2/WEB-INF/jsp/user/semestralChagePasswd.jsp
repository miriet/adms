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
<title>비밀번호 변경화면</title>
<script>
//다음에 변경
function delay(){
	if(confirm('다음에 변경하시겠습니까?')){
		$.showLoadingMessage();
		var id = $("#itx_id").val();
		$("#inputForm").ajaxSubmit({
			url: "/user/changPwdDelay/"+id,
			type:'POST',
			success:function(data, statusText, xhr){
				$.showClosingMessage();
				alert("저장되었습니다.");
				window.location.href="../user";
				
			},
			error: function(data) {
				alert(data);
			}
		});
	}
}
//비밀번화 변경
function goProcess(){
	$.showLoadingMessage();
	var id = $("#itx_id").val();
	$("#inputForm").ajaxSubmit({
		url: "/user/changPwdProcess",
		type:'POST',
		success:function(data, statusText, xhr){
			if(data["code"] == "0"){
				$.showClosingMessage();
				alert(data["message"]);
				$("#inputForm").attr("target","_self");
				$("#inputForm").attr("action","../user");
				$("#inputForm").submit();
			}
			else if(data["code"] == "-1"){
				$.showClosingMessage();
				alert(data["message"]);
			}
		},
		error: function(request,status,error) {
			$.showClosingMessage();
			alert("오류가 발생하였습니다.다시 진행해 주십시요.");
		}
	});
}
</script>
</head>
<body class="index">

<form name="inputForm" id="inputForm" method="post" >
<input type="hidden" name="userId" id="itx_id" value="${userInfo.id }">
<div class="login" style="padding:0 0;width:680px;margin:5% auto;">
	<h4 style="text-align:center; padding:20px 0;font-weight:600;font-size: 24px;">기간경과에 따른 비밀번호  변경안내</h4>
	

	<div class="id" style="padding:20px 58px;">
		<ul >
			<li style="list-style:disc;">6개월마다 비밀번호변경을 해야 합니다.</li>
			<li style="list-style:disc;">최소 8자리 이상  + 영어 대문자/소문자 /숫자/특수문자 3가지 조합하여 사용 가능합니다.</li>
			<li style="list-style:disc;">계정명, 일련번호 제외(예시-123,111,abc,aaa 등)</li>
			<li style="list-style:disc;">동일 비밀번호 사용제한(2개 교대 사용방지)</li>
		</ul>
	</div>
	
	
	<table style="border-top:1px solid #aaa;border-bottom:1px solid #aaa;margin:10px auto;width:600px;">
		<colgroup>
			<col width="40%" />
			<col width="%" />
		</colgroup>
		<tbody>
			<tr style="height:30px;">
				<th style="text-align:center;padding:10px 0;">현재 비밀번호</th>
				<td><input type="password" name="passwd" style="display:inline-block;"></td>
			</tr>
			<tr style="height:30px;">
				<th style="text-align:center;padding:10px 0;">새 비밀번호</th>
				<td><input type="password" name="newPasswd"></td>
			</tr>
			<tr style="height:30px;">
				<th style="text-align:center;padding:10px 0;">새 비밀번호 확인</th>
				<td><input type="password" name="newconfirmPasswd"></td>
			</tr>

		</tbody>
	</table>
	<div style="text-align:center;">
		<span><a class="btnLarge blue" href="javascript:void(0)" onclick="delay();"><font color="#fff">다음에 변경하기</font></a></span>
		<span><a class="btnLarge blue" href="javascript:void(0)" onclick="goProcess();"><font color="#fff">변경하기</font></a></span>
	</div>
</div>
</form>

</body>
</html>
