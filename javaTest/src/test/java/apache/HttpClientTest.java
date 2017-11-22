package apache;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;

/**
 * Created by zhangkang on 2017/11/14.
 */
public class HttpClientTest {
    public static void main(String[] args) throws Exception {
        Integer[] arr = new Integer[]{
                38823973, 40853979, 41435706, 42012599, 42174814, 42336077, 44698284, 44860633, 40652790, 40986131, 41557485, 41565984, 41576120, 41761676, 41771491, 42014261, 43149486, 43155072, 43234100, 43284773, 43406156, 44559113
        };
        for (int i = 0; i < arr.length; i++) {
            HttpGet httpGet = new HttpGet("http://super.lvmama.com/vst_order/apportion/apportionAmount.do?force=Y&orderId=" + arr[i]);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse resp = httpClient.execute(httpGet);
            System.out.println((i + 1) + ": " + EntityUtils.toString(resp.getEntity()));
        }
    }
}
