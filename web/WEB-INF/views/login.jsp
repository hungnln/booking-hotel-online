<%-- 
    Document   : login
    Created on : Sep 11, 2021, 11:41:04 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking | Log in</title>
    </head>
    <body>       
        <div><a href="Index">Index </a> | <a href="SignUp"> Sign Up</a></div>
        <h1>Login</h1>
        <c:if test="${not empty requestScope.msgLoginFail}">
            <strong>${requestScope.msgLoginFail}</strong>
        </c:if>
        <form action="Login" method="POST">
            <div>
                <input type="text" name="userID" value="${param.userID}" placeholder="User ID"/>
                <c:if test="${not empty requestScope.msgID}">
                    <strong>${requestScope.msgID}</strong>
                </c:if>
            </div>
            <div>
                <input type="password" name="userPassword" value="" placeholder="User Password" />
                <c:if test="${not empty requestScope.msgPassword}">
                    <strong>${requestScope.msgPassword}</strong>
                </c:if>
            </div>
            <div><input type="submit" value="LOGIN" /> </div>
        </form>
    </body>
</html>
