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
			var groupType = $("#groupId option:selected").val();
			if (!(groupType =='4' || groupType =='5'))$("#deptId option:selected").val("");
			if(confirm('저장하시겠습니까?')){
				$.showLoadingMessage();
				$("#inputForm").ajaxSubmit({
					url: "/user/update",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						alert("저장되었습니다.");
						parent.fnSearch();
						parent.$("#updateFormLayer").data("kendoWindow").close();
					}
				});
			}else{
				$.fieldErrorMessage($("#inputForm").data("kendoValidator"));
			}
		}
	}
	
	function userUnlock(){
			if(confirm('계정잠금 해제를 하시겠습니까?')){
				$.showLoadingMessage();
				$("#inputForm").ajaxSubmit({
					url: "/user/userUnlock",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						alert("해제되었습니다.");
						parent.fnSearch();
						parent.$("#updateFormLayer").data("kendoWindow").close();
					}
				});
			}
	}
	
	$(document).ready(function(){
		
		$("#inputForm").kendoValidator({validateOnBlur: false});
		
		$("#siteId").kendoDropDownList();
		
		$("#btnSave").on("click", function(){
				save();
		});
		
		$("#btnUserUnLock").on("click", function(){
			userUnlock();
		});	
		$("#btnClose").on("click", function(){
				parent.$("#updateFormLayer").data("kendoWindow").close();
		});
		
		var groupType = $("#groupId option:selected").val();
		if(groupType == '4'|| groupType == '5') $("#detpShow").css("display","");
		else $("#detpShow").css("display","none");
	});
	
function changeType(type){
	if (type == '4' || type == '5' )$("#detpShow").css("display","");
	else $("#detpShow").css("display","none");
}
</script>
</head>
<body class="index">
	<form id="inputForm" name="inputForm" method="post" >
	<input type="hidden" id="id" name="id" 	value='${result.id}'/>
	<div class="tableType2">
	<table id="mainInfo" class="table_sc">
		<colgroup>
			<col width="30%" />
			<col width="70%" />
		</colgroup>
		<tr height="30px">
			<th class="TBC_th">*ID</th>
			<td class="TBC_td">
				<%-- <input id="loginId" name="loginId" style="width:98%" value="${result.loginId}" class="k-textbox k-invalid" readonly=true required validationMessage='<spring:message code="cms.validation.field.required" arguments="ID"/>'/> --%>
				<input id="loginId" name="loginId" style="width:98%" value="${result.loginId}" class="k-textbox k-invalid" readonly=true required />
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">*사용자명</th>
			<td class="TBC_td">
				<%-- <input id="name" name="name" style="width:98%" class="k-textbox k-invalid" value="${result.name}" required validationMessage='<spring:message code="cms.validation.field.required" arguments="사용자명"/>'/> --%>
				<input id="name" name="name" style="width:98%" class="k-textbox k-invalid" value="${result.name}" required />
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">*이메일</th>
			<td class="TBC_td">
				<%-- <input type="email" id="email" name="email" style="width:98%" class="k-textbox k-invalid" value="${result.email}" required data-email-msg='<spring:message code="cms.validation.field.email"/>'/> --%>
				<input type="email" id="email" name="email" style="width:98%" class="k-textbox k-invalid" value="${result.email}" required />
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">사용자그룹</th>
			<td class="TBC_td">
				
			<c:choose>	
				<c:when test="${fn:length(groupList) > 1}">
				<%-- <select name="groupId" id="groupId" style="width:200px" required validationMessage='<spring:message code="cms.validation.field.required" arguments="사이트"/>'> --%>
				<select name="adminGroup.groupId" id="groupId" style="width:200px" required  onChange="javascript:changeType(this.value)">
					<c:forEach items="${groupList}" var="group" varStatus="status">
						<option value="${group.groupId}" ${result.adminGroup.groupId == group.groupId ? 'selected' : ''}>${group.groupName}</option>
					</c:forEach>
				</select>
				</c:when>
				<c:otherwise>
					${groupList[0].groupName}
					<input type="hidden" name="groupId" value="${groupList[0].groupId}"/>
				</c:otherwise>
			</c:choose>			
			</td>
		</tr>
		<tr id="detpShow" height="30px" style="display:none">
				<th class="TBC_th">학과</th>
				<td class="TBC_td">				
					<c:if test="${fn:length(deptList) > 1}">
					<select name="department.id" id="deptId" style="width:200px" required >
						<c:forEach items="${deptList}" var="dept" varStatus="status">
							<option value="${dept.id}" ${result.department.deptCode == dept.deptCode ? 'selected' : ''} >${dept.deptName}</option>
						</c:forEach>
					</select>					
					</c:if>
			</td>
		</tr>
		<tr height="30px">
			<th class="TBC_th">사용여부</th>
			<td class="TBC_td">
				<input type="radio" name="isDel" id="isDel1" class="k-radio" value="false" ${result.isDel == 'fasle' ? 'checked=true' : ''}><label class="k-radio-label" for="isDel1" >Yes</label>
				&nbsp;
				<input type="radio" name="isDel" id="isDel2" class="k-radio" value="true" ${result.isDel == 'true' ? 'checked=true' : ''}><label class="k-radio-label" for="isDel2" >No</label>
			</td>
		</tr>
		
	</table>
	</div>
	</form>

	<div class="btnBox">
		<span id="btnClose" class="btnLarge"><button>취소</button></span>
		<span id="btnSave" class="btnLarge blue"><button>저장</button></span>
		<c:if test="${userInfo.groupId == '1' }">
			<c:if test="${result.pwdCount == 5 }">
				<span id="btnUserUnLock" class="btnLarge blue"><button>계정잠금해제</button></span>
			</c:if>
		</c:if>
	</div>
</body>
</html>
