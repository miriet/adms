package postech.adms.repository.user;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.user.AdminUser;

public interface UserRepository extends JpaQueryDslBaseRepository<AdminUser,Long>, CustomUserRepository{
	public AdminUser findByName(String name);
	public AdminUser findByLoginId(String loginId);
}
