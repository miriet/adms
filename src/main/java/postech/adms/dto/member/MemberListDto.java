package postech.adms.dto.member;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.mysema.query.annotations.QueryProjection;

import postech.adms.domain.codes.Gender;
import postech.adms.domain.member.AlumniMember;

public class MemberListDto implements Serializable{

	private static final long serialVersionUID = -6416512232653596336L;

	private Long memberId;
	private String birthday;
	private String name;
	private String mobile;
	private String email;
	private String department;
	private String degree;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private boolean isAlive;
 
	@QueryProjection
	public MemberListDto(AlumniMember member){
		this.memberId = member.getId();
		this.birthday = member.getBirthdayOfficial(); 
		this.name = member.getName();
		this.mobile = member.getMobile();
		this.email = member.getEmail();
		this.department = (member.getDegrees() == null || member.getDegrees().isEmpty()) ? "Not Registered" : member.getDegrees().get(0).getDeptName();
		this.degree = getDegreeType(member);
		this.gender = member.getGender();
		this.isAlive = member.isAlive();
	}

	private String getDegreeType(AlumniMember member) {
		String result = "Not Registered";
		if (member.getDegrees() != null && !member.getDegrees().isEmpty() && member.getDegrees().get(0).getDegreeType() != null) {
			result=member.getDegrees().get(0).getDegreeType().name();
		}
		return result;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}

	public String getDegree() {
		return degree;
	}

	public Gender getGender() {
		return gender;
	}

	public boolean isAlive() {
		return isAlive;
	}
}
