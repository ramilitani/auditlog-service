package br.com.rmsystems.auditlog;

import br.com.rmsystems.auditlog.repositories.h2.JSessionRepository;
import br.com.rmsystems.auditlog.repositories.mongo.AuditRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = AuditRepository.class)
@EnableJpaRepositories(basePackageClasses = JSessionRepository.class)
@EnableJms
public class AuditlogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditlogServiceApplication.class, args);
	}

}
