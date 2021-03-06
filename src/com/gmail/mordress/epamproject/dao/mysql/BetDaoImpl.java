package com.gmail.mordress.epamproject.dao.mysql;

import com.gmail.mordress.epamproject.dao.interfaces.BetDao;
import com.gmail.mordress.epamproject.entities.Bet;
import com.gmail.mordress.epamproject.entities.HorseRace;
import com.gmail.mordress.epamproject.entities.User;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides additional operations for bet-interaction with mysql.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class BetDaoImpl extends BaseDaoImpl implements BetDao {

    private static Logger logger = Logger.getLogger(BetDaoImpl.class);

    /** Returns all bets by some user.
     * @param instance - user.
     * @return List of user's bets
     * @throws PersistentException - if DBMS can't successful complete this operation.
     */
    @Override
    public List<Bet> findAllBetsByUser(User instance) throws PersistentException {
        String sql = "SELECT * FROM `bet` WHERE `user_ID` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, instance.getId());
            resultSet = statement.executeQuery();
            List<Bet> bets = new ArrayList<>();
            Bet bet = null;
            while (resultSet.next()) {
                bet = new Bet();
                bet.setId(resultSet.getInt("bet_ID"));
                bet.setResultRank(resultSet.getInt("result_rank"));
                bet.setBetAmount(resultSet.getBigDecimal("bet_amount"));
                bet.setWinAmount(resultSet.getBigDecimal("win_amount"));
                bet.setIsWinner(resultSet.getBoolean("is_winner"));
                bet.setUser(instance);
                HorseRace horseRace = new HorseRace();
                horseRace.setId(resultSet.getInt("horse_race_ID"));
                bet.setHorseRace(horseRace);
                bet.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            logger.error(String.format("Can not find bets by user \"%s\"", instance.getLogin()));
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }

    /** Returns all bets, which winned in racing.
     * @return List of bets.
     * @throws PersistentException - if DBMS can't successful complete this operation.
     */
    @Override
    public List<Bet> findWinnedBets() throws PersistentException {
        String sql = "SELECT * FROM `bet` WHERE `is_winner` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, new Boolean(true));
            resultSet = statement.executeQuery();
            List<Bet> bets = new ArrayList<>();
            Bet bet = null;
            while (resultSet.next()) {
                bet = new Bet();
                bet.setId(resultSet.getInt("bet_ID"));
                bet.setResultRank(resultSet.getInt("result_rank"));
                bet.setBetAmount(resultSet.getBigDecimal("bet_amount"));
                bet.setWinAmount(resultSet.getBigDecimal("win_amount"));
                bet.setIsWinner(resultSet.getBoolean("is_winner"));
                User user = new User();
                user.setId(resultSet.getInt("user_ID"));
                bet.setUser(user);
                HorseRace horseRace = new HorseRace();
                horseRace.setId(resultSet.getInt("horse_race_ID"));
                bet.setHorseRace(horseRace);
                bet.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            logger.error("Can not find winned bets.");
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }

    /** Returns all bets, which winned in racing, by some user.
     * @param instance - user.
     * @return List of user's bets.
     * @throws PersistentException - if DBMS can't successful complete this operation.
     */
    @Override
    public List<Bet> findWinnedBetsByUser(User instance) throws PersistentException {
        String sql = "SELECT * FROM `bet` WHERE (`user_ID` = ? AND `is_winner` = ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, instance.getId());
            statement.setBoolean(2, new Boolean(true));
            resultSet = statement.executeQuery();
            List<Bet> bets = new ArrayList<>();
            Bet bet = null;
            while (resultSet.next()) {
                bet = new Bet();
                bet.setId(resultSet.getInt("bet_ID"));
                bet.setResultRank(resultSet.getInt("result_rank"));
                bet.setBetAmount(resultSet.getBigDecimal("bet_amount"));
                bet.setWinAmount(resultSet.getBigDecimal("win_amount"));
                bet.setIsWinner(resultSet.getBoolean("is_winner"));
                bet.setUser(instance);
                HorseRace horseRace = new HorseRace();
                horseRace.setId(resultSet.getInt("horse_race_ID"));
                bet.setHorseRace(horseRace);
                bet.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            logger.error(String.format("Can not find winned bets by user \"%s\"", instance.getLogin()));
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }

    /** Returns all bets, which have no been processed by bookmaker.
     * @return List of bets.
     * @throws PersistentException - if DBMS can't successful complete this operation.
     */
    @Override
    public List<Bet> findNotCompleteBets() throws PersistentException {
        String sql = "SELECT * FROM `bet` WHERE `win_amount` IS NULL";
        PreparedStatement statement = null;
        ResultSet resultSet= null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            List<Bet> bets = new ArrayList<>();
            Bet bet = null;
            while (resultSet.next()) {
                bet = new Bet();
                bet.setId(resultSet.getInt("bet_ID"));
                bet.setResultRank(resultSet.getInt("result_rank"));
                bet.setBetAmount(resultSet.getBigDecimal("bet_amount"));
                bet.setWinAmount(null);
                bet.setIsWinner(null);
                User user = new User();
                user.setId(resultSet.getInt("user_ID"));
                bet.setUser(user);
                HorseRace horseRace = new HorseRace();
                horseRace.setId(resultSet.getInt("horse_race_ID"));
                bet.setHorseRace(horseRace);
                bet.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            logger.error("Can not take no-complete bets form db");
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }

    @Override
    public Integer create(Bet instance) throws PersistentException {
        String sql = "INSERT INTO `bet` (`horse_race_ID`, `result_rank`, `bet_amount`, `win_amount`," +
                " `is_winner`, `user_ID`, `created_date`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, instance.getHorseRace().getId());
            statement.setInt(2, instance.getResultRank());
            statement.setBigDecimal(3, instance.getBetAmount());
            if (instance.getWinAmount() != null) {
                statement.setBigDecimal(4, instance.getWinAmount());
            } else {
                statement.setBigDecimal(4, null);
            }
            if (instance.getIsWinner() != null) {
                statement.setBoolean(5, instance.getIsWinner());
            } else {
                statement.setBoolean(5, false);
            }
            statement.setInt(6, instance.getUser().getId());
            statement.setTimestamp(7, new java.sql.Timestamp(instance.getCreatedDate().getTime()));
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                logger.info("Bet with id = " + instance.getId() + " successfully created!");
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `bets`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            logger.error("Can not create bet with id = " + instance.getId());
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch(SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public Bet read(Integer id) throws PersistentException {
        String sql = "SELECT  * FROM `bet` WHERE `bet_ID` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Bet bet = null;
            if (resultSet.next()) {
                bet = new Bet();
                bet.setId(id);
                HorseRace horseRace = new HorseRace();
                horseRace.setId(resultSet.getInt("horse_race_ID"));
                bet.setHorseRace(horseRace);
                bet.setResultRank(resultSet.getInt("result_rank"));
                bet.setIsWinner(resultSet.getBoolean("is_winner"));
                bet.setBetAmount(resultSet.getBigDecimal("bet_amount"));
                bet.setWinAmount(resultSet.getBigDecimal("win_amount"));
                User user = new User();
                user.setId(resultSet.getInt("user_ID"));
                bet.setUser(user);
                bet.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
            }
            return bet;
        } catch (SQLException e) {
            logger.error("Can not read bet with id = " + id);
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                resultSet.close();
            } catch(SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void update(Bet instance) throws PersistentException {
        String sql = "UPDATE `bet` SET `horse_race_ID` = ?, `result_rank` = ?,`bet_amount` = ?, " +
                "`win_amount` = ?, `is_winner` = ?, `created_date` = ?, `user_ID` = ? WHERE `bet_ID` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, instance.getHorseRace().getId());
            statement.setInt(2, instance.getResultRank());
            statement.setBigDecimal(3, instance.getBetAmount());
            statement.setBigDecimal(4, instance.getWinAmount());
            statement.setBoolean(5, instance.getIsWinner());
            statement.setTimestamp(6, new java.sql.Timestamp(instance.getCreatedDate().getTime()));
            statement.setInt(7, instance.getUser().getId());
            statement.setInt(8, instance.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update bet with id = " + instance.getId());
            throw new PersistentException(e.getMessage(), e.getCause());
        } finally {
            try {
                statement.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = "DELETE FROM `bet` WHERE `bet_ID` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not delete bet with ID = " + id);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }
}
