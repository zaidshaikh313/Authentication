package com.self.repos;

import com.self.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepos extends JpaRepository<Role,Integer> {
}
