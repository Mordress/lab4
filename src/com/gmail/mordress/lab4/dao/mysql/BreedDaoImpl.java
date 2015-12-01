package com.gmail.mordress.lab4.dao.mysql;

import com.gmail.mordress.lab4.dao.BreedDao;
import com.gmail.mordress.lab4.dao.DaoException;
import com.gmail.mordress.lab4.domain.Breed;
import com.gmail.mordress.lab4.exceptions.PersistentException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BreedDaoImpl extends BaseDaoImpl implements BreedDao{

    private static Logger logger = Logger.getLogger(BreedDaoImpl.class);

    @Override
    public Breed findByName(String name) throws DaoException {
        return null;
    }

    @Override
    public List<Breed> getAllBreeds() throws DaoException {
        return null;
    }

    @Override
    public Integer create(Breed instance) throws DaoException {
        String sql = "INSERT INTO `breed` (`name`) VALUES (?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        return null;
    }

    @Override
    public Breed read(Integer id) throws DaoException {
        String sql = "SELECT `name` FROM `breed` WHERE `breed_ID` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Breed breed = null;
            if (resultSet.next()) {
                breed = new Breed();
                breed.setId(id);
                breed.setName(resultSet.getString("name"));
            }
            return breed;
        } catch (SQLException e) {
            logger.debug("Can not read breed with ID = " + id);
            throw new DaoException(e.getMessage(), e.getCause());
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
    public void update(Breed instance) throws DaoException {
        //TODO LOGIC FOR CHECKING UNIQUE BREEDS
        String sql = "UPDATE `breed` SET `name` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, instance.getName());

        } catch (SQLException e) {
            logger.debug("Can not update breed with ID = "  + instance.getId());
            throw new DaoException(e.getMessage(), e.getCause());
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }

    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "DELETE FROM `breed` WHERE `breed_ID` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            logger.debug("Can not delete breed with ID = " + id);
            throw new DaoException(e.getMessage(), e.getCause());
        }finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {}
        }
    }
}
