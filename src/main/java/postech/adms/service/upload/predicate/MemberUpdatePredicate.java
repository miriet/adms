package postech.adms.service.upload.predicate;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.util.StringUtils;
import postech.adms.domain.member.QAlumniMember;
import postech.adms.domain.member.QDegree;
import postech.adms.domain.upload.QAlumniUploadDetail;

import java.util.Map;

//import com.mysema.query.jpa.JPASubQuery;

public class MemberUpdatePredicate {
	private static final QAlumniUploadDetail ALUMNI_UPLOAD_DETAIL = QAlumniUploadDetail.alumniUploadDetail;
	private static final QAlumniMember ALUMNI_MEMBER = QAlumniMember.alumniMember;

	/* upload detail 정보에서 조회할 때 UploadInfo 필터링 조건 설정 */
	private static BooleanExpression infoIdEqual(String infoId) {
		return infoIdEqual(ALUMNI_UPLOAD_DETAIL, infoId);
	}

	private static BooleanExpression infoIdEqual(QAlumniUploadDetail alumniUploadDetail, String infoId) {

		return alumniUploadDetail.alumniUploadInfo().infoId.eq(Long.parseLong(infoId));
	}

	// ---------------------------------------------------- //
	private static BooleanExpression nameLike(String name) {
		return nameLike(ALUMNI_UPLOAD_DETAIL, name);
	}

	private static BooleanExpression nameLike(QAlumniUploadDetail alumniUploadDetail, String name) {
		return alumniUploadDetail.name.like("%" + name + "%");
	}

	/* upload detail 정보에서 조회할 때 입학년도 필터링 조건 설정 */
	private static BooleanExpression entranceYearlike(String entranceYear) {
		return entranceYearlike(ALUMNI_UPLOAD_DETAIL, entranceYear);
	}

	private static BooleanExpression entranceYearlike(QAlumniUploadDetail alumniUploadDetail, String entranceYear) {
		return alumniUploadDetail.bsEntranceYear.like("%" + entranceYear + "%")
				.or(alumniUploadDetail.msEntranceYear.like("%" + entranceYear + "%"))
				.or(alumniUploadDetail.phdEntranceYear.like("%" + entranceYear + "%"))
				.or(alumniUploadDetail.unityEntranceYear.like("%" + entranceYear + "%"))
				.or(alumniUploadDetail.pamtipEntranceYear.like("%" + entranceYear + "%"));
	}

	/* upload detail 정보에서 조회할 때 학과(전공) 필터링 조건 설정 */
	private static BooleanExpression deptEqual(String dept) {
		return deptEqual(ALUMNI_UPLOAD_DETAIL, dept);
	}

	private static BooleanExpression deptEqual(QAlumniUploadDetail alumniUploadDetail, String dept) {
		return alumniUploadDetail.bsDept.eq(dept)
				.or(alumniUploadDetail.msDept.eq(dept))
				.or(alumniUploadDetail.phdDept.eq(dept))
				.or(alumniUploadDetail.unityDept.eq(dept))
				.or(alumniUploadDetail.pamtipDept.eq(dept));
	}

	/* upload detail 정보에서 조회할 때 학위 필터링 조건 설정
	*  졸업연도가 있으면 학위취득한 것으로 인정 */
	private static BooleanExpression degreeTypeEqual(String degreeType) {
		return degreeTypeEqual(ALUMNI_UPLOAD_DETAIL, degreeType);
	}

	private static BooleanExpression degreeTypeEqual(QAlumniUploadDetail alumniUploadDetail, String degreeType) {
		BooleanExpression result = null;
		switch (degreeType){
			case "BS"		: result = alumniUploadDetail.bsGraduationYear.gt("1986"); break;
			case "MS"		: result = alumniUploadDetail.msGraduationYear.gt("1986"); break;
			case "PhD"		: result = alumniUploadDetail.phdGraduationYear.gt("1986"); break;
			case "UNITY"	: result = alumniUploadDetail.unityGraduationYear.gt("1986"); break;
			case "PAMTIP"	: result = alumniUploadDetail.pamtipGraduationYear.gt("1986"); break;
			default: result = alumniUploadDetail.name.isNotNull();break;
		}
		return result;
	}

	// ---------------------------------------------------- //

	public static Predicate searchUploadDetailPredicate(Map<String, String> param) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QDegree degree = QDegree.degree;

		// 회원명
		if (param.get("infoId")!=null && param.get("infoId") !="") {
			booleanBuilder.and(infoIdEqual(param.get("infoId")));
		} else {
			booleanBuilder.and(infoIdEqual("0"));
		}

		// 회원명
		if (!StringUtils.isEmpty(param.get("name"))) {
			booleanBuilder.and(nameLike(param.get("name")));
		}

		// 입학년도
		if (!StringUtils.isEmpty(param.get("searchEntranceYear"))) {
			String entYear = param.get("searchEntranceYear");
			booleanBuilder.and(entranceYearlike(entYear + "%"));
		}


		// 학과
		if (!StringUtils.isEmpty(param.get("searchDept"))) {
			String deptCode = param.get("searchDept");
			booleanBuilder.and(deptEqual(deptCode));

		}

		// 학위
		if (!StringUtils.isEmpty(param.get("searchDegreeType"))) {
			String degreeType = param.get("searchDegreeType");
			booleanBuilder.and(degreeTypeEqual(degreeType));

		}

		return booleanBuilder;
	}

	public static Predicate searchUploadDetailMinusConditionPredicate(Map<String, String> param) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if("4".equals(param.get("viewType"))){				// Member - Upload
			booleanBuilder.and(ALUMNI_UPLOAD_DETAIL.name.isNull().and(ALUMNI_UPLOAD_DETAIL.birthdayOfficial.isNull()));
		} else if ("5".equals(param.get("viewType"))) {		// Upload - Member
			booleanBuilder.and(ALUMNI_MEMBER.name.isNull().and(ALUMNI_MEMBER.birthdayOfficial.isNull()));
		}

		// 회원명
		if (!StringUtils.isEmpty(param.get("name"))) {
			booleanBuilder.and(nameLike(param.get("name")));
		}

		// 입학년도
		if (!StringUtils.isEmpty(param.get("searchEntranceYear"))) {
			String entYear = param.get("searchEntranceYear");
			booleanBuilder.and(entranceYearlike(entYear + "%"));
		}


		// 학과
		if (!StringUtils.isEmpty(param.get("searchDept"))) {
			String deptCode = param.get("searchDept");
			booleanBuilder.and(deptEqual(deptCode));
		}

		// 학위
		if (!StringUtils.isEmpty(param.get("searchDegreeType"))) {
			String degreeType = param.get("searchDegreeType");
			booleanBuilder.and(degreeTypeEqual(degreeType));
		}

		return booleanBuilder;
	}
}
