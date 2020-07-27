// 打开模态框，并把roleName回显
function showConfirmModal(roleArray) {
    // 清空roleNameDiv
    $("#roleNameDiv").empty();

    // 打开模态框
    $("#confirmModal").modal("show");

    // 全局roleIdArray
    window.roleIdArray = [];

    // 遍历roleArray
    for (var i = 0;i < roleArray.length;i++) {
        // 取出role
        var role = roleArray[i];

        // 获取roleName
        var roleName = role.roleName;

        // 把roleName拼接到roleNameDiv
        $("#roleNameDiv").append(roleName+"<br>");

        // 取出roleId
        var roleId = role.roleId;

        // 把roleId存到roleIdArray
        window.roleIdArray.push(roleId);
    }
}


// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();
    // 2.填充表格
    fillTableBody(pageInfo);
}
// 获取分页数据
function getPageInfoRemote() {
    var reslutAjax = $.ajax({
        url:'/role/manage',
        type:'post',
        data:{
            pageNum:window.pageNum,
            pageSize:window.pageSize,
            keyword:window.keyword
        },
       async:false,
       dataType:'json'
    });

    console.log(reslutAjax);

    //判断当前状态码
    var status = reslutAjax.status;
    // 如果当前响应状态码不是 200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if (status !=200) {
        layer.msg("失败！响应状态码="+status+" 说明信息="+reslutAjax.statusText);
        return null;
    }

    // 如果响应状态码是 200，说明请求处理成功，获取 pageInfo
    var resultEntity = reslutAjax.responseJSON;

    // 从 resultEntity 中获取 result 属性
    var result = resultEntity.result;

    // 判断 result 是否成功
    if(result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认 result 为成功后获取 pageInfo
    var pageInfo = resultEntity.data;

    // 返回 pageInfo
    return pageInfo;

}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除 tbody 中的旧的内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    $("#sumBox").prop("checked",false);

    // 判断 pageInfo 对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜 索的数据！</td></tr>");
        return ;
    }

    // 使用 pageInfo 的 list 属性填充 tbody
    for(var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox' ></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }
    // // 生成分页导航条
    generatorNavigator(pageInfo);

}

// 生成分页页码导航条
function generatorNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;
    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    };
    // 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

//  翻页时回调函数
function paginationCallBack(pageIndex,jQuery) {
    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generatePage();
    // 取消页码超链接的默认行为
    return false;
}