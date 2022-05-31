package postech.adms.repository.upload;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.upload.AlumniUploadDetail;
import postech.adms.dto.upload.UploadDetailListDto;

/**
 * Created by miriet on 2017. 5. 16..
 */
public interface AlumniUploadDetailRepository extends JpaQueryDslBaseRepository<AlumniUploadDetail,Long>, CustomUploadDetailRepository {

    public UploadDetailListDto findByNameAndBirthdayOfficialAndAlumniUploadInfo_InfoId(String name, String birthday, Long infoId);

}
