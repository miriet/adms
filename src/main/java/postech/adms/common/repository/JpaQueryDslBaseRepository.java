package postech.adms.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface JpaQueryDslBaseRepository <T, ID extends Serializable> extends JpaRepository<T, ID>,QueryDslPredicateExecutor<T>{

}
