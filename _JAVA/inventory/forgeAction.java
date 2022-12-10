package inventory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.userDAO;

@WebServlet("/forge")
public class forgeAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //먼저 아이템아이디정보, 유저아이디 정보 불러옴
        int invID = Integer.parseInt(request.getParameter("invid"));
        String userID = request.getParameter("id");

        userDAO userDao = new userDAO();
        int canforge = userDao.enoughcoin(userID, 1000);
        
        if(canforge == 1) {
            invDAO invDao = new invDAO();
            invDTO invDto = invDao.getinvDTO(invID, userID);

            //업데이트 시작
            int upforgeLV = 1 + invDto.getforgeLV();
            int upitemAtt = 5 * invDto.getitemRank() + invDto.getitemAtt();
            int upitemDef = 5 * invDto.getitemRank() + invDto.getitemDef();
            int upitemAvd = 2 * invDto.getitemRank() + invDto.getitemAvd();
            int upitemCrit = 1 * invDto.getitemRank() + invDto.getitemCrit();
            int sucRate = 100 - 5 * invDto.getitemRank() - 3 * invDto.getforgeLV() - 25;
            if(sucRate < 5) {
                sucRate = 5;
            }
            if(invDto.getitemRank() == 5) {
                sucRate = 100;
            }

            int downcoin = userDao.coin(userID, -1000);
            if ( downcoin == 1) {
                int uSuc = (int)(Math.random() * 100); //성공했니?
                if(uSuc <= sucRate) {
                    int result = invDao.forgeupdate(invID, userID, upforgeLV, upitemAtt, upitemDef, upitemAvd, upitemCrit, 0);
                    if(result == 1) {
                        System.out.println("강화성공! 확률: "+String.valueOf(sucRate)+" 난수: "+String.valueOf(uSuc));
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("location.href = '../webproject/inventory/inventory.jsp'");
                        script.println("alert('강화 성공!!')");
                        
                        script.println("</script>");
                    } else {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('강화 중 오류 발생')");
                        script.println("location.href = '../webproject/inventory/inventory.jsp'");
                        script.println("</script>");
                    }
                } else {
                    int result = invDao.forgeupdate(invID, userID, invDto.getforgeLV(), invDto.getitemAtt(), invDto.getitemDef(), invDto.getitemAvd(), invDto.getitemCrit(), 2);
                    if(result == 1) {
                        System.out.println("강화실패... 확률: "+String.valueOf(sucRate)+" 난수: "+String.valueOf(uSuc));
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('강화 실패...')");
                        script.println("location.href = '../webproject/inventory/inventory.jsp'");
                        script.println("</script>");
                    } else {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('강화 중 오류발생')");
                        script.println("location.href = '../webproject/inventory/inventory.jsp'");
                        script.println("</script>");    
                    }
                }
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('코인 빼는 중 오류 발생')");
                script.println("location.href = '../webproject/inventory/inventory.jsp'");
                script.println("</script>");
            }

        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('코인이 부족합니다.')");
            script.println("location.href = '../webproject/inventory/inventory.jsp'");
            script.println("</script>");
        }



        

    }
    
}