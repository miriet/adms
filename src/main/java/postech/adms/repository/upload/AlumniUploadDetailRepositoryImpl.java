package postech.adms.repository.upload;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.QAlumniMember;
import postech.adms.domain.upload.QAlumniUploadDetail;
import postech.adms.dto.upload.QUploadDetailListDto;
import postech.adms.dto.upload.UploadDetailListDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AlumniUploadDetailRepositoryImpl implements CustomUploadDetailRepository{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<UploadDetailListDto> readUpdateDetailListByMemberAndUpload(Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniUploadDetail qaAlumniUploadDetail = QAlumniUploadDetail.alumniUploadDetail;
		QAlumniMember alumniMember = QAlumniMember.alumniMember;

		List<UploadDetailListDto> content = query.from(qaAlumniUploadDetail)
				.innerJoin(alumniMember)
				.on(qaAlumniUploadDetail.name.eq(alumniMember.name), qaAlumniUploadDetail.birthdayOfficial.eq(alumniMember.birthdayOfficial))
				.where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUploadDetailListDto(qaAlumniUploadDetail));

		Long total =  countQuery.from(qaAlumniUploadDetail).where(predicate).count();
		return new PageImpl<UploadDetailListDto>(content,pageable,total);
	}

	@Override
	public Page<AlumniMember> readUpdateDetailListByMemberMinusUpload(Long infoId,Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniUploadDetail qaAlumniUploadDetail = QAlumniUploadDetail.alumniUploadDetail;
		QAlumniMember alumniMember = QAlumniMember.alumniMember;

		List<AlumniMember> content = query.from(alumniMember)
				.leftJoin(qaAlumniUploadDetail)
				.on(alumniMember.name.eq(qaAlumniUploadDetail.name), alumniMember.birthdayOfficial.eq(qaAlumniUploadDetail.birthdayOfficial), qaAlumniUploadDetail.alumniUploadInfo().infoId.eq(infoId))
				.where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QAlumniMember(alumniMember));

		Long total =  countQuery.from(qaAlumniUploadDetail)
				.leftJoin(alumniMember)
				.on(qaAlumniUploadDetail.name.eq(alumniMember.name), qaAlumniUploadDetail.birthdayOfficial.eq(alumniMember.birthdayOfficial), qaAlumniUploadDetail.alumniUploadInfo().infoId.eq(infoId))
				.where(predicate).count();
		return new PageImpl<AlumniMember>(content,pageable,total);
	}

	@Override
	public Page<UploadDetailListDto> readUpdateDetailListByUploadMinusMember(Long infoId,Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniUploadDetail qaAlumniUploadDetail = QAlumniUploadDetail.alumniUploadDetail;
		QAlumniMember alumniMember = QAlumniMember.alumniMember;

		List<UploadDetailListDto> content = query.from(qaAlumniUploadDetail)
				.leftJoin(alumniMember)
				.on(qaAlumniUploadDetail.name.eq(alumniMember.name), qaAlumniUploadDetail.birthdayOfficial.eq(alumniMember.birthdayOfficial), qaAlumniUploadDetail.alumniUploadInfo().infoId.eq(infoId))
				.where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUploadDetailListDto(qaAlumniUploadDetail));

		Long total =  countQuery.from(qaAlumniUploadDetail)
				.leftJoin(alumniMember)
				.on(qaAlumniUploadDetail.name.eq(alumniMember.name), qaAlumniUploadDetail.birthdayOfficial.eq(alumniMember.birthdayOfficial), qaAlumniUploadDetail.alumniUploadInfo().infoId.eq(infoId))
				.where(predicate).count();
		return new PageImpl<UploadDetailListDto>(content,pageable,total);
	}
}
