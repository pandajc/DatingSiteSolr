<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>尚玫瑰个人中心</title>
    <% pageContext.setAttribute("ctp",pageContext.getServletContext().getContextPath()); %>
</head>
<body>
<jsp:include page="common_title.jsp"></jsp:include>
    <h1 style="text-align: center">个人中心</h1>
    <div>
        <div style="width: 310px; float: left;margin-left: 450px">
            <form action="${ctp}/userUpdate" method="post" enctype="multipart/form-data">
                账户：${loginUser.userName}<br><br>
                <input type="hidden" name="pn" value="${pageNum}">
                <input type="hidden" name="keyword" value="${keyword}">
                <input type="hidden" name="userId" value="${loginUser.userId}"/>
                <input type="hidden" name="pictureGroupName" value="${loginUser.pictureGroupName}"/>
                <input type="hidden" name="pictureRemoteName" value="${loginUser.pictureRemoteName}"/>
                密码：<input type="password" name="userPwd" value="${loginUser.userPwd}"/><br><br>
                昵称：<input type="text" name="nickName" value="${loginUser.nickName}"/><br><br>
                住址：<input type="text" name="hometown" value="${loginUser.hometown}"/><br><br>
                职业：<input type="text" name="job" value="${loginUser.job}"/><br><br>
                性别：<input type="radio" name="gender" value="男" ${loginUser.gender == "男"? "checked='checked'" : ""}/>男
                <input type="radio" name="gender" value="女" ${loginUser.gender == "女"? "checked='checked'" : ""}/>女<br><br>
                描述：<textarea name="userDescribe" cols="20" rows="5">${loginUser.userDescribe}</textarea><br><br>
                头像：<input type="file" name="file" />
                <br><br>
                <input id="subBtn" type="submit" value="修改"/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <c:if test="${empty keyword}">
                    <a href="${ctp}/list?pn=${pageNum}">返回</a>
                </c:if>
                 <c:if test="${not empty keyword}">
                    <a href="${ctp}/search?pn=${pageNum}&keyword=${keyword}">返回</a>
                </c:if>
                <label style='position:relative;left: 50px;color: red;font-size: 15px;font-weight: bolder;'>
                    ${msg}</label>
            </form>
        </div>
        <div style="width: 220px;height: 300px; float: left">
            <img id="userPic" src="http://${loginUserTrackerServer}/${loginUser.pictureGroupName}/${loginUser.pictureRemoteName}" style="width: 210px;height: 270px;" alt="未上传头像">
        </div>
    </div>
</body>
<script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${ctp}/static/jquery-validation-1.13.1/dist/jquery.validate.js"></script>
<script type="text/javascript" >
    $("input[type='file']").change(function (event) {
        var files = this.files;
        var file = files[0];
        console.log(file);
        var URL = window.URL;
        console.log(URL);
        var imgURL = URL.createObjectURL(file);
        console.log(imgURL);
        $("#userPic").attr("src", imgURL);

    });
    $("#subBtn").click(function () {
        $("form:first").submit();
        return false;
    });
    $("textarea").change(function () {
        console.log($(this).text())
        console.log($(this).text().trim());
        var newText = $(this).text().trim();
        console.log(newText);
        $(this).text("newText");
    });
    $(function(){

        $.validator.setDefaults({

            showErrors : function(map, list) {
                console.log(list);
                var success_ele = this.successList;
                console.log(success_ele);
                $.each(list, function(){
                    myShowError(this, false);
                });
                $.each(success_ele, function(){
                    myShowError(this, true);
                });

            }
        })
        function myShowError(ele, flag){
            if(flag == true){
                $(ele).nextAll("label[name='"+$(ele).attr("name")+"']").remove();
            }else {
                $(ele.element).nextAll("label:first").remove();
                console.log($(ele.element));
                console.log();
                var attr = $(ele.element).attr("name");
                $(ele.element).nextAll("br:first").after("<label name='"+attr+"' style='position:relative;" +
                    "left: 50px;color: red;font-size: 15px;font-weight: bolder;'>"+ele.message+"</label>");

            }
        };
        $("form:first").validate({
            rules : {
                userName : {
                    required : true,
                    minlength : 5,
                    maxlength : 18
                },
                userPwd : {
                    required : true,
                    minlength : 5,
                    maxlength : 18
                },
                nickName : {
                    required : true,
                    minlength : 1,
                    maxlength : 10
                },
                hometown : {
                    required : true,
                    minlength : 1,
                    maxlength : 10
                },
                job : {
                    required : true,
                    minlength : 1,
                    maxlength : 10
                },
                gender : {
                    required : true
                },
                userDescribe : {
                    required : true,
                    minlength : 1,
                    maxlength : 40
                }

            },
            messages : {
                userName : {
                    required : "账号不能为空",
                    minlength : "账户名长度应为5-18个字符",
                    maxlength : "账户名长度应为5-18个字符"
                },
                userPwd : {
                    required : "密码不能为空",
                    minlength : "密码长度应为5-18个字符",
                    maxlength : "密码长度应为5-18个字符"
                },
                nickName : {
                    required : "昵称不能为空",
                    minlength : "昵称长度应为1-10个字符",
                    maxlength : "昵称长度应为1-10个字符"
                },
                hometown : {
                    required : "住址不能为空",
                    minlength : "密码长度应为1-10个字符",
                    maxlength : "密码长度应为1-10个字符"
                },
                job : {
                    required : "职业不能为空",
                    minlength : "密码长度应为1-10个字符",
                    maxlength : "密码长度应为1-10个字符"
                },
                gender : {
                    required : "请选择性别"
                },
                userDescribe : {
                    required : "描述不能为空",
                    minlength : "描述长度应为1-40个字符",
                    maxlength : "描述长度应为1-40个字符"
                },

            }

        });
    });

</script>
</html>
