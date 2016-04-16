package org.zk.commons;

/**
 * Created by Administrator on 2016/4/2.
 */
public class StringUtils {

    /**
     * 去除uri中的contextPath
     * @param uri
     * @return
     */
    public static String removeContextPath(String ctx, String uri) {
        return uri.substring(ctx.length()+1, uri.length());
    }

}
