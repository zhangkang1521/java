package org.zk.dto;

/**
 * Created by root on 7/16/16.
 */
public class Result {
    private boolean success = true;
    private String msg = "操作成功";

    public void setErrorMsg(String msg){
        this.success = false;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
