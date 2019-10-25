package br.com.rmsystems.auditlog.repositories.mongo;

import br.com.rmsystems.auditlog.model.mongo.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {

    @Override
    Page<Audit> findAll(Pageable pageable);
    Page<Audit> findBySessionUserId(Long userId, Pageable pageable);
}
