<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script language="javascript">

	function fnCheckValue(){
		if($.trim($("#userId").val()) == ""){
			alert("ID를 입력하세요!");
			$("#userId").focus();
	   		return false;
	    }
		
		if($.trim($("#password").val()) == ""){
			alert("비밀번호를 입력하세요!");
			$("#password").focus();
	   		return false;
	    }
		
		return true;
	}
	
	function fnLogin() {
		if(fnCheckValue()){
			$("#myform").ajaxSubmit({
				url:"./login_post",
				permissionType:'POST',
				success:function(data, statusText, xhr){
					if(data["code"] == "0"){
						$("#myform").attr("target","_self");
						$("#myform").attr("action",data["redirectUrl"]);
						$("#myform").submit();
					}else{
						alert(data["message"]);
					}
				}
			});
		}
	}
	
	function joinForm() {
		$("#myform").attr("action","/join/joinForm");
	    $("#myform").submit();
	}
	
	$(document).ready(function(){
		$("#userId").focus();
		$("#userId").keydown(function(evt){
			if( (evt.keyCode) && (evt.keyCode==13) ) { $("#password").focus(); }
		});
		$("#password").keydown(function(evt){
			if( (evt.keyCode) && (evt.keyCode==13) ) { fnLogin(); }
		});
		
		$("#btnLogin").click(function(){
			fnLogin();
		});
	});

</script>
</head>


<body class="index">
	<form name="myform" id="myform" method="post" >
		<div class="login" style="height:533px;">
			<h1>ADMS</h1>
			<div class="id">
				<label>ADMS ID</label>
				<input permissionType="text" name="userId" id="userId" />
			</div>
			<div class="password">
				<label>PASSWORD</label>
				<input permissionType="password" name="password" id="password" />
				 <label style="display:none;"><input permissionType="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" checked/> 아이디 저장</label>
			</div>		
			<div>
				<img id="btnLogin" alt="로그인" style="cursor: pointer;"/>
				<a href="javascript:void(0)" onclick="javascript:joinForm()"><span id="btnJoin" class="btnLarge blue" style="color:#fff;height:20px;width:229px;margin-top:10px;text-align:center;margin-left:88px;">회원가입</span></a>
                <!-- <a href="javascript:void(0)" onclick="javascript:auth_type_check()"><span id="btnCert" class="btnLarge blue" style="color:#fff;height:20px;width:229px;margin-top:10px;text-align:center;margin-left:88px;">본인인증</span></a> -->
			</div>
		</div>
	</form>
</body>

</html>
