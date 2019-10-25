package br.com.rmsystems.auditlog.repositories.h2;

import br.com.rmsystems.auditlog.model.h2.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findUserByLogin(String login);
}
