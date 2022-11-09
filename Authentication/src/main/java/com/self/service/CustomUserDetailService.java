package com.self.service;

import com.self.model.CustomUserDetails;
import com.self.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByUsername(username);
        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Username not Found"));
//        return optionalUser.map(CustomUserDetails::new).get();
        //    return new CustomUserDetails(optionalUser.get());
//
        List<GrantedAuthority> authorityList = optionalUser.get().getRoles().stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.getRole_name()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getUsername(), optionalUser.get().getPassword(), authorityList);

    }
}
