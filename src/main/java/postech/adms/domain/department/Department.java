package postech.adms.domain.department;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import postech.adms.common.persistence.FieldCopy;

@Entity
@Table(name = "ADMS_DEPARTMENT")
public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5830286805478696906L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPT_ID")
	private Long id;

	/**
	 * 학과코드
	 */
	@Column(name = "DEPT_CODE")
	@FieldCopy(isCopy = true)
	private String deptCode;

	/**
	 * 학과명
	 */
	@Column(name = "DEPT_NAME")
	@FieldCopy(isCopy = true)
	private String deptName;

	/**
	 * 학과 영문명
	 */
	@Column(name = "DEPT_ENG_NAME")
	@FieldCopy(isCopy = true)
	private String deptEngName;

	/**
	 * 학과 단축명
	 */
	@Column(name = "DEPT_SHORT_NAME")
	@FieldCopy(isCopy = true)
	private String deptShortName;

	/**
	 * 사용여부
	 */
	@Column(name = "IS_DEL")
	@FieldCopy(isCopy = true)
	private Boolean isDel = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptEngName() {
		return deptEngName;
	}

	public void setDeptEngName(String deptEngName) {
		this.deptEngName = deptEngName;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}

    
}
