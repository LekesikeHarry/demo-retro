package com.demoretrofit.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

@Component
public class EncryptionUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String KEY_STRING = "L!i@v#i$a%**r!a@d#h$e";
    private static final String IV_STRING = "3975513975513975";

    private static final Gson gson = new Gson();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public String encryptJsonString(Object object) throws Exception {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = objectMapper.writeValueAsString(object);

        // Ensure the key is 16 bytes (128 bits) long
        byte[] keyBytes = Arrays.copyOfRange(KEY_STRING.getBytes(StandardCharsets.UTF_8), 0, 16);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        // Ensure the IV is 16 bytes long
        byte[] ivBytes = Arrays.copyOfRange(IV_STRING.getBytes(StandardCharsets.UTF_8), 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        byte[] encrypted = encrypt(jsonString.getBytes(StandardCharsets.UTF_8), secretKey, ivParameterSpec);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public <T> T decryptJsonString(String ciphertextBase64, Class <T> classType) throws Exception {
        // Ensure the key is 16 bytes (128 bits) long
        byte[] keyBytes = Arrays.copyOfRange(KEY_STRING.getBytes(StandardCharsets.UTF_8), 0, 16);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // Ensure the IV is 16 bytes long
        byte[] ivBytes = Arrays.copyOfRange(IV_STRING.getBytes(StandardCharsets.UTF_8), 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        byte[] decode = Base64.getDecoder().decode(ciphertextBase64);
        byte[] decrypted = decrypt(decode, secretKey, ivParameterSpec);
        return gson.fromJson(new String(decrypted, StandardCharsets.UTF_8), classType);
//        return new String(decrypted, StandardCharsets.UTF_8);
//        System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
    }

    public static byte[] encrypt(byte[] plaintext, SecretKeySpec key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(byte[] ciphertext, SecretKeySpec key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(ciphertext);
    }
}
