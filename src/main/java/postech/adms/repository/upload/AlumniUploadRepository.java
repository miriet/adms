package postech.adms.repository.upload;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.upload.AlumniUploadInfo;

import java.util.List;

public interface AlumniUploadRepository extends JpaQueryDslBaseRepository<AlumniUploadInfo,Long>, CustomUploadRepository{
	public List<AlumniUploadInfo> findByUploader(int uploaderId);
	public AlumniUploadInfo findByInfoId(Long infoId);
}
