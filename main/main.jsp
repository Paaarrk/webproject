<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/custom.css">
    <title>가입 성공!!</title>
    <script src="${pageContext.request.contextPath}/asset/js/jquery-3.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/bootstrap.js"></script>
</head>
<%
    String id = (String)request.getAttribute("userID");
%>
<body>
    <div>
        <table id="login">
            <thead>
                <th colspan="3">
                    <%= id%>이 로그인에 성공하셨습니다!
                </th>
            </thead>
            <tbody>
                <tr>
                    <td width="180px" colspan="2">

                    </td>
                    <td>
                        <input class="btn btn-primary" type="button" value="닫기" onclick="self.close()">
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>