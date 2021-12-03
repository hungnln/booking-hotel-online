/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Booking;
import dtos.Hotel;
import dtos.Rating;
import dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class RatingDAO {

    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private DBContext db = new DBContext();

    private void closeConection() throws Exception {
        if (con != null) {
            con.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }

    }

    public boolean createRating(Rating rating) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Insert into tbl_Ratings(userID,hotelID,description,bookingID,score) values (?,?,?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, rating.getUser().getUserID());
                pstmt.setString(2, rating.getHotel().getHotelID());
                pstmt.setString(3, rating.getDescription());
                pstmt.setString(4, rating.getBooking().getBookingID());
                pstmt.setInt(5, rating.getScore());
                return pstmt.executeUpdate() > 0;
            }
        } finally {
            closeConection();
        }
        return false;
    }

    public Rating getRatingByBookingID(Booking booking) throws Exception {
        UserDAO userDAO = new UserDAO();
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select userID,hotelID,description,bookingID,score from tbl_Ratings where bookingID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, booking.getBookingID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    User user = userDAO.getUserByID(userID);
                    String hotelID = rs.getString("hotelID");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    String description = rs.getString("description");
                    int score = rs.getInt("score");
                    return new Rating(hotel, user, description, booking, score);
                }
            }
        } finally {
            closeConection();
        }
        return null;

    }
}
