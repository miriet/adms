package postech.adms.domain.member;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import postech.adms.common.persistence.FieldCopy;
import postech.adms.domain.codes.DegreeType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author miriet
 * @version 1.0
 * @created 20-2-2017 12:39:53
 */
//@Embeddable
@Entity
@Table(name = "ADMS_DEGREE")
@JsonIgnoreProperties({"alumniMember", "degreeType"})
public class Degree implements Serializable{

	private static final long serialVersionUID = -1568786495806363091L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEGREE_ID")
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
	 * 학위타입
	 */
	@Column(name = "DEGREE_TYPE")
	@Enumerated(EnumType.STRING)
	@FieldCopy(isCopy = true)
	private DegreeType degreeType;

	/**
	 * 학번
	 */
	@Column(name = "STUDENT_ID")
	@FieldCopy(isCopy = true)
	private String studentId;

	/**
	 * 입학년도
	 */
	@Column(name = "ENTRANCE_YEAR")
	@FieldCopy(isCopy = true)
	private String entranceYear;

	/**
	 * 졸업일자
	 */
	@Column(name = "GRADUATION_YEAR")
	@FieldCopy(isCopy = true)
	private String graduationYear;

	/**
     * 지도교수
     */
	@Column(name = "SUPERVISOR")
    @FieldCopy(isCopy = true)
    private String supervisor;

    /**
     * 졸업 시 예상진로
     */
    @Column(name = "EXPECTED_PATH")
    @FieldCopy(isCopy = true)
    private String expectedPath;

    /**
     * 졸업 시 예상직장
     */
    @Column(name = "EXPECTED_WORK")
    @FieldCopy(isCopy = true)
    private String expectedWork;

    /**
	 * 회원
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "MEMBER_ID")
	private AlumniMember alumniMember;

	public Degree(){

	}

	
	public String getExpectedPath() {
		return expectedPath;
	}


	public void setExpectedPath(String expectedPath) {
		this.expectedPath = expectedPath;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	public String getEntranceYear() {
		return entranceYear;
	}

	public void setEntranceYear(String entranceYear) {
		this.entranceYear = entranceYear;
	}

	public String getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public AlumniMember getAlumniMember() {
		return alumniMember;
	}

	public void setAlumniMember(AlumniMember alumniMember) {
		this.alumniMember = alumniMember;
	}
    
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getExpectedWork() {
        return expectedWork;
    }

    public void setExpectedWork(String expectedWork) {
        this.expectedWork = expectedWork;
    }

    public void finalize() throws Throwable {

	}

}