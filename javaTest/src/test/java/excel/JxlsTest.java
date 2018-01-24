package excel;

import net.sf.jxls.transformer.XLSTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangkang on 2018/1/24.
 */
public class JxlsTest {
    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setUsername("zk");
        Map beans = new HashMap();
        beans.put("user", user);
        beans.put("title", "hello");
        List<User> list = new ArrayList<>();
        for(int i=0; i< 5; i++) {
            User tmp = new User();
            tmp.setUsername("zk" + i);
            list.add(tmp);
        }
        beans.put("list", list);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS("E:/a.xlsx", beans, "E:/b.xlsx");
    }

}


