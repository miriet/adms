package postech.adms.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import postech.adms.domain.member.AlumniMember;
import postech.adms.dto.member.MemberListDto;
import postech.adms.service.department.DepartmentService;
import postech.adms.service.member.MemberService;
import postech.adms.web.result.memberExcelDownloadView;
import postech.adms.web.security.UserInfo;

@Controller("alumnusMemberController")
@RequestMapping("/alumnusmembers")
public class AlumnusMemberController extends BasicController {
	@Resource(name = "admsMemberService")
	protected MemberService memberService;

	@Resource(name = "departmentService")
	protected DepartmentService departmentService;

	protected String downloadFilePath = "/files/ALUMNI_MEMBERS.xlsx";

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		// 접속자 정보
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("deptList", departmentService.findByIsDel(false));
		model.addAttribute("userInfo", userInfo);

		return "alumnusmembersForm";
	}

	// --------------------------------------------------------
	// 목록.검색
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/find")
	public void find(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.DESC, "auditable.dateCreated"));
		Page<MemberListDto> result = memberService.alumnusFind(param, page);
		setModelWithkendoList(model, result);
	}

	// --------------------------------------------------------
	// 엑셀 다운로드
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/excelDown")
	public ModelAndView excelDown(@RequestParam Map<String, String> param, ModelAndView model) {
		List<AlumniMember> result = memberService.alumnusFindExcel(param);

		model.addObject("result", result);
		model.addObject("fileName", "동창회원목록");
		model.addObject("filePath", downloadFilePath);
		model.setView(new memberExcelDownloadView());

		return model;
	}

	// --------------------------------------------------------
	// 동기 상세조회
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/{id}")
	public String findOne(@PathVariable("id") Long id, ModelMap model) {
		AlumniMember member = memberService.findOne(id);

		model.addAttribute("result", member);
		model.addAttribute("deptList", departmentService.findByIsDel(false));

		return "member/alumnusMemberView";
	}
}
