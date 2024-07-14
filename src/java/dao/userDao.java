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
import java.sql.SQLException;
import model.userModel;
import util.DatabaseConnection;

public class userDao {
    
    public userModel userlogin(String email, String password) {
        userModel user = null;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new userModel(
                    rs.getString("userID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("icNumber"),
                    rs.getString("dob"),
                    rs.getString("occupation"),
                    rs.getString("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving user data.", e);
        }
        return user;
    }


    
    
public void signup(userModel user) {
    if (user == null) {
        System.err.println("User object is null.");
        return;
    }

    String sql = "INSERT INTO users (firstName, lastName, email, phoneNumber, password, address, icNumber, dob, occupation, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
 
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPhoneNumber());
        stmt.setString(5, user.getPassword());
        stmt.setString(6, user.getAddress());
        stmt.setString(7, user.getIcNumber());
        stmt.setString(8, user.getDob());  
        stmt.setString(9, user.getOccupation());
        stmt.setString(10, user.getAge()); 

        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("SQL Exception: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Exception: " + e.getMessage());
        e.printStackTrace();
    }
}


    public userModel getUserByID(String UID) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new userModel(
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("icNumber"),
                    rs.getString("dob"),
                    rs.getString("occupation"),
                    rs.getString("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(userModel user) {
        String sql = "UPDATE users SET firstName = ?,email = ?, phoneNumber = ?, address = ?, icNumber = ?, dob = ?, occupation = ?, age = ? WHERE userID =?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getIcNumber());
            stmt.setString(6, user.getDob());
            stmt.setString(7, user.getOccupation());
            stmt.setString(8, user.getAge());
            stmt.setString(9, user.getUserid());
       
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
