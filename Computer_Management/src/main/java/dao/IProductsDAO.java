package dao;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductsDAO {

    //    public int getNoOfRecords();
//    public List<Product> selectAllProductsPaggingFilter(int offset, int noOfRecords, String q, int idProduct);
    public List<Product> selectAllProductsPaggingFilter(int offset, int noOfRecords, String q, int idcategory);
    public int getNoOfRecords();
    public void insertProducts(Product products) throws SQLException;

    public Product selectProducts(int id);

    public List<Product> selectAllProducts();

    List<Product> selectAllProductsStatement();

    public boolean deleteProducts(int id) throws SQLException;

    boolean updateProducts(Product products) throws SQLException;
}
