/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBContext;

/**
 *
 * @author SE140018
 */
public class UserDAO {

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

    public User checkLogin(String id, String password) throws Exception {
        User user = null;
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT userName,userAddress,userPhone,userCreateDate,userStatus,userRole from tbl_Users where userID = ? and userPassword = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String userName = rs.getNString("userName");
                    String userAddress = rs.getNString("userAddress");
                    String userPhone = rs.getNString("userPhone");
                    String userCreatDate = rs.getString("userCreateDate");
                    boolean userStatus = rs.getBoolean("userStatus");
                    int userRole = rs.getInt("userRole");
                    user = new User(id, password, userName, userAddress, userPhone, userCreatDate, userStatus, userRole);
                }
            }
        } finally {
            closeConection();
        }
        return user;
    }

    public boolean checkExistEmail(String email) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT userName from tbl_Users where userID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, email);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConection();
        }
        return false;
    }

    public boolean createUser(User user) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Insert into tbl_Users(userID,userPassword,userName,userAddress,userPhone,userCreateDate,userStatus,userRole) values (?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserID());
                pstmt.setString(2, user.getUserPassword());
                pstmt.setString(3, user.getUserName());
                pstmt.setString(4, user.getUserAddress());
                pstmt.setString(5, user.getUserPhone());
                pstmt.setString(6, user.getUserCreateDate());
                pstmt.setBoolean(7, user.isUserStatus());
                pstmt.setInt(8, user.getUserRole());
                return pstmt.executeUpdate() > 0;
            }
        } finally {
            closeConection();
        }
        return false;
    }

    public boolean updateUser(User user) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Update tbl_Users set userPassword = ?,userName = ?, userAddress =?, userPhone =?, userCreateDate =?, userStatus = ?, userRole=? where userID= ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserPassword());
                pstmt.setString(2, user.getUserName());
                pstmt.setString(3, user.getUserAddress());
                pstmt.setString(4, user.getUserPhone());
                pstmt.setString(5, user.getUserCreateDate());
                pstmt.setBoolean(6, user.isUserStatus());
                pstmt.setInt(7, user.getUserRole());
                pstmt.setString(8, user.getUserID());
                return pstmt.executeUpdate() > 0;
            }
        } finally {
            closeConection();
        }
        return false;
    }

    public User getUserByID(String userID) throws Exception {
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT userName,userAddress,userPhone,userCreateDate,userStatus,userRole from tbl_Users where userID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, userID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String userName = rs.getNString("userName");
                    String userAddress = rs.getNString("userAddress");
                    String userPhone = rs.getNString("userPhone");
                    String userCreatDate = rs.getString("userCreateDate");
                    boolean userStatus = rs.getBoolean("userStatus");
                    int userRole = rs.getInt("userRole");
                    return new User(userID, userName, userAddress, userPhone, userCreatDate, userStatus, userRole);
                }
            }
        } finally {
            closeConection();
        }
        return null;
    }

}
