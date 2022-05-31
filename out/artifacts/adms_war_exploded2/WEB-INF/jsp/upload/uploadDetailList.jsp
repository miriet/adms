<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script>
	function firstGrid(){
		$("#uploadList").kendoGrid({
	        dataSource: {
	            transport: {
	                read: {
	                	url : "/upload/findList",
	                	dataType : "json"
	                },
	                parameterMap: function(data, type){
		            	return {
				     		page: data.page,
				     		take: data.take,
				     		rows : data.pageSize,
				     		infoId : $("#infoId").val()
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
	       	scrollable : true,
	       	width: 1000,
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
				{field: "detailId", title:"선택", width:50, attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px; ", class:'tableHead'},template: '<input type="checkbox" name="detailId" id="detailId" value="#:detailId#"/>',filterable: {multi:true, checkAll: false }},
				{field: "name", title:"이름", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
				{field: "birthdayOfficial", width:150, title:"생년월일", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "birthdayReal", width:150, title:"실제생일", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "birthdayRealType", width:100, title:"실생일 음양", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "email", title:"이메일", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "phone", title:"전화번호", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "mobile", title:"휴대폰", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "nationality", title:"국적", width:60, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "gender", title:"성별", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "isAlive", title:"isAlive", width:80, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "mailingAddress", title:"우편물수령지", width:80, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "currentWork", width:100, title:"직장명", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "currentWorkDept", title:"부서", width:80, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "bsId", title:"학부학번", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "bsDept", title:"학부학과", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "bsEntranceYear", title:"학부입학년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "bsGraduationYear", title:"학부졸업년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "bsSupervisor", title:"학부지도교수", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "bsExpectedPath", title:"학부예상진로", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "bsExpectedWork", title:"학부예상직장", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "msId", title:"석사학번", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "msDept", title:"석사학과", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "msEntranceYear", title:"석사입학년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "msGraduationYear", title:"석사졸업년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "msSupervisor", title:"석사지도교수", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "msExpectedPath", title:"석사예상진로", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "msExpectedWork", title:"석사예상직장", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "phdId", title:"박사학번", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "phdDept", title:"박사학과", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "phdEntranceYear", title:"박사입학년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "phdGraduationYear", title:"박사졸업년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "phdSupervisor", title:"박사지도교수", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "phdExpectedPath", title:"박사예상진로", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "phdExpectedWork", title:"박사예상직장", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "unityId", title:"통합학번", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "unityDept", title:"통합학과", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "unityEntranceYear", title:"톡합입학년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "unityGraduationYear", title:"통합졸엽년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "unitySupervisor", title:"통합지도교수", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "unityExpectedPath", title:"통합예상진로", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "unityExpectedWork", title:"통합예상직장", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "pamtipId", title:"pamtip학번", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "pamtipDept", title:"pamtip학과", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "pamtipEntranceYear", title:"pamtip입학년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "pamtipGraduationYear", title:"pamtip졸업년도", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "homeZipcode", title:"자택우편번호", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "homeAddr1", title:"자택주소1", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "homeAddr2", title:"자택주소2", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "homeAddr3", title:"자택주소3", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "homeAddr4", title:"자택주소4", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "workZipcode", title:"직장우편번호", width:100, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "workAddr1", title:"직장주소1", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "workAddr2", title:"직장주소2", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "workAddr3", title:"직장주소3", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
                {field: "workAddr4", title:"직장주소4", width:160, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				// {field: "mailingZipcode", title:"mailingZipcode", width:150, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				// {field: "mailingAddr1", title:"mailingAddr1", width:300, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				// {field: "mailingAddr2", title:"mailingAddr2", width:200, headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}}
	        ]
	    });
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
			alert("회원을 선택해주세요");
		}else{
			$("#selectedDetailId").val(check_array);
			$("#inputForm").ajaxSubmit({
				url: "/upload/addMember",
				type:'POST',
				success:function(data, statusText, xhr){
					$.showClosingMessage();
					alert("저장되었습니다.");
					parent.$("#detailFormLayer").data("kendoWindow").close();
				}
			});
		}
	}
	
	$(document).ready(function(){
		$("#btnClose").on("click" , function(){
			parent.$("#detailFormLayer").data("kendoWindow").close();
		});
		
		$("#btnUpdate").on("click" , function(){
			save();
		});
		
		firstGrid();
		
		$(".k-textbox .k-icon").css({"top":"0",'margin':'0px','position':'static'});
		$("input.k-textbox.k-input").css("display","none");
		$("input.k-formatted-value.k-textbox.k-input").css("width","50px");
	});
</script>
</head>
<div id="result"></div>
<body>
	<form id="inputForm" name="inputForm" method="post" >
		<input type="hidden" id="infoId" value="${infoId }">
		<input type="hidden" id="selectedDetailId" name="selectedDetailId" />
	</form>
	<!-- InnerContents01 end  -->
	<div class="tableType1">
		<table id="uploadList" class="uploadList">
		</table>
	</div>
	<div class="btnBox">
		<span id="btnClose" class="btnLarge"><button>취소</button></span>
		<span id="btnUpdate" class="btnLarge blue"><button>승인</button></span>
	</div>
	
</body>
</html>
