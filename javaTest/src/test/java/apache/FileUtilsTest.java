package apache;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collection;

/**
 * Created by Administrator on 12/8/2016.
 */
public class FileUtilsTest {

    @Test
    public void testListFile() {
        Collection collection = FileUtils.listFiles(new File("E:/tmp"), new String[]{"java"}, true);
    }
}
