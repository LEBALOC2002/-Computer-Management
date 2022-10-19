package dao;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductsDAO {

    private static final String INSERT_PRODUCTS_SQL = "INSERT INTO products (images, title, price, quantity, description , idcategory) VALUES " + " (?, ?, ?, ?,?,?);";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String SELECT_PRODUCT_BY_ID = "select * from products where id =?;";
    private static final String UPDATE_PRODUCT = "update products set images=?, title =?, price=?, quantity =?, description =?, idcategory=? where id = ?;";
    private static final String DELETE_PRODUCT = "delete from products where id = ?;";
    private static final String SELECT_ALL_USER_PAGGING_FILLTER = "select SQL_CALC_FOUND_ROWS * from products where name like ? and id = ? limit ?, ?;";
    private static final String SELECT_ALL_USER_PAGGING_FILLTER_ALL =  "select SQL_CALC_FOUND_ROWS * from products where title like ? limit ?, ?;";
    private static final String SELECT_ALL_PRODUCT_SEARCH_FILTER_ALLCATEGORY =  "select SQL_CALC_FOUND_ROWS * from product where name like ? and idcategory = ? limit ?,?;";
    private static final String SELECT_ALL_PRODUCT_SEARCH_FILTER = "select SQL_CALC_FOUND_ROWS * from product where name like ? limit ?,?;";
//    private int noOfRecords;

    private String jdbcURL = "jdbc:mysql://localhost:3306/case_m3?products=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private int noOfRecords;


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    @Override
    public List<Product> selectAllProductsPaggingFilter(int offset, int noOfRecords, String q, int idcategory) {
        List<Product> listProduct = new ArrayList<>();
        Product product = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (idcategory != -1) {
                String query = "select SQL_CALC_FOUND_ROWS * from products where title like ? and idcategory = ? limit ?, ?";
                stmt = connection.prepareStatement(query);
                stmt.setString(1, '%' + q + '%');
                stmt.setInt(2, idcategory);
                stmt.setInt(3, offset);
                stmt.setInt(4,noOfRecords);
            } else {
                if (idcategory == -1) {
                    String query = "select SQL_CALC_FOUND_ROWS * from products where title like ? limit ?, ?";
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, '%' + q + '%');
                    stmt.setInt(2, offset);
                    stmt.setInt(3, noOfRecords);
                }
            }
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setImages(rs.getString("images"));
                product.setQuantity(rs.getInt("quantity"));
                product.setPrice(rs.getInt("price"));
                product.setDescription(rs.getString("description"));
                product.setIdcategory(rs.getInt("idcategory"));
                listProduct.add(product);
            }
            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listProduct;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public void insertProducts(Product products) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {
            preparedStatement.setString(1, products.getImages());
            preparedStatement.setString(2, products.getTitle());
            preparedStatement.setInt(3, products.getPrice());
            preparedStatement.setInt(4, products.getQuantity());
            preparedStatement.setString(5, products.getDescription());
            preparedStatement.setInt(6,products.getIdcategory());

            System.out.println(this.getClass() + " insertProduct: " + preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Product selectProducts(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(this.getClass() +  " selectProduct: " + preparedStatement);
            while (rs.next()){
                int idProduct = rs.getInt("id");
                String title = rs.getString("title");
                String images = rs.getString("images");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                int category = rs.getInt("idcategory");
                Product products = new Product(idProduct ,title,images,price,quantity,description,category);
                return products ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> productsList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparableStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            System.out.println("preparableStatement: " + preparableStatement);
            ResultSet rs = preparableStatement.executeQuery();

            while (rs.next()){
                int idProduct = rs.getInt("id");
                String title = rs.getString("title");
                String image = rs.getString("images");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                int idCategory = rs.getInt("idcategory");
                Product products = new Product(idProduct,title,image,price,quantity,description,idCategory);
                productsList.add(products);
            }
        }catch (SQLException ex){
            printSQLException(ex);
        }
        return productsList;
    }

    @Override
    public List<Product> selectAllProductsStatement() {
        return null;
    }

    @Override
    public boolean deleteProducts(int id) throws SQLException {
        boolean rowDeleted = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            System.out.println(this.getClass() + " deleteProduct " + preparedStatement);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            printSQLException(ex);
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProducts(Product products) throws SQLException {
        boolean rowUpdated;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
            preparedStatement.setString(1, products.getImages());
            preparedStatement.setString(2, products.getTitle());
            preparedStatement.setInt(3, products.getPrice());
            preparedStatement.setInt(4, products.getQuantity());
            preparedStatement.setString(5, products.getDescription());
            preparedStatement.setInt(6,products.getIdcategory());
            preparedStatement.setInt(7, products.getId());
            System.out.println(this.getClass() + " updateProduct " + preparedStatement);
            rowUpdated = preparedStatement.executeUpdate() > 0;
            return rowUpdated;
        } catch (SQLException ex) {
            printSQLException(ex);
        }
        return false;
    }
    public void printSQLException(SQLException ex) {
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

