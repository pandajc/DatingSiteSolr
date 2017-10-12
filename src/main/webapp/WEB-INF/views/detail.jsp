<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/1 0001
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>尚玫瑰详情页面</title>
    <% pageContext.setAttribute("ctp",pageContext.getServletContext().getContextPath()); %>
</head>
<body>
<jsp:include page="common_title.jsp"></jsp:include>
    <h1 style="text-align: center">会员信息</h1>
    <div>
        <div style="width: 310px; float: left;margin-left: 450px">
                昵称：${user.nickName}<br><br>
                住址：${user.hometown}<br><br>
                职业：${user.job}<br><br>
                性别：${user.gender}<br><br>
                描述：${user.userDescribe}<br><br><br><br>
                <c:if test="${empty keyword}">
                    <a href="${ctp}/list?pn=${pageNum}">返回</a>
                </c:if>
                <c:if test="${not empty keyword}">
                    <a href="${ctp}/search?pn=${pageNum}&keyword=${keyword}">返回</a>
                </c:if>
        </div>
        <div style="width: 220px;height: 300px; float: left">
            <img id="userPic" src="http://${trackerServer}/${user.pictureGroupName}/${user.pictureRemoteName}" style="width: 210px;height: 270px;" alt="未上传头像">
        </div>
    </div>
</body>
<script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
</html>
