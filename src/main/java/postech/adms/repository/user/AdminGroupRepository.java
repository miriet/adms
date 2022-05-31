package postech.adms.repository.user;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.user.AdminGroup;

import java.util.List;

/**
 * Created by miriet on 2017. 3. 26..
 */
public interface AdminGroupRepository extends JpaQueryDslBaseRepository<AdminGroup,Long>{
        List<AdminGroup> findByIsDel(boolean isDel);
}
