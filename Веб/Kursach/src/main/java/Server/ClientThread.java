package Server;

import Server.DBClass.*;
import Server.TCP.Request;
import Server.TCP.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientThread implements Runnable {
    private final Socket clientSocket;
    private Request request;
    private Response response;
    private final Gson gson;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientThread(Socket clientSocket) throws IOException{
        //response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            while (clientSocket.isConnected()){
                System.out.println("Reading from input stream...");
                String message = "";
                try {
                    System.out.println("Reading from input stream...");
                    message = in.readLine();
                    if (message == null) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                    // Обработка полученного сообщения от клиента
                    System.out.println("Received message: " + message);
                } catch (SocketException e) {
                    System.out.println("Client connection reset.");
                    break;
                }
                request = gson.fromJson(message, Request.class);
                switch (request.getRequestType()) {
                    case EDIT_USER_BY_LOGIN:{
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        ResultSet userById = dbHandler.getUserByLogin(user);
                        User us = null;
                        while (true){
                            if(!userById.next()) break;
                            String idUser = userById.getString(Const.USER_ID);
                            String login = userById.getString(Const.USER_LOGIN);
                            String password = userById.getString(Const.USER_PASSWORD);
                            String role = userById.getString(Const.USER_ROLE);
                            String idCompany = userById.getString(Const.USER_IDCOMPANY);
                            String gender = userById.getString(Const.USER_GENDER);

                            us = new User(idUser, login, password, gender, role, idCompany);
                        }

                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if (mese.equals("true")){
                            String jsObj = in.readLine();
                            User usera = gson.fromJson(jsObj, User.class);
                            us.setLogin(usera.getLogin());
                            us.setPassword(usera.getPassword());
                            dbHandler.EditUser(us);
                        }
                        break;

                    }

                    case DIAGRAM:{
                        String company = gson.fromJson(request.getRequestMessage(), String.class);
                        ArrayList<Transactions> transList = new ArrayList<>();
                        ArrayList<Products> prodList = new ArrayList<>();
                        ArrayList<Companies> compList = new ArrayList<>();
                        ResultSet transAll = dbHandler.getTransactionAll();
                        ResultSet prodAll = dbHandler.getProductsAll();
                        ResultSet compAll = dbHandler.getCompanyAll();
                        while (true){
                            if(!transAll.next()) break;
                            String idTransactions = transAll.getString("idTransactions");
                            String idProduct = transAll.getString("idProduct");
                            String idCompany = transAll.getString("idCompany");
                            String amount = transAll.getString("Amount");
                            String date = transAll.getString("Date");
                            String typeTr = transAll.getString("Type");
                            Transactions transactions = new Transactions(idTransactions, idProduct, idCompany, amount, date, typeTr);
                            transList.add(transactions);
                        }
                        while (true){
                            if(!prodAll.next()) break;
                            String idProduct = prodAll.getString(Const.PRODUCT_ID);
                            String name = prodAll.getString(Const.PRODUCT_NAME);
                            String price = prodAll.getString(Const.PRODUCT_PRICE);
                            Products products = new Products(idProduct, name, price);
                            prodList.add(products);
                        }
                        while (true){
                            if(!compAll.next()) break;
                            String idCompany = compAll.getString(Const.COMPANIES_ID);
                            String name = compAll.getString(Const.COMPANIES_NAME);
                            Companies companies = new Companies(idCompany, name);
                            compList.add(companies);
                        }
                        out.println(gson.toJson(transList));
                        out.flush();
                        out.println(gson.toJson(prodList));
                        out.flush();
                        out.println(gson.toJson(compList));
                        out.flush();
                        break;
                    }
                    case COMPANY_BY_ID:{
                        String company = gson.fromJson(request.getRequestMessage(), String.class);
                        String nameCompany = " ";
                        ResultSet comp = dbHandler.getCompanyByString(company);
                        while (true){
                            if(!comp.next()) break;
                            nameCompany = comp.getString(Const.COMPANIES_NAME);
                        }
                        System.out.println(nameCompany);
                        out.println(gson.toJson(nameCompany));
                        out.flush();
                        break;
                    }
                    case PRODUCT_TRANSACTION:{
                        ArrayList<Transactions> transArray = gson.fromJson(request.getRequestMessage(),
                                new TypeToken<ArrayList<Transactions>>() {}.getType());
                        ArrayList<Products> productsArrayList = new ArrayList<>();
                        Products prod = null;
                        for(Transactions tr : transArray){
                            ResultSet pr = dbHandler.getProductByTransactionID(tr);
                            while (true){
                                if(!pr.next()) break;
                                String idProd = pr.getString(Const.PRODUCT_ID);
                                String name = pr.getString(Const.PRODUCT_NAME);
                                String price = pr.getString(Const.PRODUCT_PRICE);
                                prod = new Products(idProd, name, price);
                            }
                            productsArrayList.add(prod);
                        }
                        out.println(gson.toJson(productsArrayList));
                        out.flush();
                        break;
                    }
                    case TRANSACTION_BY_IDCOMPANY:{
                        String company = gson.fromJson(request.getRequestMessage(), String.class);
                        ArrayList<Transactions> transArray = new ArrayList<>();
                        Transactions trans = new Transactions();
                        ResultSet tr = dbHandler.getTransactionsByIDCompany(company);
                        while (true){
                            if(!tr.next()) break;
                            String idTransactions = tr.getString("idTransactions");
                            String idProduct = tr.getString("idProduct");
                            String idCompany = tr.getString("idCompany");
                            String amount = tr.getString("Amount");
                            String date = tr.getString("Date");
                            String typeTr = tr.getString("Type");
                            trans = new Transactions(idTransactions, idProduct, idCompany, amount, date, typeTr);
                            transArray.add(trans);
                        }
                        out.println(gson.toJson(transArray));
                        out.flush();
                        break;
                    }
//                    case ADD_USER_TRANSACTION:{
//                        break;
//                    }
//                    case DELETE_USER_TRANSACTIONS:{
//                        break;
//                    }
//                    case UPDATE_USER_TRANSACTIONS:{
//                        break;
//                    }
                    case GET_USER_INFO:{
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        ResultSet getUser = dbHandler.getUserByLogin(user);
                        User us = null;
                        while(true){
                            if(!getUser.next()) break;
                            String idUser = getUser.getString(Const.USER_ID);
                            String login = getUser.getString(Const.USER_LOGIN);
                            String password = getUser.getString(Const.USER_PASSWORD);
                            String role = getUser.getString(Const.USER_ROLE);
                            String idCompany = getUser.getString(Const.USER_IDCOMPANY);
                            String gender = getUser.getString(Const.USER_GENDER);
                            us = new User(idUser, login, password, gender, role, idCompany);
                        }
                        System.out.println(us.getIdUser());
                        System.out.println(us.getLogin());
                        System.out.println(us.getGender());
                        System.out.println(us.getRole());
                        System.out.println(us.getIdCompany());
                        System.out.println(us.getPassword());
                        out.println(gson.toJson(us));
                        out.flush();
                        break;
                    }
                    case DELETE_COMPANY: {
                        Companies companies = gson.fromJson(request.getRequestMessage(), Companies.class);
                        dbHandler.deleteCompanyByID(companies);
                        break;
                    }
                    case DELETE_DELIVERY: {
                        Delivery delivery = gson.fromJson(request.getRequestMessage(), Delivery.class);
                        dbHandler.deleteDeliveryByID(delivery);
                        break;
                    }
                    case DELETE_USER: {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        dbHandler.DeleteUser(user);
                        break;
                    }
                    case DELETE_PRODUCT: {
                        Products products = gson.fromJson(request.getRequestMessage(), Products.class);
                        dbHandler.deleteProductByID(products);
                        break;
                    }
                    case UPDATE_USER:{
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        ResultSet userById = dbHandler.getUserByID(user);
                        User us = null;
                        while (true){
                            if(!userById.next()) break;
                            String idUser = userById.getString(Const.USER_ID);
                            String login = userById.getString(Const.USER_LOGIN);
                            String password = userById.getString(Const.USER_PASSWORD);
                            String role = userById.getString(Const.USER_ROLE);
                            String idCompany = userById.getString(Const.USER_IDCOMPANY);
                            String gender = userById.getString(Const.USER_GENDER);

                            us = new User(idUser, login, password, gender, role, idCompany);
                        }

                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if (mese.equals("true")){
                            String jsObj = in.readLine();
                            User usera = gson.fromJson(jsObj, User.class);
                            us.setLogin(usera.getLogin());
                            us.setPassword(usera.getPassword());
                            us.setRole(usera.getRole());
                            us.setIdCompany(usera.getIdCompany());
                            us.setGender(usera.getGender());
                            dbHandler.EditUser(us);
                        }
                        break;

                    }
                    case UPDATE_DELIVERY:{
                        Delivery delivery = gson.fromJson(request.getRequestMessage(), Delivery.class);
                        ResultSet deliveryById = dbHandler.getDeliveryByID(delivery);
                        Delivery deli = null;
                        while (true){
                            if(!deliveryById.next()) break;
                            String idDelivery = deliveryById.getString(Const.DELIVERY_ID);
                            String itTrans = deliveryById.getString(Const.DELIVERY_ID_TRANSACTION);
                            String address = deliveryById.getString(Const.DELIVERY_ADDRESS);
                            String status = deliveryById.getString(Const.DELIVERY_STATUS);

                            deli = new Delivery(idDelivery, itTrans, address, status);
                        }

                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if (mese.equals("true")){
                            String jsObj = in.readLine();
                            Delivery deliva = gson.fromJson(jsObj, Delivery.class);
                            deli.setIdTransaction(deliva.getIdTransaction());
                            deli.setDeliveryAdress(deliva.getDeliveryAdress());
                            deli.setDeliveryStatus(deliva.getDeliveryStatus());
                            dbHandler.EditDelivery(deli);
                        }
                        break;
                    }
                    case ADD_USER:{
                        String js = in.readLine();
                        String msg = gson.fromJson(js, String.class);
                        if(msg.equals("true")){
                            String jsUser = in.readLine();
                            User user = gson.fromJson(jsUser, User.class);
                            dbHandler.AddUser(user);
                        }
                        break;
                    }
                    case ADD_DELIVERY:{
                        String js = in.readLine();
                        String msg = gson.fromJson(js, String.class);
                        if(msg.equals("true")){
                            String jsDeli = in.readLine();
                            Delivery delivery = gson.fromJson(jsDeli, Delivery.class);
                            dbHandler.AddDelivery(delivery);
                        }
                        break;
                    }
                    case SHOW_USER:{
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ArrayList<User> userArrayList = new ArrayList<>();
                        ResultSet allUser = dbHandler.getUserAll();
                        while (true){
                            if (!allUser.next()) break;
                            String idUser = allUser.getString(Const.USER_ID);
                            String login = allUser.getString(Const.USER_LOGIN);
                            String password = allUser.getString(Const.USER_PASSWORD);
                            String role = allUser.getString(Const.USER_ROLE);
                            String idCompany = allUser.getString(Const.USER_IDCOMPANY);
                            String gender = allUser.getString(Const.USER_GENDER);


                            User user = new User(idUser, login, password, gender, role, idCompany);
                            userArrayList.add(user);
                        }
                        out.println(gson.toJson(userArrayList));
                        out.flush();
                        break;
                    }
                    case SHOW_DELIVERY:{
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ArrayList<Delivery> deliveryArrayList = new ArrayList<>();
                        ResultSet allDeli = dbHandler.getDeliveryAll();
                        while (true){
                            if (!allDeli.next()) break;
                            String idDelivery = allDeli.getString(Const.DELIVERY_ID);
                            String idTransaction = allDeli.getString(Const.DELIVERY_ID_TRANSACTION);
                            String deliveryAddress = allDeli.getString(Const.DELIVERY_ADDRESS);
                            String deliveryStatus = allDeli.getString(Const.DELIVERY_STATUS);

                            Delivery delivery = new Delivery(idDelivery, idTransaction, deliveryAddress, deliveryStatus);
                            deliveryArrayList.add(delivery);
                        }
                        out.println(gson.toJson(deliveryArrayList));
                        out.flush();

                        break;
                    }
                    case UPDATE_PRODUCT:{
                        Products products = gson.fromJson(request.getRequestMessage(), Products.class);
                        ResultSet productById = dbHandler.getProductByID(products);
                        Products prod = null;
                        while (true){
                            if(!productById.next()) break;
                            String idProduct = productById.getString(Const.PRODUCT_ID);
                            String name = productById.getString(Const.PRODUCT_NAME);
                            String price = productById.getString(Const.PRODUCT_PRICE);
                            prod = new Products(idProduct, name, price);
                        }

                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if (mese.equals("true")){
                            String jsObj = in.readLine();
                            Products proda = gson.fromJson(jsObj, Products.class);
                            prod.setName(proda.getName());
                            prod.setPrice(proda.getPrice());
                            dbHandler.EditProduct(prod);
                        }

                        break;
                    }
                    case UPDATE_COMPANY:{
                        Companies companies = gson.fromJson(request.getRequestMessage(), Companies.class);
                        ResultSet companyById = dbHandler.getCompanyByID(companies);
                        Companies comp = null;
                        while (true){
                            if(!companyById.next()) break;
                            String idCompany = companyById.getString(Const.COMPANIES_ID);
                            String name = companyById.getString(Const.COMPANIES_NAME);
                            comp = new Companies(idCompany, name);
                        }

                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if (mese.equals("true")){
                            String jsObj = in.readLine();
                            Companies compa = gson.fromJson(jsObj, Companies.class);
                            comp.setName(compa.getName());
                            dbHandler.EditCompany(comp);
                        }
                        break;
                    }
                    case ADD_COMPANY:{
                        String js = in.readLine();
                        String msg = gson.fromJson(js, String.class);
                        if(msg.equals("true")){
                            String jsComp = in.readLine();
                            Companies companies = gson.fromJson(jsComp, Companies.class);
                            dbHandler.AddCompany(companies);
                        }
                        break;
                    }
                    case ADD_PRODUCT: {
                        String js = in.readLine();
                        String msg = gson.fromJson(js, String.class);
                        if(msg.equals("true")){
                            String jsProd = in.readLine();
                            Products products = gson.fromJson(jsProd, Products.class);
                            dbHandler.AddProduct(products);
                        }
                        break;
                    }
                    case SHOW_PRODUCT:{
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ArrayList<Products> productsArrayList = new ArrayList<>();
                        ResultSet allProd = dbHandler.getProductsAll();
                        while (true){
                            if (!allProd.next()) break;
                            String idProduct = allProd.getString(Const.PRODUCT_ID);
                            String name = allProd.getString(Const.PRODUCT_NAME);
                            String price = allProd.getString("Price");
                            Products products = new Products(idProduct, name, price);
                            productsArrayList.add(products);
                        }
                        out.println(gson.toJson(productsArrayList));
                        out.flush();
                        break;
                    }
                    case SHOW_COMPANY:{
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ArrayList<Companies> companiesList = new ArrayList<>();
                        ResultSet allComp = dbHandler.getCompanyAll();
                        while (true){
                            if (!allComp.next()) break;
                            String idCompany = allComp.getString(Const.COMPANIES_ID);
                            String name = allComp.getString(Const.COMPANIES_NAME);
                            Companies companies = new Companies(idCompany, name);
                            companiesList.add(companies);
                        }
                        out.println(gson.toJson(companiesList));
                        out.flush();
                        break;
                    }
                    case ADD_TRANSACTIONS:{
                        //String aaa = in.readLine();
                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if(mese.equals("true")){
                            String ie = in.readLine();
                            IEClass ieClass = gson.fromJson(ie, IEClass.class);
                            String nameProd = ieClass.getProductName();
                            System.out.println(nameProd);
                            String nameComp = ieClass.getCompanyName();
                            System.out.println(nameComp);
                            Products product = new Products();
                            Companies company = new Companies();
                            product.setName(nameProd);
                            company.setName(nameComp);
                            System.out.println(nameComp + "/" + nameProd);
                            ResultSet resProd = dbHandler.getProductByName(product);
                            ResultSet resComp = dbHandler.getCompanyByName(company);
                            String prodId = null;
                            String compId = null;
                            while (true){
                                if(!resProd.next()) break;
                                prodId = resProd.getString("idProduct");
                            }
                            while (true){
                                if(!resComp.next()) break;
                                compId = resComp.getString("idCompany");
                            }
//
                            Transactions transactions = new Transactions(null, prodId, compId, ieClass.getAmount(),
                                    ieClass.getDate(), ieClass.getType());
                            dbHandler.AddTransaction(transactions);
//                            trans.setDate(ieClass.getDate());
//                            trans.setAmount(ieClass.getAmount());
//                            trans.setType(ieClass.getType());
//                            trans.setIdProduct(prodId);
//                            System.out.println(prodId);
//
//                            trans.setIdCompany(compId);
//                            System.out.println(compId);
//                            dbHandler.EditTransactions(trans);

                        }
                        break;

                    }
                    case DELETE_TRANSACTIONS: {
                        Transactions transactions = gson.fromJson(request.getRequestMessage(), Transactions.class);
                        dbHandler.deleteTransactionByID(transactions);
                        break;
                    }
                    case UPDATE_TRANSACTIONS:{
                        System.out.println("UPDATE_TRANSACTIONS");
                        Transactions transactions = gson.fromJson(request.getRequestMessage(), Transactions.class);
                        ResultSet updateTrans = dbHandler.getTransactionsByID(transactions);
                        Transactions trans = null;
                        while (true){
                            if (!updateTrans.next()) break;
                            String idTransactions = updateTrans.getString("idTransactions");
                            String idProduct = updateTrans.getString("idProduct");
                            String idCompany = updateTrans.getString("idCompany");
                            String amount = updateTrans.getString("Amount");
                            String date = updateTrans.getString("Date");
                            String typeTr = updateTrans.getString("Type");
                            trans = new Transactions(idTransactions, idProduct, idCompany, amount, date, typeTr);
                        }
                        //String jsComp = in.readLine();
                        //String jsProd = in.readLine();
                        ArrayList<String> compName = new ArrayList<>();
                        ArrayList<String> prodName = new ArrayList<>();
                        ResultSet getCompName = dbHandler.getCompanyAll();
                        ResultSet getProdName = dbHandler.getProductsAll();
                        while (true){
                            if(!getProdName.next()) break;
                            prodName.add(getProdName.getString("Name"));
                        }
                        while (true){
                            if(!getCompName.next()) break;
                            compName.add(getCompName.getString("Name"));
                        }
                        // для проверок вроде
                        out.println(gson.toJson(compName));
                        System.out.println(compName);
                        out.println(gson.toJson(prodName));
                        System.out.println(prodName);

                        out.println(gson.toJson(trans));
                        out.flush();
                        String msg = in.readLine();
                        String mese = gson.fromJson(msg, String.class);
                        if(mese.equals("true")){
                            String ie = in.readLine();
                            IEClass ieClass = gson.fromJson(ie, IEClass.class);
                            String nameProd = ieClass.getProductName();
                            System.out.println(nameProd);
                            String nameComp = ieClass.getCompanyName();
                            System.out.println(nameComp);
                            Products product = new Products();
                            Companies company = new Companies();
                            product.setName(nameProd);
                            company.setName(nameComp);
                            System.out.println(nameComp + "/" + nameProd);
                            ResultSet resProd = dbHandler.getProductByName(product);
                            ResultSet resComp = dbHandler.getCompanyByName(company);
                            String prodId = null;
                            String compId = null;
                            while (true){
                                if(!resProd.next()) break;
                                prodId = resProd.getString("idProduct");
                            }
                            while (true){
                                if(!resComp.next()) break;
                                compId = resComp.getString("idCompany");
                            }

                            trans.setDate(ieClass.getDate());
                            trans.setAmount(ieClass.getAmount());
                            trans.setType(ieClass.getType());
                            trans.setIdProduct(prodId);
                            System.out.println(prodId);

                            trans.setIdCompany(compId);
                            System.out.println(compId);
                            dbHandler.EditTransactions(trans);

                        }
                        break;
                    }
                    case SHOW_TRANSACTIONS:{
                        ArrayList<IEClass> ieArray = new ArrayList<>();
                        ArrayList<Transactions> transac = new ArrayList<>();
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ResultSet resultTrans = dbHandler.getTransactionAll();
                        //ResultSet resultProd = dbHandler.getCompanyAll();
                        //ResultSet resultComp = dbHandler.getProductsAll();
                        ArrayList<String> idProd = new ArrayList<>();
                        ArrayList<String> idComp = new ArrayList<>();
                        //ArrayList<String> nameProd = new ArrayList<>();
                        //ArrayList<String> nameComp = new ArrayList<>();
                        String nameProd, nameComp;
                        nameProd = "";
                        nameComp = "";
                        Companies companies = new Companies();
                        Products products = new Products();
                        try {
                            while (true) {
                                if (!resultTrans.next()) break;
                                String idTransactions = resultTrans.getString("idTransactions");
                                String idProduct = resultTrans.getString("idProduct");
                                String idCompany = resultTrans.getString("idCompany");
                                String amount = resultTrans.getString("Amount");
                                String date = resultTrans.getString("Date");
                                String typeTr = resultTrans.getString("Type");
                                Transactions transactions = new Transactions(idTransactions, idProduct, idCompany, amount, date, typeTr);
                                transac.add(transactions);
                                companies.setIdCompany(idCompany);
                                products.setIdProduct(idProduct);
                                //idProd.add(idProduct);
                                //idComp.add(idCompany);
                                ResultSet resultIdComp = dbHandler.getCompanyByID(companies);
                                ResultSet resultIdProd = dbHandler.getProductByID(products);
                                while (true){
                                    if (!resultIdComp.next()) break;
                                    nameComp = resultIdComp.getString("Name");
                                }
                                while (true){
                                    if (!resultIdProd.next()) break;
                                    nameProd = resultIdProd.getString("Name");
                                }
                                IEClass ieclass = new IEClass(idTransactions, nameProd, nameComp, amount, date, typeTr);

                                System.out.println(ieclass);
                                ieArray.add(ieclass);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //for (String pr : idProd)
                        //System.out.println(idProd);
                        //System.out.println("Comp" + idComp);
                        out.println(gson.toJson(ieArray));
                        out.flush();
                        out.println(gson.toJson(transac));
                        out.flush();
                        break;
                    }
                    case LOGIN_USER: {
                        System.out.println("LOGIN_USER");
                        int coun = 0;

                        //User admin = new User();
//                        admin.setLogin("админ");
//                        admin.setPassword("админ");
                        User userLogin = gson.fromJson(request.getRequestMessage(), User.class);
                        System.out.println(userLogin.getLogin());
                        ResultSet resultUser = dbHandler.getUserByLogAndPass(userLogin);
                        String resRole = null;
                        while (true) {
                            try {
                                if (!resultUser.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            coun++;
                            resRole = resultUser.getString(Const.USER_ROLE);
                        }
                        //assert resRole != null;
                        if (resRole == null) {
                            out.println(gson.toJson("error"));
                            out.flush();
                        } else if (resRole.equals(Const.USER_ROLE_USER)) {
                            out.println(gson.toJson("false"));
                            out.flush();
                        } else {
                            out.println(gson.toJson("true"));
                            out.flush();
                        }

                        out.println(gson.toJson(coun));
                        out.flush();
                        break;
                    }
                    case SIGN_UP_USER: {
                        //System.out.println("SIGN_UP_USER");
                        String type = gson.fromJson(request.getRequestMessage(), String.class);
                        System.out.println(type);
                        ArrayList<String> companyName = new ArrayList<>();
                        ResultSet resultCompanies = dbHandler.getCompanyAll();
                        while (true) {
                            try {
                                if (!resultCompanies.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                companyName.add(resultCompanies.getString(Const.COMPANIES_NAME));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        out.println(gson.toJson(companyName));
                        out.flush();

                        String comp = in.readLine();
                        Companies companies = gson.fromJson(comp, Companies.class);
                        //companies
                        ResultSet compRes = dbHandler.getCompanyByName(companies);
                        System.out.println(companies.getName());
                        String idCompany = "3";
                        while (true) {
                            try {
                                if (!compRes.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                idCompany = compRes.getString(Const.COMPANIES_ID);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("ID: "+idCompany);
                        out.println(gson.toJson(idCompany));
                        out.flush();

                        String userJson = in.readLine();
                        User userCheck = gson.fromJson(userJson, User.class);
                        int counter = 0;
                        ResultSet result = dbHandler.getUserByLogin(userCheck);
                        while (true) {
                            try {
                                if (!result.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            counter++;
                        }
                        System.out.println(userCheck.getLogin());
                        System.out.println("COUNTER: " + counter);
                        out.println(gson.toJson(counter));
                        out.flush();

                        String check = in.readLine();
                        String chek = gson.fromJson(check, String.class);
                        if(chek.equals("true")){
                            System.out.println("TRUE");
                            String userAdd = in.readLine();
                            User user = gson.fromJson(userAdd, User.class);
                            dbHandler.AddUser(user);
                        }
                        else
                            System.out.println("FALSE");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("Клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " закрыл соединение.");
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
