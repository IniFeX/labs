<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 20.11.2023
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
</head>
<body>

<sql:setDataSource var="dataSource" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/laba4" user="root" password="123123" />

<sql:query var="authors" dataSource="${dataSource}">
  SELECT * FROM Authors;
</sql:query>

<c:forEach var="author" items="${authors.rows}">
  <c:out value="${author['firstName']}"/> <c:out value="${author['lastName']}"/><br/>
</c:forEach>
</body>
</html>
