package com.self.service;

import com.self.dto.LoginRequest;
import com.self.dto.LoginResponse;
import com.self.dto.RegisterRequest;
import com.self.exceptions.UserAlreadyExistException;
import com.self.model.Role;
import com.self.model.User;
import com.self.repos.RoleRepos;
import com.self.repos.UserRepos;
import com.self.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepos userRepos;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private RoleRepos roleRepos;

    Optional<User> findByUsername(String username) {
        return userRepos.findByUsername(username);
    }

    public ResponseEntity<?> login(LoginRequest user) {
        Authentication authentication = null;
        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(user1.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    public LoginResponse addUser(RegisterRequest user) {
        if (userRepos.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User is present already");
        }

        Set<Role> roles = new HashSet<>();
//        user.getRole().forEach(inRole -> {
//            Role role = new Role();
//            role.setRole_name(inRole);
//            roles.add(role);
//        });
        Role role = roleRepos.findById(1).get();
        roles.add(role);
        User user1 = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), roles);
        userRepos.save(user1);
        return new LoginResponse("User added");
    }


}
