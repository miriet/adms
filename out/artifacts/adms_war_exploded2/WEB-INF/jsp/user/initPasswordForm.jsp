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
	function save(){
		if($("#inputForm").data("kendoValidator").validate()) {
			if(confirm('저장하시겠습니까?')){
				$.showLoadingMessage();
						
				$("#inputForm").ajaxSubmit({
					url: "/user/initPassword",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						alert("저장되었습니다.");
						parent.fnSearch();
						parent.$("#initPasswordFormLayer").data("kendoWindow").close();
					},
					error: function(data) {
						alert(data);
					}
				});
			}
		}else{
			$.fieldErrorMessage($("#inputForm").data("kendoValidator"));
		}
	}
	
	$(document).ready(function(){
		
		$("#inputForm").kendoValidator({
			validateOnBlur: false,
			rules: {
				
				verifyPasswords: function(input){
                    var ret = true;
                    if (input.is("[name=matchingPassword]")) {
						ret = input.val() === $("#password").val();
					}
					return ret;
				}
			}
		});
		
		$("#btnSave").on("click", function(){
				save();
		});
		
		$("#btnClose").on("click", function(){
				parent.$("#initPasswordFormLayer").data("kendoWindow").close();
		});
	});
</script>
</head>
<body class="index">
	<form id="inputForm" name="inputForm" method="post" >
	<input type="hidden" id="id" name="id" 		value='${result.id}'/>
	<div class="tableType2">
	<table id="mainInfo" class="table_sc">
		<colgroup>
			<col width="30%" />
			<col width="70%" />
		</colgroup>
		<tr height="30px">
			<th class="TBC_th">ID</th>
			<td class="TBC_td">
				${result.loginId}
			</td>
			<form:errors path="userName" element="div" />
		</tr>
		<tr height="30px">
			<th class="TBC_th">사용자명</th>
			<td class="TBC_td">
				${result.name}
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">비밀번호</th>
			<td class="TBC_td">
				<%-- <input type="password" id="password" name="password" style="width:98%" class="k-textbox k-invalid" value="" required validationMessage='<spring:message code="cms.validation.field.required" arguments="password"/>'/> --%>
				<input type="password" id="password" name="password" style="width:98%" class="k-textbox k-invalid" value="" required />
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">비밀번호확인</th>
			<td class="TBC_td">
				<%-- <input type="password" id="matchingPassword" name="matchingPassword" style="width:98%" class="k-textbox k-invalid" value="" verifyPasswords data-verifyPasswords-msg='<spring:message code="cms.validation.field.password"/>'/> --%>
				<input type="password" id="matchingPassword" name="matchingPassword" style="width:98%" class="k-textbox k-invalid" value="" />
			</td>
		</tr>
		
	</table>
	</div>
	</form>

	<div class="btnBox">
		<span id="btnClose" class="btnLarge"><button>취소</button></span>
		<span id="btnSave" class="btnLarge blue"><button>저장</button></span>
	</div>
</body>
</html>
