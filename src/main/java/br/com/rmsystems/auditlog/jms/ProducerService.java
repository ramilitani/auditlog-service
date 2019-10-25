package br.com.rmsystems.auditlog.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerService {

    private Logger logger = LogManager.getLogger(ProducerService.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String queue, Object payload) {

        logger.info("Sending message to {}, payload: {}", queue, payload.toString());

        jmsTemplate.convertAndSend(queue, payload);
    }
}
