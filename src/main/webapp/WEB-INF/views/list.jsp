<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>尚玫瑰</title>
    <jsp:include page="common_list_css.jsp"></jsp:include>
    <% pageContext.setAttribute("ctp", pageContext.getServletContext().getContextPath()); %>
</head>
<body class="body_css">
<jsp:include page="common_title.jsp"></jsp:include>
<div style="margin: 0 auto; width: 1120px;height: 500px;">
    <jsp:include page="common_bar.jsp"></jsp:include>
    <div>
        <c:forEach items="${pageInfo.list}" var="user">
            <div class="user_div">
                <div><img src="http://${trackerServer}/${user.pictureGroupName}/${user.pictureRemoteName}"
                          class="user_pic"></div>
                <div style="float:left; width: 330px;height:180px;margin: 10px">
                    <div class="user_info">昵称：${user.nickName}</div>
                    <div class="user_info">性别：${user.gender}</div>
                    <div class="user_info">住址：${user.hometown}</div>
                    <div class="user_info">职业：${user.job}</div>
                    <div class="user_info" style="height: 50px">描述：${user.userDescribe}</div>
                    <div style="float: right">
                        <a href="${ctp}/userDetail?id=${user.userId}&pn=${pageInfo.pageNum}">详情&gt;&gt;</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div id="npn">
        <a href="${ctp}/list?pn=1">首页</a>
        <c:if test="${not pageInfo.hasPreviousPage}">上一页</c:if>
       <c:if test="${pageInfo.hasPreviousPage}">
           <a href="${ctp}/list?pn=${pageInfo.pageNum - 1}">上一页</a>
        </c:if>
        <c:forEach items="${pageInfo.navigatepageNums}" var="navigatepageNum">
            <c:if test="${navigatepageNum == pageInfo.pageNum}">
                ${navigatepageNum}
            </c:if>
            <c:if test="${navigatepageNum != pageInfo.pageNum}">
                <a href="${ctp}/list?pn=${navigatepageNum}">${navigatepageNum}</a>
            </c:if>
        </c:forEach>
        <c:if test="${not pageInfo.hasNextPage}">下一页</c:if>
        <c:if test="${pageInfo.hasNextPage}">
            <a href="${ctp}/list?pn=${pageInfo.pageNum + 1}">下一页</a>
        </c:if>
        <a href="${ctp}/list?pn=${pageInfo.pages}">尾页</a>
        第${pageInfo.pageNum}页
        共${pageInfo.pages}页
        共${pageInfo.total}条记录
    </div>
</div>
<jsp:include page="common_footer.jsp"></jsp:include>
</body>
<jsp:include page="common_img_script.jsp"></jsp:include>
</html>
