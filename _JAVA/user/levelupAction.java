package user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/levelup")
public class levelupAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String userID = (String) request.getParameter("id");
        userDAO userDao = new userDAO();
        userDTO userDto = userDao.userinformation(userID);

        int needexp = userDto.getlevel() * userDto.getlevel() * 200 + 500;
        if(userDto.getlevel() > 10) {
            needexp = 20000;
        }
        if(userDto.getexp() < needexp) {
            PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('경험치가 부족해요...')");
                script.println("location.href = '../webproject/rank/levelup.jsp'");
                script.println("</script>");
        } else {
            int result1 = userDao.exp(userID, -needexp);
            int result2 = userDao.levelup(userID);
            int result4 = 0; // 최초 레벨업 시, battleinfo등록
            if(result1 == 1 && result2 == 1) {
                userDTO levelup = new userDAO().userinformation(userID);
                ranklvDAO ranklvDao = new ranklvDAO();
                ranklvDTO ranklvDto = ranklvDao.getranklvDTO(userID);
                int result3 = 0;
                if(ranklvDto == null){
                    result3 = new ranklvDAO().setRank(levelup.getlevel(), userID);
                    result4 = new battleinfoDAO().setbattleinfofirst(userID); //battleinfo 등록
                } else {
                    result3 = new ranklvDAO().updateRank(levelup.getlevel(), userID);
                    result4 = 1;
                }

                if (result3 == 1 && result4 == 1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('레벨업에 성공하였습니다!!')");
                    script.println("location.href = '../webproject/rank/levelup.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('레벨업은 되었는데 랭킹등록에는 실패했어요')");
                    script.println("location.href = '../webproject/rank/levelup.jsp'");
                    script.println("</script>");
                }
                
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('전부실패했어요. 다시시도해주세요.')");
                script.println("location.href = '../webproject/rank/levelup.jsp'");
                script.println("</script>");
            }
        }

    }
}