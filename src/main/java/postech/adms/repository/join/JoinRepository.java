package postech.adms.repository.join;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.user.AdminUser;

public interface JoinRepository extends JpaQueryDslBaseRepository<AdminUser,Long>{
	public AdminUser findByLoginId(String loginId);
	public AdminUser findByNameAndBirthday(String name, String birthday);
	public AdminUser findByDi(String di);
}
