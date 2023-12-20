<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8" %>
<HTML>
<HEAD>
    <TITLE>Использование JavaServer Pages</TITLE>
    <META NAME="author" CONTENT="Marty Hall -- hall@apl.jhu.edu">
    <META NAME="keywords"
          CONTENT="JSP,JavaServer Pages,servlets">
    <META NAME="description"
          CONTENT="Быстрый пример четырех основных тэгов JSP.">
    <LINK REL=STYLESHEET
          HREF="My-Style-Sheet.css"
          TYPE="text/css">
</HEAD>
<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE"
      VLINK="#551A8B" ALINK="#FF0000">
<CENTER>
    <TABLE BORDER=5 BGCOLOR="#EF8429">
        <TR><TH CLASS="TITLE">
            Использование JavaServer Pages</TABLE>
</CENTER>
<P>
    Некое динамическое содержание созданное с использованием различных механизмов JSP:
<UL>
    <LI><B>Выражение.</B><BR>
        Имя вашего хоста: <%= request.getRemoteHost() %>.
    <LI><B>Scriptlet.</B><BR>
            <% System.out.println("Дополнительные данные запроса: " +
 request.getQueryString()); %>
    <LI><B>Объявление (совместно с выражением).</B><BR>
            <%! private int accessCount = 0; %>
        Количество обращений к странице с момента загрузки сервера: <%= ++accessCount
%>
    <LI><B>Директива (совместно с выражением).</B><BR>
        <%@ page import = "java.util.*" %>
        Текущая дата: <%= new Date() %>
</UL>
<a href="authors.jsp">Отобразить список авторов</a>
<a href="authors2.jsp">Отобразить список авторов 2</a>
</BODY>
</HTML>