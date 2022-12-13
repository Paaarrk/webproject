package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class commentDAO {
    private Connection conn;

    public commentDAO() {
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

    //작성 시간 설정
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
        String SQL = "SELECT commentID FROM webproject.commentinfo ORDER BY commentID DESC";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

            return 1; //첫번째 게시물이다

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

    // 페이징 처리 (10단위로 끊길때 다음페이지 버튼이 생기지 않도록)
    public boolean nextPage(int pageNumber, int boardID) {
        String SQL = "SELECT * FROM webproject.commentinfo WHERE commentID < ? AND commentAvailable = 1 AND boardID = ? ORDER BY commentID DESC LIMIT 10";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, selectNum(pageNumber, boardID));
            pstmt.setInt(2, boardID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
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
        
        return false;
    }

    // 이전 페이지 마지막 commentid숫자
    public int selectNum(int pageNumber, int boardID) {
        if(pageNumber == 1) {
            boardDAO boardDao = new boardDAO();
            return boardDao.getNext();
        } else {
            String SQL = "SELECT commentID FROM (SELECT commentID FROM webproject.commentinfo WHERE commentAvailable = 1 AND boardID = ? ORDER BY commentID DESC LIMIT ?) AS selection ORDER BY selection.commentID ASC LIMIT 1";
            ResultSet rs = null;
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setInt(1, boardID);
                pstmt.setInt(2, (pageNumber-1) * 10);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return -1;
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
            return -1;
        }
    }

    //댓글 DTO에 넣기
    public commentDTO getcommentDTO(int commentID) {
        String SQL = "SELECT commentID, boardID, webproject.commentinfo.ID, comment, commentAvailable, commentDate, webproject.memberinfo.NickName FROM webproject.commentinfo LEFT JOIN webproject.memberinfo ON webproject.commentinfo.ID = webproject.memberinfo.ID WHERE commentID = ?  ";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, commentID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                commentDTO commentDto = new commentDTO();

                commentDto.setcommentID(rs.getInt(1));
                commentDto.setboardID(rs.getInt(2));
                commentDto.setID(rs.getString(3));
                commentDto.setcomment(rs.getString(4));
                commentDto.setcommentAvailable(rs.getInt(5));
                commentDto.setcommentDate(rs.getString(6));
                

                //닉네임 추가
                commentDto.setNickname(rs.getString(7));
                return commentDto;
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
        
        return null; //해당 글이 존재하지 않습니다
    }

    //댓글리스트
    public ArrayList<commentDTO> getList(int pageNumber, int boardID) {
        String SQL = "SELECT commentID, boardID, webproject.commentinfo.ID, comment, commentAvailable, commentDate, webproject.memberinfo.NickName FROM webproject.commentinfo LEFT JOIN webproject.memberinfo ON webproject.commentinfo.ID = webproject.memberinfo.ID WHERE commentID < ? AND commentAvailable = 1 AND boardID = ? ORDER BY commentID DESC LIMIT 10";
        ArrayList<commentDTO> list = new ArrayList<commentDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, selectNum(pageNumber, boardID));
            pstmt.setInt(2, boardID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                commentDTO commentDto = new commentDTO();

                commentDto.setcommentID(rs.getInt(1));
                commentDto.setboardID(rs.getInt(2));
                commentDto.setID(rs.getString(3));
                commentDto.setcomment(rs.getString(4));
                commentDto.setcommentAvailable(rs.getInt(5));
                commentDto.setcommentDate(rs.getString(6));
                commentDto.setNickname(rs.getString(7));
                list.add(commentDto);
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

    //댓글 작성
    public int write(int boardID, String comment, String ID) {
        String SQL = "INSERT INTO webproject.commentinfo VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, getNext());
            pstmt.setInt(2, boardID);
            pstmt.setString(3, ID);
            pstmt.setString(4, comment);
            pstmt.setInt(5, 1);
            pstmt.setString(6, getDate());
            
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

        return -1; //데이터 베이스 오류
    }

    //댓글 수정
    public int update(String comment, int commentID) {
        String SQL = "UPDATE webproject.commentinfo SET comment = ? WHERE commentID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, comment);
            pstmt.setInt(2, commentID);
            
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

        return -1; //데이터 베이스 오류
    }

    //댓글 삭제
    public int delete(int commentID) {
        String SQL = "UPDATE webproject.commentinfo SET commentAvailable = 0 WHERE commentID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, commentID);
            
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
}
