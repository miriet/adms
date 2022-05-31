package postech.adms.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import postech.adms.domain.user.AdminUser;
import postech.adms.service.user.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component("admsAuthenticationSuccessHandler")
public class AdmsAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Resource
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			                            HttpServletResponse response,
                                        Authentication authentication)
					throws IOException, ServletException {

		SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

        String loginId = request.getParameter("userId");
        AdminUser user = userService.findByLoginId(loginId);

        Long pwdCount = user.getPwdCount() == null ? 0L : user.getPwdCount();
        Long GroupId = user.getAdminGroup().getGroupId(); // 로그인 사용자 그룹ID
        String message = "";

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        Map<String, String> jsonBean = new HashMap<String, String>();

        if (GroupId == null) {
            message = "사용자 그룹정보가 존재하지 않습니다.\n관리자에게 문의하시길 바랍니다.";
        } else if (GroupId == 6) { // 일반동창회원 그룹 일때, 비밀번호 오류 횟수 체크
			if (pwdCount < 5) {
				userService.updatePwdCount(loginId, 0L);
				jsonBean.put("code", "0");
				jsonBean.put("message", "SUCCESS");

				if (savedRequest == null) {
					jsonBean.put("redirectUrl", super.determineTargetUrl(request, response));
				} else {
					//6개월마다 비밀번호 변경작업
					if (timeChangePasswd(user)) {
						jsonBean.put("redirectUrl", savedRequest.getRedirectUrl() + "user/semestralChagePasswd");
					} else {
                        jsonBean.put("redirectUrl", "/myinfo");
                        // jsonBean.put("redirectUrl", savedRequest.getRedirectUrl() + "alumnusmembers");
					}
				}
			} else {
				message = "비밀번호 입력횟수를 초과하셨습니다.(" + pwdCount + "/5)" + "\n관리자에게 문의하시길 바랍니다.";
				jsonBean.put("code", "1");
				jsonBean.put("message", message);
			}
		} else { // 그 외
			jsonBean.put("code", "0");
			jsonBean.put("message", "SUCCESS");

			if (savedRequest == null) {
				jsonBean.put("redirectUrl", super.determineTargetUrl(request, response));
			} else {
				jsonBean.put("redirectUrl", savedRequest.getRedirectUrl());
			}
		}

		final ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(jsonBean));
	}

	//마지막 비밀번호 날짜 비교조회(6개월)
	private boolean timeChangePasswd(AdminUser data) {
		boolean result = false;
		final Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, -6);

		if (today.getTime().compareTo(data.getLastChangePwd()) > 0) {
			result = true;
		}

		return result;
	}
}
