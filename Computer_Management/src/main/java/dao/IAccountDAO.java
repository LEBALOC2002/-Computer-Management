package dao;

import model.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountDAO {
    public void insertAccount(Account account) throws SQLException;
    public Account selectAccount(int id);

    public List<Account> selectAllAccount();


    List<Account> selectAllAccountStatement();

    public boolean deleteAccount(int id) throws SQLException;

    public boolean updateAccount(Account account) throws SQLException;

    boolean updateUserWithSP(Account account) throws SQLException;

    boolean checkEmailExists(String email) throws SQLException;
}
