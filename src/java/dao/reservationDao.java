/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author thesp
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Blob;
import model.reservationModel;

import util.DatabaseConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class reservationDao {
    
    public void approve(String rid) {
        
        String sql = "UPDATE reservation SET  status = ? WHERE reservationID =?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "Approved");
            stmt.setString(2, rid);
       
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }     
    
    public void reject(String rid) {

        String sql = "UPDATE reservation SET  status = ? WHERE reservationID =?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "Rejected");
            stmt.setString(2, rid);
       
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
    
      public List<reservationModel> viewall() throws SQLException {
        List<reservationModel> rList = new ArrayList<>();

        reservationModel list;
        ResultSet rs = null;
         Connection conn = DatabaseConnection.getConnection();
        try {
            String sqlQuery = "select * from reservation ;";
            PreparedStatement stat = conn.prepareStatement(sqlQuery);
            rs = stat.executeQuery();
            while (rs.next()) {
                list = new reservationModel();
                list.setrid (rs.getString("reservationID"));
                list.setDate(rs.getString("reservationDate"));
                list.setFileData(rs.getBlob("fileData"));
                list.setUserId(rs.getString("userId"));
                list.setstatus(rs.getString("status"));

                rList.add(list);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            if (rs != null) {
                rs.close();
            }

            conn.close();
        }

        return rList;
    }  
      
      
        public List<reservationModel> viewbyrid(String rid) throws SQLException {
        List<reservationModel> rList = new ArrayList<>();

        reservationModel list;
        ResultSet rs = null;
         Connection conn = DatabaseConnection.getConnection();
        try {
            String sqlQuery = "select * from reservation where reservationID=?;";
            PreparedStatement stat = conn.prepareStatement(sqlQuery);
            stat.setString(1, rid);
            rs = stat.executeQuery();
            while (rs.next()) {
                list = new reservationModel();
                list.setrid (rs.getString("reservationID"));
                list.setDate(rs.getString("reservationDate"));
                list.setFileData(rs.getBlob("fileData"));
                list.setUserId(rs.getString("userId"));
                list.setstatus(rs.getString("status"));

                rList.add(list);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            if (rs != null) {
                rs.close();
            }

            conn.close();
        }

        return rList;
    }
        
        public void deleteReservation(String rid) {
        String sql = "DELETE FROM reservation WHERE reservationID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        
        public void updatereservationwofile(reservationModel reserve) {

        String sql = "UPDATE reservation SET  reservationDate = ? WHERE reservationID =?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reserve.getDate());
            stmt.setString(2, reserve.getrid());
       
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }          
        public void updatereservation(reservationModel reserve) {

        String sql = "UPDATE reservation SET  reservationDate = ?, fileData = ? WHERE reservationID =?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reserve.getDate());
            stmt.setBlob(2, reserve.getFileData());
            stmt.setString(3, reserve.getrid());
       
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<reservationModel> viewbyuserid(String userid) throws SQLException {
        List<reservationModel> rList = new ArrayList<>();

        reservationModel list;
        ResultSet rs = null;
         Connection conn = DatabaseConnection.getConnection();
        try {
            String sqlQuery = "select * from reservation where userID=?;";
            PreparedStatement stat = conn.prepareStatement(sqlQuery);
            stat.setString(1, userid);
            rs = stat.executeQuery();
            while (rs.next()) {
                list = new reservationModel();
                list.setrid (rs.getString("reservationID"));
                list.setDate(rs.getString("reservationDate"));
                list.setFileData(rs.getBlob("fileData"));
                list.setUserId(rs.getString("userId"));
                list.setstatus(rs.getString("status"));

                rList.add(list);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            if (rs != null) {
                rs.close();
            }

            conn.close();
        }

        return rList;
    }
    
    public void addreservation(reservationModel reserve) {
        String sql = "INSERT INTO reservation (reservationDate, fileData, userId) VALUES ( ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reserve.getDate());
            stmt.setBlob(2, reserve.getFileData());
            stmt.setString(3, reserve.getUserId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public reservationModel getReservationByID(String rid) {
        String sql = "SELECT * FROM reservation WHERE reservationID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new reservationModel(
                    rs.getString("rid"),
                    rs.getString("reservationDate"),
                    rs.getBlob("fileData"),
                    rs.getString("phoneNumber")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(reservationModel reserve) {
        String sql = "UPDATE reservation SET reservationDate = ? WHERE reservationID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reserve.getDate());
            stmt.setString(2, reserve.getrid());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String rid) {
        String sql = "DELETE FROM reservation WHERE reservationID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
