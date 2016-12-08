package apache;

import com.beust.jcommander.internal.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.zhangkang.entity.User;
import org.zk.other.serialize.Message;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/7/2016.
 */
public class URLEncodeUtilsTest {

    @Test
    public void testFormat() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username", "zk"));
        list.add(new BasicNameValuePair("age", "12"));
        list.add(new BasicNameValuePair("sex", "M"));
        System.out.println(URLEncodedUtils.format(list, "UTF-8"));
    }

    @Test
    public void testParse() {
        String url = "username=zk&age=12&sex=M";
        List<NameValuePair> list = URLEncodedUtils.parse(url, Charset.defaultCharset());
    }


}
