package postech.adms.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("admsRequestFilter")
public class AdmsRequestFilter extends OncePerRequestFilter {
	@Resource(name = "admsRequestProcessor")
	protected AdmsRequestProcessor requestProcessor;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			requestProcessor.process(new ServletWebRequest(request, response));
			filterChain.doFilter(request, response);
		} finally {
			requestProcessor.postProcess(new ServletWebRequest(request, response));
		}
	}
}
