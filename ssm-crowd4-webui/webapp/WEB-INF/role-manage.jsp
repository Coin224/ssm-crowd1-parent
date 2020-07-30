<%--
  Created by IntelliJ IDEA.
  User: 82150
  Date: 2020/7/22
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="/crowd/my-role.js" charset="UTF-8"></script>
<script type="text/javascript">
    $(function () {
        // 1.为分页准备初始化数据
        window.pageNum = 1;
        window.pageSize = 10;
        window.keyword = "";
        // 2.
        generatePage();

        $("#queryBtn").click(function () {
            // 获取到输入框中的值
            window.keyword = $("#kwBtn").val();
            generatePage();
        });

        $("#showAddRole").click(function () {
            $("#addModal").modal('show');
        });

        $("#addRole").click(function () {
            var name = $("#addModal [name=name]").val();
            $.ajax({
                url:'role/addRole',
                type:'post',
                data:{"name":name},
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("新增角色成功！！！");
                        window.pageNum = 99999999;
                        generatePage();
                    } else {
                        layer.msg("新增角色失败！！！"+response.operationMessage)
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            // 隐藏modal
            $("#addModal").modal('hide');

            // 清空输入框
            $("#addModal [name=name]").val("");
        });

        $("#rolePageBody").on("click",".pencilBtn",function () {
            // 打开模态框
            $("#editModal").modal('show');

            // 获取roleName
            var roleName = $(this).parent().prev().text();

            // 获取roleId
            window.roleId = this.id;

            // 设置模态框中input中的值
            $("#editModal [name = name]").val(roleName);
        });


        $("#editRole").click(function () {
            var name = $("#editModal [name=name]").val();
            $.ajax({
                url:'role/editRole',
                type:'post',
                data:{
                    "id":window.roleId,
                    "name":name
                },
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("修改角色成功！！！");
                        generatePage();
                    } else {
                        layer.msg("修改角色失败！！！"+response.operationMessage)
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            // 隐藏modal
            $("#editModal").modal('hide');

        });


        // 全选，全不选
        $("#sumBox").click(function () {
            // ①获取当前多选框自身的状态
            var checked = $(this).is(':checked');

            // ②用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked",checked);
        });

        // 全选，全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function () {
            // 被选中的个数
            var checkLen = $(".itemBox:checked").length;

            // 总的个数
            var sumLen = $(".itemBox").length;

            $("#sumBox").prop("checked",checkLen == sumLen);

        });

        // 批量删除
        $("#batchRemoveRole").click(function () {
            // 获取role数组
            var roleArray = [];
            $(".itemBox:checked").each(function () {
                var roleId = this.id;
                var roleName = $(this).parent().next().text();

                roleArray.push({
                    roleId:roleId,
                    roleName:roleName
                });
            });

            if (roleArray.length == 0) {
                layer.msg("请至少选择一个删除");
                return;
            }
            showConfirmModal(roleArray);
        });

        // 删除单条记录
        $("#rolePageBody").on("click",".removeBtn",function () {

            var roleId = this.id;
            var roleName = $(this).parent().prev().text();
            var roleArray = [];
            roleArray.push({
                "roleId":roleId,
                "roleName":roleName
            });
            showConfirmModal(roleArray);
        });

        // 执行删除
        $("#confirmRemoveRole").click(function () {
            // ajax
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                url:'role/removeRole',
                type:'post',
                data:requestBody,
                contentType:'application/json',
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("删除角色成功！！！");
                        generatePage();
                    } else {
                        layer.msg("删除角色失败！！！"+response.operationMessage)
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            $("#confirmModal").modal("hide");
        });

        // 显示权限的树形结构
        $("#rolePageBody").on("click",".checkBtn",function () {
            window.roleId = this.id;
            // 打开模态框
            $("#roleAuthModal").modal("show");
            // 展示树形结构
            buildAuthTree();
        });

        // 绑定单击响应函数 给角色绑定权限
        $("#saveAuthForRole").click(function () {
            // 获取数据
            var roleId = window.roleId;
            // 被选中的节点
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getCheckedNodes(true);

            // 遍历nodes 获取id 把id装到数组中
            var authIdArray = [];
            for (var i = 0 ; i < nodes.length ; i ++) {
                var node = nodes[i];

                var authId = node.id;

                authIdArray.push(authId);
            }
            var requestBody = {
                roleId:[roleId],
                authIdArray:authIdArray
            };

            requestBody = JSON.stringify(requestBody);
            $.ajax({
                url:'assign/do/save/auth/for/role',
                type:'post',
                data:requestBody,
                contentType: 'application/json',
                dataType:'json',
                success:function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("给角色添加权限成功！！！");
                        // 重新生成树
                        buildAuthTree();
                    } else {
                        layer.msg("给角色添加权限失败！！！"+response.operationMessage)
                    }
                },
                error:function (response) {
                    layer.msg("状态码："+response.status+",状态信息： "+response.statusMessage);
                }
            });
            $("#roleAuthModal").modal("hide");

        });

    });
</script>
<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="kwBtn" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveRole" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddRole" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="sumBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="4" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/include/include-role-add.jsp"%>
<%@include file="/WEB-INF/include/include-role-edit.jsp"%>
<%@include file="/WEB-INF/include/include-role-confirm.jsp"%>
<%@include file="/WEB-INF/include/modal-auth-confirm.jsp"%>
</body>
</html>

