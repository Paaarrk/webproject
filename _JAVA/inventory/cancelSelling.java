package inventory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cancelSelling")
public class cancelSelling extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String userID = (String) request.getParameter("id");
        int invID = Integer.parseInt(request.getParameter("invid"));

        invDAO invDao = new invDAO();
        invDTO invDto = invDao.getinvDTO(invID, userID);
        if(invDto.getitemName()==null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href = '../webproject/inventory/selling.jsp'");
            script.println("alert('잘못된 접근입니다. (판매되었을 수 있음!)')");
            script.println("</script>");
        } else {
            //정상적인 접근이면 State = 0으로
            int result = invDao.tradeFunc(invID, userID, 0, 0);
            if(result == 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href = '../webproject/inventory/selling.jsp'");
                script.println("alert('판매가 성공적으로 취소되었습니다!')");
                script.println("</script>");
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href = '../webproject/inventory/selling.jsp'");
                script.println("alert('이미 판매하지 않는 상품이거나 판매가되었습니다')");
                script.println("</script>");
            }
        }


    } 
}