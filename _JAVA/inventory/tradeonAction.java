package inventory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tradeon")
public class tradeonAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String setprice = request.getParameter("price");
        if((setprice == "") || (setprice.matches("^[0-9]*$")==false)){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('숫자만 입력하세요')");
            script.println("location.href = '../webproject/inventory/inventory.jsp'");
            script.println("</script>");  
        } else {

        //먼저 아이템아이디정보, 유저아이디 정보 불러옴
        int invID = Integer.parseInt(request.getParameter("invid"));
        String userID = request.getParameter("id");

        invDAO invDao = new invDAO();

        int updateState = 1;
        int updatePrice = Integer.parseInt(request.getParameter("price"));

        int suctrade = invDao.tradeFunc(invID, userID, updatePrice, updateState);
        if(suctrade == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('거래소 등록 성공')");
            script.println("location.href = '../webproject/inventory/inventory.jsp'");
            script.println("</script>");  
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('거래소 등록 실패')");
            script.println("location.href = '../webproject/inventory/inventory.jsp'");
            script.println("</script>");  
        }
    }
    } 
}