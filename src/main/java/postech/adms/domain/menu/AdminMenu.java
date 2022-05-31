package postech.adms.domain.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import postech.adms.domain.user.AdminGroup;

import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ADMS_MENU")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "admsAdminMenu")
public class AdminMenu implements Serializable{

	private static final long serialVersionUID = -7415354799484421336L;

	@Id
//    @GeneratedValue(generator = "AdminMenuId")
//	@GenericGenerator(
//	        name="AdminMenuId",
//	        strategy="IdOverrideTableGenerator",
//	        parameters = {
//	            @Parameter(name="segment_value", value="AdminMenu"),
//	            @Parameter(name="entity_name", value="AdminMenu")
//	        }
//	    )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_ID")
	protected Long menuId;

	@Column(name = "MENU_NAME",nullable=false,length=100)
	protected String name;
	
	@Column(name = "IS_DEL" ,nullable=false)
	protected Boolean isDel = false;
	
	@Column(name = "IS_LEAF",nullable=false)
	protected Boolean isLeaf = true;
	
	@Column(name = "MENU_PATH",nullable=true)
	protected String menuPath;
	
	@Column(name = "DISP_ORDER",nullable=false)
	protected Integer sort;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@org.hibernate.annotations.NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="PARENT_MENU_ID")
	protected AdminMenu parentMenu;
	
	@JsonIgnore
	@Where(clause="IS_DEL=0")
	@OrderBy("sort ASC")
	@OneToMany(mappedBy = "parentMenu")
	@org.hibernate.annotations.Fetch(
		org.hibernate.annotations.FetchMode.SUBSELECT
	)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@org.hibernate.annotations.Cache(
		usage=org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
	)
	@org.hibernate.annotations.Cascade({
		org.hibernate.annotations.CascadeType.ALL
	})
	protected Set<AdminMenu> childMenus = Sets.newHashSet();
	
	@ManyToMany
	@JoinTable(name="ADMS_MENU_ADMIN_GROUP",
			joinColumns = {@JoinColumn(name="MENU_ID")},
			inverseJoinColumns = {@JoinColumn(name="GROUP_ID")})
	@LazyCollection(LazyCollectionOption.EXTRA)
	@org.hibernate.annotations.Cache(
		usage=org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY
	)
	@org.hibernate.annotations.Fetch(
		org.hibernate.annotations.FetchMode.SUBSELECT
	)
	protected Set<AdminGroup> menuPermissions = Sets.newHashSet();
	
	
	public String getFullPath(){
		List<String> paths = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		setFullPath(this,paths);
		
		int index = 0;
		
		for(String path : paths){
			if(index > 0){
				if(index != 1){
					sb.append(">");
				}
				sb.append(path);
			}
			
			index++;
		}
		
		return sb.toString();
	}
	
	private void setFullPath(AdminMenu menu,List<String> paths){
		if(menu.getParentMenu() != null){
			setFullPath(menu.getParentMenu(),paths);
			paths.add(menu.getName());
		}else{
			paths.add(menu.getName());
		}
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDel() {
		return isDel;
	}

	public void setDel(Boolean del) {
		isDel = del;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public AdminMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(AdminMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public Set<AdminMenu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set<AdminMenu> childMenus) {
		this.childMenus = childMenus;
	}

	/*public Set<AdminPermission> getMenuPermissions() {
		return menuPermissions;
	}

	public void setMenuPermissions(Set<AdminPermission> menuPermissions) {
		this.menuPermissions = menuPermissions;
	}*/
}
