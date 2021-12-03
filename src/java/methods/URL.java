/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

/**
 *
 * @author SE140018
 */
public class URL {

    private static final String subPath = "WEB-INF/views/";
    private static final String LOGIN_PAGE = subPath + "login.jsp";
    private static final String LOGOUT_PAGE = subPath + "logout.jsp";
    private static final String SIGNUP_PAGE = subPath + "signup.jsp";
    private static final String INDEX = subPath + "index.jsp";
    private static final String PROFILE_PAGE = subPath + "user-profile.jsp";
    private static final String CHANGEPASSWORD_PAGE = subPath + "user-password-change.jsp";
    private static final String CHOOSE_HOTEL_PAGE = subPath + "hotel.jsp";
    private static final String CHOOSE_ROOM_PAGE = subPath + "room.jsp";
    private static final String CART = subPath + "view-cart.jsp";
    private static final String BOOKING_DETAIL = subPath + "user-booking-detail.jsp";
    private static final String BOOKING_ALL = subPath + "user-booking.jsp";
    private static final String INDEX_SERVLET = "Index";
    private static final String INDEX_ADMIN_PAGE = subPath + "admin-index.jsp";
    private static final String USER_BOOKING_DETAIL_SERVLET = "UserBookingDetail";
    private static final String ERROR_PAGE = subPath +"error.jsp";

    public static String getERROR_PAGE() {
        return ERROR_PAGE;
    }   

    public static String getUSER_BOOKING_DETAIL_SERVLET() {
        return USER_BOOKING_DETAIL_SERVLET;
    }

    public static String getINDEX_ADMIN_PAGE() {
        return INDEX_ADMIN_PAGE;
    }

    public static String getINDEX_SERVLET() {
        return INDEX_SERVLET;
    }

    public static String getLOGIN_PAGE() {
        return LOGIN_PAGE;
    }

    public static String getLOGOUT_PAGE() {
        return LOGOUT_PAGE;
    }

    public static String getSIGNUP_PAGE() {
        return SIGNUP_PAGE;
    }

    public static String getINDEX() {
        return INDEX;
    }

    public static String getPROFILE_PAGE() {
        return PROFILE_PAGE;
    }

    public static String getCHANGEPASSWORD_PAGE() {
        return CHANGEPASSWORD_PAGE;
    }

    public static String getCHOOSE_HOTEL_PAGE() {
        return CHOOSE_HOTEL_PAGE;
    }

    public static String getCHOOSE_ROOM_PAGE() {
        return CHOOSE_ROOM_PAGE;
    }

    public static String getCART() {
        return CART;
    }

    public static String getBOOKING_DETAIL() {
        return BOOKING_DETAIL;
    }

    public static String getBOOKING_ALL() {
        return BOOKING_ALL;
    }

}
