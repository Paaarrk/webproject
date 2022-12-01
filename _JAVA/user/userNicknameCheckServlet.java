package user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userNICKNAMECheck")
public class userNicknameCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memNickname = (String) request.getParameter("userNICKNAME");
        
        userDAO userDAO = new userDAO();
        int result = userDAO.regNICKNAMECheck(memNickname);
        
        PrintWriter out = response.getWriter();
        out.write(result+"");
    }
}
