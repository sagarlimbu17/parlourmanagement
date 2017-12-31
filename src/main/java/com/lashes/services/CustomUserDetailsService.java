package com.lashes.services;


import com.lashes.dao.UserDao;
import com.lashes.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.lashes.entities.User user = userDao.findByUsername(username);
        List<GrantedAuthority> authorities =buildUserAuthority(user.getUserRole());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.lashes.entities.User user, List<GrantedAuthority> authorities){
        return  new User(user.getUsername(),user.getPassword(),user.isEnabled(),true,
                true,true,authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles){
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        for(UserRole userRole : userRoles){
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }
}
