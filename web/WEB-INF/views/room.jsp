<%-- 
    Document   : room
    Created on : Oct 2, 2021, 9:33:56 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | View Room</title>
    </head>
    <body>
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
        <h1>Hotel : ${requestScope.hotel.hotelName}</h1>
        <a href="SearchHotel?areaID=${requestScope.area.areaID}&checkin=${param.checkin}&checkout=${param.checkout}" onclick="return confirm('Change another?')">Want to change?</a>
        <form action="LoadHotel">           
            <select name="typeID">
                <option value="">Choose room type</option>
                <c:forEach var="type" items="${requestScope.types}">                   
                    <c:choose>
                        <c:when test="${param.typeID eq type.typeID}">
                            <option value="${type.typeID}" selected="selected">${type.typeName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${type.typeID}">${type.typeName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <c:choose>
                <c:when test="${not empty sessionScope.cart and empty param.checkin }">                   
                    <input type="hidden" name="hotelID" value="${sessionScope.hotel.hotelID}">
                    Enter Check-In Date:
                    <input type="date" id="checkin" name="checkin" onchange="checkCheckIn()" value="${sessionScope.checkin}">
                    Enter Check-Out Date:
                    <input type="date" id="checkout" name="checkout" onchange="checkCheckOut()" value ="${sessionScope.checkout}">
                </c:when>
                <c:otherwise>                   
                    <input type="hidden" name="hotelID" value="${param.hotelID}">
                    Enter Check-In Date:
                    <input type="date" id="checkin" name="checkin" onchange="checkCheckIn()" value="${param.checkin}">
                    Enter Check-Out Date:
                    <input type="date" id="checkout" name="checkout" onchange="checkCheckOut()" value ="${param.checkout}">
                </c:otherwise>
            </c:choose>
            <input type="submit" value="OK" onclick="return check();">
        </form>
        <form action="Cart" method="POST">            
            <c:choose>

                <c:when test="${not empty requestScope.rooms}">
                    <c:set var="i" value="0"></c:set>
                    <c:choose>
                        <c:when test="${not empty sessionScope.cart and empty param.checkin}">
                            <input type="hidden" name="hotelID" value="${sessionScope.hotel.hotelID}">
                            <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                            <input type="hidden" name="checkout" value="${sessionScope.checkout}">
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="hotelID" value="${param.hotelID}">
                            <input type="hidden" name="checkin" value="${param.checkin}">
                            <input type="hidden" name="checkout" value="${param.checkout}">
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="room" items="${requestScope.rooms}">
                        <input type="hidden" name="roomID_${i}" value="${room.roomID}">                       

                        <div>
                            <img src="images/${room.roomImage}" alt="${room.roomImage}" width="240" height="120">                           
                            <div>${room.roomDescription}</div>
                            <div>Price per days : ${room.roomPrice}</div>
                            <c:if test="${not empty sessionScope.checkin or not empty param.checkin}">
                                <div style="color: red;">Only ${room.roomActiveQuantity} left</div>
                                <input type="number" value="0" min="0" max="${room.roomActiveQuantity}" step="1" name="quantity_${i}">
                                <c:set var="i" value="${i+1}"></c:set>
                            </c:if>
                        </c:forEach>
                        <input type="hidden" name="index" value="${i}">
                        <c:if test="${(not empty param.checkin and not empty param.checkout) or (not empty sessionScope.checkin or not empty sessionScope.checkout)}">
                            <div>
                                <input type="submit" value="Choose" onclick="return check();">
                            </div>
                        </c:if>  
                    </c:when>  
                    <c:otherwise>
                        Can't find any room!
                    </c:otherwise>
                </c:choose>  
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
            function getCheckIn() {
                return document.getElementById('checkin').value;
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
