package model;

public class Product {
    protected int id ;
    protected String title;
    protected String images;
    protected int price ;
    protected int quantity;
    private String description ;
    private int idcategory;

    public Product() {
    }

    public Product( String title, String images, int price, int quantity, String description, int idcategory) {
        this.title = title;
        this.images = images;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.idcategory = idcategory;
    }

    public Product(int id, String title, String images, int price, int quantity, String description, int idcategory) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.idcategory = idcategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }
}
