package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loadInfo")
public class userInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String memId = (String) request.getParameter("userID");
        System.out.println(memId+" : 아이디 읽어왔다");
        
        userDAO userDAO = new userDAO();
        userDTO userinfo = userDAO.userinformation(memId);
        System.out.println("여긴 오니?");
        
        String user_ID = userinfo.getmemId();                           //0 ID
        String user_NAME = userinfo.getmemName();                       //1 NAME
        String user_NICKNAME = userinfo.getnickName();                  //2 NICKNAME
        String user_EMAIL = userinfo.gete_mail();                       //3 EMAIL
        String user_GENDER = userinfo.getgender();                      //4 GENDER
        String user_PHONE = userinfo.getphoneNum();                     //5 PHONE
        String user_COIN = String.valueOf(userinfo.getcoin());          //6 COIN
        String user_CASH = String.valueOf(userinfo.getcash());          //7 CASH
        String user_LEVEL = String.valueOf(userinfo.getlevel());        //8 LEVEL
        String user_EXP = String.valueOf(userinfo.getexp());            //9 EXP
        Date user_REGDATE = userinfo.getregDate();

        // sql의 date값 string으로 변경
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String user_DATE = format.format(user_REGDATE);                 //10 DATE

        String result = user_ID+":"+user_NAME+":"+user_NICKNAME+":"+user_EMAIL+":"+user_GENDER+":"+user_PHONE+":"+user_COIN+":"+user_CASH+":"+user_LEVEL+":"+user_EXP+":"+user_DATE;
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        System.out.println("정상적으로 정보 로딩 완료");
    }
}
