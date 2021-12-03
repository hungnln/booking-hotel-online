/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Area;
import dtos.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import methods.Variable;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class HotelDAO {

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

    public HotelDAO() {
    }

    public Hotel getHotelByID(String hotelID) throws Exception {
        Hotel hotel = null;
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select hotelName,hotelRatingScore,hotelImage,areaID,hotelRatingCount,roomAllQuantity from tbl_Hotels where hotelID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, hotelID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String hotelName = rs.getString("hotelName");
                    float hotelRating = Float.parseFloat(rs.getString("hotelRatingScore"));
                    String hotelImage = rs.getString("hotelImage");
                    String areaID = rs.getString("areaID");
                    AreaDAO areaDAO = new AreaDAO();
                    Area area = areaDAO.getAreaByID(areaID);
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    hotel = new Hotel(hotelID, hotelName, hotelRating, hotelRatingCount, hotelImage, area, roomAllQuantity);
                }
            }
        } finally {
            closeConection();
        }
        return hotel;
    }
    public List<Hotel> getAllHotelsByArea(String areaID) throws Exception {
        List<Hotel> list = new ArrayList<>();
        AreaDAO areaDAO = new AreaDAO();
        Area area = areaDAO.getAreaByID(areaID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select hotelID,hotelName,hotelRatingScore,hotelImage,hotelRatingCount,roomAllQuantity from tbl_Hotels where areaID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, areaID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    float hotelRatingScore = Float.parseFloat(rs.getString("hotelRatingScore"));
                    String hotelImage = rs.getString("hotelImage");
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    Hotel hotel = new Hotel(hotelID, hotelName, hotelRatingScore, hotelRatingCount, hotelImage, area, roomAllQuantity);
                    list.add(hotel);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }
    public List<Hotel> getAllHotels() throws Exception {
        List<Hotel> list = new ArrayList<>();
        AreaDAO areaDAO = new AreaDAO();       
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select areaID,hotelID,hotelName,hotelRatingScore,hotelImage,hotelRatingCount,roomAllQuantity from tbl_Hotels";
                pstmt = con.prepareStatement(sql);               
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String areaID = rs.getString("areaID");
                     Area area = areaDAO.getAreaByID(areaID);
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    float hotelRatingScore = Float.parseFloat(rs.getString("hotelRatingScore"));
                    String hotelImage = rs.getString("hotelImage");
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    Hotel hotel = new Hotel(hotelID, hotelName, hotelRatingScore, hotelRatingCount, hotelImage, area, roomAllQuantity);
                    list.add(hotel);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }
    

    public List<Hotel> getAllHotelsByAreaByHotelName(String areaID, String name) throws Exception {
        List<Hotel> list = new ArrayList<>();
        AreaDAO areaDAO = new AreaDAO();
        Area area = areaDAO.getAreaByID(areaID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select hotelID,hotelName,hotelRatingScore,hotelImage,hotelRatingCount,roomAllQuantity from tbl_Hotels where areaID = ? and hotelName like ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, areaID);
                pstmt.setString(2, "%" + name + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    float hotelRatingScore = Float.parseFloat(rs.getString("hotelRatingScore"));
                    String hotelImage = rs.getString("hotelImage");
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    Hotel hotel = new Hotel(hotelID, hotelName, hotelRatingScore, hotelRatingCount, hotelImage, area, roomAllQuantity);
                    list.add(hotel);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public List<Hotel> getAllHotelAvailableByArea(String areaID, String checkin, String checkout) throws Exception {
        List<Hotel> list = new ArrayList<>();
        AreaDAO areaDAO = new AreaDAO();
        Area area = areaDAO.getAreaByID(areaID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "select a.hotelID,a.hotelImage,a.hotelName,a.hotelRatingCount,a.hotelRatingScore,a.roomAllQuantity,a.roomAllQuantity - ISNULL(b.room,0) as activeRoom from\n"
                        + "(select hotelID,hotelImage,hotelName,hotelRatingCount,hotelRatingScore,roomAllQuantity from tbl_Hotels where tbl_Hotels.areaID=?) a\n"
                        + "full join\n"
                        + "(select tbl_Hotels.hotelID,count(tbl_Logs.roomID) as room\n"
                        + "from tbl_Hotels left join tbl_Logs on tbl_Hotels.hotelID=tbl_Logs.hotelID\n"
                        + "where tbl_Hotels.areaID=? and (checkin< ? AND checkout> ?)\n"
                        + "group by tbl_Hotels.hotelID) b\n"
                        + "on a.hotelID = b.hotelID";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, areaID);
                pstmt.setString(2, areaID);
                pstmt.setTimestamp(3, Variable.SQL_CHECK_OUT_DATE(checkout));
                pstmt.setTimestamp(4, Variable.SQL_CHECK_IN_DATE(checkin));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    float hotelRatingScore = rs.getFloat("hotelRatingScore");
                    String hotelImage = rs.getString("hotelImage");
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int activeRoom = rs.getInt("activeRoom");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    Hotel hotel = new Hotel(hotelID, hotelName, hotelRatingScore, hotelRatingCount, hotelImage, area, roomAllQuantity, activeRoom);
                    list.add(hotel);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }
    public List<Hotel> getAllHotelAvailableByAreaByName(String areaID,String name, String checkin, String checkout) throws Exception {
        List<Hotel> list = new ArrayList<>();
        AreaDAO areaDAO = new AreaDAO();
        Area area = areaDAO.getAreaByID(areaID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "select a.hotelID,a.hotelImage,a.hotelName,a.hotelRatingCount,a.hotelRatingScore,a.roomAllQuantity,a.roomAllQuantity - ISNULL(b.room,0) as activeRoom from\n"
                        + "(select hotelID,hotelImage,hotelName,hotelRatingCount,hotelRatingScore,roomAllQuantity from tbl_Hotels where tbl_Hotels.areaID=? and tbl_Hotels.hotelName like ?) a\n"
                        + "left join\n"
                        + "(select tbl_Hotels.hotelID,count(tbl_Logs.roomID) as room\n"
                        + "from tbl_Hotels left join tbl_Logs on tbl_Hotels.hotelID=tbl_Logs.hotelID\n"
                        + "where tbl_Hotels.areaID=? and (checkin< ? AND checkout> ?)\n"
                        + "group by tbl_Hotels.hotelID) b\n"
                        + "on a.hotelID = b.hotelID";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, areaID);
                pstmt.setString(2, "%"+name+"%");
                pstmt.setString(3, areaID);
                pstmt.setTimestamp(4, Variable.SQL_CHECK_OUT_DATE(checkout));
                pstmt.setTimestamp(5, Variable.SQL_CHECK_IN_DATE(checkin));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    float hotelRatingScore = rs.getFloat("hotelRatingScore");
                    String hotelImage = rs.getString("hotelImage");
                    int hotelRatingCount = rs.getInt("hotelRatingCount");
                    int activeRoom = rs.getInt("activeRoom");
                    int roomAllQuantity = rs.getInt("roomAllQuantity");
                    Hotel hotel = new Hotel(hotelID, hotelName, hotelRatingScore, hotelRatingCount, hotelImage, area, roomAllQuantity, activeRoom);
                    list.add(hotel);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }
    public boolean rateHotel(Hotel hotel) throws Exception{
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Update tbl_Hotels set hotelRatingScore=?,hotelRatingCount=? where hotelID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setFloat(1, hotel.getHotelRatingScore());
                pstmt.setInt(2, hotel.getHotelRatingCount());
                pstmt.setString(3, hotel.getHotelID());
                return pstmt.executeUpdate() >0;
            }
        } finally{
            closeConection();
        }
        return false;
    }
}
