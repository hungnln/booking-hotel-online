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
public class BookingCartItem {
    private Hotel hotel;
    private RoomType roomType;
    private int quantity;

    public BookingCartItem() {
    }

    public BookingCartItem(Hotel hotel, RoomType roomType, int quantity) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.quantity = quantity;
    }

    

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
