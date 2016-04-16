package org.zk.struts2Demo.action;

import org.zk.struts2Demo.entity.User;

/**
 * Created by Administrator on 2016/4/3.
 */
public class UserAction {

    public UserAction(){
        System.out.println("new UserAction()");
    }

    public String add(User user){
        return "error";
    }

    public String add(){
        System.out.print("invoke add()");
        return "success";
    }

}
