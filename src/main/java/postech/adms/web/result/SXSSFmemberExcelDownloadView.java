package postech.adms.web.result;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class SXSSFmemberExcelDownloadView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fileNm = (String) model.get("fileName");
		response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(fileNm.getBytes("euc-kr"), "8859_1") + ".xlsx\";");
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");

		List<AlumniMember> result = (List<AlumniMember>) model.get("result");
		String filePath = (String) model.get("filePath");

		InputStream is = getClass().getResourceAsStream(filePath);
//		XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(filePath));
		XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(is));
//		XSSFSheet sheet1 = workbook.getSheetAt(0);
//		ExcelUtil excelUtil = new ExcelUtil(work);

//		Workbook workbook = WorkbookFactory.create(new File(filePath));
//		XSSFWorkbook work = new XSSFWorkbook(new File(filePath));
		SXSSFWorkbook wb = new SXSSFWorkbook( workbook,1000);
		Sheet sheet = wb.getSheetAt(0);

		System.out.println("ExcelDownloadView::result count: " + result.size());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		int index = 3;
		for (AlumniMember data : result) {
			Row row = sheet.createRow(index);

			if(index%100 == 0) {
				System.out.println("ExcelDownloadView::Name:" + data.getName() + ", birth:" + data.getBirthdayOfficial() + ", count:" + index);
			}

			// 회원정보
			row.createCell(0).setCellValue(data.getName());
			row.createCell(1).setCellValue(data.getBirthdayOfficial());
			row.createCell(2).setCellValue(data.getBirthdayReal());
			row.createCell(3).setCellValue(data.getBirthdayType() == null ? "":data.getBirthdayType().toString());
			row.createCell(4).setCellValue(data.getEmail());
			row.createCell(5).setCellValue(data.getPhone());
			row.createCell(6).setCellValue(data.getMobile());
			row.createCell(7).setCellValue(data.getNationality());
			row.createCell(8).setCellValue(data.getGender() == null ? "" : data.getGender().toString());
			row.createCell(9).setCellValue(data.getIsAlive()  ? "Y" : "N");
			row.createCell(10).setCellValue(data.getMailingAddress() == null ? "" : data.getMailingAddress().toString()); // 우편물 수령지
			row.createCell(11).setCellValue(data.getCurrentWork() == null ? "" : data.getCurrentWork().toString()); //현재 직장명
			row.createCell(12).setCellValue(data.getCurrentWorkDept() == null ? "" : data.getCurrentWorkDept().toString()); //현재 부서
			row.createCell(13).setCellValue(data.getCurrentJobTitle() == null ? "" : data.getCurrentJobTitle().toString()); //현재 직책

			// 학위
			for (Degree degree : data.getDegrees()) {
				if (degree.getDegreeType().equals(DegreeType.BS)) {
					row.createCell(14).setCellValue(degree.getStudentId() == null ? "" : degree.getStudentId());
					row.createCell(15).setCellValue(degree.getDeptName() == null ? "" : degree.getDeptName());
					row.createCell(16).setCellValue(degree.getEntranceYear() == null ? "" : degree.getEntranceYear());
					row.createCell(17).setCellValue(degree.getGraduationYear() == null ? "" : degree.getGraduationYear());
					row.createCell(18).setCellValue(degree.getSupervisor() == null ? "" : degree.getSupervisor());
					row.createCell(19).setCellValue(degree.getExpectedPath() == null ? "" : degree.getExpectedPath());
					row.createCell(20).setCellValue(degree.getExpectedWork() == null ? "" : degree.getExpectedWork());
				}

				if (degree.getDegreeType().equals(DegreeType.MS)) {
					row.createCell(21).setCellValue(degree.getStudentId() == null ? "" : degree.getStudentId());
					row.createCell(22).setCellValue(degree.getDeptName() == null ? "" : degree.getDeptName());
					row.createCell(23).setCellValue(degree.getEntranceYear() == null ? "" : degree.getEntranceYear());
					row.createCell(24).setCellValue(degree.getGraduationYear() == null ? "" : degree.getGraduationYear());
					row.createCell(25).setCellValue(degree.getSupervisor() == null ? "" : degree.getSupervisor());
					row.createCell(26).setCellValue(degree.getExpectedPath() == null ? "" : degree.getExpectedPath());
					row.createCell(27).setCellValue(degree.getExpectedWork() == null ? "" : degree.getExpectedWork());
				}

				if (degree.getDegreeType().equals(DegreeType.PhD)) {
					row.createCell(28).setCellValue(degree.getStudentId() == null ? "" : degree.getStudentId());
					row.createCell(29).setCellValue(degree.getDeptName() == null ? "" : degree.getDeptName());
					row.createCell(30).setCellValue(degree.getEntranceYear() == null ? "" : degree.getEntranceYear());
					row.createCell(31).setCellValue(degree.getGraduationYear() == null ? "" : degree.getGraduationYear());
					row.createCell(32).setCellValue(degree.getSupervisor() == null ? "" : degree.getSupervisor());
					row.createCell(33).setCellValue(degree.getExpectedPath() == null ? "" : degree.getExpectedPath());
					row.createCell(34).setCellValue(degree.getExpectedWork() == null ? "" : degree.getExpectedWork());
				}

				if (degree.getDegreeType().equals(DegreeType.UNITY)) {
					row.createCell(35).setCellValue(degree.getStudentId() == null ? "" : degree.getStudentId());
					row.createCell(36).setCellValue(degree.getDeptName() == null ? "" : degree.getDeptName());
					row.createCell(37).setCellValue(degree.getEntranceYear() == null ? "" : degree.getEntranceYear());
					row.createCell(38).setCellValue(degree.getGraduationYear() == null ? "" : degree.getGraduationYear());
					row.createCell(39).setCellValue(degree.getSupervisor() == null ? "" : degree.getSupervisor());
					row.createCell(40).setCellValue(degree.getExpectedPath() == null ? "" : degree.getExpectedPath());
					row.createCell(41).setCellValue(degree.getExpectedWork() == null ? "" : degree.getExpectedWork());
				}

				if (degree.getDegreeType().equals(DegreeType.PAMTIP)) {
					row.createCell(42).setCellValue(degree.getStudentId() == null ? "" : degree.getStudentId());
					row.createCell(43).setCellValue(degree.getDeptName() == null ? "" : degree.getDeptName());
					row.createCell(44).setCellValue(degree.getEntranceYear() == null ? "" : degree.getEntranceYear());
					row.createCell(45).setCellValue(degree.getGraduationYear() == null ? "" : degree.getGraduationYear());
				}
			}

			// 주소
			for (Address address : data.getAddresses()) {
				if (address.getAddressType().equalsIgnoreCase("HOME")) {
					row.createCell(46).setCellValue(address.getZipCode() == null ? ""  : address.getZipCode());
					row.createCell(47).setCellValue(address.getAddress1() == null ? "" : address.getAddress1());
					row.createCell(48).setCellValue(address.getAddress2() == null ? "" : address.getAddress2());
					row.createCell(49).setCellValue(address.getAddress3() == null ? "" : address.getAddress3());
					row.createCell(50).setCellValue(address.getAddress4() == null ? "" : address.getAddress4());
				}

				if (address.getAddressType().equalsIgnoreCase("WORK")) {
					row.createCell(51).setCellValue(address.getZipCode() == null ? ""  : address.getZipCode());
					row.createCell(52).setCellValue(address.getAddress1() == null ? "" : address.getAddress1());
					row.createCell(53).setCellValue(address.getAddress2() == null ? "" : address.getAddress2());
					row.createCell(54).setCellValue(address.getAddress3() == null ? "" : address.getAddress3());
					row.createCell(55).setCellValue(address.getAddress4() == null ? "" : address.getAddress4());
				}

			}

			index++;
		}

		wb.write(response.getOutputStream());
	}
}
