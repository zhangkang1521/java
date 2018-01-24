package org.zk;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES Coder<br/>
 * secret key length:	56 bit, default:	56 bit<br/>
 * mode:	ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding:	Nopadding/PKCS5Padding/ISO10126Padding/
 * @author Aub
 *
 */
public class DESCoder {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
//	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/ISO10126Padding";


    /**
     * 初始化密钥
     *
     * @return byte[] 密钥
     * @throws Exception
     */
    public static byte[] initSecretKey() throws Exception{
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
//        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
//        //初始化此密钥生成器，使其具有确定的密钥大小
//        kg.init(56);
//        //生成一个密钥
//        SecretKey  secretKey = kg.generateKey();
        String secretKey = "LVMAMA_VER_CODE_KEY";
        return secretKey.getBytes();
    }

    /**
     * 转换密钥
     *
     * @param key	二进制密钥
     * @return Key	密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception{
        //实例化DES密钥规则
        DESKeySpec dks = new DESKeySpec(key);
        //实例化密钥工厂
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成密钥
        SecretKey  secretKey = skf.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密
     *
     * @param data	待加密数据
     * @param key	密钥
     * @return byte[]	加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,Key key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data	待加密数据
     * @param key	二进制密钥
     * @return byte[]	加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param data	待加密数据
     * @param key	二进制密钥
     * @param cipherAlgorithm	加密算法/工作模式/填充方式
     * @return byte[]	加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密
     *
     * @param data	待加密数据
     * @param key	密钥
     * @param cipherAlgorithm	加密算法/工作模式/填充方式
     * @return byte[]	加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }



    /**
     * 解密
     *
     * @param data	待解密数据
     * @param key	二进制密钥
     * @return byte[]	解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data	待解密数据
     * @param key	密钥
     * @return byte[]	解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,Key key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data	待解密数据
     * @param key	二进制密钥
     * @param cipherAlgorithm	加密算法/工作模式/填充方式
     * @return byte[]	解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    /**
     * 解密
     *
     * @param data	待解密数据
     * @param key	密钥
     * @param cipherAlgorithm	加密算法/工作模式/填充方式
     * @return byte[]	解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    private static String  showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b:data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    public static String encrypt(String data) throws Exception{
        byte[] key = initSecretKey();
        Key k = toKey(key);
        byte[] encryptData = encrypt(data.getBytes(), k);
        return Hex.encodeHexStr(encryptData);
    }

    public static String decrypt(String data) throws Exception{
        byte[] key = initSecretKey();
        Key k = toKey(key);
        byte[] decryptData = decrypt(Hex.decodeHex(data.toCharArray()), k);
        return new String(decryptData);
    }
    
    public static String encrypt(String data, String keystr ) throws Exception{
    	Key key = toKey(keystr.getBytes());
    	byte[] encryptData = encrypt(data.getBytes(), key);
    	String encrypPasswdString =  Hex.encodeHexStr(encryptData);
    	return encrypPasswdString;
    }
    
    public static String decrypt(String encryptdata,String keystr) throws Exception{
    	Key key = toKey(keystr.getBytes());
    	byte[] decryptByte = decrypt(Hex.decodeHex(encryptdata.toCharArray()), key);
    	return new String(decryptByte);
    }
    
    public static void main(String[] args) throws Exception {
    	//卡号+"_时间戳"
    	String passwd = "asdf";
    	String keystr = "123_456_789_1011";
    	
    	String encryptData = encrypt(passwd, keystr);
    	System.out.println(encryptData);
    	String decryptData = decrypt(encryptData, keystr);
    	System.out.println(decryptData);
    	
    	System.out.println("--------");
        String en = encrypt(DateUtil.formatSimpleDate());
        System.out.println(en);
        //System.out.println(decrypt(en)  + "    data  " + DateUtil.formatSimpleDate(DateUtil.getTodayDate()));
    }
    
    private static class DateUtil{
    	
    	private static String formatSimpleDate(){
    		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	}
    }
}

class Hex {

    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data
     *            byte[]
     * @return 十六进制char[]
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data
     *            byte[]
     * @param toLowerCase
     *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制char[]
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data
     *            byte[]
     * @param toDigits
     *            用于控制输出的char[]
     * @return 十六进制char[]
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data
     *            byte[]
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data
     *            byte[]
     * @param toLowerCase
     *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data
     *            byte[]
     * @param toDigits
     *            用于控制输出的char[]
     * @return 十六进制String
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param data
     *            十六进制char[]
     * @return byte[]
     * @throws RuntimeException
     *             如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch
     *            十六进制char
     * @param index
     *            十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException
     *             当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

}


