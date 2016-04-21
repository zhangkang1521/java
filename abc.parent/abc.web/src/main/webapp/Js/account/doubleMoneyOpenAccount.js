$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#submit").click(MyAction.SubmitData);

});

var MyAction = {
	Init:function (){
		    var empId = $("#empId").val();
	        var o2 = { empId: empId};
	                $.post("/account/doubleMoneyOpenAccount.json", o2, function (data) {
	                       if (data.success) {
	                    	   $("#RealName").val(data.data.RealName);
	                    	   $("#IdentificationNo").val(data.data.IdentificationNo);
	                    	   $("#Mobile").val(data.data.Mobile);
	                    	   $("#Email").val(data.data.Email);
	                    	   $("#AccountMark").val(data.data.AccountMark);
	                    	   if($("#AccountMark").val()=="1"){
	                    		   $("#submit").html('已开户');
	                    	   }
	                        }
	                        else {
	                            Colyn.log(data.message);
	                        }
	                    })
	 },	
    SubmitData: function () {
    	var AccountMark = $("#AccountMark").val();
    	if(AccountMark == "1"){
    		 Colyn.log("该用户已开户!");
    		 return;
    	}
    		var empId=$("#empId").val();
    	    var empName=$("#empName").val();
    	    var RealName =$("#RealName").val();
    	    var IdentificationNo = $("#IdentificationNo").val();
    	    var Mobile =$("#Mobile").val();
    	    var Email =$("#Email").val();
           var  url = "/account/dryOpenAccount?RealName="+RealName+"&empId="+empId+"&empName="+empName+"&IdentificationNo="+IdentificationNo+"&Mobile="+Mobile+"&Email="+Email;
           window.open(encodeURI(url), 'newwindow', 'height=500, width=800, top=90,left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
    }
}


