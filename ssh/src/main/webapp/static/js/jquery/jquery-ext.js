/**
 * Created by root on 7/16/16.
 */

// 设置jQuery Ajax全局的参数
$.ajaxSetup({
    type: "POST",
    timeout: 5000, // 5min
    error: function (jqXHR, textStatus, errorThrown) {
        if (textStatus == 'timeout') {
            alert('请求超时');
            return;
        }
        switch (jqXHR.status) {
            case(500):
                alert("服务器系统内部错误");
                break;
            case(401):
                alert("未登录");
                break;
            case(403):
                alert("无权限执行此操作");
                break;
            case(408):
                alert("请求超时");
                break;
            default:
                alert("未知错误");
        }
    }
});


(function ($) {
    //备份jquery的ajax方法
    var _ajax = $.ajax;

    //重写jquery的ajax方法
    $.ajax = function (opt) {
        //扩展增强处理
        var _opt = $.extend(opt, {});
        // append a timestamp param,avoid cache
        var oldUrl = _opt.url;
        var timestamp = new Date().getTime();
        if (oldUrl.indexOf('?') == -1) {
            _opt.url = oldUrl + '?t=' + timestamp;
        } else {
            _opt.url = oldUrl + '&t=' + timestamp;
        }
        _ajax(_opt);
    };
})(jQuery);

