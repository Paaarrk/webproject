package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class boardDAO {
    private Connection conn;

    public boardDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/webproject?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String dbID = 
            String dbPassword = 
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        String SQL = "SELECT NOW()";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return ""; //데이터 베이스 오류
    }

    //다음 글 확인
    public int getNext() {
        String SQL = "SELECT boardID FROM webproject.boardinfo ORDER BY boardID DESC";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

            return 1; //첫번째 게시물이다

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }

    //글쓰기
    public int write(String boardTitle, String ID, String boardContent) {
        String SQL = "INSERT INTO webproject.boardinfo VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, getNext());
            pstmt.setString(2, boardTitle);
            pstmt.setString(3, ID);
            pstmt.setString(4, getDate());
            pstmt.setString(5, boardContent);
            pstmt.setInt(6, 1);
            pstmt.setInt(7, 0);
            
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }

    //게시글 목록 가져오기
    public ArrayList<boardDTO> getList(int pageNumber) {
        String SQL = "SELECT boardID, boardTitle, webproject.boardinfo.ID, boardDate, boardContent, boardAvailable, boardType, webproject.memberinfo.NickName FROM webproject.boardinfo LEFT JOIN webproject.memberinfo ON webproject.boardinfo.ID = webproject.memberinfo.ID WHERE boardID < ? AND boardAvailable = 1 ORDER BY boardID DESC LIMIT 10";
        ArrayList<boardDTO> list = new ArrayList<boardDTO>();
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                boardDTO boardDto = new boardDTO();

                boardDto.setboardID(rs.getInt(1));
                boardDto.setboardTitle(rs.getString(2));
                boardDto.setID(rs.getString(3));
                boardDto.setboardDate(rs.getString(4));
                boardDto.setboardContent(rs.getString(5));
                boardDto.setboardAvailable(rs.getInt(6));
                boardDto.setboardType(rs.getInt(7));

                //닉네임 추가
                boardDto.setNickname(rs.getString(8));
                list.add(boardDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return list; 
    }

    // 페이징 처리 (10단위로 끊길때 다음페이지 버튼이 생기지 않도록)
    public boolean nextPage(int pageNumber) {
        String SQL = "SELECT * FROM webproject.boardinfo WHERE boardID < ? AND boardAvailable = 1 ORDER BY boardID DESC LIMIT 10";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return false;
    }

    //게시글 보기
    public boardDTO getboardDTO(int boardID) {
        String SQL = "SELECT boardID, boardTitle, webproject.boardinfo.ID, boardDate, boardContent, boardAvailable, boardType, webproject.memberinfo.NickName FROM webproject.boardinfo LEFT JOIN webproject.memberinfo ON webproject.boardinfo.ID = webproject.memberinfo.ID WHERE boardID = ?  ";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, boardID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                boardDTO boardDto = new boardDTO();

                boardDto.setboardID(rs.getInt(1));
                boardDto.setboardTitle(rs.getString(2));
                boardDto.setID(rs.getString(3));
                boardDto.setboardDate(rs.getString(4));
                boardDto.setboardContent(rs.getString(5));
                boardDto.setboardAvailable(rs.getInt(6));
                boardDto.setboardType(rs.getInt(7));

                //닉네임 추가
                boardDto.setNickname(rs.getString(8));
                return boardDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return null; //해당 글이 존재하지 않습니다
    }

    //게시글 수정
    public int update(int boardID, String boardTitle, String boardContent) {
        String SQL = "UPDATE webproject.boardinfo SET boardTitle = ?, boardContent = ? WHERE boardID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, boardTitle);
            pstmt.setString(2, boardContent);
            pstmt.setInt(3, boardID);
            
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }

    public int delete(int boardID) {
        String SQL = "UPDATE webproject.boardinfo SET boardAvailable = 0 WHERE boardID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, boardID);
            
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return -1; //데이터베이스 오류
    }

    
}
