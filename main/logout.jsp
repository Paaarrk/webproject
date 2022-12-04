<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> 로그아웃 </title>
</head>
<body>
    <%
        System.out.println(session+"로그아웃 완료");
        session.invalidate();
    %>
    <script>
        location.href = '../main/login.jsp';
    </script>
</body>
</html>