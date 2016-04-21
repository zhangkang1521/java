
$(function() {
	MyGrid.Resize();
	$("#TransferFunds").click(MyAction.TransferFunds);// 【资金划转】
	MyAction.Init();
	$("#Search").click(MyAction.Search);// 【搜索】
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
});

var timer;
var Dialog;
var MyAction = {
	// 页面初始化
	Init : function() {
		$("#FullMoneyGrid").datagrid({
			method : "POST",// 请求远程数据的方法类型。
			url : "/moneyManage/json/moneyTransferListView.json?"+createSearchParam(),// 一个用以从远程站点请求数据的超链接地址。
			height : $(window).height() - 52,
			pageSize : 10,// 当设置分页属性时，初始化每页记录数。
			fitColumns : false,// 设置为true将自动使列适应表格宽度以防止出现水平滚动。
			rownumbers : true,// 设置为true将显示行数。
			nowrap : false,// 设置为true，当数据长度超出列宽时将会自动截取。
			striped : false,// 设置为true将交替显示行背景。
			idField : "proLoanId", // 表明该列是一个唯一列。【贷款申请ID】
			remoteSort : true,// 定义是否通过远程服务器对数据排序。
			view : myview,// 定义数据表格的视图。【重写当没有数据时】
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "proLoanNo",
				title : "项目名称",
				width : 270,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 19);
				}
			}, {
				field : "pdoProductName",
				title : "项目类型",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (value == 1) {
						return "信用贷";
					} else if (value == 2) {
						return "抵押贷";
					} else if (value == 3) {
						return "担保贷";
					} else if (value == 4) {
						return "综合贷";
					}
				}
			}, {
				field : "proAddEmp",
				title : "借款人",
				width : 160,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 10);
				}
			}, {
				field : "proAddEmpPhone",
				title : "借款人号码",
				width : 100,	
				align : "center"
				
			}, {
				field : "proLoanMoney",
				title : "借款金额",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "proLoanRate",
				title : "年化收益率",
				width : 90,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatPercent(value, "%");
				}
			}, {
				field : "proLoanPeriod",
				title : "借款期限",
				width : 80,
				align : "center"
			}, {
				field : "proLoanPeriodUnit",
				title : "期限类型",
				width : 30,
				align : "center",
				formatter : function(value, rowData, index) {
					if(value=='1'){return '年'}
					else if(value=='2'){return '月'}
					else if(value=='3'){return '日'}
				}
			}, {
				field : "proFullDateStr",
				title : "满标日期",
				width : 80,
				align : "center"
			}, {
				field : "proLoanUse",
				title : "借款用途",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 10);
				}
			}, {
				field : "govName",
				title : "担保机构",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					return value == null ? "-" : CutString(value, 8);
				}
			}, {
				field : "proStartDateStr",
				title : "划转日期",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (!value) {
						return '-';
					}
					else {
						return value;
					}
				}
			}, {
				field : "pro_loan_state",
				title : "划转状态",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (value == '0') {
						return "待响应";
					} else if (value == '1') {
						return "已划转";
					} else if (value == '2') {
						return "划转失败";
					}
					else {
						return '待划转';
					}
				}
			} ] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#FullMoneyGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 30, 50, 100 ],
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	},
	// 【搜索】
	Search : function() {
		var param = createSearchParam();
		$.post("/moneyManage/json/moneyTransferListView.json", param, function(
				data) {
			$("#FullMoneyGrid").datagrid("loadData", data);
			$("#FullMoneyGrid").datagrid("clearSelections");
		}, "json");
	},
	// 【资金划转】
	// 打开资金划转页面[方法]
	TransferFunds : function(LoanId) {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var LoanState = row.pro_loan_state;// 划转状态
		if (LoanState == '0' || LoanState == '1') {
			Colyn.log("该项目已划转！");
			return;
		}
		var LoanId = row.proLoanId;
		$.AjaxColynJson("/moneyManage/MoneyTransferAddView.json?loanId="	+ LoanId,
				"", function(data) {
					if (data.success) {
			var Dialog = $.hDialog({
			href : "/moneyManage/MoneyTransferAddView?loanId="
					+ LoanId,
			iconCls : 'icon-add',
			title : "满标资金划转",
			width : $(window).width() - 40,
			height : $(window).height() - 50,
			onLoad : function() {
				// 实收手续费改变[事件移除]
				$("#len_collect_fee").die('keyup', MyAction.collectFeeChange);
				// 实收手续费改变
				$("#len_collect_fee").live('keyup', MyAction.collectFeeChange);
				// 生成还款计划表[事件移除]
				$("#btnCrtRepSch").die('click', MyAction.crtRepaySchedule);
				// 生成还款计划表
				$("#btnCrtRepSch").live('click', MyAction.crtRepaySchedule);
				// 获取手机验证码[事件移除]
//				$("#btnCheckCode").die('click', MyAction.getMbphoneVfcCode);
				// 获取手机验证码
//				$("#btnCheckCode").live('click', MyAction.getMbphoneVfcCode);
			},
			onBeforeClose : function() {
				MyAction.Init();
			},
			buttons : [ {
				text : '确认划转',
				iconCls : 'icon-add',
				handler : function() {
					// 确认划转
					MyAction.getCfmTransfer(Dialog);
				}
			}, {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
				
					Dialog.dialog("close");
				}
			} ]
		})
					} else {
						Colyn.log(data.message);
					}
				});

	},
	// 实收手续费改变【资金划转子页面】
	collectFeeChange : function() {
		var varYSSXF = parseFloat($("#len_pay_fee").val());// 应收手续费
		var varYHZJE = parseFloat($("#len_pay_total").val());// 应划转金额
		var varHZJE = parseFloat($("#len_pay_total").val()) + parseFloat($("#len_pay_fee").val());// 划转金额
		var varSSSXF = parseFloat($("#len_collect_fee").val());// 实收手续费
		var varTZZE = varSSSXF - varYSSXF;// 临时变量 = 实收手续费 － 应收手续费

		if (!isNaN(varSSSXF)) {
			if (varSSSXF > varYSSXF) {
				Colyn.log("实收手续费不能大于应收手续费！");
			} else if (varSSSXF < 0) {
				Colyn.log("实收手续费不能小于0！");
			} else {
				$("#len_lend_money").val((varHZJE - varSSSXF).toFixed(2));
			}
		}
	},
	// 生成还款计划表【资金划转子页面】
	crtRepaySchedule : function() {
		if ($('#tbRepaySchedule').form('validate')) {
			var txtPeriods = $("#txtPeriods").val();

			if (txtPeriods == undefined) {
				Colyn.log("Error：未发现[还款期数]对象！");
			} else {
				if (txtPeriods == "") {
					$("#txtPeriods").focus();
					Colyn.log("请输入[还款期数]！");
				} else if (parseInt(txtPeriods) == 0) {
					$("#txtPeriods").focus();
					Colyn.log("请输入大于0的[还款期数]！");
				} else {
					var LoanId = $("#hdfLoanID").val();// 贷款申请ID
					var Periods = $("#txtPeriods").val();// 期数
					$.AjaxColynJson(
							"/moneyManage/json/CreateRepaySchedule.json?loanId="
									+ LoanId + "&periods=" + Periods, "",
							function(data) {
								if (data.success) {
									$("#tbdRepaySchedule").html(data.message);
									$("#hdfPeriods").val(txtPeriods);
								} else {
									Colyn.log(data.message);
								}
							});
				}
			}
		}
	},
	// 获取手机验证码【资金划转子页面】
	getMbphoneVfcCode : function() {
		$.AjaxColynJson("/moneyManage/json/MoneyGetCheckCode.json?t=" + new Date(),
				"", function(data) {
					if (data.success) {
						Colyn.log(data.message);
						MyAction.getMbpVfcCodeAfter();
					} else {
						Colyn.log(data.message);
					}
				});
	},
	// 确认划转【资金划转子页面】
	getCfmTransfer : function(_dialog) {
		if ($('#tbRepaySchedule').form('validate')) {
			if ($('#tbTransferFunds').form('validate')) {
//				var CheckCode = $("#txtCheckCode").val();// 验证码
				var lenCollectFee = $("#len_collect_fee").val();// 实收手续费
				var lendRecord = $("#txtPeriods").val();// 还款期数
				//var hdfPeriods = $("#hdfPeriods").val();// 还款期数[隐藏域]
				//if (lendRecord != hdfPeriods) {
				//	Colyn.log("请先生成[投资人还款计划表]！");
				//} else {
//					var param = $('#Colyn').serializeArray();
//					param = convertArray(param);
//					param = {
//						"param" : JSON.stringify(param)
//					};
					$.AjaxColynJson("/moneyManage/json/MoneyTransferAddData.json?periods="+lendRecord+"&loanId="+loanId+"&lenCollectFee="+lenCollectFee+"&t=" + new Date(),
									'',
									function(data) {
										if (data.success) {
											Colyn.log(data.message);
                                            _dialog.dialog("close");
										} else {
											Colyn.log(data.message);
											_dialog.dialog("close");
										}
									});
					MyAction.Init();
				
			//	}
			}
		}
	},
	// 获取手机验证码后续【资金划转子页面】
	getMbpVfcCodeAfter : function() {
		$("#btnCheckCode").attr('disabled', "true");
		var second = 120;// 倒计时秒
		function ShowCountDown() {
			if (second > 0) {
				second--;
				$("#btnCheckCode").val("剩余" + second + "秒");
			} else {
				
			}

		}
		timer = window.setInterval(function() {
			ShowCountDown();
		}, 1000);
	}
}

function createSearchParam() {
	var form = {
		proLoanNo : $("#pro_loan_no").val(),
		govName : $("#gov_name").val(),
		startProFullDate : $("#start_pro_full_date").datebox("getValue"),
		endProFullDate : $("#end_pro_full_date").datebox("getValue"),
		pdoProductName : $("#pdo_product_name").val(),
		proStartDate : $("#pro_start_date").datebox("getValue"),
		proEndDate : $("#pro_end_date").datebox("getValue"),
		proLoanState : $("#pro_loan_state").val()
	};
	return getParam(form);
}