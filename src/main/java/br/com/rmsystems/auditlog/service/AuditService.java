package br.com.rmsystems.auditlog.service;

import br.com.rmsystems.auditlog.dto.JSession;
import br.com.rmsystems.auditlog.jms.ProducerService;
import br.com.rmsystems.auditlog.model.mongo.Audit;
import br.com.rmsystems.auditlog.repositories.mongo.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.*;

/**
 * The audit service
 */
@Service
public class AuditService {

    private Logger logger = LogManager.getLogger(AuditService.class);

    @Autowired
    ProducerService sender;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    SessionService sessionService;

    /**
     * Make the audit message through the request and call the sendMessage method
     * to put it in ActiveMQ
     * @param requestWrapper the request
     * @param responseWrapper the response
     */
    public void queueAudit(ContentCachingRequestWrapper requestWrapper,
                           ContentCachingResponseWrapper responseWrapper) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestBody = new String(requestWrapper.getContentAsByteArray());
            String responseBody = new String(responseWrapper.getContentAsByteArray());
            Map body = StringUtils.isEmpty(requestBody) ? null : mapper.readValue(requestBody, Map.class);
            Map response = StringUtils.isEmpty(responseBody) ? null : mapper.readValue(responseBody, Map.class);
            JSession session = sessionService.getCurrentSession();
            Audit audit = Audit.builder()
                ._id(new ObjectId())
                .actionDate(new Date())
                .actionUrl(requestWrapper.getContextPath() + requestWrapper.getServletPath())
                .header(getHeadersInfo(requestWrapper))
                .body(body)
                .response(response)
                .session(session)
                .build();
            sender.sendMessage("audit.queue", audit);

        } catch (Exception ex) {
            logger.error("Error saving audit: {} ", ex);
        }
    }

    /**
     * Save the audit message on MongoDB
     * @param audit the audit message
     */
    public void saveAudit(Audit audit) {
        auditRepository.save(audit);
    }

    /**
     * List the audit messages by userId, besides receive the page and size parameters used in the pagination
     * @param userId the user id
     * @param page the page to be search
     * @param size the page size
     * @return the list of audit messages
     */
    public List<Audit> findAuditByUser(Long userId, int page, int size) {
        Page<Audit> auditPage = auditRepository.findBySessionUserId(userId, PageRequest.of(page,size));
        return auditPage.getContent();
    }

    /**
     * List all the audit messages store on database, besides receive the page and size parameters used in the pagination
     * @param page the page to be search
     * @param size the page size
     * @return the list of audit messages
     */
    public List<Audit> listAllAudit(int page, int size) {
        Page<Audit> auditPage = auditRepository.findAll(PageRequest.of(page, size));
        return auditPage.getContent();
    }

    /**
     * Get the header info from the request
     * @param request the request
     * @return the map with the header information
     */
    private Map<String, String> getHeadersInfo(ContentCachingRequestWrapper request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
