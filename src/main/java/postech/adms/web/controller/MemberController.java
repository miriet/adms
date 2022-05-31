package postech.adms.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import postech.adms.domain.department.Department;
import postech.adms.domain.member.AlumniMember;
import postech.adms.dto.member.MemberListDto;
import postech.adms.service.department.DepartmentService;
import postech.adms.service.member.MemberService;
import postech.adms.web.result.SXSSFmemberExcelDownloadView;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by miriet on 2017. 3. 27.
 */
@Controller("admsMemberController")
@RequestMapping("/members")
public class MemberController extends BasicController {

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

		return "member";
	}

	// --------------------------------------------------------
	// 목록.검색
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/find")
	public void find(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.DESC, "auditable.dateCreated"));

		Page<MemberListDto> result = memberService.find(param, page);
		setModelWithkendoList(model, result);
	}

	// --------------------------------------------------------
	// 회원정보(등록 Form)
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/writeForm")
	public String writeForm(@ModelAttribute AlumniMember alumniMember, BindingResult bindingResult, ModelMap model) {
		List<Department> deptList = departmentService.findByIsDel(false);
		model.addAttribute("deptList", deptList);

		return "member/memberWriteForm";
	}

	// --------------------------------------------------------
    // 회원정보(수정 Form)
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/{id}")
	public String findOne(@PathVariable("id") Long id, ModelMap model) {
		AlumniMember member = memberService.findOne(id);

		model.addAttribute("result", member);
		model.addAttribute("deptList", departmentService.findByIsDel(false));

		return "member/memberUpdateForm";
	}

	// --------------------------------------------------------
    // 저장 처리
    // --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/update")
	public void update(@ModelAttribute AlumniMember alumniMember, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {

		}

		if (alumniMember.getId() == null) { // 신규
			try {
				memberService.insert(alumniMember);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // 수정
			try {
				memberService.update(alumniMember);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------
    // 삭제 처리
    // --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/delete/{id}/{info}")
	public void delete(@PathVariable("id") Long id, @PathVariable("info") String info, ModelMap model) {
		// 학위정보 삭제
		if (info.equalsIgnoreCase("degreeInfo")) {
			memberService.deleteDegree(id);
		} else if (info.equalsIgnoreCase("addressInfo")) { // 주소정보 삭제
			memberService.deleteAddress(id);
		}
	}

	// --------------------------------------------------------
	// 엑셀 다운로드
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/excelDown")
	public ModelAndView excelDown(@RequestParam Map<String, String> param, ModelAndView model) {
		List<AlumniMember> result = memberService.find(param);

		model.addObject("result", result);
		model.addObject("fileName", "동창회원목록");
		model.addObject("filePath", downloadFilePath);
		model.setView(new SXSSFmemberExcelDownloadView());
//		model.setView(new memberExcelDownloadView());

		return model;
	}

	/*
    @PreAuthorize("hasRole('PERMISSION_GROUP_VIEW')")
    @RequestMapping("/list")
	public void readUsersWithoutUserRoles(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.DESC, "auditable.dateCreated"));
		Page<MemberListDto> result = memberService.readUsersWithoutUserRoles(Long.parseLong(param.get("siteId")), page);
		setModelWithkendoList(model, result);
	}

    // 수정폼
	@PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
	@RequestMapping("/update/{memberId}")
	public void updateProcess(@PathVariable("memberId") Long memberId,
	                   ModelMap model) {
		AlumniMember alumniMember =  memberService.findOne(memberId);
		memberService.update(alumniMember);
	}
   */

}
