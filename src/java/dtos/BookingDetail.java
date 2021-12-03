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
public class BookingDetail {
    private Booking booking;
    private RoomType roomType;
    private int quantity;

    public BookingDetail() {
    }

    public BookingDetail(Booking booking, RoomType roomType, int quantity) {
        this.booking = booking;
        this.roomType = roomType;
        this.quantity = quantity;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    

    
    
}
