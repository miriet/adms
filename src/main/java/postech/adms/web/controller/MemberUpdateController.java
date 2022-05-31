package postech.adms.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
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
import postech.adms.domain.upload.QAlumniUploadInfo;
import postech.adms.dto.member.MemberListDto;
import postech.adms.dto.upload.UploadDetailListDto;
import postech.adms.dto.upload.UploadListDto;
import postech.adms.service.department.DepartmentService;
import postech.adms.service.member.MemberService;
import postech.adms.service.upload.AlumniUploadService;
import postech.adms.service.upload.MemberUpdateService;
import postech.adms.web.result.memberExcelDownloadView;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by miriet on 2017. 3. 27.
 */
@Controller("admsMemberUpdateController")
@RequestMapping("/update")
public class MemberUpdateController extends BasicController {

	@Resource(name = "admsMemberService")
	protected MemberService memberService;

	@Resource(name = "departmentService")
	protected DepartmentService departmentService;

	@Resource
	private AlumniUploadService alumniUploadService;

	@Resource
	private MemberUpdateService memberUpdateService;

	protected String downloadFilePath = "/files/ALUMNI_MEMBERS.xlsx";

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		// 접속자 정보
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // PageRequest page = new PageRequest(0, 20, new Sort(Sort.Direction.DESC,"UPLOAD_DATE"));
		PageRequest page = new PageRequest(0, 20, new QSort(QAlumniUploadInfo.alumniUploadInfo.uploadDate.desc()));
		Page<UploadListDto> uploadListDtos = alumniUploadService.find(userInfo.getId().intValue(), userInfo.getGroupId(), userInfo.getDeptCode(), page);

		model.addAttribute("deptList",   departmentService.findByIsDel(false));
		model.addAttribute("uploadList", uploadListDtos.getContent());
		model.addAttribute("userInfo",   userInfo);

		return "update";
	}

	/**
	 * 엑셀 파일 회원 목록 리스트
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/findList")
	public void findList(@RequestParam Map<String,String> param, ModelMap model){
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")), new Sort(Sort.Direction.DESC, "name"));

		/*
		Page<UploadDetailListDto> dtos = memberUpdateService.findDetail(param, page);
		Page<UploadDetailListDto> dtos = memberUpdateService.detailFind(Integer.parseInt(param.get("infoId")), param, page);
		Page<UploadDetailListDto> dtos = alumniUploadService.detailFind(Integer.parseInt(param.get("infoId")), page);
		Page<UploadDetailListDto> dtos = alumniUploadService.detailFind(Integer.parseInt((param.get("infoId")==null)?"0":param.get("infoId")), page);

		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();
		for(UploadDetailListDto dto:dtos){
			AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(dto.getName(), dto.getBirthdayOfficial());
			result.add(dto);
			if (alumniMember == null) {
				result.add(new UploadDetailListDto());
			} else{
				result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
			}
		}
		*/

		Map<String, Object> returnValue;
		String p = param.get("viewType");
		switch (p) {
			case "1": 	returnValue = memberUpdateService.findUpdateListByDetail(param, page); break;
			case "2": 	returnValue = memberUpdateService.findUpdateListByMember(param, page); break;
			case "3": 	returnValue = memberUpdateService.findUpdateListByMemberAndUpload(param, page);	break;
			case "4": 	returnValue = memberUpdateService.findUpdateListByMemberMinusUpload(param, page); break;
			case "5": 	returnValue = memberUpdateService.findUpdateListByUploadMinusMember(param, page); break;
			default:  	returnValue = memberUpdateService.findUpdateListByDetail(param, page); break;
		}

		Page<?> dtos = (Page<?>)returnValue.get("dto");

		List<UploadDetailListDto> result = (List<UploadDetailListDto>)returnValue.get("result");

		setModelWithkendoList(model, dtos, result);
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

	/**
	 * 엑셀파일 회원 업로드 승인
	 * @param selectedDetailId
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/addMembers")
	public void addUploadMember(@RequestParam("selectedDetailId") long[] selectedDetailId, ModelMap model){
		alumniUploadService.addUploadMember(selectedDetailId);
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
		if (info.equalsIgnoreCase("degreeInfo")) { // 학위정보 삭제
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

		model.addObject("result",   result);
		model.addObject("fileName", "동창회원목록");
		model.addObject("filePath", downloadFilePath);

		model.setView(new memberExcelDownloadView());

		return model;
	}

}
