/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Type;
import dtos.Hotel;
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
public class TypeDAO {

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

    public Type getTypeByID(String typeID) throws Exception {
        Type type = null;       
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT typeName from tbl_Types where typeID=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, typeID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String typeName = rs.getString("typeName");                    
                    type = new Type(typeID, typeName);
                }
            }
        } finally {
            closeConection();
        }
        return type;
    }

//    public List<Category> getAllCategoriesByHotel(String hotelID) throws Exception {
//        List<Category> list = new ArrayList<>();
//        HotelDAO hotelDAO = new HotelDAO();
//        Hotel Hotel = hotelDAO.getHotelByID(hotelID);
//        try {
//            con = db.getConnection();
//            if (con != null) {
//                String sql = "SELECT categoryID,categoryName from tbl_Categories where hotelID=?";
//                pstmt = con.prepareStatement(sql);
//                pstmt.setString(1, hotelID);
//                rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    String categoryID = rs.getString("catgoryID");
//                    String categoryName = rs.getString("categoryName");
//                    Category category = new Category(categoryID, categoryName, Hotel);
//                    list.add(category);
//                }
//            }
//        } finally {
//            closeConection();
//        }
//        return list;
//    }
    public List<Type> getAllTypes () throws Exception{
        List<Type> list = new ArrayList<>();
        try {
             con = db.getConnection();
            if (con != null) {
                String sql = "SELECT typeID,typeName from tbl_Types";
                pstmt = con.prepareStatement(sql);             
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String typeID = rs.getString("typeID");
                    String typeName = rs.getString("typeName");
                    Type type = new Type(typeID, typeName);
                    list.add(type);
                }
            }
        } finally{
            closeConection();
        }
        return list;
    }
}
