package org.zk.other.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * https://mp.weixin.qq.com/s?__biz=MjM5NzMyMjAwMA==&mid=2651477088&idx=2&sn=48b90f853985760f47c5ee3f84191e7b&scene=0#rd
 * Created by zhangkang on 2017/8/9.
 */
public class JDK7FileTest {
    public static void main(String[] args) throws Exception{
        Path path = Paths.get("E:/test");
//        File file = path.toFile();
//        boolean deleted = Files.deleteIfExists(path);
//        System.out.println(deleted);
//        System.out.println(Files.size(path));

        // 不要手动拼路径
//        Path path = Paths.get("foo", "bar", "a.txt");
//        System.out.println(path);

        // 读取文件
//        byte[] bytes = Files.readAllBytes(path);
//        List<String> list = Files.readAllLines(path, Charset.defaultCharset());

        // 遍历目录下所有文件
//        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println(file.getFileName());
//                return FileVisitResult.CONTINUE;
//            }
//        });

        File file = new File("./cc.txt");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath()); //每个file都有一个惟一的规范形式

    }
}
