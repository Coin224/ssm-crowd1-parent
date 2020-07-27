function myDiyDemo(treeId,treeNode) {
    console.log(treeId);
    console.log(treeNode);

    // 拼接得到span的id
    var spanId = treeNode.tId + "_ico";

    // 通过spanId找到span标签
    // 删除原来的class
    // 添加新的class
    $("#"+spanId).removeClass().addClass(treeNode.icon)
}

function myAddHoverDom(treeId,treeNode) {
    // 找到节点
    var anchorId = treeNode.tId + "_a";
    // 设置button组的id
    var btnGrpId = "btnGrp" + treeNode.tId;
    window.name = treeNode.name;
    window.url = treeNode.url;
    window.icon = treeNode.icon;
    window.pid = treeNode.pid;
    var id = treeNode.id;
    // 设置<a>标签作为按钮
    var addBtn = "<a id='"+id+"' class=\"addBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\">&nbsp;&nbsp;<i class=\"fa fa-fw fa-plus rbg \"></i></a>";

    var removeBtn = "<a id='"+id+"' class=\"removeBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\" >&nbsp;&nbsp;<i class=\"fa fa-fw fa-times rbg \"></i></a>";

    var editBtn = "<a id='"+id+"' class=\"editBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\" title=\"修改权限信息\">&nbsp;&nbsp;<i class=\"fa fa-fw fa-edit rbg \"></i></a>";

    var btnHtml = "";

    var level = treeNode.level;
    if (level == 0) {
        // 如果等级为0 只能添加子节点
        btnHtml = addBtn;
    }

    if (level == 1) {

        // 如果等级为1 可以添加和编辑
        btnHtml = addBtn + editBtn;
        // 如果有没有子节点可以删除
        // 获取子节点的数量
        var length = treeNode.children.length;
        if ( length  == 0) {
            btnHtml = btnHtml + removeBtn;
        }
    }
    if (level == 2) {
        btnHtml = editBtn + removeBtn;
    }

    // 判断这个组的length是否大于0
    // 如果超过0 不添加
    // 因为鼠标在上面移动的时候会添加很多
    if ($("#"+btnGrpId).length > 0) {
        return;
    }
    $("#"+anchorId).append("<span id='"+btnGrpId+"'>"+btnHtml+"</span>")
}

function myRemoveHoverDom(treeId,treeNode) {
    var btnGrpId = "btnGrp" + treeNode.tId;
    $("#"+btnGrpId).unbind().remove();
}

function buildMenuTree() {
    $.ajax({
        url:'/menu/whole/tree',
        type:'post',
        dataType:'json',
        success:function (response) {
            var result = response.operationResult;
            if (result == 'SUCCESS') {
                // 用来设置树形结构：如图标等
                var setting = {
                    view:{
                        addDiyDom: myDiyDemo,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom,
                    },
                    data:{
                        key:{

                            // 为了实现点击不会跳转
                            // 这个路径找不到
                            url:'wangwangwang'
                        }
                    }

                };
                // 请求的属性结构的JSON数据
                var zNodes = response.data;

                // 初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
        }
    });
}