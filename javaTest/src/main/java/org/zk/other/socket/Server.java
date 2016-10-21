package org.zk.other.socket;

import org.zk.other.serialize.Message;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 10/19/2016.
 */
public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(8181);
        Socket socket = server.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message msg = (Message) ois.readObject();
        System.out.println(msg);
        ois.close();
        server.close();
    }
}
