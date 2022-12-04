package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userLogin")
public class userLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memId = (String) request.getParameter("userID");
        String memPw = (String) request.getParameter("userPW");
        
        int result = new userDAO().login(memId, memPw);
        
        if (result == 1) {

            HttpSession session = request.getSession();

            request.setAttribute("userID", memId);
            session.setAttribute("userID", memId);

            System.out.println("Session생성: "+session);

            response.sendRedirect("main/main.jsp");

        } else {
            response.sendRedirect("main/login.jsp");
        }
    }
    
}
