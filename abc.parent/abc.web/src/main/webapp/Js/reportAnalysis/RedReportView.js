$(function() {
    MyGrid.Resize();
    MyAction.Init();
    $("#Export").click(MyAction.Export);
    $(window).resize(function() {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function() {
        $("#redReportGrid").datagrid({
            method: "Get",
            url: "/reportAnalysis/json/redReportView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,	
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            idField: "loanId",
            view: myview,
            // 重写当没有数据时
            emptyMsg: '没有找到数据',
            // 返回数据字符
            columns: [[
               { field: "rs_sendtime", title: "派发日期", width: 150, align: "center" },
               { field: "rs_type_name", title: "派发类型", width: 150, align: "center" },
               { field: "rs_theme", title: "派发来源", width: 150, align: "center" },
               { field: "rs_amt", title: "派发金额", width: 150, align: "center" },
               { field: "user_name", title: "注册用户名", width: 150, align: "center" },
               { field: "user_real_name", title: "姓名", width: 150, align: "center" },
               { field: "user_phone", title: "手机号", width: 150, align: "center" },
               { field: "rs_closetime", title: "截止有效期", width: 150, align: "center" },
               { field: "red_type_name", title: "有效标志", width: 150, align: "center" },
               { field: "loan_no", title: "使用红包项目代码", width: 150, align: "center" },
               { field: "ru_amount", title: "使用红包金额", width: 250, align: "center" },
               { field: "in_invest_money", title: "投资金额", width: 150, align: "right" }
                    ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#redReportGrid').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50, 100],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onBeforeRefresh: function() {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
    },
    
    Export: function () {
    	$('#searchForm1').attr("action","/reportAnalysis/json/redReportExcel.json");
		$('#searchForm1').submit();
    	
    }

};

