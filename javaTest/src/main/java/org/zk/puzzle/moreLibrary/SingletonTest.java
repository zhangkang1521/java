package org.zk.puzzle.moreLibrary;

import java.io.*;

/**
 * Created by Administrator on 9/23/2016.
 */
public class SingletonTest {

    public static void main(String[] args) throws Exception{
        Singleton singleton1 = Singleton.INSTANCE;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(singleton1);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Singleton singleton2 = (Singleton)ois.readObject();
        // 不写readResolve返回false
        System.out.println(singleton1 == singleton2);
    }
}


class Singleton implements Serializable{
    public static final Singleton INSTANCE = new Singleton();

    private Singleton(){

    }

    // readObject方法会检查是否有这个方法，有则回调这个方法
    private Object readResolve(){
        return Singleton.INSTANCE;
    }

}