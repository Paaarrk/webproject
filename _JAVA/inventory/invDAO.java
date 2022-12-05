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

                return invDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return null; //해당 아이템 db에없음
    }


    // 획득한 아이템 저장
    public int setinv(int itemID, String ID, String itemName, int itemAtt, int itemDef, int itemAvd, int itemCrit) {
        String SQL = "INSERT INTO webproject.inventory VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            
            
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return -1; //데이터 베이스 오류
    }
}
