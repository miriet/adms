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
	<style type="text/css">
		.adms-checkbox {
			background-color: #0000BB;
			border-color: #5555BB;
			color: #ffe9a8;/* corner edges of checked checkbox */
		}
	</style>
<script>
function initialGrid(){
	<%--var userGroupId = "${userInfo.groupId}";--%>
    var checkedIds = {};

	var memberGrid = $("#grid").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url : "/update/findList",
                    dataType : "json"
                },
                parameterMap: function(data, type){
                    return {
                        page: data.page,
                        take: data.take,
                        rows : data.pageSize,
                        infoId : $("#uploadList").val(),
                        name : $("#memberName").val(),
                        searchEntranceYear : $("#searchEntranceYear").val(),
                        searchDept :  $("#searchDept").val(),
                        searchDegreeType :  $("#searchDegreeType").val(),
						viewType : $('input:radio[name=viewType]:checked').val()
                };
                }
            },
            schema: {
                data: "rows",
                total: "count"
            },
            pageSize: 50,
            serverPaging: true,
            serverFiltering: false,
            serverSorting: false
        },

//        selectable:"multiple, row",
        scrollable : true,
        width: 1000,
        height: 565,
        filterable: false,
        sortable: false,
        pageable: {
            refresh: true
        },
        navigatable: true,
        dataBinding: function(e) {
            record = (this.dataSource.page() -1) * this.dataSource.pageSize();
		},
        dataBound: function(e) {
        },
        columns: [
            {field: "detailId", title:"??????", locked:true, width:50, attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px; ", class:'tableHead'},
                headerTemplate: "<input type='checkbox' id='header-chb' class='adms-checkbox'></label>",
					template: 	(function(dataItem){
					    			var resultValue;
					    			if (dataItem.sourceTable=="U"){
					    			    resultValue = "<input type='checkbox' name='detailId' id='detailId' value='"+dataItem.detailId+"' class='adms-checkbox'>";
									} else {
					    			    resultValue = "<img src='/static/images/btn_write.png' height='24' width='24' onclick='openMemberInfoUpdateForm("+dataItem.detailId+")'>";
									}
					    			return resultValue;}
								)},
            {field: "name", title:"??????", locked:true, width:70, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
            {field: "birthdayOfficial", width:100, title:"????????????", locked:true, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "birthdayReal", width:100, title:"????????????", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "birthdayRealType", width:80, title:"???????????????", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "email", title:"?????????", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "phone", title:"??????", width:120, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "mobile", title:"?????????", width:120, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "nationality", title:"??????", width:60, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "gender", title:"??????", width:50, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "isAlive", title:"????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "isMarried", title:"????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "membershipFeeStatus", width:100, title:"??????????????????", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "bsId", title:"BS ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "bsDept", title:"BS ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "bsEntranceYear", title:"BS ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "bsGraduationYear", title:"BS ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "msId", title:"MS ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "msDept", title:"MS ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "msEntranceYear", title:"MS ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "msGraduationYear", title:"MS ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "phdId", title:"PhD ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "phdDept", title:"PhD ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "phdEntranceYear", title:"PhD ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "phdGraduationYear", title:"PhD ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "unityId", title:"Unity ??????", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "unityDept", title:"Unity ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "unityEntranceYear", title:"Unity ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "unityGraduationYear", title:"Unity ????????????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "pamtipId", title:"PAMTIP ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "pamtipDept", title:"PAMTIP ??????", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "pamtipEntranceYear", title:"PAMTIP ????????????", width:120, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "pamtipGraduationYear", title:"PAMTIP ????????????", width:120, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "homeZipcode", title:"homeZipcode", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "homeAddr1", title:"homeAddr1", width:300, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "homeAddr2", title:"homeAddr2", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "workZipcode", title:"workZipcode", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "workAddr1", title:"workAddr1", width:300, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "workAddr2", title:"workAddr2", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "mailingZipcode", title:"mailingZipcode", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "mailingAddr1", title:"mailingAddr1", width:300, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
            {field: "mailingAddr2", title:"mailingAddr2", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}}
        ]
    });

//    $('#grid tbody tr').live('dblclick', function () {
//        alert(ID);
//    });
}

function uploadListForm(e){
    var data = this.dataItem($(e.target).closest("tr"));
    $("body").append("<div id='uploadListFormLayer' style='overflow: hidden; padding: 0;'></div>");

    $("#uploadListFormLayer").kendoWindow({
        width: "900px",
        height: "650px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "????????? ??????",
        content : "/upload/findList",
        iframe: true,
        close: function() {
            $(this.element).parent().remove();
        }
    }).data("kendoWindow").center().open();
}

function save(){
    var check_array = [];
    var chk_id = document.getElementsByName("detailId");
    var length = chk_id.length;
    var checked = 0;
    for(i=0;i<length;i++){
        if(chk_id[i].checked == true){
            check_array.push(Number(chk_id[i].value));
            checked += 1;
        }
    }
    if(checked == 0){
        alert("????????? ????????? ??????????????????");
    }else{
        $("#selectedDetailId").val(check_array);
        $("#inputForm").ajaxSubmit({
            url: "/update/addMembers",
            type:'POST',
            success:function(data, statusText, xhr){
                $.showClosingMessage();
                alert("?????????????????????.");
                fnSearch();
            }
        });
    }
}

function openMemberInfoUpdateForm(detailId){
//	var data = this.dataItem($(e.target).closest("tr"));
	$("body").append("<div id='updateFormLayer' style='overflow: hidden; padding: 0;'></div>");
	
	$("#updateFormLayer").kendoWindow({
        width: "800px",
        height: "800px",
        modal: true,
        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
        visible : false,
        actions: ["Minimize", "Maximize", "Close"],
        title: "???????????? ??????",
        content : "/members/" + detailId,
        iframe: true,
        close: function() {
       		$(this.element).parent().remove();
       	}
   	}).data("kendoWindow").center().open();
}

function fnSearch(){
    if($("#uploadList").val() == null) {
        alert("????????? ???????????????");
        return;
	}
    $("#grid").data("kendoGrid").dataSource.page(1);
}

// ?????? ????????????
function excelDown() {
    $("#searchForm").attr("action","/members/excelDown");
    $("#searchForm").submit();
}

$(document).ready(function(){
	
	$("#btnSearch").on("click", function(){
		fnSearch();
	});	
	
    $("#btnInsert").on("click", function(){
        save();
    });
    
    $("#btnExcel").on("click", function(){
    	excelDown();
    });

	// Enter Key ???????????? 
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
		    <!--  
			<label class="pl40" style="width:100px;">?????????ID</label>
			<input id="userName" type="text" name="userName" value="" class="k-textbox" >
		     -->
			<label class="pl40" style="width:60px;">????????????</label>
			<select name="uploadList" id="uploadList" style="width:600px">
				<option value="">??????</option>
				<c:choose>
					<c:when test="${fn:length(uploadList) > 1}">
						<c:forEach items="${uploadList}" var="upList" varStatus="status">
							<option value="${upList.infoId}">${upList.title} | ${upList.fileName} | ${upList.uploaderName} | ${upList.uploadDate}</option>
						</c:forEach>
					</c:when>
				</c:choose>
			</select>
			????????????:
			<input type="radio" name="viewType" value="1" checked>?????????
			<input type="radio" name="viewType" value="2">??????
			<input type="radio" name="viewType" value="3">??????
			<input type="radio" name="viewType" value="4" >??????-?????????
			<input type="radio" name="viewType" value="5" >?????????-??????
		<br/>

			<label class="pl40" style="width:30px;">??????</label>
			<input type="text" id="memberName" name="memberName" class="k-textbox" style="width: 100px;"/>

			<label class="pl40" style="width:30px;">??????</label>
            <select name="searchDept" id="searchDept" style="width:130px" ${userInfo.groupId gt 3? 'disabled' : ''}>
                <option value="">??????</option>
	            <c:choose>
	                <c:when test="${fn:length(deptList) > 1}">
	                    <c:forEach items="${deptList}" var="dept" varStatus="status">
	                        <option value="${dept.deptCode}" ${userInfo.deptCode == dept.deptCode ? 'selected' : ''}>${dept.deptName}</option>
	                    </c:forEach>
	                </c:when>
	            </c:choose>
            </select>
            <label class="pl40" style="width:30px;">??????</label>
			<select id="searchDegreeType" name="searchDegreeType" style="width:100px">
			    <option value="">??????</option>
			    <option value="BS">BS</option>                             
			    <option value="MS">MS</option>                             
			    <option value="PhD">PhD</option>                           
			    <option value="UNITY">UNITY</option>                       
			    <option value="PAMTIP">PAMTIP</option>                     
			</select>
			<label class="pl40" style="width:50px;">????????????</label>
			<input type="text" id="searchEntranceYear" name="searchEntranceYear" class="k-textbox" style="width: 100px"/>

            <!-- 
			<label class="pl40" style="width:100px;">????????????</label>
			<select id="isDel" name="isDel" style="margin: 0 30px 10px 0;">
			    <option value="0" selected>Yes</option>
				<option value="1">No</option>
			</select>
             -->
             			
		</div>
		</form>
		
		<div class="btnBox">
			<span id="btnSearch" class="btnLarge blue"><button>??????</button></span>
            <!-- ????????????????????? ???????????? ??????  -->
            <c:if test="${userInfo.groupId != 3}">
            <span id="btnInsert" class="btnLarge"><button>????????????</button></span>
            </c:if>
            <%--<span id="btnExcel" class="btnLarge blue"><button>??????????????????</button></span>--%>
		</div>
	</div>
	<!-- search end -->
updateList
	<!-- InnerContents01 end  -->
	<div>
	<%--<div  class="tableType1">--%>
		<table id="grid" class="list">
		<%--<table id="list" class="list">--%>
		</table>
	</div>
	<form id="inputForm" name="inputForm" method="post" >
		<input type="hidden" id="infoId" value="${infoId }">
		<input type="hidden" id="selectedDetailId" name="selectedDetailId" />
	</form>
</body>
</html>
