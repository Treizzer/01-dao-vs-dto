package com.treizer.dao_vs_dto.persistence.DAOs.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.treizer.dao_vs_dto.persistence.DAOs.interfaces.ICommonDao;
import com.treizer.dao_vs_dto.persistence.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDao implements ICommonDao<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return this.em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(this.em.find(User.class, id));
    }

    @Override
    public User save(User user) {
        // return this.em.persist(user);
        // var newUser = this.em.merge(user);
        // this.em.flush();
        // return newUser;
        return this.em.merge(user);
    }

    @Override
    public User update(User user) {
        return this.em.merge(user);
    }

    @Override
    public void delete(User user) {
        this.em.remove(user);
    }

}
