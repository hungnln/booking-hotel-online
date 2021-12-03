<%-- 
    Document   : index
    Created on : Oct 1, 2021, 9:42:59 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | Welcome Page</title>
    </head>
    <body>
        <h1>Booking Hotel</h1>
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div>
                    Welcome ${sessionScope.userdata.userName}
                    <a href="Cart">View Cart</a>
                    <a href="UserUpdate">User</a>
                    <a href="UserBooking">Your Booking</a>
                    <a href="Logout">Log out</a>
                </div>
            </c:when>
            <c:otherwise>
                Welcome Guest
                <a href="Cart">View Cart</a>
                <a href="Login">Login</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty requestScope.msg}">
            <div>${requestScope.msg}</div>
        </c:if>
        <form action="SearchHotel" method="GET" onsubmit="return check();">
            <select name="areaID" id="cb-box">
                <option value="">Choose your area</option>
                <c:forEach var="area" items="${requestScope.areas}">
                    <option value="${area.areaID}">${area.areaName}</option>
                </c:forEach>
            </select>

            Enter Check-In Date:
            <input type="date" id="checkin" name="checkin" onchange="checkCheckIn()">
            Enter Check-Out Date:
            <input type="date" id="checkout" name="checkout" onchange="checkCheckOut()">
            <input type="submit" value="OK">
        </form>
        <c:if test="${not empty requestScope.hotels}">
            <c:set var="hotels" value="${requestScope.hotels}"></c:set> 
            <c:forEach var="hotel" items="${hotels}">               
                <div style="float: left; width: 300px;">
                    <img src="images/${hotel.hotelImage}" alt="${hotel.hotelName}" width="240" height="120">
                    <div>
                        ${hotel.hotelName}
                        Rating: ${hotel.hotelRating}
                    </div>
                    <div>
                        <a href="LoadHotel?hotelID=${hotel.hotelID}&checkin=${sessionScope.checkin}&checkout=${sessionScope.checkout}">Choose</a>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <script>
            function checkCheckOut() {
                if (document.getElementById('checkin').value >= document.getElementById('checkout').value)
                {
                    window.alert('Not allow');
                    document.getElementById('checkout').value = "";
                    request.abort();
                }
            }
            function checkCheckIn() {
                if (document.getElementById('checkout').value !== "") {
                    if (document.getElementById('checkin').value >= document.getElementById('checkout').value) {
                        window.alert('Not allow');
                        document.getElementById('checkin').value = "";
                        request.abort();
                    }
                }
            }
            function check() {
                if ((document.getElementById('checkin').value === "" && document.getElementById('checkout').value !== "") || (document.getElementById('checkin').value !== "" && document.getElementById('checkout').value === "")) {
                    window.alert('Please enter full checkin and checkout or no choose');
                    return false;
                }
            }
        </script>

    </body>

</html>
