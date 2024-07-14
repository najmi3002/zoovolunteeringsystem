<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Approval</title>
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
            max-width: 800px;
            margin: 20px auto;
            flex: 1;
        }
        .main-content h3 {
            margin-top: 0;
        }
        .approval-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .approval-table th, .approval-table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .approval-table th {
            background-color: #f2f2f2;
        }
        .approval-table td {
            background-color: #fff;
        }
        .action-buttons {
            display: flex;
        }
        .action-buttons form {
            margin-right: 10px;
        }
        .action-buttons input[type="submit"] {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            color: #fff;
        }
        .action-buttons input[type="submit"].approve {
            background-color: #4CAF50;
        }
        .action-buttons input[type="submit"].approve:hover {
            background-color: #45a049;
        }
        .action-buttons input[type="submit"].reject {
            background-color: #f44336;
        }
        .action-buttons input[type="submit"].reject:hover {
            background-color: #e53935;
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
          
            <a href="admin_approval.jsp">Admin Approval</a>
           
        </div>
        <div class="content">
            <div class="header">
                <h2>Admin Approval</h2>
            <form action="userServlet" method="post">
                <input type="hidden" name="command" value="userlogout" >
                <input type="submit" value="Logout" class="logout-button">
            </form>
            </div>
            <div class="main-content">
                <h3>Pending Reservations</h3>
                <table class="approval-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Name</th>
                            <th>IC Number</th>
                            <th>Phone</th>
                            <th>Email</th>
                            <th>Status</th>
                            <th>Receipt</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${reserve}">
                            <tr>
                                        <td ><c:out value="${r.date}" /></td>
                                        <td ><c:out value="${users[r.userId].firstName}" /></td>
                                        <td ><c:out value="${users[r.userId].icNumber}" /></td>
                                        <td ><c:out value="${users[r.userId].phoneNumber}" /></td>
                                        <td ><c:out value="${users[r.userId].email}" /></td>
                                         <td ><c:out value="${r.status}" /></td>                     
                                         <td>
                                         <a href="<%= request.getContextPath() %>/reservationServlet?rid=${r.rid}&command=downloadfile">Download</a>
                                         </td>
                                         
                                         <td class="action-buttons">
                                    <form action="reservationServlet" method="post">
                                      <input type="hidden" name="rid" value="${r.rid}" >
                                        <input type="hidden" name="command" value="approve">
                                        <input type="submit" value="Approve" class="approve">
                                    </form>
                                        
                                    <form action="reservationServlet" method="post">
                                        <input type="hidden" name="rid" value="${r.rid}" >
                                        <input type="hidden" name="command" value="reject">
                                        <input type="submit" value="Reject" class="reject">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>