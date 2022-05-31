package postech.adms.web.message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.ObjectError;

public class MessageHandler {

	private @Resource(name = "messageSourceAccessor") MessageSourceAccessor messageSource;

	/**
	 * 에러에 대한 메시지 한개 처리(ajax를 위한)
	 * @param errList
	 * @return
	 */
	public String getMessageOne(List<ObjectError> errList) {
		String result="";
		for (ObjectError err:errList) {
			result = getErrMessage(err);
			break;
		}
		return result;
	}

	/**
	 * 에러에 대한 메시지 처리
	 * @param err
	 * @return
	 */
	private String getErrMessage(ObjectError err) {
		String messgae = err.getDefaultMessage();
		for (String errCode:err.getCodes()) {
			if (errCode != null) {
				errCode = errCode.replaceAll(" ", "");
				String subMessage = getMessage(errCode,err.getArguments());
				if (subMessage != null && !errCode.equals(subMessage)) {
					messgae = subMessage;
					break;
				}
			}
		}
		return messgae;
	}

	/**
	 * 에러코드에 대한 메시지 처리
	 * @param msgCode 에러 코드
	 * @return
	 */
	public String getMessage(String msgCode) {
		return messageSource.getMessage(msgCode);
	}

	/**
	 * 에러 코드에 대한 메시지 처리
	 * @param msgCode 에러코드
	 * @param args 에러 메시지의 인수
	 * @return
	 */
	public String getMessage(String msgCode,Object[] args) {
		return messageSource.getMessage(msgCode,args);
	}
}
