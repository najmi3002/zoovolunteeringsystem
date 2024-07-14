<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Activity & History</title>
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
        .reservation-details {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .reservation-details th, .reservation-details td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .reservation-details th {
            background-color: #f2f2f2;
        }
        .reservation-details td {
            background-color: #fff;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h2>VOLUNTEER MANAGEMENT</h2>
            <a href="dashboard.jsp">Dashboard</a>
            <a href="reservation.jsp">Reservation</a>
            <a href="#">Activity & History</a>
            <a href="#">Regulation & Rules</a>
        </div>
        <div class="content">
            <div class="header">
                <h2>Activity & History</h2>
                <form action="logout" method="post">
                    <input type="submit" value="Logout">
                </form>
            </div>
            <div class="main-content">
                <h3>Reservation Details</h3>
                <table class="reservation-details">
                    <tr>
                        <th>Date</th>
                        <td>2024-05-15</td>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <td>Alif Satar Bin Omar</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>alifsatar@gmail.com</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>Paid</td>
                    </tr>
                    <tr>
                        <th>IC Number</th>
                        <td>020803424543</td>
                    </tr>
                    <tr>
                        <th>Phone Number</th>
                        <td>0199735345</td>
                    </tr>
                    <tr>
                        <th>Date of Birth</th>
                        <td>23/3/2001</td>
                    </tr>
                    <tr>
                        <th>Address</th>
                        <td>Klang, Selangor</td>
                    </tr>
                    <tr>
                        <th>Age</th>
                        <td>23</td>
                    </tr>
                    <tr>
                        <th>Occupation</th>
                        <td>Clerk</td>
                    </tr>
                </table>
            </div>
             <div class="main-content">
                <h3> history Reservation Details</h3>
                <table class="reservation-details">
                    <tr>
                        <th>Date</th>
                        <td>2024-05-15</td>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <td>Alif Satar Bin Omar</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>alifsatar@gmail.com</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>Paid</td>
                    </tr>
                    <tr>
                        <th>IC Number</th>
                        <td>020803424543</td>
                    </tr>
                    <tr>
                        <th>Phone Number</th>
                        <td>0199735345</td>
                    </tr>
                    <tr>
                        <th>Date of Birth</th>
                        <td>23/3/2001</td>
                    </tr>
                    <tr>
                        <th>Address</th>
                        <td>Klang, Selangor</td>
                    </tr>
                    <tr>
                        <th>Age</th>
                        <td>23</td>
                    </tr>
                    <tr>
                        <th>Occupation</th>
                        <td>Clerk</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>