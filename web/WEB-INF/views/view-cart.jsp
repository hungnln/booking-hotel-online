<%-- 
    Document   : view-booking
    Created on : Oct 5, 2021, 1:24:08 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | Cart</title>
    </head>
    <body>
        <h1>Booking Cart</h1>
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div>
                    Welcome ${sessionScope.userdata.userName}
                    <a href="Index">Index</a>
                    <a href="UserUpdate">User</a>
                    <a href="Logout">Log out</a>
                </div>
            </c:when>
            <c:otherwise>
                Welcome Guest
                <a href="Index">Index</a>
                <a href="Login">Login</a>
            </c:otherwise>
        </c:choose>
        <c:if  test="${not empty sessionScope.cart}">
            Confirm your booking:
            ${sessionScope.hotel.hotelName}
            <div id="date-label">
                Check in: ${sessionScope.checkin}
                Check out: ${sessionScope.checkout}
            </div>
            <div id="btn-changeDate"><button onclick="showDate()">Change Date</button></div>
            <div id="date" style="display: none;">
                <form action="ChangeDateBooking" method="POST">
                    <input type="hidden" name="hotelID" value="${param.hotelID}">                 
                    Enter Check-In Date:
                    <input type="date" id="checkin" name="checkin" onchange="checkCheckIn()" value="${sessionScope.checkin}">
                    Enter Check-Out Date:
                    <input type="date" id="checkout" name="checkout" onchange="checkCheckOut()" value ="${sessionScope.checkout}">
                    <input type="submit" value="Change Date" onclick="return checkChange()">
                </form>
            </div>
            Your choose: <a href="LoadHotel?change=change" onclick=" return confirm('Change room will remove your old booking...')">Want to change room ?</a>
            <c:if test="${not empty requestScope.msg}">
                <div>${requestScope.msg}</div>
            </c:if>
            <c:forEach var="booking" items="${sessionScope.cart}">             
                <div>
                    Detail :${booking.roomType.roomDescription} 
                    Type : ${booking.roomType.type.typeName}
                    Quantity: ${booking.quantity}
                    Price : ${booking.roomType.roomPrice} per day $
                </div>                   
            </c:forEach>
            Total: ${sessionScope.total} $
            <form action="Booking" method="GET">
                <input type="hidden" name="hotelID" value="${sessionScope.hotel.hotelID}">
                <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                <input type="hidden" name="checkout" value="${sessionScope.checkout}">
                <input type="text" name="discountCode" value="${param.discountCode}">
                <input type="submit" value="Booking">
            </form>

        </c:if>
        <script>
            function showDate() {
                document.getElementById('date').style.display = 'block';
                document.getElementById('date-label').style.display = 'none';
                document.getElementById('btn-changeDate').style.display ='none';
            }
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
            function checkChange() {
                if (document.getElementById('checkout').value === "" || document.getElementById('checkin').value === "") {
                    window.alert('Not allow');
                    return false;
                }

            }
        </script>

    </body>
</html>
