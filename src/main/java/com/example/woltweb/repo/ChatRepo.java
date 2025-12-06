package com.example.woltweb.repo;

import com.example.woltweb.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Integer> {
}
