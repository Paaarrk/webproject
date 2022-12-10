<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
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

        int pagenumber = 1;
        if(request.getParameter("pageNumber") != null) {
            pagenumber = Integer.parseInt(request.getParameter("pageNumber"));
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

        //section 함수
        
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
                    <a class="nav-link" href="../inventory/trademarket.jsp" style="color:#000">거래소</a>
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
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th style="width: 75px; background-color: #6e6e6e; text-align: center">이미지</th>
                        <th style="background-color: #6e6e6e; text-align: center; width: 150px">아이템 이름</th>
                        <th style="background-color: #6e6e6e; text-align: center; width: 100px">아이템 분류</th>
                        <th style="background-color: #6e6e6e; text-align: center; width: 150px">가격</th>
                        <th style="background-color: #6e6e6e; text-align: center;">자세히 보기</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        invDAO invDao = new invDAO();
                        ArrayList<invDTO> list = invDao.gettradeList(pagenumber, 1);
                        if(list.size()>0) {
                            for(int i = 0; i < list.size(); i++) {
                    %>
                    <tr style="width: 75px">
                        <td style="height: 75px;"><div style="width: 75px; height: 75px"><img src='<%= list.get(i).getitemUrl() %>'></div></td>
                    
                    
                        
                        <% switch(list.get(i).getitemRank()) {
                            case 1:
                        %>
                            <td style="color: #4e71e4"><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                            case 2:
                        %>
                            <td style="color: #ad4ee4"><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                            case 3:
                        %>
                            <td style="color: #eef109; text-shadow:1px 1px 1px #000"><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                            case 4:
                        %>
                            <td style="color: #4ee462"><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                            case 5:
                        %>
                            <td style="color: #fd1d1d"><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                            default:
                        %>
                            <td><a href="#"></a><%= list.get(i).getitemName() %> (<%= list.get(i).getforgeLV() %>강)</a></td>
                        <%  break;
                        }
                        %>
                        
                        
                        
                        <% if(list.get(i).getitemID() < 200) {
                        %>
                        <td>무기</td>
                        <%
                        } else {
                        %>
                        <td>악세서리</td>
                        <%
                        }
                        %>
                        <td style="width: 150px; height: 95px; float: right"><%= list.get(i).getitemPrice() %> 코인</td>
                        <td><a href="../inventory/viewTradeitem.jsp?invID=<%= list.get(i).getinvID() %>">☞ 성능 확인하기</a></td>
                    </tr>
                    <%
                        } } else {
                    %>
                            <tr>
                                <td colspan="4" style="color: red">거래소에 올라온 아이템이 없어요..!</td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="1">페이지: <%= pagenumber %></td>
                        <td colspan="4" style="text-align: right">
                            <%
                                if(pagenumber !=1) {
                            %>
                                    <a style="width: 100px;" href="../inventory/trademarket.jsp?pageNumber=<%= pagenumber - 1 %>" class="btn btn-success btn-arrow-left">이전</a>
                            <%
                                } if(invDao.nextPage(pagenumber + 1, 1)) {
                            %>
                                    <a style="width: 100px;" href="../inventory/trademarket.jsp?pageNumber=<%= pagenumber + 1 %>" class="btn btn-success btn-arrow-left">다음</a>
                            <%
                                }
                            %>
                        </td>

                    </tr>
                </tbody>
            </table>
            
        </div>

    </section>
    
</div>
</body>
</html>