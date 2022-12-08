package inventory;

public class invDTO {
    //item
    private int itemID;
    private String itemName;
    private String itemUrl;
    private int itemAtt;
    private int itemDef;
    private int itemAvd;
    private int itemCrit;
    private int itemRank;

    //inventory
    private int invID;
    private String ID; //userID
    private int forgeLV;
    private String itemDate;
    private int itemState;


    //get
    public int getitemID() {
        return this.itemID;
    }

    public String getitemName() {
        return this.itemName;
    }

    public String getitemUrl() {
        return this.itemUrl;
    }

    public int getitemAtt() {
        return this.itemAtt;
    }

    public int getitemDef() {
        return this.itemDef;
    }

    public int getitemAvd() {
        return this.itemAvd;
    }

    public int getitemCrit() {
        return this.itemCrit;
    }

    public int getitemRank() {
        return this.itemRank;
    }

    //inventory
    public int getinvID() {
        return this.invID;
    }

    public String getID() {
        return this.ID;
    }

    public int getforgeLV() {
        return this.forgeLV;
    }

    public String getitemDate() {
        return this.itemDate;
    }

    public int getitemState() {
        return this.itemState;
    }


    //set
    public void setitemID(int itemID) {
        this.itemID = itemID;
    }

    public void setitemName(String itemName) {
        this.itemName = itemName;
    }

    public void setitemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setitemAtt(int itemAtt) {
        this.itemAtt = itemAtt;
    }

    public void setitemDef(int itemDef) {
        this.itemDef = itemDef;
    }

    public void setitemAvd(int itemAvd) {
        this.itemAvd = itemAvd;
    }

    public void setitemCrit(int itemCrit) {
        this.itemCrit = itemCrit;
    }

    public void setitemRank(int itemRank) {
        this.itemRank = itemRank;
    }

    //inventory
    public void setinvID(int invID) {
        this.invID = invID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setforgeLV(int forgeLV) {
        this.forgeLV = forgeLV;
    }

    public void setitemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public void setitemState(int itemState) {
        this.itemState = itemState;
    }
}
