package org.zk.controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangkang on 2016/5/10.
 */
public class ParamServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("param:");
        Map<String, String[]> map = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entrySet = map.entrySet();
        for (Map.Entry<String,String[]> entry : entrySet) {
            System.out.println(entry.getKey() + "=>" + Arrays.toString(entry.getValue()));
        }
    }
}
