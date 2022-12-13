package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ranklvDAO {
    private Connection conn;

    public ranklvDAO() {
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

    //레벨등록 시간 설정
    public String getDate() {
        String SQL = "SELECT NOW()";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
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
        

        return ""; //데이터 베이스 오류
    }

    //작성시 primarykey생성
    public int getNext() {
        String SQL = "SELECT indexlv FROM webproject.rankinglv ORDER BY indexlv DESC";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

            return 1; //첫번째 등록자이다

        } catch (Exception e) {
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

        return -1; //데이터 베이스 오류
    }

    //랭킹 등록
    public int setRank(int LV, String ID) {
        PreparedStatement pstmt = null;
        String SQL = "INSERT INTO webproject.rankinglv VALUES (?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext());
            pstmt.setInt(2, LV);
            pstmt.setString(3, ID);
            pstmt.setString(4, getDate());

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

    //랭킹 업데이트
    public int updateRank(int LV, String ID) {
        PreparedStatement pstmt = null;
        String SQL = "UPDATE webproject.rankinglv SET LV = ?, accomDate = ? WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, LV);
            pstmt.setString(2, getDate());
            pstmt.setString(3, ID);

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

    //랭킹 DTO에 출력
    public ranklvDTO getranklvDTO(String ID) {
        String SQL = "SELECT indexlv, LV, webproject.rankinglv.ID, accomDate, webproject.memberinfo.Nickname FROM webproject.rankinglv LEFT JOIN webproject.memberinfo ON webproject.rankinglv.ID = webproject.memberinfo.ID WHERE webproject.rankinglv.ID = ? ";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ranklvDTO ranklvDto = new ranklvDTO();

                ranklvDto.setindexlv(rs.getInt(1));
                ranklvDto.setLV(rs.getInt(2));
                ranklvDto.setID(rs.getString(3));
                ranklvDto.setaccomDate(rs.getString(4));
                ranklvDto.setNickname(rs.getString(5));
                
                return ranklvDto;
            }
        } catch (Exception e) {
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
        
        return null; 
    }

    //랭킹 리스트
    public ArrayList<ranklvDTO> getList() {
        String SQL = "SELECT indexlv, LV, webproject.rankinglv.ID, accomDate, webproject.memberinfo.NickName FROM webproject.rankinglv LEFT JOIN webproject.memberinfo ON webproject.rankinglv.ID = webproject.memberinfo.ID ORDER BY LV DESC, indexlv ASC LIMIT 10";
        ArrayList<ranklvDTO> list = new ArrayList<ranklvDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ranklvDTO ranklvDto = new ranklvDTO();

                ranklvDto.setindexlv(rs.getInt(1));
                ranklvDto.setLV(rs.getInt(2));
                ranklvDto.setID(rs.getString(3));
                ranklvDto.setaccomDate(rs.getString(4));
                ranklvDto.setNickname(rs.getString(5));
                
                list.add(ranklvDto);
            }
        } catch (Exception e) {
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
        
        return list; 
    }
}
