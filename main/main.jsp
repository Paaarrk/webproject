<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/custom.css">
    <title>가입 성공!!</title>
    <script src="${pageContext.request.contextPath}/asset/js/jquery-3.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/bootstrap.js"></script>
    <%
        String id = (String)request.getAttribute("userID");
    %>
    <script>
        
        
        // 화면에 유저정보를 출력하기위해 데이터 받아오기
        window.onload = function loadinfo() {
            var memID = '<%= id%>';

            if(userID = null) {
                console.log("유저 아이디가 로딩되지 않았습니다.");
                return false;
            } else {
                console.log("유저 "+memID+"가 로그인하였습니다");
                $.ajax({
                    url : "${pageContext.request.contextPath}/loadInfo",
                    type : 'POST',
                    data : { userID: memID },
                    success : function(result) {
                        
                        let data = result.split(':');
                        console.log(data);
                        document.getElementById("userNICKNAME").innerHTML = data[2];
                        document.getElementById("userGENDER").innerHTML = data[4];
                        document.getElementById("userREGDATE").innerHTML = data[10];
                        document.getElementById("userLEVEL").innerHTML = data[8];
                        document.getElementById("userEXP").innerHTML = data[9];
                        document.getElementById("userCOIN").innerHTML = data[6];
                    },
                    error : function(){
                    alert("서버요청실패");
                    }
                })
            }
        }         
    </script>
</head>

<body>
    <div><form id="information">
        <table id="login">
            <thead>
                <th colspan="3">
                    <%= id%>이 로그인에 성공하셨습니다!
                </th>
            </thead>
            <tbody>
                <tr>
                    <td>
                    닉네임: <div id="userNICKNAME"></div>
                </td>
                </tr>
                <tr>
                    <td>
                    성별: <div id="userGENDER"></div>
                </td>
                </tr>
                <tr>
                    <td>
                    가입일자: <div id="userREGDATE"></div>
                </td>
                </tr>
                <tr>
                    <td>
                    LEVEL: <div id="userLEVEL"></div>
                </td>
                </tr>
                <tr>
                    <td>
                    EXP: <div id="userEXP"></div>
                </td>
                </tr>
                <tr>
                    <td>
                    COIN: <div id="userCOIN"></div>
                </td>
                </tr>
                <tr>
                    <td width="180px" colspan="2">

                    </td>
                    <td>
                        <input class="btn btn-primary" type="button" value="닫기" onclick="self.close()">
                    </td>
                </tr>
            </tbody>
        </table>
    </form></div>
</body>
</html>