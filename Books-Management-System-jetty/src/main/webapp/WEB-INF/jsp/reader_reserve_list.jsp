<%--
  Created by IntelliJ IDEA.
  User: 10394
  Date: 2017/12/25
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${readerCard.name}的预定</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: rgb(240, 242, 245);
        }
    </style>
</head>
<body>


<nav class="navbar navbar-default" role="navigation" style="background-color:#fff">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand " href="reader_main.html"><p class="text-primary">我的图书馆</p></a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <a href="reader_querybook.html">
                        图书查询
                    </a>
                </li>
                <li>
                    <a href="reader_info.html">
                        个人信息
                    </a>
                </li>
                <li class="active">
                    <a href="mylend.html">
                        我的借还
                    </a>
                </li>
                <li>
                    <a href="reader_reserve_list.html">
                        我的预定
                    </a>
                </li>
                <li>
                    <a href="reader_repasswd.html">
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="reader_info.html"><span class="glyphicon glyphicon-user"></span>&nbsp;${readerCard.name}，已登录</a>
                </li>
                <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>


<div style="position: relative;top: 10%">
    <c:if test="${!empty succ}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${error}
        </div>
    </c:if>
</div>

<div class="panel panel-default" style="position:relative;top: 80px;width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            我的预定
        </h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>预定号</th>
                <th>读者号</th>
                <th>书号</th>
                <th>预定时间</th>
                <th>是否成功</th>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="li">
                <tr>
                    <td><c:out value="${li.reserveId}"></c:out></td>
                    <td><c:out value="${li.readerId}"></c:out></td>
                    <td><c:out value="${li.bookId}"></c:out></td>
                    <td><c:out value="${li.reserveTime}"></c:out></td>
                    <td>
                        <c:if test="${li.vaild == 0}">
                            失败
                        </c:if>
                        <c:if test="${li.vaild == 1}">
                            成功
                        </c:if>
                        <c:if test="${li.vaild == 3}">
                            失效
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
