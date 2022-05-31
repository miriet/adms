package postech.adms.service.jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.adms.common.util.StringUtil;
import postech.adms.domain.codes.*;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.service.member.MemberService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Transactional("transactionManager")
public class SapService {

    @Resource(name = "admsMemberService")
    private MemberService memberService;

    static String ABAP_AS        = "ABAP_AS_WITHOUT_POOL";
    static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
    static String ABAP_MS        = "ABAP_MS_WITHOUT_POOL"; // Message Server 를 사용하는 경우.

    // SAP 접속정보
    static {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,          "[URL]sap.infinov.com");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,           "10"          );
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,          "100"         );
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,            "[USER_ID]"    );
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,          "[PASSWORD]"     );
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,            "ko"          );
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME,          "***"         );
        connectProperties.setProperty(DestinationDataProvider.JCO_EXPIRATION_TIME, "600"         );
        createDestinationDataFile(ABAP_AS, connectProperties);

        // Connection Pool 사용하는 경우.
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,   "5" );
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,      "10");
        createDestinationDataFile(ABAP_AS_POOLED, connectProperties);
    }

    private String birthdayofficial;

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

    /**
     * A slightly more complex example than before. Query the companies list
     * returned in a table and then obtain more details for each company.
     * @throws JCoException
     */
    // =========================================================================
    // POSTECH SAP => ADMS : 회원데이터 전송
    // sendType: "T":test용, "R": 실제
    // (JCO Table)
    // =========================================================================
    public String getMemberFromSAP(String fromDate, String toDate, String sendType) throws JCoException {
        String result = "";
        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction("ZGM_RFC_ADMS_SEND");

        if (function == null) throw new RuntimeException("[ZGM_RFC_ADMS_SEND] not found in SAP.");

        // 검색조건 Setting
        function.getImportParameterList().clear();
        function.getImportParameterList().setValue("FROMDATE", fromDate);  // 검색 FROM 일자 (7일전 날짜)
        function.getImportParameterList().setValue("TODATE"  , toDate);   // 검색 TO   일자 (오늘   날짜)

        try {
            function.execute(destination);
        } catch (AbapException e) {
            System.out.println(e.toString());
            result = "-1";
            return result;
        }

        JCoTable returnTable = function.getTableParameterList().getTable(0); // ZGMS0011_1
        int rowCount = returnTable.getNumRows();

        System.out.println("Total selected row count:" + rowCount);

        List<AlumniMember> alumniMemberList = new ArrayList<AlumniMember>();
        List<AlumniMember> alumniMemberListForTest = new ArrayList<AlumniMember>();
        // 수신 데이터 조합
        for (int i = 0; i < rowCount; i++) {
            returnTable.setRow(i);

            // ---------------------------------------------
            // 1. 회원정보
            // ---------------------------------------------
            AlumniMember alumniMember = new AlumniMember();
            alumniMember.getAuditable().setCreatedBy(1L);
            System.out.print("\nData "+i);
            System.out.println(" ::: CreatedBy: "+ alumniMember.getAuditable().getCreatedBy());

            alumniMember.setName(returnTable.getString("NAME"));                                      // 성명
            System.out.print("name:: " + alumniMember.getName());
            String birthdayofficial = returnTable.getString("REGIDENTID");
            int genderCode = Integer.parseInt(birthdayofficial.substring(6,7));
            if(genderCode < 3){
                birthdayofficial = "19"+birthdayofficial.substring(0,6);
            }else {
                birthdayofficial = "20"+birthdayofficial.substring(0,6);
            }
            if(genderCode%2 == 0){
                alumniMember.setGender(Gender.FEMALE);
            }else{
                alumniMember.setGender(Gender.MALE);
            }

            alumniMember.setBirthdayOfficial(StringUtil.changeDateFormat(birthdayofficial));                      // 생년월일
            alumniMember.setBirthdayReal(returnTable.getString("BIRTHDAY"));                      // 생년월일
            alumniMember.setBirthdayType(returnTable.getString("BIRTHDAYTYPE").equals("01")? CalType.SOLAR : CalType.LUNAR);// 음/양구분
            alumniMember.setIsMarried(returnTable.getString("ISMARRIED").equals("2") ? true : false); // 결혼여부
            alumniMember.setIsAlive(returnTable.getString("ISALIVE").equals("00") ? true : false);    // 생존여부
            alumniMember.setMailingAddress(returnTable.getString("MAILINGTYPE").equals("XXDEFAULT") ? "HOME"
                    : returnTable.getString("MAILINGTYPE").equals("FIRMA") ? "WORK" : "");            // 우편물수령지
            alumniMember.setEmail(returnTable.getString("EMAIL"));                                    // 이메일
            alumniMember.setPhone(returnTable.getString("PHONE"));                                    // 전화번호
            alumniMember.setMobile(returnTable.getString("MOBILE"));                                  // 휴대폰
            alumniMember.setNationality(returnTable.getString("NATIONALITY"));                        // 국적
            alumniMember.setCurrentWorkType(returnTable.getString("CURRENTWORKTYPE"));                // 현재직종구분
            alumniMember.setCurrentWork(returnTable.getString("CURRENTWORK"));                        // 현재직장명
            alumniMember.setCurrentWorkDept(returnTable.getString("CURRENTWORKDEPT"));                // 현재직장부서
            alumniMember.setCurrentJobTitle(returnTable.getString("CURRENTJOBTITLE"));                // 현재직장직책

            System.out.print("  BirthdayOfficial::" + alumniMember.getBirthdayOfficial());
            System.out.print("  BirthdayRealFromSAP::" + returnTable.getString("BIRTHDAY"));
            System.out.print("  BirthdayReal::" + alumniMember.getBirthdayReal());

            // ---------------------------------------------
            // 2. 학위정보
            // ---------------------------------------------
            List<Degree> degrees = new ArrayList<Degree>();

            // 2-1. 학사
            if (returnTable.getString("BSID") != null && !"".equals(returnTable.getString("BSID"))) {
                Degree degree = new Degree();
                degree.setDegreeType(DegreeType.BS);
                degree.setStudentId(returnTable.getString("BSID"));  // 학번
                degree.setDeptCode(Integer.toString(Integer.parseInt(returnTable.getString("BSDEPT")))); // 학과코드

                String entranceYear = returnTable.getString("BSID").substring(0, 4);
                if (Integer.parseInt(entranceYear) < 2100) { // 앞 4자리가 년도

                } else {// 앞 2자리가 년도
                    entranceYear = "19" + entranceYear.substring(0, 2);
                }
                degree.setEntranceYear(entranceYear+"03"); // 입학년도

                 degree.setGraduationYear(returnTable.getString("BSGRADUATIONDATE")); // 졸업일자
//                degree.setGraduationYear(returnTable.getString("BSGRADUATIONYEAR"));    // 졸업년도
                degree.setSupervisor(returnTable.getString("BSPROFESSOR"));             // 지도교수
                degree.setExpectedPath(getExpectedPath(returnTable.getString("BSPLAN")));                // 졸업당시 진로
                degree.setExpectedWork(returnTable.getString("BSPLANWORK"));            // 졸업당시 직장명

                if (degree != null) degrees.add(degree);
            }



            if (degrees != null && degrees.size() > 0) alumniMember.setDegrees(degrees);

            // ---------------------------------------------
            // 3. 주소정보
            // ---------------------------------------------
            List<Address> addresses = new ArrayList<Address>();

            // 3-1. 자택
            if (returnTable.getString("HOMEZIPCODE") != null && !"".equals(returnTable.getString("HOMEZIPCODE"))) {
                Address address = new Address();
                address.setAddressType("HOME");
                address.setAddressName("자택");
                address.setZipCode(returnTable.getString("HOMEZIPCODE"));// 자택우편번호
                address.setAddress1(returnTable.getString("HOMEADDR1")); // 자택주소1
                address.setAddress2(returnTable.getString("HOMEADDR2")); // 자택주소2
                address.setAddress3(returnTable.getString("HOMEADDR3")); // 자택주소3
                address.setAddress4(returnTable.getString("HOMEADDR4")); // 자택주소4

                if (address != null) addresses.add(address);
            }

            // 3-2. 직장
            if (returnTable.getString("WORKZIPCODE") != null && !"".equals(returnTable.getString("WORKZIPCODE"))) {
                Address address = new Address();
                address.setAddressType("WORK");
                address.setAddressName("직장");
                address.setZipCode(returnTable.getString("WORKZIPCODE"));// 직장우편번호
                address.setAddress1(returnTable.getString("WORKADDR1")); // 직장주소1
                address.setAddress2(returnTable.getString("WORKADDR2")); // 직장주소2
                address.setAddress3(returnTable.getString("WORKADDR3")); // 직장주소3
                address.setAddress4(returnTable.getString("WORKADDR4")); // 직장주소4

                if (address != null) addresses.add(address);
            }

            if (addresses != null && addresses.size() > 0 ) alumniMember.setAddresses(addresses);

            alumniMemberList.add(alumniMember);

            if(alumniMember.getBirthdayOfficial().equals("1971-05-15") && alumniMember.getName().equals("정일균")){
                alumniMemberListForTest.add(alumniMember);
            }
        }

        System.out.println("*** before saving Alumni infos ***");
        System.out.println("alumniMemberList.size():: " + alumniMemberList.size());
        // 저장처리
        if (alumniMemberList != null && alumniMemberList.size() > 0) {
            try {
                if (sendType.equals("T")) {
                    System.out.println("====================");
                    System.out.println("=== total: " + alumniMemberList.size());
                    System.out.println("=== Test total: " + alumniMemberListForTest.size());
                    System.out.println("=== " + alumniMemberListForTest.get(0).getName());
                    System.out.println("====================");
                    memberService.saveMemberList(alumniMemberListForTest);
                }else {
                    memberService.saveMemberList(alumniMemberList);
                }
                result = "100";
            } catch (Exception e) {
                System.out.println(e.toString());
                result = "199";
            }

        } else {
            result = "109";
        }
        return result;
    }

    /*
    * =========================================================================
    * ADMS => POSTECH SAP : 회원데이터 전송
    * (JCO Table)
    * sendMemberToSAP()으로 이름 변경하고 일반회원이 개인정보를 변경하거나
    * 관리자가 일괄적으로 여러명의 정보를 변경하는 경우에 AlumniMember 객체 또는 memberId를
    * 파라미터로 넘겨받아 해당 Id의 회원정보를 SAP으로 전송한다.
    * (전송하기 전에 ADMS에서는 save()가 완료되어야 한다.
    * =========================================================================
    */
    public String sendMemberToSAP(long memberId) throws JCoException {
        return sendMemberToSAP(memberService.findByMemberId(memberId));
    }
    public String sendMemberToSAP(String memberId) throws JCoException {
        return sendMemberToSAP(memberService.findByMemberId(Long.parseLong(memberId)));
    }
    public String sendMemberToSAP(AlumniMember alumniMember) throws JCoException {
        String result = "201";

        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction("ZGM_RFC_ADMS_GET");

        if (function == null) throw new RuntimeException("[ZGM_RFC_ADMS_GET] not found in SAP.");

        // 전송테이블 데이터 생성
        JCoTable sendTable = function.getTableParameterList().getTable(0);
        sendTable.appendRow();

        // 회원정보
        sendTable.setValue("BIRTHDAY",        StringUtil.changeDateFormat(alumniMember.getBirthdayReal()));
        sendTable.setValue("BIRTHDAYTYPE",    alumniMember.getBirthdayType().equals(BirthdayType.SOLAR) ? "01" : "02");
        sendTable.setValue("ISMARRIED",       alumniMember.getIsMarried() ? "2" : "1");
        sendTable.setValue("ISALIVE",         alumniMember.getIsAlive() ? "00" : "01");
        sendTable.setValue("MAILINGTYPE",     alumniMember.getMailingAddress() == "HOME" ? "XXDEFAULT" : alumniMember.getMailingAddress() == "WORK" ? "FIRMA" : "");
        sendTable.setValue("EMAIL",           alumniMember.getEmail());
        sendTable.setValue("PHONE",           alumniMember.getPhone());
        sendTable.setValue("MOBILE",          alumniMember.getMobile());
        sendTable.setValue("CURRENTWORKTYPE", alumniMember.getCurrentWorkType());
        sendTable.setValue("CURRENTWORK",     alumniMember.getCurrentWork());
        sendTable.setValue("CURRENTWORKDEPT", alumniMember.getCurrentWorkDept());
        sendTable.setValue("CURRENTJOBTITLE", alumniMember.getCurrentJobTitle());

        // 학위정보
        for (Degree degree : alumniMember.getDegrees()) {
//            sendTable.appendRow();
            sendTable.setValue("STUDENTID", degree.getStudentId());

            if (DegreeType.BS.equals(degree.getDegreeType())) {
                sendTable.setValue("DEGREE", "UG");
                sendTable.setValue("BSPLAN", setExpectedPath(degree.getExpectedPath()));
                sendTable.setValue("BSPLANWORK", degree.getExpectedWork());
            } else if (DegreeType.MS.equals(degree.getDegreeType())) {
                sendTable.setValue("DEGREE", "MA");
                sendTable.setValue("MSPLAN", setExpectedPath(degree.getExpectedPath()));
                sendTable.setValue("MSPLANWORK", degree.getExpectedWork());
            } else if (DegreeType.PhD.equals(degree.getDegreeType())) {
                sendTable.setValue("DEGREE", "DR");
                sendTable.setValue("PHDPLAN", setExpectedPath(degree.getExpectedPath()));
                sendTable.setValue("PHDPLANWORK", degree.getExpectedWork());
            } else if (DegreeType.UNITY.equals(degree.getDegreeType())) {
                sendTable.setValue("DEGREE", "MP");
                sendTable.setValue("UNITYPLAN", setExpectedPath(degree.getExpectedPath()));
                sendTable.setValue("UNITYPLANWORK", degree.getExpectedWork());
            } else if (DegreeType.PAMTIP.equals(degree.getDegreeType())) {
                sendTable.setValue("DEGREE", "PA");
            }

        }

        // 주소정보
        for (Address address : alumniMember.getAddresses()) {
            if (AddressType.HOME.equals(address.getAddressType())) {
                sendTable.setValue("HOMEZIPCODE", address.getZipCode());
                sendTable.setValue("HOMEADDR1",   address.getAddress1());
                sendTable.setValue("HOMEADDR2",   address.getAddress2());
                sendTable.setValue("HOMEADDR3",   address.getAddress3());
                sendTable.setValue("HOMEADDR4",   address.getAddress4());
            } else if (AddressType.WORK.equals(address.getAddressType())) {
                sendTable.setValue("WORKZIPCODE", address.getZipCode());
                sendTable.setValue("WORKADDR1",   address.getAddress1());
                sendTable.setValue("WORKADDR2",   address.getAddress2());
                sendTable.setValue("WORKADDR3",   address.getAddress3());
                sendTable.setValue("WORKADDR4",   address.getAddress4());
            }
        }

        try {
            function.execute(destination);
            result = "200";
        } catch (AbapException e) {
            System.out.println(e.toString());
            result = "299";
            return result;
        }

        return result;
    }

    private String getExpectedPath(String code){
        String result;
        switch (code){
            case "0001": result = "취업"; break;
            case "0002": result = "진학"; break;
            case "0003": result = "창업"; break;
            case "0005": result = "군입대"; break;
            default: result = "기타"; break;
        }
        return result;
    }

    private String setExpectedPath(String path){
        String result;
        switch (path){
            case "취업": result = "0001"; break;
            case "진학": result = "0002"; break;
            case "창업": result = "0003"; break;
            case "군입대": result = "0005"; break;
            default: result = "0004"; break;
        }
        return result;
    }

}
