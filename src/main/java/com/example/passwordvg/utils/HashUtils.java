package com.example.passwordvg.utils;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtils {


    public String hashText(String text, Enum<HashAlgorithm> HashAlgorithm){
        try {

            MessageDigest md = MessageDigest.getInstance(HashAlgorithm.toString());

            md.update(text.getBytes());

            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
