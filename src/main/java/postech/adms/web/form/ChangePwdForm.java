package postech.adms.web.form;

public class ChangePwdForm {
	Long   userId;           // 사용자번호
	String passwd;           // 현재 비밀번호
	String newPasswd;        // 새 비밀번호
	String newconfirmPasswd; // 새 비밀번호 롹인

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNewPasswd() {
		return newPasswd;
	}
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	public String getNewconfirmPasswd() {
		return newconfirmPasswd;
	}
	public void setNewconfirmPasswd(String newconfirmPasswd) {
		this.newconfirmPasswd = newconfirmPasswd;
	}



}
