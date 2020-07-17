<%--
  Created by IntelliJ IDEA.
  User: 82150
  Date: 2020/7/16
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#sendOne").click(function () {
                $.ajax({
                    url:'send/array',
                    type:'post',
                    data:{"array":[2,4,6]},
                    dataType:'text',
                    success:function (result) {
                        alert(result);
                    },
                    error:function (result) {
                        alert(result);
                    }
                });
            });

            $("#sendTwo").click(function () {
                var array = [1,2,3];
                $.ajax({
                    url: 'send/two',  //请求资源的地址
                    type: 'post',     //请求的方式
                    data: JSON.stringify(array),//请求体
                    contentType:'application/json',//请求体内容的类型
                    dataType: 'text',//怎么处理服务器请求的数据
                    success: function (result) {
                        alert(result);
                    },
                    error: function (result) {
                        alert(result);
                    }
                });
            });

            $("#sendThree").click(function () {
                $.ajax({
                    url:'send/three',
                    type:'post',
                    data:JSON.stringify([1,2,3,4,5,6]),
                    contentType: 'application/json;charset=UTF-8',
                    dataType:'json',
                    success:function (result) {
                        console.log(result);
                    },
                    error:function (result) {
                        console.log(result);
                    }
                })
            });
            $("#btn1").click(function () {
               layer.msg("点我干嘛啊 baby")
            });
        })
    </script>
</head>
<body>
index.jsp

<a href="test">点我</a>

<button id="sendOne" >send arr one</button>
<button id="sendTwo" >send arr two</button>

<button id="sendThree" >send arr three</button>
<button id="btn1">点我</button>
</body>
</html>
