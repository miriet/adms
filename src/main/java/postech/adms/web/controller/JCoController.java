package postech.adms.web.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import postech.adms.service.jco.SapService;
import postech.adms.service.member.MemberService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * basic examples for Java to ABAP communication
 */
@Controller("JCoController")
@RequestMapping("/jco")
public class JCoController {
    @Resource(name = "admsMemberService")
    private MemberService memberService;
    @Resource
    private SapService sapService;

//    static String ABAP_AS        = "ABAP_AS_WITHOUT_POOL";
//    static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
//    static String ABAP_MS        = "ABAP_MS_WITHOUT_POOL"; // Message Server 를 사용하는 경우.
//
//    // SAP 접속정보
//    static {
//        Properties connectProperties = new Properties();
//        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,          "eccap.postech.ac.kr");
//        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,           "10"          );
//        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,          "100"         );
//        connectProperties.setProperty(DestinationDataProvider.JCO_USER,            "RFC_CM03"    );
//        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,          "postech"     );
//        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,            "ko"          );
//        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME,          "PTP"         );
//        connectProperties.setProperty(DestinationDataProvider.JCO_EXPIRATION_TIME, "600"         );
//        createDestinationDataFile(ABAP_AS, connectProperties);
//
//        // Connection Pool 사용하는 경우.
//        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,   "5" );
//        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,      "10");
//        createDestinationDataFile(ABAP_AS_POOLED, connectProperties);
//    }

    /**
     * 자바 프로그램 실행시에 createDestinationDataFile매서드가 작업 폴더에 다음 2개의 파일을 생성합니다.
     *
     * ABAP_AS_WITH_POOL.jcoDestination
     * ABAP_AS_WITHOUT_POOL.jcoDestination
     *
     * 이 파일은 JCoDestinationManager.getDestination매서드 호출시 Jco가 읽어서 사용합니다.
    */
    private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
        File destCfg = new File(destinationName + ".jcoDestination");

        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "==> POSTECH vs ADMS Connection.");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }

    @RequestMapping("/sendMemberToSAP")
    public String sendMemberToSAP(ModelMap model) throws JCoException{
    	String result = "member/saveResult";

    	sapService.sendMemberToSAP(34118L);

    	return result;
    }

    @RequestMapping("/getMemberFromSAP")
    public String getMemberFromSAP(@RequestParam String fromDate,@RequestParam String toDate, ModelMap model) throws JCoException {
        String returnPage = "member/saveResult";
        sapService.getMemberFromSAP(fromDate, toDate, "R");
        return returnPage;
    }

    @RequestMapping("/getMemberFromSAPTest")
    public String getMemberFromSAPTest(@RequestParam String fromDate,@RequestParam String toDate, ModelMap model) throws JCoException {
        String returnPage = "member/saveResult";
        sapService.getMemberFromSAP(fromDate, toDate, "T");
        return returnPage;
    }
//        /**
//         * A slightly more complex example than before. Query the companies list
//         * returned in a table and then obtain more details for each company.
//         * @throws JCoException
//         */
//    // =========================================================================
//    // POSTECH SAP => ADMS : 회원데이터 전송
//    // (JCO Table)
//    // =========================================================================
//    @RequestMapping("/getMemberFromSAP")
//    public String getMemberFromSAP(ModelMap model) throws JCoException {
//        String returnPage = "member/saveResult";
//
//        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
//        JCoFunction function = destination.getRepository().getFunction("ZGM_RFC_ADMS_SEND");
//
//        if (function == null) throw new RuntimeException("[ZGM_RFC_ADMS_SEND] not found in SAP.");
//
//        // 검색조건 Setting
//        function.getImportParameterList().clear();
//        function.getImportParameterList().setValue("FROMDATE", setDate(-8));  // 검색 FROM 일자 (7일전 날짜)
//        function.getImportParameterList().setValue("TODATE"  , setDate(-1));   // 검색 TO   일자 (오늘   날짜)
//
//        try {
//            function.execute(destination);
//        } catch (AbapException e) {
//            System.out.println(e.toString());
//            model.addAttribute("result", "-1");
//            return returnPage;
//        }
//
//        JCoTable returnTable = function.getTableParameterList().getTable(0); // ZGMS0011_1
//        int rowCount = returnTable.getNumRows();
//
//        List<AlumniMember> alumniMemberList = new ArrayList<AlumniMember>();
//        // 수신 데이터 조합
//        for (int i = 0; i < rowCount; i++) {
//            returnTable.setRow(i);
//
//            // ---------------------------------------------
//            // 1. 회원정보
//            // ---------------------------------------------
//            AlumniMember alumniMember = new AlumniMember();
//
//            alumniMember.setName(returnTable.getString("NAME"));                                      // 성명
//            alumniMember.setBirthdayOfficial(returnTable.getString("BIRTHDAY"));                      // 생년월일
//            alumniMember.setBirthdayType(returnTable.getString("BIRTHDAYTYPE").equals("01")? CalType.SOLAR : CalType.LUNAR);// 음/양구분
//            alumniMember.setIsMarried(returnTable.getString("ISMARRIED").equals("2") ? true : false); // 결혼여부
//            alumniMember.setIsAlive(returnTable.getString("ISALIVE").equals("00") ? true : false);    // 생존여부
//            alumniMember.setMailingAddress(returnTable.getString("MAILINGTYPE").equals("XXDEFAULT") ? "HOME"
//                    : returnTable.getString("MAILINGTYPE").equals("FIRMA") ? "WORK" : "");            // 우편물수령지
//            alumniMember.setEmail(returnTable.getString("EMAIL"));                                    // 이메일
//            alumniMember.setPhone(returnTable.getString("PHONE"));                                    // 전화번호
//            alumniMember.setMobile(returnTable.getString("MOBILE"));                                  // 휴대폰
//            alumniMember.setNationality(returnTable.getString("NATIONALITY"));                        // 국적
//            alumniMember.setCurrentWorkType(returnTable.getString("CURRENTWORKTYPE"));                // 현재직종구분
//            alumniMember.setCurrentWork(returnTable.getString("CURRENTWORK"));                        // 현재직장명
//            alumniMember.setCurrentWorkDept(returnTable.getString("CURRENTWORKDEPT"));                // 현재직장부서
//            alumniMember.setCurrentJobTitle(returnTable.getString("CURRENTJOBTITLE"));                // 현재직장직책
//            // alumniMember.setName(returnTable.getString("REGIDENTID"));                             // 주민번호
//
//            // ---------------------------------------------
//            // 2. 학위정보
//            // ---------------------------------------------
//            List<Degree> degrees = new ArrayList<Degree>();
//
//            // 2-1. 학사
//            if (returnTable.getString("BSID") != null && !"".equals(returnTable.getString("BSID"))) {
//                Degree degree = new Degree();
//                degree.setDegreeType(DegreeType.BS);
//                degree.setStudentId(returnTable.getString("BSID"));  // 학번
//                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("BSDEPT")))); // 학과코드
//
//                String entranceYear = returnTable.getString("BSID").substring(0, 4);
//                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도
//
//                } else {// 앞 2자리가 년도
//                    entranceYear = "19" + entranceYear.substring(0, 2);
//                }
//                degree.setEntranceYear(entranceYear); // 입학년도
//
//                // degree.setGraduationYear(returnTable.getString("BSGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("BSGRADUATIONYEAR"));    // 졸업년도
//                degree.setSupervisor(returnTable.getString("BSPROFESSOR"));             // 지도교수
//                degree.setExpectedPath(returnTable.getString("BSPLAN"));                // 졸업당시 진로
//                degree.setExpectedWork(returnTable.getString("BSPLANWORK"));            // 졸업당시 직장명
//
//                if (degree != null) degrees.add(degree);
//            }
//
//            // 2-2. 석사
//            if (returnTable.getString("MSID") != null && !"".equals(returnTable.getString("MSID"))) {
//                Degree degree = new Degree();
//                degree.setDegreeType(DegreeType.MS);
//                degree.setStudentId(returnTable.getString("MSID"));  // 학번
//                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("MSDEPT")))); // 학과코드
//
//                String entranceYear = returnTable.getString("BSID").substring(0, 4);
//                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도
//
//                } else {// 앞 2자리가 년도
//                    entranceYear = "19" + entranceYear.substring(0, 2);
//                }
//                degree.setEntranceYear(entranceYear); // 입학년도
//
//                // degree.setGraduationYear(returnTable.getString("MSGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("MSGRADUATIONYEAR"));    // 졸업년도
//                degree.setSupervisor(returnTable.getString("MSPROFESSOR"));             // 지도교수
//                degree.setExpectedPath(returnTable.getString("MSPLAN"));                // 졸업당시 진로
//                degree.setExpectedWork(returnTable.getString("MSPLANWORK"));            // 졸업당시 직장명
//
//                if (degree != null) degrees.add(degree);
//            }
//
//            // 2-3. 박사
//            if (returnTable.getString("PHDID") != null && !"".equals(returnTable.getString("PHDID"))) {
//                Degree degree = new Degree();
//                degree.setDegreeType(DegreeType.PhD);
//                degree.setStudentId(returnTable.getString("PHDID"));  // 학번
//                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("PHDDEPT")))); // 학과코드
//
//                String entranceYear = returnTable.getString("BSID").substring(0, 4);
//                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도
//
//                } else {// 앞 2자리가 년도
//                    entranceYear = "19" + entranceYear.substring(0, 2);
//                }
//                degree.setEntranceYear(entranceYear); // 입학년도
//
//                // degree.setGraduationYear(returnTable.getString("PHDGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("PHDGRADUATIONYEAR"));    // 졸업년도
//                degree.setSupervisor(returnTable.getString("PHDPROFESSOR"));             // 지도교수
//                degree.setExpectedPath(returnTable.getString("PHDPLAN"));                // 졸업당시 진로
//                degree.setExpectedWork(returnTable.getString("PHDPLANWORK"));            // 졸업당시 직장명
//
//                if (degree != null) degrees.add(degree);
//            }
//
//            // 2-4. 통합
//            if (returnTable.getString("UNITYID") != null && !"".equals(returnTable.getString("UNITYID"))) {
//                Degree degree = new Degree();
//                degree.setDegreeType(DegreeType.UNITY);
//                degree.setStudentId(returnTable.getString("UNITYID"));  // 학번
//                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("UNITYDEPT")))); // 학과코드
//
//                String entranceYear = returnTable.getString("BSID").substring(0, 4);
//                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도
//
//                } else {// 앞 2자리가 년도
//                    entranceYear = "19" + entranceYear.substring(0, 2);
//                }
//                degree.setEntranceYear(entranceYear); // 입학년도
//
//                // degree.setGraduationYear(returnTable.getString("UNITYGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("UNITYGRADUATIONYEAR"));    // 졸업년도
//                degree.setSupervisor(returnTable.getString("UNITYPROFESSOR"));             // 지도교수
//                degree.setExpectedPath(returnTable.getString("UNITYPLAN"));                // 졸업당시 진로
//                degree.setExpectedWork(returnTable.getString("UNITYPLANWORK"));            // 졸업당시 직장명
//
//                if (degree != null) degrees.add(degree);
//            }
//
//            // 2-5. 팸팁
//            if (returnTable.getString("PAMTIID") != null && !"".equals(returnTable.getString("PAMTIID"))) {
//                Degree degree = new Degree();
//                degree.setDegreeType(DegreeType.PAMTIP);
//                degree.setStudentId(returnTable.getString("PAMTIID"));  // 학번
//                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("PAMTIDEPT")))); // 학과코드
//
//                String entranceYear = returnTable.getString("BSID").substring(0, 4);
//                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도
//
//                } else {// 앞 2자리가 년도
//                    entranceYear = "19" + entranceYear.substring(0, 2);
//                }
//                degree.setEntranceYear(entranceYear); // 입학년도
//
//                // degree.setGraduationYear(returnTable.getString("PAMTIGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("PAMTIGRADUATIONYEAR"));    // 졸업년도
//
//                if (degree != null) degrees.add(degree);
//            }
//
//            if (degrees != null && degrees.size() > 0) alumniMember.setDegrees(degrees);
//
//            // ---------------------------------------------
//            // 3. 주소정보
//            // ---------------------------------------------
//            List<Address> addresses = new ArrayList<Address>();
//
//            // 3-1. 자택
//            if (returnTable.getString("HOMEZIPCODE") != null && !"".equals(returnTable.getString("HOMEZIPCODE"))) {
//                Address address = new Address();
//                address.setAddressType("HOME");
//                address.setAddressName("자택");
//                address.setZipCode(returnTable.getString("HOMEZIPCODE"));// 자택우편번호
//                address.setAddress1(returnTable.getString("HOMEADDR1")); // 자택주소1
//                address.setAddress2(returnTable.getString("HOMEADDR2")); // 자택주소2
//                address.setAddress3(returnTable.getString("HOMEADDR3")); // 자택주소3
//                address.setAddress4(returnTable.getString("HOMEADDR4")); // 자택주소4
//
//                if (address != null) addresses.add(address);
//           }
//
//            // 3-2. 직장
//            if (returnTable.getString("WORKZIPCODE") != null && !"".equals(returnTable.getString("WORKZIPCODE"))) {
//                Address address = new Address();
//                address.setAddressType("WORK");
//                address.setAddressName("직장");
//                address.setZipCode(returnTable.getString("WORKZIPCODE"));// 직장우편번호
//                address.setAddress1(returnTable.getString("WORKADDR1")); // 직장주소1
//                address.setAddress2(returnTable.getString("WORKADDR2")); // 직장주소2
//                address.setAddress3(returnTable.getString("WORKADDR3")); // 직장주소3
//                address.setAddress4(returnTable.getString("WORKADDR4")); // 직장주소4
//
//                if (address != null) addresses.add(address);
//           }
//
//           if (addresses != null && addresses.size() > 0 ) alumniMember.setAddresses(addresses);
//
//            alumniMemberList.add(alumniMember);
//            // System.out.println(returnTable.getString("NAME"));
//        }
//
//        // 저장처리
//        if (alumniMemberList != null && alumniMemberList.size() > 0) {
//            try {
//                memberService.saveMemberList(alumniMemberList);
//                model.addAttribute("result", "100");
//            } catch (Exception e) {
//                System.out.println(e.toString());
//                model.addAttribute("result", "199");
//            }
//
//        } else {
//            model.addAttribute("result", "109");
//        }
//        return returnPage;
//    }
//
//    // =========================================================================
//    // ADMS => POSTECH SAP : 회원데이터 전송
//    // (JCO Table)
//    // =========================================================================
//    @RequestMapping("/getMemberFromADMS")
//    public String getMemberFromADMS(HttpServletRequest request, ModelMap model) throws JCoException {
//        // 수정일자 기간(검색조건)
//        String strFromDate = request.getParameter("FROMDATE"); // 수정일자 FROM
//        String strToDate   = request.getParameter("TOATE");    // 수정일자 TO
//
//        // 수정기간(검색조건)이 공란일때, 오늘날짜 셑팅
//        if (StringUtils.isEmpty(strFromDate)) strFromDate = setDate(-7); // 7일전 날짜
//        if (StringUtils.isEmpty(strToDate))   strToDate   = setDate(0);  // 오늘 날짜
//
//        String returnPage = "member/saveResult";
//
//        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
//        JCoFunction function = destination.getRepository().getFunction("ZGM_RFC_ADMS_GET");
//
//        if (function == null) throw new RuntimeException("[ZGM_RFC_ADMS_GET] not found in SAP.");
//
//        // 회원목록 조회
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("fromDate", strFromDate); // 수정일자 FROM
//        param.put("toDate",   strToDate);   // 수정일자 TO
//        List<AlumniMember> alumniMemberList = memberService.getMember(param);
//
//        // 전송대상 데이터가 없을때,
//        if (alumniMemberList.size() == 0) {
//            model.addAttribute("result", "209");
//            return returnPage;
//        } else {
//            // 전송테이블 데이터 생성
//            JCoTable sendTable = function.getTableParameterList().getTable(0);
//            for (AlumniMember alumniMember : alumniMemberList) {
//
//                // 학위정보
//                for (Degree degree : alumniMember.getDegrees()) {
//                    sendTable.appendRow();
//                    sendTable.setValue("STUDENTID", degree.getStudentId());
//
//                    if (DegreeType.BS.equals(degree.getDegreeType())) {
//                        sendTable.setValue("DEGREE", "UG");
//                        sendTable.setValue("BSPLAN", degree.getExpectedPath());
//                        sendTable.setValue("BSPLANWORK", degree.getExpectedWork());
//                    } else if (DegreeType.MS.equals(degree.getDegreeType())) {
//                        sendTable.setValue("DEGREE", "MA");
//                        sendTable.setValue("MSPLAN", degree.getExpectedPath());
//                        sendTable.setValue("MSPLANWORK", degree.getExpectedWork());
//                    } else if (DegreeType.PhD.equals(degree.getDegreeType())) {
//                        sendTable.setValue("DEGREE", "DR");
//                        sendTable.setValue("PHDPLAN", degree.getExpectedPath());
//                        sendTable.setValue("PHDPLANWORK", degree.getExpectedWork());
//                    } else if (DegreeType.UNITY.equals(degree.getDegreeType())) {
//                        sendTable.setValue("DEGREE", "MP");
//                        sendTable.setValue("UNITYPLAN", degree.getExpectedPath());
//                        sendTable.setValue("UNITYPLANWORK", degree.getExpectedWork());
//                    } else if (DegreeType.PAMTIP.equals(degree.getDegreeType())) {
//                        sendTable.setValue("DEGREE", "PA");
//                    }
//
//                    // 회원정보
//                    sendTable.setValue("BIRTHDAY",        alumniMember.getBirthdayOfficial());
//                    sendTable.setValue("BIRTHDAYTYPE",    alumniMember.getBirthdayType().equals(BirthdayType.SOLAR) ? "01" : "02");
//                    sendTable.setValue("ISMARRIED",       alumniMember.getIsMarried() ? "2" : "1");
//                    sendTable.setValue("ISALIVE",         alumniMember.getIsAlive() ? "00" : "01");
//                    sendTable.setValue("MAILINGTYPE",     alumniMember.getMailingAddress() == "HOME" ? "XXDEFAULT" : alumniMember.getMailingAddress() == "WORK" ? "FIRMA" : "");
//                    sendTable.setValue("EMAIL",           alumniMember.getEmail());
//                    sendTable.setValue("PHONE",           alumniMember.getPhone());
//                    sendTable.setValue("MOBILE",          alumniMember.getMobile());
//                    sendTable.setValue("CURRENTWORKTYPE", alumniMember.getCurrentWorkType());
//                    sendTable.setValue("CURRENTWORK",     alumniMember.getCurrentWork());
//                    sendTable.setValue("CURRENTWORKDEPT", alumniMember.getCurrentWorkDept());
//                    sendTable.setValue("CURRENTJOBTITLE", alumniMember.getCurrentJobTitle());
//
//                    // 주소정보
//                    for (Address address : alumniMember.getAddresses()) {
//                        if (AddressType.HOME.equals(address.getAddressType())) {
//                            sendTable.setValue("HOMEZIPCODE", address.getZipCode());
//                            sendTable.setValue("HOMEADDR1",   address.getAddress1());
//                            sendTable.setValue("HOMEADDR2",   address.getAddress2());
//                            sendTable.setValue("HOMEADDR3",   address.getAddress3());
//                            sendTable.setValue("HOMEADDR4",   address.getAddress4());
//                        } else if (AddressType.WORK.equals(address.getAddressType())) {
//                            sendTable.setValue("WORKZIPCODE", address.getZipCode());
//                            sendTable.setValue("WORKADDR1",   address.getAddress1());
//                            sendTable.setValue("WORKADDR2",   address.getAddress2());
//                            sendTable.setValue("WORKADDR3",   address.getAddress3());
//                            sendTable.setValue("WORKADDR4",   address.getAddress4());
//                        }
//                    }
//                }
//            }
//
//            try {
//                function.execute(destination);
//                model.addAttribute("result", "200");
//            } catch (AbapException e) {
//                System.out.println(e.toString());
//                model.addAttribute("result", "299");
//                return returnPage;
//            }
//
//            return returnPage;
//        }
//    }

    // =========================================================================
    // 날짜 설정 (yyyyMMdd)
    // =========================================================================
    private String setDate(int interval) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, interval);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        strDate = sdf.format(cal.getTime());

        return strDate;
    }

}
