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
            String dbID = "root";
            String dbPassword = "dlsdyd11!!";
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
        } finally {
            try {
                
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
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

    //회원 정보 출력
    public userDTO userinformation(String userId)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                User.setmemId(rs.getString(1));
                User.setmemPw(rs.getString(2));
                User.setmemName(rs.getString(3));
                User.setnickName(rs.getString(4));
                User.setgender(rs.getString(5));
                User.sete_mail(rs.getString(6));
                User.setphoneNum(rs.getString(7));
                User.setcoin(rs.getInt(8));
                User.setcash(rs.getInt(9));
                User.setlevel(rs.getInt(10));
                User.setexp(rs.getInt(11));
                User.setregDate(rs.getDate(12));
                User.setAuth(rs.getInt(13));

                return User;

            } else {
                System.out.println("존재하지 않는 아이디");
                return null;
            }
        } catch (Exception e) {
            System.out.println("회원정보출력 도중 오류 발생");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("회원정보출력 삭제중 오류 발생");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return null;
    }

    //글쓰면 코인, 경험치 주기
    public int userWrite(String userId)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int coin = 0;
            int exp = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                User.setmemId(rs.getString(1));
                User.setmemPw(rs.getString(2));
                User.setmemName(rs.getString(3));
                User.setnickName(rs.getString(4));
                User.setgender(rs.getString(5));
                User.sete_mail(rs.getString(6));
                User.setphoneNum(rs.getString(7));
                User.setcoin(rs.getInt(8));
                User.setcash(rs.getInt(9));
                User.setlevel(rs.getInt(10));
                User.setexp(rs.getInt(11));
                User.setregDate(rs.getDate(12));
                User.setAuth(rs.getInt(13));

                coin = User.getcoin();
                coin = coin + 500;
                exp = User.getexp();
                exp = exp + 500;

                User.setcoin(coin);
                User.setexp(exp);

                SQL = "UPDATE webproject.memberinfo SET Coin = ?, exp = ? WHERE (ID = ?)";
                try {
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setInt(1, coin);
                    pstmt.setInt(2, exp);
                    pstmt.setString(3, userId);
                    result = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("코인&경험치 수급실패");
                    result = 0;
                    e.printStackTrace();
                } finally {
                    try {
                        if(rs != null) rs.close();
                        if(pstmt != null) pstmt.close();
                    } catch (Exception e) {
                        System.out.println("정보업데이트 실패함");
                        e.printStackTrace();
                    }
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return -1;
    }

    // 코인갯수체크
    public int enoughcoin(String userId, int coinneed)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int coin = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                
                User.setcoin(rs.getInt(8));

                coin = User.getcoin();
                if(coin<coinneed) {
                    result = 0;
                } else {
                    result = 1;
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        } 

        System.out.println("회원 정보 출력 실패");
        return -1;
    }


    // 코인 증감
    public int coin(String userId, int coinadd)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int coin = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                User.setmemId(rs.getString(1));
                User.setmemPw(rs.getString(2));
                User.setmemName(rs.getString(3));
                User.setnickName(rs.getString(4));
                User.setgender(rs.getString(5));
                User.sete_mail(rs.getString(6));
                User.setphoneNum(rs.getString(7));
                User.setcoin(rs.getInt(8));
                User.setcash(rs.getInt(9));
                User.setlevel(rs.getInt(10));
                User.setexp(rs.getInt(11));
                User.setregDate(rs.getDate(12));
                User.setAuth(rs.getInt(13));

                coin = User.getcoin();
                coin = coin + coinadd;
                

                SQL = "UPDATE webproject.memberinfo SET Coin = ? WHERE (ID = ?)";
                try {
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setInt(1, coin);
                    pstmt.setString(2, userId);
                    result = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("코인 계산 실패");
                    result = -1;
                    e.printStackTrace();
                } finally {
                    try {
                        if(rs != null) rs.close();
                        if(pstmt != null) pstmt.close();
                    } catch (Exception e) {
                        System.out.println("정보업데이트 실패함");
                        e.printStackTrace();
                    }
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return -1;
    }

    //경험치 체크
    public int enoughexp(String userId, int expneed)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int exp = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                
                User.setexp(rs.getInt(11));

                exp = User.getexp();
                if(exp<expneed) {
                    result = 0;
                } else {
                    result = 1;
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return -1;
    }
    
    // 경험치 증감
    public int exp(String userId, int expadd)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int exp = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                
                User.setexp(rs.getInt(11));

                exp = User.getexp();
                exp = exp + expadd;
                

                SQL = "UPDATE webproject.memberinfo SET exp = ? WHERE (ID = ?)";
                try {
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setInt(1, exp);
                    pstmt.setString(2, userId);
                    result = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("경험치 계산 실패");
                    result = -1;
                    e.printStackTrace();
                } finally {
                    try {
                        if(rs != null) rs.close();
                        if(pstmt != null) pstmt.close();
                    } catch (Exception e) {
                        System.out.println("정보업데이트 실패함");
                        e.printStackTrace();
                    }
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return -1;
    }

    // 레벨업
    public int levelup(String userId)
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM webproject.memberinfo WHERE (ID = ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            int result = 0;
            int level = 0;

            if(rs.next()) {
                // 정보 DTO 에 하나씩 저장
                userDTO User = new userDTO();
                
                User.setlevel(rs.getInt(10));

                level = User.getlevel();
                level = level + 1;
                

                SQL = "UPDATE webproject.memberinfo SET level = ? WHERE (ID = ?)";
                try {
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setInt(1, level);
                    pstmt.setString(2, userId);
                    result = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("레벨 계산 실패");
                    result = -1;
                    e.printStackTrace();
                } finally {
                    try {
                        if(rs != null) rs.close();
                        if(pstmt != null) pstmt.close();
                    } catch (Exception e) {
                        System.out.println("정보업데이트 실패함");
                        e.printStackTrace();
                    }
                }

                return result;

            } else {
                System.out.println("존재하지 않는 아이디");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("정보 업데이트 실패");
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }

        System.out.println("회원 정보 출력 실패");
        return -1;
    }
}
