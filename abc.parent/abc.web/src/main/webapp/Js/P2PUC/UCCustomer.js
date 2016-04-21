﻿var dialog;
var loanType;


var MyDialog = {

    //选择担保机构
    chooseGuaranteeGov: function () {
        var duty = $(this).attr("_duty");
        var govId = $("#govId").val();
        dialog = $.hDialog({
            title: '担保机构信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/government/guaranteeGovListView?duty=' + duty + "&govId=" + govId,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForGuaranteeGov.chooseGuaranteeGov();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
    //选择相关的担保机构
    chooseRelatedGuarGov: function () {
        var duty = $(this).attr("_duty");
        var govId = $("govId").val();
        dialog = $.hDialog({
            title: '担保机构信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/government/relatedGuarGovListView?duty=' + duty + "&govId=" + govId,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForGuaranteeGov.chooseGuaranteeGov();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
    //选择借款机构
    chooseLendingGov: function () {
        var duty = $(this).attr("_duty");
        dialog = $.hDialog({
            title: '借款机构信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/government/LendingGovListView?duty=' + duty,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForLendingGov.chooseLendingGov();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
    //选择机构(担保机构和信贷机构)
    chooseAllGov: function () {
        var duty = $(this).attr("_duty");
        dialog = $.hDialog({
            title: '机构信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/government/allGovListView?duty=' + duty,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForAllGov.chooseAllGov();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
//    chooseBorrower: function () {
//        var Dialog = $.hDialog({
//            content: "<iframe src='/P2PUC/BorrowerListView' height='" + ($(window).height() - 58) + "' width='" + ($(window).width() - 20) + "'  frameborder='0'  scrolling='no' ></iframe>",
//            //href: "/P2PUC/BorrowerListView",
//            iconCls: 'icon-add',
//            title: "借款人信息查看",
//            width: 1000,
//            height: $(window).height() - 50,
//            buttons: [{
//                text: '确认选择',
//                iconCls: 'icon-ok',
//                handler: function () {
//                    Dialog.dialog("close");
//                }
//            }, {
//                text: '关闭',
//                iconCls: 'icon-cancel',
//                handler: function () {
//                    Dialog.dialog("close");
//                }
//            }]
//        })
//    },

    chooseBorrower: function () {
        var duty = $(this).attr("_duty");
       //var category = $(this).attr("_category");
        if(undefined == loanType){
        	loanType = 1
        }
        
        if(loanType==1){
        	//显示普通用户
        	 dialog = $.hDialog({
                 title: '借款人信息',
                 width: 800,
                 height: 400,
                 cache: false,
                 href: '/user/UserListView?duty=' + duty +'&loanType='+loanType,
                 modal: true,
                 onClose: function () {

                 }, buttons: [{
                     text: '确认选择',
                     iconCls: 'icon-ok',
                     handler: function () {
                         MyActionForUser.chooseUser();
                     }
                 }, {
                     text: '关闭',
                     iconCls: 'icon-cancel',
                     handler: function () {
                         dialog.dialog("close");
                     }
                 }]

             });
        } else if(loanType==2){
        	//显示企业用户
        	 dialog = $.hDialog({
                 title: '借款人信息',
                 width: 800,
                 height: 400,
                 cache: false,
                 href: '/user/companyListView?duty=' + duty +'&loanType='+loanType,
                 modal: true,
                 onClose: function () {

                 }, buttons: [{
                     text: '确认选择',
                     iconCls: 'icon-ok',
                     handler: function () {
                         MyActionForUser.chooseUser();
                     }
                 }, {
                     text: '关闭',
                     iconCls: 'icon-cancel',
                     handler: function () {
                         dialog.dialog("close");
                     }
                 }]

             });
        }
       
    },

    //选择客户经理
    chooseCustMan: function () {
        var duty = $(this).attr("_duty");
        dialog = $.hDialog({
            title: '客户经理信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/employee/customerManagerList?duty=' + duty,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForAdminMa.chooseAdminMa();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
    //选择用户账户信息
    chooseUserAccount: function () {
        var duty = $(this).attr("_duty");
        dialog = $.hDialog({
            title: '用户账户信息',
            width: 700,
            height: $(window).height() - 50,
            cache: false,
            href: '/account/userAccountListView?duty=' + duty,
            modal: true,
            onClose: function () {

            }, buttons: [{
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    MyActionForUserAcc.chooseUserAcc();
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        });
    },
    selectPayName: function (event) {
	    var value = $("#loan_pay_organ").val();
	    var duty = $(this).attr("_duty");
	
	    if(value==""){
	    	var params = MyDialog.GetParam(duty);
	            $("#" + params.nameId).val("");
	            $("#" + params.valueId).val("");
	    }
    	if(value=="0"){
   	        var category = $(this).attr("_category");
   	  
   	        dialog = $.hDialog({
   	            title: '借款人信息',
   	            width: 800,
   	            height: 400,
   	            cache: false,
   	            href: '/user/UserListView?duty=' + duty + "&category=" + category,
   	            modal: true,
   	            onClose: function () {

   	            }, buttons: [{
   	                text: '确认选择',
   	                iconCls: 'icon-ok',
   	                handler: function () {
   	                    MyActionForUser.chooseUser();
   	                }
   	            }, {
   	                text: '关闭',
   	                iconCls: 'icon-cancel',
   	                handler: function () {
   	                    dialog.dialog("close");
   	                }
   	            }]

   	        });
   	}
    	
    	if(value=="1"){
            var govId = $("#govId").val();
            dialog = $.hDialog({
                title: '担保机构信息',
                width: 800,
                height: 400,
                cache: false,
                href: '/government/guaranteeGovListView?duty=' + duty + "&govId=" + govId,
                modal: true,
                onClose: function () {

                }, buttons: [{
                    text: '确认选择',
                    iconCls: 'icon-ok',
                    handler: function () {
                        MyActionForGuaranteeGov.chooseGuaranteeGov();
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog("close");
                    }
                }]

            });
    	}
    },
    GetParam: function (param) {
        var Para = {
            isSingleSelect: true,
            isHtml: false,
            nameId: '',
            valueId: ''
        };
        var paramArr = param.split('|');
        if (paramArr.length > 2) {
            if (paramArr[0].toLowerCase() == "false") {
                Para.isSingleSelect = false;
            }
            if (paramArr[1].toLowerCase() == "true") {
                Para.isHtml = true;
            }
            Para.nameId = paramArr[2];
            Para.valueId = paramArr[3];
        } else {
            Para.nameId = paramArr[0];
            Para.valueId = paramArr[1];
        }
        return Para;
    },
    selectPayStatus:function (param) {
     var type =$("#payee_status").val();
   	 switch (type) {
        case "0": {
       	 document.getElementById("trNode").style.display="none"; 
       	 break;
        } case "1": {
        	 document.getElementById("trNode").style.display=""; 
        break;
        }
   	 }
   }
};



$(function () {
    //图片增加class=evt_Select 客户名名称 input 增加class=cin_Name
    //页面增加隐藏域 <input type="hidden" class="cusId" name="cin_Id" value="" /> 用来存客户Id
    //点击事件 
    //$(".evt_Select").click(function () {
    //    dialog = $('#cus').dialog({
    //        title: '客户名称',
    //        width: 600,
    //        height: 200,
    //        cache: false,
    //        href: '/DBUC/GetCustomList',
    //        modal: true,
    //        onClose: function () {

    //        }
    //    });
    //});



    ////给文本框赋值
    //$("input[name='cin_CompName']").live("click", function () {
    //    var id = $(this).attr("_attrvalue");
    //    var value = $(this).attr("value");
    //    var obj = $("#wrap").find(".cin_Name");
    //    var obj1 = $("#wrap").find(".cusId");
    //    obj.val(value);
    //    obj1.val(id);
    //    dialog.dialog("close");
    //    obj.focus();
    //});
});
$(function () {
    //选择担保机构（所有的担保机构）
    $(".evt-GuaranteeGov").live("click", MyDialog.chooseGuaranteeGov);
    //选择相关的担保机构
    $(".evt-RelatedGuarGov").live("click", MyDialog.chooseRelatedGuarGov);
    //选择借款机构
    $(".evt-LendingGov").live("click", MyDialog.chooseLendingGov);
    //选择机构(担保机构和信贷机构)
    $(".evt-AllGov").live("click", MyDialog.chooseAllGov);
    //选择借款人
    $(".evt-Borrower").live("click", MyDialog.chooseBorrower);
    //客户经理
    $(".evt-CustMan").live("click", MyDialog.chooseCustMan);
    //用户账户
    $(".evt-UserAccount").live("click", MyDialog.chooseUserAccount);

    $("#loan_pay_organ").die().live("change",MyDialog.selectPayName);
    
    $("#payee_status").live("change",MyDialog.selectPayStatus);
    
});
