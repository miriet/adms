package postech.adms.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import postech.adms.audit.AuditableListener;
import postech.adms.audit.UserAuditable;
import postech.adms.common.persistence.FieldCopy;
import postech.adms.domain.codes.CalType;
import postech.adms.domain.codes.Gender;
import postech.adms.domain.codes.MembershipFeeStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miriet
 * @version 1.0
 * @created 20-2-2017 12:39:53
 */
@Entity
@Table(name = "ADMS_MEMBER")
@EntityListeners(AuditableListener.class)
@JsonIgnoreProperties({"birthdayType", "membershipFeeStatus", "gender", "degrees", "addresses", "auditable"})
public class AlumniMember implements Serializable {

    private static final long serialVersionUID = 6264736458052417125L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    /**
     * 성명
     */
    @Column(name = "NAME")
    @FieldCopy(isCopy = true)
    private String name;

    /**
     * 생년월일
     */
    @Column(name = "BIRTHDAY_OFFICIAL")
    @FieldCopy(isCopy = true)
    private String birthdayOfficial;

    /**
     * 실제 생년월일
     */
    @Column(name = "BIRTHDAY_REAL")
    @FieldCopy(isCopy = true)
    private String birthdayReal;

    /**
     * 생년월일. 양/음력 구분
     */
    @Column(name = "BIRTHDAY_TYPE")
    @Enumerated(EnumType.STRING)
    @FieldCopy(isCopy = true)
    private CalType birthdayType = CalType.SOLAR;

    /**
     * 이메일
     */
    @Column(name = "EMAIL")
    @FieldCopy(isCopy = true)
    private String email;

    /**
     * 성별
     */
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    @FieldCopy(isCopy = true)
    private Gender gender;

    /**
     * 생존여부
     */
    @Column(name = "ALIVE_YN")
    @FieldCopy(isCopy = true)
    private boolean isAlive = Boolean.TRUE;

    /**
     * 결혼여부
     */
    @Column(name = "MARRIED_YN")
    @FieldCopy(isCopy = true)
    private boolean isMarried;

    /**
     * 동창회비 납부여부
     */
    @Column(name = "MEMBERSHIP_FEE_STATUS")
    @Enumerated(EnumType.STRING)
    @FieldCopy(isCopy = true)
    private MembershipFeeStatus membershipFeeStatus;

    /**
     * 동창회비 최종 납부 일자
     */
    @Column(name = "MEMBERSHIP_FEE_DATE")
    @Temporal(TemporalType.DATE)
    @FieldCopy(isCopy = true)
    private Date membershipFeeDate;

    /**
     * 동창회비 납부금액 (누적금액)
     */
    @Column(name = "MEMBERSHIP_FEE_TOTAL")
    @FieldCopy(isCopy = true)
    private int membershipFeeTotal;

    /**
     * 연락처
     */
    @Column(name = "PHONE")
    @FieldCopy(isCopy = true)
    private String phone;

    /**
     * 휴대폰
     */
    @Column(name = "MOBILE")
    @FieldCopy(isCopy = true)
    private String mobile;

    /**
     * 현재 직종
     */
    @Column(name = "CURRENT_WORK_TYPE")
    @FieldCopy(isCopy = true)
    private String currentWorkType;

    /**
     * 현재 직장명
     */
    @Column(name = "CURRENT_WORK")
    @FieldCopy(isCopy = true)
    private String currentWork;

    /**
     * 현재 부서
     */
    @Column(name = "CURRENT_WORK_DEPT")
    @FieldCopy(isCopy = true)
    private String currentWorkDept;

    /**
     * 현재 직책
     */
    @Column(name = "CURRENT_JOB_TITLE")
    @FieldCopy(isCopy = true)
    private String currentJobTitle;

    /**
     * 우편물 수령지
     */
    @Column(name = "MAILING_ADDRESS")
    @FieldCopy(isCopy = true)
    private String mailingAddress;

    /**
     * 국적
     */
    @Column(name = "NATIONALITY")
    @FieldCopy(isCopy = true)
    private String nationality = "KOR";

    /**
     * 사용여부
     */
    @Column(name = "IS_DEL")
    @FieldCopy(isCopy = true)
    private boolean isDel = Boolean.FALSE;

    /**
     * 개인정보 보호법 동의여부
     */
    @Column(name = "PRIVACY_ACT_AGREE")
    @FieldCopy(isCopy = true)
    private boolean privacyActAgree;

    /**
     * 학위정보
     */
    @OneToMany(mappedBy = "alumniMember", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Degree> degrees = new ArrayList<Degree>();

    /**
     * 회원주소
     */
    @OneToMany(mappedBy = "alumniMember", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Address> addresses = new ArrayList<Address>();

    /**
     * 생성일시,생성자,수정일시,수정자
     */
    @Embedded
    protected UserAuditable auditable = new UserAuditable();

    public AlumniMember() {

    }

    public AlumniMember(String birthdayOfficial, String birthdayReal, CalType birthdayType, String email, Gender gender,
                        boolean isAlive, boolean isMarried, MembershipFeeStatus membershipFeeStatus, Date membershipFeeDate, int membershipFeeTotal, String mobile, String name,
                        String nationality, String phone, List<Address> addresses, List<Degree> degrees, String currentWorkType, String currentWork, String currentWorkDept,
                        String currentJobTitle, String mailingAddress) {
        this.birthdayOfficial = birthdayOfficial;
        this.birthdayReal = birthdayReal;
        this.birthdayType = birthdayType;
        this.email = email;
        this.gender = gender;
        this.isAlive = isAlive;
        this.isMarried = isMarried;
        this.membershipFeeStatus = membershipFeeStatus;
        this.membershipFeeDate = membershipFeeDate;
        this.membershipFeeTotal = membershipFeeTotal;
        this.mobile = mobile;
        this.name = name;
        this.nationality = nationality;
        this.phone = phone;
        this.addresses = addresses;
        this.degrees = degrees;
//		this.firstWork = firstWork;
        this.currentWorkType = currentWorkType;
        this.currentWork = currentWork;
        this.currentWorkDept = currentWorkDept;
        this.currentJobTitle = currentJobTitle;
        this.mailingAddress = mailingAddress;

    }

    @Override
    public void finalize() throws Throwable {

    }

    public void addDegree(Degree degree) {
        if (degrees == null || degrees.isEmpty()) {
            degrees = new ArrayList<Degree>();
        }

        Degree findDegree = getFindDegree(degrees, degree);
        if (findDegree == null) {
            degree.setAlumniMember(this);
            degrees.add(degree);
        } else {
            findDegree.setAlumniMember(this);
            findDegree.setEntranceYear(degree.getEntranceYear());
            findDegree.setGraduationYear(degree.getGraduationYear());
        }
    }

    private Degree getFindDegree(List<Degree> degreesList, Degree degree) {
        Degree result = null;
        for (Degree data : degreesList) {
            if (degree.getDegreeType().equals(data.getDegreeType())
                    && degree.getDeptCode().equals(data.getDeptCode())
                    // && degree.getDeptName().equals(data.getDeptName())
                    && degree.getStudentId().equals(data.getStudentId())) {
                result = data;
                break;
            }
        }
        return result;
    }

    public void addAddress(Address address) {
        if (addresses == null || addresses.isEmpty()) {
            addresses = new ArrayList<Address>();
        }

        Address findAddress = getFindAdderss(addresses, address);
        if (findAddress == null) {
            address.setAlumniMember(this);
            addresses.add(address);
        } else {
            findAddress.setAlumniMember(this);
            findAddress.setCountry("KOR");
            findAddress.setZipCode(address.getZipCode());
            findAddress.setAddress1(address.getAddress1());
            findAddress.setAddress2(address.getAddress2());
            findAddress.setAddress3(address.getAddress3());
            findAddress.setAddress4(address.getAddress4());
        }
    }

    private Address getFindAdderss(List<Address> addressList, Address address) {
        Address result = null;
        for (Address data : addressList) {
            if (address.getAddressType().equals(data.getAddressType())) {
                result = data;
                break;
            }
        }
        return result;
    }

    public String getCurrentWorkType() {
        return currentWorkType;
    }

    public void setCurrentWorkType(String currentWorkType) {
        this.currentWorkType = currentWorkType;
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

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthdayOfficial() {
        return birthdayOfficial;
    }

    public void setBirthdayOfficial(String birthdayOfficial) {
        this.birthdayOfficial = birthdayOfficial;
    }

    public String getBirthdayReal() {
        return birthdayReal;
    }

    public void setBirthdayReal(String birthdayReal) {
        this.birthdayReal = birthdayReal;
    }

    public CalType getBirthdayType() {
        return birthdayType;
    }

    public void setBirthdayType(CalType birthdayType) {
        this.birthdayType = birthdayType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public MembershipFeeStatus getMembershipFeeStatus() {
        return membershipFeeStatus;
    }

    public void setMembershipFeeStatus(MembershipFeeStatus membershipFeeStatus) {
        this.membershipFeeStatus = membershipFeeStatus;
    }

    public Date getMembershipFeeDate() {
        return membershipFeeDate;
    }

    public void setMembershipFeeDate(Date membershipFeeDate) {
        this.membershipFeeDate = membershipFeeDate;
    }

    public int getMembershipFeeTotal() {
        return membershipFeeTotal;
    }

    public void setMembershipFeeTotal(int membershipFeeTotal) {
        this.membershipFeeTotal = membershipFeeTotal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degrees) {
        this.degrees = degrees;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(Boolean isMarried) {
        this.isMarried = isMarried;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean isPrivacyActAgree() {
        return privacyActAgree;
    }

    public void setPrivacyActAgree(Boolean privacyActAgree) {
        this.privacyActAgree = privacyActAgree;
    }

    public UserAuditable getAuditable() {
        return auditable;
    }

    public void setAuditable(UserAuditable auditable) {
        this.auditable = auditable;
    }

}