<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>尚玫瑰</title>
    <% pageContext.setAttribute("ctp", pageContext.getServletContext().getContextPath()); %>
</head>
<body>
<jsp:include page="common_title.jsp"></jsp:include>
<div style="margin: 0 auto; width: 1120px;">
    <jsp:include page="common_bar.jsp"></jsp:include>
    <h2 style="text-align: center;position: relative; top: 50px">未查询到任何结果，请换一换关键词试试</h2><br>

</div>
</body>
</html>
