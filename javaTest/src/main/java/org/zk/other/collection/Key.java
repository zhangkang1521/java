package org.zk.other.collection;

import com.google.common.hash.HashCode;

import java.util.Random;

/**
 * Created by Administrator on 10/19/2016.
 */
public class Key {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
        //return new Random().nextInt();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Key) {
            Key key = (Key)obj;
            if(key.id.equals(this.id)) {
                return true;
            }
        }
        return false;
    }
}
