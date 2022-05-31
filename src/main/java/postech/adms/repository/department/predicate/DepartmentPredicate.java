package postech.adms.repository.department.predicate;

import org.apache.commons.lang3.BooleanUtils;

import com.mysema.query.types.expr.BooleanExpression;

import postech.adms.domain.department.QDepartment;

public class DepartmentPredicate {
	private static final QDepartment department = QDepartment.department;

	public static BooleanExpression isDelEqual(boolean isDel) {
		return isDelEqual(department, isDel);
	}

	public static BooleanExpression isDelEqual(String isDel) {
		return isDelEqual(department, BooleanUtils.toBoolean(isDel));
	}

	public static BooleanExpression isDelEqual(QDepartment dept, boolean isDel) {
		return dept.isDel.eq(isDel);
	}

}
