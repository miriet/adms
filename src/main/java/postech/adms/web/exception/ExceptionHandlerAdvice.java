package postech.adms.web.exception;

import postech.adms.web.bind.MultipartEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(MultipartFile.class, new MultipartEditor());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleError403(AccessDeniedException exception, WebRequest request)   {
		ModelAndView mav = new ModelAndView();

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("jsonView");
		} else {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("error/403page");
		}

		return mav;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleError404(NoHandlerFoundException exception, WebRequest request)   {
		ModelAndView mav = new ModelAndView();

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("jsonView");
		} else {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("error/404page");
		}

		return mav;
	}

	/*
	@ExceptionHandler(value = UploadException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView uploadException(UploadException exception, WebRequest request) {
		if(exception.getUploadFilePaths().size() > 0){
			uploader.delete(exception.getUploadFilePaths());
		}

		ModelAndView mav = new ModelAndView();

		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
			mav.addObject("message", exception.getMessage() );
			mav.addObject("trace", getStackTraceString(exception));
			mav.setViewName("jsonView");
		}else{
			mav.addObject("message", exception.getMessage() );
			mav.addObject("trace", getStackTraceString(exception));
			mav.setViewName("error/500page");
		}

		return mav;
	}
	*/

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView exception(Exception exception, WebRequest request) {
		ModelAndView mav = new ModelAndView();
		// System.out.println("here");
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("jsonView");
		} else {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("error/500page");
		}

		return mav;
	}

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView runtimeException(Exception exception, WebRequest request) {
		ModelAndView mav = new ModelAndView();

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("jsonView");
		} else {
			mav.addObject("message", exception.getMessage());
			mav.addObject("trace",   getStackTraceString(exception));
			mav.setViewName("error/500page");
		}

		return mav;
	}

	private String getStackTraceString(Exception exception) {
		StringBuilder result = new StringBuilder();

		for (StackTraceElement element : exception.getStackTrace()) {
			result.append(element + "\n");
		}

		return result.toString();
	}
}
