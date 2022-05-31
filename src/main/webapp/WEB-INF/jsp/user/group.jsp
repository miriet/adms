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
	                	url : "/group/find",
	                	dataType : "json"
	                },
	                parameterMap: function(data, type){
		            	return {
				     		page: data.page,
				     		take: data.take,
				     		rows : data.pageSize,
				     		groupName : $("#groupName").val(),
//				     		siteId : $("#siteId").val(),
				     		isDel :  $("#isDel").val()
				     	};
				    }
	            },
	            schema: {
	            	data: "rows",
	            	total: "count"
	            },
	            pageSize: 10,
	            serverPaging: true,
	            serverFiltering: false,
	            serverSorting: false
	        },
	       	selectable: true,
	        filterable: false,
	        sortable: false,
	        pageable: {
	            pageable: {
					info: false
			    }
	        },
	        
	        pageable : false,
	        dataBinding: function(e) {
	        	record = (this.dataSource.page() -1) * this.dataSource.pageSize();
	        	
	       	},
	        dataBound: function() {
	        	$("[name='linkTitle']").click(function(e){
					var groupId = $(e.target).attr("groupId");
					var groupName = $(e.target).attr("groupName");
					fnGroupUserListGrid(groupId, groupName);
				});

	        	$("#btnWrite").click(function(e) {
	        		openWriteForm();
	        	});
	        },
	        columns: [
	            {title:"번호",headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;height:15px;"},width:50,attributes: { style: "font-size: 12px;text-align: center;height:15px;" },template: "#=++record #"},
				// {title:"번호",width:50, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;height:15px;"},attributes: { style: "text-align: center;" }, template: "#=++record #"},
				{field:"groupName", title:"그룹명", width: "200px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: left;"}, template:kendo.template($("#fnLinkFmatter").html())},
				{field:"description", title:"설명", width: "100px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: center;"}},
				{field:"groupId",title: "GROUPID",  hidden: true, width: "100px" },
				{ command: { click: openUpdateForm, width:60,name: "btnUpdate", text: "<img src='/static/images/btn_write.png'>" },title:"그룹정보",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}},
				// { command: { click: openAuthForm, width:60,name: "btnDelete", text: "<img src='/static/images/btn_write.png'>" },title:"그룹권한",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}}
	        ]
	    });

	}
	
	function fnGroupUserListGrid(groupId,groupName){
		$("#groupArea").empty();	//리스트영역 비워줌 
		$("#groupArea").append("<input type='hidden' id='groupId' value='" + groupId + "'/>");
		$("#groupArea").append("<h2>" + groupName + "</h2>");
		$("#groupArea").append("<table id='groupList' class='list' width='100%''></table>");
		
		$("#groupList").kendoGrid({
	        dataSource: {
	            transport: {
	                read: {
	                	url : "/user/userGroupFind",
	                	dataType : "json"
	                },
	                parameterMap: function(data, type){
		            	return {
				     		page: data.page,
				     		take: data.take,
				     		rows : data.pageSize,
				     		groupId :  groupId
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
	      	height: 400,
	        filterable: false,
	        sortable: false,
	        pageable: {
	            refresh: true
	        },
	        toolbar: [
  	             {
  	                text: "",
  	                template: kendo.template($("#groupUserToolbar").html())
  	              }
              ],
	        dataBinding: function(e) {
	        	record = (this.dataSource.page() -1) * this.dataSource.pageSize();
	        	
	       	},
	        dataBound: function() {
	        	$("#btnAddUser").click(function(e) {  
	        		openAddUserForm();
	        	});
	        },
	        columns: [
				{title:"번호",width:50,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;;padding:12px 5px; ", class:'tableHead'},template: "#=++record #"},
				{field: "userName", title:"ID", width: "200px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
				{field: "name", title:"관리자명", width: "100px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
//				{field: "siteName", title:"사이트", width: "120px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "email", title:"이메일", width: "150px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
				{field: "dateCreated", title:"등록일", width: "80px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "isDel", title:"사용여부", width: "80px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field:"id", 		title: "USERID", hidden: true, width: "100px" },
				{ command: { click: fnRemoveUser, width:60,name: "btnDelete", text: "<img src='/static/images/btn_delete.png'>" },title:"사용자 삭제여부",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}}
	        ]
	    });
		
	}
	
	function openAuthForm(e){
		var data = this.dataItem($(e.target).closest("tr"));
		
		$("body").append("<div id='writeFormLayer' style='overflow: hidden; padding: 0;'></div>");
		
		$("#writeFormLayer").kendoWindow({
	        width: "500px",
	        height: "650px",
	        modal: true,
	        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
	        visible : false,
	        actions: ["Minimize", "Maximize", "Close"],
	        title: "SET AUTH",
	        content : "/group/setAuthForm/" + data.groupId,
	        iframe: true,
	        close: function() {
	       		$(this.element).parent().remove();
	       	}
	   	}).data("kendoWindow").center().open();
	}
	
	function openAddUserForm(){
		$("body").append("<div id='addUserFormLayer' style='overflow: hidden; padding: 0;'></div>");
		
		$("#addUserFormLayer").kendoWindow({
	        width: "1150px",
	        height: "500px",
	        modal: true,
	        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
	        visible : false,
	        actions: ["Minimize", "Maximize", "Close"],
	        title: "사용자 추가",
	        content : "/group/addUserForm/" + $("#groupId").val(),
	        iframe: true,
	        close: function() {
	       		$(this.element).parent().remove();
	       		$("#groupList").data("kendoGrid").dataSource.page(1);
	       	}
	   	}).data("kendoWindow").center().open();
	}
	
	
	 function fnRemoveUser(e){
		if(confirm("그룹에서 선택한 사용자를 삭제하시겠습니까?")){
			var data = this.dataItem($(e.target).closest("tr"));
			$.showLoadingMessage();
			$.ajax({
				url: "/group/removeUser?groupId=" + $("#groupId").val() + "&userId=" + data.id,
				type:'GET',
				success:function(data, statusText, xhr){
					alert("삭제되었습니다.");
					$("#groupList").data("kendoGrid").dataSource.page(1);
					$.showClosingMessage();
				}
			});
		}
	} 

	function openUpdateForm(e){
		var data = this.dataItem($(e.target).closest("tr"));
		$("body").append("<div id='updateFormLayer' style='overflow: hidden; padding: 0;'></div>");
		
		$("#updateFormLayer").kendoWindow({
	        width: "600px",
	        height: "330px",
	        modal: true,
	        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
	        visible : false,
	        actions: ["Minimize", "Maximize", "Close"],
	        title: "관리자 그룹 수정",
	        content : "/group/" + data.groupId,
	        iframe: true,
	        close: function() {
	       		$(this.element).parent().remove();
	       		$("#list").data("kendoGrid").dataSource.page(1);
	       	}
	   	}).data("kendoWindow").center().open();
	}
	
	function openWriteForm(){
		$("body").append("<div id='writeFormLayer' style='overflow: hidden; padding: 0;'></div>");
		
		$("#writeFormLayer").kendoWindow({
	        width: "600px",
	        height: "330px",
	        modal: true,
	        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
	        visible : false,
	        actions: ["Minimize", "Maximize", "Close"],
	        title: "관리자 그룹 등록",
	        content : "/group/insertForm",
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
		
//		$("select[name=siteId]").kendoDropDownList({
//			change : function(){
//				fnSearch();
//			}
//		});
		
		$("#isDel").kendoDropDownList();
		
		initialGrid();
	});
</script>
</head>

<body>
	<script id="groupUserToolbar" type="text/x-kendo-template">
        <button class="k-button" id="btnAddUser">사용자 추가</button>
	</script>
	
	<script type="text/x-kendo-template" id="fnLinkFmatter">
	    <a href='\\#' name='linkTitle' groupId='#: groupId #' groupName='#: groupName #'> #: groupName #</a>
	</script>
	
	<!-- search start -->
	<div class="searchBox">
		<div class="optionBox">
			<label class="pl40" style="width:100px;">그룹명</label>
			<input id="groupName" type="text" name="groupName" value="" class="k-textbox" >
			
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
	
	<!-- InnerContents01 end  -->
	<div class="tableType1">
		<table id="list" class="list">
		</table>
	</div>
	
	<div id="groupArea" style="width:100%;margin:5px auto;padding-top:25px"></div>

</body>
</html>
