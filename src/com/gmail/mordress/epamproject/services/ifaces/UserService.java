package com.gmail.mordress.epamproject.services.ifaces;

import com.gmail.mordress.epamproject.entities.User;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import java.math.BigDecimal;
import java.util.List;

public interface UserService extends Service {

    public User findUser(String login, String password) throws PersistentException;

    public boolean checkUniqueLogin(String login) throws PersistentException;

    public List<User> getAllSimpleUsers() throws PersistentException;

    public User findById(Integer id) throws PersistentException;

    public void save(User user) throws PersistentException;

    public void delete(Integer id) throws PersistentException;

    public void updateUserCash(Integer userId, BigDecimal newCashAmount) throws PersistentException;

}