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
	var userGroupId = "${userInfo.groupId}";
	
	var memberGrid = $("#list").kendoGrid({
        dataSource : {
            transport: {
                read: {
                	url : "/alumnusmembers/find",
                	dataType : "json"
                },
                parameterMap: function(data, type){
	            	return {
			     		page: data.page,
			     		take: data.take,
			     		rows : data.pageSize,
			     		// userName : $("#userName").val(),
			     		name : $("#name").val(),
			     		searchEntranceYear : $("#searchEntranceYear").val(),
                        searchDept :  $("#searchDept").val(),
                        searchDegreeType :  $("#searchDegreeType").val()
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
			{title:"번호",headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;height:15px;"},width:50,attributes: { style: "font-size: 12px;text-align: center;height:15px;" },template: "#=++record #"},
			{field: "name", title:"이름", width: "8%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: left;"}},
			{field: "birthday", title:"생년월일", width: "11%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: center;"}},
			{field: "department", title:"학과", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: center;"}},
			{field: "degree", title:"학위", width: "8%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: center;"}},
			{field: "email", title:"이메일", width: "20%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: left;"}},
			{field: "mobile", title:"모바일", width: "10%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: left;"}},
			{field: "gender", title:"성별", width: "8%", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}, attributes: {style: "text-align: center;"}},
			{field: "memberId",title: "MEMBERID", hidden: true },
	        {command: { click: openViewForm, name: "btnView", text: "<img src='/static/images/btn_write.png' height='24' width='24'>" },title:"상세조회",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;"}}
        ]
    });
	
	// 대학본부 그룹은 수정권한 없음
	if (userGroupId == 3) {
		$("#list").data("kendoGrid").hideColumn(9);
	}
}


function openViewForm(e){
	var data = this.dataItem($(e.target).closest("tr"));
	$("body").append("<div id='viewFormLayer' style='overflow: hidden; padding: 0;'></div>");
	
	$("#viewFormLayer").kendoWindow({
        width: "1000px",
        height: "700px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "회원정보 수정",
        content : "/alumnusmembers/" + data.memberId,
        iframe: true,
        close: function() {
       		$(this.element).parent().remove();
       	}
   	}).data("kendoWindow").center().open();
}


function fnSearch(){
	$("#list").data("kendoGrid").dataSource.page(1);
}

// 엑셀 다운로드
function excelDown() {
    $("#searchForm").attr("action","/alumnusmembers/excelDown");
    $("#searchForm").submit();
}

$(document).ready(function(){
	
	$("#btnSearch").on("click", function(){
		fnSearch();
	});	
	
   
    
    $("#btnExcel").on("click", function(){
    	excelDown();
    });
    
	// $("#isDel").kendoDropDownList();
	
	// Enter Key 검색하기 
	$('input').keyup(function(e) {
	    if (e.keyCode == 13) fnSearch();
	});
	
    initialGrid();
	
});
</script>
</head>
<div id="result"></div>
<body>
	<!-- search start -->
	<div class="searchBox">
        <form name="searchForm" id="searchForm" method="post">
		<div class="optionBox">
            <label class="pl40" style="width:70px;">학위</label>
			<select id="searchDegreeType" name="searchDegreeType" style="width:160px">
			    <option value="선택">선택</option>
			    <option value="BS">BS</option>                             
			    <option value="MS">MS</option>                             
			    <option value="PhD">PhD</option>                           
			    <option value="UNITY">UNITY</option>                       
			    <option value="PAMTIP">PAMTIP</option>                     
			</select>                                                    	
		</div>
		</form>
		
		<div class="btnBox">
			<span id="btnSearch" class="btnLarge blue"><button>검색</button></span>
            <!-- 대학본부그룹은 등록권한 없음  -->
          <%--   <c:if test="${userInfo.groupId != 3}">
            <span id="btnInsert" class="btnLarge"><button>등록</button></span>
            </c:if> --%>
            <span id="btnExcel" class="btnLarge blue"><button>엑셀다운로드</button></span>
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
