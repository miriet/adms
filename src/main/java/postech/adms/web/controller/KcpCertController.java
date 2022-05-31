package postech.adms.web.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kcp.CT_CLI;


@Controller("kcpCertController")
@RequestMapping("/kcpCert")
public class KcpCertController {

	static final String ENC_KEY      = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // 반드시 외부에 노출되지 않도록 관리하시기 바랍니다.

	/*
	static final String SITE_CD      = "A7XRC"; // 테스트 : S6186, 포항공대 : A7XRC
	static final String WEBSITE_CD   = "J18032902305";      // 포항공대 : J18032902305
	static final String ENC_KEY      = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // 반드시 외부에 노출되지 않도록 관리하시기 바랍니다.
	static final String REG_TX       = "cert"; // 요청종류
	static final String CERT_OTP_USE = "Y";    // Y : 실명확인+OTP점유확인, N : 실명확인 only
	static final String CERT_ENC_USE = "Y";    // 필수 (고정값 : 메뉴얼 참고)
	static final String CERT_METHOD  = "01";   // 요청구분
	static final String RET_URL      = "http://127.0.0.1/kcpCert/certRequest"; // 인증결과 리턴 페이지
    */

	/**
     * 본인인증 테스트 Method Begin ========================================
     */

	/**
	 * 본인인증 샘플 입력화면 #1
	 */
	@RequestMapping("/certForm")
	public String certForm(ModelMap model, HttpServletRequest request){
		return "kcpcert/sample/kcpcert_start";
	}

	/**
	 * 본인인증 샘플 입력화면 #2
	 */
	@RequestMapping("/reqForm")
	public String reqForm(ModelMap model, HttpServletRequest request){
		return "kcpcert/sample/kcpcert_proc_req";
	}

	/**
	 * 본인인증 샘플 결과화면 #3
	 */
	@RequestMapping("/resForm")
	public String resForm(ModelMap model, HttpServletRequest request){
		return "kcpcert/sample/kcpcert_proc_res";
	}

    /**
     * 본인인증 테스트 Method End ======================================
     */

	/**
	 * 통신사 본인인증 폼
	 */
	@RequestMapping("/certRequest")
	public String certRequest(ModelMap model, HttpServletRequest request){

		try {
			request.setCharacterEncoding("euc-kr");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String req_tx        = "";

		String site_cd       = "";
		String ordr_idxx     = "";

		String year          = "";
		String month         = "";
		String day           = "";
		String user_name     = "";
		String sex_code      = "";
		String local_code    = "";

		String web_siteid    = "";
		String web_siteid_hashYN = "";
		String cert_able_yn  = "";

		String up_hash       = "";

		final String ENC_KEY       = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // 반드시 외부에 노출되지 않도록 관리하시기 바랍니다.
		/*------------------------------------------------------------------------*/
		/*  :: 전체 파라미터 남기기                                               */
		/*------------------------------------------------------------------------*/
		final StringBuffer sbParam = new StringBuffer();
		CT_CLI       cc      = new CT_CLI();
		cc.setCharSetUtf8();

		// request 로 넘어온 값 처리
		final Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			final String nmParam = (String) params.nextElement();
			final String valParam[] = request.getParameterValues(nmParam);

			for (int i = 0; i < valParam.length; i++) {
				if (nmParam.equals("site_cd")) {
					site_cd = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("req_tx")) {
					req_tx = f_get_parm_str(valParam[i]);
				}

			    if (nmParam.equals("ordr_idxx")) {
					ordr_idxx = f_get_parm_str(valParam[i]);
				}

			    if (nmParam.equals("name")) {
					user_name = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("year")) {
					year = f_get_parm_int(valParam[i]);
				}

				if (nmParam.equals("month")) {
					month = f_get_parm_int(valParam[i]);
				}

				if (nmParam.equals("day")) {
					day = f_get_parm_int(valParam[i]);
				}

				if (nmParam.equals("sex_code")) {
					sex_code = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("local_code")) {
					local_code = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("web_siteid_hashYN")) {
					web_siteid_hashYN = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("web_siteid")) {
					web_siteid = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("cert_able_yn")) {
					cert_able_yn = f_get_parm_str(valParam[i]);
				}

				// 인증창으로 넘기는 form 데이터 생성 필드
				sbParam.append("<input type=\"hidden\" name=\"" + nmParam + "\" value=\"" + f_get_parm_str(valParam[i]) + "\"/>");
			}
		}

		if (req_tx.equals("cert")) {
			// !!up_hash 데이터 생성시 주의 사항
			// year , month , day 가 비어 있는 경우 "00" , "00" , "00" 으로 설정이 됩니다
			// 그외의 값은 없을 경우 ""(null) 로 세팅하시면 됩니다.
			// up_hash 데이터 생성시 site_cd 와 ordr_idxx 는 필수 값입니다.
			if (cert_able_yn.equals("Y")) {
				up_hash = cc.makeHashData(ENC_KEY,
						                  site_cd   +
										  ordr_idxx +
								          (web_siteid_hashYN.equals("Y") ? web_siteid : "") +
										  ""   +
										  "00" +
										  "00" +
										  "00" +
										  ""   +
										  ""
										 );
			} else {
				up_hash = cc.makeHashData(ENC_KEY,
                                          site_cd   +
										  ordr_idxx +
								          (web_siteid_hashYN.equals("Y") ? web_siteid : "") +
										  user_name +
										  year      +
										  month     +
										  day       +
										  sex_code  +
										  local_code
										 );
			}

			cc = null; // 객체 해제

			// 인증창으로 넘기는 form 데이터 생성 필드 ( up_hash )
			sbParam.append("<input type=\"hidden\" name=\"up_hash\" value=\"" + up_hash + "\"/>");
		}

		model.addAttribute("sbParam", sbParam);

		return "kcpcert/WEB_ENC/kcpcert_proc_req";
	}

	/**
	 * 인증결과
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/certResponse")
	public String certResponse(ModelMap model, HttpServletRequest request){
		String site_cd        = "";
		String ordr_idxx      = "";

		String cert_no        = "";
		String cert_enc_use   = "";
		String enc_cert_data  = "";
		String enc_info       = "";
		String enc_data       = "";
		String req_tx         = "";

		String tran_cd        = "";
		String res_cd         = "";
		String res_msg        = "";

		String dn_hash        = "";

		/*------------------------------------------------------------------------*/
		/*  :: 전체 파라미터 남기기                                               */
		/*------------------------------------------------------------------------*/
		StringBuffer sbParam = new StringBuffer();
		CT_CLI cc = new CT_CLI();
		//cc.setCharSetUtf8(); // UTF-8 처리

		// request 로 넘어온 값 처리
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String nmParam = (String) params.nextElement();
			String valParam[] = request.getParameterValues(nmParam);

			for (int i = 0; i < valParam.length; i++) {
				if (nmParam.equals("site_cd")) {
					site_cd = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("ordr_idxx")) {
					ordr_idxx = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("res_cd")) {
					res_cd = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("cert_enc_use")) {
					cert_enc_use = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("req_tx")) {
					req_tx = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("cert_no")) {
					cert_no = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("enc_cert_data")) {
					enc_cert_data = f_get_parm_str(valParam[i]);
				}

				if (nmParam.equals("dn_hash")) {
					dn_hash = f_get_parm_str(valParam[i]);
				}

				// 결과 메시지가 한글 데이터 URL decoding 해줘야합니다.
				// 부모창으로 넘기는 form 데이터 생성 필드
				if (nmParam.equals("res_msg")) {
					sbParam.append("<input type=\"hidden\" name=\"" + nmParam + "\" value=\"" + URLDecoder.decode(valParam[i]) + "\"/>");
				} else {
					sbParam.append("<input type=\"hidden\" name=\"" + nmParam + "\" value=\"" + f_get_parm_str(valParam[i]) + "\"/>");
				}

			}
		}

		// 결과 처리
		StringBuffer retParam = new StringBuffer();
		
		if (cert_enc_use.equals("Y")) {
			if (res_cd.equals("0000")) {
				// dn_hash 검증
				// KCP 가 리턴해 드리는 dn_hash 와 사이트 코드, 주문번호 , 인증번호를 검증하여
				// 해당 데이터의 위변조를 방지합니다
				if (!cc.checkValidHash(ENC_KEY, dn_hash, (site_cd + ordr_idxx + cert_no))) {
					// 검증 실패시 처리 영역

					System.out.println("dn_hash 변조 위험있음");
					//cc = null; // 객체 반납 ( 루틴 탈출시에만 호출 )
				}

				// 가맹점 DB 처리 페이지 영역

				System.out.println(site_cd);
				System.out.println(cert_no);
				System.out.println(enc_cert_data);

				// 인증데이터 복호화 함수
				// 해당 함수는 암호화된 enc_cert_data 를
				// site_cd 와 cert_no 를 가지고 복화화 하는 함수 입니다.
				// 정상적으로 복호화 된경우에만 인증데이터를 가져올수 있습니다.
				cc.decryptEncCert( ENC_KEY, site_cd, cert_no, enc_cert_data );
				//cc.setCharSetUtf8(); // 복호화 결과값 인코딩 변경 메서드 ( UTF-8 인코딩 사용시 주석을 해제하시기 바랍니다.)

				System.out.println("#######################################################");
				System.out.println( "▣ 사이트 코드 = ["         + site_cd       + "]");                // 사이트 코드
				System.out.println( "▣ cert_no = ["             + cert_no       + "]");                // cert_no
				System.out.println( "▣ enc_cert_data = ["       + enc_cert_data + "]");                // enc_cert_data
				System.out.println("-------------------------------------------------------");
				System.out.println( "▣ 이동통신사 코드 = ["     + cc.getKeyValue("comm_id")    + "]"); // 이동통신사 코드
				System.out.println( "▣ 전화번호 = ["            + cc.getKeyValue("phone_no")   + "]"); // 전화번호
				System.out.println( "▣ 이름 = ["                + cc.getKeyValue("user_name")  + "]"); // 이름
				System.out.println( "▣ 생년월일 = ["            + cc.getKeyValue("birth_day")  + "]"); // 생년월일
				System.out.println( "▣ 성별코드 = ["            + cc.getKeyValue("sex_code")   + "]"); // 성별코드
				System.out.println( "▣ 내/외국인 정보 = ["      + cc.getKeyValue("local_code") + "]"); // 내/외국인 정보
				System.out.println( "▣ CI = ["                  + cc.getKeyValue("ci")         + "]"); // CI
				System.out.println( "▣ DI 중복가입 확인값 = ["  + cc.getKeyValue("di")         + "]"); // DI 중복가입 확인값
				// System.out.println( "▣ CI_URL = ["              + URLDecoder.decode(cc.getKeyValue("ci_url")) + "]"); // CI URL 인코딩 값
				// System.out.println( "▣ DI_URL = ["              + URLDecoder.decode(cc.getKeyValue("di_url")) + "]"); // DI URL 인코딩 값
				System.out.println( "▣ 웹사이트 아이디 = ["     + cc.getKeyValue("web_siteid") + "]"); // 암호화된 웹사이트 아이디
				System.out.println( "▣ 암호화된 결과코드 = ["   + cc.getKeyValue("res_cd")     + "]"); // 암호화된 결과코드
				System.out.println( "▣ 암호화된 결과메시지 = [" + cc.getKeyValue("res_msg")    + "]"); // 암호화된 결과메시지
				System.out.println("#######################################################");

				retParam.append("<input type=\"hidden\" name=\"kcpPhoneNo\" value=\"" + cc.getKeyValue("phone_no") + "\"/>");
				retParam.append("<input type=\"hidden\" name=\"kcpName\" value=\"" + cc.getKeyValue("user_name") + "\"/>");
				retParam.append("<input type=\"hidden\" name=\"kcpBirthday\" value=\"" + cc.getKeyValue("birth_day") + "\"/>");
				retParam.append("<input type=\"hidden\" name=\"kcpCi\" value=\"" + cc.getKeyValue("ci") + "\"/>");
				retParam.append("<input type=\"hidden\" name=\"kcpDi\" value=\"" + cc.getKeyValue("di") + "\"/>");
				
			} else {
				/*if( res_cd.equals( "0000" ) != true )*/
				// 인증실패
			}
		} else {
			/*if( cert_enc_use.equals( "Y" ) != true )*/
			// 암호화 인증 안함
		}

		cc = null; // 객체 반납

		model.addAttribute("retParam", retParam);
		return "kcpcert/WEB_ENC/kcpcert_proc_res";
	}

	/**
	 * null 값을 처리하는 메소드
	 */
	public String f_get_parm_str(String val) {
		if (val == null) {
			val = "";
		}
		return val;
	}

	/**
	 * 중요 해당 함수는 year, month, day 변수가 null 일 경우 00 으로 치환합니다
	 */
	public String f_get_parm_int(String val) {
		String ret_val = "";

		if (val == null) {
			val = "00";
		}

		if (val.equals("")) {
			val = "00";
		}

		ret_val = val.length() == 1 ? ("0" + val) : val;

		return ret_val;
	}

}
