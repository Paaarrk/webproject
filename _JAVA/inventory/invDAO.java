package inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class invDAO {
    private Connection conn;

    public invDAO() {
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

    //인벤토리에 아이템을 등록할 때 필요한 현재시간 (획득일 등록)
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

    //인벤토리에 아이템 등록시 생성될 PrimaryKey결정
    public int getNext() {
        String SQL = "SELECT invID FROM webproject.inventory ORDER BY invID DESC";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

            return 1; //인벤에 등록된 첫번째 아이템

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }

    // 획득한 아이템의 능력치를 보내줌
    public invDTO getitemInfo(int itemID) {
        String SQL = "SELECT * FROM webproject.item WHERE itemID = ?";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, itemID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                invDTO invDto = new invDTO();

                invDto.setitemID(rs.getInt(1));
                invDto.setitemName(rs.getString(2));
                invDto.setitemUrl(rs.getString(3));
                invDto.setitemAtt(rs.getInt(4));
                invDto.setitemDef(rs.getInt(5));
                invDto.setitemAvd(rs.getInt(6));
                invDto.setitemCrit(rs.getInt(7));
                invDto.setitemRank(rs.getInt(8));

                return invDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return null; //해당 아이템 db에없음
    }


    // 획득한 아이템 저장
    public int setinv(int itemID, String ID, String itemName, int itemAtt, int itemDef, int itemAvd, int itemCrit, int itemRank) {
        String SQL = "INSERT INTO webproject.inventory VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, getNext());
            pstmt.setString(2, ID);
            pstmt.setInt(3, itemID);
            pstmt.setString(4, itemName);
            pstmt.setInt(5, itemAtt);
            pstmt.setInt(6, itemDef);
            pstmt.setInt(7, itemAvd);
            pstmt.setInt(8, itemCrit);
            pstmt.setInt(9, 0);
            pstmt.setString(10, getDate());
            pstmt.setInt(11, 0);
            pstmt.setInt(12, itemRank);
            
            
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }


    /* 인벤토리 관련 */


    // 인벤토리 목록 (나의 인벤토리 목록 - State=0 기본상태/ State=1 거래소에올라감/ State=2 삭제or파괴)
    public ArrayList<invDTO> getList(int pageNumber, int itemState, String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE invID < ? AND itemState = ? AND ID = ? ORDER BY invID DESC LIMIT 10";
        ArrayList<invDTO> list = new ArrayList<invDTO>();
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            pstmt.setInt(2, itemState);
            pstmt.setString(3, ID);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                invDTO invDto = new invDTO();

                invDto.setinvID(rs.getInt(1));
                invDto.setID(rs.getString(2));
                invDto.setitemID(rs.getInt(3));
                invDto.setitemName(rs.getString(4));
                invDto.setitemAtt(rs.getInt(5));
                invDto.setitemDef(rs.getInt(6));
                invDto.setitemAvd(rs.getInt(7));
                invDto.setitemCrit(rs.getInt(8));
                invDto.setforgeLV(rs.getInt(9));
                invDto.setitemDate(rs.getString(10));
                invDto.setitemState(rs.getInt(11));
                invDto.setitemRank(rs.getInt(12));
                invDto.setitemUrl(rs.getString(13));

                list.add(invDto);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return list; 
    }

    // 페이징 처리 (10단위로 끊길때 다음페이지 버튼이 생기지 않도록)
    public boolean nextPage(int pageNumber, int itemState, String ID) {
        String SQL = "SELECT * FROM webproject.inventory WHERE invID < ? AND itemState = ? AND ID = ? ORDER BY invID DESC LIMIT 10";
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            pstmt.setInt(2, itemState);
            pstmt.setString(3, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return false;
    }
}
