package postech.adms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import postech.adms.common.util.StringUtil;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.user.AdminUser;
import postech.adms.service.join.JoinService;
import postech.adms.service.member.MemberService;
import postech.adms.web.controller.form.JoinForm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("admsJoinController")
@RequestMapping("/join")
public class JoinController {

	@Resource(name = "admsJoinService")
	protected JoinService joinService;

	@Resource(name = "admsMemberService")
	protected MemberService memberService;

	// --------------------------------------------------------
	// 회원가입 등록화면
	// --------------------------------------------------------
	@RequestMapping("/joinForm")
	public String joinForm(@ModelAttribute("joinForm") JoinForm joinForm, ModelMap model) {

		return "join/joinForm";
	}

	// --------------------------------------------------------
	// 회원가입 처리
	// --------------------------------------------------------
	@RequestMapping("/insertJoin")
	public void insertJoin(@ModelAttribute("joinForm") JoinForm joinForm, BindingResult bindingResult, ModelMap model,
			HttpSession session, HttpServletRequest request) {
		//AdminUser adminUser = joinService.findByNameAndBirthday(joinForm.getName(), joinForm.getBirthday());
		AdminUser adminUser = joinService.findByDi(joinForm.getDi());

		// AlumniMember alumniMember = memberService.findByNameAndBirthdayReal(joinForm.getName(), joinForm.getBirthday());
		AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(joinForm.getName(), StringUtil.changeDateFormat(joinForm.getBirthday()));

		System.out.println("joinForm.name:"+joinForm.getName()+", joinForm.birthday:"+joinForm.getBirthday());
		System.out.println("alumniMember.name:"+alumniMember.getName());
		if (joinForm.getKcpName().equals(joinForm.getName()) && joinForm.getKcpBirthday().equals(joinForm.getBirthday())) {
			if (adminUser != null) {
				//이미 가입된 회원
				model.addAttribute("code", "-2");
			} else {
				if (alumniMember != null) {
					joinService.insertJoin(joinForm, alumniMember);
					//가입완료
					model.addAttribute("code", "0");
				} else {
					//AlumniMember에 존재하지 않음
					model.addAttribute("code", "-3");
				}
			}
		} else {
			model.addAttribute("code", "-1");
		}
		
	}

	// --------------------------------------------------------
	// 아이디 중복확인
	// --------------------------------------------------------
	@RequestMapping("/checkDupMemberId")
	public void checkDupMemberId(@ModelAttribute("joinForm") JoinForm joinForm, ModelMap model, HttpSession session,
			HttpServletRequest request) {
		if (joinService.validUserId(joinForm.getLoginId()) == false) {
			//아이디는 4자~20자 이하 영문과 숫자 또는 영문+숫자만으로 등록할 수 있습니다.
			model.addAttribute("code", "-1");
		} else {
			AdminUser data = joinService.getDataByUserId(joinForm.getLoginId());
			if (data == null) {
				//사용가능한 아이디 입니다.
				model.addAttribute("code", "0");
			} else {
				//이미 사용중인 아이디 입니다.
				model.addAttribute("code", "-2");
			}
		}
	}

}
