package com.email.service;

import com.email.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
