package board;

public class boardDTO {
    private int boardID;
    private String boardTitle;
    private String ID;
    private String boardDate;
    private String boardContent;
    private int boardAvailable;
    private int boardType;

    private String Nickname; //JOIN할때만 씁니다
    public String getNickname() {
        return this.Nickname;
    }
    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }


    public int getboardID() {
        return this.boardID;
    }
    public String getboardTitle() {
        return this.boardTitle;
    }
    public String getID() {
        return this.ID;
    }
    public String getboardDate() {
        return this.boardDate;
    }
    public String getboardContent() {
        return this.boardContent;
    }
    public int getboardAvailable() {
        return this.boardAvailable;
    }
    public int getboardType() {
        return this.boardType;
    }

    public void setboardID(int boardID) {
        this.boardID=boardID;
    }
    public void setboardTitle(String boardTitle) {
        this.boardTitle=boardTitle;
    }
    public void setID(String ID) {
        this.ID=ID;
    }
    public void setboardDate(String boardDate) {
        this.boardDate=boardDate;
    }
    public void setboardContent(String boardContent) {
        this.boardContent=boardContent;
    }
    public void setboardAvailable(int boardAvailable) {
        this.boardAvailable=boardAvailable;
    }
    public void setboardType(int boardType) {
        this.boardType=boardType;
    }
}
