package libraries;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public class User implements Serializable{
    private int id; //id res_users, uid res_groups_users_rel, user_id res_company_users_rel
    private int companyId; //cid res_company_users_rel
    private String login; //login res_users
    private String password; //password res_users
    private String postalCode; //zip res_partner
    private String address; //street res_partner
    private String name; //name res_partner
    private String mobilePhone; //mobile res_partner
    private boolean active; //active res_partner
    //private NotificationType notificationType; //notification_type res_users
    
    public User() {
        this.id = -1;
        this.login = "";
        this.password = "";
        this.postalCode = "";
        this.address = "";
        this.name = "";
        this.mobilePhone = "";
        
        this.companyId = 1;
        this.active= true;
        //this.notificationType = NotificationType.email;
    }

    public User(int id, String login, String password, String postalCode, String address, String name, String mobilePhone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.postalCode = postalCode;
        this.address = address;
        this.name = name;
        this.mobilePhone = mobilePhone;
        
        this.companyId = 1;
        this.active= true;
        //this.notificationType = NotificationType.email;
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

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
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

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCompanyId() {
        return companyId;
    }

    public boolean isActive() {
        return active;
    }
    
    /*public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }*/
}
