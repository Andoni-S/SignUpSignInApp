/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public class User {
    private int id;
    private String login;
    private String password;
    private String street;
    private String postalCode;
    private String province;
    private String name;
    private String mobilePhone;

    public User() {
    }

    public User(int id, String login, String password, String street, String postalCode, String province, String name, String mobilePhone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.street = street;
        this.postalCode = postalCode;
        this.province = province;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getName() {
        return name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    
}
