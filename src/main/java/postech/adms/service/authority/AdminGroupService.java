package postech.adms.service.authority;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import postech.adms.common.util.EntityUtil;
import postech.adms.domain.user.AdminGroup;
import postech.adms.domain.user.AdminUser;
import postech.adms.repository.user.AdminGroupRepository;
import postech.adms.service.authority.predicate.AdminGroupPredicate;
import postech.adms.service.user.UserService;

@Service("admsAdminGroupService")
@Transactional("transactionManager")
public class AdminGroupService {
	@Resource
	protected AdminGroupRepository adminGroupRepository;
	
	@Resource(name="admsUserService")
	protected UserService userService;
	
	/**
	 * 전체 그룹 리스트
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<AdminGroup> find(Map<String,String> param, Pageable pageable){
		return adminGroupRepository.findAll(AdminGroupPredicate.searchPredicate(param), pageable);
	}
	/**
	 * 해당 그룹 찾기
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public AdminGroup findOne(Long id){
		return adminGroupRepository.findOne(id);
	}
	/**
	 * 회원정보 등록화면,변경화면에 사용
	 * @param isDel
	 * @return
	 */
	public List<AdminGroup> findByIsDel(boolean isDel) {
		return adminGroupRepository.findByIsDel(isDel); 
	}
	/**
	 * 그룹 등록
	 * @param group
	 */
	public void insert(AdminGroup group){
		adminGroupRepository.save(group);
	}
	
	/**
	 * 그룹 수정
	 * @param dto
	 */
	public void update(AdminGroup dto){
		AdminGroup adminGroup = findOne(dto.getGroupId());
		EntityUtil.transformDtoToModel(dto, adminGroup);
	}
	
	/**
	 * 그룹에 사용자 추가 
	 * @param groupId
	 * @param userIds
	 */
	public void addUserToGroup(Long groupId,long[] userIds){
		AdminGroup adminGroup = findOne(groupId);
		for (long userId : userIds) {
			AdminUser user = userService.findOne(userId);
			user.setAdminGroup(adminGroup);
		}
	}
	/**
	 * 그룹에 속한 사용자 제거
	 * @param user
	 */
	public void removeUser(long userId) {
		AdminUser data = userService.findOne(userId);
		data.setAdminGroup(null);
	}
	
	/**
	 * 현재 사용되고 있지 않는 메서드(그룹 삭제)
	 * @param id
	 */
	public void delete(Long id){
		AdminGroup adminGroup = findOne(id);
		adminGroup.setIsDel(true);
	}
/*
	public void setAuth(Long groupId, String[] selectedPermission) {
		AdminGroup adminGroup = findOne(groupId);
		//Set<AdminPermission> rolePermissions = (Set<AdminPermission>)adminPermissionRepository.findAll(QAdminPermission.adminPermission.id.in(Arrays.asList(selectedPermission)));
		//List<AdminPermission> list = (List<AdminPermission>)adminPermissionRepository.findAll(QAdminPermission.adminPermission.id.in(Arrays.asList(selectedPermission)));
		Set<AdminPermission> adminPermissions = Sets.newConcurrentHashSet(adminPermissionRepository.findAll(QAdminPermission.adminPermission.permissionCode.in(Arrays.asList(selectedPermission))));
		System.out.println(adminPermissions);
		adminGroup.setGroupPermissions(adminPermissions);
	}
*/	
}
