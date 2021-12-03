/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Area;
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
public class AreaDAO {

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

    public List<Area> getAllAreas() throws Exception {
        List<Area> list = new ArrayList<>();
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select areaID,areaName from tbl_Areas";
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String areaID = rs.getString("areaID");
                    String areaName = rs.getString("areaName");
                    Area area = new Area(areaID, areaName);
                    list.add(area);
                }
            }
        } finally {
            closeConection();
        }
        return list;
    }

    public Area getAreaByID(String areaID) throws Exception {
        Area area = null;
        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "Select areaName from tbl_Areas where areaID=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, areaID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String areaName = rs.getString("areaName");
                    area = new Area(areaID, areaName);
                }
            }
        } finally {
            closeConection();
        }
        return area;
    }
}
