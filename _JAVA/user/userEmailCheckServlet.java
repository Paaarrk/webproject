package user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.userDAO;
import user.userDTO;

@WebServlet("/userEMAILCheck")
public class userEmailCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memEmail = (String) request.getParameter("userEMAIL");
        
        userDAO userDAO = new userDAO();
        int result = userDAO.regEMAILCheck(memEmail);
        
        PrintWriter out = response.getWriter();
        out.write(result+"");
    }
}

