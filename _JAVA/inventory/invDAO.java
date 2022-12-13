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
            String dbID = "root";
            String dbPassword = "dlsdyd11!!";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //인벤토리에 아이템을 등록할 때 필요한 현재시간 (획득일 등록)
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

    //인벤토리에 아이템 등록시 생성될 PrimaryKey결정
    public int getNext() {
        String SQL = "SELECT invID FROM webproject.inventory ORDER BY invID DESC";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

            return 1; //인벤에 등록된 첫번째 아이템

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

    // 획득한 아이템의 기본 능력치를 보내줌
    public invDTO getitemInfo(int itemID) {
        String SQL = "SELECT * FROM webproject.item WHERE itemID = ?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
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
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch (Exception e) {
                System.out.println("정보업데이트 실패함");
                e.printStackTrace();
            }
        }
        
        return null; //해당 아이템 db에없음
    }


    // 획득한 아이템 저장
    public int setinv(int itemID, String ID, String itemName, int itemAtt, int itemDef, int itemAvd, int itemCrit, int itemRank) {
        String SQL = "INSERT INTO webproject.inventory VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);

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
            pstmt.setInt(13, 0);
            
            
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


    /* 인벤토리 관련 */


    // 인벤토리 목록 (나의 인벤토리 목록 - State=0 기본상태/ State=1 거래소에올라감/ State=2 삭제or파괴)
    public ArrayList<invDTO> getList(int itemState, String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE itemState = ? AND ID = ? ORDER BY invID DESC";
        ArrayList<invDTO> list = new ArrayList<invDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, itemState);
            pstmt.setString(2, ID);

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



    // 거래소리스트 페이징 처리 (10단위로 끊길때 다음페이지 버튼이 생기지 않도록)
    public boolean nextPage(int pageNumber, int itemState) {
        String SQL = "SELECT * FROM webproject.inventory WHERE invID < ? AND itemState = ? ORDER BY invID DESC LIMIT 10";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, selectNum(pageNumber));
            pstmt.setInt(2, itemState);
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

    // 인벤토리에서 선택한 아이템을 보여줌
    public invDTO getinvDTO(int invID, String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE invID = ? AND ID = ?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, invID);
            pstmt.setString(2,ID);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
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

                return invDto;
            } else {
                return null;
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
        
        return null; //해당 아이템이 존재하지않음
    }

    // 강화하기
    public int forgeupdate(int invID, String userID, int upforgeLV, int upitemAtt, int upitemDef, int upitemAvd, int upitemCrit, int SuccessorFail) {
        PreparedStatement pstmt = null;
        if (SuccessorFail == 0) {
            String SQL = "UPDATE webproject.inventory SET itemAtt = ?, itemDef = ?, itemAvd = ?, itemCrit = ?, forgeLV = ?, itemState = ? WHERE invID = ? AND ID = ?";
            
            try {
                pstmt = conn.prepareStatement(SQL);

                pstmt.setInt(1, upitemAtt);
                pstmt.setInt(2, upitemDef);
                pstmt.setInt(3, upitemAvd);
                pstmt.setInt(4, upitemCrit);
                pstmt.setInt(5, upforgeLV);
                pstmt.setInt(6, SuccessorFail);
                pstmt.setInt(7, invID);
                pstmt.setString(8, userID);
            
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
        } else {
            String SQL = "DELETE FROM webproject.inventory WHERE invID = ? AND ID = ?";
            
            try {
                pstmt = conn.prepareStatement(SQL);

                pstmt.setInt(1, invID);
                pstmt.setString(2, userID);

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
        } 

        return -1; //데이터 베이스 오류
    }

    // 아이템 거래소 관련 - State: 0-인벤토리 / 1-거래중
    public int tradeFunc(int invID, String ID, int itemPrice, int itemState) {
        String SQL = "UPDATE webproject.inventory SET itemState = ?, itemPrice = ? WHERE invID = ? AND ID = ?";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, itemState);
            pstmt.setInt(2, itemPrice);
            pstmt.setInt(3, invID);
            pstmt.setString(4, ID);

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

    // 아이템 구매
    public int tradePurchase(int invID, String ID) {
        String SQL = "UPDATE webproject.inventory SET itemState = 0, itemPrice = 0, ID = ? WHERE invID = ? ";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, ID);
            pstmt.setInt(2, invID);

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

    // 이전 페이지 마지막 invid숫자
    public int selectNum(int pageNumber) {
        if(pageNumber == 1) {
            invDAO invDao = new invDAO();
            return invDao.getNext();
        } else {
            String SQL = "SELECT invID FROM (SELECT invID FROM webproject.inventory WHERE itemState=1 ORDER BY invID DESC LIMIT ?) AS selection ORDER BY selection.invID ASC LIMIT 1";
            ResultSet rs = null;
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setInt(1, (pageNumber-1) * 10);
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
    // 거래소 등록품 목록 
    public ArrayList<invDTO> gettradeList(int pageNumber, int itemState) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl, itemPrice FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE invID < ? AND itemState = ? ORDER BY invID DESC LIMIT 10";
        ArrayList<invDTO> list = new ArrayList<invDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, selectNum(pageNumber));
            pstmt.setInt(2, itemState);
            

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
                invDto.setitemPrice(rs.getInt(14));

                list.add(invDto);

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

    // 거래소 자세히 보기
    public invDTO gettradeDTO(int invID, int itemState) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl, itemPrice FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE invID = ? AND itemState = ?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, invID);
            pstmt.setInt(2,itemState);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
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
                invDto.setitemPrice(rs.getInt(14));

                return invDto;
            } else {
                invDTO invDto = new invDTO();
                
                invDto.setitemID(0);
                return invDto;
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
        
        return null; //해당 아이템이 존재하지않음
    }

    //내가 올린 아이템목록
    public ArrayList<invDTO> mygettradeList(int pageNumber, int itemState, String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl, itemPrice FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE invID < ? AND itemState = ? AND ID = ? ORDER BY invID DESC LIMIT 10";
        ArrayList<invDTO> list = new ArrayList<invDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, selectNum(pageNumber));
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
                invDto.setitemPrice(rs.getInt(14));

                list.add(invDto);

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

    // 나의 이전 페이지 마지막 invid숫자
    public int myselectNum(int pageNumber, String ID) {
        if(pageNumber == 1) {
            invDAO invDao = new invDAO();
            return invDao.getNext();
        } else {
            String SQL = "SELECT invID FROM (SELECT invID FROM webproject.inventory WHERE itemState=1 AND ID = ? ORDER BY invID DESC LIMIT ?) AS selection ORDER BY selection.invID ASC LIMIT 1";
            ResultSet rs = null;
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, ID);
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

    //나의 페이징처리
    public boolean mynextPage(int pageNumber, int itemState, String ID) {
        String SQL = "SELECT * FROM webproject.inventory WHERE invID < ? AND itemState = ? AND ID = ? ORDER BY invID DESC LIMIT 10";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, myselectNum(pageNumber, ID));
            pstmt.setInt(2, itemState);
            pstmt.setString(3, ID);
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
//장착관련
    // 아이템 장착 or 해제하기ㅣ
    public int controlitem(String ID, int invID, int itemState) {
        String SQL = "UPDATE webproject.inventory SET itemState = ? WHERE invID = ? AND ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, itemState);
            pstmt.setInt(2, invID);
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

    // 장착한 무기아이템 DTO
    public invDTO getweapDTO(String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE ID = ? AND itemState = 3 AND webproject.inventory.itemID < 200";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                invDTO weapDto = new invDTO();

                weapDto.setinvID(rs.getInt(1));
                weapDto.setID(rs.getString(2));
                weapDto.setitemID(rs.getInt(3));
                weapDto.setitemName(rs.getString(4));
                weapDto.setitemAtt(rs.getInt(5));
                weapDto.setitemDef(rs.getInt(6));
                weapDto.setitemAvd(rs.getInt(7));
                weapDto.setitemCrit(rs.getInt(8));
                weapDto.setforgeLV(rs.getInt(9));
                weapDto.setitemDate(rs.getString(10));
                weapDto.setitemState(rs.getInt(11));
                weapDto.setitemRank(rs.getInt(12));
                weapDto.setitemUrl(rs.getString(13));

                return weapDto;
            } else {
                return null;
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


    // 장착한 악세서리아이템 DTO
    public invDTO getaccDTO(String ID) {
        String SQL = "SELECT invID, ID, webproject.inventory.itemID as itemID, webproject.inventory.itemName,  webproject.inventory.itemAtt,  webproject.inventory.itemDef,  webproject.inventory.itemAvd,  webproject.inventory.itemCrit, forgeLV, itemDate, itemState,  webproject.inventory.itemRank, webproject.item.itemUrl FROM webproject.inventory LEFT JOIN webproject.item ON webproject.inventory.itemID = webproject.item.itemID WHERE ID = ? AND itemState = 3 AND webproject.inventory.itemID > 200";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                invDTO accDto = new invDTO();

                accDto.setinvID(rs.getInt(1));
                accDto.setID(rs.getString(2));
                accDto.setitemID(rs.getInt(3));
                accDto.setitemName(rs.getString(4));
                accDto.setitemAtt(rs.getInt(5));
                accDto.setitemDef(rs.getInt(6));
                accDto.setitemAvd(rs.getInt(7));
                accDto.setitemCrit(rs.getInt(8));
                accDto.setforgeLV(rs.getInt(9));
                accDto.setitemDate(rs.getString(10));
                accDto.setitemState(rs.getInt(11));
                accDto.setitemRank(rs.getInt(12));
                accDto.setitemUrl(rs.getString(13));

                return accDto;
            } else {
                return null;
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

    
}
