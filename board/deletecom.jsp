<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="board.commentDAO" %>
<%@ page import="board.commentDTO" %>
<%@ page import="java.io.PrintWriter" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>놀이터</title>
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

        } 
        
        int commentid = 0;
        if (request.getParameter("commentID") != null) {
            commentid = Integer.parseInt(request.getParameter("commentID"));
        }
        if (commentid == 0) {
            PrintWriter scri = response.getWriter();
            scri.println("<script>");
            scri.println("alert('유효하지 않은 게시물입니다.')");
            scri.println("location.href = '../board/board.jsp'");
            scri.println("</script>");
        } 

        commentDTO commentDto = new commentDAO().getcommentDTO(commentid);
        if (!id.equals(commentDto.getID())) {
        PrintWriter scrip = response.getWriter();
        scrip.println("<script>");
        scrip.println("alert('권한이 없습니다')");
        scrip.println("location.href = '../board/board.jsp'");
        scrip.println("</script>");
        } else {
            commentDAO commentDao = new commentDAO();
            int result = commentDao.delete(commentid);
            if (result == 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('삭제 완료')");
                script.println("location.href = '../board/board.jsp'");
                script.println("</script>"); 
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('글 삭제에 실패했습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } 
        }
    %>
</body>
</html>