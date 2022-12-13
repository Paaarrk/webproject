package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import inventory.invDAO;
import inventory.invDTO;


public class battleinfoDAO {
    private Connection conn;

    public battleinfoDAO() {
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

    // 등록
    public int setbattleinfofirst(String ID) {
        PreparedStatement pstmt = null;
        String SQL = "INSERT INTO webproject.battleinfo VALUES (?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);

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

    // 정보 불러오기(단순정보)
    public battleinfoDTO battleinfo(String ID){
        String SQL = "SELECT ID, weapID, accID, win, lose FROM webproject.battleinfo WHERE ID = ? ";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                battleinfoDTO battleinfo = new battleinfoDTO();

                battleinfo.setID(rs.getString(1));
                battleinfo.setweapID(rs.getInt(2));
                battleinfo.setaccID(rs.getInt(3));
                battleinfo.setwin(rs.getInt(4));
                battleinfo.setlose(rs.getInt(5));
                
                return battleinfo;
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

    // 정보 불러오기(랭킹위한 전투력 계산)
    public battleinfoDTO forRank(String ID){
        String SQL = "SELECT ID, weapID, accID, win, lose FROM webproject.battleinfo WHERE ID = ? ";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                battleinfoDTO battleinfo = new battleinfoDTO();
                invDTO weap = new invDAO().getweapDTO(ID);
                invDTO acc = new invDAO().getaccDTO(ID);

                String nickname = new userDAO().userinformation(ID).getnickName();

                int hp = new userDAO().userinformation(ID).getlevel() * 523 + 10000;
                int att = weap.getitemAtt() + acc.getitemAtt();
                int def = weap.getitemDef() + acc.getitemDef();
                int avd = weap.getitemAvd() + acc.getitemAvd();
                int crit = weap.getitemCrit() + acc.getitemCrit();
                int power = hp*3 + att*52 + def*33 + avd*527 + crit*728;

                battleinfo.setID(rs.getString(1));
                battleinfo.setweapID(rs.getInt(2));
                battleinfo.setaccID(rs.getInt(3));
                battleinfo.setwin(rs.getInt(4));
                battleinfo.setlose(rs.getInt(5));

                battleinfo.setNickname(nickname);
                battleinfo.setPower(power);
                battleinfo.sethp(hp);
                battleinfo.setatt(att);
                battleinfo.setdef(def);
                battleinfo.setavd(avd);
                battleinfo.setcrit(crit);

                
                return battleinfo;
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
    public ArrayList<battleinfoDTO> getList() {
        String SQL = "SELECT ID FROM webproject.battleinfo WHERE weapID > 0 AND accID > 0";
        ArrayList<battleinfoDTO> list = new ArrayList<battleinfoDTO>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                battleinfoDTO battleinfo = new battleinfoDAO().forRank(rs.getString(1));

                list.add(battleinfo);
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
        
        Comparator<battleinfoDTO> cp = new Comparator<battleinfoDTO>() {
            @Override
            public int compare(battleinfoDTO data1, battleinfoDTO data2){
                int a = data1.getPower();
                int b = data2.getPower();
                if(a > b) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

        Collections.sort(list,cp);

        return list; 
    }

    // 무기 장착 (weapID = invID)
    public int equipWeap(String ID, int weapID) {
        PreparedStatement pstmt = null;
        String SQL = "UPDATE webproject.battleinfo SET weapID = ? WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, weapID);
            pstmt.setString(2, ID);

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

    // 악세서리 장착 (accID = invID)
    public int equipAcc(String ID, int accID) {
        PreparedStatement pstmt = null;
        String SQL = "UPDATE webproject.battleinfo SET accID = ? WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, accID);
            pstmt.setString(2, ID);

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
