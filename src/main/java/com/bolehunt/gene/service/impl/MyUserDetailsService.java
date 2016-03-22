package com.bolehunt.gene.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolehunt.gene.domain.Role;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("Load user by username " + username);
        
        User user = userService.getUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else {
        	return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
                    true, true, true, true, getGrantedAuthorities(user));
        }

    }
    
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Role> roleList = userService.getRoleListByUser(user);
        for(Role role : roleList){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}

