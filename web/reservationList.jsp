<%@ page import="model.userModel" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="util.DatabaseConnection" %>
<%@ page import="java.sql.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            text-align: center
        }
        .sidebar a:hover {
            background-color: #01579b;
        }
        .content {
            flex: 1;
            display: flex;
            flex-direction: column;
            padding: 20px;
            box-sizing: border-box
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1)
        }
        .header h2 {
            margin: 0;
            color: #333;
        }
        .main-content {
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1)
            max-width 600px;
            margin: 20px auto;
            flex: 1.
        }
        .main-content h3 {
            margin-top: 0
        }
        .form-group {
            margin-bottom: 15px
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333
        }
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box
        }
        .form-group input[type="file"] {
            padding: 3px
        }
        .form-group input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px
        }
        .form-group input[type="submit"]:hover {
            background-color: #45a049
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
                    <input type="hidden" name="command" value="userlogout">
                    <input type="submit" value="Logout" class="logout-button">
                </form>
            </div>
            <div class="main-content">
                <h3>Volunteer Information</h3>

                <!-- Display user details -->
                <div>
                    <h2>User Information</h2>
                    <p>Name: ${userDetails.firstName} ${userDetails.lastName}</p>
                    <p>IC Number: ${userDetails.icNumber}</p>
                    <p>Phone Number: ${userDetails.phoneNumber}</p>
                    <p>Email: ${userDetails.email}</p>
                    <p>Date of Birth: ${userDetails.dob}</p>
                    <p>Address: ${userDetails.address}</p>
                    <p>Age: ${userDetails.age}</p>
                    <p>Occupation: ${userDetails.occupation}</p>
                </div>

                <table class="table">
                    <thead>
                        <tr>
                            <th>DATE</th>
                            <th>STATUS</th>
                            <th>ACTION</th>
                        </tr>
                    </thead>
                    <tbody id="itTableBody">
                        <c:forEach var="r" items="${reserve}">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap"><c:out value="${r.date}" /></td>
                                <td class="px-6 py-4 whitespace-nowrap"><c:out value="${r.status}" /></td>
                                <td>
                                    <button type="button" onclick="confirmEdit('${r.status}', '${r.rid}')">Edit</button>
                                    <button type="button" onclick="confirmDelete('${r.rid}')">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        function confirmDelete(rid) {
            const userConfirmed = confirm("Are you sure you want to delete this reservation?");
            if (userConfirmed) {
                window.location.href = 'reservationServlet?command=deleteReservation&rid=' + rid;
            }
        }

        function confirmEdit(status, rid) {
            if (status.toLowerCase() !== 'pending') {
                alert("You cannot edit this reservation");
            } else {
                window.location.href = 'reservationEdit.jsp?rid=' + rid;
            }
        }
    </script>
</body>
</html>
