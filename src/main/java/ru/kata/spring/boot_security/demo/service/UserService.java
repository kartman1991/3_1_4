package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> findAll();

    public User findById(Long id);

    public void save(User user);

    public void update(User user);

    public void delete(Long id);
}
