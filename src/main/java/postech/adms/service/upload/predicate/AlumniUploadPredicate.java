package postech.adms.service.upload.predicate;

import com.mysema.query.types.expr.BooleanExpression;
import postech.adms.domain.upload.AlumniUploadInfo;
import postech.adms.domain.upload.QAlumniUploadDetail;
import postech.adms.domain.upload.QAlumniUploadInfo;

public class AlumniUploadPredicate {
	private static final QAlumniUploadInfo ALUMNI_UPLOAD_INFO = QAlumniUploadInfo.alumniUploadInfo;
	private static final QAlumniUploadDetail ALUMNI_UPLOAD_DETAIL = QAlumniUploadDetail.alumniUploadDetail;

	public static BooleanExpression uploaderEqual(int uploaderId){
		return ALUMNI_UPLOAD_INFO.uploader.eq(uploaderId);
	}
	
	public static BooleanExpression infoIdEqual(AlumniUploadInfo alumniUploadInfo){
//	public static BooleanExpression infoIdEqual(long infoId){
//		return ALUMNI_UPLOAD_DETAIL.alumniUploadInfo().infoId.eq(infoId);
		return ALUMNI_UPLOAD_DETAIL.alumniUploadInfo().eq(alumniUploadInfo);
	}
	
	public static BooleanExpression groupIdEqual(Long groupId){
		return ALUMNI_UPLOAD_INFO.groupId.eq(groupId);
	}
	
	public static BooleanExpression deptCodeEqual(String deptCode){
		return ALUMNI_UPLOAD_INFO.deptCode.eq(deptCode);
	}

}
