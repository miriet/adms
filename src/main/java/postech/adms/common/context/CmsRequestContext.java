package postech.adms.common.context;

import postech.adms.common.classloader.ThreadLocalManager;
import postech.adms.domain.menu.AdminMenu;
import postech.adms.web.security.UserInfo;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmsRequestContext {
    private static final ThreadLocal<CmsRequestContext> CMS_REQUEST_CONTEXT = ThreadLocalManager.createThreadLocal(CmsRequestContext.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected WebRequest webRequest;
    protected java.util.Locale javaLocale;
    protected UserInfo user;
    protected AdminMenu currentMenu;
    protected MessageSourceAccessor messageSourceAccessor;
    protected String menuPath;

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public static CmsRequestContext getCmsRequestContext() {
        return CMS_REQUEST_CONTEXT.get();
    }

    public static void setCmsRequestContext(CmsRequestContext cmsRequestContext) {
        CMS_REQUEST_CONTEXT.set(cmsRequestContext);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        this.webRequest = new ServletWebRequest(request);
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public WebRequest getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
        if (webRequest instanceof ServletWebRequest) {
            this.request = ((ServletWebRequest) webRequest).getRequest();
            setResponse(((ServletWebRequest) webRequest).getResponse());
        }
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public AdminMenu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(AdminMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public MessageSourceAccessor getMessageSourceAccessor() {
        return messageSourceAccessor;
    }

    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public String getRequestURIWithoutContext() {
        String requestURIWithoutContext = null;

        if (request.getRequestURI() != null) {
            if (request.getContextPath() != null) {
                requestURIWithoutContext = request.getRequestURI().substring(request.getContextPath().length());
            } else {
                requestURIWithoutContext = request.getRequestURI();
            }

            // Remove JSESSION-ID or other modifiers
            int pos = requestURIWithoutContext.indexOf(";");
            if (pos >= 0) {
                requestURIWithoutContext = requestURIWithoutContext.substring(0, pos);
            }
        }

        return requestURIWithoutContext;
    }
}
