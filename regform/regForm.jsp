<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../asset/css/bootstrap.css">
    <link rel="stylesheet" href="../asset/css/custom.css">
    <title>-회원 가입-</title>
    <script src="../asset/js/jquery-3.6.1.min.js"></script>
    <script src="../asset/js/bootstrap.js"></script>

    <script type="text/javascript" src="../asset/js/regForm.js">

    </script>
</head>
<body>
    <div class="container">
        <form method="post" action="../userRegister" name="userINFO" onsubmit="return checkValue()">
            <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th colspan="5"><h4>회원가입</h4></th>
                    </tr>
                </thead>
                <tbody>
                    
                    <tr>
                        <td style="width: 130px;"><h5>아이디</h5></td>
                        <td colspan="3">
                            <ul>
                                <input class="form-control" id="userID" type="text" name="userID" maxlength="20" placeholder="20자 내로 입력해주세요!" onkeydown="notIDClick()">
                            </ul>
                            <ul style="height: 10px">
                                <div id="idmsg"></div>
                            </ul>
                        </td>
                        <td style="width: 130px;">
                            <button class="btn btn-primary" type="button" onclick="regIDCheck();">중복체크
                            <input type="hidden" name="userID_DUPLICATION" value="IDUncheck">
                        </td>
                    </tr>
                    
                    
                    <tr>
                        <td style="width: 130px;"><h5>비밀번호</h5></td>
                        <td colspan="3"><input class="form-control" type="password" name="userPW" maxlength="20" placeholder="20자 내로 입력해주세요!"></td>
                    </tr>

                   
                    <tr> 
                        <td style="width: 130px;"><h5>비밀번호 확인</h5></td>
                        <td colspan="3"><input class="form-control" type="password" name="userPWCHK" maxlength="20" placeholder="위 비밀번호와 동일하게 입력해주세요!"></td>
                    </tr>

                    
                    <tr> 
                        <td style="width: 130px;"><h5>이메일</h5></td>
                        <td colspan="3">
                            <ul>
                                <input class="form-control" id="userEMAIL" type="e-mail" name="userEMAIL" maxlength="45" onkeydown="notEMAILClick()" placeholder="aa@aa.com">
                            </ul>
                            <ul style="height: 10px">
                                <div id="emailmsg"></div>
                            </ul>
                        </td>
                        <td style="width: 130px;">
                            <button class="btn btn-primary" type="button" onclick="regEMAILCheck();">중복체크
                            <input type="hidden" name="userEMAIL_DUPLICATION" value="EMAILUncheck">
                        </td>
                    </tr>

                    <tr> 
                        <td style="width: 130px;"><h5>닉네임</h5></td>
                        <td colspan="3">
                            <ul>
                                <input class="form-control" id="userNICK" type="text" name="userNICKNAME" maxlength="45" onkeydown="notNICKNAMEClick()">
                            </ul>
                            <ul style="height: 10px">
                                <div id="nickmsg"></div>
                            </ul>
                        </td>
                        <td style="width: 130px;">
                            <button class="btn btn-primary" type="button" onclick="regNICKNAMECheck();">중복체크
                            <input type="hidden" name="userNICKNAME_DUPLICATION" value="NICKNAMEUncheck">
                        </td>
                    </tr>
                   
                    <tr> 
                        <td style="width: 130px;"><h5>이름</h5></td>
                        <td colspan="4"><input class="form-control" type="text" name="userNAME" maxlength="45"></td>
                    </tr>

                    <tr> 
                        <td style="width: 130px;"><h5>전화번호</h5></td>
                        <td colspan="4"><input class="form-control" type="tel" name="userPHONE" maxlength="20" placeholder=" '-' 제외해주세요!"></td>
                    </tr>

                    <tr> 
                        <td style="width: 130px;"><h5>성별</h5></td>
                        <td colspan="4">
                            <div class="form-group" style="text-align: center; margin: 0 auto;">
                                <div class="btn-group" role="group" data-toggle="buttons">   
                                    <label class="btn btn-primary active">
                                        <input type="radio" name="userGENDER" autocomplete="off" value="M" checked>남자
                                    </label>
    
                                    <label class="btn btn-primary active">
                                        <input type="radio" name="userGENDER" autocomplete="off" value="F">여자
                                    </label>
                                </div>
                            </div>
                        </td>
                    </tr>

                    <tr> 
                        <td style="text-align: right" colspan="5">
                            <input class="btn btn-primary pull-right" type="button" value="취소" onclick="self.close()">
                            <input class="btn btn-primary pull-right" type="submit" value="회원가입"> 
                        </td>
                    </tr>

                </tbody>
            </table>
        </form>
    </div>

</body>
</html>