package Server;

import Server.DBClass.*;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }
    public void DeleteUser(User user){
        String delete = String.format("DELETE FROM %s WHERE %s = ?",
                Const.USER_TABLE, Const.USER_ID);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(delete);
            prsT.setString(1, user.getIdUser());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void EditUser(User user){
        String update = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                Const.USER_TABLE, Const.USER_LOGIN,
                Const.USER_PASSWORD, Const.USER_GENDER,
                Const.USER_ROLE, Const.USER_IDCOMPANY, Const.USER_ID);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, user.getLogin());
            prsT.setString(2, user.getPassword());
            prsT.setString(3, user.getGender());
            prsT.setString(4, user.getRole());
            prsT.setString(5, user.getIdCompany());
            prsT.setString(6, user.getIdUser());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void EditUserByLogin(User user){
        String update = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                Const.USER_TABLE, Const.USER_LOGIN,
                Const.USER_PASSWORD,
                Const.USER_LOGIN);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, user.getLogin());
            prsT.setString(2, user.getPassword());
            prsT.setString(3, user.getLogin());



            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddUser(User user){
        String insert = String.format("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES(?,?,?,?,?)" ,
                Const.USER_TABLE, Const.USER_LOGIN,
                Const.USER_PASSWORD, Const.USER_GENDER,
                Const.USER_ROLE, Const.USER_IDCOMPANY);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(insert);
            prsT.setString(1, user.getLogin());
            prsT.setString(2, user.getPassword());
            prsT.setString(3, user.getGender());
            prsT.setString(4, user.getRole());
            prsT.setString(5, user.getIdCompany());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public ResultSet getUserAll() {
        ResultSet result = null;

        String select = "SELECT * FROM " + Const.USER_TABLE;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getUserByLogAndPass(User user) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + " =? AND " + Const.USER_PASSWORD + " =?";

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, user.getLogin());
            prsT.setString(2, user.getPassword());
            result = prsT.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getUserByID(User user) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, user.getIdUser());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getUserByLogin(User userCheck) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, userCheck.getLogin());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public ResultSet getCompanyAll() {
        ResultSet result = null;

        String select = "SELECT * FROM " + Const.COMPANIES_TABLE;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;

    }
    public ResultSet getCompanyByID(Companies companies) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.COMPANIES_TABLE + " WHERE " + Const.COMPANIES_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, companies.getIdCompany());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getCompanyByString(String id) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.COMPANIES_TABLE + " WHERE " + Const.COMPANIES_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, id);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getCompanyByName(Companies companies) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.COMPANIES_TABLE + " WHERE " + Const.COMPANIES_NAME + " =?";

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, companies.getName());

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void AddCompany(Companies companies){
        String insert = String.format("INSERT INTO %s(%s) VALUES(?)" ,
                Const.COMPANIES_TABLE,
                Const.COMPANIES_NAME);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(insert);
            prsT.setString(1, companies.getName());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void EditCompany(Companies companies){
//        String update = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
//                Const.PRODUCT_TABLE, Const.PRODUCT_NAME,
//                Const.PRODUCT_PRICE, Const.PRODUCT_ID);

        String update = "UPDATE " + Const.COMPANIES_TABLE + " SET " +
                Const.COMPANIES_NAME + " =? " +
                "WHERE " + Const.COMPANIES_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, companies.getName());
            prsT.setString(2, companies.getIdCompany());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }    }
    public void deleteCompanyByID(Companies companies) {
        String delete = "DELETE FROM " + Const.COMPANIES_TABLE + " WHERE " + Const.COMPANIES_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(delete);
            prsT.setString(1, companies.getIdCompany());

            prsT.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getProductsAll() {
        ResultSet result = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;

    }
    public ResultSet getProductByID(Products product) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, product.getIdProduct());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getProductByTransactionID(Transactions transactions) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.TRANSACTIONS_ID_PRODUCT + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, transactions.getIdProduct());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void EditProduct(Products products){
//        String updatee = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
//                Const.PRODUCT_TABLE, Const.PRODUCT_NAME,
//                Const.PRODUCT_PRICE, Const.PRODUCT_ID);

        String update = "UPDATE " + Const.PRODUCT_TABLE + " SET " +
                Const.PRODUCT_NAME + " =?, " +
                Const.PRODUCT_PRICE + " =? " +
                "WHERE " + Const.PRODUCT_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, products.getName());
            prsT.setString(2, products.getPrice());
            prsT.setString(3, products.getIdProduct());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }    }
    public ResultSet getProductByName(Products product) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_NAME + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, product.getName());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void AddProduct(Products product){
        String insert = String.format("INSERT INTO %s(%s, %s) VALUES(?, ?)",
                Const.PRODUCT_TABLE,
                Const.PRODUCT_NAME, Const.PRODUCT_PRICE);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(insert);
            prsT.setString(1, product.getName());
            prsT.setString(2, product.getPrice());


            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteProductByID(Products products) {
        String delete = "DELETE FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(delete);
            prsT.setString(1, products.getIdProduct());

            prsT.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getTransactionAll(){
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.TRANSACTIONS_TABLE;
        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
    public ResultSet getTransactionsByID(Transactions transactions) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.TRANSACTIONS_TABLE + " WHERE " + Const.TRANSACTIONS_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, transactions.getIdTransactions());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet getTransactionsByIDCompany(String idComp) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.TRANSACTIONS_TABLE + " WHERE " + Const.TRANSACTIONS_ID_COMPANY + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, idComp);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void AddTransaction(Transactions transaction){
        String insert = String.format("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES(?,?,?,?,?)" ,
                Const.TRANSACTIONS_TABLE,
                Const.TRANSACTIONS_ID_PRODUCT, Const.TRANSACTIONS_ID_COMPANY,
                Const.TRANSACTIONS_AMOUNT, Const.TRANSACTIONS_DATE,
                Const.TRANSACTIONS_TYPE);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(insert);
            prsT.setString(1, transaction.getIdProduct());
            prsT.setString(2, transaction.getIdCompany());
            prsT.setString(3, transaction.getAmount());
            prsT.setString(4, transaction.getDate());
            prsT.setString(5, transaction.getType());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void EditTransactions(Transactions transactions){
        String update = "UPDATE " + Const.TRANSACTIONS_TABLE + " SET " +
                Const.TRANSACTIONS_ID_PRODUCT + " =?, " +
                Const.TRANSACTIONS_ID_COMPANY + " =?, " +
                Const.TRANSACTIONS_AMOUNT + " =?, " +
                Const.TRANSACTIONS_DATE + " =?, " +
                Const.TRANSACTIONS_TYPE + " =? " +
                "WHERE " + Const.TRANSACTIONS_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, transactions.getIdProduct());
            prsT.setString(2, transactions.getIdCompany());
            prsT.setString(3, transactions.getAmount());
            prsT.setString(4, transactions.getDate());
            prsT.setString(5, transactions.getType());
            prsT.setString(6, transactions.getIdTransactions());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }    }
    public void deleteTransactionByID(Transactions transactions) {
        String delete = "DELETE FROM " + Const.TRANSACTIONS_TABLE + " WHERE " + Const.TRANSACTIONS_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(delete);
            prsT.setString(1, transactions.getIdTransactions());

            prsT.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getDeliveryAll(){
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.DELIVERY_TABLE;
        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
    public void AddDelivery(Delivery delivery){
        String insert = String.format("INSERT INTO %s(%s, %s, %s) VALUES(?, ?, ?)",
                Const.DELIVERY_TABLE,
                Const.DELIVERY_ID_TRANSACTION, Const.DELIVERY_ADDRESS, Const.DELIVERY_STATUS);

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(insert);
            prsT.setString(1, delivery.getIdTransaction());
            prsT.setString(2, delivery.getDeliveryAdress());
            prsT.setString(3, delivery.getDeliveryStatus());

            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getDeliveryByID(Delivery delivery) {
        ResultSet result = null;
        String select = "SELECT * FROM " + Const.DELIVERY_TABLE + " WHERE " + Const.DELIVERY_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(select);
            prsT.setString(1, delivery.getIdDelivery());
            //prsT.setInt(5, idCompany);

            result = prsT.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void EditDelivery(Delivery delivery){
//        String updatee = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
//                Const.PRODUCT_TABLE, Const.PRODUCT_NAME,
//                Const.PRODUCT_PRICE, Const.PRODUCT_ID);

        String update = "UPDATE " + Const.DELIVERY_TABLE + " SET " +
                Const.DELIVERY_ID_TRANSACTION + " =?, " +
                Const.DELIVERY_ADDRESS + " =?, " +
                Const.DELIVERY_STATUS + " =? " +

                "WHERE " + Const.DELIVERY_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(update);
            prsT.setString(1, delivery.getIdTransaction());
            prsT.setString(2, delivery.getDeliveryAdress());
            prsT.setString(3, delivery.getDeliveryStatus());
            prsT.setString(4, delivery.getIdDelivery());


            prsT.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteDeliveryByID(Delivery delivery) {
        String delete = "DELETE FROM " + Const.DELIVERY_TABLE + " WHERE " + Const.DELIVERY_ID + " =?" ;

        try {
            PreparedStatement prsT = getDbConnection().prepareStatement(delete);
            prsT.setString(1, delivery.getIdDelivery());

            prsT.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}




