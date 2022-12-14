<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="board.boardDTO" %>
<%@ page import="board.boardDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="board.commentDTO" %>
<%@ page import="board.commentDAO" %>


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

        int boardid = 0;
        if (request.getParameter("boardID") != null) {
            boardid = Integer.parseInt(request.getParameter("boardID"));
        }
        if (boardid == 0) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('유효하지 않은 게시물입니다.')");
            script.println("location.href = '../board/board.jsp'");
            script.println("</script>");
        } 
        int pagenumber = 1;
        if(request.getParameter("pageNumber") != null) {
            pagenumber = Integer.parseInt(request.getParameter("pageNumber"));
        }

        boardDTO boardDto = new boardDAO().getboardDTO(boardid);
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
                <li class="nav-item">
                    <a class="nav-link" href="../main/main.jsp">메인</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="../board/board.jsp" style="color:#000">게시판</a>
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

    <section>
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th colspan="4" style="background-color: #6e6e6e; text-align: center;">게시글 보기</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="width: 20%;">게시글 제목</td>
                        <td colspan="3"><%= boardDto.getboardTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></td>
                    </tr>
                    <tr>
                        <td>작성자</td>
                        <td colspan="3"><%= boardDto.getNickname() %></td>
                    </tr>
                    <tr>
                        <td>작성일자</td>
                        <td colspan="3"><%= boardDto.getboardDate().substring(0,11) + boardDto.getboardDate().substring(11, 13) +"시" + boardDto.getboardDate().substring(14,16) + "분 " + boardDto.getboardDate().substring(17,19) + "초" %></td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td colspan="3" style="min-height: 300px; text-align: left;"><%= boardDto.getboardContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></td>
                    </tr>
                    <tr>
                        <form name="commentWirte" method="post" action="../commentAction">
                        <td><input type="submit" class="btn btn-primary" value="댓글 작성"></td>
                        <td colspan="3">
                            <textarea type="text" class="form-control" placeholder="글 내용" name="comment" maxlength="20" style="height: 80px"></textarea>
                        </td>
                        <input type="hidden" name="boardID" value="<%= boardDto.getboardID() %>">
                        <input type="hidden" name="id" value="<%= id %>">
                        </form>
                    </tr>
                    <tr>
                        <td colspan="4">댓글</td>
                    </tr>
                    <%
                        commentDAO commentDao = new commentDAO();
                        ArrayList<commentDTO> list = commentDao.getList(pagenumber, boardid);
                        for(int i = 0; i < list.size(); i++) {
                    %>
                    <tr>
                        <td><%= list.get(i).getNickname() %></td>
                        <td style="text-align: left"><%= list.get(i).getcomment().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></td>
                        <td style="font-size: 10px; width: 25%"><%= list.get(i).getcommentDate().substring(0,11) + list.get(i).getcommentDate().substring(11, 13) +"시" + list.get(i).getcommentDate().substring(14,16) + "분 " + list.get(i).getcommentDate().substring(17,19) + "초" %></td>
                        <td style="width: 12%">
                            <% if (id.equals(list.get(i).getID())) { %>
                            <a href = "../board/updatecom.jsp?commentID=<%= list.get(i).getcommentID() %> " class="btn btn-primary" style="width: 50px; height: 20px"><div style="font-size: 10px">수정</div></a>
                            <a href = "../board/deletecom.jsp?commentID=<%= list.get(i).getcommentID() %> " class="btn btn-primary" style="width: 50px; height: 20px"><div style="font-size: 10px">삭제</div></a>
                            <% } else {} %>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                if(pagenumber !=1) {
            %>
                    <a style="width: 100px; float: right" href="../board/view.jsp?boardID=<%= boardid %>&&pageNumber=<%= pagenumber - 1 %>" class="btn btn-success btn-arrow-left">이전</a>
            <%
                } if(commentDao.nextPage(pagenumber + 1, boardid)) {
            %>
                    <a style="width: 100px; float: right" href="../board/view.jsp?boardID=<%= boardid %>&&pageNumber=<%= pagenumber + 1 %>" class="btn btn-success btn-arrow-right">다음</a>
            <%
                }
            %>
            <a href="../board/board.jsp" class="btn btn-primary" style="width: 80px">목록</a>
            <%
                if(id != null && id.equals(boardDto.getID())) {
            %>
                    <a href = "../board/update.jsp?boardID=<%= boardid %>" class="btn btn-primary" style="width: 80px">수정</a>
                    <a href = "../board/deleteAction.jsp?boardID=<%= boardid %>" class="btn btn-primary" style="width: 80px">삭제</a>
            <%
                }
            %>
            
        </div>

    </section>
    
</div>
</body>
</html>