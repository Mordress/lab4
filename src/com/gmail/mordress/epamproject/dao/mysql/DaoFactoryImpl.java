package com.gmail.mordress.epamproject.dao.mysql;

import com.gmail.mordress.epamproject.dao.interfaces.*;
import com.gmail.mordress.epamproject.dao.pool.ConnectionPool;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DaoFactoryImpl implements DaoFactory {

    private static Logger logger = Logger.getLogger(DaoFactoryImpl.class);

    private static Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(BreedDao.class, BreedDaoImpl.class);
        classes.put(HorseDao.class, HorseDaoImpl.class);
        classes.put(RaceDao.class, RaceDaoImpl.class);
        classes.put(HorseRaceDao.class, HorseRaceDaoImpl.class);
        classes.put(BetDao.class, BetDaoImpl.class);
        classes.put(UserDao.class, UserDaoImpl.class);
    }

    private Connection connection;

    public DaoFactoryImpl() throws PersistentException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(true);
        } catch(SQLException e) {
            throw new PersistentException(e.getMessage(), e.getCause());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Dao<?>> T createDao(Class<? extends Dao<?>> key) throws PersistentException {
        Class<? extends BaseDaoImpl> value = classes.get(key);
        if(value != null) {
            try {
                BaseDaoImpl dao = value.newInstance();
                dao.setConnection(connection);
                return (T)dao;
            } catch(InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to create DAO", e);
                throw new PersistentException(e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch(SQLException e) {}
    }
}
