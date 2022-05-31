package postech.adms.service.department;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.adms.domain.department.Department;
import postech.adms.repository.department.DepartmentRepository;

import javax.annotation.Resource;
import java.util.List;

@Service("departmentService")
@Transactional("transactionManager")
public class DepartmentService {
	@Resource
	protected DepartmentRepository departmentRepository;

	public List<Department> findByIsDel(boolean isDel) {
		return departmentRepository.findByIsDel(isDel);
	}

	/**
	 * 해당 학과 찾기
	 * 
	 * @param id
	 * @return
	 */
	public Department findOne(Long id) {
		return departmentRepository.findOne(id);
	}
}
