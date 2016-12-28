package apache;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/25/2016.
 */
public class RegTest {

    // 150 4092

    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream(new FileOutputStream("E:/all_8-10.txt")));
        System.setErr(new PrintStream(new FileOutputStream("E:/maybe_8-·0.txt")));
        String key = "YMGM";
        String value = "5JGR";
        for(int i=8000; i< 10000; i++) {
            String phone = String.format("150%04d4092", i);
            //System.out.println(phone);
            RegResp regResp = check(key, value, phone);
            if(regResp!=null && regResp.getIsOk()) {
                key = regResp.getCASCheckKey();
                value = regResp.getCASCheckVale();
               // System.out.println(phone+" not");
            } else {
                System.err.println(phone+" is registed");
            }
        }
    }



    public static RegResp check(String key, String value, String phone) {
       RegResp regResp = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost("https://passport.banggo.com/CASServer/custom/checkMobileUniqueAjax.do");          //这里用上本机的某个工程做测试
            //创建参数列表
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("mobile", phone));
            list.add(new BasicNameValuePair(key, value));
            String cookie = "loginCodeName=056a5172-b610-4280-8bb5-bb10c078636d; JSESSIONID=D46E045473FB45B852BFF91545EA4DBE; bgi_unique_id=14826543176567521; __ozlvd1903=1482654395; PHPSESSID=ST3037248Et960cTQABdtu0xdZud0passportbanggocom02; old_session_id=ST3037248Et960cTQABdtu0xdZud0passportbanggocom02; bg_uid=ST3037248Et960cTQABdtu0xdZud0passportbanggocom02; Hm_lvt_0600094e16cec594db18b43c878d459f=1482654318; Hm_lpvt_0600094e16cec594db18b43c878d459f=1482654409; _qzja=1.1656376363.1482654317515.1482654317515.1482654317515.1482654406596.1482654409125..0.0.3.1; _qzjc=1; _qzjto=3.1.0; _qzja_dsp=1.2071400729.1482654317521.1482654317521.1482654317521.1482654406601.1482654409130..0.0.3.1; _qzjc_dsp=1; _qzjto_dsp=3.1.0; OZ_1U_2345=vid=v85f826e2061dd.0&ctime=1482654409&ltime=1482654406; OZ_1Y_2345=erefer=https%3A//www.baidu.com/link%3Furl%3DEREgfSS_tOSegEymH8CB6L1njbxO9Q36vBhJnNT3zroo4_7cTDvCDoNZv83HJ1V2%26wd%3D%26eqid%3Dd37838cf0002e39500000004585f8274&eurl=http%3A//metersbonwe.banggo.com/&etime=1482654317&ctime=1482654409&ltime=1482654406&compid=2345; __xsptplus630=630.1.1482654317.1482654409.3%232%7Cwww.baidu.com%7C%7C%7C%7C%23%23wz4_bB4vSEYoV9opq4A_rM8ilbWHKP3f%23; cas_sessionId=D46E045473FB45B852BFF91545EA4DBE; NSC_qbttqpsu-101.227.185.137*443=ffffffff0958113d45525d5f4f58455e445a4a42378b";
            post.setHeader("Cookie", cookie);
            //url格式编码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
            post.setEntity(uefEntity);
            //System.out.println("POST 请求...." + post.getURI());
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    String json = EntityUtils.toString(entity);
                    System.out.println("phone:"+phone+"|"+json);
                    try {
                        regResp = JSONObject.parseObject(json, RegResp.class);
                    }catch (Exception e) {
                        System.out.println(phone+" maybe");
                    }
                }
            } finally {
                httpResponse.close();
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return regResp;
    }

    @Test
    public void testPost() {
        String str = "";
        RegResp regResp = JSONObject.parseObject(str, RegResp.class);
    }
}
