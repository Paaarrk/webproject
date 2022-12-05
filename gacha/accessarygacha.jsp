<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/custom.css">
    <title>메인 페이지</title>
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

        //section함수
        function gacha() {
            var memID = '<%= id%>';

            if(userID = null) {
                console.log("유저 아이디가 로딩되지 않았습니다.");
                return false;
            } else {
                $.ajax({
                    url : "${pageContext.request.contextPath}/userCoinCheck",
                    type : 'POST',
                    data : { userID: memID },
                    success : function(result) {
                        if(result==1){
                            /* 코인까지 빠진상황*/
                            var rand = Math.floor(Math.random() * 100 + 1); //1~100난수생성
                            var getitem = 0;
                            if (rand==1) {
                                getitem = 261;
                            } else if (rand<5) {
                                getitem = 240+Math.floor(Math.random() * 2 + 1); 
                            } else if (rand<10) {
                                getitem = 220+Math.floor(Math.random() * 2 + 1);
                            } else if (rand<21) {
                                getitem = 200+Math.floor(Math.random() * 2 + 1);
                            } else {
                                getitem = 0;
                            }

                            if(getitem>0) {
                                $.ajax({
                                url : "${pageContext.request.contextPath}/gachaServlet",
                                type : 'POST',
                                data : { itemID: getitem, userID: memID },
                                    success : function(result) {
                        
                                        let data = result.split(':');
                                        console.log(data);
                                        
                                        document.getElementById("itemName").innerHTML = data[1];
                                        document.getElementById("itemAtt").innerHTML = data[3];
                                        document.getElementById("itemDef").innerHTML = data[4];
                                        document.getElementById("itemAvd").innerHTML = data[5];
                                        document.getElementById("itemCrit").innerHTML = data[6];
                                    },
                                    error : function() {
                                        alert("서버요청실패");
                                        location.href = '../gacha/gacha.jsp';
                                    }
                                })
                            } else {
                                alert('꽝!! 다음기회에');
                            }
                            
                        } else {
                            alert('코인이 부족합니다!!');
                        }
                    },
                    error : function(){
                    alert("서버요청실패");
                    location.href = '../main/logout.jsp';
                    }
                })
            }
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
                    <a class="nav-link" href="#">거래소</a>
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
            <input class="btn btn-primary" style="float: right; font-family: 'Hanna';" type="button" value="로그아웃" onclick="logout()">
        </div>
    </aside>

    
    <section1>
        <div style="font-family: 'Hanna'; text-align: center; font-size: 30px;">이번의 아이템</div>
        <table class="table table-bordered table-hover" style="margin-top:2%" id="itemlist">
            <thead style="text-align: center; font-family: 'Hanna'">
                <td style="width:100px">등급</td>
                <td style="width:75px">사진</td>
                <td style="width:160px">이름</td>
                <td>확률</td>
            </thead>
            <tbody style="text-align: center">
                <tr>
                    <td style="color: #4ee462; background: #888888"">레전더리</td>
                    <td><img src="../asset/img/261.jpg"></td>
                    <td style="color: #4ee462; background: #888888">거대한공포</td>
                    <td>1%</td>
                </tr>
                <tr>
                    <td style="color: #eef109; background: #888888"">유니크</td>
                    <td><img src="../asset/img/241.jpg"></td>
                    <td style="color: #eef109; background: #888888">가디언엔젤링</td>
                    <td>1.5%</td>
                </tr>
                <tr>
                    <td style="color: #eef109; background: #888888"">유니크</td>
                    <td><img src="../asset/img/242.jpg"></td>
                    <td style="color: #eef109; background: #888888">마력이깃든안대</td>
                    <td>1.5%</td>
                </tr>
            </tbody>
        </table>
        <div style="font-size: 15px">이 외의 자세한 정보는 뽑으면서 알아가요!</div>
        <input class="btn btn-primary" style="float: right; width: 80px" type="button" onclick="gacha()" value="1회뽑기">
    </section1>
    <section2>
        <div style="background: #eeeeee; font-family: Hanna">획득한 아이템 정보</div>
        <div style="background: #000000; height: 230px; color:antiquewhite">
            <table style="border: 0px">
                <th>
                   <td colspan ="2" style="width: 200px; height: 20px">
                        <div id="itemName"></div>
                   </td> 
                </th>
                <tr>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:center">공격력</td>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:left"><div id="itemAtt"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 50px; text-align:center">방어력</td>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:left"><div id="itemDef"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:center">회피율</td>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:left"><div id="itemAvd"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:center">치명타확률</td>
                    <td colspan ="1" style="width: 100px; height: 50px; text-align:left"><div id="itemCrit"></div></td>
                </tr>
            </table>
        </div>
    </section2>
</div>
</body>
</html>