package com.vishnu.project.service.implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishnu.project.model.Role;
import com.vishnu.project.model.User;
import com.vishnu.project.repository.UserRepository;

@Service

public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)  
    {
    	
        User user = userRepository.findByUsername(username);
    	
        	
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        try
        {
	        if(user != null)
	        {
		        for (Role role : user.getRoles())
		        {
		            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		        }
		        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	        }
	        
	        return null;
	        
        }
        catch(Exception e)
        {
        	throw new UsernameNotFoundException(username);
        }
      }
}
