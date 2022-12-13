package board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/commentUpdate")
public class commentUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String Comment = (String) request.getParameter("comment");
        int commentid = Integer.parseInt(request.getParameter("commentID"));

        commentDAO commentDao = new commentDAO();
        int result = commentDao.update(Comment, commentid);

        if (result == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('댓글 수정완료!')");
            script.println("location.href = '../webproject/board/board.jsp'");
            script.println("</script>");
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('다시 시도해주세요')");
            script.println("location.href = '../webproject/board/board.jsp'");
            script.println("</script>");
        }
    }
    
}