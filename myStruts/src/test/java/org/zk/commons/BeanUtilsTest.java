package org.zk.commons;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ArrayStack;
import org.junit.Test;
import org.zk.entity.Classes;
import org.zk.entity.Course;
import org.zk.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/4.
 */
public class BeanUtilsTest {

    @Test
    public void testSetSimpleProperty() throws Exception {
        User user = new User();

        BeanUtils.setProperty(user, "userName", "zhangkang");
//        BeanUtils.setProperty(user, "agexx", 21);//没有该属性则不设置
        System.out.println(user);
    }

    @Test
    public void testNestProperty() throws Exception {
        User user = new User();
        user.setClasses(new Classes());
        BeanUtils.setProperty(user, "classes.name", "senior 13");//嵌套对象，必须new class()
        System.out.println(user);
    }

    @Test
    public void testSetListProperty() throws Exception{
        User user = new User();
        List<Course> courseList = new ArrayList<Course>();
        courseList.add(new Course());
        user.setCourseList(courseList);

        BeanUtils.setProperty(user, "courseList[0].no", "1001");
        BeanUtils.setProperty(user, "courseList[0].name", "java");
        System.out.println(user);
    }
}

