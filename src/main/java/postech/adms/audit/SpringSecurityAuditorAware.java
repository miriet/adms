package postech.adms.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import postech.adms.web.security.UserInfo;

public class SpringSecurityAuditorAware implements AuditorAware<Long>{
	public Long getCurrentAuditor() {
		Long memberInnerId = null;
		try {
	    	UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			memberInnerId = userInfo.getId();
			
			
		} catch (Exception e) {
			try {
				memberInnerId = 0L;
			} catch (Exception ex) { }
		}
		if (memberInnerId == null) {
			memberInnerId = 0L;
		}
		return memberInnerId;
	}
}
