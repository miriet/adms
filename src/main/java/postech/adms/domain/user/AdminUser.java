package postech.adms.domain.user;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedDate;
import postech.adms.audit.AuditableListener;
import postech.adms.audit.UserAuditable;
import postech.adms.common.persistence.FieldCopy;
import postech.adms.domain.department.Department;
import postech.adms.domain.member.AlumniMember;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ADMS_ADMIN")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "spAdminUser")
@EntityListeners(AuditableListener.class)
public class AdminUser implements Serializable {

	private static final long serialVersionUID = -703853490503572496L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADMIN_ID")
	@FieldCopy(isCopy = false)
	protected Long id;

	@FieldCopy
	@Column(name = "LOGIN_ID", nullable = false, length = 45)
	private String loginId;

	@FieldCopy
	@Column(name = "NAME", nullable = false, length = 45)
	private String name;

	@FieldCopy(isCopy = false)
	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;

	@FieldCopy
	@Column(name = "EMAIL", nullable = true, length = 100)
	protected String email;
	
	@FieldCopy(isCopy = false)
	@Column(name = "CI", nullable = true, length = 100)
	protected String ci;
	
	@FieldCopy(isCopy = false)
	@Column(name = "DI", nullable = true, length = 100)
	protected String di;
	
	/*
	@FieldCopy(isCopy=false)
	@Column(name = "DEPARTMENT",nullable=true,length=45)
	private String department;
    */
	
	@FieldCopy(isCopy = false)
	@ManyToOne
	@JoinColumn(name = "ADMIN_GROUP_ID", referencedColumnName = "GROUP_ID")
	private AdminGroup adminGroup;

//	@FieldCopy(isCopy=false)
	@Embedded
	protected UserAuditable auditable = new UserAuditable();
	
	@FieldCopy
	@Column(name = "IS_DEL", nullable = false)
	protected Boolean isDel = Boolean.FALSE;

	@Column(name = "DEPT_CODE", nullable = true)
	private String deptCode;

	@OneToOne
	@JoinColumn(name = "DEPT_CODE", referencedColumnName = "DEPT_CODE", insertable = false, updatable = false)
	private Department department;
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
	private AlumniMember member;
	
	/**
	 * 추가 컬럼
	 * 2018.03.15 박재현
	 * 비밀번호 횟수제한(5)
	 * @return
	 */
	@Column(name = "PWD_COUNT")
	private Long pwdCount;
	
	/**
	 * 추가 컬럼
	 * 2018.03.15 박재현
	 * 비밀번호 히스토리(2개까지만 저장)
	 * @return
	 */
	@Column(name = "PASSWD_HIST" , nullable = false, length = 500)
	private String passwdHist;
	
	/**
	 * 추가 컬럼
	 * 생년월일
	 * 2018.03.16 박재현
	 */
	@Column(name = "BIRTHDAY")
	private String birthday;
	
	/**
	 * 추가 컬럼
	 * 2018.03.15 박재현
	 * 마지막 비밀번호 변경일자(6개월)
	 * @return
	 */
	@LastModifiedDate
	@Column(name="LAST_CHANGE_PWD",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastChangePwd;
	
//	@JsonIgnore
//	public String getMatchingPassword() {
//		return matchingPassword;
//	}
//
//	public void setMatchingPassword(String matchingPassword) {
//		this.matchingPassword = matchingPassword;
//	}
	
	
	public Long getPwdCount() {
		return pwdCount;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public Date getLastChangePwd() {
		return lastChangePwd;
	}
	public void setLastChangePwd(Date lastChangePwd) {
		this.lastChangePwd = lastChangePwd;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setPwdCount(Long pwdCount) {
		this.pwdCount = pwdCount;
	}
	public String getPasswdHist() {
		return passwdHist;
	}
	public void setPasswdHist(String passwdHist) {
		this.passwdHist = passwdHist;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public AdminGroup getAdminGroup() {
		return adminGroup;
	}

	public void setAdminGroup(AdminGroup adminGroup) {
		this.adminGroup = adminGroup;
	}

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}


	public UserAuditable getAuditable() {
		return auditable;
	}

	public void setAuditable(UserAuditable auditable) {
		this.auditable = auditable;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public AlumniMember getMember() {
		return member;
	}
	public void setMember(AlumniMember member) {
		this.member = member;
	}

}
