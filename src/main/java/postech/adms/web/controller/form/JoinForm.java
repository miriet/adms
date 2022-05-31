package postech.adms.web.controller.form;

public class JoinForm {
	String name;
	String birthday;
	String loginId;
	String password;
	String passwordConfirm;
	String email;
	String checkDupUserId;
	boolean memberPolicy;
	String ci;
	String di;
	String kcpName;
	String kcpBirthday;
	
	
	public String getKcpName() {
		return kcpName;
	}
	public void setKcpName(String kcpName) {
		this.kcpName = kcpName;
	}
	public String getKcpBirthday() {
		return kcpBirthday;
	}
	public void setKcpBirthday(String kcpBirthday) {
		this.kcpBirthday = kcpBirthday;
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
	public boolean isMemberPolicy() {
		return memberPolicy;
	}
	public void setMemberPolicy(boolean memberPolicy) {
		this.memberPolicy = memberPolicy;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getCheckDupUserId() {
		return checkDupUserId;
	}
	public void setCheckDupUserId(String checkDupUserId) {
		this.checkDupUserId = checkDupUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	
}
