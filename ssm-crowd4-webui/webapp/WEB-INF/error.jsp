<%--
  Created by IntelliJ IDEA.
  User: 82150
  Date: 2020/7/21
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1>${requestScope.exception.message}</h1>
        </div>
    </div>
</div>

</body>
</html>

