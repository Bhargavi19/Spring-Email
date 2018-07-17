package com.mailer.service.implementation;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mailer.model.User;
//import com.mailer.repository.RoleRepository;
import com.mailer.repository.UserRepository;
import com.mailer.service.UserService;
/**
 * 
 * @author vishnu
 *
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    /*@Autowired
    private RoleRepository roleRepository;*/
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) 
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       // user.setRoles(new HashSet<>(/*roleRepository.findAll()*/));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) 
    {
        return userRepository.findByUsername(username);
    }
}
