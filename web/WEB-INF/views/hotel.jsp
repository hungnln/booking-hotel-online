<%-- 
    Document   : room
    Created on : Oct 2, 2021, 12:02:18 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | View Hotel</title>
    </head>
    <body>
        <h1>HOTEL</h1>
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div>
                    Welcome ${sessionScope.userdata.userName}
                    <a href="Index">Index</a>
                    <a href="Cart">View Cart</a>
                    <a href="UserUpdate">User</a>
                    <a href="Logout">Log out</a>
                </div>
            </c:when>
            <c:otherwise>
                Welcome Guest
                <a href="Index">Index</a>
                <a href="Cart">View Cart</a>
                <a href="Login">Login</a>
            </c:otherwise>
        </c:choose>
        <form action="SearchHotel" method="GET" onsubmit="return check();">
            <select name="areaID" id="cb-box">
                <option value="">Choose your area</option>
                <c:forEach var="area" items="${requestScope.areas}">
                    <c:choose>
                        <c:when test="${param.areaID eq area.areaID}">
                            <option value="${area.areaID}" selected="selected">${area.areaName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${area.areaID}">${area.areaName}</option>
                        </c:otherwise>
                    </c:choose>                   
                </c:forEach>
            </select>
            <div> <input type="text" name="hotelName" value="${param.hotelName}" placeholder="Enter Hotel Name">
            </div>
            Enter Check-In Date:<br>
            <input type="date" id="checkin" name="checkin" onchange="checkCheckIn()" value="${param.checkin}"><br>
            Enter Check-Out Date:<br>
            <input type="date" id="checkout" name="checkout" onchange="checkCheckOut()" value ="${param.checkout}"><br>
            <input type="submit" value="OK" onclick="return check();">
            <c:if test="${not empty requestScope.hotels}">
                <c:set var="hotels" value="${requestScope.hotels}"></c:set> 
                <c:forEach var="hotel" items="${hotels}">               
                    <div>
                        <img src="images/${hotel.hotelImage}" alt="${hotel.hotelName}" width="240" height="120">
                        <div>
                            ${hotel.hotelName}
                            Rating: ${hotel.hotelRating}
                        </div>
                        <div>
                            <a href="LoadHotel?hotelID=${hotel.hotelID}&checkin=${param.checkin}&checkout=${param.checkout}">Choose</a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </form>
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
