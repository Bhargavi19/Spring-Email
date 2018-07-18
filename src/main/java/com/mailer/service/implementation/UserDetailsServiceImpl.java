package com.mailer.service.implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.mailer.model.Role;
import com.mailer.model.User;
import com.mailer.repository.UserRepository;
/**
 * This class implements the UserDetailsService.
 * 
 * @author vishnu
 *
 */
@Service

public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    
    /**
     * @param username provides the username of the User
     * @return UserDetails has necessary information about the user
     * @exception UsernameNotFoundException if the username is not found 
     */
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
		       /* for (Role role : user.getRoles())
		        {
		            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		        }*/
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
