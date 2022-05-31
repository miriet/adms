package postech.adms.web.filter;

import postech.adms.common.classloader.ThreadLocalManager;
import postech.adms.common.context.CmsRequestContext;
import postech.adms.service.menu.MenuService;
import postech.adms.web.security.UserInfo;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

@Component("admsRequestProcessor")
public class AdmsRequestProcessor implements AdmsWebRequestProcessor {

	@Resource(name = "admsMenuService")
	protected MenuService menuService;

	@Resource(name = "messageSourceAccessor")
	protected MessageSourceAccessor messageSourceAccessor;

	@Override
	public void process(WebRequest request) {
		CmsRequestContext src = CmsRequestContext.getCmsRequestContext();

		if (src == null) {
			src = new CmsRequestContext();
			CmsRequestContext.setCmsRequestContext(src);
		}

		src.setWebRequest(request);
		src.setMessageSourceAccessor(messageSourceAccessor);
		src.setCurrentMenu(menuService.findByMenuPath(src.getRequestURIWithoutContext()));

		if (src.getCurrentMenu() != null) {
			src.setMenuPath(src.getCurrentMenu().getFullPath());
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.getPrincipal().equals("anonymousUser")) {
			src.setUser((UserInfo) authentication.getPrincipal());
		}

		if (src.getCurrentMenu() != null) {
			request.setAttribute("menuTitle", src.getCurrentMenu().getName(), RequestAttributes.SCOPE_REQUEST);
		} else {
			request.setAttribute("menuTitle", "", RequestAttributes.SCOPE_REQUEST);
		}
	}

	@Override
	public void postProcess(WebRequest request) {
		ThreadLocalManager.remove();
	}
}
