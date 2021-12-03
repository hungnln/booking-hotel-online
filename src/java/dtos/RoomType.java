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
public class RoomType {
    private String roomID;
    private Hotel hotel;
    private Type type;
    private long roomPrice;
    private boolean roomStatus;
    private String roomImage;
    private int roomQuantity;
    private String roomDescription;
    private int roomActiveQuantity;
    

    public RoomType() {
    }

    public RoomType(String roomID, Hotel hotel, Type type, long roomPrice, boolean roomStatus, String roomImage, int roomQuantity, String roomDescription) {
        this.roomID = roomID;
        this.hotel = hotel;
        this.type = type;
        this.roomPrice = roomPrice;
        this.roomStatus = roomStatus;
        this.roomImage = roomImage;
        this.roomQuantity = roomQuantity;
        this.roomDescription = roomDescription;
    }

    public RoomType(String roomID, Hotel hotel, Type type, long roomPrice, boolean roomStatus, String roomImage, int roomQuantity, String roomDescription, int roomActiveQuantity) {
        this.roomID = roomID;
        this.hotel = hotel;
        this.type = type;
        this.roomPrice = roomPrice;
        this.roomStatus = roomStatus;
        this.roomImage = roomImage;
        this.roomQuantity = roomQuantity;
        this.roomDescription = roomDescription;
        this.roomActiveQuantity = roomActiveQuantity;
    }

    public int getRoomActiveQuantity() {
        return roomActiveQuantity;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }   

    public long getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(long roomPrice) {
        this.roomPrice = roomPrice;
    }

    public boolean isRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    

    

    
    
}
