package inventory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gachaServlet")
public class gachaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        
        int itemId = Integer.parseInt(request.getParameter("itemID"));
        String userId = (String) request.getParameter("userID");
        System.out.println(itemId+" : 아이템 아이디 읽어왔다");
        
        invDAO invDao = new invDAO();
        invDTO invinfo = invDao.getitemInfo(itemId);
        System.out.println("여긴 오니?");
        
        String result = null;
        int storage = 0;
        storage = invDao.setinv(itemId, userId, invinfo.getitemName(), invinfo.getitemAtt(), invinfo.getitemDef(), invinfo.getitemAvd(), invinfo.getitemCrit());
        if(storage != -1) {
            //데이터 저장 성공

            result = String.valueOf(itemId)+":"+invinfo.getitemName()+":"+invinfo.getitemUrl()+":"+String.valueOf(invinfo.getitemAtt())+":"+String.valueOf(invinfo.getitemDef())+":"+String.valueOf(invinfo.getitemAvd())+":"+String.valueOf(invinfo.getitemCrit());

        } else {
            result = null;
        }

        
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        System.out.println("정상적으로 정보 로딩 완료");
    }
    
}