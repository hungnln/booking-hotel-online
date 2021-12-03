/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author SE140018
 */
public class CustomerPojo {
    @SerializedName("email") protected String email;
    @SerializedName("name") protected String name;
    @SerializedName("phone") protected String phone;

    public CustomerPojo() {
    }

    public CustomerPojo(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   
    
}
