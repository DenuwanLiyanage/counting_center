package com.example.counting_center.util;

public class AESHelper {
    public static String encrypt(String key, String message){
        AES aes = new AES(key);
        String encodedMsg = GCM.asciiToHex(message);
        return aes.encrypt(encodedMsg);
    }

    public static String decrypt(String key, String message){
        AES aes = new AES(key);
        String msg = aes.decrypt(message);
        return GCM.hexToAscii(msg);
    }
}
