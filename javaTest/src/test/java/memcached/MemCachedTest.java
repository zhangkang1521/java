package memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by zhangkang on 2017/7/21.
 */
public class MemCachedTest {

    MemCachedClient memCachedClient;

    @BeforeTest
    public void testBefore() {
        String[] servers = {"10.200.4.77:11211"};
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();
        memCachedClient = new MemCachedClient();
    }

    @Test
    public void testSet() {
        memCachedClient.set("a", "AAA");
    }

    @Test
    public void testGet() {
        // set key flag expire byte (flag必须是32才能读出来)
        System.out.println(memCachedClient.get("a"));
        System.out.println(memCachedClient.get("b"));
    }
}
