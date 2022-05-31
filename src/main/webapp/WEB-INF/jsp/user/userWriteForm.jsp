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
				var groupType = $("#groupId option:selected").val();
				if (!(groupType =='4' || groupType =='5'))$("#deptId option:selected").val("");
				$.showLoadingMessage();						
				$("#inputForm").ajaxSubmit({
					url: "/user/insert",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						alert("저장되었습니다.");
						parent.fnSearch();
						parent.$("#writeFormLayer").data("kendoWindow").close();
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

		$("#btnSave").on("click", function() {
				save();
		});

		$("#btnClose").on("click", function() {
				parent.$("#writeFormLayer").data("kendoWindow").close();
		});
		
		var deptType = $("#groupId").val();
		if(deptType == '4' || deptType == '5'){
			$("#detpShow").css("display","");
		}
	});
function changeType(type){
	if (type == '4' || type == '5' ) $("#detpShow").css("display","");
	else $("#detpShow").css("display","none");
}
</script>
</head>
<body class="index">
	<form id="inputForm" name="inputForm" method="post" >
	<div class="tableType2">
	<table id="mainInfo" class="table_sc">
		<colgroup>
			<col width="30%" />
			<col width="70%" />
		</colgroup>
		<tr height="30px">
			<th class="TBC_th">ID</th>
			<td class="TBC_td">
				<%-- <input id="userName" name="userName" style="width:98%" class="k-textbox k-invalid" value="" required validationMessage='<spring:message code="cms.validation.field.required" arguments="ID"/>'/> --%>
				<input id="loginId" name="loginId" style="width:98%" class="k-textbox k-invalid" value="" required/>
			</td>
			<form:errors path="userName" element="div" />
		</tr>
		<tr height="30px">
			<th class="TBC_th">사용자그룹</th>
			<td class="TBC_td">				
				<c:if test="${fn:length(groupList) > 1}">
				<select name="adminGroup.groupId" id="groupId" style="width:200px" required onChange="javascript:changeType(this.value)">
					<c:forEach items="${groupList}" var="group" varStatus="status">
						<option value="${group.groupId}">${group.groupName}</option>
					</c:forEach>
				</select>					
				</c:if>
		</td>
		<tr id="detpShow" height="30px" style="display:none">
				<th class="TBC_th">학과</th>
				<td class="TBC_td">				
					<c:if test="${fn:length(deptList) > 1}">
					<select name="department.id" id="deptId" style="width:200px" required >
						<c:forEach items="${deptList}" var="dept" varStatus="status">
							<option value="${dept.id}">${dept.deptName}</option>
						</c:forEach>
					</select>					
					</c:if>
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">사용자명</th>
			<td class="TBC_td">
				<%-- <input id="name" name="name" style="width:98%" class="k-textbox k-invalid" value="" required validationMessage='<spring:message code="cms.validation.field.required" arguments="사용자명"/>'/> --%>
				<input id="name" name="name" style="width:98%" class="k-textbox k-invalid" value="" required />
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">이메일</th>
			<td class="TBC_td">
				<%-- <input type="email" id="email" name="email" style="width:98%" class="k-textbox k-invalid" value="" required data-email-msg='<spring:message code="cms.validation.field.email"/>'/> --%>
				<input type="email" id="email" name="email" style="width:98%" class="k-textbox k-invalid" value="" required />
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
		<tr height="30px">
			<th class="TBC_th">사용여부</th>
			<td class="TBC_td">
				<input type="radio" name="isDel" id="isDel1" class="k-radio" value="false" checked><label class="k-radio-label" for="isDel1" >Yes</label>
				&nbsp;
				<input type="radio" name="isDel" id="isDel2" class="k-radio" value="true"><label class="k-radio-label" for="isDel2" >No</label>
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
