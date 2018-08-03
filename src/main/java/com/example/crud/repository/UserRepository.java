package com.example.crud.repository;

import com.example.crud.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    public User getUserById(Long id){
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void saveUser(User user){
        entityManager.merge(user);
    }

    @Transactional
    public void deleteUserById(Long id){
        User user = new User();
        user.setId(id);
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }
}
