package Server.DBClass;

public class Companies {
    private String idCompany;
    private String Name;

    public Companies(String idCompany, String name) {
        this.idCompany = idCompany;
        Name = name;
    }

    public Companies(){    }
    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
