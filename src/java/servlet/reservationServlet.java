package servlet;

import dao.reservationDao;
import dao.userDao;
import model.userModel;
import model.reservationModel;
import util.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "reservationServlet", urlPatterns = {"/reservationServlet"})
@MultipartConfig
public class reservationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("rid");
        System.out.println("thename : " + name);
        String cmd = request.getParameter("command");
        System.out.println("thecommand : " + cmd);
        if (cmd != null) {
            switch (cmd) {
                case "addReservation":
                    addReservation(request, response);
                    break;
                case "updateReservation":
                    updateReservation(request, response);
                    break;
                case "viewbyuserid":
                    viewbyuserid(request, response);
                    break;
                case "deleteReservation":
                    deleteReservation(request, response);
                    break;
                case "downloadfile":
                    downloadFile(request, response);
                    break;
                case "viewall":
                    viewAll(request, response);
                    break;
                case "reject":
                    reject(request, response);
                    break;
                case "approve":
                    approve(request, response);
                    break;
                default:
                    response.sendRedirect("FAIL.jsp");
                    break;
            }
        } else {
            response.sendRedirect("FAIL.jsp");
        }
    }

    private void approve(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String rid = req.getParameter("rid");
        reservationDao reserveDAO = new reservationDao();
        reserveDAO.approve(rid);

        resp.sendRedirect(req.getContextPath() + "/reservationServlet?command=viewall");
    }

    private void reject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String rid = req.getParameter("rid");
        reservationDao reserveDAO = new reservationDao();
        reserveDAO.reject(rid);

        resp.sendRedirect(req.getContextPath() + "/reservationServlet?command=viewall");
    }

    private void viewAll(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        reservationDao rdao = new reservationDao();
        List<reservationModel> reservations = rdao.viewall();

        if (reservations != null && !reservations.isEmpty()) {
            req.setAttribute("reserve", reservations);
            Map<String, userModel> userMap = new HashMap<>();
            userDao udao = new userDao();

            for (reservationModel reservation : reservations) {
                String userID = reservation.getUserId();
                if (!userMap.containsKey(userID)) {
                    userModel user = udao.getUserByID(userID);
                    if (user != null) {
                        userMap.put(userID, user);
                    }
                }
            }
            req.setAttribute("users", userMap);

            RequestDispatcher dispatcher = req.getRequestDispatcher("admin_approval.jsp");
            dispatcher.forward(req, resp);
        } else {
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileId = request.getParameter("rid");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT fileData FROM reservation WHERE reservationID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                InputStream inputStream = rs.getBlob("fileData").getBinaryStream();
                int fileLength = inputStream.available();

                response.setContentType("application/octet-stream");
                response.setContentLength(fileLength);
                response.setHeader("Content-Disposition", "attachment; filename=\"downloaded_file.pdf\"");

                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.close();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteReservation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String rid = req.getParameter("rid");
        reservationDao reserveDAO = new reservationDao();
        reserveDAO.deleteReservation(rid);
        RequestDispatcher dispatcher = req.getRequestDispatcher("reservationServlet?command=viewbyuserid");
        dispatcher.forward(req, resp);
    }

    private void viewbyuserid(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            userModel user = (userModel) session.getAttribute("user");
            String userID = user.getUserid();

            reservationDao rdao = new reservationDao();
            List<reservationModel> r = rdao.viewbyuserid(userID);

            if (r != null && !r.isEmpty()) {
                req.setAttribute("reserve", r);

                // Fetch user details
                userDao udao = new userDao();
                userModel userDetails = udao.getUserByID(userID);
                if (userDetails != null) {
                    req.setAttribute("userDetails", userDetails);
                }

                System.out.println(r.get(0).getDate());
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("reservationList.jsp");
            dispatcher.forward(req, resp);
        } else {
            System.out.println("Session expired or invalid.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp"); // Redirect to login if session is null
        }
    }

    private void updateReservation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String date = req.getParameter("date");
        Part filePart = req.getPart("fileupload");
        String userID = req.getParameter("userID");

        String rid = req.getParameter("rid");
        String name = req.getParameter("name");
        String ic = req.getParameter("ic");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");
        String address = req.getParameter("address");
        String age = req.getParameter("age");
        String occupation = req.getParameter("occupation");

        try (InputStream fileContent = filePart.getInputStream()) {
            reservationModel reserve = new reservationModel();

            reserve.setrid(rid);
            reserve.setDate(date);
            reserve.setUserId(userID);
            Blob fileData = convertInputStreamToBlob(fileContent);
            reserve.setFileData(fileData);
            reservationDao reserveDAO = new reservationDao();

            if (filePart != null && filePart.getSize() > 0) {
                reserveDAO.updatereservation(reserve);
            } else {
                reserveDAO.updatereservationwofile(reserve);
            }

            // Update user info
            userModel user = new userModel();
            user.setFirstName(name);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setAddress(address);
            user.setIcNumber(ic);
            user.setDob(dob);
            user.setOccupation(occupation);
            user.setAge(age);
            user.setUserid(userID);
            userDao userdao = new userDao();
            userdao.updateUser(user);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/reservation.jsp");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/reservationServlet?command=viewbyuserid");
    }

    private void addReservation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String date = req.getParameter("date");
        Part filePart = req.getPart("fileupload");
        String userID = req.getParameter("userID");

        String name = req.getParameter("name");
        String ic = req.getParameter("ic");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");
        String address = req.getParameter("address");
        String age = req.getParameter("age");
        String occupation = req.getParameter("occupation");

        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream()) {
                reservationModel reserve = new reservationModel();

                reserve.setDate(date);
                reserve.setUserId(userID);
                Blob fileData = convertInputStreamToBlob(fileContent);
                reserve.setFileData(fileData);
                reservationDao reserveDAO = new reservationDao();
                reserveDAO.addreservation(reserve);

                // Update user info
                userModel user = new userModel();
                user.setFirstName(name);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setAddress(address);
                user.setIcNumber(ic);
                user.setDob(dob);
                user.setOccupation(occupation);
                user.setAge(age);
                user.setUserid(userID);
                userDao userdao = new userDao();
                userdao.updateUser(user);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/reservation.jsp");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/reservation.jsp");
        }
        resp.sendRedirect(req.getContextPath() + "/reservationServlet?command=viewbyuserid");
    }

    private Blob convertInputStreamToBlob(InputStream inputStream) throws SQLException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();
            return new javax.sql.rowset.serial.SerialBlob(bytes);
        } catch (IOException e) {
            throw new SQLException("Error converting InputStream to Blob: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(reservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(reservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
