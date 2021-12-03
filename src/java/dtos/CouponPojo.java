/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author SE140018
 */
public class CouponPojo {

    @SerializedName("id")
    protected String id;
    @SerializedName("code")
    protected String code;
    @SerializedName("coupon")
    protected HashMap<String, Object> coupon;
    @SerializedName("metadata")
    protected HashMap<String, Object> metadata;
    @SerializedName("expires_at")
    protected long expires_at;
    @SerializedName("customer")
    protected String customer;
    @SerializedName("max_redemptions")
    protected int max_redemptions;
    @SerializedName("times_redeemed")
    protected int times_redeemed; 
    @SerializedName("active")
    protected boolean active;

    public CouponPojo(String id, String code, HashMap<String, Object> coupon, HashMap<String, Object> metadata, long expires_at, String customer, int max_redemptions, int times_redeemed, boolean active) {
        this.id = id;
        this.code = code;
        this.coupon = coupon;
        this.metadata = metadata;
        this.expires_at = expires_at;
        this.customer = customer;
        this.max_redemptions = max_redemptions;
        this.times_redeemed = times_redeemed;
        this.active = active;
    }

   

   

    

    

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public HashMap<String, Object> getCoupon() {
        return coupon;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public long getExpires_at() {
        return expires_at;
    }

    public String getCustomer() {
        return customer;
    }

    

    public int getMax_redemptions() {
        return max_redemptions;
    }

    public int getTimes_redeemed() {
        return times_redeemed;
    }

    public boolean isActive() {
        return active;
    }
    

}
