package br.com.rmsystems.auditlog.repositories.h2;

import br.com.rmsystems.auditlog.model.h2.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JSessionRepository extends JpaRepository<Session, String> {

    Session findBySessionId(String sessionId);
}
