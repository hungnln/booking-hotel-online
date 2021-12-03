/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Type;
import dtos.Hotel;
import dtos.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import methods.Variable;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class RoomTypeDAO {

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

//    public List<RoomType> getAllRoomByHotel(String hotelID) throws Exception {
//        List<RoomType> list = new ArrayList<>();
//        HotelDAO hotelDAO = new HotelDAO();
//        Hotel hotel = hotelDAO.getHotelByID(hotelID);
//        try {
//            con = db.getConnection();
//            if (con != null) {
//                String sql = "Select roomID,typeID,roomQuantity,roomPrice,roomStatus,roomImage,roomDescription from tbl_Rooms where hotelID=?";
//                pstmt = con.prepareStatement(sql);
//                pstmt.setString(1, hotelID);
//                rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    String roomID = rs.getString("roomID");
//                    String categoryID = rs.getString("categoryID");
//                    TypeDAO typeDAO = new TypeDAO();
//                    Type type = typeDAO.getTypeByID(categoryID);
//                    int roomQuantity = Integer.parseInt(rs.getString("roomQuantity"));
//                    float roomPrice = Float.parseFloat(rs.getString("roomPrice"));
//                    boolean roomStatus = rs.getBoolean("roomStatus");
//                    String roomImage = rs.getString("roomImage");
//                    String roomDescription = rs.getString("Description");
//                    RoomType roomType = new RoomType(roomID, hotel, category, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription);
//                    list.add(roomType);
//                }
//            }
//        } finally {
//            closeConection();
//        }
//        return list;
//    }
    public RoomType getRoomTypeByRoomID(String roomID) throws Exception {
        TypeDAO typeDAO = new TypeDAO();
        HotelDAO hotelDAO = new HotelDAO();       
        con = db.getConnection();
        if (con != null) {
            String sql = "Select roomQuantity,roomPrice,roomStatus,roomImage,roomDescription,hotelID,typeID from tbl_RoomType";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String typeID = rs.getString("typeID");
                Type type = typeDAO.getTypeByID(typeID);
                String hotelID = rs.getString("hotelID");
                Hotel hotel = hotelDAO.getHotelByID(hotelID);
                int roomQuantity = rs.getInt("roomQuantity");
                long roomPrice = rs.getLong("roomPrice");
                boolean roomStatus = rs.getBoolean("roomStatus");
                String roomImage = rs.getString("roomImage");
                String roomDescription = rs.getString("roomDescription");
                return new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription);
            }
        }
        return null;
    }

    public List<RoomType> getAllRoomByHotel(String hotelID) throws Exception {
        List<RoomType> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = hotelDAO.getHotelByID(hotelID);
        TypeDAO typeDAO = new TypeDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select roomID,typeID,roomPrice,roomStatus,roomImage,hotelID,roomQuantity,roomDescription from tbl_RoomType where hotelID=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, hotelID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    String typeID = rs.getString("typeID");
                    Type type = typeDAO.getTypeByID(typeID);
                    long roomPrice = rs.getLong("roomPrice");
                    boolean roomStatus = rs.getBoolean("roomStatus");
                    String roomImage = rs.getString("roomImage");
                    int roomQuantity = rs.getInt("roomQuantity");
                    String roomDescription = rs.getString("roomDescription");
                    RoomType roomType = new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription);
                    list.add(roomType);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public List<RoomType> getAllRoomByHotelByType(String hotelID, String typeID) throws Exception {
        List<RoomType> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = hotelDAO.getHotelByID(hotelID);
        TypeDAO typeDAO = new TypeDAO();
        Type type = typeDAO.getTypeByID(typeID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select roomID,roomPrice,roomStatus,roomImage,hotelID,roomQuantity,roomDescription from tbl_RoomType where hotelID = ? and typeID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, hotelID);
                pstmt.setString(2, typeID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    long roomPrice = rs.getLong("roomPrice");
                    boolean roomStatus = rs.getBoolean("roomStatus");
                    String roomImage = rs.getString("roomImage");
                    int roomQuantity = rs.getInt("roomQuantity");
                    String roomDescription = rs.getString("roomDescription");
                    RoomType roomType = new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription);
                    list.add(roomType);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public List<RoomType> getAllRoomByTypeByHotelWithCheckInAndCheckOut(String typeID, String hotelID, String checkin, String checkout) throws Exception {
        List<RoomType> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = hotelDAO.getHotelByID(hotelID);
        TypeDAO typeDAO = new TypeDAO();
        Type type = typeDAO.getTypeByID(typeID);
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "select a.roomID,a.roomImage,a.roomDescription,a.roomPrice,a.roomQuantity,a.roomStatus,a.roomQuantity - ISNULL(b.room,0) as activeRoom from\n"
                        + "(select roomID,roomImage,roomDescription,roomPrice,roomQuantity,roomStatus from tbl_RoomType\n"
                        + "where tbl_RoomType.hotelID= ? and tbl_RoomType.typeID= ? ) a\n"
                        + "left join \n"
                        + "(Select count(tbl_Logs.roomID) as room,tbl_Logs.roomID from tbl_Logs \n"
                        + "where (checkin < ? AND checkout > ? )\n"
                        + "group by tbl_Logs.roomID) b\n"
                        + "on a.roomID=b.roomID";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, hotelID);
                pstmt.setString(2, typeID);
                pstmt.setTimestamp(3, Variable.SQL_CHECK_OUT_DATE(checkout));
                pstmt.setTimestamp(4, Variable.SQL_CHECK_IN_DATE(checkin));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    long roomPrice = rs.getLong("roomPrice");
                    boolean roomStatus = rs.getBoolean("roomStatus");
                    String roomImage = rs.getString("roomImage");
                    int roomQuantity = rs.getInt("roomQuantity");
                    String roomDescription = rs.getString("roomDescription");
                    int roomActiveQuantity = rs.getInt("activeRoom");
                    RoomType room = new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription, roomActiveQuantity);
                    list.add(room);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public List<RoomType> getAllRoomByHotelWithCheckInAndCheckOut(String hotelID, String checkin, String checkout) throws Exception {
        List<RoomType> list = new ArrayList<>();
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = hotelDAO.getHotelByID(hotelID);
        TypeDAO typeDAO = new TypeDAO();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "select a.roomID,a.typeID,a.roomImage,a.roomDescription,a.roomPrice,a.roomQuantity,a.roomStatus,a.roomQuantity - ISNULL(b.room,0) as activeRoom from\n"
                        + "(select roomID,typeID,roomImage,roomDescription,roomPrice,roomQuantity,roomStatus from tbl_RoomType\n"
                        + "where tbl_RoomType.hotelID=?) a\n"
                        + "left join \n"
                        + "(Select count(tbl_Logs.roomID) as room,tbl_Logs.roomID from tbl_Logs \n"
                        + "where (checkin< ? AND checkout> ?)\n"
                        + "group by tbl_Logs.roomID) b\n"
                        + "on a.roomID=b.roomID";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, hotelID);
                pstmt.setTimestamp(2, Variable.SQL_CHECK_OUT_DATE(checkout));
                pstmt.setTimestamp(3, Variable.SQL_CHECK_IN_DATE(checkin));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    long roomPrice =rs.getLong("roomPrice");
                    boolean roomStatus = rs.getBoolean("roomStatus");
                    String roomImage = rs.getString("roomImage");
                    int roomQuantity = rs.getInt("roomQuantity");
                    String roomDescription = rs.getString("roomDescription");
                    int roomActiveQuantity = rs.getInt("activeRoom");
                    String typeID = rs.getString("typeID");
                    Type type = typeDAO.getTypeByID(typeID);
                    RoomType room = new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription, roomActiveQuantity);
                    list.add(room);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public RoomType getRoomByIDWithCheckInAdnCheckOut(String roomID, String checkin, String checkout) throws Exception {
        HotelDAO hotelDAO = new HotelDAO();
        RoomType roomType = null;
        TypeDAO typeDAO = new TypeDAO();
//        checkin += " 18:00:00";
//        checkout += " 12:00:00";
//        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
//        Date checkinDate = dateFormat.parse(checkin);
//        java.sql.Date sqlCheckinDate = new java.sql.Date(checkinDate.getTime());
//        Date checkoutDate = dateFormat.parse(checkout);
//        java.sql.Date sqlCheckoutDate = new java.sql.Date(checkoutDate.getTime());
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "select a.hotelID,a.roomID,a.typeID,a.roomImage,a.roomDescription,a.roomPrice,a.roomQuantity,a.roomStatus,a.roomQuantity - ISNULL(b.room,0) as activeRoom from\n"
                        + "(select roomID,hotelID,typeID,roomImage,roomDescription,roomPrice,roomQuantity,roomStatus from tbl_RoomType\n"
                        + "where tbl_RoomType.roomID=?) a\n"
                        + "left join \n"
                        + "(Select count(tbl_Logs.roomID) as room,tbl_Logs.roomID from tbl_Logs \n"
                        + "where (checkin< ? AND checkout> ?)\n"
                        + "group by tbl_Logs.roomID) b\n"
                        + "on a.roomID=b.roomID";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, roomID);
                pstmt.setTimestamp(2, Variable.SQL_CHECK_OUT_DATE(checkout));
                pstmt.setTimestamp(3, Variable.SQL_CHECK_IN_DATE(checkin));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    long roomPrice = rs.getLong("roomPrice");
                    boolean roomStatus = rs.getBoolean("roomStatus");
                    String roomImage = rs.getString("roomImage");
                    int roomQuantity = rs.getInt("roomQuantity");
                    String roomDescription = rs.getString("roomDescription");
                    int roomActiveQuantity = rs.getInt("activeRoom");
                    String typeID = rs.getString("typeID");
                    Type type = typeDAO.getTypeByID(typeID);
                    String hotelID = rs.getString("hotelID");
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    roomType = new RoomType(roomID, hotel, type, roomPrice, roomStatus, roomImage, roomQuantity, roomDescription, roomActiveQuantity);
                }
            }
        } finally {
            closeConection();
        }
        return roomType;
    }
}
