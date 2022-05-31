package postech.adms.repository.upload;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

import postech.adms.domain.upload.QAlumniUploadDetail;
import postech.adms.domain.upload.QAlumniUploadInfo;
import postech.adms.dto.upload.QUploadDetailListDto;
import postech.adms.dto.upload.QUploadListDto;
import postech.adms.dto.upload.UploadDetailListDto;
import postech.adms.dto.upload.UploadListDto;

public class AlumniUploadRepositoryImpl implements CustomUploadRepository{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<UploadListDto> readUploadList(Predicate predicate, Pageable pageable) {

		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);
		
		QAlumniUploadInfo qAlumniUploadInfo = QAlumniUploadInfo.alumniUploadInfo;

		List<UploadListDto> content = query.from(qAlumniUploadInfo).where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUploadListDto(qAlumniUploadInfo));

		Long total =  countQuery.from(qAlumniUploadInfo).where(predicate).count();
		return new PageImpl<UploadListDto>(content,pageable,total);
	}
	
	@Override
	public Page<UploadDetailListDto> readUploadDetailList(Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);
		
		QAlumniUploadDetail qaAlumniUploadDetail = QAlumniUploadDetail.alumniUploadDetail;

		List<UploadDetailListDto> content = query.from(qaAlumniUploadDetail).where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUploadDetailListDto(qaAlumniUploadDetail));

		Long total =  countQuery.from(qaAlumniUploadDetail).where(predicate).count();
		return new PageImpl<UploadDetailListDto>(content,pageable,total);
	}

}
