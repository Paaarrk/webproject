<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="inventory.invDAO" %>
<%@ page import="inventory.invDTO" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/custom.css">
    <title>게시판</title>
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

        //section함수
        
        

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
            <input class="btn btn-primary" style="float: right; font-family: 'Hanna';" type="button" value="로그아웃" onclick="logout()">
            <input class="btn btn-primary" style="float:right; font-family: 'Hanna'" type="button" value="인벤토리" onclick="goinv()">
        </div>
    </aside>

    <section>
                <%
                invDAO invDao = new invDAO();
                ArrayList<invDTO> list = invDao.getList(0, id);
                %>
        <div  style="width: 600px" class="inventory">
            <div style="background-color: #6e6e6e; text-align: center">아이템 목록 (아이템 수: <%= list.size() %>)</div>
            <div style="background-color: #6e6e6e; text-align: left; color: yellow">아이템 정보는 클릭!!</div>
                <%  
                
                if( list.size() != 0 ) {
                    for( int i = 0; i < list.size(); i++) {

                        if(list.get(i).getinvID() != 0) { %>
                            <div class="pic" style="width: 75px; height: 75px; float: left; border: 1px #000000 solid"><a href="../inventory/viewitem.jsp?invID=<%= list.get(i).getinvID() %>"><img style="width:74px; height:74px" src='<%= list.get(i).getitemUrl() %>'><span class="forge" style="text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000; color: yellow"><%= list.get(i).getforgeLV() %></span></a></div>
                <%      } else { %>
                            <div><img src='../asset/img/null.jpg'></div>
                <%      } %>                
                <%
                    }} else {
                %>
                        <div style="color: red">획득한 아이템이 없네요! 아이템을 얻어주세요..!</div>
                <%
                    }
                %>
        </div>  
        
    </section>    
        
<!--
    <section3>
        <div style="background: #eeeeee; font-family: Hanna; height: 43px; text-align: center; border:#000 solid; font-size: 25px;">아이템 정보</div>
        <div style="background: #000000; height: 260px; color:antiquewhite">
            <table style="border: 0px; width: 200px; height: 270px">
                <tr>
                   <td colspan ="2" style="height: 30px">
                        <div id="itemName"></div>
                   </td> 
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">공격력</td>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left"><div id="itemAtt"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">방어력</td>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left"><div id="itemDef"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:leftr">회피율</td>
                    <td colspan ="1" style="width: 100px; height: 30px; text-align:left"><div id="itemAvd"></div></td>
                </tr>
                <tr>
                    <td colspan ="1" style="width: 80px; height: 30px; text-align:left">치명타확률</td>
                    <td colspan ="1" style="width: 100px; height: 30px; text-align:left"><div id="itemCrit"></div></td>
                </tr>
            </table>
        </div>
    </section3>
-->
    
</div>
</body>
</html>