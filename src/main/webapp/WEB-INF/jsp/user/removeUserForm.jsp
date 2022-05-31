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
					url: "/group/removeUser",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						alert("저장되었습니다.");
						parent.$("#groupList").data("kendoGrid").dataSource.page(1);
						parent.$("#removeFormLayer").data("kendoWindow").close();
					}
				});
			}//confirm if end
		}else{
			$.fieldErrorMessage($("#inputForm").data("kendoValidator"));
		}
	}
	
	$(document).ready(function(){
		
		$("#inputForm").kendoValidator({validateOnBlur: false});
		
		$("select[name=siteId]").kendoDropDownList();
		
		$("#btnSave").on("click" , function(){
				save();
		});
		
		$("#btnClose").on("click" , function(){
				parent.$("#removeFormLayer").data("kendoWindow").close();			
		});
	});
</script>
</head>
<body class="index">
	<form id="inputForm" name="inputForm" method="post" >
	<input type="hidden" id="id" name="id" value="${result.id}"/>
	<div class="tableType2">
	<table id="mainInfo" class="table_sc">
		<colgroup>
			<col width="30%" />
			<col width="70%" />
		</colgroup>
		<tr height="30px">
			<th class="TBC_th">사용여부</th>
			<td class="TBC_td">
				<input type="radio" class="k-radio" id="active" name="isDel" value="false" ${result.isDel == 'fasle' ? 'checked=true' : ''}><label class="k-radio-label" for="active">Yes</label>
				<input type="radio" class="k-radio" id="inactive" name="isDel" value="true" ${result.isDel == 'true' ? 'checked=true' : ''}><label class="k-radio-label" for="inactive">No</label>
				
			</td>
		</tr>
	</table>
	</div>
	</form>
	<div class="btnBox">
		<span id="btnClose" class="btnLarge"><button>Cancel</button></span>
		<span id="btnSave" class="btnLarge blue"><button>OK</button></span>
	</div>
</body>
</html>
