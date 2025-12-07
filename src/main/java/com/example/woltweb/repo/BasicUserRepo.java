package com.example.woltweb.repo;

import com.example.woltweb.model.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepo extends JpaRepository<BasicUser, Integer> {
}
