package inventory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.battleinfoDAO;
import user.battleinfoDTO;

@WebServlet("/equip")
public class equipAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //먼저 아이템아이디정보, 유저아이디 정보 불러옴
        int invID = Integer.parseInt(request.getParameter("invid"));
        String userID = request.getParameter("id");

        invDTO invDto = new invDAO().getinvDTO(invID, userID);
        battleinfoDTO battleinfoDto = new battleinfoDAO().battleinfo(userID);
        if(invDto.getitemState() == 3) {
            int result1 = 0; //장착장비 장착해제
            int result2 = 0; // 배틀인포에 등록
            if(invDto.getitemID() < 200) {
                result1 = new invDAO().controlitem(userID, battleinfoDto.getweapID(), 0);
                result2 = new battleinfoDAO().equipWeap(userID, 0);
                if(result1 == 1 && result2 == 1){
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 해제완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('해제실패')");
                    System.out.println(result1+","+result2);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                }
                
            } else {
                result1 = new invDAO().controlitem(userID, battleinfoDto.getaccID(), 0);
                result2 = new battleinfoDAO().equipAcc(userID, 0);
                if(result1 == 1 && result2 == 1){
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 해제완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('해제실패')");
                    System.out.println(result1+","+result2);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                }
            }
        } else {

        
        int result1 = 0; // 기존무기 장착해제
        int result2 = 0; // 선택무기 장착
        int result3 = 0; // 배틀인포에 등록
        if(invDto.getitemID() < 200) {
            //무기
            if(battleinfoDto.getweapID() != 0) {
                
                result1 = new invDAO().controlitem(userID, battleinfoDto.getweapID(), 0);
                result2 = new invDAO().controlitem(userID, invID, 3);
                result3 = new battleinfoDAO().equipWeap(userID, invID);
                if(result1==1 && result2==1 && result3==1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 실패')");
                    System.out.println(result1+","+result2+","+result3);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } 
            } else {
                result1 = 1;
                result2 = new invDAO().controlitem(userID, invID, 3);
                result3 = new battleinfoDAO().equipWeap(userID, invID);
                if(result1==1 && result2==1 && result3==1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 실패')");
                    System.out.println(result1+","+result2+","+result3);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } 
            }
        } else {
            //악세서리
            if(battleinfoDto.getaccID() != 0) {
                
                result1 = new invDAO().controlitem(userID, battleinfoDto.getaccID(), 0);
                result2 = new invDAO().controlitem(userID, invID, 3);
                result3 = new battleinfoDAO().equipAcc(userID, invID);
                if(result1==1 && result2==1 && result3==1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 실패')");
                    System.out.println(result1+","+result2+","+result3);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } 
            } else {
                result1 = 1;
                result2 = new invDAO().controlitem(userID, invID, 3);
                result3 = new battleinfoDAO().equipAcc(userID, invID);
                if(result1==1 && result2==1 && result3==1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 완료')");
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('장착 실패')");
                    System.out.println(result1+","+result2+","+result3);
                    script.println("location.href = '../webproject/rank/setitem.jsp'");
                    script.println("</script>");
                } 
            }
        }

        
    }
}   
}
