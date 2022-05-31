package postech.adms.repository.upload;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import postech.adms.domain.upload.AlumniUploadInfo;
import postech.adms.dto.upload.UploadDetailListDto;
import postech.adms.dto.upload.UploadListDto;

public interface CustomUploadRepository {
	public Page<UploadListDto> readUploadList(Predicate predicate, Pageable pageable);
	public Page<UploadDetailListDto> readUploadDetailList(Predicate predicate, Pageable pageable);
}
