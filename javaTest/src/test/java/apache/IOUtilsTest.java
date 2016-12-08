package apache;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 12/6/2016.
 */
public class IOUtilsTest {

    @Test
    public void testReadLine() throws Exception{
        Reader reader = null;
        try {
            reader = new FileReader(new File("E:/test.txt"));
            List<String> list = IOUtils.readLines(reader);
            IOUtils.closeQuietly(reader);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Test
    public void testToString() {
        Reader reader = null;
        try {
            reader = new FileReader(new File("E:/test.txt"));
            String str = IOUtils.toString(reader);
            System.out.println(str);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Test
    public void testCopy() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream("E:/test.txt");
            out = new FileOutputStream("E:/test2.txt");
            IOUtils.copy(in, out);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    @Test
    public void testToByteArray() {
        //IOUtils.readFully();
    }
}
