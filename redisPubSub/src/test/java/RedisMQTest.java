import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.redis.RedisMQ;

/**
 * Created by zhangkang on 2016/4/29.
 */
public class RedisMQTest extends BaseTest {

    @Autowired
    RedisMQ redisMQ;

    @Test
    public void testPublish() {
        redisMQ.publish("mail", "hello.world");
    }
}
