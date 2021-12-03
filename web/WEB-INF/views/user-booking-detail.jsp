<%-- 
    Document   : user-booking-detail
    Created on : Oct 8, 2021, 7:02:41 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | View Booking Detail</title>
    </head>
    <body>
        <h1>Your Booking</h1>
        <div>
            Welcome ${sessionScope.userdata.userName}
            <a href="Index">Index</a>
            <a href="Cart">View Cart</a>
            <a href="UserUpdate">User</a>
            <a href="UserBooking">Your Booking</a>
            <a href="Logout">Log out</a>
        </div>
        <c:set var="booking" value="${requestScope.booking}"></c:set>
        <div>Area : ${booking.hotel.area.areaName}</div>
        <div>Hotel Name : ${booking.hotel.hotelName}</div>
        <div>Checkin : ${booking.checkinDate}</div>
        <div>Checkout ${booking.checkoutDate}</div>
        <c:choose>
            <c:when test="${booking.bookingStatus eq 0}">
                <div>Status: Cancel</div>
            </c:when>
            <c:when test="${booking.bookingStatus eq 1}">
                <div>Status: Booking</div>
            </c:when>
            <c:when test="${booking.bookingStatus eq 2}">
                <div>Status: Success </div>
                <div> Thanks you! Rating this hotel <button id="btn-rating" onclick="rating()">Rating</button></div>
                <div id="ratingHotel" style="display: none;">
                    <form action="Rating" method="POST">
                        <input type="hidden" name="hotelID" value="${booking.hotel.hotelID}">
                        <input type="hidden" name="bookingID" value="${booking.bookingID}">
                        <div>Your feedback : <input type="text" value="" name="feedBack"></div> 
                        <div> Rating : <input type="number" value="10" min="0" max="10" step="1" name="score"></div>
                        <input type="submit" value="OK">
                    </form>
                </div>
            </c:when>
            <c:when test="${booking.bookingStatus eq 3}">
                <c:set var="rating" value="${requestScope.rating}"></c:set>
                    <div>
                        Your feed back : ${rating.description}
                </div>
                <div>
                    Your vote : ${rating.score}
                </div>
            </c:when>
            <c:otherwise>
                <div>Status: Denied</div>
            </c:otherwise>
        </c:choose>       
        <c:forEach var="detail" items="${requestScope.bookingdetails}">
            <div>
                Type :${detail.roomType.type.typeName}
                Room Type : ${detail.roomType.roomDescription}
                Quantity : ${detail.quantity}
                Price ${detail.roomType.roomPrice}</div>            
        </c:forEach>
        <c:choose>
            <c:when test="${not empty booking.discountCode}">
                <div>Code : ${booking.discountCode}</div>
                <div>Total after using code : ${booking.bookingTotal}</div>
            </c:when>
            <c:otherwise>
                <div>Total : ${booking.bookingTotal}</div>
            </c:otherwise>
        </c:choose>
        <script>
            function rating() {
                document.getElementById('ratingHotel').style.display = 'block';
                document.getElementById('btn-rating').style.display = 'none';
            }
        </script>
    </body>
</html>
