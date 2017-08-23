<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: paul
  Date: 2017/8/18
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
    <h1>客户列表</h1>
    <table>
        <tr>
            <th>客户名称</th>
            <th>联系人</th>
            <th>电话号码</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${customerList}" var="customer">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.connect}</td>
                <td>${customer.telephone}</td>
                <td>${customer.email}</td>
                <td>${customer.remark}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>

