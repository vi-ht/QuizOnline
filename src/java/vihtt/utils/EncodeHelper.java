/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ruby
 */
public class EncodeHelper {

         public static String encode(String originalString) throws NoSuchAlgorithmException {
                  MessageDigest digest = MessageDigest.getInstance("SHA-256");
                  byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
                  return bytesToHex(encodedhash);
         }

         private static String bytesToHex(byte[] hash) {
                  StringBuilder hexString = new StringBuilder(2 * hash.length);
                  for (int i = 0; i < hash.length; i++) {
                           String hex = Integer.toHexString(0xff & hash[i]);
                           if (hex.length() == 1) {
                                    hexString.append('0');
                           }
                           hexString.append(hex);
                  }
                  return hexString.toString();
         }
}

