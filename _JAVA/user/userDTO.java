package user;

public class userDTO {
        // 회원 id
        private String memId;

        // 회원 비밀번호
        private String memPw;
    
        // 회원 이름
        private String memName;
    
        // 회원 닉네임
        private String nickName;
    
        // 성별
        private String gender;
    
        // 이메일
        private String e_mail;
    
        // 전화번호
        private String phoneNum;
        
        // 코인
        private int coin;
    
        // 캐쉬
        private int cash;
    
        // 레벨
        private int level;
    
        // 경험치
        private int exp;
    
        // 가입일
        private int regDate;
    
        public String getmemId() {
            return memId;
        }
    
        public void setmemId(String memId) {
            this.memId = memId;
        }
    
        public String getmemPw() {
            return memPw;
        }
    
        public void setmemPw(String memPw) {
            this.memPw = memPw;
        }
    
        public String getmemName() {
            return memName;
        }
    
        public void setmemName(String memName) {
            this.memName = memName;
        }
    
        public String getnickName() {
            return nickName;
        }
    
        public void setnickName(String nickName) {
            this.nickName = nickName;
        }
    
        public String getgender() {
            return gender;
        }
    
        public void setgender(String gender) {
            this.gender = gender;
        }
    
        public String gete_mail() {
            return e_mail;
        }
    
        public void sete_mail(String e_mail) {
            this.e_mail = e_mail;
        }
    
        public String getphoneNum() {
            return phoneNum;
        }
    
        public void setphoneNum(String phoneNum ) {
            this.phoneNum = phoneNum;
        }
    
        public int getcoin() {
            return coin;
        }
    
        public void setcoin(int coin) {
            this.coin = coin;
        }
    
        public int getcash() {
            return cash;
        }
    
        public void setcash(int cash) {
            this.cash = cash;
        }
    
        public int getlevel() {
            return level;
        }
    
        public void setlevel(int level) {
            this.level = level;
        }
    
        public int getexp() {
            return exp;
        }
    
        public void setexp(int exp) {
            this.exp = exp;
        }
    
        public int getregDate() {
            return regDate;
        }
    
        public void setregDate(int regDate) {
            this.regDate = regDate;
        }
}
