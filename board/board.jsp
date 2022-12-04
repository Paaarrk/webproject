<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="board.boardDAO" %>
<%@ page import="board.boardDTO" %>


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
                    }
                })
            }
        } 
        
        function logout() {
            location.href = '../main/logout.jsp';
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
                <li class="nav-item active">
                    <a class="nav-link" href="../board/board.jsp" style="color:#000">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">거래소</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">미니게임</a>
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

    <section>
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th style="background-color: #6e6e6e; text-align: center;">번호</th>
                        <th style="background-color: #6e6e6e; text-align: center;">제목</th>
                        <th style="background-color: #6e6e6e; text-align: center;">작성자</th>
                        <th style="background-color: #6e6e6e; text-align: center;">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        boardDAO boardDao = new boardDAO();
                        ArrayList<boardDTO> list = boardDao.getList(pagenumber);
                        for(int i = 0; i < list.size(); i++) {
                    %>
                    <tr>
                        <td><%= list.get(i).getboardID() %></td>
                        <td><a href="../board/view.jsp?boardID=<%= list.get(i).getboardID() %>"><%= list.get(i).getboardTitle() %></a></td>
                        <td><%= list.get(i).getID() %></td>
                        <td><%= list.get(i).getboardDate().substring(0,11) + list.get(i).getboardDate().substring(11, 13) +"시" + list.get(i).getboardDate().substring(14,16) + "분 " + list.get(i).getboardDate().substring(17,19) + "초" %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                if(pagenumber !=1) {
            %>
                    <a style="width: 100px;" href="../board/board.jsp?pageNumber=<%= pagenumber - 1 %>" class="btn btn-success btn-arrow-left">이전</a>
            <%
                } if(boardDao.nextPage(pagenumber + 1)) {
            %>
                    <a style="width: 100px;" href="../board/board.jsp?pageNumber=<%= pagenumber + 1 %>" class="btn btn-success btn-arrow-left">다음</a>
            <%
                }
            %>
            <a href="../board/write.jsp" class="btn btn-primary" style="width: 100px;">글쓰러가기</a> 글을 쓰시면 코인 500개를 얻으실 수 있습니다
        </div>

    </section>
    
</div>
</body>
</html>