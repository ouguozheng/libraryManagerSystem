<%--
  Created by IntelliJ IDEA.
  User: 10394
  Date: 2017/12/20
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加出版社</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <style>
        body{
            background-color: rgb(240,242,245);
        }
    </style>
</head>
<body>
<nav  style="position:fixed;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html">图书管理系统</a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        图书管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allbooks.html">全部图书</a></li>
                        <li class="divider"></li>
                        <li><a href="book_add.html">增加图书</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        读者管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allreaders.html">全部读者</a></li>
                        <li class="divider"></li>
                        <li><a href="reader_add.html">增加读者</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        借还管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="lendlist.html">借还日志</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        预定管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_reserve_list.html">预定日志</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        出版社管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_publish_list.html">全部出版社</a></li>
                        <li class="divider"></li>
                        <li><a href="admin_publish_add.html">增加出版社</a></li>
                    </ul>
                </li>
                <li >
                    <a href="admin_repasswd.html" >
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="login.html"><span class="glyphicon glyphicon-user"></span>&nbsp;${readerCard.name}，已登录</a></li>
                <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<div style="position: relative;top: 10%;width: 80%;margin-left: 10%">
    <form action="admin_publish_add_do.html" method="post" id="addPublish" >
        <div class="form-group">
            <label for="id">出版社ID</label>
            <input type="text" class="form-control" name="id" id="id" value="${maxPublishId}" readonly=""true>
        </div>
        <div class="form-group">
            <label for="name">出版社名称</label>
            <input type="text" class="form-control" name="name" id="name"  placeholder="请输入出版社名">
        </div>
        <div class="form-group">
            <label for="phone">出版社联系方式</label>
            <input type="text" class="form-control"  name="phone" id="phone"  placeholder="请选择出版社联系方式">
        </div>
        <div class="form-group">
            <label for="address">出版社地址</label>
            <input type="text" class="form-control" name="address" id="address"  placeholder="请输入出版社地址">
        </div>
        <div class="form-group">
            <label for="info">出版社简介</label>
            <textarea class="form-control" rows="3"  name="info" id="info" placeholder="请输入出版社简介"></textarea>
        </div>

        <input type="submit" value="添加" class="btn btn-success btn-sm" class="text-left">
        <script>
            function mySubmit(flag){
                return flag;
            }
            $("#addPublish").submit(function () {
                if($("#id").val()==''||$("#name").val()==''||$("#phone").val()==''||$("#address").val()==''||$("#info").val()==''){
                    alert("请填入完整图书信息！");
                    return mySubmit(false);
                }
            })
        </script>
    </form>
</div>
</body>
</html>
