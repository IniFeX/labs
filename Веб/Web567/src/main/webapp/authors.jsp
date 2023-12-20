<%@ page import="webapp.ee.DataBaseClassses.AuthorsDAO" %>
<%@ page import="webapp.ee.DataBaseClassses.Author" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    AuthorsDAO authorsDAO = new AuthorsDAO();
    List<Author> authors = authorsDAO.getAllAuthors();
    request.setAttribute("authors", authors);
%>

<html>
<head>
    <title>Список авторов</title>
</head>
<body>
<h1>Список всех авторов:</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Фамилия</th>
    </tr>
    <c:forEach var="author" items="${authors}">
        <tr>
            <td><c:out value="${author.authorID}" /></td>
            <td><c:out value="${author.firstName}" /></td>
            <td><c:out value="${author.lastName}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>