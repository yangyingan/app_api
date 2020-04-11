package com.dm.core.util;


import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class GsonUtil {
    /**
     * 实现格式化的时间字符串转时间对象
     */
    private static final String DATEFORMAT_default = "yyyy-MM-dd HH:mm:ss";

    /**
     * 使用默认的gson对象进行反序列化
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJsonDefault(String json, TypeToken<T> typeToken) {
        Gson gson = getGson();
        return gson.fromJson(json, typeToken.getType());
    }

    /**
     * json字符串转list或者map
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        Gson gson = getGson();
        return gson.fromJson(json, typeToken.getType());
    }

    public static <T> List<T> readJson2Array(String json, Class<T> clz) {
        Gson gson = getGson();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list = new ArrayList<>();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, (Type) clz));
        }
        return list;
    }

    public static Map<byte[], byte[]> readJsonByteMap(String json) {
        Gson gson = getGson();
        Map<String, JsonPrimitive> map = gson.fromJson(json, new TypeToken<Map<String, JsonPrimitive>>() {
        }.getType());
        Map<byte[], byte[]> vmap = new HashMap<>();
        for (String key : map.keySet()) {
            vmap.put(key.getBytes(), gson.fromJson(map.get(key), String.class).getBytes());
        }
        return vmap;
    }

    /**
     * json字符串转bean对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        Gson gson = getGson();
        return gson.fromJson(json, cls);
    }

    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //设置默认时间格式
        gsonBuilder.setDateFormat(DATEFORMAT_default);
        //添加格式化设置
        //gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        try {
            Field factories = Gson.class.getDeclaredField("factories");
            factories.setAccessible(true);
            Object o = factories.get(gson);
            Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
            for (Class c : declaredClasses) {
                if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
                    Field listField = c.getDeclaredField("list");
                    listField.setAccessible(true);
                    List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
                    int i = list.indexOf(ObjectTypeAdapter.FACTORY);
                    list.set(i, GsonUtil.MapTypeAdapter.FACTORY);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson;
    }

    public static class MapTypeAdapter extends TypeAdapter<Object> {
        public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() == Object.class) {
                    return (TypeAdapter<T>) new MapTypeAdapter(gson);
                }
                return null;
            }
        };

        private final Gson gson;

        private MapTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            //判断字符串的实际类型
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;
                case STRING:
                    return in.nextString();
                case NUMBER:
                    String s = in.nextString();
                    if (s.contains(".")) {
                        return Double.valueOf(s);
                    } else {
                        try {
                            return Integer.valueOf(s);
                        } catch (Exception e) {
                            return Long.valueOf(s);
                        }
                    }
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            //noinspection unchecked
            TypeAdapter<Object> typeAdapter = (TypeAdapter<Object>) gson.getAdapter(value.getClass());
            if (typeAdapter instanceof ObjectTypeAdapter) {
                out.beginObject();
                out.endObject();
                return;
            }
            typeAdapter.write(out, value);
        }
    }

    public static void main(String[] args) {
        String s = "{\"Integer\":123,\"Float\":123.0,\"String\":\"abc\",\"Boolean\":true,\"Double\":123.0}";
        Gson gson1 = new GsonBuilder().create();
        Gson gson2 = getGson();
        System.out.println("gson1: "+gson1.fromJson(s,new TypeToken<Map<String,Object>>(){}.getType()));
        System.out.println("gson2: "+gson2.fromJson(s,new TypeToken<Map<String,Object>>(){}.getType()));
    }
}
