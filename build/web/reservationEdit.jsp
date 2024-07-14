<%@page import="model.userModel"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@page import="util.DatabaseConnection"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
    
<head>
    <title>Reservation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            display: flex;
            min-height: 100vh;
            flex-direction: column;
        }
        .container {
            display: flex;
            flex: 1;
        }
        .sidebar {
            width: 250px;
            background-color: #0277bd;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 15px;
            box-sizing: border-box;
        }
        .sidebar h2 {
            color: #fff;
            margin-bottom: 30px;
        }
        .sidebar a {
            display: block;
            color: #fff;
            padding: 10px 15px;
            text-decoration: none;
            margin-bottom: 10px;
            border-radius: 5px;
            width: 100%;
            text-align: center;
        }
        .sidebar a:hover {
            background-color: #01579b;
        }
        .content {
            flex: 1;
            display: flex;
            flex-direction: column;
            padding: 20px;
            box-sizing: border-box;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }
        .header h2 {
            margin: 0;
            color: #333;
        }
        .main-content {
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 20px auto;
            flex: 1;
        }
        .main-content h3 {
            margin-top: 0;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
        }
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .form-group input[type="file"] {
            padding: 3px;
        }
        .form-group input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
        }
        .form-group input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>

    <style>
        h1, h2 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        a {
            text-decoration: none;
            color: #007bff;
            margin: 0 10px;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
    
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h2>VOLUNTEER MANAGEMENT</h2>
        <a href="home.jsp">Dashboard</a>
        <a href="reservation.jsp">Reservation</a>
        <a href="reservationServlet?command=viewbyuserid">Activity & History</a>
        
        </div>
        <div class="content">
            <div class="header">
                <h2>Reservation</h2>
            <form action="userServlet" method="post">
                <input type="hidden" name="command" value="userlogout" >
                <input type="submit" value="Logout" class="logout-button">
            </form>
            </div>
            <div class="main-content">
                <h3>Volunteer Information</h3>
                    <%
                     userModel user = (userModel) session.getAttribute("user");
                    if (user != null) {
                        String userID = user.getUserid();
                       String rid = request.getParameter("rid");
                    
                            Connection con = null;
                            PreparedStatement pstmt = null;
                            ResultSet rs = null;
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                con = DatabaseConnection.getConnection();
                                String query = "SELECT * FROM users u JOIN reservation r ON u.userID=r.userId WHERE u.userID = ? AND reservationID =?";
                                pstmt = con.prepareStatement(query);                  
                                pstmt.setString(1, userID); 
                                 pstmt.setString(2, rid); 
                                rs = pstmt.executeQuery();
                                
                               
                                if (rs.next()) {
                                 boolean fileExists = rs.getBlob("fileData") != null;
                                
                        
                                   
                %>
                <form action="reservationServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="updateReservation">
                     <input type="hidden" name="userID" value="<%=userID%>" required>
                     <input type="hidden" name="rid" value="<%=rid%>" required>
                    <div class="form-group">
                        <label for="date">Select Date:</label>
                        <input type="date" id="date" name="date" value="<%= rs.getString("reservationDate") %>" required>
                    </div>
                    <div class="form-group">
                        <label for="name">Name:</label>
                       
                        <input type="text" id="name" name="name" value="<%= rs.getString("firstName") %>" required>
                    </div>
                    <div class="form-group">
                        <label for="ic">IC Number:</label>
                        <input type="text" id="ic" name="ic" value="<%= rs.getString("icNumber") %>" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone Number:</label>
                        <input type="tel" id="phone" name="phone" value="<%= rs.getString("phoneNumber") %>" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="<%= rs.getString("email") %>"required>
                    </div>
                    <div class="form-group">
                        <label for="dob">Date of Birth:</label>
                        <input type="date" id="dob" name="dob" value="<%= rs.getString("dob") %>" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                     <textarea id="address" name="address" rows="3" required><%= rs.getString("address") %></textarea>

                    </div>
                    <div class="form-group">
                        <label for="age">Age:</label>
                        <input type="number" id="age" name="age" value="<%= rs.getString("age") %>"required>
                    </div>
                    <div class="form-group">
                        <label for="occupation">Occupation:</label>
                        <input type="text" id="occupation" name="occupation" value="<%= rs.getString("occupation") %>"required>
                    </div>
                    <div class="form-group">
                        <label for="file">Attach File:</label>
                         <input type="file" id="fileUpload" name="fileupload" accept=".pdf, .doc, .docx">
                        <% if (fileExists) { %>
                         <a href="<%= request.getContextPath() %>/reservationServlet?rid=<%= rid %>&command=downloadfile">Download Existing File</a>
                        <% } %>    
                    </div>
                    <div class="form-group">
                         <button type="submit" name="submit">Submit</button>
                    </div>
                </form>
                     
                <% }
        
                } catch (SQLException e){
                    e.printStackTrace();
                }catch(ClassNotFoundException e){
                     e.printStackTrace();
                } finally {
                    // Close resources
                    try {
                        if (rs != null) rs.close();
                        if (pstmt != null) pstmt.close();
                        if (con != null) con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                    } else {
                     response.sendRedirect("login.jsp");
                    }
                %>
            </div>
        </div>
    </div>
</body>
</html>