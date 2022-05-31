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
	// ********************************************   
	// 저장 처리
    // ********************************************   
	function save(){
        // -------------------------------------------   
        // 1. 회원정보
        // -------------------------------------------   
        // 필수입력 체크
        var _name = $("#name").val();
        var _email = $("#email").val();
        var _mobile = $("#mobile").val();
        var _birthdayOfficial = $("#birthdayOfficial").val();
        
        if ($.trim(_name) == "") {
            alert("회원명을 입력하세요.");
            return false;
        }
        
        // -------------------------------------------   
        // 2. 학위정보
        // -------------------------------------------   
        var _degreeTR = $("#degreeInfo tbody tr");

        // 필수입력 체크
        var mustInputDegree = true;
        _degreeTR.each(function (index, element) {
            var _deptCode = $(element).find("input[name='member.degrees.deptCode']").val();
            var _deptName = $(element).find("input[name='member.degrees.deptName']").val();
            var _degreeType = $(element).find("input[name='member.degrees.degreeType']").val();
            var _studentId = $(element).find("input[name='member.degrees.studentId']").val();
            var _entranceYear = $(element).find("input[name='member.degrees.entranceYear']").val();
            var _graduationYear = $(element).find("input[name='member.degrees.graduationYear']").val();
            
            if (_deptCode == "") {
                alert("학과를 선택하세요.");
                mustInputDegree = false;
                return false;
            }
            
            if (_degreeType == "") {
                alert("학위를 선택하세요.");
                mustInputDegree = false;
                return false;
            }
            
        });
        
        if (!mustInputDegree) {
            return false;
        }
        
        _degreeTR.each(function (index, element) {
            $(this).find("input[name='member.degrees.id']").attr("name", "member.degrees[" + index + "].id");
            $(this).find("input[name='member.degrees.deptCode']").attr("name", "member.degrees[" + index + "].deptCode");
            $(this).find("input[name='member.degrees.deptName']").attr("name", "member.degrees[" + index + "].deptName");
            $(this).find("input[name='member.degrees.degreeType']").attr("name", "member.degrees[" + index + "].degreeType");
            $(this).find("input[name='member.degrees.studentId']").attr("name", "member.degrees[" + index + "].studentId");
            $(this).find("input[name='member.degrees.entranceYear']").attr("name", "member.degrees[" + index + "].entranceYear");
            $(this).find("input[name='member.degrees.graduationYear']").attr("name", "member.degrees[" + index + "].graduationYear");
        });
        
        // -------------------------------------------   
        // 3. 주소정보
        // -------------------------------------------   
        var _addressTR = $("#addressInfo tbody tr");

        // 동일 주소타입 중복입력 불가
        var _addressTypePrev = "";
        var _addressTypeCurr = "";
        var _addressTypeDuplCount = 0;
            
        _addressTR.each(function (index, element) {
            _addressTypePrev = $(element).find("input[name='member.addresses.addressType']").val();
            _addressTypeDuplCount = 0;

            _addressTR.each(function (index2, element2) {
                _addressTypeCurr = $(element2).find("input[name='member.addresses.addressType']").val();

                if (_addressTypePrev == _addressTypeCurr) {
                    _addressTypeDuplCount ++;
                }

            });
            
        });
        
        if (parseInt(_addressTypeDuplCount) > 1 ) {
            alert("동일한 주소타입 (" + _addressTypePrev + ") 이 존재합니다.");
            return false;
        }
 
        // 필수입력 체크
        var mustInputAddr = true;
        _addressTR.each(function (index, element) {
            var _addressType = $(element).find("input[name='member.addresses.addressType']").val();
            var _addressName = $(element).find("input[name='member.addresses.addressName']").val();
            var _zipCode = $(element).find("input[name='member.addresses.zipCode']").val();
            var _address1 = $(element).find("input[name='member.addresses.address1']").val();
            var _address2 = $(element).find("input[name='member.addresses.address2']").val();

            if (_addressType == "") {
                alert("주소타입을 선택하세요.");
                mustInputAddr = false;
                return false;
            }
            /* 
            if ($.trim(_addressName) == "") {
                alert("주소명칭을 입력하세요.");
                mustInputAddr = false;
                return false;
             }
            */
            
            if ($.trim(_address1) == "") {
                alert("주소1를 입력하세요.");
                mustInputAddr = false;
                return false;
            }
            
        });

        if (!mustInputAddr) {
            return false;
        }
        
        _addressTR.each(function (index, element) {
            $(this).find("input[name='member.addresses.id']").attr("name", "member.addresses[" + index + "].id");
            $(this).find("input[name='member.addresses.addressType']").attr("name", "member.addresses[" + index + "].addressType");
            $(this).find("input[name='member.addresses.addressName']").attr("name", "member.addresses[" + index + "].addressName");
            $(this).find("input[name='member.addresses.zipCode']").attr("name", "member.addresses[" + index + "].zipCode");
            $(this).find("input[name='member.addresses.address1']").attr("name", "member.addresses[" + index + "].address1");
            $(this).find("input[name='member.addresses.address2']").attr("name", "member.addresses[" + index + "].address2");
            $(this).find("input[name='member.addresses.address3']").attr("name", "member.addresses[" + index + "].address3");
            $(this).find("input[name='member.addresses.address4']").attr("name", "member.addresses[" + index + "].address4");
        });
        
		
        // if($("#updateForm").data("kendoValidator").validate()) {
			if(confirm('저장하시겠습니까?')){
				$.showLoadingMessage();
						
				$("#updateForm").ajaxSubmit({
                    data: $("#updateForm").serialize(), 
					url: "/myinfo/update",
					type:'POST',
					success:function(data, statusText, xhr){
						$.showClosingMessage();
						if(data["code"] == "0"){
							alert('저장되었습니다.');
							location.reload();
						}else{
							alert(data["message"]);
						}
					}
				});
			}else{
				$.fieldErrorMessage($("#updateForm").data("kendoValidator"));
			}
		// }
	}
	
    // ********************************************   
    // 신규 행 추가
    // ********************************************
    //주소행 추가시 INDEX 번호부여
    var jusoAddCount = '${fn:length(result.member.addresses)}';
   
    function addNewRow(obj) {
        if (obj == "degreeInfo") {
            var degreeTR = "";
            degreeTR += '<tr height="30px">                                                                                     ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <input name="member.degrees.deptCode" type="hidden" style="width:98%" class="k-textbox k-invalid" value="30100" />    ';
            degreeTR += '        <input name="member.degrees.deptName" type="hidden" style="width:98%" class="k-textbox k-invalid" value="인문사회학부" /> ';
            degreeTR += $("#deptSelectbox").html();
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <select name="degreeType" onChange="changeDegreeType(this);">                                  ';
            degreeTR += '          <option value="BS">BS</option>                                                               ';
            degreeTR += '          <option value="MS">MS</option>                                                               ';
            degreeTR += '          <option value="PhD">PhD</option>                                                             ';
            degreeTR += '          <option value="UNITY">UNITY</option>                                                         ';
            degreeTR += '          <option value="PAMTIP">PAMTIP</option>                                                       ';
            degreeTR += '        </select>                                                                                      ';
            degreeTR += '        <input name="member.degrees.degreeType" type="hidden" value="BS" />                                   ';
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <input name="member.degrees.studentId" style="width:98%" class="k-textbox k-invalid" value="" />      ';
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <input name="member.degrees.entranceYear" style="width:98%" class="k-textbox k-invalid" value="" />   ';
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <input name="member.degrees.graduationYear" style="width:98%" class="k-textbox k-invalid" value="" /> ';
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '    <td class="TBC_td">                                                                                ';
            degreeTR += '        <span onclick="javascript:deleteDegreeRow(this);"><img src="/static/images/btn_delete.png"></span> ';
            degreeTR += '    </td>                                                                                              ';
            degreeTR += '</tr>                                                                                                  ';

            $("#degreeBody").append(degreeTR);
            
        } else if (obj == "addressInfo") {
            var addressTR = "";
            jusoAddCount ++ ;
            addressTR += ' <tr height="30px">                                                                                 ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <select name="addressType" style="width:99%" onChange="changeAddressType(this);">          ';
            addressTR += '           <option value="HOME">HOME</option>                                                       ';
            addressTR += '           <option value="WORK">WORK</option>                                                       ';
            // addressTR += '           <option value="MAILING">MAILING</option>                                                 ';
            addressTR += '         </select>                                                                                  ';
            addressTR += '        <input name="member.addresses.addressType" type="hidden" value="HOME" />                    ';
            addressTR += '        <input type="hidden" id="address_index" value="'+jusoAddCount+'" />                   	  ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td" style="text-align:center">                                                  ';
            addressTR += '         <input name="member.addresses.addressName" style="width:99%" class="k-textbox k-invalid" value="" />  ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                  ';
            addressTR += '         <input id="zipCode_'+jusoAddCount+'" name="member.addresses.zipCode" style="width:45%" class="k-textbox k-invalid" value="" />  <a class="btnLarge blue" href="javascript:void(0)" id="itx_postSearch" title="우편번호 검색 팝업이 열림" onClick="javascript:windowZipPopup('+jusoAddCount+');return false;"" style="width:35px;height:22px;"><font color="#fff">&nbsp;검색</font></a>';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <input id="address1_'+jusoAddCount+'" name="member.addresses.address1" style="width:99%" class="k-textbox k-invalid" value="" /> ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <input id="address2_'+jusoAddCount+'" name="member.addresses.address2" style="width:99%" class="k-textbox k-invalid" value="" /> ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <input id="address3_'+jusoAddCount+'" name="member.addresses.address3" style="width:99%" class="k-textbox k-invalid" value="" /> ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <input id="address4_'+jusoAddCount+'" name="member.addresses.address4" style="width:99%" class="k-textbox k-invalid" value="" /> ';
            addressTR += '     </td>                                                                                          ';
            addressTR += '     <td class="TBC_td">                                                                            ';
            addressTR += '         <span onclick="javascript:deleteAddressRow(this);"><img src="/static/images/btn_delete.png"></span> ';
            addressTR += '     </td>                                                                                          ';
            addressTR += ' </tr>                                                                                              ';

            $("#addressBody").append(addressTR);
        } 
    }
    
    // ********************************************   
    // 학위정보 현재 행 삭제
    // ********************************************   
    function deleteDegreeRow(obj){
      if (!confirm('삭제하시겠습니까?')) {
          return false;  
      }
        
      var _id = $(obj).closest("tr").find("input[name='member.degrees.id']").val();

      if (_id == "undefined" || _id == null || $.trim(_id) == "") {
          $(obj).closest("tr").remove();
      } else {
          $.ajax({
              url: '/myinfo/delete/' + _id + "/degreeInfo",
              success : function(data){
                  $(obj).closest("tr").remove();
                  alert("삭제되었습니다.");
              },
              error : function(){
                  alert("error");
              }
          });
      }
    } 

    // ********************************************   
    // 주소정보 현재 행 삭제
    // ********************************************   
    function deleteAddressRow(obj){
      if (!confirm('삭제하시겠습니까?')) {
          return false;  
      }
        
      var _id = $(obj).closest("tr").find("input[name='member.addresses.id']").val();
      
      if (_id == "undefined" || _id == null || $.trim(_id) == "") {
          $(obj).closest("tr").remove();
      } else {
          $.ajax({
              url: '/myinfo/delete/' + _id + "/addressInfo",
              success : function(data){
                  $(obj).closest("tr").remove();
                  alert("삭제되었습니다.");
              },
              error : function(){
                  alert("error");
              }
          });
      }
    } 

    // ********************************************   
    // 학위 변경시
    // ********************************************   
    function changeDegreeType(obj) {
        var _degreeType = $(obj).val();
        $(obj).closest("tr").find("input[name='member.degrees.degreeType']").val(_degreeType);
    }
 
    // ********************************************   
    // 주소구분 변경시
    // ********************************************   
    function changeAddressType(obj) {
        var _addressType = $(obj).val();
        $(obj).closest("tr").find("input[name='member.addresses.addressType']").val(_addressType);
    }
 

    // ********************************************   
    // 학과 selectBox 변경시
    // ********************************************   
    function deptSelectChange(obj) {
        var _deptCode = $(obj).val();
        var _deptName = $(obj).closest("tr").find("#deptCode option:selected").text();

        $(obj).closest("tr").find("input[name='member.degrees.deptCode']").val(_deptCode);
        $(obj).closest("tr").find("input[name='member.degrees.deptName']").val(_deptName);
        
    }

    function checkDateFormat(text) {
        var regex = /\d{4}-\d{2}-\d{2}/;

        if(regex.test(text.value)) {
            return true;
        }
        else {
            alert("날짜는 \'YYYY-MM-DD\' 형태로 입력해주세요");
            text.focus();
            return false;
        }
    }

    // ********************************************   
    //   
    // ********************************************   
    $(document).ready(function(){
		
        // 저장 버튼
        $("body").on('click', '#btnSave', function(e) {
            e.preventDefault();
            save();
        });

        // 취소 버튼
        $("body").on('click', '#btnClose', function(e) {
            parent.$("#updateFormLayer").data("kendoWindow").close();
        });

	});
    
    
//2018.03.26 박재현
//주소검색
var jusoIndex = 0;
function windowZipPopup(index) {
	var pop = window.open("/static/juso/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
	jusoIndex = index;
	$("#itx_postSearch").focus();
	return false;
}

function jusoCallBack(siNm,sggNm,roadAddr,zipNo,addrDetail){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	$("#zipCode_"+jusoIndex).val(zipNo); 			//우편번호
	$("#address1_"+jusoIndex).val(siNm + sggNm);			//시도 ,군구
	// $("#address2_"+jusoIndex).val(sggNm);			//군구
	$("#address2_"+jusoIndex).val(roadAddr);		//도로명
	$("#address3_"+jusoIndex).val(addrDetail);		//상세주소
}

</script>
</head>
<body class="index">
	<form id="updateForm" name="updateForm" method="post" >
	<input type="hidden" id="memberId" name="member.id" value='${result.member.id}'/>
	<input type="hidden" id="memberId" name="id" value='${result.id}'/>
    <h3>[ 기본정보 ]</h3>
	<div class="tableType2">
	<table id="memberInfo" class="table_sc">

        <colgroup>
            <col width="10%" />
            <col width="22%" />
            <col width="13%" />
            <col width="22%" />
            <col width="11%" />
            <col width="22%" />
        </colgroup>
        <tr height="24px">
            <th class="TBC_th" >회원명</th>
            <td class="TBC_td">${result.member.name}
                <input type="hidden" id="name" name="member.name" style="width:98%" class="k-textbox k-invalid" value="${result.member.name}" />
            </td>
            <th class="TBC_th">성별</th>
            <td class="TBC_td">
                <select name="member.gender" style="width:98%">
                    <option <c:if test="${result.member.gender == 'MALE' }">selected</c:if> value="MALE">남성</option>
                    <option <c:if test="${result.member.gender == 'FEMALE' }">selected</c:if> value="FEMALE">여성</option>
                    <option <c:if test="${result.member.gender == 'OTHER' }">selected</c:if> value="PhD">기타</option>
                    <option <c:if test="${result.member.gender == 'NO_COMMENT' }">selected</c:if> value="NO_COMMENT">응답없음</option>
                </select>
            </td>
            <th class="TBC_th">국적</th>
            <td class="TBC_td">
                <input id="nationality" name="member.nationality" style="width:98%" class="k-textbox k-invalid" value="${result.member.nationality}" />
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">생년월일</th>
            <td class="TBC_td">${result.member.birthdayOfficial}
                <input type="hidden" id="birthdayOfficial" name="member.birthdayOfficial" style="width:98%" class="k-textbox k-invalid" placeholder="YYYY-MM-DD" onchange="checkDateFormat(this)" value="${result.member.birthdayOfficial}"/>
            </td>
            <th class="TBC_th">실제생일 구분</th>
            <td class="TBC_td">
                <%--<input type="birthdayType" id="birthdayType" name="email" style="width:98%" class="k-textbox k-invalid" value="${result.birthdayType}" />--%>
                <select name="member.birthdayType" style="width:98%">
                    <option <c:if test="${result.member.birthdayType == 'SOLAR' }">selected</c:if> value="SOLAR">양력</option>
                    <option <c:if test="${result.member.birthdayType == 'LUNAR' }">selected</c:if> value="LUNAR">음력</option>
                </select>
            </td>
            <th class="TBC_th">실제생일</th>
            <td class="TBC_td">
                <input id="birthdayReal" name="member.birthdayReal" style="width:98%" class="k-textbox k-invalid" placeholder="YYYY-MM-DD" onchange="checkDateFormat(this)" value="${result.member.birthdayReal}" />
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">이메일</th>
            <td class="TBC_td">
                <input type="email" id="email" name="member.email" style="width:98%" class="k-textbox k-invalid" value="${result.member.email}" />
            </td>
            <th class="TBC_th">휴대폰</th>
            <td class="TBC_td">
                <input id="mobile" name="member.mobile" style="width:98%" class="k-textbox k-invalid" value="${result.member.mobile}" />
            </td>
            <th class="TBC_th">전화번호</th>
            <td class="TBC_td">
                <input type="phone" id="phone" name="member.phone" style="width:98%" class="k-textbox k-invalid" value="${result.member.phone}" />
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">평생회비</th>
            <td class="TBC_td">
                <select name="member.membershipFeeStatus" style="width:98%" onclick='this.initialValue=this.selectedIndex;' onfocus='this.initialValue=this.selectedIndex;' onchange='this.selectedIndex=this.initialSelect;'>
                    <option <c:if test="${result.member.membershipFeeStatus != 'LIFE_MEMBER' }">selected</c:if> value="NO_LIFE_MEMBER">평생회원 아님</option>
                    <option <c:if test="${result.member.membershipFeeStatus == 'LIFE_MEMBER' }">selected</c:if> value="LIFE_MEMBER">평생회원</option>
                </select>
            </td>
            <th class="TBC_th">최근회비납부일</th>
            <td class="TBC_td">
                <input id="membershipFeeDate" name="member.membershipFeeDate" style="width:98%" class="k-textbox k-invalid" readonly value="${result.member.membershipFeeDate}" />
            </td>
            <th class="TBC_th">총납부회비</th>
            <td class="TBC_td">
                <input type="membershipFeeTotal" id="membershipFeeTotal" name="member.membershipFeeTotal" style="width:98%" class="k-textbox k-invalid" readonly value="${result.member.membershipFeeTotal}" />
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">생존여부</th>
            <td class="TBC_td">
                <%--<input type="membershipFeeStatus" id="membershipFeeStatus" name="email" style="width:98%" class="k-textbox k-invalid" value="${result.membershipFeeStatus}" />--%>
                <select name="member.isAlive" style="width:98%" onclick='this.initialValue=this.selectedIndex;' onfocus='this.initialValue=this.selectedIndex;' onchange='this.selectedIndex=this.initialSelect;'>
                    <option <c:if test="${result.member.isAlive == true }">selected</c:if> value="true">생존</option>
                    <option <c:if test="${result.member.isAlive == false }">selected</c:if> value="false">사망</option>
                </select>
            </td>
            <th class="TBC_th">결혼여부</th>
            <td class="TBC_td">
                <select name="member.isMarried" style="width:98%">
                    <option <c:if test="${result.member.isMarried == ture }">selected</c:if> value="true">기혼</option>
                    <option <c:if test="${result.member.isMarried == false }">selected</c:if> value="false">미혼</option>
                </select>
            </td>
            <th class="TBC_th">우편물 수령지</th>
            <td class="TBC_td">
                <select name="member.mailingAddress" style="width:98%">
                    <option <c:if test="${result.member.mailingAddress == 'HOME' }">selected</c:if> value="HOME">HOME</option>
                    <option <c:if test="${result.member.mailingAddress == 'WORK' }">selected</c:if> value="WORK">WORK</option>
                </select>
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">현재직장명</th>
            <td class="TBC_td">
                <input id="currentWork" name="member.currentWork" style="width:98%" class="k-textbox k-invalid" value="${result.member.currentWork}" />
            </td>
            <th class="TBC_th">현재부서</th>
            <td class="TBC_td">
                <input id="currentWorkDept" name="member.currentWorkDept" style="width:98%" class="k-textbox k-invalid" value="${result.member.currentWorkDept}" />
            </td>
            <th class="TBC_th">현재직책</th>
            <td class="TBC_td">
                <input id="currentJobTitle" name="member.currentJobTitle" style="width:98%" class="k-textbox k-invalid" value="${result.member.currentJobTitle}" />
            </td>
        </tr>

    </table>
	</div>
	
    <br />
    <h3>[ 학위 ]</h3>
    <!-- <a href="javascript:addNewRow('degreeInfo');">[추가]</a> -->
    <%--<div class="btnBox" style="text-align:right; !important; padding-top:0px;">--%>
        <%--<a href="javascript:addNewRow('degreeInfo');"><span id="btnNew" class="btnLarge" style="margin:2px 0px;width:30px;">추가</span></a>--%>
    <%--</div>--%>
    
    <div class="tableType2">
    <table id="degreeInfo" class="table_sc">
        <thead>
        <tr height="15px" class="TBC_th">
            <th data-field="degrees.department" class="TBC_th" style="width:20%">학과</th>
            <th data-field="degrees.degreeType" class="TBC_th" style="width:16%">학위</th>
            <th data-field="degrees.studentId" class="TBC_th" style="width:16%">학번</th>
            <th data-field="degrees.entranceYear" class="TBC_th" style="width:16%">입학연도</th>
            <th data-field="degrees.graduationYear" class="TBC_th" style="width:16%">졸업연도</th>
            <th data-field="degrees.supervisor" class="TBC_th" style="width:16%">지도교수</th>
        </tr>
        </thead>
        <tbody id="degreeBody">
        <c:forEach items="${result.member.degrees}" var="degree" varStatus="status">
            <tr height="30px">
                <td class="TBC_td">
                    <input type="hidden" id="id" name="member.degrees.id" style="width:98%" value="${degree.id}" />
                    <input type="hidden"  id="deptCode" name="member.degrees.deptCode" style="width:98%" value="${degree.deptCode}" class="k-textbox k-invalid" />
                    <input type="hidden"  id="deptName" name="member.degrees.deptName" style="width:98%" value="${degree.deptName}" class="k-textbox k-invalid" />
			        <c:choose>
			            <c:when test="${fn:length(deptList) > 1}">
			            <select name="deptCode" id="deptCode" style="width:200px" onclick='this.initialValue=this.selectedIndex;' onfocus='this.initialValue=this.selectedIndex;' onchange='this.selectedIndex=this.initialSelect;'>
			            <%--<select name="deptCode" id="deptCode" style="width:200px" onchange="javascript:deptSelectChange(this);">       --%>
			                <c:forEach items="${deptList}" var="dept" varStatus="status">
			                    <option value="${dept.deptCode}" ${degree.deptCode == dept.deptCode ? 'selected' : ''}>${dept.deptName}</option>
			                </c:forEach>
			            </select>
			            </c:when>
			        </c:choose>

                </td>
                <td class="TBC_td">
					<select name="degreeType" onclick='this.initialValue=this.selectedIndex;' onfocus='this.initialValue=this.selectedIndex;' onchange='this.selectedIndex=this.initialSelect;'>
					<%--<select name="degreeType" onChange="changeDegreeType(this);">--%>
					  <option <c:if test="${degree.degreeType == 'BS' }">selected</c:if> value="BS">BS</option>
					  <option <c:if test="${degree.degreeType == 'MS' }">selected</c:if> value="MS">MS</option>                             
					  <option <c:if test="${degree.degreeType == 'PhD' }">selected</c:if> value="PhD">PhD</option>                           
					  <option <c:if test="${degree.degreeType == 'UNITY' }">selected</c:if> value="UNITY">UNITY</option>                       
					  <option <c:if test="${degree.degreeType == 'PAMTIP' }">selected</c:if> value="PAMTIP">PAMTIP</option>                     
					</select>                                                    
                    <input id="degreeType" type="hidden" name="member.degrees.degreeType" style="width:98%" value="${degree.degreeType}" class="k-textbox k-invalid" />
                </td>
                <td class="TBC_td">
                    <input id="studentId" name="member.degrees.studentId" style="width:98%" value="${degree.studentId}" class="k-textbox k-invalid" readonly />
                </td>
                <td class="TBC_td">
                    <input id="entranceYear" name="member.degrees.entranceYear" style="width:98%" value="${degree.entranceYear}" class="k-textbox k-invalid" readonly />
                </td>
                <td class="TBC_td">
                    <input id="graduationYear" name="member.degrees.graduationYear" style="width:98%" value="${degree.graduationYear}" class="k-textbox k-invalid" readonly />
                </td>
                <td class="TBC_td">
                    <input id="supervisor" name="member.degrees.supervisor" style="width:98%" value="${degree.supervisor}" class="k-textbox k-invalid" readonly />
                    <%--<span onclick="javascript:deleteDegreeRow(this);"><img src="/static/images/btn_delete.png"></span>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br />
    </div>

    <br />
    <h3>[ 주소 ]</h3>
    <!-- <a href="javascript:addNewRow('addressInfo');">[추가]</a> -->
    <div class="btnBox" style="text-align:right; !important; padding-top:0px;">
        <a href="javascript:addNewRow('addressInfo');"><span id="btnNew" class="btnLarge" style="margin:2px 0px;width:30px;">추가</span></a>
    </div>

    <div class="tableType2">
    <table id="addressInfo" class="table_sc">
        <thead>
        <tr height="15px" class="TBC_th">
            <th data-field="addresses.addressType" class="TBC_th" style="width:10%">주소타입</th>
            <%--<th data-field="addresses.addressName" class="TBC_th" style="width:8%">주소명칭</th>--%>
            <th data-field="addresses.zipCode" class="TBC_th" style="width:12%;">우편번호</th>
            <th data-field="addresses.address1" class="TBC_th" style="width:17%">시도 / 구군</th>
            <th data-field="addresses.address2" class="TBC_th" style="width:17%">도로명</th>
            <th data-field="addresses.address3" class="TBC_th" style="width:17%">상세주소</th>
            <%--<th data-field="addresses.address4" class="TBC_th" style="width:17%">주소4</th>--%>
            <th class="TBC_th" style="width:5%">삭제</th>
        </tr>
        </thead>
        <tbody id="addressBody">
        <c:forEach items="${result.member.addresses}" var="address" varStatus="status">
            <tr height="30px">
                <td class="TBC_td">
                    <%-- <input id="addressType" name="addresses.addressType" style="width:98%" value="${address.addressType}" class="k-textbox k-invalid" /> --%>
					<select name="addressType" style="width:99%" onChange="changeAddressType(this);">
                      <option <c:if test="${address.addressType == 'HOME' }">selected</c:if> value="HOME">HOME</option>                                             
					  <option <c:if test="${address.addressType == 'WORK' }">selected</c:if> value="WORK">WORK</option>
					</select>                                                                        
					<input name="member.addresses.addressType" type="hidden" value="${address.addressType}" />                
                    <input type="hidden" id="addressId" name="member.addresses.id" style="width:98%" value="${address.id}" />
                    <input type="hidden" id="address_index" value="${status.index}" />
                </td>
                <%--<td class="TBC_td">--%>
                    <%--<input id="addressName" name="member.addresses.addressName" style="width:98%" value="${address.addressName}" class="k-textbox k-invalid" />--%>
                <%--</td>--%>
                <td class="TBC_td">
                	<div class="" style="display: inline-block;position: relative">
	                    <input id="zipCode_${status.index}" name="member.addresses.zipCode" style="width:45%" value="${address.zipCode}" class="k-textbox k-invalid" />
	                    <a class="btnLarge blue" href="javascript:void(0)" id="itx_postSearch" title="우편번호 검색 팝업이 열림" onClick="javascript:windowZipPopup(${status.index});return false;"" style="width:35px;height:22px;"><font color="#fff">&nbsp;검색</font></a>
					</div>  
                </td>
                <td class="TBC_td">
                    <input id="address1_${status.index}" name="member.addresses.address1" style="width:98%" value="${address.address1}" class="k-textbox k-invalid" />
                </td>
                <td class="TBC_td">
                    <input id="address2_${status.index}" name="member.addresses.address2" style="width:98%" value="${address.address2}" class="k-textbox k-invalid" />
                </td>
                <td class="TBC_td">
                    <input id="address3_${status.index}" name="member.addresses.address3" style="width:98%" value="${address.address3}" class="k-textbox k-invalid" />
                </td>
                <td class="TBC_td">
                    <input id="address4_${status.index}" name="member.addresses.address4" style="width:98%" value="${address.address4}" class="k-textbox k-invalid" />
                </td>
                <td class="TBC_td">
                    <span onclick="javascript:deleteAddressRow(this);"><img src="/static/images/btn_delete.png"></span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br />
    
    </div>
	</form>

    <div id="degreeGrid">
    </div>
	
	<div class="btnBox">
        <a href="javascript:void(0);"><span id="btnClose" class="btnLarge">취소</span></a>
        <a href="javascript:void(0);"><span id="btnSave" class="btnLarge blue">저장</span></a>
        <!--
		<span id="btnClose" class="btnLarge"><button>취소</button></span>
		<span id="btnSave" class="btnLarge blue"><button>저장</button></span>
		 -->
	</div>

    <!-- 학과 selectBox  -->
    <div id="deptSelectbox" style="display:none;">
        <c:choose>                                                                     
            <c:when test="${fn:length(deptList) > 1}">                                 
            <select name="deptCode" id="deptCode" style="width:200px" onchange="javascript:deptSelectChange(this);">       
                <c:forEach items="${deptList}" var="dept" varStatus="status">          
                    <option value="${dept.deptCode}">${dept.deptName}</option> 
                </c:forEach>                                                           
            </select>                                                                  
            </c:when>                                                                  
        </c:choose>                                                                   
    </div>

</body>
</html>
