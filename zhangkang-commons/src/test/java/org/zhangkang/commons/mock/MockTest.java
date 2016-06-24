package org.zhangkang.commons.mock;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.zhangkang.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by root on 16-6-5.
 */
public class MockTest {

    UserDao userDao;

    HttpServletRequest request;


    @Before
    public void before(){
        userDao = mock(UserDao.class);
        User user = new User();
        user.setId(100);
        user.setUserName("zk");
        when(userDao.findById(100)).thenReturn(user);

        request = mock(HttpServletRequest.class);
    }

    @Test
    public void test(){
        System.out.println(userDao.findById(100));
    }

    @Test
    public void test2(){
        when(request.getParameter("id")).thenReturn("100");
        String id = request.getParameter("id");
        System.out.println(id);
    }
}
