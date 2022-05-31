package postech.adms.repository.user;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import postech.adms.domain.user.QAdminGroup;
import postech.adms.domain.user.QAdminUser;
import postech.adms.dto.user.QUserListDto;
import postech.adms.dto.user.UserListDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserRepositoryImpl implements CustomUserRepository{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<UserListDto> readUsers(Predicate predicate, Pageable pageable) {

		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);
		
		QAdminUser user = QAdminUser.adminUser;
		QAdminGroup adminGroup = QAdminGroup.adminGroup;
		List<UserListDto> content = query.from(user).where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUserListDto(user));

		Long total =  countQuery.from(user).where(predicate).count();
		return new PageImpl<UserListDto>(content,pageable,total);
	}
	@Override
	public Page<UserListDto> readGroupsUsers(Predicate predicate, Pageable pageable) {

		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);
		
		QAdminUser user = QAdminUser.adminUser;
		QAdminGroup adminGroup = QAdminGroup.adminGroup;
		List<UserListDto> content = query.from(user).leftJoin(user.adminGroup(),adminGroup).where(predicate)
				.limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUserListDto(user,user.adminGroup().groupName));
		Long total =  countQuery.from(user).leftJoin(user.adminGroup(),adminGroup).where(predicate).count();
		return new PageImpl<UserListDto>(content,pageable,total);
	}
	
	@Override
	public Page<UserListDto> readUsersWithoutUserRoles(Predicate predicate, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		JPAQuery countQuery = new JPAQuery(entityManager);
	
		QAdminUser user = QAdminUser.adminUser;

		List<UserListDto> content = query.from(user)
				.where(predicate).limit(pageable.getPageSize()).offset(pageable.getOffset())
				.list(new QUserListDto(user,user.adminGroup().groupName));

		Long total =  countQuery.from(user).where(predicate).count();
		
		return new PageImpl<UserListDto>(content,pageable,total);
	}
	
}
