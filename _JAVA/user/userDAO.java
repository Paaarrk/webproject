package user;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDAO {
    
    private Connection conn;

    public userDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/webproject?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String dbID
            String dbPassword
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //아이디 중복확인
    public int regIDCheck(String userId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT ID FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return 0; // 존재하는 회원
            }
            else {
                return 1; // 가입가능
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1; //데이터베이스 오류
    }

    //닉네임 중복확인
    public int regNICKNAMECheck(String userNickname) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT NickName FROM webproject.memberinfo WHERE (NickName = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userNickname);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return 0; // 존재하는 닉네임
            }
            else {
                return 1; // 닉네임 사용가능
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1; //데이터베이스 오류
    }

    //이메일 중복확인
    public int regEMAILCheck(String userEmail) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT E_mail FROM webproject.memberinfo WHERE (E_mail = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return 0; // 사용된 이메일
            }
            else {
                return 1; // 이메일 사용가능
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1; //데이터베이스 오류
    }

    //가입
    public int register(String userId, String userPw, String userName, String usernickName, String gender, String email, String phoneNum) {
        PreparedStatement pstmt = null;
        String SQL = "INSERT INTO webproject.memberinfo VALUES (?, ?, ?, ?, ?, ?, ?, 5000, 0, 1, 0, sysdate(), 0)";
        try {
            pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1,userId);
            pstmt.setString(2, userPw);
            pstmt.setString(3, userName);
            pstmt.setString(4, usernickName);
            pstmt.setString(5, gender);
            pstmt.setString(6, email);
            pstmt.setString(7, phoneNum);

            return pstmt.executeUpdate();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; //데이터베이스 오류
    }

    //로그인
    public int login(String userId, String userPw)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT ID FROM webproject.memberinfo WHERE ((ID = ?)&&(PW = ?))";

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            pstmt.setString(2,userPw);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                //로그인
                System.out.println("로그인 성공");
                return 1;
            }
            else {
                //로그인 실패
                System.out.println("로그인 실패");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("로그인 도중 오류 발생");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("로그인 처리중 오류 발생");
                e.printStackTrace();
            }
        }

        System.out.println("데이터베이스 오류");
        return -1;
    }
}
