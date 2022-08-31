package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        user.setRoleSet(Collections.singleton(new Role((long) 1, "ROLE_ADMIN")));
//        System.out.println(user.isAdmin());
//        if (confirm.equals("on")) {
//            Set<Role> set = user.getRoleSet();
//            set.add(new Role((long) 2, "ROLE_ADMIN"));
//            user.setRoleSet(set);
//        }
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        entityManager.remove(user);
        entityManager.flush();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        try {
            user = entityManager.createQuery("SELECT u FROM User u where u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (Exception e){
            throw new UsernameNotFoundException("This username not found");
        }
        return user;
    }
}