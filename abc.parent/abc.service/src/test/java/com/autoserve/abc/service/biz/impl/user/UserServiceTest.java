package com.autoserve.abc.service.biz.impl.user;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;
/**
 * @author RJQ 2014/11/20 18:01.
 */
public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    // @Test
    public void testFindEntityById() {
        User user = userService.findEntityById(1).getData();
        System.out.println("~~~~~~~~~~~~~~~~~~" + user.getUserName());

    }

    //@Test
    public void testQueryListMobile() {
        PageResult<UserDO> pageResult = userService.queryListMobile(null, "2014-01-01", "2015-01-01 ",
                new PageCondition());
        System.out.println("测试手机搜索条件" + JSON.toJSONString(pageResult));
    }

    //@Test
    public void testCreate() {
        UserDO userDO = new UserDO();
        userDO.setUserName("asdasd");
        userDO.setUserEmailIsbinded(1);
        userDO.setUserMobileIsbinded(1);
        userDO.setUserLoanCredit(new BigDecimal(10000));
        BaseResult baseResult = userService.createUser(userDO);
        System.out.println(baseResult.getMessage());
    }
    

    @Test
    public void testComputeSettCredit(){
        System.out.println();
    }

  //  @Test
    public void testModifyUserBusinessState(){
        userService.modifyUserBusinessState(1, UserType.PERSONAL, UserBusinessState.REGISTERED);
    }
}
