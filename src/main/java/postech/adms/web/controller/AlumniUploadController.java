package postech.adms.web.controller;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import postech.adms.domain.upload.QAlumniUploadInfo;
import postech.adms.dto.upload.UploadDetailListDto;
import postech.adms.dto.upload.UploadListDto;
import postech.adms.service.member.MemberService;
import postech.adms.service.upload.AlumniUploadService;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by miriet on 2017. 5. 6..
 */
@Controller("admsAlumniUploadController")
@RequestMapping("/upload")
public class AlumniUploadController extends BasicController {

	@Resource
	private AlumniUploadService alumniUploadService;

	@Resource
	private MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		return "upload";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/excelDownload")
	public void excelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = "ADMS_UPLOAD";
		response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(fileName.getBytes("euc-kr"), "8859_1") + ".xlsx\";");
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		String filePath = "/files/ADMS_UPLOAD.xlsx";
		InputStream is = getClass().getResourceAsStream(filePath);
		XSSFWorkbook work = new XSSFWorkbook(OPCPackage.open(is));

		work.write(response.getOutputStream());
	}

	/**
	 * 엑셀 업로드 리스트
	 *
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/list")
	public void find(@RequestParam Map<String, String> param, ModelMap model) {
		/*
		 * Pageable page = new
		 * QPageRequest(getCurrentPage(param.get("page")),Integer.parseInt(param
		 * .get("rows")),new
		 * QSort(QAlumniUploadInfo.alumniUploadInfo.infoId.desc()));
		 */
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new QSort(QAlumniUploadInfo.alumniUploadInfo.uploadDate.desc()));
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Page<UploadListDto> result =
		// alumniUploadService.find(userInfo.getId().intValue(),page);
		Page<UploadListDto> result = alumniUploadService.find(userInfo.getId().intValue(), userInfo.getGroupId(), userInfo.getDeptCode(), page);

		setModelWithkendoList(model, result);
	}

	/**
	 * 엑셀 파일 회원 목록 페이지 이동
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/{infoId}")
	public String infoDetailList(@PathVariable("infoId") int infoId, ModelMap model) {
		model.addAttribute("infoId", infoId);
		return "upload/uploadDetailList";
	}

	/**
	 * 엑셀 파일 회원 목록 리스트
	 *
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/findList")
	public void findList(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")));
		Page<UploadDetailListDto> result = alumniUploadService.detailFind(Integer.parseInt(param.get("infoId")), page);

		setModelWithkendoList(model, result);
	}

	/**
	 * 엑셀파일 회원 업로드 승인
	 *
	 * @param selectedDetailId
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/addMember")
	public void addUploadMember(@RequestParam("selectedDetailId") long[] selectedDetailId, ModelMap model) {
		alumniUploadService.addUploadMember(selectedDetailId);
	}

	@RequestMapping("/addMembers")
	public void addUploadMembers(@RequestParam("infoId") long infoId, ModelMap model) {
		alumniUploadService.addUploadMembers(infoId);
	}

	/**
	 * 엑셀 업로드 파일 삭제
	 *
	 * @param infoId
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/delete/{infoId}")
	public void deleteUploadfile(@PathVariable("infoId") Long infoId, ModelMap model) {
		alumniUploadService.deleteUploadFile(infoId);
	}

	/**
	 * 엑셀파일 업로드
	 *
	 * @param title
	 * @param model
	 * @param request
	 * @param excelFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/insert")
	public void insert(@RequestParam(value = "title") String title, ModelMap model, HttpServletRequest request,
			@RequestParam(value = "excelFile") MultipartFile excelFile)
			throws FileNotFoundException, IOException, ParseException {

		// 엑셀 업로드
		alumniUploadService.uploadExcel(title, excelFile);
	}
}
