<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
        <title>*** KCP Online Payment System [Jsp Version] ***</title>
        <script type="text/javascript">
            window.onload=function() {
                var frm = document.form_auth;
                
                if (frm.req_tx.value == "cert") { // 인증요청 시 호출 함수
                    opener.document.form_auth.veri_up_hash.value = frm.up_hash.value; // up_hash 데이터 검증을 위한 필드

                    // frm.action="https://testcert.kcp.co.kr/kcp_cert/cert_view.jsp";
                    frm.action="https://cert.kcp.co.kr/kcp_cert/cert_view.jsp"; // 운영(Real)
                    frm.submit();
                } else if ( (frm.req_tx.value == "auth" || frm.req_tx.value == "otp_auth")) { // 인증 결과 데이터 리턴 페이지 호출 함수
                    frm.action="/kcpCert/certResponse";
                    frm.submit();
                } else {
                    alert ("req_tx 값을 확인해 주세요");
                }
            }
        </script>
    </head>
    <body oncontextmenu="return false;" ondragstart="return false;" onselectstart="return false;">
        <form name="form_auth" method="post">
            ${sbParam}
        </form>
    </body>
</html>
