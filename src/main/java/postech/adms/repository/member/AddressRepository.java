package postech.adms.repository.member;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.member.Address;

public interface AddressRepository extends JpaQueryDslBaseRepository<Address, Long> {
	public Address findById(Long id);
}
