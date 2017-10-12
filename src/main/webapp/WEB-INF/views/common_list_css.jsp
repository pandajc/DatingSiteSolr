<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% pageContext.setAttribute("ctp", pageContext.getServletContext().getContextPath()); %>
<style type="text/css">
    .body_css{
        background-image: url('static/datingsiteimg.jpg');
        /*相对于body的大小*/
        background-size: 100% 100%;
        /*超出body不显示*/
        overflow:hidden;
    }
    .user_info {
        margin:5px;
    }
    .user_div {
        background-color: rgba(255,255,255,0.6);
        width: 500px;
        height: 200px;
        float: left;
        margin-left: 20px;
        margin-right: 20px;
        margin-top: 10px;
        margin-bottom: 10px;
        padding: 10px;
    }

    .user_pic {
        width: 150px;
        height: 200px;
        float: left;
    }

    #npn {
        position: absolute;
        top: 570px;
        left: 35%;
        margin: 10px;
    }
</style>
