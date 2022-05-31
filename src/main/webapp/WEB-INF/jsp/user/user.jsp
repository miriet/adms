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
      	height: 565,
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
			{title:"번호",width:50,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px; ", class:'tableHead'},template: "#=++record #"},
			{field: "loginId", title:"ID", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			{field: "name", title:"관리자명", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			/* {field: "groupNames", title:"그룹", width: "15%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}}, */
			{field: "description", title:"그룹", width: "15%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			{field: "email", title:"이메일", width: "20%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
			{field: "dateCreated", title:"등록일", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}}, 
			{field: "isDel", title:"사용여부", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
			{field:"id",title: "USERID", hidden: true, width: "100px" },
			{ command: { click: openInitPasswordForm, width:60,name: "btnInitPassword", text: "<img src='/static/images/btn_write.png'>" },title:"비밀번호",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}},
			{ command: { click: openUpdateForm, width:60,name: "btnUpdate", text: "<img src='/static/images/btn_write.png'>" },title:"수정",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}}
        ]
    });
}


function openUpdateForm(e){
	var data = this.dataItem($(e.target).closest("tr"));
	$("body").append("<div id='updateFormLayer' style='overflow: hidden; padding: 0;'></div>");
	
	$("#updateFormLayer").kendoWindow({
        width: "600px",
        height: "280px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "사용자 수정",
        content : "/user/" + data.id,
        iframe: true,
        close: function() {
       		$(this.element).parent().remove();
       	}
   	}).data("kendoWindow").center().open();
}

function openWriteForm(){
	$("body").append("<div id='writeFormLayer' style='overflow: hidden; padding: 0;'></div>");
	
	$("#writeFormLayer").kendoWindow({
        width: "600px",
        height: "360px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "사용자 등록",
        content : "/user/insertForm",
        iframe: true,
        close: function() {
       		$(this.element).parent().remove();
       	}
   	}).data("kendoWindow").center().open();
}

function openInitPasswordForm(e){
	var data = this.dataItem($(e.target).closest("tr"));
	$("body").append("<div id='initPasswordFormLayer' style='overflow: hidden; padding: 0;'></div>");
	
	$("#initPasswordFormLayer").kendoWindow({
        width: "600px",
        height: "260px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "비밀번호 초기화",
        content : "/user/initPasswordForm/" + data.id,
        iframe: true,
        close: function() {
       		$(this.element).parent().remove();
       	}
   	}).data("kendoWindow").center().open();
}

function fnSearch(){
	$("#list").data("kendoGrid").dataSource.page(1);
}

$(document).ready(function(){
	
	$("#btnSearch").on("click", function(){
			fnSearch();
	});	
	
	$("#btnInsert").on("click", function(){
			openWriteForm();
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
	
	
	<!-- search start -->
	<div class="searchBox">
		<div class="optionBox">
			<label class="pl40" style="width:100px;">사용자ID</label>
			<input id="loginId" type="text" name="loginId" value="" class="k-textbox" >
			<label class="pl40" style="width:100px;">관리자명</label>
			<input type="text" id="name" name="name" class="k-textbox" /><br />
			
		
			<label class="pl40" style="width:100px;">사용여부</label>
			<select id="isDel" name="isDel" style="margin: 0 30px 10px 0;">
						<option value="false" selected>Yes</option>
						<option value="true">No</option>
			</select>
		</div>
		<div class="btnBox">
			<span id="btnSearch" class="btnLarge blue"><button>검색</button></span>
			<span id="btnInsert" class="btnLarge"><button>등록</button></span>
		</div>
	</div>

	
	<!-- search end -->

	<!-- InnerContents01 end  -->
	<div  class="tableType1">
		<table id="list" class="list">
		</table>
	</div>
	
</body>
</html>
