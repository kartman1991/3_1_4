package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        Set<Role> set = new HashSet<>();

        if (user.getRol().contains("1")) {
            set.add(roleUser);
        }
        if (user.getRol().contains("2")) {
            set.add(roleAdmin);
        }
        user.setAuthorities(set);

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
            user = entityManager.createQuery("SELECT u FROM User u JOIN fetch u.roleSet where u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (NoResultException e){
            throw new NoResultException("User not found");
        }
        return user;
    }
}