<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="user.ranklvDAO" %>
<%@ page import="user.ranklvDTO" %>
<%@ page import="user.battleinfoDTO" %>
<%@ page import="user.battleinfoDAO" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>



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
                    <a class="nav-link" href="../main/main.jsp" style="color:#000">메인</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../board/board.jsp">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../inventory/trademarket.jsp">거래소</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../gacha/gacha.jsp">Gacha</a>
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

    <section_m1>
        <table class="table table-bordered table-hover" style="background: #f3f3f3">
            <%
                battleinfoDAO battleinfoDao = new battleinfoDAO();
                ArrayList<battleinfoDTO> list = battleinfoDao.getList();

                
                String[] nickname = new String[3];
                int[] power = new int[3];
                
                if(list.size() < 3)
                {
                    for(int i=0; i < list.size(); i++)
                    {
                        nickname[i] = list.get(i).getNickname();
                        power[i] = list.get(i).getPower();
                    }
                
                    for(int j=list.size(); j < 3; j++)
                    {
                        nickname[j] = "없음";
                        power[j] = 0;
                    }
                } else {
                    for(int i=0; i < 3; i++)
                    {
                        nickname[i] = list.get(i).getNickname();
                        power[i] = list.get(i).getPower();
                    }
                }
                
            %>
            <thead>
                <tr>
                    <th colspan="3">전투력 랭킹</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/1st.jpg"></td>
                    <td style="text-align:center"><%= nickname[0] %></td>
                </tr>
                <tr>
                    
                    <td style="text-align:left;"><%= power[0] %></td>
                </tr>

                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/2nd.jpg"></td>
                    <td style="text-align:center"><%= nickname[1] %></td>
                </tr>
                <tr>
                    
                    <td style="text-align:left;"><%= power[1] %></td>
                </tr>

                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/3rd.jpg"></td>
                    <td style="text-align:center"><%= nickname[2] %></td>
                </tr>
                <tr>
                    
                    <td style="text-align:left;"><%= power[2] %></td>
                </tr>
            </tbody>
        </table>
        <div id="settingitem">
            <a href="../rank/setitem.jsp" ><img src="../asset/css/img/setitem.jpg"></a>
            <div>△아이템 장착하러가기(레벨 3이상 클릭)△</div>
        </div>
    </section_m1>

    <section_m2>
        <table class="table table-bordered table-hover" style="background: #f3f3f3">
            <%
                ranklvDAO ranklvDao = new ranklvDAO();
                ArrayList<ranklvDTO> lis = ranklvDao.getList();
                String[] nicknam = new String[3];
                int[] level = new int[3];
                String[] date = new String[3];
                if (lis.size() < 3){
                    for(int i=0; i < lis.size(); i++)
                    {
                        nicknam[i] = lis.get(i).getNickname();
                        level[i] = lis.get(i).getLV();
                        date[i] = lis.get(i).getaccomDate();
                    }
                    for(int j=list.size(); j < 3; j++)
                    {
                        nicknam[j] = "없음";
                        level[j] = 0;
                        date[j] = "0000-00-00-000000000";
                    }
                } else {
                    for(int i=0; i < 3; i++)
                    {
                        nicknam[i] = lis.get(i).getNickname();
                        level[i] = lis.get(i).getLV();
                        date[i] = lis.get(i).getaccomDate();
                    }
                }
            %>
            <thead>
                <tr>
                    <th colspan="3">레벨 랭킹</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/1st.jpg"></td>
                    <td colspan="2" style="text-align:center"><%= nicknam[0] %></td>
                </tr>
                <tr>
                    <td style="width: 20%">LV: <%= level[0] %></td>
                    <td style="text-align:left; font-size: 0.7em">달성일▽ <br><%= date[0].substring(0,11) + date[0].substring(11, 13) +"시" + date[0].substring(14,16) + "분 " + date[0].substring(17,19) + "초" %></td>
                </tr>

                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/2nd.jpg"></td>
                    <td colspan="2" style="text-align:center"><%= nicknam[1] %></td>
                </tr>
                <tr>
                    <td style="width: 20%">LV: <%= level[1] %></td>
                    <td style="text-align:left; font-size: 0.7em">달성일▽ <br><%= date[1].substring(0,11) + date[1].substring(11, 13) +"시" + date[1].substring(14,16) + "분 " + date[1].substring(17,19) + "초" %></td>
                </tr>

                <tr>
                    <td rowspan="2" style="width: 75px"><img src="../asset/img/3rd.jpg"></td>
                    <td colspan="2" style="text-align:center"><%= nicknam[2] %></td>
                </tr>
                <tr>
                    <td style="width: 20%">LV: <%= level[2] %></td>
                    <td style="text-align:left; font-size: 0.7em">달성일▽ <br><%= date[2].substring(0,11) + date[2].substring(11, 13) +"시" + date[2].substring(14,16) + "분 " + date[2].substring(17,19) + "초" %></td>
                </tr>
            </tbody>
        </table>
    </section_m2>
</div>
</body>
</html>