package com.mailer.service;
/**
 * 
 * @author vishnu
 *
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
