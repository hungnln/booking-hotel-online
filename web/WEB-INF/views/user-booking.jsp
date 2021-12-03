<%-- 
    Document   : user-booking
    Created on : Oct 8, 2021, 4:16:54 PM
    Author     : SE140018
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | View Booking list</title>
    </head>
    <body>
        <h1>Booking List</h1>
        <div>
            Welcome ${sessionScope.userdata.userName}
            <a href="Index">Index</a>
            <a href="Cart">View Cart</a>
            <a href="UserUpdate">User</a>                   
            <a href="Logout">Log out</a>
        </div>
        <form action="UserBooking" method="GET">
            <div>
                <input type="text" value="${param.searchHotelName}" name="searchHotelName" placeholder="Enter hotel Name">
            </div>
            <div>
                Enter First Create Date:
                <input type="date" id="firstCreateDate" name="firstCreateDate" onchange="first_CreateDate()" value="${param.firstCreateDate}">
                Enter Last Create Date:
                <input type="date" id="lastCreateDate" name="lastCreateDate" onchange="last_CreateDate()" value ="${param.lastCreateDate}">
            </div>
            <input type="submit" value="OK" onclick="return check();">
        </form>
                <c:if test="${not empty requestScope.msg}">
                    <div>${requestScope.msg}</div>
                </c:if>
        <c:choose>
            <c:when test="${not empty requestScope.bookings}">

                <table border="1">
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Hotel</th>
                            <th>Create Date</th>
                            <th>Check In</th>
                            <th>Check Out</th>                                
                            <th>Total</th>
                            <th>Status</th>
                            <th>Detail</th>
                            <th>Option</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${requestScope.bookings}">
                            <tr>
                                <td>${booking.bookingID}</td>
                                <td>${booking.hotel.hotelName}</td>
                                <td>${booking.createDate}</td>
                                <td>${booking.checkinDate}</td>
                                <td>${booking.checkoutDate}</td>                               
                                <td>${booking.bookingTotal}</td>
                                <td>${booking.bookingStatus}</td>
                                <td><a href="UserBookingDetail?bookingID=${booking.bookingID}">View</a></td>
                                <c:choose>
                                    <c:when test="${booking.bookingStatus eq 1}">

                                        <td>
                                            <form action="DeleteBooking" method="POST">
                                                <input type="hidden" name="bookingID" value="${booking.bookingID}">
                                                <input type="submit" value="Delete" onclick="return confirm('Do you want to delete this book?');">
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                No Booking
            </c:otherwise>
        </c:choose>
        <script>
            function last_CreateDate() {
                if (document.getElementById('firstCreateDate').value >= document.getElementById('lastCreateDate').value)
                 {
                    window.alert('Not allow');
                    document.getElementById('lastCreateDate').value = "";
                    request.abort();
                }
            }
            function first_CreateDate() {
                if (document.getElementById('lastCreateDate').value !== "") {
                    if (document.getElementById('firstCreateDate').value >= document.getElementById('lastCreateDate').value) {
                        window.alert('Not allow');
                        document.getElementById('firstCreateDate').value = "";
                        request.abort();
                    }
                }
            }
            function check() {
                if ((document.getElementById('firstCreateDate').value === "" && document.getElementById('lastCreateDate').value !== "") || (document.getElementById('firstCreateDate').value !== "" && document.getElementById('lastCreateDate').value === "")) {
                    window.alert('Please enter full range createDate or no choose');
                    return false;
                }
            }
        </script>
    </body>
</html>
