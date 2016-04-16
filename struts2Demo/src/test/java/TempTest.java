import org.junit.Test;
import org.zhangkang.commons.ShowClass;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/4.
 */
public class TempTest {

    @Test
    public void test1(){
        ShowClass.showInherit(org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter.class);
    }

    @Test
    public void test2(){
        ShowClass.showInherit(com.opensymphony.xwork2.interceptor.AliasInterceptor.class);
    }
}
