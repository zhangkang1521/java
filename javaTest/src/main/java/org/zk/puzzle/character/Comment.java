package org.zk.puzzle.character;

/**
 * Created by Administrator on 10/5/2016.
 */
public class Comment {

    static String classify(char ch) {
        if ("0123456789".indexOf(ch) >= 0) {
            return "NUMBER";
        }
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(ch) >= 0) {
            return "LETTER";
        }
        // 多行注释不能嵌套
//       /*
//            /* comment this */
//           if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(ch) >= 0) {
//                return "LETTER";
//            }
//        */
        // 最好不要用多行注释，多行注释中包含 */ 会出问题
        if ("+-*/%&|=".indexOf(ch) >= 0) {
            return "OPERATOR";
        }
        return "UNKNOW";
    }

    public static void main(String[] args) {
        System.out.println(classify('a'));
    }
}
