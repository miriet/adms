package postech.adms.repository.member;

import java.util.List;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;

public interface DegreeRepository extends JpaQueryDslBaseRepository<Degree, Long> {
	public Degree findById(Long id);
	public List<Degree> findByAlumniMember(AlumniMember user);
}
