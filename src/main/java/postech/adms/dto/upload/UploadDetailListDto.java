package postech.adms.dto.upload;

import com.mysema.query.annotations.QueryProjection;
import postech.adms.domain.upload.AlumniUploadDetail;

import java.io.Serializable;
import java.text.ParseException;

public class UploadDetailListDto implements Serializable {
	
	private static final long serialVersionUID = -8780498400106290996L;
	
	private Long detailId = -1L;		// sourceTable이 M인 경우에는 member.id 가 들어감
	private String sourceTable = "U";	// M: Member, U: Upload
	private String name;
	private String birthdayOfficial;
	private	String birthdayReal;
	private String birthdayRealType;
	private String email;
	private String phone;
	private String mobile;
	private String nationality;
	private String gender;
	private boolean isAlive;
	private boolean isMarried;
	private String  membershipFeeStatus;
	private String mailingAddress;
	private String currentWork;
	private String currentWorkDept;
	private String currentJobTitle;
	private String bsId;
	private String bsDept;
	private String bsEntranceYear;
	private String bsGraduationYear;
	private String bsSupervisor;
	private String bsExpectedPath;
	private String bsExpectedWork;
	private String msId;
    private String msDept;
    private String msEntranceYear;
    private String msGraduationYear;
	private String msSupervisor;
	private String msExpectedPath;
	private String msExpectedWork;
    private String phdId;
    private String phdDept;
    private String phdEntranceYear;
    private String phdGraduationYear;
	private String phdSupervisor;
	private String phdExpectedPath;
	private String phdExpectedWork;
    private String unityId;
    private String unityDept;
    private String unityEntranceYear;
    private String unityGraduationYear;
	private String unitySupervisor;
	private String unityExpectedPath;
	private String unityExpectedWork;
    private String pamtipId;
    private String pamtipDept;
    private String pamtipEntranceYear;
    private String pamtipGraduationYear;
    private String homeZipcode;
    private String homeAddr1;
    private String homeAddr2;
	private String homeAddr3;
	private String homeAddr4;
    private String workZipcode;
	private String workAddr1;
	private String workAddr2;
	private String workAddr3;
	private String workAddr4;
    private String mailingZipcode;
    private String mailingAddr1;
    private String mailingAddr2;
	
	@QueryProjection
	public UploadDetailListDto(AlumniUploadDetail alumniUploadDetail) throws ParseException{
		this.detailId = alumniUploadDetail.getDetailId();
		this.sourceTable = "U";
		this.name = alumniUploadDetail.getName();
		this.birthdayOfficial = alumniUploadDetail.getBirthdayOfficial();
		this.birthdayReal = alumniUploadDetail.getBirthdayReal();
		this.birthdayRealType = alumniUploadDetail.getBirthdayRealType();
		this.email = alumniUploadDetail.getEmail();
		this.phone = alumniUploadDetail.getPhone();
		this.mobile = alumniUploadDetail.getMobile();
		this.nationality = alumniUploadDetail.getNationality();
		this.gender = alumniUploadDetail.getGender();
		this.isAlive = alumniUploadDetail.getIsAlive();
		this.isMarried = alumniUploadDetail.getIsMarried();
		this.membershipFeeStatus = alumniUploadDetail.getMembershipFeeStatus();
		this.mailingAddress = alumniUploadDetail.getMailingAddress();
		this.currentWork = alumniUploadDetail.getCurrentWork();
		this.currentWorkDept = alumniUploadDetail.getCurrentWorkDept();
		this.currentJobTitle = alumniUploadDetail.getCurrentJobTitle();
		this.bsId = alumniUploadDetail.getBsId();
		this.bsDept = alumniUploadDetail.getBsDept();
		this.bsEntranceYear = alumniUploadDetail.getBsEntranceYear();
		this.bsGraduationYear = alumniUploadDetail.getBsGraduationYear();
		this.bsSupervisor = alumniUploadDetail.getBsSupervisor();
		this.bsExpectedPath = alumniUploadDetail.getBsExpectedPath();
		this.bsExpectedWork = alumniUploadDetail.getBsExpectedWork();
		this.msId = alumniUploadDetail.getMsId();
		this.msDept = alumniUploadDetail.getMsDept();
		this.msEntranceYear = alumniUploadDetail.getMsEntranceYear();
		this.msGraduationYear = alumniUploadDetail.getMsGraduationYear();
		this.msSupervisor = alumniUploadDetail.getMsSupervisor();
		this.msExpectedPath = alumniUploadDetail.getMsExpectedPath();
		this.msExpectedWork = alumniUploadDetail.getMsExpectedWork();
		this.phdId = alumniUploadDetail.getPhdId();
		this.phdDept = alumniUploadDetail.getPhdDept();
		this.phdEntranceYear = alumniUploadDetail.getPhdEntranceYear();
		this.phdGraduationYear = alumniUploadDetail.getPhdGraduationYear();
		this.phdSupervisor = alumniUploadDetail.getPhdSupervisor();
		this.phdExpectedPath = alumniUploadDetail.getPhdExpectedPath();
		this.phdExpectedWork = alumniUploadDetail.getPhdExpectedWork();
		this.unityId = alumniUploadDetail.getUnityId();
		this.unityDept = alumniUploadDetail.getUnityDept();
		this.unityEntranceYear = alumniUploadDetail.getUnityEntranceYear();
		this.unityGraduationYear = alumniUploadDetail.getUnityGraduationYear();
		this.unitySupervisor = alumniUploadDetail.getUnitySupervisor();
		this.unityExpectedPath = alumniUploadDetail.getUnityExpectedPath();
		this.unityExpectedWork = alumniUploadDetail.getUnityExpectedWork();
		this.pamtipId = alumniUploadDetail.getPamtipId();
		this.pamtipDept = alumniUploadDetail.getPamtipDept();
		this.pamtipEntranceYear = alumniUploadDetail.getPamtipEntranceYear();
		this.pamtipGraduationYear = alumniUploadDetail.getPamtipGraduationYear();
		this.homeZipcode = alumniUploadDetail.getHomeZipcode();
		this.homeAddr1 = alumniUploadDetail.getHomeAddr1();
		this.homeAddr2 = alumniUploadDetail.getHomeAddr2();
		this.homeAddr3 = alumniUploadDetail.getHomeAddr3();
		this.homeAddr4 = alumniUploadDetail.getHomeAddr4();
		this.workZipcode = alumniUploadDetail.getWorkZipcode();
		this.workAddr1 = alumniUploadDetail.getWorkAddr1();
		this.workAddr2 = alumniUploadDetail.getWorkAddr2();
		this.workAddr3 = alumniUploadDetail.getWorkAddr3();
		this.workAddr4 = alumniUploadDetail.getWorkAddr4();
		this.mailingZipcode = alumniUploadDetail.getMailingZipcode();
		this.mailingAddr1 = alumniUploadDetail.getMailingAddr1();
		this.mailingAddr2 = alumniUploadDetail.getMailingAddr2();
	}

	public UploadDetailListDto() {
		super();
	}

	public String getBirthdayReal() {
		return birthdayReal;
	}

	public void setBirthdayReal(String birthdayReal) {
		this.birthdayReal = birthdayReal;
	}

	public String getBirthdayRealType() {
		return birthdayRealType;
	}

	public void setBirthdayRealType(String birthdayRealType) {
		this.birthdayRealType = birthdayRealType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isMarried() {
		return isMarried;
	}

	public void setMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}

	public String getMembershipFeeStatus() {
		return membershipFeeStatus;
	}

	public void setMembershipFeeStatus(String membershipFeeStatus) {
		this.membershipFeeStatus = membershipFeeStatus;
	}

	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public String getBsDept() {
		return bsDept;
	}

	public void setBsDept(String bsDept) {
		this.bsDept = bsDept;
	}

	public String getBsEntranceYear() {
		return bsEntranceYear;
	}

	public void setBsEntranceYear(String bsEntranceYear) {
		this.bsEntranceYear = bsEntranceYear;
	}

	public String getBsGraduationYear() {
		return bsGraduationYear;
	}

	public void setBsGraduationYear(String bsGraduationYear) {
		this.bsGraduationYear = bsGraduationYear;
	}

	public String getMsId() {
		return msId;
	}

	public void setMsId(String msId) {
		this.msId = msId;
	}

	public String getMsDept() {
		return msDept;
	}

	public void setMsDept(String msDept) {
		this.msDept = msDept;
	}

	public String getMsEntranceYear() {
		return msEntranceYear;
	}

	public void setMsEntranceYear(String msEntranceYear) {
		this.msEntranceYear = msEntranceYear;
	}

	public String getMsGraduationYear() {
		return msGraduationYear;
	}

	public void setMsGraduationYear(String msGraduationYear) {
		this.msGraduationYear = msGraduationYear;
	}

	public String getPhdId() {
		return phdId;
	}

	public void setPhdId(String phdId) {
		this.phdId = phdId;
	}

	public String getPhdDept() {
		return phdDept;
	}

	public void setPhdDept(String phdDept) {
		this.phdDept = phdDept;
	}

	public String getPhdEntranceYear() {
		return phdEntranceYear;
	}

	public void setPhdEntranceYear(String phdEntranceYear) {
		this.phdEntranceYear = phdEntranceYear;
	}

	public String getPhdGraduationYear() {
		return phdGraduationYear;
	}

	public void setPhdGraduationYear(String phdGraduationYear) {
		this.phdGraduationYear = phdGraduationYear;
	}

	public String getUnityId() {
		return unityId;
	}

	public void setUnityId(String unityId) {
		this.unityId = unityId;
	}

	public String getUnityDept() {
		return unityDept;
	}

	public void setUnityDept(String unityDept) {
		this.unityDept = unityDept;
	}

	public String getUnityEntranceYear() {
		return unityEntranceYear;
	}

	public void setUnityEntranceYear(String unityEntranceYear) {
		this.unityEntranceYear = unityEntranceYear;
	}

	public String getUnityGraduationYear() {
		return unityGraduationYear;
	}

	public void setUnityGraduationYear(String unityGraduationYear) {
		this.unityGraduationYear = unityGraduationYear;
	}

	public String getPamtipId() {
		return pamtipId;
	}

	public void setPamtipId(String pamtipId) {
		this.pamtipId = pamtipId;
	}

	public String getPamtipDept() {
		return pamtipDept;
	}

	public void setPamtipDept(String pamtipDept) {
		this.pamtipDept = pamtipDept;
	}

	public String getPamtipEntranceYear() {
		return pamtipEntranceYear;
	}

	public void setPamtipEntranceYear(String pamtipEntranceYear) {
		this.pamtipEntranceYear = pamtipEntranceYear;
	}

	public String getPamtipGraduationYear() {
		return pamtipGraduationYear;
	}

	public void setPamtipGraduationYear(String pamtipGraduationYear) {
		this.pamtipGraduationYear = pamtipGraduationYear;
	}

	public String getHomeZipcode() {
		return homeZipcode;
	}

	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	public String getHomeAddr1() {
		return homeAddr1;
	}

	public void setHomeAddr1(String homeAddr1) {
		this.homeAddr1 = homeAddr1;
	}

	public String getHomeAddr2() {
		return homeAddr2;
	}

	public void setHomeAddr2(String homeAddr2) {
		this.homeAddr2 = homeAddr2;
	}

	public String getWorkZipcode() {
		return workZipcode;
	}

	public void setWorkZipcode(String workZipcode) {
		this.workZipcode = workZipcode;
	}

	public String getWorkAddr1() {
		return workAddr1;
	}

	public void setWorkAddr1(String workAddr1) {
		this.workAddr1 = workAddr1;
	}

	public String getWorkAddr2() {
		return workAddr2;
	}

	public void setWorkAddr2(String workAddr2) {
		this.workAddr2 = workAddr2;
	}

	public String getMailingZipcode() {
		return mailingZipcode;
	}

	public void setMailingZipcode(String mailingZipcode) {
		this.mailingZipcode = mailingZipcode;
	}

	public String getMailingAddr1() {
		return mailingAddr1;
	}

	public void setMailingAddr1(String mailingAddr1) {
		this.mailingAddr1 = mailingAddr1;
	}

	public String getMailingAddr2() {
		return mailingAddr2;
	}

	public void setMailingAddr2(String mailingAddr2) {
		this.mailingAddr2 = mailingAddr2;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdayOfficial() {
		return birthdayOfficial;
	}

	public void setBirthdayOfficial(String birthdayOfficial) {
		this.birthdayOfficial = birthdayOfficial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getCurrentWork() {
		return currentWork;
	}

	public void setCurrentWork(String currentWork) {
		this.currentWork = currentWork;
	}

	public String getCurrentWorkDept() {
		return currentWorkDept;
	}

	public void setCurrentWorkDept(String currentWorkDept) {
		this.currentWorkDept = currentWorkDept;
	}

	public String getCurrentJobTitle() {
		return currentJobTitle;
	}

	public void setCurrentJobTitle(String currentJobTitle) {
		this.currentJobTitle = currentJobTitle;
	}

	public String getBsSupervisor() {
		return bsSupervisor;
	}

	public void setBsSupervisor(String bsSupervisor) {
		this.bsSupervisor = bsSupervisor;
	}

	public String getBsExpectedPath() {
		return bsExpectedPath;
	}

	public void setBsExpectedPath(String bsExpectedPath) {
		this.bsExpectedPath = bsExpectedPath;
	}

	public String getBsExpectedWork() {
		return bsExpectedWork;
	}

	public void setBsExpectedWork(String bsExpectedWork) {
		this.bsExpectedWork = bsExpectedWork;
	}

	public String getMsSupervisor() {
		return msSupervisor;
	}

	public void setMsSupervisor(String msSupervisor) {
		this.msSupervisor = msSupervisor;
	}

	public String getMsExpectedPath() {
		return msExpectedPath;
	}

	public void setMsExpectedPath(String msExpectedPath) {
		this.msExpectedPath = msExpectedPath;
	}

	public String getMsExpectedWork() {
		return msExpectedWork;
	}

	public void setMsExpectedWork(String msExpectedWork) {
		this.msExpectedWork = msExpectedWork;
	}

	public String getPhdSupervisor() {
		return phdSupervisor;
	}

	public void setPhdSupervisor(String phdSupervisor) {
		this.phdSupervisor = phdSupervisor;
	}

	public String getPhdExpectedPath() {
		return phdExpectedPath;
	}

	public void setPhdExpectedPath(String phdExpectedPath) {
		this.phdExpectedPath = phdExpectedPath;
	}

	public String getPhdExpectedWork() {
		return phdExpectedWork;
	}

	public void setPhdExpectedWork(String phdExpectedWork) {
		this.phdExpectedWork = phdExpectedWork;
	}

	public String getUnitySupervisor() {
		return unitySupervisor;
	}

	public void setUnitySupervisor(String unitySupervisor) {
		this.unitySupervisor = unitySupervisor;
	}

	public String getUnityExpectedPath() {
		return unityExpectedPath;
	}

	public void setUnityExpectedPath(String unityExpectedPath) {
		this.unityExpectedPath = unityExpectedPath;
	}

	public String getUnityExpectedWork() {
		return unityExpectedWork;
	}

	public void setUnityExpectedWork(String unityExpectedWork) {
		this.unityExpectedWork = unityExpectedWork;
	}

	public String getHomeAddr3() {
		return homeAddr3;
	}

	public void setHomeAddr3(String homeAddr3) {
		this.homeAddr3 = homeAddr3;
	}

	public String getHomeAddr4() {
		return homeAddr4;
	}

	public void setHomeAddr4(String homeAddr4) {
		this.homeAddr4 = homeAddr4;
	}

	public String getWorkAddr3() {
		return workAddr3;
	}

	public void setWorkAddr3(String workAddr3) {
		this.workAddr3 = workAddr3;
	}

	public String getWorkAddr4() {
		return workAddr4;
	}

	public void setWorkAddr4(String workAddr4) {
		this.workAddr4 = workAddr4;
	}
}
