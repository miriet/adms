package postech.adms.web.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import postech.adms.domain.codes.CalType;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.service.member.MemberService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("admsApiController")
@RequestMapping("/static/api")
public class ApiController extends BasicController {

	@Resource(name = "admsMemberService")
	protected MemberService memberService;

	// ************************************************************************
	// 1. 동창회원 여부 확인 (ADMS -> PODO)
	// input
	// : 이름(memberName), 생년월일(birthDay)
	// return : 회원목록 존재여부확인 -> Y/N
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberCheck")
	public ResponseEntity<String> jsonMemberCheck(HttpServletRequest request,
			@RequestParam Map<String, String> reqParam) throws FileNotFoundException, IOException, JSONException {

		List<AlumniMember> memberList = memberService.find(reqParam);

		JSONObject json = new JSONObject();
		try {
			json.put("result", memberList.size() > 0 ? "Y" : "N");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<String>(json.toString(), resHeaders, HttpStatus.CREATED);
	}

	// ************************************************************************
	// 2. 본인정보 조회 (ADMS -> PODO)
	// input : memberId
	// return : 본인정보(Member + Degree + Address)
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberInfo")
	public ResponseEntity<String> jsonMemberInfo(HttpServletRequest request, @RequestParam Map<String, String> reqParam)
			throws FileNotFoundException, IOException, JSONException {

		String memberId = reqParam.get("memberId");
		Long memId = 0L;

		JSONArray jsonMembers   = new JSONArray();
		JSONArray jsonDegrees   = new JSONArray();
		JSONArray jsonAddresses = new JSONArray();

		HashMap<String, String> memberInfo = new HashMap<String, String>();
		HashMap<String, String> result     = new HashMap<String, String>();

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		try {
			memId = Long.parseLong(memberId);
			AlumniMember member = memberService.findOne(memId);
			try {
				JSONObject jsonMember = new JSONObject();

				jsonMember.put("memberId",         member.getId());
				jsonMember.put("name",             member.getName());
				jsonMember.put("gender",           member.getGender().toString());
				jsonMember.put("birthdayOfficial", member.getBirthdayOfficial());
				jsonMember.put("email",            member.getEmail());
				jsonMember.put("phone",            member.getPhone());
				jsonMember.put("mobile",           member.getMobile());
				jsonMember.put("nationality",      member.getNationality());
				jsonMembers.put(jsonMember);

				// 학위
				for (Degree degree : member.getDegrees()) {
					JSONObject jsonDegree = new JSONObject();

					jsonDegree.put("memberId",       degree.getAlumniMember().getId());
					jsonDegree.put("degreeId",       degree.getId());
					jsonDegree.put("degreeType",     degree.getDegreeType().toString());
					jsonDegree.put("deptCode",       degree.getDeptCode());
					jsonDegree.put("deptName",       degree.getDeptName());
					jsonDegree.put("studentId",      degree.getStudentId());
					jsonDegree.put("entranceYear",   degree.getEntranceYear());
					jsonDegree.put("graduationYear", degree.getGraduationYear());
					jsonDegrees.put(jsonDegree);
				}

				// 주소
				for (Address address : member.getAddresses()) {
					JSONObject jsonAddress = new JSONObject();

					jsonAddress.put("memberId",    address.getAlumniMember().getId());
					jsonAddress.put("addressId",   address.getId());
					jsonAddress.put("addressType", address.getAddressType().toString());
					jsonAddress.put("addressName", address.getAddressName());
					jsonAddress.put("country",     address.getCountry());
					jsonAddress.put("zipCode",     address.getZipCode());
					jsonAddress.put("address1",    address.getAddress1());
					jsonAddress.put("address2",    address.getAddress2());
					jsonAddresses.put(jsonAddress);
				}

				memberInfo.put("members",   jsonMembers.toString());
				memberInfo.put("degrees",   jsonDegrees.toString());
				memberInfo.put("addresses", jsonAddresses.toString());

				result.put("result", memberInfo.toString());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// return new ResponseEntity<String>(jsonMember.toString(),
			// resHeaders, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(result.toString(), resHeaders, HttpStatus.OK);
	}

	// ************************************************************************
	// 3. ADMS에 회원정보 UPDATE 확인 (ADMS -> PODO)
	// input : 회원번호(memberId), 수정기준일자(updateDate)
	// return : update info 유무 확인 -> Y/N
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberUpdateCheck")
	public ResponseEntity<String> jsonMemberUpdateCheck(HttpServletRequest request,
			@RequestParam Map<String, String> reqParam) throws FileNotFoundException, IOException, JSONException {

		String memberId   = reqParam.get("memberId");
		String updateDate = reqParam.get("updateDate");
		Long   memId      = 0L;
		Date   updateDt   = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			updateDt = sdf.parse(updateDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONObject json = new JSONObject();
		try {
			memId = Long.parseLong(memberId);
			AlumniMember memberInfo = memberService.findOne(memId);

			// 요청일자 <= 수정일자 일때
			if (memberInfo.getAuditable().getDateUpdated().compareTo(updateDt) >= 0) {
				json.put("result", memberInfo != null ? "Y" : "N");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<String>(json.toString(), resHeaders, HttpStatus.CREATED);
	}

	// ************************************************************************
	// 4. ADMS에 회원정보 UPDATE 정보 조회 (ADMS -> PODO)
	// input : 회원번호(memberId), 수정기준일자(updateDate)
	// return : 회원정보 목록
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberUpdateInfo")
	public ResponseEntity<String> jsonMemberUpdateInfo(HttpServletRequest request,
			@RequestParam Map<String, String> reqParam) throws FileNotFoundException, IOException, JSONException {

		String memberId   = reqParam.get("memberId");
		String updateDate = reqParam.get("updateDate");
		Long   memId      = 0L;
		Date   updateDt   = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			updateDt = sdf.parse(updateDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONArray jsonMembers   = new JSONArray();
		JSONArray jsonDegrees   = new JSONArray();
		JSONArray jsonAddresses = new JSONArray();

		HashMap<String, String> result = new HashMap<String, String>();

		try {
			memId = Long.parseLong(memberId);
			AlumniMember memberInfo = memberService.findOne(memId);

			// 요청일자 <= 수정일자 일때
			if (memberInfo.getAuditable().getDateUpdated().compareTo(updateDt) >= 1) {
				JSONObject jsonMember = new JSONObject();

				jsonMember.put("memberId",         memberInfo.getId());
				jsonMember.put("name",             memberInfo.getName());
				jsonMember.put("gender",           memberInfo.getGender().toString());
				jsonMember.put("birthdayOfficial", memberInfo.getBirthdayOfficial());
				jsonMember.put("email",            memberInfo.getEmail());
				jsonMember.put("phone",            memberInfo.getPhone());
				jsonMember.put("mobile",           memberInfo.getMobile());
				jsonMember.put("nationality",      memberInfo.getNationality());
				jsonMembers.put(jsonMember);

				// 학위
				for (Degree degree : memberInfo.getDegrees()) {
					JSONObject jsonDegree = new JSONObject();

					jsonDegree.put("memberId",       degree.getAlumniMember().getId());
					jsonDegree.put("degreeId",       degree.getId());
					jsonDegree.put("degreeType",     degree.getDegreeType().toString());
					jsonDegree.put("deptCode",       degree.getDeptCode());
					jsonDegree.put("deptName",       degree.getDeptName());
					jsonDegree.put("studentId",      degree.getStudentId());
					jsonDegree.put("entranceYear",   degree.getEntranceYear());
					jsonDegree.put("graduationYear", degree.getGraduationYear());
					jsonDegrees.put(jsonDegree);
				}

				// 주소
				for (Address address : memberInfo.getAddresses()) {
					JSONObject jsonAddress = new JSONObject();

					jsonAddress.put("memberId",    address.getAlumniMember().getId());
					jsonAddress.put("addressId",   address.getId());
					jsonAddress.put("addressType", address.getAddressType().toString());
					jsonAddress.put("addressName", address.getAddressName());
					jsonAddress.put("country",     address.getCountry());
					jsonAddress.put("zipCode",     address.getZipCode());
					jsonAddress.put("address1",    address.getAddress1());
					jsonAddress.put("address2",    address.getAddress2());
					jsonAddresses.put(jsonAddress);
				}

				result.put("members", jsonMembers.toString());
				result.put("degrees", jsonDegrees.toString());
				result.put("addresses", jsonAddresses.toString());

			}
		} catch (Exception e) {

		}

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		return new ResponseEntity<String>(result.toString(), resHeaders, HttpStatus.OK);
	}

	// ************************************************************************
	// 5. 회원목록검색
	// input :
	// 이름(name)/학과코드(searchDept)/입학년도(searchEntranceYear)/학위(searchDegreeType)
	// return : 회원 목록 검색 (ADMS -> PODO)
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberSearch")
	public ResponseEntity<String> jsonMemberSearch(HttpServletRequest request,
			@RequestParam Map<String, String> reqParam) throws FileNotFoundException, IOException, JSONException {

		JSONArray jsonMembers   = new JSONArray();
		JSONArray jsonDegrees   = new JSONArray();
		JSONArray jsonAddresses = new JSONArray();

		HashMap<String, String> memberInfo = new HashMap<String, String>();
		HashMap<String, String> result     = new HashMap<String, String>();

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		try {
			List<AlumniMember> memberList = memberService.find(reqParam);

			for (AlumniMember member : memberList) {
				JSONObject jsonMember = new JSONObject();

				jsonMember.put("memberId",         member.getId());
				jsonMember.put("name",             member.getName());
				jsonMember.put("gender",           member.getGender().toString());
				jsonMember.put("birthdayOfficial", member.getBirthdayOfficial());
				jsonMember.put("email",            member.getEmail());
				jsonMember.put("phone",            member.getPhone());
				jsonMember.put("mobile",           member.getMobile());
				jsonMember.put("nationality",      member.getNationality());
				jsonMembers.put(jsonMember);

				// 학위
				for (Degree degree : member.getDegrees()) {
					JSONObject jsonDegree = new JSONObject();

					jsonDegree.put("memberId",       degree.getAlumniMember().getId());
					jsonDegree.put("degreeId",       degree.getId());
					jsonDegree.put("degreeType",     degree.getDegreeType().toString());
					jsonDegree.put("deptCode",       degree.getDeptCode());
					jsonDegree.put("deptName",       degree.getDeptName());
					jsonDegree.put("studentId",      degree.getStudentId());
					jsonDegree.put("entranceYear",   degree.getEntranceYear());
					jsonDegree.put("graduationYear", degree.getGraduationYear());
					jsonDegrees.put(jsonDegree);
				}

				// 주소
				for (Address address : member.getAddresses()) {
					JSONObject jsonAddress = new JSONObject();

					jsonAddress.put("memberId",    address.getAlumniMember().getId());
					jsonAddress.put("addressId",   address.getId());
					jsonAddress.put("addressType", address.getAddressType().toString());
					jsonAddress.put("addressName", address.getAddressName());
					jsonAddress.put("country",     address.getCountry());
					jsonAddress.put("zipCode",     address.getZipCode());
					jsonAddress.put("address1",    address.getAddress1());
					jsonAddress.put("address2",    address.getAddress2());
					jsonAddresses.put(jsonAddress);
				}

				memberInfo.put("members",   jsonMembers.toString());
				memberInfo.put("degrees",   jsonDegrees.toString());
				memberInfo.put("addresses", jsonAddresses.toString());

				result.put("result", memberInfo.toString());
			}

		} catch (Exception e) {

		}

		return new ResponseEntity<String>(result.toString(), resHeaders, HttpStatus.OK);
	}

	// ************************************************************************
	// 6. ADMS에 회원정보 수정요청 (PODO -> ADMS)
	// input : 회원정보(Member+Degree+Address)
	// return : 수정성공여부
	// ************************************************************************
	@RequestMapping(value = "/jsonMemberUpdate")
	public ResponseEntity<String> jsonMemberUpdate(HttpServletRequest request, @RequestParam StringBuffer reqParam)
			throws FileNotFoundException, IOException, JSONException {

		// 전달받은 화일
		JSONObject jsonObj = new JSONObject(reqParam);

		// json 문자열에서 데이터 추출
		JSONArray jsonArrMembers   = jsonObj.getJSONArray("member");
		JSONArray jsonArrDegrees   = jsonObj.getJSONArray("degrees");
		JSONArray jsonArrAddresses = jsonObj.getJSONArray("addresses");

		int memberLength  = jsonArrMembers.length();
		int degreeLength  = jsonArrDegrees.length();
		int addressLength = jsonArrAddresses.length();

		AlumniMember alumniMember = memberService.findOne(jsonArrMembers.getJSONObject(0).getLong("memberId"));

		// 회원정보
		for (int i = 0; i < memberLength; i++) {
			alumniMember = new AlumniMember();

			Long memberId              = jsonArrMembers.getJSONObject(i).getLong("memberId");
			String birthdayReal        = jsonArrMembers.getJSONObject(i).getString("birthdayReal");
			String birthdayType        = jsonArrMembers.getJSONObject(i).getString("birthdayType");
			String email               = jsonArrMembers.getJSONObject(i).getString("email");
			String mobile              = jsonArrMembers.getJSONObject(i).getString("mobile");
			String phone               = jsonArrMembers.getJSONObject(i).getString("phone");
			String isMarried           = jsonArrMembers.getJSONObject(i).getString("isMarried");
			String currentWorkType     = jsonArrMembers.getJSONObject(i).getString("currentWorkType");
			String currentWork		   = jsonArrMembers.getJSONObject(i).getString("currentWork");
			String currentWorkDept	   = jsonArrMembers.getJSONObject(i).getString("currentWorkDept");
			String currentJobTitle     = jsonArrMembers.getJSONObject(i).getString("currentJobTitle");
			String mailingAddress      = jsonArrMembers.getJSONObject(i).getString("mailingAddress");

//			String birthdayOfficial    = jsonArrMembers.getJSONObject(i).getString("birthdayOfficial");
//			String gender              = jsonArrMembers.getJSONObject(i).getString("gender");
//			String isAlive             = jsonArrMembers.getJSONObject(i).getString("isAlive");
//			String membershipFeeStatus = jsonArrMembers.getJSONObject(i).getString("membershipFeeStatus");
//			String nationality         = jsonArrMembers.getJSONObject(i).getString("nationality");
//			String name                = jsonArrMembers.getJSONObject(i).getString("name");

			alumniMember.setId(memberId);
			alumniMember.setBirthdayReal(birthdayReal);
			alumniMember.setBirthdayType(CalType.valueOf(birthdayType));
			alumniMember.setEmail(email);
			alumniMember.setMobile(mobile);
			alumniMember.setPhone(phone);
			alumniMember.setMarried(Boolean.valueOf(isMarried).booleanValue());
			alumniMember.setCurrentWorkType(currentWorkType);
			alumniMember.setCurrentWork(currentWork);
			alumniMember.setCurrentWorkDept(currentWorkDept);
			alumniMember.setCurrentJobTitle(currentJobTitle);
			alumniMember.setMailingAddress(mailingAddress);

//			alumniMember.setBirthdayOfficial(birthdayOfficial);
//			alumniMember.setGender(Gender.valueOf(gender));
//			alumniMember.setAlive(Boolean.valueOf(isAlive).booleanValue());
//			alumniMember.setMembershipFeeStatus(MembershipFeeStatus.valueOf(membershipFeeStatus));
//			alumniMember.setNationality(nationality);
//			alumniMember.setName(name);

			// 학위정보는 PODO에서 업데이트 불가. 학사정보에서 업데이트 해야함
//			for (int j = 0; j < degreeLength; j++) {
//
//				Long degreeMemberId   = jsonArrMembers.getJSONObject(j).getLong("memberId");
//				Long degreeId         = jsonArrMembers.getJSONObject(j).getLong("degreeId");
//				String degreeType     = jsonArrMembers.getJSONObject(j).getString("degreeType");
//				String deptCode       = jsonArrMembers.getJSONObject(j).getString("deptCode");
//				String deptName       = jsonArrMembers.getJSONObject(j).getString("deptName");
//				String studentId      = jsonArrMembers.getJSONObject(j).getString("studentId");
//				String entranceYear   = jsonArrMembers.getJSONObject(j).getString("entranceYear");
//				String graduationYear = jsonArrMembers.getJSONObject(j).getString("graduationYear");
//
//				Degree addDegree = new Degree();
//				addDegree.setId(degreeId);
//				addDegree.setDegreeType(DegreeType.valueOf(degreeType));
//				addDegree.setDeptCode(deptCode);
//				addDegree.setDeptName(deptName);
//				addDegree.setEntranceYear(entranceYear);
//				addDegree.setGraduationYear(graduationYear);
//				addDegree.setStudentId(studentId);
//
//				alumniMember.addDegree(addDegree);
//
//			}

			// 주소정보
			for (int k = 0; k < addressLength; k++) {

				Long AddrMemberId  = jsonArrMembers.getJSONObject(k).getLong("memberId");
				Long addressId     = jsonArrMembers.getJSONObject(k).getLong("addressId");
				String addressType = jsonArrMembers.getJSONObject(k).getString("addressType");
				String addressName = jsonArrMembers.getJSONObject(k).getString("addressName");
				String zipCode     = jsonArrMembers.getJSONObject(k).getString("zipCode");
				String address1    = jsonArrMembers.getJSONObject(k).getString("address1");
				String address2    = jsonArrMembers.getJSONObject(k).getString("address2");
				String address3    = jsonArrMembers.getJSONObject(k).getString("address3");
				String address4    = jsonArrMembers.getJSONObject(k).getString("address4");
				String country     = jsonArrMembers.getJSONObject(k).getString("country");

				Address addAddress = new Address();
				addAddress.setId(addressId);
				addAddress.setAddressType(addressType);
				addAddress.setAddressName(addressName);
				addAddress.setZipCode(zipCode);
				addAddress.setAddress1(address1);
				addAddress.setAddress2(address2);
				addAddress.setAddress3(address3);
				addAddress.setAddress4(address4);
				addAddress.setCountry(country);

				alumniMember.addAddress(addAddress);
			}
		}

		HashMap<String, String> result = new HashMap<String, String>();
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		try {
			memberService.update(alumniMember);
			result.put("result", "Y");
			return new ResponseEntity<String>(result.toString(), resHeaders, HttpStatus.OK);
		} catch (Exception e) {
			result.put("result", "N");
			return new ResponseEntity<String>(result.toString(), resHeaders, HttpStatus.BAD_REQUEST);
		}
	}

}
