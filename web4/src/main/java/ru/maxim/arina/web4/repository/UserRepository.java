package ru.maxim.arina.web4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.arina.web4.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}

