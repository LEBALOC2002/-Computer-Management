package model;

public class Account {
protected int id ;
protected String gmail ;
protected String password ;
protected String fullName ;
protected int idCountry ;


    public Account() {
    }

    public Account(int id, String gmail, String password, String fullName, int idCountry) {
        this.id = id;
        this.gmail = gmail;
        this.password = password;
        this.fullName = fullName;
        this.idCountry = idCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }
}
