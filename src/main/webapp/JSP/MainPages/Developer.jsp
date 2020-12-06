<%--
  Created by IntelliJ IDEA.
  User: Lee
  Date: 01.10.2020
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Dev Home Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<c:if test="${sessionScope.Developer == null}">
    <jsp:forward page="/JSP/Login.jsp"></jsp:forward>
</c:if>
<body>
<center><h2>Dev Home Page</h2></center>
Welcome <%=request.getAttribute("login") %>
<div style="text-align: right"><a href="LogoutServlet">Logout</a></div>
</body>
</html>
