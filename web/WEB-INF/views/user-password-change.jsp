<%-- 
    Document   : user-password-change
    Created on : Sep 12, 2021, 11:10:21 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | User Change Password</title>
    </head>
    <body>    
        Welcome ${sessionScope.userdata.userName}
        <a href="Index">Index Page</a> | <a href="UserUpdate">User Profile</a> | <a href="Logout">Logout</a>
        <h1>Change Password</h1>
        <form action="UserChangePassword" method="POST">
            <div>
                Password:<input type="password" name="userPassword" value=""/>           
                <c:if test="${not empty requestScope.msgPassword}">
                    <strong>${requestScope.msgPassword}</strong>
                </c:if>
            </div>            
            <div>
                New Password:   <input type="password" name="userNewPassword" value=""/>           
                <c:if test="${not empty requestScope.msgNewPassword}">
                    <strong>${requestScope.msgNewPassword}</strong>
                </c:if>
            </div>
            <div>
                Re New Password:<input type=password name="userReNewPassword" value=""/>
                <c:if test="${not empty requestScope.msgReNewPassword}">
                    <strong>${requestScope.msgReNewPassword}</strong>
                </c:if>
            </div>
            <div>
                <input type="submit" value="OK">
                <input type="reset" value="RESET">
            </div>
        </form>
    </body>
</html>
