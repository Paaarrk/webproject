package inventory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.userDAO;

@WebServlet("/purchaseAction")
public class purchaseAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String userID = (String) request.getParameter("id");
        int invID = Integer.parseInt(request.getParameter("invid"));
        
        invDAO invDao = new invDAO();
        invDTO invDto = invDao.gettradeDTO(invID, 1);
        int price = invDto.getitemPrice();
        

        userDAO userinfo = new userDAO();
        userDAO seller = new userDAO();
        int canpur = userinfo.enoughcoin(userID, price);
        System.out.println("통과2" + userID + invID);
        if(canpur == 1) {
            int usersuc = userinfo.coin(userID, -price);
            int sellsuc = seller.coin(invDto.getID(), price);
            System.out.println("통과3"+invDto.getID()+usersuc+sellsuc);
            if ( usersuc == 1 && sellsuc == 1) {
                int result = new invDAO().tradePurchase(invID, userID);
                if (result == 1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("location.href = '../webproject/inventory/trademarket.jsp'");
                    script.println("alert('구매 성공!')");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("location.href = '../webproject/inventory/trademarket.jsp'");
                    script.println("alert('데이터 변경중 오류발생, 문의바람')");
                    script.println("</script>");
                }
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href = '../webproject/inventory/trademarket.jsp'");
                script.println("alert('정산중 오류가 발생하였습니다.')");
                script.println("</script>");
            }

        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href = '../webproject/inventory/trademarket.jsp'");
            script.println("alert('코인이 부족합니다.')");
            script.println("</script>");
        }



    }
}

        
