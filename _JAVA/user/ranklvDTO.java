package user;

public class ranklvDTO {
    private int indexlv;
    private int LV;
    private String ID;
    private String accomDate;

    private String Nickname;


    public int getindexlv() {
        return this.indexlv;
    }

    public int getLV() {
        return this.LV;
    }

    public String getID() {
        return this.ID;
    }

    public String getaccomDate() {
        return this.accomDate;
    }

    public String getNickname() {
        return this.Nickname;
    }



    public void setindexlv(int indexlv) {
        this.indexlv = indexlv;
    }

    public void setLV(int LV) {
        this.LV = LV;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setaccomDate(String accomDate) {
        this.accomDate = accomDate;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }
}
