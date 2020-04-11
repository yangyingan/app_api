package com.dm.core.annotation.redis;

import com.dm.core.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisServiceImpl:
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Override
    public boolean set(String key, Object value) throws Exception {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(GsonUtil.toJson(value)));
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean set(String key, Object value, long expireInSeconds) throws Exception {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.setEx(serializer.serialize(key),expireInSeconds,serializer.serialize(GsonUtil.toJson(value)));
                return true;
            }
        });
        return result;
    }

    @Override
    public Object get(final String key) throws Exception {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public <T> T get(String key, Class<T> clz) throws Exception {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return GsonUtil.fromJson(result,clz);
    }

    @Override
    public boolean setNx(final String key, final Object value) {
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                boolean flag = connection.setNX(serializer.serialize(key), serializer.serialize(GsonUtil.toJson(value)));
                return flag;
            }
        });
    }

    @Override
    public boolean setNx(final String key, final Object value, final long expireSeconds) {
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                boolean flag = connection.setNX(serializer.serialize(key), serializer.serialize(GsonUtil.toJson(value)));
                if(flag){
                    connection.expire(serializer.serialize(key), expireSeconds);
                }
                return flag;
            }
        });
    }

    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public Long getExpire(String key) throws Exception {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Long incr(String key) throws Exception {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long value=connection.incr(serializer.serialize(key));
                return value;
            }
        });
    }

    @Override
    public Long incrBy(String key, long stepValue) throws Exception {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long value=connection.incrBy(serializer.serialize(key),stepValue);
                return value;
            }
        });
    }

    @Override
    public Long decr(String key) throws Exception {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long value=connection.decr(serializer.serialize(key));
                return value;
            }
        });
    }

    @Override
    public Long decrBy(String key, long stepValue) throws Exception {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long value=connection.decrBy(serializer.serialize(key),stepValue);
                return value;
            }
        });
    }

    @Override
    public <T> boolean setList(String key, List<T> list) throws Exception {
        return set(key,list);
    }

    @Override
    public <T> List<T> getList(String key,Class<T> clz)  throws Exception{
        Object json = get(key);
        if(json!=null){
            List<T> list = GsonUtil.readJson2Array(json.toString(),clz);
            return list;
        }
        return null;
    }

    @Override
    public void del(final String key) throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection conn) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return conn.del(serializer.serialize(key));
            }
        });
    }

    @Override
    public long lpush(final String key, Object obj)throws Exception {
        Assert.hasText(key,"Key is not empty.");
        final String value = GsonUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public long rpush(final String key, Object obj) throws Exception{
        Assert.hasText(key,"Key is not empty.");
        final String value = GsonUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public void hmset(String key, Object obj)  throws Exception{
        Assert.hasText(key,"Key is not empty.");
        Map<byte[], byte[]> data=GsonUtil.readJsonByteMap(GsonUtil.toJson(obj));
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.hMSet(serializer.serialize(key),data);
                return "";
            }
        });
    }

    @Override
    public <T> T hget(String key, Class<T> clz)  throws Exception{
        Assert.hasText(key,"Key is not empty.");
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Map<String,Object> result;
                Map<byte[],byte[]> data=connection.hGetAll(serializer.serialize(key));
                result= new HashMap<>();
                for (Map.Entry<byte[], byte[]> entry: data.entrySet()) {
                    result.put(serializer.deserialize(entry.getKey()),serializer.deserialize(entry.getValue()));
                }
                return GsonUtil.fromJson(GsonUtil.toJson(result),clz);
            }
        });
    }

    @Override
    public<T> List<T>  hmGetAll(String key,Class<T> clz) throws Exception{
        Assert.hasText(key,"Key is not empty.");
        List<Map<String,Object>> dataList= new ArrayList<>();
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Set<String> keysSet=redisTemplate.keys(key);
                Map<byte[],byte[]> data;
                Map<String,Object> result;
                for(String newKey:keysSet) {
                    data=connection.hGetAll(serializer.serialize(newKey));
                    result= new HashMap<>();
                    for (Map.Entry<byte[], byte[]> entry: data.entrySet()) {
                        result.put(serializer.deserialize(entry.getKey()),serializer.deserialize(entry.getValue()));
                    }
                    dataList.add(result);
                }
                return GsonUtil.readJson2Array(GsonUtil.toJson(dataList),clz);
            }
        });
    }

    @Override
    public String lpop(final String key) throws Exception{
        Assert.hasText(key,"Key is not empty.");
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res =  connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }
}
