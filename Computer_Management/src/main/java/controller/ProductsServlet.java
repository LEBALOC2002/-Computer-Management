package controller;

import AppUtils.ValidateUtils;
import dao.*;
import model.Account;
import model.Category;
import model.Country;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

@WebServlet(name = "ProductsServlet", urlPatterns = "/product")
public class ProductsServlet extends HttpServlet {
    private IAccountDAO iAccountDAO;
    private ICountryDAO iCountryDAO;
    //    private ICategoryDAO categoryDAO;
    private IProductsDAO productsDAO;
    private String errors = null;

    @Override
    public void init() throws ServletException {
        iAccountDAO = new AccountDAO();
        iCountryDAO = new CountryDAO();
        productsDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> listCategory = categoryDAO.selectAllCategory();
        List<Country> listCountry = iCountryDAO.selectAllCountry();

        if (this.getServletContext().getAttribute("listCountry") == null) {
            this.getServletContext().setAttribute("listCountry", listCountry);
        }

        if (this.getServletContext().getAttribute("listCategory") == null) {
            this.getServletContext().setAttribute("listCategory", listCategory);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    showDeleteForm(request, response);
                    break;
                case "login":
                    showLogout(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (IOException ex) {
            throw new ServletException(ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/account/signin.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher requestDispatcher;
        Product product = productsDAO.selectProducts(id);
        request.setAttribute("product", product);
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/products/edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productsDAO.deleteProducts(id);
//        List<Product> listProduct = productsDAO.selectAllProducts();
//        request.setAttribute("listProduct", listProduct);
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/products/index.jsp");
//        requestDispatcher.forward(request, response);

        response.sendRedirect("/product");
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/products/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    editProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Product newProduct = new Product();
        RequestDispatcher requestDispatcher;
        List<String> errors = new ArrayList<>();
        try {
            String title = request.getParameter("title");
            if (title.trim().equals("")) errors.add("Tên không được để trống");
            newProduct.setTitle(title);
            String images = request.getParameter("image");
            if (!ValidateUtils.isImageValid(images))
                errors.add("Đường dẫn ảnh không đúng (Đường dẫn ảnh phải có đuôi là jpg/png/jpeg)");
            newProduct.setImages(images);

            int price = Integer.parseInt(request.getParameter("price"));
            if (price < 10000 || price > 100000000) errors.add("Giá trên 10000 dưới 100000000");

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity <= 0 || quantity > 10000) errors.add("Số lượng phải lớn hơn 0 hoặc bé hơn 10000");

            String description = request.getParameter("description");
            if (description.trim().equals("")) errors.add("Không được để trống phần mô tả");

            int idCategory = Integer.parseInt(request.getParameter("category"));
            if (errors.isEmpty()) {
                Product products = new Product(title, images, price, quantity, description, idCategory);
                products.setTitle(title);
                products.setImages(images);
                products.setPrice(price);
                products.setQuantity(quantity);
                products.setDescription(description);
                products.setIdcategory(idCategory);
                request.setAttribute("message", "Thêm mới sản phẩm" + " ' " + title + " ' " + images + " ' " + price + " ' " + quantity + " ' " + description + " ' " + idCategory + "thành công");
                request.setAttribute("product", newProduct);
                productsDAO.insertProducts(products);
            }
        }
        catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng của giá hoặc số lượng không hợp lệ");
        } finally {
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/WEB-INF/products/create.jsp");
            request.setAttribute("errors", errors);
            requestDispatcher1.forward(request, response);
        }
    }



    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String title, description, image;
        int price;
        int quantity;
        int idCategory;
        List<String> errors = new ArrayList<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Product oldProduct = productsDAO.selectProducts(id);
        try {
            title = request.getParameter("title");
            if (title.trim().equals("")) errors.add("Tên sản phẩm không được để trống");
            image = request.getParameter("image");
            if (!ValidateUtils.isImageValid(image))
                errors.add("Đường dẫn ảnh không đúng (Đường dẫn ảnh phải có đuôi là jpg/png/jpeg)");
            price = Integer.parseInt(request.getParameter("price"));
            if (price < 10000 || price > 100000000) errors.add("Giá trên 1000 dưới 100000000");
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity <= 0 || quantity > 10000) errors.add("Số lượng phải lớn hơn 0 hoặc bé hơn 10000");
            description = request.getParameter("description");
            if (description.trim().equals("")) errors.add("Không được để trống phần mô tả");
            idCategory = Integer.parseInt(request.getParameter("category"));
            if (errors.isEmpty()) {
                Product products = new Product(id, title, image, price, quantity, description, idCategory);
                products.setTitle(title);
                products.setImages(image);
                products.setPrice(price);
                products.setQuantity(quantity);
                products.setDescription(description);
                products.setIdcategory(idCategory);
                request.setAttribute("message", "Sửa mới sản phẩm" + " ' " + title + " ' " + image + " ' " + price + " ' " + quantity + " ' " + description + " ' " + "thành công");
                productsDAO.updateProducts(products);
            }
        }
        catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng của giá hoặc số lượng không hợp lệ");
        } finally {
            if (request.getAttribute("product") == null)
                request.setAttribute("product", oldProduct);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/products/edit.jsp");
            request.setAttribute("errors", errors);
            requestDispatcher.forward(request, response);
        }
    }


    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        if (req.getParameter("message") != null) {
            message = req.getParameter("message");
        }
        int page = 1;
        int recordsPerPage = 3;
        String q = "";
        int category_id = -1;
        if (req.getParameter("q") != null) {
            q = req.getParameter("q");
        }
        if (req.getParameter("category_id") != null) {
            category_id = Integer.parseInt(req.getParameter("category_id"));
        }
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Product> listProduct = productsDAO.selectAllProductsPaggingFilter((page - 1) * recordsPerPage, recordsPerPage, q, category_id);
        int noOfRecords = productsDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        System.out.println(getClass() + " listProductPage " + listProduct);
        req.setAttribute("message", message);
        req.setAttribute("listProduct", listProduct);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("q", q);
        req.setAttribute("category_id", category_id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/index.jsp");
        dispatcher.forward(req, resp);
    }

}

