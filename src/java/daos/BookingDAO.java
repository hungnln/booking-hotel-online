/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Booking;
import dtos.Hotel;
import dtos.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import methods.Variable;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class BookingDAO {

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

    public boolean createBooking(Booking booking) throws Exception {
        Date date = new Date();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Insert into tbl_Bookings(bookingID,userID,bookingTotal,createDate,checkin,checkout,discount,isCanceled,hotelID,bookingStatus) values(?,?,?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, booking.getBookingID());
                pstmt.setString(2, booking.getUser().getUserID());
                pstmt.setLong(3, booking.getBookingTotal());
                pstmt.setTimestamp(4, Variable.convertDateToTimestamp(date));
                pstmt.setTimestamp(5, Variable.SQL_CHECK_IN_DATE(booking.getCheckinDate()));
                pstmt.setTimestamp(6, Variable.SQL_CHECK_OUT_DATE(booking.getCheckoutDate()));
                pstmt.setString(7, booking.getDiscountCode());
                pstmt.setBoolean(8, booking.isBookingCancel());
                pstmt.setString(9, booking.getHotel().getHotelID());
                pstmt.setInt(10, booking.getBookingStatus());
                return pstmt.executeUpdate() > 0;
            }
        } finally {
            closeConection();
        }
        return false;
    }
    public Booking getBookingByBookingIDAndUser(String bookingID,User user) throws Exception {      
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select bookingTotal,createDate,checkin,checkout,discount,isCanceled,hotelID,bookingStatus \n"
                        + "from tbl_Bookings\n"
                        + "where bookingID = ? and userID=?";                     
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, bookingID);
                pstmt.setString(2, user.getUserID());
                rs = pstmt.executeQuery();
                while (rs.next()) {                   
                    long bookingTotal = rs.getLong("bookingTotal");
                    String createDate = Variable.convertTimestampToString(rs.getTimestamp("createDate"));
                    String checkin = Variable.convertTimestampToString(rs.getTimestamp("checkin"));
                    String checkout = Variable.convertTimestampToString(rs.getTimestamp("checkout"));
                    String discount = rs.getString("discount");
                    boolean isCanceled = rs.getBoolean("isCanceled");
                    String hotelID = rs.getString("hotelID");
                    int bookingStatus = rs.getInt("bookingStatus");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    return new Booking(bookingID, user, bookingTotal, createDate, checkin, checkout, discount, bookingStatus, isCanceled, hotel);                   
                }
            }
        } finally {
            closeConection();
        }
        return null;

    }

    public List<Booking> getAllBookingByUser(User user) throws Exception {
        List<Booking> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select bookingID,bookingTotal,createDate,checkin,checkout,discount,isCanceled,hotelID,bookingStatus \n"
                        + "from tbl_Bookings\n"
                        + "where userID = ? \n"
                        + "order by createDate desc";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String bookingID = rs.getString("bookingID");
                    long bookingTotal = rs.getLong("bookingTotal");
                    String createDate = Variable.convertTimestampToString(rs.getTimestamp("createDate"));
                    String checkin = Variable.convertTimestampToString(rs.getTimestamp("checkin"));
                    String checkout = Variable.convertTimestampToString(rs.getTimestamp("checkout"));
                    String discount = rs.getString("discount");
                    boolean isCanceled = rs.getBoolean("isCanceled");
                    String hotelID = rs.getString("hotelID");
                    int bookingStatus = rs.getInt("bookingStatus");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    Booking booking = new Booking(bookingID, user, bookingTotal, createDate, checkin, checkout, discount, bookingStatus, isCanceled, hotel);
                    list.add(booking);
                }
            }
        } finally {
            closeConection();
        }
        return list;

    }

    public List<Booking> getAllBookingByUserByHotelName(User user, String hotelName) throws Exception {
        List<Booking> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select tbl_Hotels.hotelID,bookingID,bookingTotal,createDate,checkin,checkout,discount,isCanceled,bookingStatus \n"
                        + "from tbl_Bookings join tbl_Hotels on tbl_Bookings.hotelID = tbl_Hotels.hotelID\n"
                        + "where userID = ? and tbl_Hotels.hotelName like ? \n"
                        + "order by createDate desc";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserID());
                pstmt.setString(2, "%" + hotelName + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String bookingID = rs.getString("bookingID");
                    long bookingTotal = rs.getLong("bookingTotal");
                    String createDate = Variable.convertTimestampToString(rs.getTimestamp("createDate"));
                    String checkin = Variable.convertTimestampToString(rs.getTimestamp("checkin"));
                    String checkout = Variable.convertTimestampToString(rs.getTimestamp("checkout"));
                    String discount = rs.getString("discount");
                    boolean isCanceled = rs.getBoolean("isCanceled");
                    String hotelID = rs.getString("hotelID");
                    int bookingStatus = rs.getInt("bookingStatus");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    Booking booking = new Booking(bookingID, user, bookingTotal, createDate, checkin, checkout, discount, bookingStatus, isCanceled, hotel);
                    list.add(booking);
                }
            }
        } finally {
            closeConection();
        }
        return list;

    }

    public List<Booking> getAllBookingByUserByHotelNameByRangeCreateDate(User user, String hotelName, String firstCreateDate, String lastCreateDate) throws Exception {
        List<Booking> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select tbl_Hotels.hotelID,bookingID,bookingTotal,createDate,checkin,checkout,discount,isCanceled,bookingStatus \n"
                        + "from tbl_Bookings join tbl_Hotels on tbl_Bookings.hotelID = tbl_Hotels.hotelID\n"
                        + "where userID = ? and tbl_Hotels.hotelName like ? and createDate between ? and ?\n"
                        + "order by createDate desc";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserID());
                pstmt.setString(2, "%" + hotelName + "%");
                pstmt.setTimestamp(3, Variable.convertDateStringToTimestamp(firstCreateDate));
                pstmt.setTimestamp(4, Variable.convertDateStringToTimestamp(lastCreateDate));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String bookingID = rs.getString("bookingID");
                    long bookingTotal = rs.getLong("bookingTotal");
                    String createDate = Variable.convertTimestampToString(rs.getTimestamp("createDate"));
                    String checkin = Variable.convertTimestampToString(rs.getTimestamp("checkin"));
                    String checkout = Variable.convertTimestampToString(rs.getTimestamp("checkout"));
                    String discount = rs.getString("discount");
                    boolean isCanceled = rs.getBoolean("isCanceled");
                    String hotelID = rs.getString("hotelID");
                    int bookingStatus = rs.getInt("bookingStatus");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    Booking booking = new Booking(bookingID, user, bookingTotal, createDate, checkin, checkout, discount, bookingStatus, isCanceled, hotel);
                    list.add(booking);
                }
            }
        } finally {
            closeConection();
        }
        return list;

    }

    public List<Booking> getAllBookingByUserByRangeCreateDate(User user, String firstCreateDate, String lastCreateDate) throws Exception {
        List<Booking> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select bookingID,bookingTotal,createDate,checkin,checkout,discount,isCanceled,hotelID,bookingStatus \n"
                        + "from tbl_Bookings\n"
                        + "where userID = ? and createDate between ? and ?\n"
                        + "order by createDate desc";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserID());
                pstmt.setTimestamp(2, Variable.convertDateStringToTimestamp(firstCreateDate));
                pstmt.setTimestamp(3, Variable.convertDateStringToTimestamp(lastCreateDate));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String bookingID = rs.getString("bookingID");
                    long bookingTotal = rs.getLong("bookingTotal");
                    String createDate = Variable.convertTimestampToString(rs.getTimestamp("createDate"));
                    String checkin = Variable.convertTimestampToString(rs.getTimestamp("checkin"));
                    String checkout = Variable.convertTimestampToString(rs.getTimestamp("checkout"));
                    String discount = rs.getString("discount");
                    boolean isCanceled = rs.getBoolean("isCanceled");
                    String hotelID = rs.getString("hotelID");
                    int bookingStatus = rs.getInt("bookingStatus");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    Booking booking = new Booking(bookingID, user, bookingTotal, createDate, checkin, checkout, discount, bookingStatus, isCanceled, hotel);
                    list.add(booking);
                }
            }
        } finally {
            closeConection();
        }
        return list;

    }
    public boolean changeStatusBooking(Booking b) throws Exception{
        try {
            con =db.getConnection();
            if (con != null) {
                String sql = "Update tbl_Bookings set bookingStatus=? where bookingID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, b.getBookingStatus());
                pstmt.setString(2, b.getBookingID());
                return pstmt.executeUpdate() >0;
            }
        } finally{
            closeConection();
        }
        return false;
    }
    public boolean deleteBooking(String bookingID) throws Exception{
        try {
            con =db.getConnection();
            if (con != null) {
                String sql = "Update tbl_Bookings set isCanceled=?,bookingStatus = ? where bookingID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setBoolean(1, true);
                pstmt.setInt(2, 0);
                pstmt.setString(3, bookingID);
                return pstmt.executeUpdate() >0;
            }
        } finally{
            closeConection();
        }
        return false;
    }
}
