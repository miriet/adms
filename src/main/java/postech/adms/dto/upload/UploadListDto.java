package postech.adms.dto.upload;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mysema.query.annotations.QueryProjection;

import postech.adms.domain.upload.AlumniUploadInfo;

/**
 * Created by miriet on 2017. 5. 6..
 */
public class UploadListDto implements Serializable {

    private static final long serialVersionUID = -8780498400106290996L;

    private Long infoId;
    private String uploader;
    private String uploaderName;
    private String title;
    private Date uploadDate;
    private String fileName;

    @QueryProjection
    public UploadListDto(AlumniUploadInfo alumniUploadInfo) {
        this.infoId = alumniUploadInfo.getInfoId();
        this.uploader = alumniUploadInfo.getAdminUser().getLoginId();
        this.uploaderName = alumniUploadInfo.getAdminUser().getName();
        this.title = alumniUploadInfo.getTitle();
        this.uploadDate = alumniUploadInfo.getUploadDate();
        this.fileName = alumniUploadInfo.getFileName();
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadDate() {
		String data = "";
		if(uploadDate != null && !uploadDate.equals("") ) data=  DateFormatUtils.ISO_DATE_FORMAT.format(uploadDate);
		return data;
        // return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

	public String getUploaderName() {
		return uploaderName;
	}

	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
    
    
}
