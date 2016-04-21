﻿/*------------------------------------------------
 * Author:潘健  Date：2014-8-26
-------------------------------------------------*/
$(function () {
    var Dialog;
    MyGrid.Resize();
    $("#Del").click(MyAction.Del);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });

});
var MyAction = {
    Init: function () {
        $("#LogDateOperaGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PLogDateOpera.json",
            height: $(window).height() - 82,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "user_name", title: "用户名", width: 150, align: "center" },
            { field: "operation_module", title: "操作模块", width: 150, align: "center" },
            { field: "operation_content", title: "操作内容", width: 150, align: "center" },
            { field: "operation_type", title: "操作类型", width: 150, align: "center" },
            { field: "operation_time", title: "操作时间", width: 150, align: "center" },
            { field: "log_ip", title: "登陆IP", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#LogDateOperaGrid').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50, 100],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onBeforeRefresh: function () {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    Colyn.log("删除成功！")
                    MyAction.Init();
                }
            })
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    }
}