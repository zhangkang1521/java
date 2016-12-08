package org.zk.other.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 12/2/2016.
 */
public class VelocityTest {
    public static void main(String[] args) {
        String sqlClause = "hello$a,I am $username!$username!";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("a", "world");
        params.put("username", "zhangkang");
        VelocityContext velocityContext = new VelocityContext(params);
        StringWriter result = new StringWriter();
        Velocity.evaluate(velocityContext, result, "", sqlClause);
        System.out.println(result);
    }
}
