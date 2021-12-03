/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoomLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import methods.Variable;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class RoomLogDAO {

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

    public boolean createLog(RoomLog roomLog) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Insert into tbl_Logs(hotelID,roomID,checkin,checkout) values (?,?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, roomLog.getHotel().getHotelID());
                pstmt.setString(2, roomLog.getRoomType().getRoomID());
                pstmt.setTimestamp(3, Variable.SQL_CHECK_IN_DATE(roomLog.getCheckin()));
                pstmt.setTimestamp(4, Variable.SQL_CHECK_OUT_DATE(roomLog.getCheckout()));
                return pstmt.executeUpdate()>0;
            }
        } finally{
            closeConection();
        }
        return false;
    }
    public boolean deleteLog(RoomLog roomLog) throws Exception{
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Delete from tbl_Logs where hotelID=? and roomID= ? and checkin = ? and checkout = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, roomLog.getHotel().getHotelID());
                pstmt.setString(2, roomLog.getRoomType().getRoomID());
                pstmt.setTimestamp(3, Variable.SQL_CHECK_IN_DATE(roomLog.getCheckin()));
                pstmt.setTimestamp(4, Variable.SQL_CHECK_OUT_DATE(roomLog.getCheckout()));
                return pstmt.executeUpdate()>0;
            }
        } finally{
            closeConection();
        }
        return false;
    }
}
