package Client;

public class IEClass {
    private String idTransactions;
    private String ProductName;
    private String CompanyName;
    private String Amount;
    private String Date;
    private String Type;

    public IEClass(String idTransactions, String ProductName, String CompanyName, String amount, String date, String type) {
        this.idTransactions = idTransactions;
        this.ProductName = ProductName;
        this.CompanyName = CompanyName;
        Amount = amount;
        Date = date;
        Type = type;
    }

    public String getIdTransactions() {
        return idTransactions;
    }

    public void setIdTransactions(String idTransactions) {
        this.idTransactions = idTransactions;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        this.CompanyName = companyName;
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
