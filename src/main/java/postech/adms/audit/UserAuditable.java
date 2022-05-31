package postech.adms.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@EntityListeners(AuditableListener.class)
public class UserAuditable implements Serializable{

	private static final long serialVersionUID = 5445737365009381744L;
	
	@CreatedBy
	@Column(name="CREATED_BY",nullable=false,length=50)
	protected Long createdBy;
	
	@CreatedDate
	@Column(name="DATE_CREATED",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateCreated;
	
	@LastModifiedDate
	@Column(name="DATE_UPDATED",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateUpdated;
	
	@LastModifiedBy
	@Column(name="UPDATED_BY",nullable=true,length=50)
	protected Long updatedBy;
	
	/* 2017.06.24 주석 처리	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATED_BY", insertable=false,updatable=false)
	protected AdminUser createUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UPDATED_BY", insertable=false,updatable=false)
	protected AdminUser updateUser;
    */

	public UserAuditable() {
		super();
	}

	public UserAuditable(Long createdBy, Long updatedBy) {
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public Long getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	
	@JsonProperty("formatDateCreated")
	public String getFormatDateCreated(){
		if(dateCreated != null){
			return DateFormatUtils.format(dateCreated, "yyyy-MM-dd HH:mm:ss");
		}
		
		return null;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	@JsonProperty("formatDateUpdated")
	public String getFormatDateUpdated(){
		if(dateUpdated != null){
			return DateFormatUtils.format(dateUpdated, "yyyy-MM-dd HH:mm:ss");
		}
		
		return null;
	}
	
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
/*  2017.06.24 주석처리
	@JsonProperty("createUserName")
	public String getCreateUserName(){
		if(createUser != null)
			return createUser.getName();
		else return "";
	}
	
	@JsonProperty("updateUserName")
	public String getUpdateUserName(){
		if(updateUser != null)
			return updateUser.getName();
		else return "";
	}
	
	@JsonIgnore
	public AdminUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(AdminUser createUser) {
		this.createUser = createUser;
	}
	
	@JsonIgnore
	public AdminUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(AdminUser updateUser) {
		this.updateUser = updateUser;
	}
*/
}
