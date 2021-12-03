/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.PromotionCode;
import com.stripe.model.PromotionCodeCollection;
import dtos.CouponPojo;
import dtos.CouponPojos;
import dtos.CustomerPojo;
import dtos.CustomerPojos;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SE140018
 */
public class StripeAPI {

    public static Object createCouponStripeAPI() throws StripeException {
        Stripe.apiKey = "pk_test_51JhRXlGfUDxPF6UAnEcCPReudDdoFc5kquwpOkGQZc4OL6UCcUMufXNGLpI9AkMMIbU7Ft5sM2QPKckL541IXEaY006Z4ViqCW";
        Map<String, Object> params = new HashMap<>();
        params.put("percent_off", 10);
        params.put("duration", "repeating");
        params.put("duration_in_months", 3);

        Coupon coupon = Coupon.create(params);
        return coupon;
    }

    public static Object retriveCouponStripeAPI() throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Coupon coupon = Coupon.retrieve("DISCOUNTNEWACCOUNT");
        return coupon;
    }

    public static CouponPojo retrivePromoteCodeStripeAPI() throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        PromotionCode promotionCode = PromotionCode.retrieve("promo_1JhUaIGfUDxPF6UAdbyh33kg");
        CouponPojo coupon = new Gson().fromJson(promotionCode.toJson(), CouponPojo.class);
        return coupon;
    }

    public static CouponPojos getAllPromotionCode() throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Map<String, Object> params = new HashMap<>();
        PromotionCodeCollection promotionCodes = PromotionCode.list(params);
        CouponPojos coupons = new Gson().fromJson(promotionCodes.toJson(), CouponPojos.class);
        return coupons;
    }

    public static double checkPromotionCodeAndGetDiscount(String email, String code) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Map<String, Object> params = new HashMap<>();
        PromotionCodeCollection promotionCodes = PromotionCode.list(params);
        CouponPojos couponPojos = new Gson().fromJson(promotionCodes.toJson(), CouponPojos.class);
        for (CouponPojo couponPojo : couponPojos.getCouponPojos()) {
            if (couponPojo.getCode().equals(code) & couponPojo.isActive()) {
                if (couponPojo.getCustomer() == null) {
                    return (double) couponPojo.getCoupon().get("percent_off");
                } else {
                    if (getCustomerPojoByID(couponPojo.getCustomer()).getEmail().equals(email)) {
                        return (double) couponPojo.getCoupon().get("percent_off");
                    }
                }
            }
        }
        return -1;
    }

    public static String createPromotionCode(String email) throws ParseException, StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 3);
        date = c.getTime();
        java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
        Map<String, Object> params = new HashMap<>();
        params.put("coupon", "DISCOUNTNEWACCOUNT");
        params.put("customer", getCustomerPojoIDByEmail(email));
        params.put("expires_at", ts.getTime() / 1000);
        params.put("max_redemptions", 1);
        PromotionCode promotionCode = PromotionCode.create(params);
        CouponPojo couponPojo = new Gson().fromJson(promotionCode.toJson(), CouponPojo.class);
        return couponPojo.getCode();

    }

    public static boolean updatePromotionCode(String promotionCodeID) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        PromotionCode promotionCode = PromotionCode.retrieve(promotionCodeID);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("order_id", "6735");
        Map<String, Object> params = new HashMap<>();
        params.put("active", false);
        params.put("metadata", metadata);
        return promotionCode.update(params) != null;
    }

    public static boolean createCustomerPojo(CustomerPojo customerPojo) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Map<String, Object> params = new HashMap<>();
        params.put("name", customerPojo.getName());
        params.put("email", customerPojo.getEmail());
        params.put("phone", customerPojo.getPhone());
        return Customer.create(params) != null;
    }

    public static CustomerPojo getCustomerPojoByID(String id) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Customer customer = Customer.retrieve(id);
        CustomerPojo customerPojo = new Gson().fromJson(customer.toJson(), CustomerPojo.class);
        return customerPojo;
    }

    public static CustomerPojo getCustomerPojoByEmail(String email) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Map<String, Object> params = new HashMap<>();
        CustomerCollection customers = Customer.list(params);
        CustomerPojos customerPojos = new Gson().fromJson(customers.toJson(), CustomerPojos.class);
        for (CustomerPojo customerPojo : customerPojos.getCustomerPojos()) {
            if (customerPojo.getEmail().equals(email)) {
                return customerPojo;
            }
        }
        return null;
    }

    public static String getCustomerPojoIDByEmail(String email) throws StripeException {
        Stripe.apiKey = "sk_test_51JhRXlGfUDxPF6UANzFvE7wA8O6h1ZbFVvbYSTW3YnPS9IBoILstAKh5ja3HTgRfsRJJEgW3zpWDk4zYaVS9vQCt0050SZgvWy";
        Map<String, Object> params = new HashMap<>();
        CustomerCollection customers = Customer.list(params);
        for (Customer customerPojo : customers.getData()) {
            if (customerPojo.getEmail().equals(email)) {
                return customerPojo.getId();
            }
        }
        return null;
    }

    public void seen() {

    }
}
