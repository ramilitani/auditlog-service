package br.com.rmsystems.auditlog.jms;

import br.com.rmsystems.auditlog.model.mongo.Audit;
import br.com.rmsystems.auditlog.service.AuditService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;

@Service
public class ConsumerService {

    private Logger logger = LogManager.getLogger(ProducerService.class);

    @Autowired
    AuditService auditService;

    @JmsListener(destination = "audit.queue")
    public void receiveMessage(Audit audit, Message message) {
        logger.info("Received message from audit.queue. payload: {}", audit.toString());
        auditService.saveAudit(audit);
    }
}
