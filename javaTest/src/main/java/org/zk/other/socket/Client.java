package org.zk.other.socket;

import org.zk.other.serialize.Message;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 10/19/2016.
 */
public class Client {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket("127.0.0.1", 8181);
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Message msg = new Message();
        msg.setFromUserId(1);
        msg.setToUserId(2);
        msg.setSendTime(new Date());
        msg.setContent("hello,world");
        oos.writeObject(msg);
        oos.close();
        client.close();
    }
}
