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
        $("#reportGrid").datagrid({
            method: "Get",
            url: "/reportAnalysis/json/userInvestInfoReportView.json",
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
               { field: "user_name", title: "注册用户名", width: 150, align: "center" },
               { field: "user_real_name", title: "姓名", width: 150, align: "center" },
               { field: "user_doc_no", title: "身份证号", width: 150, align: "center" },
               { field: "user_phone", title: "手机号", width: 150, align: "center" },
               { field: "user_email", title: "邮箱", width: 150, align: "center" },
               { field: "user_register_date", title: "注册时间", width: 150, align: "center" },
               { field: "tj_user_real_name", title: "推荐人姓名", width: 150, align: "center" },
               { field: "tj_user_phone", title: "推荐人手机", width: 150, align: "right" },
               { field: "total_invest_money", title: "投资合计", width: 150, align: "right" },
               { field: "income_capital", title: "已收本金", width: 150, align: "right" },
               { field: "income_interest", title: "已收利息", width: 150, align: "right" },
               { field: "income_ds_capital", title: "侍收本金", width: 150, align: "center" },
               { field: "income_ds_interest", title: "侍收利息", width: 80, align: "center" },
               { field: "total_valid_invest", title: "借款合计", width: 80, align: "center" },
               { field: "pay_capital", title: "已还本金", width: 80, align: "center" },
               { field: "pay_interest", title: "已还利息", width: 80, align: "center" },
               { field: "pay_dh_capital", title: "侍还本金", width: 80, align: "center" },
               { field: "pay_dh_interest", title: "侍还利息", width: 80, align: "center" },
               { field: "address", title: "联系地址", width: 80, align: "center" }
                    ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#reportGrid').datagrid('getPager');
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
    	$('#searchForm1').attr("action","/reportAnalysis/json/userInvestInfoReportExcel.json");
		$('#searchForm1').submit();
    	
    }

};

