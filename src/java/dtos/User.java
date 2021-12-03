/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author SE140018
 */
public class User {
    private String userID;
    private String userPassword;
    private String userName;
    private String userAddress;
    private String userPhone;
    private String userCreateDate;
    private boolean userStatus;
    private int userRole;

    public User() {
    }

    public User(String userID, String userName, String userAddress, String userPhone, String userCreateDate, boolean userStatus, int userRole) {
        this.userID = userID;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userCreateDate = userCreateDate;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public User(String userID, String userPassword, String userName, String userAddress, String userPhone, String userCreateDate, boolean userStatus, int userRole) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userCreateDate = userCreateDate;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(String userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
    
}
