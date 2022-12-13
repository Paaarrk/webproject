<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="inventory.invDAO" %>
<%@ page import="inventory.invDTO" %>
<%@ page import="user.userDAO" %>
<%@ page import="user.userDTO" %>


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
        invDAO invDao = new invDAO();
        invDTO invDto = invDao.getinvDTO(invid, id);

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

        int level = 0;
        userDTO userDto = new userDAO().userinformation(id);
        level = userDto.getlevel();
        if(level < 3) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href = '../main/main.jsp'");
            script.println("alert('레벨이 3보다 낮으시네요. 레벨을 더 올리고 오세요')");
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
            location.href = '../rank/setitem.jsp'
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
        <form name="userWRITE" method="post" action="../equip">
            <table class="table table-striped" style="text-align: center; border: 3px solid #000000; width: 360px;">
                <tbody style="font-size: 15px">
                <%
                
                invDTO nowweapDto = invDao.getweapDTO(id);
                invDTO nowaccDto = invDao.getaccDTO(id);
                int nowweapAtt = 0; int nowweapDef = 0; int nowweapAvd = 0; int nowweapCrit = 0;
                int nowaccAtt = 0; int nowaccDef = 0; int nowaccAvd = 0; int nowaccCrit = 0;
                int HP = level*523+10000;
                int nowweapPower = 0;
                int nowaccPower = 0;
                if(nowweapDto != null) {
                    nowweapPower = nowweapDto.getitemAtt()*52 + nowweapDto.getitemDef()*33 + nowweapDto.getitemAvd()*527 + nowweapDto.getitemCrit()*728;
                    nowweapAtt = nowweapDto.getitemAtt();
                    nowweapDef = nowweapDto.getitemDef();
                    nowweapAvd = nowweapDto.getitemAvd();
                    nowweapCrit = nowweapDto.getitemCrit();
                }
                
                if(nowaccDto != null) {
                    nowaccPower = nowaccDto.getitemAtt()*52 + nowaccDto.getitemDef()*33 + nowaccDto.getitemAvd()*527 + nowaccDto.getitemCrit()*728; 
                    nowaccAtt = nowaccDto.getitemAtt();
                    nowaccDef = nowaccDto.getitemDef();
                    nowaccAvd = nowaccDto.getitemAvd();
                    nowaccCrit = nowaccDto.getitemCrit();
                }


                int nowPower = 3*HP+nowweapPower+nowaccPower;
                int bfPower = nowPower;
                int bfAtt = nowweapAtt + nowaccAtt;
                int bfDef = nowweapDef + nowaccDef;
                int bfAvd = nowweapAvd + nowaccAvd;
                int bfCrit = nowweapCrit + nowaccCrit;

                int afAtt = 0; int afDef = 0; int afAvd = 0; int afCrit = 0; int afPower = 0;
                if(invDto.getitemID() < 200) {
                    afAtt = nowaccAtt + invDto.getitemAtt();
                    afDef = nowaccDef + invDto.getitemDef();
                    afAvd = nowaccAvd + invDto.getitemAvd();
                    afCrit = nowaccCrit + invDto.getitemCrit();
                    afPower = 3*HP + afAtt*52 + afDef*33 + afAvd*527 + afCrit*728;
                } else {
                    afAtt = nowweapAtt + invDto.getitemAtt();
                    afDef = nowweapDef + invDto.getitemDef();
                    afAvd = nowweapAvd + invDto.getitemAvd();
                    afCrit = nowweapCrit + invDto.getitemCrit();
                    afPower = 3*HP + afAtt*52 + afDef*33 + afAvd*527 + afCrit*728;
                }

                
                %>
                    
                    <tr>
                        <td colspan="6">아이템을 장착하면 전투력이 올라갑니다.</td>
                    </tr>
                    <tr>
                        <td colspan="2">현재 전체 능력치</td><td> </td><td colspan="2">장착시 전체 능력치</td><td> </td>
                    </tr>
                    <tr>
                        <td style="width: 28%">전투력</td>
                        <td style="width: 19%"><%= bfPower %></td>
                        <td style="width: 3%">▶</td>
                        <td style="width: 28%">전투력</td>
                        <td style="width: 19%"><%= afPower %></td>
                        <td style="width: 3%">
                            <% if(bfPower>afPower) { %>
                                <span style="color:#fd1d1d">▼</span>
                            <% } else if(bfPower==afPower){ %>
                                <span style="color:#000000">-</span>
                            <% } else { %>
                                <span style="color:#51fd1d">▲</span>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 28%">공격력</td>
                        <td style="width: 19%"><%= bfAtt %></td>
                        <td style="width: 3%">▶</td>
                        <td style="width: 28%">공격력</td>
                        <td style="width: 19%"><%= afAtt %></td>
                        <td style="width: 3%">
                            <% if(bfAtt>afAtt) { %>
                                <span style="color:#fd1d1d">▼</span>
                            <% } else if(bfAtt==afAtt){ %>
                                <span style="color:#000000">-</span>
                            <% } else { %>
                                <span style="color:#51fd1d">▲</span>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 28%">방어력</td>
                        <td style="width: 19%"><%= bfDef %></td>
                        <td style="width: 3%">▶</td>
                        <td style="width: 28%">방어력</td>
                        <td style="width: 19%"><%= afDef %></td>
                        <td style="width: 3%">
                            <% if(bfDef>afDef) { %>
                                <span style="color:#fd1d1d">▼</span>
                            <% } else if(bfDef==afDef){ %>
                                <span style="color:#000000">-</span>
                            <% } else { %>
                                <span style="color:#51fd1d">▲</span>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 28%">회피율</td>
                        <td style="width: 19%"><%= bfAvd %></td>
                        <td style="width: 3%">▶</td>
                        <td style="width: 28%">회피율</td>
                        <td style="width: 19%"><%= afAvd %></td>
                        <td style="width: 3%">
                            <% if(bfAvd>afAvd) { %>
                                <span style="color:#fd1d1d">▼</span>
                            <% } else if(bfAvd==afAvd){ %>
                                <span style="color:#000000">-</span>
                            <% } else { %>
                                <span style="color:#51fd1d">▲</span>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 28%">치명타확률</td>
                        <td style="width: 19%"><%= bfCrit %></td>
                        <td style="width: 3%">▶</td>
                        <td style="width: 28%">치명타확률</td>
                        <td style="width: 19%"><%= afCrit %></td>
                        <td style="width: 3%">
                            <% if(bfCrit>afCrit) { %>
                                <span style="color:#fd1d1d">▼</span>
                            <% } else if(bfCrit==afCrit){ %>
                                <span style="color:#000000">-</span>
                            <% } else { %>
                                <span style="color:#51fd1d">▲</span>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #fd1d1d" colspan="5">TIP: 전투력이 모든 척도가 되진않아요</td>
                    </tr>
                </tbody>
            </table>
            <input class="btn btn-primary" style="float: left; font-family: 'Hanna';" type="button" value="뒤로가기" onclick="goTrade()">
            <input class="btn btn-primary" style="float: right; font-family: 'Hanna';" type="submit" value="장착or해제하기">
            

            <input type="hidden" name="invid" value="<%= invDto.getinvID() %>">
            <input type="hidden" name="id" value="<%= id %>">
        </form>
    </section5>
    
    
    
</div>
</body>
</html>