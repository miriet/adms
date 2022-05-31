package postech.adms.service.member.predicate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.member.QAlumniMember;
import postech.adms.domain.member.QDegree;
import postech.adms.domain.upload.QAlumniUploadDetail;

//import com.mysema.query.jpa.JPASubQuery;

public class MemberPredicate {
	private static final QAlumniUploadDetail ALUMNI_UPLOAD_DETAIL = QAlumniUploadDetail.alumniUploadDetail;
	private static final QAlumniMember ALUMNI_MEMBER = QAlumniMember.alumniMember;

	// ---------------------------------------------------- //
	public static BooleanExpression nameLike(String name) {
		return nameLike(ALUMNI_MEMBER, name);
	}

	public static BooleanExpression nameLike(QAlumniMember alumniMember, String name) {
		return alumniMember.name.like("%" + name + "%");
	}

	// ---------------------------------------------------- //
	public static BooleanExpression userNameLike(String userName) {
		return userNameLike(ALUMNI_MEMBER, userName);
	}

	public static BooleanExpression userNameLike(QAlumniMember alumniMember, String userName) {
		return alumniMember.name.like("%" + userName + "%");
	}

	// ---------------------------------------------------- //
	public static BooleanExpression isDelEqual(boolean isDel) {
		return isDelEqual(ALUMNI_MEMBER, isDel);
	}

	public static BooleanExpression isDelEqual(String isDel) {
		return isDelEqual(ALUMNI_MEMBER, BooleanUtils.toBoolean(isDel));
	}

	public static BooleanExpression isDelEqual(QAlumniMember alumniMember, boolean isDel) {
		// return alumniMember.isDel.eq(isDel);
		return alumniMember.isAlive.eq(true);
	}
	// ---------------------------------------------------- //

	public static Predicate searchPredicate(Map<String, String> param) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QDegree degree = QDegree.degree;

		// 회원명
		if (!StringUtils.isEmpty(param.get("name"))) {
			booleanBuilder.and(nameLike(param.get("name")));
		}

		// 입학년도
		if (!StringUtils.isEmpty(param.get("searchEntranceYear"))) {
			 String entYear = param.get("searchEntranceYear");
			 booleanBuilder.and(degree.entranceYear.like(entYear + "%"));
		}

		// 학과
		if (!StringUtils.isEmpty(param.get("searchDept"))) {
			 String deptCode = param.get("searchDept");
			 booleanBuilder.and(degree.deptCode.eq(deptCode));
		}

		// 학위
		if (!StringUtils.isEmpty(param.get("searchDegreeType"))) {
			 String degreeType = param.get("searchDegreeType");
			 booleanBuilder.and(degree.degreeType.eq(getDegreeType(degreeType)));
		}

		// 생년월일
		if (!StringUtils.isEmpty(param.get("birthDay"))) {
			 String birthDay = param.get("birthDay");
			 booleanBuilder.and(ALUMNI_MEMBER.birthdayOfficial.eq(birthDay));
		}

		// 회원명(API 요청시)
		if (!StringUtils.isEmpty(param.get("memberName"))) {
			 String memberName = param.get("memberName");
			 booleanBuilder.and(ALUMNI_MEMBER.name.eq(memberName));
		}

        // 수정일자 (FROM)
        if (!StringUtils.isEmpty(param.get("fromDate"))) {
            String strFromDate = param.get("fromDate");
            Date fromDate = null;
            try {
                fromDate = new SimpleDateFormat("yyyyMMdd").parse(strFromDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            booleanBuilder.and(ALUMNI_MEMBER.auditable().dateUpdated.goe(fromDate));
        }

        // 수정일자 (TO)
        if (!StringUtils.isEmpty(param.get("toDate"))) {
            String strToDate = param.get("toDate");
            Date toDate = null;
            try {
                toDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(strToDate + "235959");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            booleanBuilder.and(ALUMNI_MEMBER.auditable().dateUpdated.loe(toDate));
        }

		/*
		if (!StringUtils.isEmpty(param.get("loginId"))) {
			booleanBuilder.and(userNameLike(param.get("loginId")));
		}

		if (!StringUtils.isEmpty(param.get("isDel"))) {
			booleanBuilder.and(isDelEqual(param.get("isDel")));
		} else {
			booleanBuilder.and(isDelEqual(false));
		}
        */

		return booleanBuilder;
	}

	/**
	 * 2018.03.23 박재현
	 * 동창 조회에서 사용
	 * @param param
	 * @return
	 */
	public static Predicate searchAlumnusPredicate(Map<String, String> param) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QDegree degree = QDegree.degree;

		// 학위
		if (!StringUtils.isEmpty(param.get("searchDegreeType"))) {
			 String degreeType = param.get("searchDegreeType");
			 booleanBuilder.and(degree.degreeType.eq(getDegreeType(degreeType)));

			if (!degreeType.equals("선택") && degreeType != "선택") {
				String entranceYear = param.get("ENTRANCE_YEAR");
				String deptCode = param.get("DEPT_CODE");
				// Long memberId = Long.parseLong(param.get("MEMBER_ID"));

				if (entranceYear == null) entranceYear = "";
				if (deptCode == null) deptCode = "";

				booleanBuilder.and(degree.entranceYear.eq(entranceYear));
				booleanBuilder.and(degree.deptCode.eq(deptCode));
				// booleanBuilder.and(ALUMNI_MEMBER.id.ne(memberId));
			}
		}

		return booleanBuilder;
	}

	public static Predicate searchUploadDetailMinusConditionPredicate(Map<String, String> param) {
		BooleanBuilder booleanBuilder = (BooleanBuilder) MemberPredicate.searchPredicate(param);
		booleanBuilder.and(ALUMNI_UPLOAD_DETAIL.name.isNull().and(ALUMNI_UPLOAD_DETAIL.birthdayOfficial.isNull()));

		return booleanBuilder;
	}

	private static DegreeType getDegreeType(String degreeType) {
	   DegreeType result = null;

		switch (degreeType) {
		case "BS":
            result = DegreeType.BS;
			break;

		case "MS":
            result = DegreeType.MS;
			break;

		case "PhD":
            result = DegreeType.PhD;
			break;

		case "UNITY":
            result = DegreeType.UNITY;
			break;

		case "PAMTIP":
            result = DegreeType.PAMTIP;
			break;

		}

		if (result == null) {
			result = DegreeType.NULL;
		}

	   return result;
   }

	/*
	public static Predicate userWithoutUserRole(Long siteId) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		JPASubQuery subQuery = new JPASubQuery();
		booleanBuilder.and(isDelEqual(false));
		// booleanBuilder.and(siteIdEqual(siteId));
		// booleanBuilder
		// .and(ALUMNI_MEMBER.id.notIn(subQuery.from(ALUMNI_MEMBER.getAdminUser(),
		// adminRole).distinct().list(ALUMNI_MEMBER.id)));
		return booleanBuilder;
	}
    */
}
