package br.com.rmsystems.auditlog.service;

import br.com.rmsystems.auditlog.exception.ValidateException;
import br.com.rmsystems.auditlog.model.h2.User;
import br.com.rmsystems.auditlog.repositories.h2.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The user service
 */
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;


    /**
     * Find a user by login
     * @param login the login of user
     * @return the user
     */
    public User findUserByLogin(String login) {
        logger.info("call method UserService.findUserByLogin({})", login);
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
           throw new ValidateException("User " + login + " not found.");
        }
        return user;
    }
}