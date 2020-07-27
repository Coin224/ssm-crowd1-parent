<%--
  Created by IntelliJ IDEA.
  User: 82150
  Date: 2020/7/27
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<SCRIPT type="text/javascript">

    $(function () {
        // 页面加载时生成菜单树形结构
        buildMenuTree();

        // 新增菜单节点
        $("#treeDemo").on("click",".addBtn",function () {
            // 获取当前节点的id作为新增节点的pid
            window.nodeId = this.id;

            // 打开模态框
            $("#menuAddModal").modal('show');
        });

        $("#menuSaveBtn").click(function () {
            // 获取需要的值
            var pid = window.nodeId;
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());

            $.ajax({
                url:'menu/do/add',
                type:'post',
                data:{
                    pid:pid,
                    name:name,
                    url:url,
                    icon:icon
                },
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("添加菜单节点成功");
                        // 刷新菜单树形结构
                        buildMenuTree();
                    }
                    if (result == "FAILD") {
                        layer.msg("添加菜单节点失败！！！"+response.operationMessage);
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            // 打开模态框
            $("#menuAddModal").modal('hide');

            // 清空输入框
            // $("#menuAddModal [name=name]").val("");
            // $("#menuAddModal [name=url]").val("");
            // $("#menuAddModal [name=icon]").prop("checked",false);

            $("#menuResetBtn").click();
        });

        // 修改菜单节点
        $("#treeDemo").on("click",".editBtn",function () {
            // 获取当前节点的id作为新增节点的pid
            window.nodeId = this.id;

            // 打开模态框
            $("#menuEditModal").modal('show');

            // 回显数据
            $("#menuEditModal [name=name]").val(window.name);
            $("#menuEditModal [name=url]").val(window.url);

            // $("#menuEditModal [name=icon]").each(function () {
            //     // 遍历所有的icon
            //     // 获取icon的val
            //     var val = $(this).val();
            //     //如果val和window.icon相等
            //     if (val == window.icon) {
            //         $(this).prop("checked",true);
            //     }
            // });
            $("#menuEditModal [name=icon]").val([window.icon]);
        });

        $("#menuEditBtn").click(function () {
            // 获取需要的值
            var name = $.trim($("#menuEditModal [name=name]").val());
            var url = $.trim($("#menuEditModal [name=url]").val());
            var icon = $("#menuEditModal [name=icon]:checked").val();

            $.ajax({
                url:'menu/do/edit',
                type:'post',
                data:{
                    id:window.nodeId,
                    pid:window.pid,
                    name:name,
                    url:url,
                    icon:icon
                },
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("修改菜单节点成功");
                        // 刷新菜单树形结构
                        buildMenuTree();
                    }
                    if (result == "FAILD") {
                        layer.msg("修改菜单节点失败！！！"+response.operationMessage);
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            // 打开模态框
            $("#menuEditModal").modal('hide');

            // 清空输入框
            // $("#menuAddModal [name=name]").val("");
            // $("#menuAddModal [name=url]").val("");
            // $("#menuAddModal [name=icon]").prop("checked",false);
        });

        // 删除菜单节点
        $("#treeDemo").on("click",".removeBtn",function () {
            // 获取当前节点的id作为新增节点的pid
            window.nodeId = this.id;
            // 打开模态框
            $("#menuConfirmModal").modal('show');
            // 回显节点名称
            $("#removeNodeSpan").append("&nbsp;&lt;"+window.name+"&gt;&nbsp;");
        });
        $("#confirmBtn").click(function () {
            $.ajax({
                url:'menu/do/remove',
                type:'post',
                data:{
                    id:window.nodeId
                },
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result = 'SUCCESS') {
                        layer.msg("删除菜单节点成功！！！");
                        // 刷新菜单树形结构
                        buildMenuTree();
                    } else {
                        layer.msg("删除菜单节点失败！！！"+response.operationMessage);
                    }
                },
                error:function (response) {
                    layer.msg("错误码："+response.status,"错误信息："+response.statusMessage);
                }
            });
            $("#menuConfirmModal").modal('hide');
        });
    });
</SCRIPT>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>

</body>
</html>

