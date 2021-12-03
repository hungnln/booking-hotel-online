<%-- 
    Document   : index-admin
    Created on : Oct 9, 2021, 2:27:51 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Hotel | Admin Page</title>
    </head>
    <body>
        <h1>Hello this is ADMIN PAGE SITE</h1>
        <div>
            Welcome ${sessionScope.userdata.userName}
            <a href="Index">Index</a>           
            <a href="UserUpdate">User</a>
            <a href="Logout">Log out</a>
        </div>
    </body>
</html>
