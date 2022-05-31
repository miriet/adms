package postech.adms.common.upload;

import java.io.InputStream;
import java.util.List;

public interface CmsUploader {
//	public void upload(ThumbnailDto thumbnailDto);
	public void upload(String filePath, long size, InputStream inputStream);
	public void delete(List<String> keys);
	public void delete(String key);
}
