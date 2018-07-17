package com.mailer.service;

import com.mailer.model.User;
/**
 * 
 * @author vishnu
 *
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
