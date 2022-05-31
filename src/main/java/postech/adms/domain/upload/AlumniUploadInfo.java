package postech.adms.domain.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import postech.adms.domain.department.Department;
import postech.adms.domain.user.AdminGroup;
import postech.adms.domain.user.AdminUser;

/**
 * Created by miriet on 2017. 5. 6..
 */
@Entity
@Table(name = "MEMBER_UPLOAD_INFO")
public class AlumniUploadInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFO_ID")
    private Long infoId;

    @Column(name = "UPLOADER")
    private int uploader;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "STORED_FILE_NAME")
    private String storedFileName;

    @Column(name = "UPLOAD_DATE")
    private Date uploadDate;

    @OneToOne
    @JoinColumn(name = "UPLOADER", referencedColumnName = "ADMIN_ID", insertable = false, updatable = false)
    private AdminUser adminUser;

    // ======================================================================================================
    // TODO : 2017.06.24 추가
    @Column(name = "GROUP_ID")
    private Long groupId;
    
    /*@OneToOne
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
    private AdminGroup adminGroup;*/
    
    @Column(name = "DEPT_CODE")
    private String deptCode;
    
    /*@OneToOne
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEPT_ID", insertable = false, updatable = false)
    private Department department;*/
    

    // TODO : 2017.06.24 추가
    // ======================================================================================================
    
    
    
//    @JoinColumn(name = "UPLOAD_INFO_ID", nullable = false)
//    @ElementCollection
//    @CollectionTable(name = "MEMBER_UPLOAD_DETAIL", joinColumns = @JoinColumn(name = "UPLOAD_INFO_ID"))
    @OneToMany(mappedBy = "alumniUploadInfo",fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<AlumniUploadDetail> alumniUploadDetails;



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

	public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public int getUploader() {
        return uploader;
    }

    public void setUploader(int uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public List<AlumniUploadDetail> getAlumniUploadDetails() {
        return alumniUploadDetails;
    }

    public void setAlumniUploadDetails(List<AlumniUploadDetail> alumniUploadDetails) {
        this.alumniUploadDetails = alumniUploadDetails;
    }
	public void addAlumniUploadDetails(AlumniUploadDetail uploadDetail) {
		if (this.alumniUploadDetails == null) this.alumniUploadDetails = new ArrayList<AlumniUploadDetail>();
		this.alumniUploadDetails.add(uploadDetail);
		uploadDetail.setAlumniUploadInfo(this);
		
	}

}
