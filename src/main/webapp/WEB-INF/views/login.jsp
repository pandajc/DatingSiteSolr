<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>尚玫瑰登录页面</title>
    <% pageContext.setAttribute("ctp",pageContext.getServletContext().getContextPath()); %>
</head>
<body>
<jsp:include page="common_title.jsp"></jsp:include>
    <center>
        <h1>会员登录</h1>
        <form action="${ctp}/userLogin" method="post">
            账户：<input type="text" name="userName" value="${user.userName}"/><br><br>
            密码：<input type="password" name="userPwd" value="${user.userPwd}"/><br><br>
            <input id="loginBtn" type="submit" value="登录"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="${ctp}/reg.html">注册</a>
        </form><br><br>
        <label style="color: red">${msg}</label>
    </center>
</body>
<script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
    $("#loginBtn").click(function () {
        var userName = $("input[name='userName']").val().trim();
        var userPwd = $("input[name='userPwd']").val().trim();
        $("input[name='userPwd']").next().next("label").remove();
        $("input[name='userName']").next().next("label").remove();
        if (userName == ""){
            $("input[name='userName']").nextAll("br:first")
                .after("<label style='color: red;font-size: 15px;font-weight: bolder;'>请输入账户名</label>");
        }else if (userName != "" && userPwd == ""){
            $("input[name='userPwd']").nextAll("br:first")
                .after("<label style='color: red;font-size: 15px;font-weight: bolder;'>请输入密码</label>");
        }else {
            $("form:first").submit();
        }
        return false;
    });
</script>
</html>
