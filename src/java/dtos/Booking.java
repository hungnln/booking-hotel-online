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
public class Booking {
    private String bookingID;
    private User user;
    private long bookingTotal;
    private String createDate;
    private String checkinDate;
    private String checkoutDate;
    private String discountCode ;
    private int bookingStatus;
    private boolean bookingCancel;
    private Hotel hotel;

    public Booking() {
    }

    public Booking(String bookingID, User user, long bookingTotal, String createDate, String checkinDate, String checkoutDate, String discountCode, int bookingStatus, boolean bookingCancel, Hotel hotel) {
        this.bookingID = bookingID;
        this.user = user;
        this.bookingTotal = bookingTotal;
        this.createDate = createDate;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.discountCode = discountCode;
        this.bookingStatus = bookingStatus;
        this.bookingCancel = bookingCancel;
        this.hotel = hotel;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isBookingCancel() {
        return bookingCancel;
    }

    public void setBookingCancel(boolean bookingCancel) {
        this.bookingCancel = bookingCancel;
    }

    

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getBookingTotal() {
        return bookingTotal;
    }

    public void setBookingTotal(long bookingTotal) {
        this.bookingTotal = bookingTotal;
    }
   

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }  

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    
    
}
