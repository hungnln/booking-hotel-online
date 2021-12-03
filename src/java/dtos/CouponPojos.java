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
public class CouponPojos {

    @SerializedName("data")
    protected ArrayList<CouponPojo> couponPojos;

    public CouponPojos() {
    }

    public CouponPojos(ArrayList<CouponPojo> couponPojos) {
        this.couponPojos = couponPojos;
    }

    public ArrayList<CouponPojo> getCouponPojos() {
        return couponPojos;
    }

    

}
