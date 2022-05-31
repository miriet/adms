package postech.adms.repository.upload;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import postech.adms.domain.member.AlumniMember;
import postech.adms.dto.upload.UploadDetailListDto;

public interface CustomUploadDetailRepository {
    public  Page<UploadDetailListDto> readUpdateDetailListByMemberAndUpload(Predicate predicate, Pageable pageable);
    public Page<AlumniMember> readUpdateDetailListByMemberMinusUpload(Long infoId, Predicate predicate, Pageable pageable);
    public Page<UploadDetailListDto> readUpdateDetailListByUploadMinusMember(Long infoId, Predicate predicate, Pageable pageable);
}
