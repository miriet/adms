package postech.adms.web.result;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.util.excel.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class memberExcelDownloadView extends AbstractView {

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
		XSSFWorkbook work = new XSSFWorkbook(OPCPackage.open(is));
		XSSFSheet sheet = work.getSheetAt(0);
		ExcelUtil excelUtil = new ExcelUtil(work);

        System.out.println("ExcelDownloadView::result count: " + result.size());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		int index = 2;
		for (AlumniMember data : result) {
			XSSFRow row = sheet.createRow(index);

			if(index%100 == 0) {
				System.out.println("ExcelDownloadView::Name:" + data.getName() + ", birth:" + data.getBirthdayOfficial() + ", count:" + index);
			}
			if(index%3000 == 0){
				System.gc();
			}
			// 회원정보
			excelUtil.createDefaultCell(row.createCell(0),  data.getName(),             XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(1),  data.getBirthdayOfficial(), XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(2),  data.getBirthdayReal(),     XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(3),  data.getBirthdayType() == null ? "":data.getBirthdayType().toString(),     XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(4),  data.getEmail(),            XSSFCellStyle.ALIGN_LEFT);
			excelUtil.createDefaultCell(row.createCell(5),  data.getPhone(),            XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(6),  data.getMobile(),           XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(7),  data.getNationality(),      XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(8),  data.getGender() == null ? ""              : data.getGender().toString(),              XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(9),  data.getIsAlive()  ? "Y" : "N", XSSFCellStyle.ALIGN_CENTER);
			excelUtil.createDefaultCell(row.createCell(10), data.getMailingAddress() == null ? ""            : data.getMailingAddress().toString(),            XSSFCellStyle.ALIGN_CENTER); // 우편물 수령지
			excelUtil.createDefaultCell(row.createCell(11), data.getCurrentWork() == null ? ""         : data.getCurrentWork().toString(),         XSSFCellStyle.ALIGN_CENTER); //현재 직장명
			excelUtil.createDefaultCell(row.createCell(12), data.getCurrentWorkDept() == null ? ""          : data.getCurrentWorkDept().toString(),          XSSFCellStyle.ALIGN_CENTER); //현재 부서
			excelUtil.createDefaultCell(row.createCell(13), data.getCurrentJobTitle() == null ? ""            : data.getCurrentJobTitle().toString(),            XSSFCellStyle.ALIGN_CENTER); //현재 직책


			for (int idx=14; idx<=55; idx++) {
				excelUtil.createDefaultCell(row.createCell(idx), "", XSSFCellStyle.ALIGN_CENTER);
			}

			// 학위
			for (Degree degree : data.getDegrees()) {
				if (degree.getDegreeType().equals(DegreeType.BS)) {
					excelUtil.createDefaultCell(row.createCell(14), degree.getStudentId() == null ? ""      : degree.getStudentId(),      XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(15), degree.getDeptName() == null ? ""       : degree.getDeptName(),       XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(16), degree.getEntranceYear() == null ? ""   : degree.getEntranceYear(),   XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(17), degree.getGraduationYear() == null ? "" : degree.getGraduationYear(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(18), degree.getSupervisor() == null ? "" : degree.getSupervisor(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(19), degree.getExpectedPath() == null ? ""   : degree.getExpectedPath(),   XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(20), degree.getExpectedWork() == null ? ""   : degree.getExpectedWork(),   XSSFCellStyle.ALIGN_LEFT);
				}

				if (degree.getDegreeType().equals(DegreeType.MS)) {
					excelUtil.createDefaultCell(row.createCell(21), degree.getStudentId() == null ? ""      : degree.getStudentId(),      XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(22), degree.getDeptName() == null ? ""       : degree.getDeptName(),       XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(23), degree.getEntranceYear() == null ? ""   : degree.getEntranceYear(),   XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(24), degree.getGraduationYear() == null ? "" : degree.getGraduationYear(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(25), degree.getSupervisor() == null ? "" : degree.getSupervisor(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(26), degree.getExpectedPath() == null ? ""   : degree.getExpectedPath(),   XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(27), degree.getExpectedWork() == null ? ""   : degree.getExpectedWork(),   XSSFCellStyle.ALIGN_LEFT);
				}

				if (degree.getDegreeType().equals(DegreeType.PhD)) {
					excelUtil.createDefaultCell(row.createCell(28), degree.getStudentId() == null ? ""      : degree.getStudentId(),      XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(29), degree.getDeptName() == null ? ""       : degree.getDeptName(),       XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(30), degree.getEntranceYear() == null ? ""   : degree.getEntranceYear(),   XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(31), degree.getGraduationYear() == null ? "" : degree.getGraduationYear(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(32), degree.getSupervisor() == null ? "" : degree.getSupervisor(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(33), degree.getExpectedPath() == null ? ""   : degree.getExpectedPath(),   XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(34), degree.getExpectedWork() == null ? ""   : degree.getExpectedWork(),   XSSFCellStyle.ALIGN_LEFT);
				}

				if (degree.getDegreeType().equals(DegreeType.UNITY)) {
					excelUtil.createDefaultCell(row.createCell(35), degree.getStudentId() == null ? ""      : degree.getStudentId(),      XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(36), degree.getDeptName() == null ? ""       : degree.getDeptName(),       XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(37), degree.getEntranceYear() == null ? ""   : degree.getEntranceYear(),   XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(38), degree.getGraduationYear() == null ? "" : degree.getGraduationYear(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(39), degree.getSupervisor() == null ? "" : degree.getSupervisor(), XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(40), degree.getExpectedPath() == null ? ""   : degree.getExpectedPath(),   XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(41), degree.getExpectedWork() == null ? ""   : degree.getExpectedWork(),   XSSFCellStyle.ALIGN_LEFT);
				}

				if (degree.getDegreeType().equals(DegreeType.PAMTIP)) {
					excelUtil.createDefaultCell(row.createCell(42), degree.getStudentId() == null ? ""      : degree.getStudentId(),      XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(43), degree.getDeptName() == null ? ""       : degree.getDeptName(),       XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(44), degree.getEntranceYear() == null ? ""   : degree.getEntranceYear(),   XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(45), degree.getGraduationYear() == null ? "" : degree.getGraduationYear(), XSSFCellStyle.ALIGN_CENTER);
				}
			}

			// 주소
			for (Address address : data.getAddresses()) {
				if (address.getAddressType().equalsIgnoreCase("HOME")) {
					excelUtil.createDefaultCell(row.createCell(46), address.getZipCode() == null ? ""  : address.getZipCode(),  XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(47), address.getAddress1() == null ? "" : address.getAddress1(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(48), address.getAddress2() == null ? "" : address.getAddress2(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(49), address.getAddress3() == null ? "" : address.getAddress3(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(50), address.getAddress4() == null ? "" : address.getAddress4(), XSSFCellStyle.ALIGN_LEFT);
				}

				if (address.getAddressType().equalsIgnoreCase("WORK")) {
					excelUtil.createDefaultCell(row.createCell(51), address.getZipCode() == null ? ""  : address.getZipCode(),  XSSFCellStyle.ALIGN_CENTER);
					excelUtil.createDefaultCell(row.createCell(52), address.getAddress1() == null ? "" : address.getAddress1(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(53), address.getAddress2() == null ? "" : address.getAddress2(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(54), address.getAddress3() == null ? "" : address.getAddress3(), XSSFCellStyle.ALIGN_LEFT);
					excelUtil.createDefaultCell(row.createCell(55), address.getAddress4() == null ? "" : address.getAddress4(), XSSFCellStyle.ALIGN_LEFT);
				}

//				if (address.getAddressType().equalsIgnoreCase("MAILING")) {
//					excelUtil.createDefaultCell(row.createCell(56), address.getZipCode() == null ? ""  : address.getZipCode(),  XSSFCellStyle.ALIGN_CENTER);
//					excelUtil.createDefaultCell(row.createCell(57), address.getAddress1() == null ? "" : address.getAddress1(), XSSFCellStyle.ALIGN_LEFT);
//					excelUtil.createDefaultCell(row.createCell(58), address.getAddress2() == null ? "" : address.getAddress2(), XSSFCellStyle.ALIGN_LEFT);
//					excelUtil.createDefaultCell(row.createCell(59), address.getAddress3() == null ? "" : address.getAddress3(), XSSFCellStyle.ALIGN_LEFT);
//					excelUtil.createDefaultCell(row.createCell(60), address.getAddress4() == null ? "" : address.getAddress4(), XSSFCellStyle.ALIGN_LEFT);
//				}
			}

			index++;
		}

		work.write(response.getOutputStream());
	}
}
