<%-- 
    Document   : change-user-info
    Created on : Sep 12, 2021, 10:58:35 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | User Profile</title>
    </head>
    <body>
        <h1>User Profile</h1>
        Welcome ${sessionScope.userdata.userName}
        <a href="Booking Hotel">Index Page</a>|<a href="Logout">Logout</a>
        <c:set var="user" value="${sessionScope.userdata}"/>

        <form action="UserUpdate" method="POST">
            <c:if test="${not empty requestScope.msgUpdate}">                                            
                <strong>${requestScope.msgUpdate}</strong>
            </c:if>
            <div>
                ID:<label>${user.userID}</label> 
                <c:if test="${not empty requestScope.msgID}">                                            
                    <strong>${requestScope.msgID}</strong>                
                </c:if>
            </div>
            <div>
                Name:<input type="text" name="userName" value="${user.userName}"/>           
                <c:if test="${not empty requestScope.msgName}">                                            
                    <strong>${requestScope.msgName}</strong>
                </c:if>  
            </div>            
            <div>
                Password: <a href="UserChangePassword">Change Password</a>                   
            </div>
            <div>
                Phone Number:<input type="text" name="userPhone" value="${user.userPhone}"/>
                <c:if test="${not empty requestScope.msgPhone}">                                            
                    <strong>${requestScope.msgPhone}</strong>
                </c:if>
            </div>

            <div>
                Address:<input type="text" name="userAddress" value="${user.userAddress}"/>
                <c:if test="${not empty requestScope.msgAddress}">                                            
                    <strong>${requestScope.msgAddress}</strong> 
                </c:if>
            </div>
            <div>
                <input type="submit" value="OK" />
                <input type="reset"  value="RESET" />
            </div>
        </form>
    </body>
</html>
