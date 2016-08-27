package org.zk.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer implements RedisSerializer<Object> {

    public byte[] serialize(Object t) throws SerializationException {
        return t == null ? null : JSON.toJSONBytes(t,
                SerializerFeature.WriteClassName);
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : JSON.parse(bytes, Feature.IgnoreNotMatch);
    }

}
