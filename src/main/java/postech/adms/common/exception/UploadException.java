package postech.adms.common.exception;

import java.util.ArrayList;
import java.util.List;

public class UploadException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private List<String> uploadFilePaths = new ArrayList<String>();
	
	public UploadException(Exception cause) {
        super(cause) ;
    }
	
	public UploadException(Exception cause,List<String> uploadFilePaths) {
		super(cause);
		this.setUploadFilePaths(uploadFilePaths);
	}
	
    public UploadException(String message) {
        super(message) ;
    }
    
    public UploadException(String message,List<String> uploadFilePaths) {
        super(message) ;
        this.setUploadFilePaths(uploadFilePaths);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause) ;
    }

	public List<String> getUploadFilePaths() {
		return uploadFilePaths;
	}

	public void setUploadFilePaths(List<String> uploadFilePaths) {
		this.uploadFilePaths = uploadFilePaths;
	}
}
