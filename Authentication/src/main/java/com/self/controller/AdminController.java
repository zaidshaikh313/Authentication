package com.self.controller;

import com.self.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    ResponseEntity<LoginResponse> get(){
        System.out.println();
        return ResponseEntity.ok(new LoginResponse("u r the bosss"));
    }
}
