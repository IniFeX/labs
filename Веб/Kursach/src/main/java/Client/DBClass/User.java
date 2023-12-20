package Client.DBClass;

public class User {
    private String idUser;
    private String login;
    private String password;
    private String gender;
    private String role;
    private String idCompany;

    public User(String idUser, String login, String password, String gender, String role, String idCompany) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.idCompany = idCompany;
    }
    public User( String login, String password, String gender, String role, String idCompany) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.idCompany = idCompany;
    }


    public User() {}

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
