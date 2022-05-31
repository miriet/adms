package postech.adms.web.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserInfo extends User{

	private static final long serialVersionUID = 5876472778161857270L;

	protected Long   id;
	protected String name;
	protected String userName;
	protected String nickName;
	protected String email;
	protected String saltKey;
	protected Long   siteId;
	protected List<String> permissionNames;
	protected Long   groupId;
	protected String deptCode;

	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSaltKey() {
		return saltKey;
	}

	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public List<String> getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(List<String> permissionNames) {
		this.permissionNames = permissionNames;
	}

	public static void main(String[] args){
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		String encoded = encoder.encodePassword("1234", "POSTECH");
		System.out.println("Encoded password::" + encoded);
	}
}
