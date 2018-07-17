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
 * This class implements the UserService interface
 * This class provides methods to persist the information provided by new users.
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
    
    /**
     * This method persists the information provided by the user.
     * @param user is the User pojo that has to be persisted 
     */
    @Override
    public void save(User user) 
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       // user.setRoles(new HashSet<>(/*roleRepository.findAll()*/));
        userRepository.save(user);
    }
    /**
     * This method is used to find user based on username
     * @param username is the username of the user
     * @return User object is returned
     */
    @Override
    public User findByUsername(String username) 
    {
        return userRepository.findByUsername(username);
    }
}
