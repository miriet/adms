package postech.adms.repository.member;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.QAlumniMember;
import postech.adms.domain.member.QDegree;
import postech.adms.dto.member.MemberListDto;
import postech.adms.dto.member.QMemberListDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MemberRepositoryImpl implements CustomMemberRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<MemberListDto> readMembers(Predicate predicate, Pageable pageable, boolean isDgreeJoin) {

		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;
		QDegree degree = QDegree.degree;
		
		if (isDgreeJoin) {
			List<MemberListDto> content = query.from(member)
	                                      .innerJoin(member.degrees, degree)
	                                      .where(predicate).distinct()
	                                      .limit(pageable.getPageSize()).offset(pageable.getOffset())
	                                      .list(new QMemberListDto(member));
	
	
			Long total = countQuery.from(member)
					     .innerJoin(member.degrees, degree)
					     .where(predicate).distinct()
					     .count();
			return new PageImpl<MemberListDto>(content, pageable, total);
		} else {
			List<MemberListDto> content = query.from(member)
                    .where(predicate)
                    .limit(pageable.getPageSize()).offset(pageable.getOffset())
                    .list(new QMemberListDto(member));

			Long total = countQuery.from(member)
			             .where(predicate)
			             .count();
			
			return new PageImpl<MemberListDto>(content, pageable, total);
		}
	}

	@Override
	public Page<AlumniMember> readAlumniMembers(Predicate predicate, Pageable pageable, boolean isDgreeJoin) {

		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;
		QDegree degree = QDegree.degree;

		if (isDgreeJoin) {
			List<AlumniMember> content = query.from(member)
					.innerJoin(member.degrees, degree)
					.where(predicate)
					.limit(pageable.getPageSize()).offset(pageable.getOffset())
					.list(new QAlumniMember(member));


			Long total = countQuery.from(member)
					.innerJoin(member.degrees, degree)
					.where(predicate)
					.count();
			return new PageImpl<AlumniMember>(content, pageable, total);
		} else {
			List<AlumniMember> content = query.from(member)
					.where(predicate)
					.limit(pageable.getPageSize()).offset(pageable.getOffset())
					.list(new QAlumniMember(member));

			Long total = countQuery.from(member)
					.where(predicate)
					.count();

			return new PageImpl<AlumniMember>(content, pageable, total);
		}
	}

	@Override
	public List<AlumniMember> readMemberList(Predicate predicate, boolean isDgreeJoin) {

		JPAQuery query = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;
		QDegree degree = QDegree.degree;
		
		if (isDgreeJoin) {
			List<AlumniMember> content = query.from(member)
                                         .innerJoin(member.degrees, degree)
                                         .where(predicate).distinct()
                                         .list(new QAlumniMember(member));

            return content;
			
		} else {
			List<AlumniMember> content = query.from(member)
                                         .where(predicate)
                                         .list(new QAlumniMember(member));

            return content;
		}
	}

	@Override
	public Page<MemberListDto> readMembersWithoutUserRoles(Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;

		List<MemberListDto> content = query.from(member)
				                      .where(predicate)
				                      .limit(pageable.getPageSize())
				                      .offset(pageable.getOffset())
				                      .list(new QMemberListDto(member));

		Long total = countQuery.from(member)
				     .where(predicate)
				     .count();

		return new PageImpl<MemberListDto>(content, pageable, total);
		
		
	}
	/**
	 * 2018.03.23 박재현 
	 * 동창 조회에서 사용
	 */
	@Override
	public Page<MemberListDto> readAlumMembers(Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;
		QDegree degree = QDegree.degree;
			
			List<MemberListDto> content = query.from(member)
	                                      .innerJoin(member.degrees, degree)
	                                      .where(predicate)
	                                      .limit(pageable.getPageSize()).offset(pageable.getOffset())
	                                      .list(new QMemberListDto(member));
	
	
			Long total = countQuery.from(member)
					     .innerJoin(member.degrees, degree)
					     .where(predicate)
					     .count();
			return new PageImpl<MemberListDto>(content, pageable, total);

	}

	@Override
	public List<AlumniMember> readAlumMembersList(Predicate predicate) {
		JPAQuery query = new JPAQuery(entityManager);

		QAlumniMember member = QAlumniMember.alumniMember;
		QDegree degree = QDegree.degree;
		
		List<AlumniMember> content = query.from(member)
                                     .innerJoin(member.degrees, degree)
                                     .where(predicate)
                                     .list(new QAlumniMember(member));

        return content;
			

	}
	
}
