package postech.adms.web.filter;

import org.springframework.web.context.request.WebRequest;

public interface AdmsWebRequestProcessor {
	public void process(WebRequest request);
	public void postProcess(WebRequest request);
}
