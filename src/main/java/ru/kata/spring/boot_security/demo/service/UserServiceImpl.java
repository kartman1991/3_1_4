package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }
    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }
    @Transactional
    public void update(User user) {
        userDAO.update(user);
    }
    @Transactional
    public void delete(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userDAO.loadUserByUsername(username);
    }

    public static String getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String s = "";
        for (GrantedAuthority g : authentication.getAuthorities()) {
            s = s + g;
        }
        return s.replace("ROLE_", " ");
    }
}
