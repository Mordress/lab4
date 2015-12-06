package com.gmail.mordress.lab4.dao.interfaces;

import com.gmail.mordress.lab4.exceptions.DaoException;
import com.gmail.mordress.lab4.domain.Bet;
import com.gmail.mordress.lab4.domain.User;

import java.util.List;

public interface UserDao extends Dao<User> {

    public User findUser(String login, String password) throws DaoException;

    public boolean checkUniqueLogin(String login) throws DaoException;

    public boolean isSimpleUser(User user) throws DaoException;

    public boolean isPositiveCashAmount(User user) throws DaoException;

    public List<User> getAllSimpleUsers() throws DaoException;

    public List<User> getAllUsers() throws DaoException;

    public List<Bet> findAllBetsByUser(User user) throws DaoException;
}