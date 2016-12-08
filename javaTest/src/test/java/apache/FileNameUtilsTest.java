package apache;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Administrator on 12/7/2016.
 */
public class FileNameUtilsTest {

    @Test
    public void testGetExtension() {
        System.out.println(FilenameUtils.getExtension("/home/zk/a.txt"));
        System.out.println(FilenameUtils.getExtension("/home/zk/a.tar.gz"));
        System.out.println(FilenameUtils.getExtension("/home/zk/aa"));
    }

    @Test
    public void testGetBaseName() {
        System.out.println(FilenameUtils.getBaseName("/home/zk/hello.txt"));
        System.out.println(FilenameUtils.getBaseName("/home/zk/hello.tar.gz"));
        System.out.println(FilenameUtils.getBaseName("E://tmp//hello.tar.gz"));
        System.out.println(FilenameUtils.getBaseName("E://tmp/hello.tar.gz"));
        System.out.println(FilenameUtils.getBaseName("E:\\tmp\\hello.tar.gz"));
    }

    @Test
    public void testConcat() {
        System.out.println(FilenameUtils.concat("/home/zk", "hello.tar.gz"));
        System.out.println(FilenameUtils.concat("E:\\home\\zk", "hello.tar.gz"));
        System.out.println(FilenameUtils.concat("E://home//zk", "hello.tar.gz"));
    }

    @Test
    public void testWildcardMatch() {
        System.out.println(FilenameUtils.wildcardMatch("/home/zk/hello.tar.gz", "*.gz"));
        System.out.println(FilenameUtils.wildcardMatch("/home/zk/hello.tar.gz", "/home/zk/*"));
        System.out.println(FilenameUtils.wildcardMatch("/home/zk/hello.tar.gz", "/home/*"));
        System.out.println(FilenameUtils.wildcardMatch("/home/zk/hello.tar.gz", "*.??"));
    }

    @Test
    public void testSeperateToUnix() {
        System.out.println(FilenameUtils.separatorsToUnix("E:\\home\\zk"));
        System.out.println(FilenameUtils.separatorsToUnix("E:\\\\home\\zk"));
    }

    @Test
    public void testGetFullPath() {
        System.out.println(FilenameUtils.getFullPath("/home/zk/hello.txt"));
    }

    @Test
    public void testIsExtension() {
        List<String> extensionList = Lists.newArrayList("txt", "doc");
        System.out.println(FilenameUtils.isExtension("a.txt", extensionList));
        System.out.println(FilenameUtils.isExtension("a.doc", extensionList));
        System.out.println(FilenameUtils.isExtension("a.xls", extensionList));
    }
}
