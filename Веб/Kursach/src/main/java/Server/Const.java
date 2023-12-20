package Server;

public class Const {
    public static final String USER_TABLE = "user";
    public static final String USER_ID = "idUser";
    public static final String USER_LOGIN = "Login";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_ROLE = "Role";
    public static final String USER_GENDER = "Gender";
    public static final String USER_IDCOMPANY = "idCompany";
    public static final String USER_ROLE_ADMIN = "Администратор";
    public static final String USER_ROLE_USER = "Пользователь";



    public static final String COMPANIES_TABLE = "companies";

    public static final String COMPANIES_ID = "idCompany";
    public static final String COMPANIES_NAME = "Name";

    public static final String PRODUCT_TABLE = "products";
    public static final String PRODUCT_ID = "idProduct";
    public static final String PRODUCT_NAME = "Name";
    public static final String PRODUCT_PRICE = "Price";

    public static final String TRANSACTIONS_TABLE = "transactions";

    public static final String TRANSACTIONS_ID = "idTransactions";
    public static final String TRANSACTIONS_ID_PRODUCT = "idProduct";
    public static final String TRANSACTIONS_ID_COMPANY = "idCompany";
    public static final String TRANSACTIONS_AMOUNT = "Amount";
    public static final String TRANSACTIONS_DATE = "Date";
    public static final String TRANSACTIONS_TYPE = "Type";

    public static final String DELIVERY_TABLE = "delivery";
    public static final String DELIVERY_ID = "idDelivery";
    public static final String DELIVERY_ID_TRANSACTION = "idTransaction";
    public static final String DELIVERY_ADDRESS = "DeliveryAddress";
    public static final String DELIVERY_STATUS = "DeliveryStatus";


}
