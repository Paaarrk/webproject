function checkValue()
{
    var form = document.userINFO;

    if(!form.userID.value){
        alert("아이디를 입력하세요.");
        return false;
    }

    if(form.userID_DUPLICATION.value != "IDCheck"){
        alert("아이디 중복체크를 해주세요.");
        return false;
    }

    if(!form.userPW.value){
        alert("비밀번호를 입력하세요.");
        return false;
    }

    // 비밀번호와 비밀번호 확인 값 동일한지 확인
    if(form.userPW.value != form.userPWCHK.value){
        alert("비밀번호와 비밀번호 확인이 동일하지 않습니다.");
        return false;
    }
    
    if(!form.userEMAIL.value){
        alert("이메일을 입력하세요.");
        return false;
    }

    if(form.userEMAIL_DUPLICATION.value != "EMAILCheck"){
        alert("이메일 중복체크를 해주세요.");
        return false;
    }

    if(!form.userNICKNAME.value){
        alert("닉네임을 입력하세요.");
        return false;
    }

    if(form.userNICKNAME_DUPLICATION.value != "NICKNAMECheck"){
        alert("닉네임 중복체크를 해주세요.");
        return false;
    }

    if(!form.userNAME.value){
        alert("이름을 입력하세요.");
        return false;
    }

    if(!form.userPHONE.value){
        alert("전화번호를 입력하세요.");
        return false;
    }

    if(isNaN(form.userPHONE.value)){
        alert("전화번호에서 - 제외한 숫자만 입력하세요.");
        return false;
    }
}


// 아이디 재입력 방지
function notIDClick()
{
    document.userINFO.userID_DUPLICATION.value = "IDUncheck";
    document.getElementById("idmsg").innerHTML ="<div style='color: red;'>아이디 중복확인 부탁드립니다</div>";
}

// 아이디중복검사
function regIDCheck()
{
    var userID = $('#userID').val();

    var pat_num = /[0-9]/;
    var pat_eng = /[a-zA-Z]/;
    var pat_spc = /[~!@#$%^&*()_+:{}=,./';"|*\\\-\ ]/;
    var pat_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    if (!userID) {
        document.getElementById("idmsg").innerHTML ="<div style='color: red;'>아이디를 입력해주세요</div>";
        return false;
    } else if (((pat_spc.test(userID)) || (pat_kor.test(userID))) == true) {
        document.getElementById("idmsg").innerHTML ="<div style='color: red;'>한글 및 특수문자는 사용할 수 없습니다</div>";
        return false;
    } else {
        $.ajax({
            url : "../userIDCheck",
            type : 'POST',
            data : { userID: userID },
            success : function(result){
                if(result == '1'){
                    document.userINFO.userID_DUPLICATION.value = "IDCheck";
                    document.getElementById("idmsg").innerHTML ="<div style='color: green;'>사용 가능한 아이디입니다</div>";
                } else{
                    document.userINFO.userID_DUPLICATION.value = "IDUCheck";
                    document.getElementById("idmsg").innerHTML ="<div style='color: red;'>이미 존재하는 아이디입니다</div>";
                } 
            },
            error : function(){
                alert("서버요청실패");
            }
        })
    }
}

// 닉네임 재입력 방지
function notNICKNAMEClick()
{
    document.userINFO.userNICKNAME_DUPLICATION.value = "NICKNAMEUncheck";
    document.getElementById("nickmsg").innerHTML = "<div style='color: red;'>닉네임 중복확인 부탁드립니다</div>";
}
// 닉네임 중복검사
function regNICKNAMECheck()
{
    var nic = document.userINFO.userNICKNAME.value;
    var pat_spc = /[\ ]/;

    if(!nic) {
        document.getElementById("nickmsg").innerHTML = "<div style='color: red;'>사용하실 닉네임을 입력해주세요</div>";   
        return false;        
    } else if((pat_spc.test(nic))==true) {
        document.getElementById("nickmsg").innerHTML = "<div style='color: red;'>공백문자를 사용할 수 없습니다</div>";
        return false;       
    } else {

        var userNICKNAME = $('#userNICK').val();

        $.ajax({
                url : "../userNICKNAMECheck",
                type : 'POST',
                data : { userNICKNAME: userNICKNAME },
                success : function(result){
                    if(result == 1){
                        document.userINFO.userNICKNAME_DUPLICATION.value = "NICKNAMECheck";
                        document.getElementById("nickmsg").innerHTML ="<div style='color: green;'>사용 가능한 닉네임입니다</div>";
                    } else{
                        document.userINFO.userNICKNAME_DUPLICATION.value = "NICKNAMEUncheck";
                        document.getElementById("nickmsg").innerHTML ="<div style='color: red;'>이미 존재하는 닉네임입니다</div>";
                    } 
                },
                error : function(){
                    alert("서버요청실패");
                }
            })
    }
}

// 이메일 재입력 방지
function notEMAILClick()
{
    document.userINFO.userEMAIL_DUPLICATION.value = "EMAILUncheck";
    document.getElementById("emailmsg").innerHTML = "<div style='color: red;'>이메일 중복확인 부탁드립니다</div>";
}

// 이메일 중복검사
function regEMAILCheck()
{
    var userEMAIL = $('#userEMAIL').val();
    var pat_spc = /[@]/;

    if(!userEMAIL) {
        document.getElementById("emailmsg").innerHTML = "<div style='color: red;'>이메일 입력바랍니다</div>"
    } else if((pat_spc.test(userEMAIL)) == false) {
        document.getElementById("emailmsg").innerHTML ="<div style='color: red;'>이메일 형식이 아닙니다</div>";
    } else {
        $.ajax({
        url : "../userEMAILCheck",
        type : 'POST',
        data : { userEMAIL: userEMAIL },
        success : function(result){
            if(result == 1){
                document.userINFO.userEMAIL_DUPLICATION.value = "EMAILCheck";
                document.getElementById("emailmsg").innerHTML ="<div style='color: green;'>사용 가능한 이메일입니다</div>";
            } else{
                document.userINFO.userEMAIL_DUPLICATION.value = "EMAILUncheck";
                document.getElementById("emailmsg").innerHTML ="<div style='color: red;'>이미 가입한 이메일입니다</div>";
            } 
        },
        error : function(){
            alert("서버요청실패");
        }
    })
    }
}
