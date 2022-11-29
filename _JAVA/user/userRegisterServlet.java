package user;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.userDAO;
import user.userDTO;

@WebServlet("/userRegister")
public class userRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memId = (String) request.getParameter("userID");
        String memPw = (String) request.getParameter("userPW");
        String e_mail = (String) request.getParameter("userEMAIL");
        String memnickName = (String) request.getParameter("userNICKNAME");
        String memName = (String) request.getParameter("userNAME");
        String phoneNum = (String) request.getParameter("userPHONE");
        String gender = (String) request.getParameter("userGENDER");
        
        int result = new userDAO().register(memId, memPw, memName, memnickName, gender, e_mail, phoneNum);
        
        if (result == 1) {
            response.sendRedirect("regform/regSuccess.jsp");
        } else {
            response.sendRedirect("regform/regFailed.jsp");
        }
    }
    
}
