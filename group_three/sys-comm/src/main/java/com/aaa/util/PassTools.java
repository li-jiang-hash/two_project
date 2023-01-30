package com.aaa.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Teacher陈（86521760@qq.com）
 * @date ：Created in 2022/6/2 10:53
 * @description：我的工具类
 * @modified By：
 * @version: 1.0
 */
public class PassTools {


    /**
     * @param password
     * @return newPassword
     * @create by: Teacher陈（86521760@qq.com）
     * @description: 密码加密
     * @create time: 2022/6/6 9:53
     */
    public static String makePassword(String password) {
        String md5 = SecureUtil.md5(password);
        return md5;
    }
    /* */

    /**
     * @param password  明文密码
     * @return map  返回封装了加密之后的密码和盐值
     * @create by: Teacher陈（86521760@qq.com）
     * @description: 密码加盐加密
     * @create time: 2022/6/6 9:53
     */
    public static Map<String, String> makePasswordSalt(String password) {
        Map<String, String> map = new HashMap<>();
        //随机生成密钥(盐值)
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        //将盐值转换格式（方便数据库保存）,byte数组转字符串
        String salt = Base64.encode(key);

        //构建加密对象
        SymmetricCrypto aes = null;
        //Base64.decode(salt)，将字符串转换成byte数组
        aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Base64.decode(salt));
        //加密为16进制表示
        String encryptHex = aes.encryptHex(password);
        //加密之后的密文
        map.put("password", encryptHex);
        //盐值，保存盐值的作用是为了登录
        map.put("salt", salt);
        return map;
    }
    /**
     * @param1 password  明文密码
     * @param2 salt  盐值
     * @return string 密文
     * @create by: Teacher陈（86521760@qq.com）
     * @description: 密码加盐加密
     * @create time: 2022/6/6 9:53
     */
    public static String makePasswordSalt(String password,String salt) {
        //构建
        SymmetricCrypto aes = null;
        aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Base64.decode(salt));
        //加密为16进制表示
        String encryptHex = aes.encryptHex(password);
        return encryptHex;
    }

    public static void main(String[] args)  {
        Map<String, String> stringStringMap = PassTools.makePasswordSalt("552157");
        System.out.println("stringStringMap = " + stringStringMap);
    }
}
