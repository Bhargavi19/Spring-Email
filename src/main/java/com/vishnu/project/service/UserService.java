package com.vishnu.project.service;

import com.vishnu.project.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
