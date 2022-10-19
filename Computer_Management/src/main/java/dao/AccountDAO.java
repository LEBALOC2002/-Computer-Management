package dao;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {

    private static final String INSERT_USER = "INSERT INTO `case_m3`.`account` (`gmail`, `password`, `full_name`,`country`) VALUES (?,?,?);";
    private static final String SELECT_ACCOUNT_BYID = "SELECT id , gmail, password, full_name , country FROM account  where id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM  account";

    private static final String DELETE_USERS_SQL = "delete from account  where id = ?;";
    private static final String UPDATE_USERS_SQL = "update account set gmail= ?, password =? , full_name =? , country = ? where id = ?;\"";
    private static final String SP_EDIT_ACCOUNT = "call case_m3.acount(?, ?, ?, ?, ?)";
    private static final String CHECK_EMAIL_EXISTS = "SELECT * FROM account where email = ?";
    private String jdbcURL = "jdbc:mysql://localhost:3306/case_m3?account=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }




    @Override
    public void insertAccount(Account account) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
        preparedStatement.setString(1, account.getGmail());
        preparedStatement.setString(2,account.getPassword());
        preparedStatement.setString(3,account.getFullName());
        preparedStatement.setInt(4,account.getIdCountry());
        preparedStatement.executeUpdate();

    }


    @Override
    public Account selectAccount(int id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparableStatement = connection.prepareStatement(SELECT_ACCOUNT_BYID);
            preparableStatement.setInt(1 , id);
            ResultSet rs = preparableStatement.executeQuery();

            System.out.println(this.getClass() + " selectAccount: " + preparableStatement);
            while (rs.next()){
                int idAccount = rs.getInt("id");
                String gmail = rs.getString("gmail");
                String password = rs.getString("password");
                String full_name = rs.getString("full_name");
                int idCountry = rs.getInt("idCountry");
                Account account = new Account(idAccount, gmail, password,full_name ,idCountry);
                return account;
            }
            //preparableStatement.setString(2, name);
        }catch (SQLException ex){
            printSQLException(ex);
        }
        return null;
    }

    @Override
    public List<Account> selectAllAccount() {

        List<Account> listAccount = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparableStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = preparableStatement.executeQuery();

            while (rs.next()){
                int idAccount = rs.getInt("id");
                String gmail = rs.getString("gmail");
                String password = rs.getString("password");
                String full_name = rs.getString("full_name");
                int idCountry = rs.getInt("idcountry");
                Account account = new Account(idAccount,gmail,password,full_name,idCountry);
                listAccount.add(account);
            }
        }catch (SQLException ex){
            printSQLException(ex);
        }
        return listAccount;
    }

    public  List<Account> selectAllAccountStatement(){
        List<Account> listAccount = new ArrayList<>();
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(SELECT_ALL_USERS);
            while (rs.next()){
                int idAccount = rs.getInt("id");
                String gmail = rs.getString("gmail");
                String password = rs.getString("password");
                String full_name = rs.getString("full_name");
                int idCountry = rs.getInt("idCountry");
                Account account = new Account(idAccount,gmail,password,full_name,idCountry);
                listAccount.add(account);
            }
        }catch (SQLException ex){
            printSQLException(ex);
        }
        return listAccount;
    }

    @Override
    public boolean deleteAccount(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateAccount(Account account) throws SQLException {
        return false;
    }



    @Override
    public boolean updateUserWithSP(Account account) throws SQLException {
        Connection connection = getConnection();
        //call c6_customermanager.sp_editUser(?, ?, ?, ?, ?)
        CallableStatement callableStatement = connection.prepareCall(SP_EDIT_ACCOUNT);
        callableStatement.setInt(1, account.getId());
        callableStatement.setString(2, account.getGmail());
        callableStatement.setString(3, account.getPassword());
        callableStatement.setString(4, account.getFullName());
        callableStatement.setInt(5,account.getIdCountry());
        callableStatement.registerOutParameter(5, Types.VARCHAR);

        System.out.println(this.getClass() + " updateUserWithSP " + callableStatement);
        callableStatement.executeUpdate();
        String message = callableStatement.getString(5);
        System.out.println("Message: " + message);

        return true;
    }

    @Override
    public boolean checkEmailExists(String email) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EMAIL_EXISTS);
        preparedStatement.setString(1, email);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}