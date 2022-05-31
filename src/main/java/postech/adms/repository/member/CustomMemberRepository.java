package postech.adms.repository.member;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import postech.adms.domain.member.AlumniMember;
import postech.adms.dto.member.MemberListDto;

import java.util.List;

public interface CustomMemberRepository {
	public Page<MemberListDto> readMembers(Predicate predicate, Pageable pageable, boolean isDgreeJoin);

	public Page<AlumniMember> readAlumniMembers(Predicate predicate, Pageable pageable, boolean isDgreeJoin);

	public Page<MemberListDto> readMembersWithoutUserRoles(Predicate predicate, Pageable pageable);

	public List<AlumniMember> readMemberList(Predicate predicate, boolean isDgreeJoin);
	
	public Page<MemberListDto> readAlumMembers(Predicate predicate, Pageable pageable);
	
	public List<AlumniMember> readAlumMembersList(Predicate predicate);
	
}
