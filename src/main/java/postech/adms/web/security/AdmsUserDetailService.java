package postech.adms.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import postech.adms.domain.user.AdminUser;
import postech.adms.service.user.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 *	스프링 시큐리티에서 사용자 정보를 처리하기 위한 서비스 클래스
 *	사용자가 입력한 ID, Password를 이용해서 User 객체를 가져온다
 *	인증 실패시 실패횟수를 저장하고 5회이상 실패시 Lock을 건다 <-- lock 은 AuthenticationFailHandler에서 처리
 * 	인증 성공시 User 객체를 UserInfo에 복사하고 UserInfo를 세션에 저장한다 <-- AuthenticationSuccessHandler에서 처리
 *
 * 	참고 : http://comtk.tistory.com/20
 * */

@Component("admsUserDetailService")
public class AdmsUserDetailService implements UserDetailsService {
	@Resource
	protected UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// AdminUser user = userService.findByUserName(userName);
		AdminUser user = userService.findByLoginId(userName);

		if (user == null) {
			throw new UsernameNotFoundException("로그인 정보가 정확하지 않습니다!");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<String> permissionNames = new ArrayList<String>();

		/*
		for(AdminPermission permission : user.getAdminGroup().getGroupPermissions()){
			authorities.add(new SimpleGrantedAuthority(permission.getPermissionCode()));
			permissionNames.add(permission.getPermissionCode());
		}
		 */

		permissionNames.add(user.getAdminGroup().getGroupName());
		UserInfo userInfo = new UserInfo(user.getLoginId(), user.getPassword(), true, true, true, true, authorities);

		userInfo.setId(user.getId());
		userInfo.setEmail(user.getEmail());
		userInfo.setName(user.getName());
		userInfo.setUserName(user.getLoginId());
		userInfo.setSaltKey("POSTECH");
//		userInfo.setSiteId(user.getSiteId());
		userInfo.setPermissionNames(permissionNames);
		userInfo.setGroupId(user.getAdminGroup().getGroupId());
		userInfo.setDeptCode(user.getDeptCode());

		return userInfo;
	}
}
