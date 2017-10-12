<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% pageContext.setAttribute("ctp", pageContext.getServletContext().getContextPath()); %>
<div style=" height: 25px;">
    <div style="float: left;">
        <c:if test="${empty loginUser}">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            未登录
        </c:if>
        <c:if test="${not empty loginUser}">
            <span style="float: left;margin-left: 30px;">欢迎您，</span><span
                style="color: red;float: left">${loginUser.nickName}</span>
            <c:if test="${empty keyword}">
                <div style="float: left;margin-left: 10px;"><a href="${ctp}/toUpdate?pn=${pageInfo.pageNum}">个人中心</a></div>
            </c:if>
            <c:if test="${not empty keyword}">
                <div style="float: left;margin-left: 10px;"><a href="${ctp}/toUpdate?pn=${pageInfo.pageNum}&keyword=${keyword}">个人中心</a></div>
            </c:if>
            <div style="float: left;margin-left: 10px;"><a href="${ctp}/userLogout">退出</a></div>
        </c:if>
    </div>

    <span style="float: right;">
                <a href="${ctp}/login.html">登录</a>
                <a href="${ctp}/reg.html">注册</a>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </span>
    <form action="${ctp}/search" method="get">
        <span style="float: right;margin-right: 20px;">
            <input id="searchInput" type="text" name="keyword" placeholder="搜索从这里开始"/>
            <input id="searchBtn" type="submit" value="搜索"/>
        </span>
    </form>
</div>
