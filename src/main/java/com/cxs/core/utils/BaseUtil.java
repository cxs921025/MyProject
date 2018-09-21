package com.cxs.core.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import sun.misc.BASE64Encoder;

import javax.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author ChenXS
 * Basic tool class.
 */
public class BaseUtil {

    /**
     * 不允许实例化
     */
    private BaseUtil() {
    }

    /**
     * 判断参数实体id是否为null
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> boolean isIdNull(T entity) {
        boolean isNull = false;
        if (entity == null) return isNull;
        MethodAccess methodAccess = MethodAccess.get(entity.getClass());

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                String fs = field.getName();
                int nameIndex = methodAccess.getIndex("get" + String.valueOf(fs.charAt(0)).toUpperCase() + fs.substring(1));
                Object value = methodAccess.invoke(entity, nameIndex, new Object[0]);
                if ((value == null) || ("".equals(value)) || (value == new Integer(0))) {
                    isNull = true;
                    break;
                }
            }
        }
        return isNull;
    }

    /**
     * 生成随机id
     *
     * @return
     */
    public static synchronized String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public static synchronized String encryptionWithMd5(String str){
        MessageDigest md5= null;
        String newStr = str;
        try {
            //确定计算方法
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newStr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newStr;
    }
}
