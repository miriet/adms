package postech.adms.repository.member;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.member.AlumniMember;

public interface MemberRepository extends JpaQueryDslBaseRepository<AlumniMember, Long>, CustomMemberRepository {
	public AlumniMember findByName(String name);
	// public AlumniMember findByMemberId(Long memberId);
	public AlumniMember findByNameAndBirthdayOfficial(String name, String birthdayOfficial);
	public AlumniMember findByNameAndBirthdayReal(String name, String birthdayReal);
}
