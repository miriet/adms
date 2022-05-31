package postech.adms.repository.department;

import java.util.List;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.department.Department;

public interface DepartmentRepository extends JpaQueryDslBaseRepository<Department, Long> {
	List<Department> findByIsDel(boolean isDel);

}
