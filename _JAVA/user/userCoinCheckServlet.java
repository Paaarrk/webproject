package user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/userCoinCheck")
public class userCoinCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memId = (String) request.getParameter("userID");
        int result;
        userDAO userDAO = new userDAO();
        if (userDAO.enoughcoin(memId,3000) == 1) {
            int a = userDAO.coin(memId, -3000);
            if(a == 1) {
                result = 1;
            } else {
                System.out.println("코인 계산을 실패함");
                result = 0; //실패
            }
        } else {
            result = 0; //실패
        }
        
        PrintWriter out = response.getWriter();
        out.write(result+"");
    }
}
