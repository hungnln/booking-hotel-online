/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.text.DecimalFormat;

/**
 *
 * @author SE140018
 */
public class Hotel {

    private String hotelID;
    private String hotelName;
    private float hotelRatingScore;
    private int hotelRatingCount;
    private String hotelImage;
    private Area area;
    private static DecimalFormat decimalFormat  = new DecimalFormat("#.##");
    private String hotelRating ;
    private int roomActive;
    private int roomAllQuantity;
            

    public Hotel() {
    }
    public Hotel(String hotelID, String hotelName, float hotelRatingScore, int hotelRatingCount, String hotelImage, Area area,int roomAllQuantity) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelRatingScore = hotelRatingScore;
        this.hotelRatingCount = hotelRatingCount;
        this.hotelImage = hotelImage;
        this.area = area;
        this.hotelRating = decimalFormat.format(hotelRatingScore/hotelRatingCount);
        this.roomAllQuantity = roomAllQuantity;
    }
    

    public Hotel(String hotelID, String hotelName, float hotelRatingScore, int hotelRatingCount, String hotelImage, Area area,int roomAllQuantity, int roomActive) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelRatingScore = hotelRatingScore;
        this.hotelRatingCount = hotelRatingCount;
        this.hotelImage = hotelImage;
        this.area = area;
        this.hotelRating =decimalFormat.format(hotelRatingScore/hotelRatingCount);
        this.roomActive = roomActive;
        this.roomAllQuantity = roomAllQuantity;
    }

    public int getRoomActive() {
        return roomActive;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public float getHotelRatingScore() {
        return hotelRatingScore;
    }

    public void setHotelRatingScore(float hotelRatingScore) {
        this.hotelRatingScore = hotelRatingScore;
    }

    public int getHotelRatingCount() {
        return hotelRatingCount;
    }

    public void setHotelRatingCount(int hotelRatingCount) {
        this.hotelRatingCount = hotelRatingCount;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public int getRoomAllQuantity() {
        return roomAllQuantity;
    }

    public void setRoomAllQuantity(int roomAllQuantity) {
        this.roomAllQuantity = roomAllQuantity;
    }
    

    

    

}
