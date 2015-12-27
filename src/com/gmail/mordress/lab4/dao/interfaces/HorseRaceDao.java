package com.gmail.mordress.lab4.dao.interfaces;

import com.gmail.mordress.lab4.entities.Horse;
import com.gmail.mordress.lab4.entities.HorseRace;
import com.gmail.mordress.lab4.entities.Race;
import com.gmail.mordress.lab4.exceptions.PersistentException;
import java.util.List;

public interface HorseRaceDao extends Dao<HorseRace> {

    public List<HorseRace> findByRace(Race race) throws PersistentException;

    public HorseRace findByRaceAndHorse(Integer horseId, Integer raceId) throws PersistentException;

    public List<HorseRace> getAllHorseRaces() throws PersistentException;

    public List<HorseRace> getHorseRacePerHorse(Horse horse) throws PersistentException;



}
