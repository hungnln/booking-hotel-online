/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author SE140018
 */
public class CustomerPojos {

    @SerializedName("data")
    protected ArrayList<CustomerPojo> customerPojos;

    public CustomerPojos(ArrayList<CustomerPojo> customerPojos) {
        this.customerPojos = customerPojos;
    }

    public ArrayList<CustomerPojo> getCustomerPojos() {
        return customerPojos;
    }

}
