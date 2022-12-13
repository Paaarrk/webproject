<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/custom.css">
    <title>놀이터</title>
    <script src="${pageContext.request.contextPath}/asset/js/jquery-3.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/bootstrap.js"></script>
    <%
        String id = null;
        if (session.getAttribute("userID") != null) {
            id = (String) session.getAttribute("userID");
        }
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
                    location.href = '../main/logout.jsp';
                    }
                })
            }
        } 
        
        function logout() {
            location.href = '../main/logout.jsp';
        }

        function goinv() {
            location.href = '../inventory/inventory.jsp';
        }

        function levelup() {
            location.href = '../rank/levelup.jsp';
        }

        //section 함수들
        //weapongacha
        function weapongacha() {
            document.getElementById("weapongacha").innerHTML = '<div style="color: green; text-align: center; font-size: 30px;">무기 가챠하러 가기!</div>';
        }
        function weapongacha_out() {
            document.getElementById("weapongacha").innerHTML = '<div style="color: black; text-align: center; font-size: 30px;">당신도 행운의 주인공!</div>';
        }
        //accessarygacha
        function accessarygacha() {
            document.getElementById("accessarygacha").innerHTML = '<div style="color: green; text-align: center; font-size: 30px;">악세서리 가챠하러 가기!</div>';
        }
        function accessarygacha_out() {
            document.getElementById("accessarygacha").innerHTML = '<div style="color: black; text-align: center; font-size: 30px;">당신도 행운의 주인공!</div>';
        }
    </script>
</head>

<body>
<div class="maincontainer">
    <header style="text-align: center">
        <div style="height: 200px; text-align: center; font-size: 4em; color:aqua; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;" >
        <br>놀이터에 오신것을 환영합니다 ^-^/</div>
    </header>

    
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="../main/main.jsp">놀이터에 오신것을 환영합니다</a>
        <button class="navbar-toggler" type="button" aria-controls="header_nav"
            data-toggle="collapse" data-target="#header_nav"
            aria expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="header_nav">
            <ul class="nav navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="../main/main.jsp" >메인</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../board/board.jsp">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../inventory/trademarket.jsp">거래소</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../gacha/gacha.jsp" style="color:#000">Gacha</a>
                </li>
            </ul>
        </div>
    </nav>
    
    
    <aside>
        <div id="information" style="width: 400px; height:200px">
            <table class="table table-bordered table-hover" style="margin-top:0%" id="Userinfo" >
                <thead>
                    <th colspan="4" style="font-family: 'Hanna';">
                        <%= id%>의 회원정보
                    </th>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">닉네임</td> <td colspan="1" style="width: 130px"><div id="userNICKNAME"></td></div>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">성별</td> <td colspan="1" style="width: 130px"><div id="userGENDER"></td></div>
                    </tr>
    
                    <tr>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">가입일자</td> <td colspan="1" style="width: 130px"><div id="userREGDATE"></td></div>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">코인</td> <td colspan="1" style="width: 130px"><div id="userCOIN"></td></div>
                    </tr>
    
                    <tr>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">레벨</td> <td colspan="1" style="width: 130px"><div id="userLEVEL"></td></div>
                        <td colspan="1" style="width: 70px; font-family: 'Hanna';">경험치</td> <td colspan="1" style="width: 130px"><div id="userEXP"></td></div>
                    </tr>
                </tbody>
            </table>
            <input class="btn btn-primary" style="float: left; font-family: 'Hanna';" type="button" value="레벨업" onclick="levelup()">
            <input class="btn btn-primary" style="float: right; font-family: 'Hanna';" type="button" value="로그아웃" onclick="logout()">
            <input class="btn btn-primary" style="float:right; font-family: 'Hanna'" type="button" value="인벤토리" onclick="goinv()">
        </div>
    </aside>

    <section1>
        <a href="../gacha/weapongacha.jsp" height = "150" width = "400" onmouseover="weapongacha()" onmouseout="weapongacha_out()"><img src="../asset/css/img/weapongacha.jpg"></a>

        <br><div id="weapongacha" style="color: black; text-align: center; font-size: 30px;">당신도 행운의 주인공!</div><br>
        <a href="../gacha/accessarygacha.jsp" height = "165" width = "400" onmouseover="accessarygacha()" onmouseout="accessarygacha_out()"><img src="../asset/css/img/accessarygacha.jpg"></a>
        <br><div id="accessarygacha" style="color: black; text-align: center; font-size: 30px;">당신도 행운의 주인공!</div>
    </section1>

    <section2>
        <div id="gachainfo" style="width: 190px">
            <table class="table table-bordered table-hover" style="margin-top: 0%; margin-left: 5px; background: #eeeeee" id="Gachainfo">
                <theaad>
                    <th colspan="2" style="background: #dddddd">가챠 확률</th>
                </theaad>
                <tbody>
                    <tr>
                        <td style="width: 80px; color: #4ee462; background: #dddddd">레전더리</td>
                        <td style="width: 110px">1%</td>
                    </tr>
                    <tr>
                        <td style="width: 80px; color: #eef109; background: #dddddd">유니크</td>
                        <td style="width: 110px">3%</td>
                    </tr>
                    <tr>
                        <td style="width: 80px; color: #ad4ee4; background: #dddddd">에픽</td>
                        <td style="width: 110px">5%</td>
                    </tr>
                    <tr>
                        <td style="width: 80px; color: #4e71e4; background: #dddddd">노멀</td>
                        <td style="width: 110px">11%</td>
                    </tr>
                    <tr>
                        <td style="width: 80px; color: #000000; background: #dddddd">꽝</td>
                        <td style="width: 110px">80%</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="color: #ff0000; background: #dddddd">가챠가격: 1회 3000코인</td>
                    </tr>
                </tbody>

            </table>
        </div>
    </section2>
</div>
</body>
</html>