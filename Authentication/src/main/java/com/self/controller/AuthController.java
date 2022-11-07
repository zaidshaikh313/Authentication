package com.self.controller;


import com.self.dto.LoginRequest;
import com.self.dto.RegisterRequest;
import com.self.model.User;
import com.self.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String get(){
        return "Yeah we got it ";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user){
        return ResponseEntity.ok( userService.addUser(user));
    }


}
