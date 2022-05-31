package postech.adms.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import postech.adms.common.exception.SystemException;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	/* 이름 */
	public static String getBaseName(String filePath){
		return FilenameUtils.getBaseName(filePath);
	}
	
	/* 이름 + 확장자 */
	public static String getName(String filePath){
		return FilenameUtils.getName(filePath);
	}
	
	public static String getPath(String filePath){
		return FilenameUtils.getFullPath(filePath);
	}
	
	/* 확장자 */
	public static String getExtension(String fileName){
		return FilenameUtils.getExtension(fileName);
	}
	
	public static boolean exists(String filePath) {
		boolean result = false;
		
		File file = new File(filePath);
		result = file.exists();
		
		return result;
	}
	
	public static void mkDir(String dirPath){
		File dir = new File(dirPath);
		dir.mkdirs();
	}
	
	public static Long sizeOf(String filePath){
		File file = new File(filePath);
		return FileUtils.sizeOf(file);
	}
	
	public static void deleteFile(String filePath){
		try {
			FileUtils.forceDelete(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	
	public static void deleteDirectory(String filePath){
		try {
			FileUtils.deleteDirectory(new File(filePath));
		} catch (IOException e) {
			throw new SystemException(e);
		}
	}
	
	public static void cleanDirectory(String filePath){
		try {
			FileUtils.cleanDirectory(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		deleteDirectory("/imagetest");
	}

}
