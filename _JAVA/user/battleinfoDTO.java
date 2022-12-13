package user;

public class battleinfoDTO{
    private String ID;
    private int weapID;
    private int accID;
    private int win;
    private int lose;

    // 부가요소
    private String Nickname;
    private int Power;
    private int hp;
    private int att;
    private int def;
    private int avd;
    private int crit;

    //Get
    public String getID() {
        return this.ID;
    }

    public int getweapID() {
        return this.weapID;
    }

    public int getaccID() {
        return this.accID;
    }

    public int getwin() {
        return this.win;
    }

    public int getgetlose() {
        return this.lose;
    }


    // 부가요소
    public String getNickname() {
        return this.Nickname;
    }

    public int getPower() {
        return this.Power;
    }

    public int gethp() {
        return this.hp;
    }

    public int getatt() {
        return this.att;
    }

    public int getdef() {
        return this.def;
    }

    public int getavd() {
        return this.avd;
    }

    public int getcrit() {
        return this.crit;
    }



    //Set
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setweapID(int weapID) {
        this.weapID = weapID;
    }

    public void setaccID(int accID) {
        this.accID = accID;
    }

    public void setwin(int win) {
        this.win = win;
    }

    public void setlose(int lose) {
        this.lose = lose;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public void setPower(int Power) {
        this.Power = Power;
    }

    public void sethp(int hp) {
        this.hp = hp;
    }

    public void setatt(int att) {
        this.att = att;
    }

    public void setdef(int def) {
        this.def = def;
    }

    public void setavd(int avd) {
        this.avd = avd;
    }

    public void setcrit(int crit) {
        this.crit = crit;
    }
}
