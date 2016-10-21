package org.zk.other.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Created by Administrator on 10/19/2016.
 */
public class SerializeDemo {
    public static void main(String[] args) throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E:/message.ser"));
        Message msg = new Message();
        msg.setContent("hello,world");
        msg.setSendTime(new Date());
        oos.writeObject(msg);
        System.out.println(System.identityHashCode(msg));
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:/message.ser"));
        Message msg2 = (Message)ois.readObject();
        System.out.println(msg2.getContent()+","+msg2.getSendTime());
        System.out.println(System.identityHashCode(msg2));
        ois.close();
    }
}
