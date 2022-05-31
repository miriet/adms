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
//ID 중복체크
function goCheckDupUserId() {
	$("#itx_memIdCheck").val($("#itx_loginId").val());
	if ($("#itx_loginId").val() == '') {
		alert("아이디를 입력해 주세요.");
		return;
	} else {
		$("#joinForm").ajaxSubmit({
			url: "/join/checkDupMemberId",
			type:'POST',
			async:true,
			success:function(data, statusText, xhr){
				if (data["code"] == "0") {
					$("#itx_checkDupUserId").val("Y");
					alert("사용가능한 아이디 입니다.")
				} else if (data["code"] == "-1") {
					$("#itx_checkDupUserId").val("");
					alert("아이디는 4자~20자 이하 영문과 숫자 또는 영문+숫자만으로 등록할 수 있습니다.");
				} else {
					$("#itx_checkDupUserId").val("");
					alert("이미 사용중인 아이디 입니다.");
				}
			},
			error: function(xhr, status, err) {
    				alert("에러가 발생하였습니다.다시 시도하여 주시기 바랍니다.");
            }
		});
	}
}

//중복체크 이후 ID 변경 방지
function goClearCheckDupUserId() {
	$("#itx_checkDupUserId").val("");
}

//회원가입
function save(){
	
	if ($("input:checkbox[id=itx_memberPolicy]").is(":checked") == false) {
		alert("회원약관에 동의하지 않으셨습니다");
		return;
	}
	
	// if ($("#itx_name").val() == '') {
	// 	alert("성명을 입력해 주세요.");
	// 	return;
	// }
	//
	// if ($("#itx_birthday").val() == '') {
	// 	alert("생년월일을 입력해 주세요.");
	// 	return;
	// }

    if ($("#itx_name").val() == '' || $("#itx_birthday").val() == '') {
    	alert("본인인증을 해 주세요.");
    	return;
    }

	if ($("#itx_loginId").val() == '') {
		alert("아이디를 입력해 주세요.");
		return;
	}
	
	if ($("#itx_checkDupUserId").val() == '') {
		alert("아이디 중복확인을 해주세요.");
		return;
	}
	
	//영문, 숫자 혼용해서 8~20자 이내
	var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*?]/;     // 원하는 특수문자 추가 제거
	var pwd1 = $("#itx_passwd").val();
	if (pwd1 == '') {
		alert("비밀번호를 입력해 주세요.");
		return;
	}
	
	if(!pattern1.test(pwd1)||!pattern2.test(pwd1)||!pattern3.test(pwd1)||pwd1.length<8||pwd1.length>20){
		alert("영문 대ㆍ소문자,숫자,특수문자를 조합하여 8~20자리를 사용하세요.");
		$.skyUnBlockUI();
		return false;
	} else if(/(\w)\1\1/.test(pwd1)) {
		alert('패스워드에 같은 문자를 3번 이상 사용하실 수 없습니다.');
		$.skyUnBlockUI();
		return false;
	}
	
	var pwd2 = $("#itx_passwd2").val();
	if(pwd1 != pwd2) {
		alert('비밀번호가 일치하지 않습니다.'); 
		$.skyUnBlockUI();
		return false;
	}
	
	if ($("#itx_email").val() == '') {
		alert("이메일을 입력해주세요.");
		return;
	}
	
	// 본인인증 팝업 실행
	// auth_type_check();
	goJoinProcess();
}

// 인증창 호출 함수
function auth_type_check() {
    var auth_form = document.form_auth;
    if( ( navigator.userAgent.indexOf("Android") > - 1 || navigator.userAgent.indexOf("iPhone") > - 1 ) == false ) {// 스마트폰이 아닌경우
        var return_gubun;
        var width  = 410;
        var height = 500;

        var leftpos = screen.width  / 2 - ( width  / 2 );
        var toppos  = screen.height / 2 - ( height / 2 );

        var winopts  = "width=" + width   + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
        var position = ",left=" + leftpos + ", top="    + toppos;
        var AUTH_POP = window.open('', 'auth_popup', winopts + position);
    }
    
    auth_form.target = "auth_popup"; // !!주의 고정값 ( 리턴받을때 사용되는 타겟명입니다.)
    auth_form.action = "/kcpCert/certRequest"; // 인증창 호출 및 결과값 리턴 페이지 주소
    auth_form.submit();
    
    return true;
    
}

// 본인인증 종료후 인증데이터 리턴 함수
function auth_data( frm ) {
    var auth_form = document.form_auth;
    var nField    = frm.elements.length;
    /* 
    // up_hash 검증 
    if ( frm.up_hash.value != auth_form.veri_up_hash.value ) {
        alert("up_hash 변조 위험있음");
    }              
    */
	var form_value = "";
	var form_html = "";
    for ( i=0 ; i<frm.length; i++) {
    	if (frm.elements[i].name == 'kcpName')     $("#itx_kcpName").val(frm.elements[i].value);
    	if (frm.elements[i].name == 'kcpBirthday') $("#itx_kcpBirthday").val(frm.elements[i].value);
    	if (frm.elements[i].name == 'kcpName')     $("#itx_name").val(frm.elements[i].value);
    	if (frm.elements[i].name == 'kcpBirthday') $("#itx_birthday").val(frm.elements[i].value);
    	if (frm.elements[i].name == 'kcpCi')       $("#itx_ci").val(frm.elements[i].value);
    	if (frm.elements[i].name == 'kcpDi')       $("#itx_di").val(frm.elements[i].value);
        //form_value += "["+frm.elements[i].name + "] = [" + frm.elements[i].value + "]\n";
        //form_html += "<input type='hidden' name='" + frm.elements[i].name + "' value='" + frm.elements[i].value + "'/>";
        //retParam.append("<input type=\"hidden\" name=\"phoneNo\" value=\"" + cc.getKeyValue("phone_no") + "\"/>");

    }
    
    // 회원가입처리
    // goJoinProcess();
}

function goJoinProcess() {
	$("#joinForm").ajaxSubmit({
		url: "/join/insertJoin",
		type:'POST',
		async:true,
		success:function(data, statusText, xhr){
			//$.showClosingMessage();
			if (data["code"] == "0") {
				alert("가입되었습니다.");
				location.href="/login";
			} else if (data["code"] == "-1") {
				resetAll();
				alert("회원정보와 본인인증 정보가 일치하지 않습니다.");
			} else if (data["code"] == "-2") {
				resetAll();
				alert("이미 가입된 회원입니다..");
			} else {
				resetAll();
				alert("동문등록이 되어 있지 않아, 가입이 불가능 합니다.");
			}
		},
		error : function(xhr, status, err) {
			resetAll();
			alert("에러가 발생하였습니다.다시 시도하여 주시기 바랍니다.");
		}
	});
}

function resetAll(){
	document.joinForm.reset();
}

function onlyNumber(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if( ( keyID >=48 && keyID <= 57 ) || ( keyID >=96 && keyID <= 105 ) || keyID == 8 || keyID == 9 || keyID == 110) {
		return true;
	}
	else {
		return false;
	}
}

$(document).ready(function(){
    auth_type_check();

});

</script>
</head>
<body class="index">

	<form name="joinForm" id="joinForm" method="post" >
		<input type="hidden" name="kcpName" id="itx_kcpName" value="" />
		<input type="hidden" name="kcpBirthday" id="itx_kcpBirthday" value="" />
		<input type="hidden" name="ci" id="itx_ci" value="" />
		<input type="hidden" name="di" id="itx_di" value="" />
		
		<div class="login" style="padding:0 0;width:680px;height:620px;margin:5% auto;">
			<h2 style="text-align:center; padding:20px 0;">회원가입</h2>
			
			<textarea rows="10" title="회원약관" readonly="readonly" style="width:600px;margin-left:30px;padding:5px 10px;">

제1장 총칙

  제1조 목적 

    이 약관은 포항공과대학교 동문정보 관리 시스템(이하 "ADMS"라 한다)을 이용함에 있어 사이트와 이용자의 권리의무 및 책임사항을 규정함을 목적으로 합니다.

  제2조 용어 정의 

    이 약관에서 사용하는 용어의 정의는 다음과 같습니다. 
    "사이트"라 함은 포항공과대학교가 컴퓨터 등 정보통신설비를 이용하여 제공할 수 있도록 설정한 가상의 공간을 말합니다.
    "서비스"라 함은 포항공과대학교 동문정보관리시스템에서 제공하는 인터넷 상의 모든 서비스를 말합니다.
    "회원(이용자)"이라 함은 본 약관에 동의하고, ADMS에 로그인하여 본 약관에 따라 ADMS가 제공하는 서비스를 받는 자를 말합니다.
    "운영자"라 함은 서비스의 전반적인 관리와 원활한 운영을 위하여 포항공과대학교에서 선정한 사람을 말합니다.
    "ID"라 함은 회원이 서비스에 제공받기 위하여 본 사이트로 접속할 수 있는 Login 명을 의미하며 
    영문, 숫자, 영문과 숫자의 조합으로 합니다.
    "비밀번호"라 함은 회원의 비밀보호 및 회원 본인 임을 확인하고 서비스에 제공되는 각종 정보의 보안을 위해 회원 자신이 설정하며 포항공과대학교가 승인하는
    영문, 숫자, 특수문자의 혼합으로 8자에서 20자 사이로 표기한 암호 문자를 말합니다.
    "개인정보"라 함은 당해 정보에 포함되어 있는 성명, 학번 등의 사항에 의하여 특정개인을 식별할 수 있는 정보를 말합니다.
    회원은 일반회원, 총동창회, 운영자로 구분되며, 각 회원은 다음과 같은 권한을 가지고 있습니다.
    일반회원 : << 일반회원 >>
    총동창회 : << 총동창회 >>
    운영자 : << 운영자 >>

  제3조 약관의 게시 및 변경 

    << 약관의 게시 및 변경 >>

			</textarea>
			<div class="id" style="padding:0 0;">
				<input type="checkbox" name="memberPolicy" id="itx_memberPolicy" style="width:15px;height:29px;margin-left:40px;vertical-align:middle;" />
				<label for="itx_memberPolicy" style="width:500px;margin-left:65px;vertical-align:middle;"> 회원약관을 이해했으며, 내용에 동의합니다.</label>
			</div>
			
			
			<table style="border-top:1px solid #aaa;border-bottom:1px solid #aaa;margin:30px auto;width:600px;">
				<caption class="sr-only">회원가입 회원정보 입력 화면</caption>
				<colgroup>
					<col width="25%" />
					<col width="%" />
				</colgroup>
				<tbody>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">성명</th>
						<td><input type="text" name="name" id="itx_name" readonly/></td>
					</tr>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">생년월일</th>
						<td><input type="text" name="birthday" id="itx_birthday" readonly onkeydown="return onlyNumber(event)" /></td>
					</tr>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">아이디</th>
						<td>
							<input type="text" name="loginId" id="itx_loginId" onChange="javascript:goClearCheckDupUserId()" style="display:inline-block;" />
							<a href="javascript:void(0)" onclick="javascript:goCheckDupUserId()" class="btnLarge blue" style="text-align:center;color:#fff;width:100px;height:20px">중복확인</a>
							<input type="hidden" name="checkDupUserId" id="itx_checkDupUserId" />
						</td>
					</tr>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">비밀번호</th>
						<td><input type="password" name="password" id="itx_passwd" placeholder="영문 대ㆍ소문자,숫자,특수문자 조합 8-20자리" /></td>
					</tr>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">비밀번호 확인</th>
						<td><input type="password" name="passwordConfirm" id="itx_passwd2" /></td>
					</tr>
					<tr style="height:30px;">
						<th style="text-align:center;padding:10px 0;">E-Mail</th>
						<td><input type="text" name="email" id="itx_email" /></td>
					</tr>
				</tbody>
			</table>
			<!-- <div name="div_auth" id="div_auth"></div> -->
			<div style="text-align:center;">
				<!-- <a href="javascript:void(0)" onclick="javascript:auth_type_check()" class="btnLarge blue" style="color:#fff;height:20px;">회원가입</a> -->
				<a href="javascript:void(0)" onclick="javascript:save()" class="btnLarge blue" style="color:#fff;height:20px;width:150px;">회원가입(본인인증)</a>
			</div>
		</div>
	</form>
	
    <!-- 본인인증 폼  -->
    <form name="form_auth">
        <!-- 주문번호 -->
        <input type="hidden" name="ordr_idxx" value="POSTECH.ADMS"/>
        
        <!-- 성명 -->
        <input type="hidden" name="user_name" value=""/>
        
        <!-- 성별구분 -->
        <input type="hidden" name="sex_code" value=""/>
        
        <!-- 내/외국인구분 -->
        <input type="hidden" name="local_code" value=""/>
    
	    <!-- 요청종류 (고정값 : cert )-->
	    <input type="hidden" name="req_tx" value="cert"/>
	    
	    <!-- 요청구분 (01:휴대폰인증) -->
	    <input type="hidden" name="cert_method" value="01"/>
	    
	    <!-- 웹사이트아이디 -->
	    <input type="hidden" name="web_siteid" value=""/> 
	    
	    <!-- 노출 통신사 default 처리시 아래의 주석을 해제하고 사용하십시요 
	         SKT : SKT , KT : KTF , LGU+ : LGT
	    <input type="hidden" name="fix_commid" value="KTF"/>
	    -->
	    
	    <!-- 사이트코드 -->
	    <input type="hidden" name="site_cd" value="A7XRC" />
	    
	    <!-- Ret_URL : 인증결과 리턴 페이지 (가맹점 URL 로 설정해 주셔야 합니다.) -->
	    <!-- TODO : 운영서버 적용시, 수정할 것  -->
	    <input type="hidden" name="Ret_URL" value="http://localhost:8080/kcpCert/certRequest" />
	    
	    <!-- cert_otp_use 필수 ( 메뉴얼 참고)
	         Y : 실명 확인 + OTP 점유 확인 , N : 실명 확인 only
	    -->
	    <input type="hidden" name="cert_otp_use" value=""/>
	    
	    <!-- cert_enc_use 필수 (고정값 : 메뉴얼 참고) -->
	    <input type="hidden" name="cert_enc_use" value="Y"/>
	
	    <!-- 결과처리  -->
	    <input type="hidden" name="res_cd"       value=""/>
	    <input type="hidden" name="res_msg"      value=""/>
	
	    <!-- up_hash 검증 을 위한 필드 -->
	    <input type="hidden" name="veri_up_hash" value=""/>
	
	    <!-- 본인확인 input 비활성화 -->
	    <input type="hidden" name="cert_able_yn" value="Y"/>
	
	    <!-- web_siteid 검증 을 위한 필드 -->
	    <input type="hidden" name="web_siteid_hashYN" value="Y"/>
	
	    <!-- 가맹점 사용 필드 (인증완료시 리턴)-->
	    <!-- 
	    <input type="hidden" name="param_opt_1"  value="opt1"/> 
	    <input type="hidden" name="param_opt_2"  value="opt2"/> 
	    <input type="hidden" name="param_opt_3"  value="opt3"/>
	     --> 
    </form>
</body>
</html>
