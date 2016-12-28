package org.zk.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;
import org.zhangkang.commons.utils.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Administrator on 12/5/2016.
 */
public class SpringUtilTest {


    @Test
    public void testResource() throws Exception{
        // spring 提供统一资源访问Resource接口
        Resource res1 = new FileSystemResource("E:/test.txt");
        Resource res2 = new ClassPathResource("log4j.properties");
        //TODO ServletContextResource 访问web目录下资源

        // 复制文件
        FileCopyUtils.copy(res1.getFile(), new File("E:/test3.txt"));
        String str = FileCopyUtils.copyToString(new FileReader(res2.getFile()));
        System.out.println(str);

    }

    @Test
    public void testProperties() throws Exception{
        Properties prop = PropertiesLoaderUtils.loadAllProperties("log4j.properties");
        System.out.println(prop);
    }

    @Test
    public void testEscape() {
        System.out.println(JavaScriptUtils.javaScriptEscape("'\""));
        System.out.println(HtmlUtils.htmlEscape("<"));
        System.out.println(HtmlUtils.htmlEscapeDecimal("<"));
        System.out.println(HtmlUtils.htmlEscapeHex("<"));
        System.out.println(HtmlUtils.htmlUnescape("&lt;&#60;&#x3c;"));
    }
}
