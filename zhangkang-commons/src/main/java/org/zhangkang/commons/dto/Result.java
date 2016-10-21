package org.zhangkang.commons.dto;

/**
 * 消息返回体
 * Created by Administrator on 9/23/2016.
 */
public class Result {
    private boolean success = true;
    private int code = 200;
    private String msg = "操作成功";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
