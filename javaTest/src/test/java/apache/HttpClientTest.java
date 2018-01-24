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
                41707195,41954408,42195828,43238924,43238924,43586540,44013611,44134154,44135852,44149962,44160349,44323129,44328797,44332776,44348013,43454313,43165082,44407582,44502712,44554967,44589143,42194383,44592120,44624859,44630141,44270975,44769707,44772874,44782738,44798696,44800429,44805295,44835392,44838516,44861592,44871712,45191523,45205560,46136750,46168974
        };
        for (int i = 0; i < arr.length; i++) {
            HttpGet httpGet = new HttpGet("http://super.lvmama.com/vst_order/apportion/apportionAmount.do?force=Y&orderId=" + arr[i]);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse resp = httpClient.execute(httpGet);
            System.out.println((i + 1) + ": " + EntityUtils.toString(resp.getEntity()));
        }
    }
}
