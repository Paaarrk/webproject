<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="board.boardDAO" %>
<%@ page import="board.boardDTO" %>
<%@ page import="user.userDAO" %>
<%@ page import="java.io.PrintWriter" %>

<jsp:useBean id="board" class="board.boardDTO" scope="page" />
<jsp:setProperty name="board" property="boardTitle" />
<jsp:setProperty name="board" property="boardContent" />

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>보낸다</title>
</head>
<body>
    <%
        String id = null;
        if (session.getAttribute("userID") != null) {
            id = (String) session.getAttribute("userID");
        }
        if (id == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 하세요')");
            script.println("location.href = '../main/login.jsp'");
            script.println("</script>");

        } else {
            boardDAO boardDao = new boardDAO();
            int result = boardDao.write(board.getboardTitle(), id, board.getboardContent());
            if (result == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('글쓰기에 실패했습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                userDAO userinf = new userDAO();
                int res = userinf.userWrite(id);

                if(res== 1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("location.href = '../board/board.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('글쓰기에 실패했습니다.')");
                    script.println("history.back()");
                    script.println("</script>");
                }
            }
        }
    %>
</body>
</html>