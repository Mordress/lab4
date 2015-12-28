package com.gmail.mordress.epamproject.services.ifaces;

import com.gmail.mordress.epamproject.entities.Bet;
import com.gmail.mordress.epamproject.entities.User;
import com.gmail.mordress.epamproject.exceptions.PersistentException;

import java.util.List;

public interface BetService extends Service{

    public List<Bet> findAllBetsByUser(User user) throws PersistentException;

    public List<Bet> findNotFixedBets() throws PersistentException;

    public List<Bet> findNoFinishedBets() throws PersistentException;

    public void save(Bet bet) throws PersistentException;

    public Bet read(Integer id) throws PersistentException;

    public void delete(Integer id) throws PersistentException;
}