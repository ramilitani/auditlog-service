package br.com.rmsystems.auditlog.service;

import br.com.rmsystems.auditlog.dto.JSession;
import br.com.rmsystems.auditlog.exception.ValidateException;
import br.com.rmsystems.auditlog.model.h2.Session;
import br.com.rmsystems.auditlog.repositories.h2.JSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpSession;

/**
 * The Session Service
 */
@Service
public class SessionService {
    Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    HttpSession httpSession;

    @Autowired
    JSessionRepository JSessionRepository;

    /**
     * Validate session through the x-auth-token parameter retrieved from the request header
     * @param request the request
     */
    public void verifySession(ContentCachingRequestWrapper request) {
        boolean isAuthenticated = false;
        String token = request.getHeader("x-auth-token");
        if (!StringUtils.isEmpty(token)) {
            isAuthenticated = isValidToken(token);
        }

        if (!isAuthenticated) {
            throw new ValidateException("User not authenticated.");
        }
    }

    /**
     * Get the current session
     * @return the JSession
     */
    public JSession getCurrentSession() {
        return (JSession) httpSession.getAttribute("JSESSION");
    }

    /**
     * Verify if token is valid
     * @param token the token
     * @return true if valid, false otherwise
     */
    private boolean isValidToken(final String token) {
        logger.info("validating token: {})", token);
        boolean isValid = true;
        Session session = JSessionRepository.findBySessionId(token);
        if (session == null) {
            isValid = false;
        }
        return isValid;
    }
}
