<%--
  Created by IntelliJ IDEA.
  User: 82150
  Date: 2020/7/30
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>

<script type="text/javascript">
    $(function () {
        // 给roRightBtn绑定单击响应函数
        $("#toRightBtn").click(function () {
            // 选择页面上第一个select选择器被选中的option元素 追加到第二个select
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        });
        $("#toLeftBtn").click(function () {
            //
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        });
        // 给提交按钮绑定单击响应函数在点击提交按钮时，全部选中
        $("#subBtn").click(function () {
            $("select:eq(1)>option").prop("selected",true);
        });
    });
</script>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="/assign/do/save/role" method="post" role="form" class="form-inline">
                        <input type="hidden" name="adminId" value="${param.adminId}"/>
                        <input type="hidden" name="pageNum" value="${param.pageNum}"/>
                        <input type="hidden" name="keyword" value="${param.keyword}"/>
                        <div class="form-group">
                            <label for="unAssignRole">未分配角色列表</label><br>
                            <select id="unAssignRole" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoles}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="assignRole">已分配角色列表</label><br>
                            <select id="assignRole" name="roleIdList" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoles}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="subBtn" type="submit" class="btn btn-lg btn-success btn-block" style="width: 150px;margin: 20px auto 20px 230px">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

