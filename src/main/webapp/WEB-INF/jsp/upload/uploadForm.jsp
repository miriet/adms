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
<style>
#tdBgm .k-upload{
    display: inline-block;
    min-width: 75%;
    max-width: 75%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<title></title>
<script>
	function save(){
		if ($("#inputForm").data("kendoValidator").validate()) {
			if(confirm('저장하시겠습니까?')){
				$.showLoadingMessage();
					
				$("#inputForm").ajaxSubmit({
					url: "/episode/insert",
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
	
	function setBook(bookId, bookName){
		$("#bookId").val(bookId);
		$("#bookName").val(bookName);
		$("#seasonId").data("kendoDropDownList").enable(true);
		$("#seasonId").data("kendoDropDownList").dataSource.data([]);
		$("#seasonId").data("kendoDropDownList").dataSource.read({"bookId" : $("#bookId").val()});
	}
	
	$(document).ready(function(){
		$("#seasonId").kendoDropDownList({
			dataSource: {
		        transport: {
		            read : {
		            	url : "/season/findByBookId",
		            	dataType : "json"
		            }
		      	},
		      	schema: {
		            data: "rows",
		            model: {   
			            fields: {
			            	id: { type: "string" },
			            	number: { type: "string" }
			        	}
		            }
		      	}
		   	},
			dataTextField: "number",
			dataValueField: "id",
			optionLabel: {
				number: "Select",
				id: ""
			},
			change : function(){
				$.ajax({
					url: "/episode/maxNumber/" + $("#seasonId").data("kendoDropDownList").value(),
					type:'POST',
					success:function(data, statusText, xhr){
						$("#number").data("kendoNumericTextBox").value(data["result"]);
						$("#number").data("kendoNumericTextBox").enable(true);
						$("#btnCheckEpisodeNumber").css("display","");
						$("#btnCheckEpisodeNumber").css("pointer-events","");
						$("#checkDoneMessage").css("display","none");
						$("#checkNumber").val("");
					}
				});
			},
			index : 0,
			enable : false,
			autoBind: false
		});
		
		$("#episodeType").kendoDropDownList();
		
		$("#btnBookSearch").click(function(){
			$.openBookSearchPopup();
		});
		
		$("#btnBookInit").click(function(){
			$("#bookId").val("");
			$("#bookName").val("");
			$("#seasonId").data("kendoDropDownList").dataSource.data([]);
			$("#seasonId").data("kendoDropDownList").enable(false);
			$("#number").data("kendoNumericTextBox").enable(false)
			$("#number").data("kendoNumericTextBox").value("");
			$("#btnCheckEpisodeNumber").css("display","");
			$("#btnCheckEpisodeNumber").css("pointer-events","none");
			$("#checkDoneMessage").css("display","none");
			$("#checkNumber").val("");
		});
		
		$("input:radio[name='isBgm']").click(function(){
			if($('input:radio[name="isBgm"]:checked').val() == "true"){
				$("#bgmFile").data("kendoUpload").enable(true);
			}else{
				$("#tdBgm .k-upload-files.k-reset").find("li").remove();
				$("#bgmFile").data("kendoUpload").enable(false);
			}
		});
		
		$("#number").kendoNumericTextBox({
            format: "n0",
            min: 1,
            max: 100,
            step: 1,
            change : function(){
            	$("#btnCheckEpisodeNumber").css("display","");
            	$("#checkDoneMessage").css("display","none");
            	$("#checkNumber").val("");
            }
        }).data("kendoNumericTextBox").enable(false);
		
		$("#rectangleImage").kendoUpload({
			multiple: false,
			select : function(e){
				$.checkFileExtension(e);
			}
		});
		$("#squareImage").kendoUpload({
			multiple: false,
			select : function(e){
				$.checkFileExtension(e);
			}
		});
		$("#bgmFile").kendoUpload({
			multiple: false,
			select : function(e){
				//checkFileExtension(e);
			}
		});
		
		$("#episodeImages").kendoUpload({
			select : function(e){
				$.checkFileExtension(e);
			}
		});
		
		$("#btnSave").click(function(){
				save();
		});
		
		$("#btnClose").click(function(){
				parent.$("#writeFormLayer").data("kendoWindow").close();
		});
		
		$("#btnCheckEpisodeNumber").click(function(){
			$.ajax({
				url: "/episode/checkEpisodeNumber?seasonId=" + $("#seasonId").data("kendoDropDownList").value() + "&number=" + $("#number").data("kendoNumericTextBox").value(),
				type:'POST',
				success:function(data, statusText, xhr){
					if(data["result"] == false){
						$("#btnCheckEpisodeNumber").css("display","none");
						$("#checkDoneMessage").css("display","");
						$("#checkNumber").val("true");
					}
				}
			});
		});
		
		$("#inputForm").kendoValidator({
			validateOnBlur: false,
			rules: {
                partRequired: function (input) {
                    if (input.is("[data-partRequired-msg]")) {
                    	var inputValue = $("input:radio[name='" + input.data("targetField") + "']:checked").val() + "";
                    	
                    	if(inputValue == "true"){
                    		if($.trim(input.val()) == ""){
                    			return $("#bgmFile").closest(".k-upload").find(".k-file").length;
                    		}
                    	}
                  	}

                    return true;
                },
                uploadRequired: function(input) {
                    if (input[0].type == "file") {
                    	return input.closest(".k-upload").find(".k-file").length;
                    }
                    return true;
                }
            }
		});
	});
</script>
</head>
<body class="index">
	<form id="inputForm" name="inputForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="checkNumber" name="checkNumber" 	value='' required validationMessage='<spring:message code="cms.validation.field.duplication" arguments="회차"/>'/>
	<div class="tableType2">
	<table id="bookInfo" class="table_sc">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr height="35px">
			<th class="TBC_th">작품</th>
			<td class="TBC_td">
				<input type="hidden" id="bookId" name="bookId" 	value='' required validationMessage='<spring:message code="cms.validation.field.required" arguments="작품"/>'/>
				<input type="text" id="bookName" name="bookName" class="k-textbox" style="width: 55% " readOnly/>
				<span id="btnBookSearch" class="btnSmall"><input type="button" value="추가"></span>
				<span id="btnBookInit" class="btnSmall"><input type="button" value="초기화"></span>
			</td>
			<th class="TBC_th">시즌</th>
			<td class="TBC_td" colspan="3">
				<select name="seasonId" id="seasonId" style="width:200px" required validationMessage='<spring:message code="cms.validation.field.required" arguments="시즌"/>'>
					<option value="">Select</option>
				</select>
			</td>
		</tr>
		<tr height="35px">
			<th class="TBC_th">에피소드명</th>
			<td class="TBC_td" colspan="3">
				<input type="text" id="name" name="name" class="k-textbox" style="width: 99% " />
			</td>
		</tr>
		<tr height="35px">
			<th class="TBC_th">에피소드유형</th>
			<td class="TBC_td" >
				<select name="episodeType" id="episodeType" style="width:200px" required validationMessage='<spring:message code="cms.validation.field.required" arguments="에피소드유형"/>'>
					<option value="">Select</option>
					<c:forEach var="code" items="${episodeType}">
						<option value="${code.code}">${code.name }</option>
					</c:forEach>
				</select>
			</td>
			<th class="TBC_th">회차</th>
			<td class="TBC_td" >
				<input type="text" id="number" name="number" style="width:100px" value="" required validationMessage='<spring:message code="cms.validation.field.required" arguments="회차"/>'/>회
				<span id="btnCheckEpisodeNumber" class="btnSmall" style="margin-left:15px;pointer-events:none" ><input type="button" value="중복검사" ></span>
				<span id="checkDoneMessage" style="display:none">사용가능</span>
			</td>
		</tr>
		<tr height="95px">
			<th class="TBC_th">썸네일(190*190)</th>
			<td class="TBC_td" colspan="3">
				<input name="rectangleImage" id="rectangleImage" type="file" data-target-field="isSimple" uploadRequired data-uploadRequired-msg='<spring:message code="cms.validation.field.required" arguments="이미지"/>'/>
			</td>
		</tr>
		<tr height="95px">
			<th class="TBC_th">썸네일(150*102)</th>
			<td class="TBC_td" colspan="3">
				<input id="squareImage" name="squareImage" type="file" data-target-field="isSimple" uploadRequired data-uploadRequired-msg='<spring:message code="cms.validation.field.required" arguments="이미지"/>'/>
			</td>
		</tr>
		<tr height="95px">
			<th class="TBC_th">BGM</th>
			<td class="TBC_td" colspan="3" id="tdBgm">
				<p style="padding-bottom:10px;">
				<input type="radio" name="isBgm" id="isBgm1" class="k-radio" value="true" checked/>
              	<label class="k-radio-label" for="isBgm1">사용</label>&nbsp;&nbsp;&nbsp;
              	<input type="radio" name="isBgm" id="isBgm2" class="k-radio" value="false" />
              	<label class="k-radio-label" for="isBgm2" >미사용</label>
              	</p>
				<input id="bgmFile" name="bgmFile" type="file" style="width:60%" data-target-field="isBgm" uploadRequired data-uploadRequired-msg='<spring:message code="cms.validation.field.required" arguments="BGM"/>'/>
			</td>
		</tr>
		<tr height="95px">
			<th class="TBC_th">에피소드이미지</th>
			<td class="TBC_td" colspan="3">
				<input id="episodeImages" name="episodeImages" type="file" data-target-field="isSimple" uploadRequired data-uploadRequired-msg='<spring:message code="cms.validation.field.required" arguments="이미지"/>'/>
			</td>
		</tr>
		<tr>
			<th class="TBC_th">작가한마디</th>
			<td class="TBC_td" colspan="3">
				<textarea rows="7" name="writerWord" class="k-textbox" style="width:98%"></textarea>
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
