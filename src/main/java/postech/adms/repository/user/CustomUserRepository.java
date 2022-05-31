package postech.adms.repository.user;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import postech.adms.dto.user.UserListDto;

public interface CustomUserRepository {
	public Page<UserListDto> readUsers(Predicate predicate, Pageable pageable);
	public Page<UserListDto> readGroupsUsers(Predicate predicate, Pageable pageable);
	public Page<UserListDto> readUsersWithoutUserRoles(Predicate predicate, Pageable pageable);
}
