// ��ģ̬�򣬲���roleName����
function showConfirmModal(roleArray) {
    // ���roleNameDiv
    $("#roleNameDiv").empty();

    // ��ģ̬��
    $("#confirmModal").modal("show");

    // ȫ��roleIdArray
    window.roleIdArray = [];

    // ����roleArray
    for (var i = 0;i < roleArray.length;i++) {
        // ȡ��role
        var role = roleArray[i];

        // ��ȡroleName
        var roleName = role.roleName;

        // ��roleNameƴ�ӵ�roleNameDiv
        $("#roleNameDiv").append(roleName+"<br>");

        // ȡ��roleId
        var roleId = role.roleId;

        // ��roleId�浽roleIdArray
        window.roleIdArray.push(roleId);
    }
}


// ִ�з�ҳ������ҳ��Ч�����κ�ʱ�������������������¼���ҳ��
function generatePage() {
    // 1.��ȡ��ҳ����
    var pageInfo = getPageInfoRemote();
    // 2.�����
    fillTableBody(pageInfo);
}
// ��ȡ��ҳ����
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

    //�жϵ�ǰ״̬��
    var status = reslutAjax.status;
    // �����ǰ��Ӧ״̬�벻�� 200��˵�������˴�������������������ʾ��ʾ��Ϣ���õ�ǰ����ִֹͣ��
    if (status !=200) {
        layer.msg("ʧ�ܣ���Ӧ״̬��="+status+" ˵����Ϣ="+reslutAjax.statusText);
        return null;
    }

    // �����Ӧ״̬���� 200��˵��������ɹ�����ȡ pageInfo
    var resultEntity = reslutAjax.responseJSON;

    // �� resultEntity �л�ȡ result ����
    var result = resultEntity.result;

    // �ж� result �Ƿ�ɹ�
    if(result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // ȷ�� result Ϊ�ɹ����ȡ pageInfo
    var pageInfo = resultEntity.data;

    // ���� pageInfo
    return pageInfo;

}

// �����
function fillTableBody(pageInfo) {
    // ��� tbody �еľɵ�����
    $("#rolePageBody").empty();

    // ���������Ϊ����û���������ʱ����ʾҳ�뵼����
    $("#Pagination").empty();

    $("#sumBox").prop("checked",false);

    // �ж� pageInfo �����Ƿ���Ч
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>��Ǹ��û�в�ѯ������ �������ݣ�</td></tr>");
        return ;
    }

    // ʹ�� pageInfo �� list ������� tbody
    for(var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox' ></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button id='"+roleId+"' type='button' class='checkBtn btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }
    // // ���ɷ�ҳ������
    generatorNavigator(pageInfo);

}

// ���ɷ�ҳҳ�뵼����
function generatorNavigator(pageInfo) {
    // ��ȡ�ܼ�¼��
    var totalRecord = pageInfo.total;
    // �����������
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "��һҳ",
        "next_text": "��һҳ"
    };
    // ���� pagination()����
    $("#Pagination").pagination(totalRecord, properties);
}

//  ��ҳʱ�ص�����
function paginationCallBack(pageIndex,jQuery) {
    // �޸� window ����� pageNum ����
    window.pageNum = pageIndex + 1;
    // ���÷�ҳ����
    generatePage();
    // ȡ��ҳ�볬���ӵ�Ĭ����Ϊ
    return false;
}


function buildAuthTree() {
    var ajaxResult = $.ajax({
        url:'assign/to/get/all/auth',
        type:'post',
        dataType: 'json',
        async: false
    });

    console.log(ajaxResult);
    // ��ȡ״̬��
    var status = ajaxResult.status;
    if (status != 200) {
        // �����Ϊ200 ������ʧ��
        layer.msg("״̬�룺"+status+",��Ϣ��"+ajaxResult.statusText);
        return null;
    }

    // ����ɹ�
    var zNodes = ajaxResult.responseJSON.data;

    var settings = {
        data:{
            key:{
                name:'title'
            },
            simpleData: {
                enable:true,
                pIdKey: 'categoryId'
            },
        },
        check: {
            enable: true
        }
    };

    // ��ʼ�����νṹ
    $.fn.zTree.init($("#treeDemo"), settings, zNodes);

    // չ�����еĽڵ�
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(true);

    var roleId = window.roleId;
    console.log("roleID = "+roleId)

    ajaxResult = $.ajax({
        url:'assign/to/get/auth/for/role',
        type:'post',
        data:{
            roleId:roleId
        },
        dataType:'json',
        async:false
    });

    if (ajaxResult.status != 200) {//����ʧ��
        layer.msg("����ʧ�ܣ������룺"+ajaxResult.status+",������Ϣ��"+ajaxResult.statusText);
    }

    // �õ��ڵ�Id�ļ���
    var authIdList = ajaxResult.responseJSON.data;

    // ��������
    for (var i = 0 ; i < authIdList.length ; i ++) {
        // ��ȡ��authid
        // ����authid�ҵ��ڵ�
        var authId = authIdList[i];
        var node = treeObj.getNodeByParam("id", authId, null);
        // ѡ�нڵ�
        treeObj.checkNode(node, true, false);
    }

}