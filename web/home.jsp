<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            display: flex;
            height: 100vh;
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
        .logout-button {
            background-color: #0277bd;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .logout-button:hover {
            background-color: #01579b;
        }
        .main-content {
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }
        .gallery {
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .gallery img {
            max-width: 300px;
            height: auto;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .gallery h3 {
            margin-top: 0;
            color: #333;
        }
        .gallery p {
            color: #555;
            max-width: 600px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>VOLUNTEER MANAGEMENT</h2>
        <a href="home.jsp">Dashboard</a>
        <a href="reservation.jsp">Reservation</a>
        <a href="reservationServlet?command=viewbyuserid">Activity & History</a>
    </div>
    <div class="content">
        <div class="header">
            <h2>Welcome to the Home Page!</h2>
            <form action="userServlet" method="post">
                <input type="hidden" name="command" value="userlogout">
                <input type="submit" value="Logout" class="logout-button">
            </form>
        </div>
        <div class="main-content">
            <div class="gallery">
                <h3>Gallery</h3>
                <img src="images/red-panda.jpg" alt="Red Panda">
                <h3>Red Panda</h3>
                <p>Red panda, (Ailurus fulgens), reddish brown, long-tailed, raccoonlike mammal, about the size of a large domestic cat, that is found in the mountain forests of the Himalayas and adjacent areas of eastern Asia and subsists mainly on bamboo and other vegetation, fruits, and insects. Once classified as a relative of the giant panda, it is now usually classified as the sole member of the family Ailuridae.</p>
            </div>
        </div>
    </div>
</body>
</html>
