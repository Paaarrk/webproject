<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../asset/css/bootstrap.css">
    <link rel="stylesheet" href="../asset/css/custom.css">
    <title>-Login-</title>
    <script src="../asset/js/jquery-3.6.1.min.js"></script>
    <script src="../asset/js/bootstrap.js"></script>

    <script>
        // 아이디 비밀번호 입력확인
        function checkValue() {
            var form = document.userLOGIN;
            if(!form.userID.value) {
                document.getElementById("msg").innerHTML = "<div style='color: red;'><h5>아이디를 입력해주세요</h5></div>";
                return false;
            }
            if(!form.userPW.value) {
                document.getElementById("msg").innerHTML = "<div style='color: red;'><h5>비밀번호를 입력해주세요</h5></div>";
                return false;
            }
        }

        function goRegfrom() {
            window.open('../regform/regForm.jsp', 'regForm', 'width=1000, height=1200, center, menubar=no, location=no, scrollbars=no,resizable=no')
        }

        
    </script>
</head>
<body>
    <form method="post" action="../userLogin" name="userLOGIN" onsubmit="return checkValue()">
        <table id="login" class="table table-bordered table-hover">
            <thead>
                <th colspan="3"><h4>LOGIN</h4></th>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 130px;"><h5>아이디</h5></td>
                    <td colspan="2"><input class="form-control" type="text" id="userID" name="userID" maxlength="20"></td>
                </tr>
                <tr>
                    <td style="width: 130px;"><h5>비밀번호</h5></td>
                    <td colspan="2"><input class="form-control" type="password" id="userPW" name="userPW" maxlength="20"></td>
                </tr>

                <tr>
                    <td style="width: 330px; text-align: right;" colspan="2">
                        <div id="msg"><h5><span style="color: #00c5f7">놀이터</span>에 오신것을 환영합니다!</h5></div>
                    </td>
                    <td colspan="1" style="text-align: center;">
                        <input class="btn btn-primary pull-right" type="button" value="회원가입" onclick="goRegfrom()">
                        <input class="btn btn-primary pull-right" type="submit" value="로그인">
                    </td>
                </tr>
            </tbody>
            <tfooter>
                <tr>
                    
                    
                </tr>
            </tfooter>
        </table>
    </form>
</body>
</html>