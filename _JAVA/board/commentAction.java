package board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.userDAO;


@WebServlet("/commentAction")
public class commentAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int boardID= Integer.parseInt(request.getParameter("boardID"));
        String Comment = (String) request.getParameter("comment");
        String userID = (String) request.getParameter("id");
        userDAO userDao = new userDAO();
        commentDAO commentDao = new commentDAO();

        int result = commentDao.write(boardID, Comment, userID);
        int result2 = userDao.userWrite(userID);

        if (result == 1 && result == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('댓글 작성완료! 경험치와 코인 500개가 지급됩니다')");
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
