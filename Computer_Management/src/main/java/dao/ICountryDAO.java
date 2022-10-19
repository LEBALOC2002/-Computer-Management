package dao;

import model.Country;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface ICountryDAO {
    void insertCountry(Country country) throws SQLException;

    Country selectCountry(int id);

    List<Country> selectAllCountry();

    boolean deleteCountry(int id) throws SQLException;

    boolean updateCountry(Country country) throws SQLException;
}