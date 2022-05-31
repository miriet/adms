package postech.adms.domain.upload;

import com.opencsv.bean.CsvBindByName;
import postech.adms.common.persistence.FieldCopy;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.member.Degree;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by miriet on 2017. 5. 6..
 */
@Entity
@Table(name = "MEMBER_UPLOAD_DETAIL")
public class AlumniUploadDetail implements Serializable {

    private static final long serialVersionUID = 3488488249321233238L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long detailId;

    /*@Column(name = "UPLOAD_INFO_ID", nullable=false)
    private int uploadInfoId;*/
    @ManyToOne
    @JoinColumn(name = "UPLOAD_INFO_ID")
    protected AlumniUploadInfo alumniUploadInfo;
    
    @Column(name = "MEMBER_ID", nullable=true)
    private int memberId;
    /*@OneToOne
    protected AlumniMember alumniMember;*/

    @CsvBindByName
    @Column(name = "NAME", nullable=true)
    @FieldCopy(isCopy = true)
    private String name;

    @CsvBindByName
    @Column(name = "BIRTHDAY", nullable=true)
    @FieldCopy(isCopy = true)
    private String birthdayOfficial;

    @CsvBindByName
    @Column(name = "BIRTHDAY_REAL", nullable=true)
    @FieldCopy(isCopy = true)
    private String birthdayReal;

    @CsvBindByName
    @Column(name = "BIRTHDAY_REAL_TYPE", nullable=true)
    @FieldCopy(isCopy = true)
//    @Enumerated(EnumType.STRING)
    private String  birthdayRealType;
//    private CalType birthdayRealType;

    @CsvBindByName
    @Column(name = "EMAIL", nullable=true)
    @FieldCopy(isCopy = true)
    private String email;

    @CsvBindByName
    @Column(name = "PHONE", nullable=true)
    @FieldCopy(isCopy = true)
    private String phone;

    @CsvBindByName
    @Column(name = "MOBILE", nullable=true)
    @FieldCopy(isCopy = true)
    private String mobile;

    @CsvBindByName
    @Column(name = "NATIONALITY", nullable=true)
    @FieldCopy(isCopy = true)
    private String nationality;

    @CsvBindByName
    @Column(name = "GENDER", nullable=true)
    @FieldCopy(isCopy = true)
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
    private String  gender;

    /* 생사여부 */
    @CsvBindByName
    @Column(name = "ALIVE_YN", nullable=true)
    @FieldCopy(isCopy = true)
    private boolean isAlive = true;

    @CsvBindByName
    @Column(name = "MARRIED_YN", nullable=true)
    @FieldCopy(isCopy = true)
    private boolean isMarried;

    @CsvBindByName
    @Column(name = "ALUMNI_FEE", nullable=true)
    @FieldCopy(isCopy = true)
//    @Enumerated(EnumType.STRING)
//    private MembershipFeeStatus membershipFeeStatus;
    private String  membershipFeeStatus;

    @CsvBindByName
    @Column(name = "FEE_PAYMENT_DATE", nullable=true)					//??
    @FieldCopy(isCopy = true)
    private String feePaymentDate;

    @CsvBindByName
    @Column(name = "MAILING_ADDRESS", nullable=true)					// 우편물 수령지
    @FieldCopy(isCopy = true)
    private String mailingAddress;

    @CsvBindByName
    @Column(name = "CURRENT_WORK", nullable=true)					// 현재직장
    @FieldCopy(isCopy = true)
    private String currentWork;

    @CsvBindByName
    @Column(name = "CURRENT_WORK_DEPT", nullable=true)					// 현재부서
    @FieldCopy(isCopy = true)
    private String currentWorkDept;

    @CsvBindByName
    @Column(name = "CURRENT_JOB_TITLE", nullable=true)					// 현재직책
    @FieldCopy(isCopy = true)
    private String currentJobTitle;



    @Column(name = "REVIEW_YN", nullable=true)							//??
    @FieldCopy(isCopy = true)
    private boolean isReviewed;

    @Column(name = "REVIEW_DATE", nullable=true)						//??
    @FieldCopy(isCopy = true)
    private Date reviewDate;


    @CsvBindByName
    @Column(name = "BS_ID", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsId;

    @CsvBindByName
    @Column(name = "BS_DEPT", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsDept;

    @CsvBindByName
    @Column(name = "BS_ENTRANCE_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsEntranceYear;

    @CsvBindByName
    @Column(name = "BS_GRADUATION_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsGraduationYear;

    @CsvBindByName
    @Column(name = "BS_SUPERVISOR", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsSupervisor;

    @CsvBindByName
    @Column(name = "BS_EXPECTED_PATH", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsExpectedPath;

    @CsvBindByName
    @Column(name = "BS_EXPECTED_WORK", nullable=true)
    @FieldCopy(isCopy = true)
    private String bsExpectedWork;

    @CsvBindByName
    @Column(name = "MS_ID", nullable=true)
    @FieldCopy(isCopy = true)
    private String msId;

    @CsvBindByName
    @Column(name = "MS_DEPT", nullable=true)
    @FieldCopy(isCopy = true)
    private String msDept;

    @CsvBindByName
    @Column(name = "MS_ENTRANCE_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String msEntranceYear;

    @CsvBindByName
    @Column(name = "MS_GRADUATION_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String msGraduationYear;

    @CsvBindByName
    @Column(name = "MS_SUPERVISOR", nullable=true)
    @FieldCopy(isCopy = true)
    private String msSupervisor;

    @CsvBindByName
    @Column(name = "MS_EXPECTED_PATH", nullable=true)
    @FieldCopy(isCopy = true)
    private String msExpectedPath;

    @CsvBindByName
    @Column(name = "MS_EXPECTED_WORK", nullable=true)
    @FieldCopy(isCopy = true)
    private String msExpectedWork;

    @CsvBindByName
    @Column(name = "PHD_ID", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdId;

    @CsvBindByName
    @Column(name = "PHD_DEPT", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdDept;

    @CsvBindByName
    @Column(name = "PHD_ENTRANCE_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdEntranceYear;

    @CsvBindByName
    @Column(name = "PHD_GRADUATION_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdGraduationYear;

    @CsvBindByName
    @Column(name = "PHD_SUPERVISOR", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdSupervisor;

    @CsvBindByName
    @Column(name = "PHD_EXPECTED_PATH", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdExpectedPath;

    @CsvBindByName
    @Column(name = "PHD_EXPECTED_WORK", nullable=true)
    @FieldCopy(isCopy = true)
    private String phdExpectedWork;

    @CsvBindByName
    @Column(name = "UNITY_ID", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityId;

    @CsvBindByName
    @Column(name = "UNITY_DEPT", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityDept;

    @CsvBindByName
    @Column(name = "UNITY_ENTRANCE_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityEntranceYear;

    @CsvBindByName
    @Column(name = "UNITY_GRADUATION_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityGraduationYear;

    @CsvBindByName
    @Column(name = "UNITY_SUPERVISOR", nullable=true)
    @FieldCopy(isCopy = true)
    private String unitySupervisor;

    @CsvBindByName
    @Column(name = "UNITY_EXPECTED_PATH", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityExpectedPath;

    @CsvBindByName
    @Column(name = "UNITY_EXPECTED_WORK", nullable=true)
    @FieldCopy(isCopy = true)
    private String unityExpectedWork;

    @CsvBindByName
    @Column(name = "PAMTIP_ID", nullable=true)
    @FieldCopy(isCopy = true)
    private String pamtipId;

    @CsvBindByName
    @Column(name = "PAMTIP_DEPT", nullable=true)
    @FieldCopy(isCopy = true)
    private String pamtipDept;

    @CsvBindByName
    @Column(name = "PAMTIP_ENTRANCE_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String pamtipEntranceYear;

    @CsvBindByName
    @Column(name = "PAMTIP_GRADUATION_YEAR", nullable=true)
    @FieldCopy(isCopy = true)
    private String pamtipGraduationYear;

    @CsvBindByName
    @Column(name = "HOME_ZIPCODE", nullable=true)
    @FieldCopy(isCopy = true)
    private String homeZipcode;

    @CsvBindByName
    @Column(name = "HOME_ADDR1", nullable=true)
    @FieldCopy(isCopy = true)
    private String homeAddr1;

    @CsvBindByName
    @Column(name = "HOME_ADDR2", nullable=true)
    @FieldCopy(isCopy = true)
    private String homeAddr2;

    @CsvBindByName
    @Column(name = "HOME_ADDR3", nullable=true)
    @FieldCopy(isCopy = true)
    private String homeAddr3;

    @CsvBindByName
    @Column(name = "HOME_ADDR4", nullable=true)
    @FieldCopy(isCopy = true)
    private String homeAddr4;

    @CsvBindByName
    @Column(name = "WORK_ZIPCODE", nullable=true)
    @FieldCopy(isCopy = true)
    private String workZipcode;

    @CsvBindByName
    @Column(name = "WORK_ADDR1", nullable=true)
    @FieldCopy(isCopy = true)
    private String workAddr1;

    @CsvBindByName
    @Column(name = "WORK_ADDR2", nullable=true)
    @FieldCopy(isCopy = true)
    private String workAddr2;

    @CsvBindByName
    @Column(name = "WORK_ADDR3", nullable=true)
    @FieldCopy(isCopy = true)
    private String workAddr3;

    @CsvBindByName
    @Column(name = "WORK_ADDR4", nullable=true)
    @FieldCopy(isCopy = true)
    private String workAddr4;

    @CsvBindByName
    @Column(name = "MAILING_ZIPCODE", nullable=true)
    @FieldCopy(isCopy = true)
    private String mailingZipcode;

    @CsvBindByName
    @Column(name = "MAILING_ADDR1", nullable=true)
    @FieldCopy(isCopy = true)
    private String mailingAddr1;

    @CsvBindByName
    @Column(name = "MAILING_ADDR2", nullable=true)
    @FieldCopy(isCopy = true)
    private String mailingAddr2;

    @CsvBindByName
    @Column(name = "MAILING_ADDR3", nullable=true)
    @FieldCopy(isCopy = true)
    private String mailingAddr3;

    @CsvBindByName
    @Column(name = "MAILING_ADDR4", nullable=true)
    @FieldCopy(isCopy = true)
    private String mailingAddr4;


    public Degree getDegree(String degreeType){
        Degree result = new Degree();

        switch (degreeType){
            case "BS" :     if(bsId == null||"".equals(bsId)){result = null; break;}
                            result.setDegreeType(DegreeType.BS);
                            result.setStudentId(this.bsId);
                            result.setDeptName(this.bsDept);
                            result.setEntranceYear(this.bsEntranceYear);
                            result.setGraduationYear(this.bsGraduationYear);
                            result.setSupervisor(this.bsSupervisor);
                            result.setExpectedPath(this.bsExpectedPath);
                            result.setExpectedWork(this.bsExpectedWork);
                            break;
            case "MS" :     result.setDegreeType(DegreeType.MS);
                            result.setStudentId(this.msId);
                            result.setDeptName(this.msDept);
                            result.setEntranceYear(this.msEntranceYear);
                            result.setGraduationYear(this.msGraduationYear);
                            result.setSupervisor(this.msSupervisor);
                            result.setExpectedPath(this.msExpectedPath);
                            result.setExpectedWork(this.msExpectedWork);
                            break;
            case "PHD" :    result.setDegreeType(DegreeType.PhD);
                            result.setStudentId(this.phdId);
                            result.setDeptName(this.phdDept);
                            result.setEntranceYear(this.phdEntranceYear);
                            result.setGraduationYear(this.phdGraduationYear);
                            result.setSupervisor(this.phdSupervisor);
                            result.setExpectedPath(this.phdExpectedPath);
                            result.setExpectedWork(this.phdExpectedWork);
                            break;
            case "UNITY" :  result.setDegreeType(DegreeType.UNITY);
                            result.setStudentId(this.unityId);
                            result.setDeptName(this.unityDept);
                            result.setEntranceYear(this.unityEntranceYear);
                            result.setGraduationYear(this.unityGraduationYear);
                            result.setSupervisor(this.unitySupervisor);
                            result.setExpectedPath(this.unityExpectedPath);
                            result.setExpectedWork(this.unityExpectedWork);
                            break;
            case "PAMTIP" : result.setDegreeType(DegreeType.PAMTIP);
                            result.setStudentId(this.pamtipId);
                            result.setDeptName(this.pamtipDept);
                            result.setEntranceYear(this.pamtipEntranceYear);
                            result.setGraduationYear(this.pamtipGraduationYear);
                            break;
        }

        return result;
    }
  
    /*public int getUploadInfoId() {
		return uploadInfoId;
	}

	public void setUploadInfoId(int uploadInfoId) {
		this.uploadInfoId = uploadInfoId;
	}*/

	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
   

	public AlumniUploadInfo getAlumniUploadInfo() {
		return alumniUploadInfo;
	}

	public void setAlumniUploadInfo(AlumniUploadInfo alumniUploadInfo) {
		this.alumniUploadInfo = alumniUploadInfo;
	}

	/*public AlumniMember getAlumniMember() {
		return alumniMember;
	}

	public void setAlumniMember(AlumniMember alumniMember) {
		this.alumniMember = alumniMember;
	}*/

    public Long getDetailId() {
        return detailId;
    }

    public String getName() {
        return name;
    }

    public String getBirthdayOfficial() {
        return birthdayOfficial;
    }

    public String getBirthdayReal() {
        return birthdayReal;
    }

    public String  getBirthdayRealType() {
        return birthdayRealType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGender() {
        return gender;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean getIsMarried() {
        return isMarried;
    }

    public String getMembershipFeeStatus() {
        return membershipFeeStatus;
    }

    public String getFeePaymentDate() {
        return feePaymentDate;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public String getBsId() {
        return bsId;
    }

    public String getBsDept() {
        return bsDept;
    }

    public String getBsEntranceYear() {
        return bsEntranceYear;
    }

    public String getBsGraduationYear() {
        return bsGraduationYear;
    }

    public String getMsId() {
        return msId;
    }

    public String getMsDept() {
        return msDept;
    }

    public String getMsEntranceYear() {
        return msEntranceYear;
    }

    public String getMsGraduationYear() {
        return msGraduationYear;
    }

    public String getPhdId() {
        return phdId;
    }

    public String getPhdDept() {
        return phdDept;
    }

    public String getPhdEntranceYear() {
        return phdEntranceYear;
    }

    public String getPhdGraduationYear() {
        return phdGraduationYear;
    }

    public String getUnityId() {
        return unityId;
    }

    public String getUnityDept() {
        return unityDept;
    }

    public String getUnityEntranceYear() {
        return unityEntranceYear;
    }

    public String getUnityGraduationYear() {
        return unityGraduationYear;
    }

    public String getPamtipId() {
        return pamtipId;
    }

    public String getPamtipDept() {
        return pamtipDept;
    }

    public String getPamtipEntranceYear() {
        return pamtipEntranceYear;
    }

    public String getPamtipGraduationYear() {
        return pamtipGraduationYear;
    }

    public String getHomeZipcode() {
        return homeZipcode;
    }

    public String getHomeAddr1() {
        return homeAddr1;
    }

    public String getHomeAddr2() {
        return homeAddr2;
    }

    public String getWorkZipcode() {
        return workZipcode;
    }

    public String getWorkAddr1() {
        return workAddr1;
    }

    public String getWorkAddr2() {
        return workAddr2;
    }

    public String getMailingZipcode() {
        return mailingZipcode;
    }

    public String getMailingAddr1() {
        return mailingAddr1;
    }

    public String getMailingAddr2() {
        return mailingAddr2;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public String getCurrentWork() {
        return currentWork;
    }

    public String getCurrentWorkDept() {
        return currentWorkDept;
    }

    public String getCurrentJobTitle() {
        return currentJobTitle;
    }

    public String getBsSupervisor() {
        return bsSupervisor;
    }

    public String getBsExpectedPath() {
        return bsExpectedPath;
    }

    public String getBsExpectedWork() {
        return bsExpectedWork;
    }

    public String getMsSupervisor() {
        return msSupervisor;
    }

    public String getMsExpectedPath() {
        return msExpectedPath;
    }

    public String getMsExpectedWork() {
        return msExpectedWork;
    }

    public String getPhdSupervisor() {
        return phdSupervisor;
    }

    public String getPhdExpectedPath() {
        return phdExpectedPath;
    }

    public String getPhdExpectedWork() {
        return phdExpectedWork;
    }

    public String getUnitySupervisor() {
        return unitySupervisor;
    }

    public String getUnityExpectedPath() {
        return unityExpectedPath;
    }

    public String getUnityExpectedWork() {
        return unityExpectedWork;
    }

    public String getHomeAddr3() {
        return homeAddr3;
    }

    public String getHomeAddr4() {
        return homeAddr4;
    }

    public String getWorkAddr3() {
        return workAddr3;
    }

    public String getWorkAddr4() {
        return workAddr4;
    }

    public String getMailingAddr3() {
        return mailingAddr3;
    }

    public String getMailingAddr4() {
        return mailingAddr4;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdayOfficial(String birthdayOfficial) {
        this.birthdayOfficial = birthdayOfficial;
    }

    public void setBirthdayReal(String birthdayReal) {
        this.birthdayReal = birthdayReal;
    }

    public void setBirthdayRealType(String birthdayRealType) {
        this.birthdayRealType = birthdayRealType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public void setMembershipFeeStatus(String membershipFeeStatus) {
        this.membershipFeeStatus = membershipFeeStatus;
    }

    public void setFeePaymentDate(String feePaymentDate) {
        this.feePaymentDate = feePaymentDate;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setBsId(String bsId) {
        this.bsId = bsId;
    }

    public void setBsDept(String bsDept) {
        this.bsDept = bsDept;
    }

    public void setBsEntranceYear(String bsEntranceYear) {
        this.bsEntranceYear = bsEntranceYear;
    }

    public void setBsGraduationYear(String bsGraduationYear) {
        this.bsGraduationYear = bsGraduationYear;
    }

    public void setMsId(String msId) {
        this.msId = msId;
    }

    public void setMsDept(String msDept) {
        this.msDept = msDept;
    }

    public void setMsEntranceYear(String msEntranceYear) {
        this.msEntranceYear = msEntranceYear;
    }

    public void setMsGraduationYear(String msGraduationYear) {
        this.msGraduationYear = msGraduationYear;
    }

    public void setPhdId(String phdId) {
        this.phdId = phdId;
    }

    public void setPhdDept(String phdDept) {
        this.phdDept = phdDept;
    }

    public void setPhdEntranceYear(String phdEntranceYear) {
        this.phdEntranceYear = phdEntranceYear;
    }

    public void setPhdGraduationYear(String phdGraduationYear) {
        this.phdGraduationYear = phdGraduationYear;
    }

    public void setUnityId(String unityId) {
        this.unityId = unityId;
    }

    public void setUnityDept(String unityDept) {
        this.unityDept = unityDept;
    }

    public void setUnityEntranceYear(String unityEntranceYear) {
        this.unityEntranceYear = unityEntranceYear;
    }

    public void setUnityGraduationYear(String unityGraduationYear) {
        this.unityGraduationYear = unityGraduationYear;
    }

    public void setPamtipId(String pamtipId) {
        this.pamtipId = pamtipId;
    }

    public void setPamtipDept(String pamtipDept) {
        this.pamtipDept = pamtipDept;
    }

    public void setPamtipEntranceYear(String pamtipEntranceYear) {
        this.pamtipEntranceYear = pamtipEntranceYear;
    }

    public void setPamtipGraduationYear(String pamtipGraduationYear) {
        this.pamtipGraduationYear = pamtipGraduationYear;
    }

    public void setHomeZipcode(String homeZipcode) {
        this.homeZipcode = homeZipcode;
    }

    public void setHomeAddr1(String homeAddr1) {
        this.homeAddr1 = homeAddr1;
    }

    public void setHomeAddr2(String homeAddr2) {
        this.homeAddr2 = homeAddr2;
    }

    public void setWorkZipcode(String workZipcode) {
        this.workZipcode = workZipcode;
    }

    public void setWorkAddr1(String workAddr1) {
        this.workAddr1 = workAddr1;
    }

    public void setWorkAddr2(String workAddr2) {
        this.workAddr2 = workAddr2;
    }

    public void setMailingZipcode(String mailingZipcode) {
        this.mailingZipcode = mailingZipcode;
    }

    public void setMailingAddr1(String mailingAddr1) {
        this.mailingAddr1 = mailingAddr1;
    }

    public void setMailingAddr2(String mailingAddr2) {
        this.mailingAddr2 = mailingAddr2;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setCurrentWork(String currentWork) {
        this.currentWork = currentWork;
    }

    public void setCurrentWorkDept(String currentWorkDept) {
        this.currentWorkDept = currentWorkDept;
    }

    public void setCurrentJobTitle(String currentJobTitle) {
        this.currentJobTitle = currentJobTitle;
    }

    public void setBsSupervisor(String bsSupervisor) {
        this.bsSupervisor = bsSupervisor;
    }

    public void setBsExpectedPath(String bsExpectedPath) {
        this.bsExpectedPath = bsExpectedPath;
    }

    public void setBsExpectedWork(String bsExpectedWork) {
        this.bsExpectedWork = bsExpectedWork;
    }

    public void setMsSupervisor(String msSupervisor) {
        this.msSupervisor = msSupervisor;
    }

    public void setMsExpectedPath(String msExpectedPath) {
        this.msExpectedPath = msExpectedPath;
    }

    public void setMsExpectedWork(String msExpectedWork) {
        this.msExpectedWork = msExpectedWork;
    }

    public void setPhdSupervisor(String phdSupervisor) {
        this.phdSupervisor = phdSupervisor;
    }

    public void setPhdExpectedPath(String phdExpectedPath) {
        this.phdExpectedPath = phdExpectedPath;
    }

    public void setPhdExpectedWork(String phdExpectedWork) {
        this.phdExpectedWork = phdExpectedWork;
    }

    public void setUnitySupervisor(String unitySupervisor) {
        this.unitySupervisor = unitySupervisor;
    }

    public void setUnityExpectedPath(String unityExpectedPath) {
        this.unityExpectedPath = unityExpectedPath;
    }

    public void setUnityExpectedWork(String unityExpectedWork) {
        this.unityExpectedWork = unityExpectedWork;
    }

    public void setHomeAddr3(String homeAddr3) {
        this.homeAddr3 = homeAddr3;
    }

    public void setHomeAddr4(String homeAddr4) {
        this.homeAddr4 = homeAddr4;
    }

    public void setWorkAddr3(String workAddr3) {
        this.workAddr3 = workAddr3;
    }

    public void setWorkAddr4(String workAddr4) {
        this.workAddr4 = workAddr4;
    }

    public void setMailingAddr3(String mailingAddr3) {
        this.mailingAddr3 = mailingAddr3;
    }

    public void setMailingAddr4(String mailingAddr4) {
        this.mailingAddr4 = mailingAddr4;
    }

//    public void setAlumniUploadInfo(AlumniUploadInfo alumniUploadInfo) {
//        this.alumniUploadInfo = alumniUploadInfo;
//    }

//
//    public int getUploadInfoId() {
//        return uploadInfoId;
//    }
//
//    public void setUploadInfoId(int uploadInfoId) {
//        this.uploadInfoId = uploadInfoId;
//    }
}
