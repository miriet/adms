package postech.adms.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import postech.adms.domain.user.AdminUser;
import postech.adms.service.user.UserService;

@Component("admsAuthenticationFailHandler")
public class AdmsAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler{

	public static final int NOT_FOUND_USER = 2;

	@Resource
	private UserService userService;

	@Autowired
	private ShaPasswordEncoder encoder;

	@Autowired
	private SaltSource saltSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

        String ajaxHeader = request.getHeader("X-Requested-With") == null ? "" : request.getHeader("X-Requested-With");

		if ("XMLHttpRequest".equals(ajaxHeader)) {

			//아이디 존재유무
			String loginId = request.getParameter("userId");
			AdminUser user = userService.findByLoginId(loginId);

			Long pwdCount = null;
			Long GroupId  = null; // 로그인 사용자 그룹ID
			String message = "";

			if (user == null) {
                message = "등록된 아이디가 존재하지 않습니다.";
            } else {
                pwdCount = user.getPwdCount() == null ? 0L : user.getPwdCount();
                GroupId = user.getAdminGroup().getGroupId(); // 로그인 사용자 그룹ID
                if (GroupId == null) {
                    message = "사용자 그룹정보가 존재하지 않습니다.\n관리자에게 문의하시길 바랍니다.";
                } else if (GroupId == 6) { // 일반동창회원 그룹 일때, 비밀번호 오류 횟수 체크
                    //비밀번호 검증
                    if (pwdCount < 5) {
						String password = encoder.encodePassword(request.getParameter("password"), "POSTECH");
						if (!password.matches(user.getPassword())) {
							pwdCount++;
							userService.updatePwdCount(loginId, pwdCount);
							message = "비밀번호를 다시 입력해 주세요.(" + pwdCount + "/5)";
						}
					} else if (pwdCount == 5) {
						message = "비밀번호 입력횟수를 초과하셨습니다.(" + pwdCount + "/5)" + "\n관리자에게 문의하시길 바랍니다.";
					}
				} else {
                    message = "비밀번호를 다시 입력해 주세요.";
				}
			}

			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");

			Map<String, String> jsonBean = new HashMap<String, String>();
			jsonBean.put("code", "1");
			//jsonBean.put("message", exception.getMessage());
			jsonBean.put("message", message);

			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(jsonBean));
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
