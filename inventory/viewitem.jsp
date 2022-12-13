<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="inventory.invDAO" %>
<%@ page import="inventory.invDTO" %>


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

        int invid = 0;
        if (request.getParameter("invID") != null) {
            invid = Integer.parseInt(request.getParameter("invID"));
        }
        if (invid == 0) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('접근 불가능한 아이템 입니다')");
            script.println("location.href = '../inventory/inventory.jsp'");
            script.println("</script>");
        } 
        invDTO invDto = new invDAO().getinvDTO(invid, id);

        if(invDto == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('접근 불가능한 아이템 입니다')");
            script.println("location.href = '../inventory/inventory.jsp'");
            script.println("</script>");
        }

        if(invDto.getitemState() == 2 || invDto.getitemState() == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('접근 불가능한 아이템 입니다')");
            script.println("location.href = '../inventory/inventory.jsp'");
            script.println("</script>");
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

        //section함수
        function goTrade() {
            location.href = '../inventory/trade.jsp?invID=<%= invid %>'
        }


    </script>

    

    <style type="text/css">
        a, a:hover {
            color: #000000;
            text-decoration: none;
        }
    </style>
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
                <li class="nav-item">
                    <a class="nav-link" href="../main/main.jsp">메인</a>
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

    
    <section4>
        <div style="background: #eeeeee; font-family: Hanna; height: 43px; text-align: center; border:#000 solid; font-size: 25px;">아이템 정보</div>
        <div style="background: #000000; height: 300px; color:antiquewhite">
            <table style="border: 0px; width: 200px; height: 300px">
                <tr>
                    <td colspan = '1' style="width:75px; height: 75px"><div id="itemImage"><img src='<%= invDto.getitemUrl() %>'></div></td>
                    <td><div id="itemName">
                        <% if(invDto.getitemRank() == 1) { %> 
                            <div style="color: #4e71e4"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } else if (invDto.getitemRank() == 2) { %>
                            <div style="color: #ad4ee4"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } else if (invDto.getitemRank() == 3) { %>
                            <div style="color: #eef109"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } else if (invDto.getitemRank() == 4) { %>
                            <div style="color: #4ee462"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } else if (invDto.getitemRank() == 5) { %>
                            <div style="color: #fd1d1d"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } else { %>
                            <div style="color: #ffffff"><%= invDto.getitemName() %> (<%= invDto.getforgeLV() %>강)</div>
                        <% } %>
                    </div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">공격력</td>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left"><div id="itemAtt"><%= invDto.getitemAtt() %></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">방어력</td>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left"><div id="itemDef"><%= invDto.getitemDef() %></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:leftr">회피율</td>
                    <td colspan ="1" style="width: 100px; height: 30px; text-align:left"><div id="itemAvd"><%= invDto.getitemAvd() %></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">치명타확률</td>
                    <td colspan ="1" style="width: 100px; height: 30px; text-align:left"><div id="itemCrit"><%= invDto.getitemCrit() %></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">종류</td>
                    <td colspan ="1" style="width: 100px; height: 30px; text-align:left"><div id="itemType">
                        <% if (invDto.getitemID() < 200) { %>
                            무기
                        <% } else { %>
                            악세서리
                        <% } %>     
                    </div></td>
                </tr>
            </table>
        </div>   
    </section4>

    <section5>
        <form name="userWRITE" method="post" action="../forge">
            <table class="table table-striped" style="text-align: center; border: 3px solid #000000; width: 360px;">
                <tbody>
                    
                    <tr>
                        <td colspan="5">아이템 강화성공시 스탯이 증가합니다!</td>
                    </tr>
                    <tr>
                        <td colspan="2">이전 능력치</td><td> </td><td colspan="2">강화 성공시 능력치</td>
                    </tr>
                    <tr>
                        <td style="width: 27%">강화수치</td>
                        <td style="width: 20%"><%= invDto.getforgeLV() %>강</td>
                        <td style="width: 6%">▶</td>
                        <td style="width: 27%">강화수치</td>
                        <td style="width: 20%"><%= invDto.getforgeLV() + 1 %>강</td>
                    </tr>
                    <tr>
                        <td style="width: 27%">공격력</td>
                        <td style="width: 20%"><%= invDto.getitemAtt() %></td>
                        <td style="width: 6%">▶</td>
                        <td style="width: 27%">공격력</td>
                        <td style="width: 20%"><%= invDto.getitemAtt() + 5*invDto.getitemRank() %></td>
                    </tr>
                    <tr>
                        <td style="width: 27%">방어력</td>
                        <td style="width: 20%"><%= invDto.getitemDef() %></td>
                        <td style="width: 6%">▶</td>
                        <td style="width: 27%">방어력</td>
                        <td style="width: 20%"><%= invDto.getitemDef() + 5*invDto.getitemRank() %></td>
                    </tr>
                    <tr>
                        <td style="width: 27%">회피율</td>
                        <td style="width: 20%"><%= invDto.getitemAvd() %></td>
                        <td style="width: 6%">▶</td>
                        <td style="width: 27%">회피율</td>
                        <td style="width: 20%"><%= invDto.getitemAvd() + 2*invDto.getitemRank() %></td>
                    </tr>
                    <tr>
                        <td style="width: 27%">치명타확률</td>
                        <td style="width: 20%"><%= invDto.getitemCrit() %></td>
                        <td style="width: 6%">▶</td>
                        <td style="width: 27%">치명타확률</td>
                        <td style="width: 20%"><%= invDto.getitemCrit() + 1*invDto.getitemRank() %></td>
                    </tr>
                    <tr>
                        <%
                            int sucRate = 100 - invDto.getitemRank()*5 - invDto.getforgeLV()*3 - 25;
                            if(sucRate < 5) {
                                sucRate = 5;
                            }
                            if(invDto.getitemRank() == 5) {
                                //GM장비
                                sucRate = 100;
                            }
                        %>
                        <td style="color: #fd1d1d" colspan="5">성공확률: <%= sucRate %>%, 실패시 파괴</td>
                    </tr>
                </tbody>
            </table>
            <input class="btn btn-primary" style="float: left; font-family: 'Hanna';" type="button" value="거래소에 올리기" onclick="goTrade()">
            <% if (invDto.getforgeLV() < 40) { %>
            <input class="btn btn-primary" style="float: right; font-family: 'Hanna';" type="submit" value="강화하기(코인 -1000)">
            <% } else { %>
            <div style="color: red; float: right">더이상 강화 불가!</div>
            <% } %>

            <input type="hidden" name="invid" value="<%= invDto.getinvID() %>">
            <input type="hidden" name="id" value="<%= id %>">
        </form>
    </section5>
    
    
    
</div>
</body>
</html>