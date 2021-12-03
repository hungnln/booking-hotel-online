/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Booking;
import dtos.BookingCartItem;
import dtos.BookingDetail;
import dtos.RoomType;
import dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class BookingDetailDAO {

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

    public boolean createBookingDetail(String bookingID, BookingCartItem item) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Insert into tbl_BookingDetails(bookingID,roomID,bookingQuantity) values (?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, bookingID);
                pstmt.setString(2, item.getRoomType().getRoomID());
                pstmt.setInt(3, item.getQuantity());
                return pstmt.executeUpdate() > 0;
            }
        } finally {
            closeConection();
        }
        return false;
    }

    public List<BookingDetail> getBookingDetailByBookingID(String bookingID,User user) throws Exception {
        List<BookingDetail> list = new ArrayList<>();
        RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = bookingDAO.getBookingByBookingIDAndUser(bookingID, user);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select bookingID,roomID,bookingQuantity from tbl_BookingDetails where bookingID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, bookingID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    RoomType roomType = roomTypeDAO.getRoomTypeByRoomID(roomID);
                    int bookingQuanity = rs.getInt("bookingQuantity");
                    BookingDetail bookingDetail = new BookingDetail(booking, roomType, bookingQuanity);
                    list.add(bookingDetail);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }
}
