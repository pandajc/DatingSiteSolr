<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% pageContext.setAttribute("ctp", pageContext.getServletContext().getContextPath()); %>
<script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

    $(".user_pic").mouseover(function(event){
        var src = $(this).attr("src");
        $("#showBigImg").attr("src", src);
        $("#showBig")
            .show()
            .css("left",880)
            .css("top",10);
    })
    /*.mousemove(function(event){
        $("#showBig")
            .css("left",event.pageX+50)
            .css("top",event.pageY-150);
    })*/
        .mouseout(function(){
            $("#showBig").hide();
        });

</script>
