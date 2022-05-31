package postech.adms.common.upload;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import postech.adms.domain.upload.AlumniUploadInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component("admsFileUploader")
public class AdmsFileUploader{
//	@Value("#{cms['cms.amazon.bucket.name']}")
	protected String AWS_BUCKETNAME = "spottoon";

//	@Value("#{cms['cms.amazon.access.key']}")
	protected String AWS_ACCESS_KEY = "AKIAIBVNBSSO65OGXTQA";

//	@Value("#{cms['cms.amazon.secret.key']}")
	protected String AWS_SECRET_KEY = "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";

	private static final String filePath = "/data/adms/upload/";

	public AlumniUploadInfo parseUploadFileInfo(HttpServletRequest request){

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;

		AlumniUploadInfo alumniUploadInfo = new AlumniUploadInfo();

		File file = new File(filePath);
		if(file.exists() == false){
			file.mkdirs();
		}

		while(iterator.hasNext()){
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = System.currentTimeMillis() + "_" + originalFileName;

				file = new File(filePath + storedFileName);
				try {
					multipartFile.transferTo(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

				alumniUploadInfo.setFilePath(filePath);
				alumniUploadInfo.setFileName(originalFileName);
				alumniUploadInfo.setStoredFileName(storedFileName);
			}
		}
		return alumniUploadInfo;
	}


//	@Override
	public void delete(List<String> keys){
		AmazonS3 amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(AWS_BUCKETNAME);
		List<KeyVersion> keyVersions = new ArrayList<KeyVersion>();
		
		for(String key : keys){
			keyVersions.add(new KeyVersion(key));
		}
		
		multiObjectDeleteRequest.setKeys(keyVersions);
		
		amazonS3Client.deleteObjects(multiObjectDeleteRequest);
	}
	
//	@Override
	public void delete(String key){
		AmazonS3 amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(AWS_BUCKETNAME);
		List<KeyVersion> keyVersions = new ArrayList<KeyVersion>();
		keyVersions.add(new KeyVersion(key));
		multiObjectDeleteRequest.setKeys(keyVersions);
		amazonS3Client.deleteObjects(multiObjectDeleteRequest);
	}
}
