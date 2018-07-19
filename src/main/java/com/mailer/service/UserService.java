package com.mailer.service;

import com.mailer.model.User;
/**
 * This interface provide abstract methods that has to be implemented by service implementation classes
 * @author vishnu
 *
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
