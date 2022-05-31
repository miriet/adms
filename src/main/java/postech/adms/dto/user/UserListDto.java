package postech.adms.dto.user;

import com.mysema.query.annotations.QueryProjection;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import postech.adms.domain.user.AdminGroup;
import postech.adms.domain.user.AdminUser;

import java.io.Serializable;
import java.util.Date;

public class UserListDto implements Serializable{

	private static final long serialVersionUID = 1592970533590141321L;
	
	private Long id;
	private String loginId;
	private String userName;
	private String name;
	private String email;
	private AdminGroup adminGroup;
	private Date dateCreated;
	private String isDel;
	private Long pwdCount;
	
	@QueryProjection
	public UserListDto(AdminUser user, String siteName){
		this.setId(user.getId());
		this.setLoginId(user.getLoginId());
		this.setUserName(user.getLoginId());
		this.setName(user.getName());
		this.setEmail(user.getEmail());
		this.setAdminGroup(user.getAdminGroup());
		this.setDateCreated(user.getAuditable().getDateCreated());
		this.setIsDel(Boolean.toString(!user.getIsDel()));
		this.setPwdCount(user.getPwdCount());
	}
	
	@QueryProjection
	public UserListDto(AdminUser user){
		this.setId(user.getId());
		this.setLoginId(user.getLoginId());
		this.setUserName(user.getLoginId());
		this.setName(user.getName());
		this.setEmail(user.getEmail());
		this.setAdminGroup(user.getAdminGroup());
		this.setDateCreated(user.getAuditable().getDateCreated());
		this.setIsDel(Boolean.toString(!user.getIsDel()));
		this.setPwdCount(user.getPwdCount());
	}
	
	
	public Long getPwdCount() {
		return pwdCount;
	}

	public void setPwdCount(Long pwdCount) {
		this.pwdCount = pwdCount;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return StringUtils.defaultString(email);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGroupNames(){
		return adminGroup != null? adminGroup.getGroupName() :"";
	}
	public String getDescription(){
		return adminGroup != null? adminGroup.getDescription() :"";
	}
	public AdminGroup getAdminGroup() {
		return adminGroup;
	}

	public void setAdminGroup(AdminGroup adminGroup) {
		this.adminGroup = adminGroup;
	}

	public String getDateCreated() {
		String data = "";
		if(dateCreated != null && !dateCreated.equals("") ) data=  DateFormatUtils.ISO_DATE_FORMAT.format(dateCreated);
		return data;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
}
