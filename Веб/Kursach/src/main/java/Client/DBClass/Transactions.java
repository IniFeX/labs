package Client.DBClass;

public class Transactions {
    private String idTransactions;
    private String idProduct;
    private String idCompany;
    private String Amount;
    private String Date;
    private String Type;

    public Transactions(String idTransactions, String idProduct, String idCompany, String amount, String date, String type) {
        this.idTransactions = idTransactions;
        this.idProduct = idProduct;
        this.idCompany = idCompany;
        Amount = amount;
        Date = date;
        Type = type;
    }
    public Transactions(){}
    public String getIdTransactions() {
        return idTransactions;
    }

    public void setIdTransactions(String idTransactions) {
        this.idTransactions = idTransactions;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
