package com.self.repos;


import com.self.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepos extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String username);
}
