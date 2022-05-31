package postech.adms.common.util;

import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.domain.upload.AlumniUploadDetail;
import postech.adms.dto.upload.UploadDetailListDto;

/**
 * Created by miriet on 2017. 5. 20..
 */
public class AlumniInfoCopy {

	public AlumniMember copyAlumniInfo(AlumniMember alumniMember, AlumniUploadDetail alumniUploadDetail) {
		EntityUtil.transformDtoToModel(alumniMember, alumniUploadDetail);

		for (DegreeType degreeType : DegreeType.values()) {
			Degree sourceDegree = alumniUploadDetail.getDegree(degreeType.name());
			alumniMember.addDegree(sourceDegree);
		}
		return alumniMember;
	}

	public static UploadDetailListDto copyAlumniToUploadDetailListDto(AlumniMember alumniMember) {
		UploadDetailListDto dto = new UploadDetailListDto();

		dto.setAlive(alumniMember.getIsAlive());
		dto.setBirthdayOfficial(alumniMember.getBirthdayOfficial());
		dto.setBirthdayReal(alumniMember.getBirthdayReal());
		dto.setBirthdayRealType(alumniMember.getBirthdayType().name());
		dto.setDetailId(alumniMember.getId());
		dto.setEmail(alumniMember.getEmail());
		dto.setGender(alumniMember.getGender() == null ? "" : alumniMember.getGender().toString());
		dto.setMarried(alumniMember.isMarried());
		dto.setMembershipFeeStatus(alumniMember.getMembershipFeeStatus() == null ? "" : alumniMember.getMembershipFeeStatus().toString());
		dto.setMobile(alumniMember.getMobile());
		dto.setName(alumniMember.getName());
		dto.setNationality(alumniMember.getNationality());
		dto.setPhone(alumniMember.getPhone());
		dto.setSourceTable("M");

		for (Degree degree : alumniMember.getDegrees()) {
			if (degree.getDegreeType().name() == "BS") {
				dto.setBsDept(degree.getDeptCode());
				dto.setBsId(degree.getStudentId());
				dto.setBsEntranceYear(degree.getEntranceYear());
				dto.setBsGraduationYear(degree.getGraduationYear());
			}

			if (degree.getDegreeType().name() == "MS") {
				dto.setMsDept(degree.getDeptCode());
				dto.setMsId(degree.getStudentId());
				dto.setMsEntranceYear(degree.getEntranceYear());
				dto.setMsGraduationYear(degree.getGraduationYear());
			}

			if (degree.getDegreeType().name() == "PhD") {
				dto.setPhdDept(degree.getDeptCode());
				dto.setPhdId(degree.getStudentId());
				dto.setPhdEntranceYear(degree.getEntranceYear());
				dto.setPhdGraduationYear(degree.getGraduationYear());
			}

			if (degree.getDegreeType().name() == "UNITY") {
				dto.setUnityDept(degree.getDeptCode());
				dto.setUnityId(degree.getStudentId());
				dto.setUnityEntranceYear(degree.getEntranceYear());
				dto.setUnityGraduationYear(degree.getGraduationYear());
			}

			if (degree.getDegreeType().name() == "PAMTIP") {
				dto.setPamtipDept(degree.getDeptCode());
				dto.setPamtipId(degree.getStudentId());
				dto.setPamtipEntranceYear(degree.getEntranceYear());
				dto.setPamtipGraduationYear(degree.getGraduationYear());
			}
		}

		for (Address address : alumniMember.getAddresses()) {
			if (address.getAddressType() == "HOME") {
				dto.setHomeZipcode(address.getZipCode());
				dto.setHomeAddr1(address.getAddress1());
				dto.setHomeAddr2(address.getAddress2());
			}

			if (address.getAddressType() == "WORK") {
				dto.setWorkZipcode(address.getZipCode());
				dto.setWorkAddr1(address.getAddress1());
				dto.setWorkAddr2(address.getAddress2());
			}

			if (address.getAddressType() == "MAILING") {
				dto.setMailingZipcode(address.getZipCode());
				dto.setMailingAddr1(address.getAddress1());
				dto.setMailingAddr2(address.getAddress2());
			}
		}

		return dto;
	}

	// public static void main(String[] args) {
	// try {
	// ApplicationContext ac = new
	// ClassPathXmlApplicationContext("context.xml");
	// DataSource source = (DataSource) ac.getBean("dataSource");
	// JdbcTemplate jt = new JdbcTemplate(source);
	// jt.batchUpdate(new String[]{"update employee set department='111'",
	// "delete from employee where employid='1'"});
	// } catch (BeansException e) {
	// e.printStackTrace();
	// } catch (DataAccessException e) {
	// e.printStackTrace();
	// }
	// }

}
