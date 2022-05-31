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

        // 확인 버튼
        $("body").on('click', '#btnClose', function(e) {
            parent.$("#viewFormLayer").data("kendoWindow").close();
        });

	});
	
</script>
</head>
<body class="index">
	<form id="updateForm" name="updateForm" method="post" >
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
            <th class="TBC_th">*회원명</th>
            <td class="TBC_td">
                ${result.name}
            </td>
            <th class="TBC_th">성별</th>
            <td class="TBC_td">
                <c:if test="${result.gender == 'MALE' }">남성</c:if>
                <c:if test="${result.gender == 'FEMALE' }">여성</c:if>
                <c:if test="${result.gender == 'OTHER' }">기타</c:if>
                <c:if test="${result.gender == 'NO_COMMENT' }">응답없음</c:if>
            </td>
            <th class="TBC_th">국적</th>
            <td class="TBC_td">
                ${result.nationality}
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">생년월일</th>
            <td class="TBC_td">
                ${result.birthdayOfficial}
            </td>
            <th class="TBC_th">실제생일 구분</th>
            <td class="TBC_td">
            <c:if test="${result.birthdayType == 'SOLAR' }">양력</c:if>
            <c:if test="${result.birthdayType == 'LUNAR' }">음력</c:if>
            </td>
            <th class="TBC_th">실제생일</th>
            <td class="TBC_td">
                ${result.birthdayReal}
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">이메일</th>
            <td class="TBC_td">
                ${result.email}
            </td>
            <th class="TBC_th">휴대폰</th>
            <td class="TBC_td">
                ${result.mobile}
            </td>
            <th class="TBC_th">전화번호</th>
            <td class="TBC_td">
                ${result.phone}
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">생존여부</th>
            <td class="TBC_td">
                    <c:if test="${result.isAlive == true }">생존</c:if> 
                    <c:if test="${result.isAlive == false }">사망</c:if>                
            </td>
            <th class="TBC_th">결혼여부</th>
            <td class="TBC_td">                
                    <c:if test="${result.isMarried == ture }">기혼</c:if>
                    <c:if test="${result.isMarried == false }">미혼</c:if>
            </td>
            <th class="TBC_th">우편물 수령지</th>
            <td class="TBC_td">
                비공개
            </td>
        </tr>
        <tr height="24px">
            <th class="TBC_th">현재직장명</th>
            <td class="TBC_td">
                <input id="currentWork" name="member.currentWork" style="width:98%" class="k-textbox k-invalid" value="${result.currentWork}" />
            </td>
            <th class="TBC_th">현재부서</th>
            <td class="TBC_td">
                <input id="currentWorkDept" name="member.currentWorkDept" style="width:98%" class="k-textbox k-invalid" value="${result.currentWorkDept}" />
            </td>
            <th class="TBC_th">현재직책</th>
            <td class="TBC_td">
                <input id="currentJobTitle" name="member.currentJobTitle" style="width:98%" class="k-textbox k-invalid" value="${result.currentJobTitle}" />
            </td>
        </tr>

    </table>
	</div>
	
    <br />
    <h3>[ 학위 ]</h3>
        
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
        <c:forEach items="${result.degrees}" var="degree" varStatus="status">
            <tr height="30px">
                <td class="TBC_td" align="center">
			        <c:choose>                                                                     
			            <c:when test="${fn:length(deptList) > 1}">                                 
			           	 	<c:forEach items="${deptList}" var="dept" varStatus="status">          
			                   	<c:if test="${degree.deptCode == dept.deptCode}">
			                   		${dept.deptName}
			                   	</c:if> 
			                </c:forEach>                                                                  
			            </c:when>                                                                  
			        </c:choose>
                </td>
                <td class="TBC_td" align="center">
					  <c:if test="${degree.degreeType == 'BS' }">BS</c:if>                              
					  <c:if test="${degree.degreeType == 'MS' }">MS</c:if>                              
					  <c:if test="${degree.degreeType == 'PhD' }">PhD</c:if>                            
					  <c:if test="${degree.degreeType == 'UNITY' }">UNITY</c:if>                       
					  <c:if test="${degree.degreeType == 'PAMTIP' }">PAMTIP</c:if> 
                </td>
                <td class="TBC_td" align="center">
              	  	${degree.studentId}
                </td>
                <td class="TBC_td" align="center">
              		${degree.entranceYear}
                </td>
                <td class="TBC_td" align="center">
                    ${degree.graduationYear}
                </td>
                <td class="TBC_td" align="center">
                        ${degree.supervisor}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br />
    </div>

    <br />
    <h3>[ 주소 ]</h3>
    <div class="tableType2">
    <table id="addressInfo" class="table_sc">
        <thead>
        <tr height="15px" class="TBC_th">
            <th data-field="addresses.addressType" class="TBC_th" style="width:10%">주소타입</th>
            <th data-field="addresses.zipCode" class="TBC_th" style="width:8%;">우편번호</th>
            <th data-field="addresses.address1" class="TBC_th" style="width:15%">시/도</th>
            <th data-field="addresses.address2" class="TBC_th" style="width:15%">시/군/구</th>
            <th data-field="addresses.address3" class="TBC_th" style="width:25%">도로명</th>
            <th data-field="addresses.address4" class="TBC_th" style="width:15%">세부주소</th>
        
        </tr>
        </thead>
        <tbody id="addressBody">
        <c:forEach items="${result.addresses}" var="address" varStatus="status">
            <tr height="30px">
                <td class="TBC_td">
                      <c:if test="${address.addressType == 'HOME' }">HOME</c:if>                                              
					  <c:if test="${address.addressType == 'WORK' }">WORK</c:if>                                              
					  <c:if test="${address.addressType == 'MAILING' }">MAILING</c:if> 
                </td>
                <%--<td class="TBC_td">--%>
                	<%--${address.addressName}--%>
                <%--</td>--%>
                <td class="TBC_td">
                	${address.zipCode}
                </td>
                <td class="TBC_td">
                	${address.address1}
                </td>
                <td class="TBC_td">
                	${address.address2}
                </td>
                <td class="TBC_td">
                	${address.address3}
                </td>
                <td class="TBC_td">
                	${address.address4}
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
        <a href="javascript:void(0);"><span id="btnClose" class="btnLarge">확인</span></a>
	</div>

</body>
</html>
