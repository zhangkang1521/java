package org.zk.thinking.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 8/13/2016.
 */
public class DynamicFields {

    private Object[][] fields;

    public DynamicFields(int initialSize) {
        fields = new Object[initialSize][2];
    }

    private int hasField(String id) {
        if (id == null) {
            return -1;
        }
        for (int i = 0; i < fields.length; i++) {
            if (id.equals(fields[i][0])) {
                return i;
            }
        }
        return -1;
    }

    private int makeField(String id){
        for (int i = 0; i < fields.length; i++) {
            if (fields[i][0] == null) {
                return i;
            }
        }
        Object[][] tmp = new Object[fields.length+1][2];
        for(int i=0; i<fields.length; i++){
            tmp[i][0] = fields[i][0];
            tmp[i][1] = fields[i][1];
        }
        tmp[fields.length][0] = null;
        tmp[fields.length][1] = null;

        fields = tmp;
        return fields.length-1;
    }

    public Object setField(String id, Object value) throws DynamicFieldException {
        if (value == null) {
            DynamicFieldException dfe = new DynamicFieldException();
            throw dfe;
        }
        int pos = hasField(id);
        if(pos==-1){
            pos = makeField(id);
        }

        Object oldValue = fields[pos][1];
        fields[pos][0] = id;
        fields[pos][1] = value;
        return oldValue;
    }

    public static void main(String[] args) throws Exception{
        DynamicFields dynamicFields = new DynamicFields(2);
        for(int i=0; i<6; i++){
            dynamicFields.setField(i+"", i+"hh");
        }
        dynamicFields.setField("1", "xx");
        System.out.println(JSONObject.toJSONString(dynamicFields.fields));
    }
}

class DynamicFieldException extends Exception {

}
