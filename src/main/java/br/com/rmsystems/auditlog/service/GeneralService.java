package br.com.rmsystems.auditlog.service;

import br.com.rmsystems.auditlog.dto.JSession;
import br.com.rmsystems.auditlog.exception.ValidateException;
import br.com.rmsystems.auditlog.model.h2.Session;
import br.com.rmsystems.auditlog.model.h2.User;
import br.com.rmsystems.auditlog.repositories.h2.JSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/*
    This service is intended to group various services for the testing proposal
    simulating general actions in an e-commerce system.
    It was done this way because this service is beyond the scope of this project, so
    it doesn't follow the correct design patterns (to separate them into different classes).
    The services that are the purpose of this project are named AuditService, ConsumerService and ProducerService.
 */
@Service
public class GeneralService {

    Logger logger = LoggerFactory.getLogger(GeneralService.class);

    @Autowired
    UserService userService;

    @Autowired
    JSessionRepository jSessionRepository;

    /**
     * Method responsible to put user in session
     * @param login the login of the user
     * @param password the password of the user
     * @param session the http session
     * @return the JSession
     */
    public JSession login(String login, String password, HttpSession session) {

        logger.info("logging in user ({},{})", login, password);
        try
        {
            User user = userService.findUserByLogin(login);
            if (user.getPassword().equals(password)) {
                JSession jSession = JSession.builder()
                        .login(login)
                        .userId(user.getId())
                        .sessaoId(session.getId())
                        .build();
                session.setAttribute("JSESSION", jSession);
                return jSession;

            } else {
                throw new ValidateException("Login or password invalid.");
            }
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    /**
     * Method to logout user of the session
     * @param s the session
     */
    public void logout(HttpSession s) {
        Session session = jSessionRepository.findBySessionId(s.getId());
        if (session != null) {
            jSessionRepository.delete(session);
        }
    }
}