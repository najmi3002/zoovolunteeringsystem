package servlet;

import dao.userDao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.userModel;
import util.DatabaseConnection;


@WebServlet(name = "userServlet", urlPatterns = {"/userServlet"})
public class userServlet extends HttpServlet {

    
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {

    String cmd = request.getParameter("command");

    if (cmd != null) {
        switch (cmd) {
            case "usersignup":
                usersignup(request, response);
                break;
            case "userlogin":
                userlogin(request, response);
                break;
                
            case "adminlogin":
                adminlogin(request, response);
                break;
              case "userlogout":
                userlogout(request, response);
                break;
            default:
                response.sendRedirect("FAIL.jsp");
                break;
        }
    } else {
        response.sendRedirect("FAIL.jsp");
    }
}
    private void userlogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); 
        if (session != null) {
            session.invalidate(); 
        }
        System.out.println("User logged out successfully.");
        resp.sendRedirect(req.getContextPath() + "/index.html"); 
    }
    
private void adminlogin(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    String sql = "SELECT * FROM admin WHERE admin_email = ? AND admin_password = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {

            resp.sendRedirect(req.getContextPath() + "/reservationServlet?command=viewall");
        } else {

            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("adminlogin.jsp").forward(req, resp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error retrieving user data.", e);
    }
}

    
    private void userlogin(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            userDao userdao = new userDao();
            userModel user = userdao.userlogin(email, password);

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if (user != null) {
                
                System.out.println("Login successful for user: " + email);
                resp.sendRedirect(req.getContextPath() + "/home.jsp");
            } else {
               
                System.out.println("Invalid email or password for: " + email);
                req.setAttribute("loginFailed", true);
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }

    private void usersignup(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
    
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");      
        String address = req.getParameter("address");
        String icNumber = req.getParameter("icNumber");
        String dob = req.getParameter("dob");
        String occupation = req.getParameter("occupation");
        String age = req.getParameter("age");
       
        userModel user = new userModel(firstName, lastName, email, phoneNumber, password, address, icNumber, dob, occupation, age);
        userDao userdao = new userDao();
        userdao.signup(user);
        
        req.getRequestDispatcher("login.jsp").forward(req, resp);
        

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
