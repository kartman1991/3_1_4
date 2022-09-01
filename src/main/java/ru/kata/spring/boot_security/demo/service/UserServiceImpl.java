package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
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
//            throw new UsernameNotFoundException("User not found");
    }
}
