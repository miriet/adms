package postech.adms.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import postech.adms.audit.AuditableListener;
import postech.adms.audit.UserAuditable;
import postech.adms.common.persistence.FieldCopy;

/**
 * @author miriet
 * @version 1.0
 * @created 02-3-2017 ���� 2:35:03
 */
@Entity
@Table(name = "ADMS_ADMIN_GROUP")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "admsAdminGroup")
@EntityListeners(AuditableListener.class)
public class AdminGroup implements Serializable{

	private static final long serialVersionUID = 7142500077827371920L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private Long groupId;

	@FieldCopy
	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name = "LEVEL")
	private String level;

	@FieldCopy
	@Column(name = "IS_DEL",nullable=false)
	protected Boolean isDel = false;

	@FieldCopy
	@Column(name="GROUP_DESC",nullable=true,length=900)
	private String description;

	@OneToMany(mappedBy = "adminGroup")
	private Set<AdminUser> adminUser;

	@Embedded
	protected UserAuditable auditable = new UserAuditable();

	public AdminGroup(){

	}

	public AdminGroup(String groupName, String level) {
		this.groupName = groupName;
		this.level = level;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean del) {
		isDel = del;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AdminUser> getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Set<AdminUser> adminUser) {
		this.adminUser = adminUser;
	}

	public void finalize() throws Throwable {

	}

}