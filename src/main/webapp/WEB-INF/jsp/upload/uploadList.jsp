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
<link rel="stylesheet" href="/static/js/fancybox/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
<script type="text/javascript" src="/static/js/util/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="/static/js/fancybox/jquery.fancybox.pack.js?v=2.1.5"></script>
<title></title>
<script>
	function initialGrid(){
		$("#list").kendoGrid({
	        dataSource: {
	            transport: {
	                read: {
	                	url : "/upload/list",
	                	dataType : "json"
	                },
	                parameterMap: function(data, type){
		            	return {
				     		page: data.page,
				     		take: data.take,
				     		rows : data.pageSize,
				     		//startNumber : $("#startNumber").data("kendoNumericTextBox").value(),
				     		//endNumber : $("#endNumber").data("kendoNumericTextBox").value()
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
	        	$("img[name='episodeImage']").each(function(index,element){
	        		$(element).fancybox({
		        		href : $(element).attr("previewUrl"),
		        		title : $(element).attr("title"),
		        		prevEffect		: 'none',
		        		nextEffect		: 'none',
		        		helpers		: {
		        			title	: { type : 'inside' },
		        			buttons	: {}
		        		}
		        	});
	        	});
	        },
	        columns: [
				{title:"번호",width:50,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px; ", class:'tableHead'},template: "#=++record #"},
				{field: "uploaderName", title:"등록자", width: "150px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: left;"}},
				{field: "title", title:"제목", width: "250px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "fileName", title:"파일이름", width: "280px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{field: "uploadDate", title:"등록일", width: "140px", headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}, attributes: {style: "text-align: center;"}},
				{title: "infoId",  field:"infoId", hidden: true, width: "100px" },
				{ command: { click: infoDetailForm, width:60,name: "btnUpdate", text: "내용보기" },title:"내용보기",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}},
				{ command: { click: fnDelete, width:60,name: "btnDelete", text: "<img src='/static/images/btn_delete.png'>" },title:"삭제",width:100,attributes: { style: "text-align: center;" },headerAttributes: {style:"font-weight:bold;font-size: 12px;text-align: center;padding:12px 5px;"}}
	        ]
	    });
	}
	
	function infoDetailForm(e){
		var data = this.dataItem($(e.target).closest("tr"));
		$("body").append("<div id='detailFormLayer' style='overflow: hidden; padding: 0;'></div>");
		
		$("#detailFormLayer").kendoWindow({
	        width: "1200px",
	        height: "650px",
	        modal: true,
	        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
	        visible : false,
	        actions: ["Minimize", "Maximize", "Close"],
	        title: "업로드 회원 목록",
	        content : "/upload/" + data.infoId,
	        iframe: true,
	        close: function() {
	       		$(this.element).parent().remove();
	       	}
	   	}).data("kendoWindow").center().open(); 
	}
	
	function fnDelete(e){
		if(confirm('삭제하시겠습니까?')){
			var data = this.dataItem($(e.target).closest("tr"));
			$.showLoadingMessage();
				
			$.ajax({
				url: "/upload/delete/" + data.infoId,
				type:'POST',
				success:function(data, statusText, xhr){
					$.showClosingMessage();
					alert("삭제되었습니다.");
					fnSearch();
				}
			});
		}
	}
	
	function fnSearch(){
		$("#list").data("kendoGrid").dataSource.page(1);
	}

	function fnUpload() {
		var title = $("#title").val();
		var excelFile = $("#excelFile").val();
		if (title == "" || title == null) {
			alert("제목을 입력해주세요.")
		}
		else if (excelFile == "" || excelFile == null){
			alert("파일을 선택해주세요.")
		}
		else {
	        if ($("#uploadForm").data("kendoValidator").validate()) {
	            if(confirm('저장하시겠습니까?')){
	                $.showLoadingMessage();
	
	                $("#uploadForm").ajaxSubmit({
	                    url: "/upload/insert",
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
	            $.fieldErrorMessage($("#uploadForm").data("kendoValidator"));
	        }
		}
    }
	
	function fnDownload() {
		$("#uploadForm").attr("action","./upload/excelDownload.do");
		$("#uploadForm").submit();
	}

	$(document).ready(function(){
        $("#uploadForm").kendoValidator({validateOnBlur: false});

		$("#btnUpload").click(function(){
			fnUpload();
		});
		
		$("#btnDownload").click(function(){
			fnDownload();
		});
		
		initialGrid();
		
		//
		$(".k-textbox .k-icon").css({"top":"0",'margin':'0px','position':'static'});
		$("input.k-textbox.k-input").css("display","none");
		$("input.k-formatted-value.k-textbox.k-input").css("width","50px");
	});
</script>
</head>
<div id="result"></div>
<body>


	
	
	<!-- search start -->
	<div class="searchBox">
		<div class="optionBox">
			<form id="uploadForm" name="uploadForm" method="post" enctype="multipart/form-data">
				<label>제목: </label>
				<input type="text" id="title" name="title" class="k-textbox" style="width: 30% " />
				<label>   File 선택</label>
				<!-- <input name="csvFile" id="csvFile" type="file" data-target-field="isSimple"/> -->
				<input type="file" name="excelFile" id="excelFile"  data-target-field="isSimple" style="width: 30% "/>
			<%--<input type="hidden" id="bookId" name="bookId" 	value='' />--%>
				<span id="btnDownload" class="btnLarge blue"><button>양식다운로드</button></span>
			</form>
		</div>
		<div class="btnBox">
			<span id="btnUpload" class="btnLarge blue"><button>Upload</button></span>
		</div>
	</div>
	
	<!-- InnerContents01 end  -->
	<div class="tableType1">
		<table id="list" class="list">
		</table>
	</div>
	
</body>
</html>
