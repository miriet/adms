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
function initialGrid(){
	$("#list").kendoGrid({
        dataSource: {
            transport: {
                read: {
                	url : "/user/find",
                	dataType : "json"
                },
                parameterMap: function(data, type){
	            	return {
			     		page: data.page,
			     		take: data.take,
			     		rows : data.pageSize,
			     		loginId : $("#loginId").val(),
			     		name : $("#name").val(),
			     		siteId : $("#siteId").val(),
			     		isDel :  $("#isDel").val()
			     	};
			    }
            },
            schema: {
            	data: "rows",
            	total: "count"
            },
            pageSize: 20,
            serverPaging: true,
            serverFiltering: false,
            serverSorting: false
        },
       	selectable: true,
      	height: 300,
        filterable: false,
        sortable: false,
        pageable: {
            refresh: true
        },
        dataBinding: function(e) {
        	record = (this.dataSource.page() -1) * this.dataSource.pageSize();
        	
       	},
        dataBound: function() {
        	
        },
        columns: [
			{field:"id" ,title:"선택",width:50,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px; ", class:'tableHead'},template: '<input type="checkbox" name="id" id="id" value="#:id#"/>',filterable: {multi:true, checkAll: false }},      
			{field: "loginId", title:"ID", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			{field: "name", title:"관리자명", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			{field: "groupNames", title:"그룹", width: "15%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},			 
			{field: "isDel", title:"사용여부", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
        ]
    });
}

function save(){
	var check_array = [];
	var chk_id = document.getElementsByName("id");
	var length = chk_id.length;
	var checked = 0;
	for(i=0;i<length;i++){
		if(chk_id[i].checked == true){
			check_array.push(Number(chk_id[i].value));
			checked += 1;
		}
	}
	if(checked == 0){
		alert("그룹을 변경할 사용자를 선택해주세요");
	}else{
		$("#selectedUserId").val(check_array);
		$("#inputForm").ajaxSubmit({
			url: "/group/addUser",
			type:'POST',
			success:function(data, statusText, xhr){
				$.showClosingMessage();
				alert("저장되었습니다.");
				parent.$("#groupList").data("kendoGrid").dataSource.page(1);
				parent.$("#addUserFormLayer").data("kendoWindow").close();
			}
		});
	}
}

function fnSearch(){
	$("#list").data("kendoGrid").dataSource.page(1);
}

$(document).ready(function(){
	
	$("#btnSearch").on("click", function(){
			fnSearch();
	});	
		
	$("select[name=siteId]").kendoDropDownList({
		change : function(){
			fnSearch();
		}
	});
	
	$("#btnUpdate").on("click" , function(){
		save();
	});
	
	$("#btnClose").on("click" , function(){
			parent.$("#addUserFormLayer").data("kendoWindow").close();
	});
	$("#isDel").kendoDropDownList();
	
	initialGrid();

	//엔터로 검색하기 
	$('input').keyup(function(e) {
	    if (e.keyCode == 13) fnSearch();
	});
});
</script>
</head>
<div id="result"></div>
<body>
	<form id="inputForm" name="inputForm" method="post" >
	 <input type="hidden" id="groupId" name="groupId" value="${groupId}"/>
	 <input type="hidden" id="selectedUserId" name="selectedUserId" />
	</form>
	
	<!-- search start -->
	<div class="searchBox">
		<div class="optionBox">
			<label class="pl40" style="width:100px;">사용자ID</label>
			<input id="loginId" type="text" name="loginId" value="" class="k-textbox" >
			<label class="pl40" style="width:100px;">관리자명</label>
			<input type="text" id="name" name="name" class="k-textbox" /><br />		
		</div>
		<div class="btnBox">
			<span id="btnSearch" class="btnLarge blue"><button>검색</button></span>
		</div>
	</div>

	
	<!-- search end -->

	<!-- InnerContents01 end  -->
	<div  class="tableType1">
		<table id="list" class="list">
		</table>
	</div>
	<div class="btnBox">
		<span id="btnClose" class="btnLarge"><button>취소</button></span>
		<span id="btnUpdate" class="btnLarge blue"><button>저장</button></span>	
	</div>
</body>
</html>
