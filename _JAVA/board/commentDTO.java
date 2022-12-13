package board;

public class commentDTO {
    private int commentID;
    private int boardID;
    private String comment;
    private String ID;
    private int commentAvailable;
    private String commentDate;

    private String Nickname;

    //get
    public int getcommentID(){
        return this.commentID;
    }

    public int getboardID(){
        return this.boardID;
    }

    public String getcomment(){
        return this.comment;
    }

    public String getID(){
        return this.ID;
    }

    public int getcommentAvailable(){
        return this.commentAvailable;
    }

    public String getcommentDate(){
        return this.commentDate;
    }


    public String getNickname() {
        return this.Nickname;
    }

    //set
    public void setcommentID(int commentID) {
        this.commentID = commentID;
    }

    public void setboardID(int boardID) {
        this.boardID = boardID;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setcommentAvailable(int commentAvailable) {
        this.commentAvailable = commentAvailable;
    }

    public void setcommentDate(String commentDate) {
        this.commentDate = commentDate;
    }


    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }
}
