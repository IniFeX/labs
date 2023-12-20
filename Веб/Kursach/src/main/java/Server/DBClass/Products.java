package Server.DBClass;

public class Products {
    private String idProduct;
    private String Name;
    private String Price;

    public Products(String idProduct, String name, String price) {
        this.idProduct = idProduct;
        Name = name;
        Price = price;
    }
    public Products() {}
    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
