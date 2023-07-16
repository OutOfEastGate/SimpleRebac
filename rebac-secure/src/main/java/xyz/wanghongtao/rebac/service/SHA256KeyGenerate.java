package xyz.wanghongtao.rebac.service;

import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author wanghongtao
 * @data 2023/7/16 17:56
 */
@Service
public class SHA256KeyGenerate implements KeyService {
    @Override
    public GenerateKey generateKey() {
        GenerateKey generateKey = new GenerateKey();

        SecureRandom random = new SecureRandom();
        byte[] keyBytes1 = new byte[32]; // 32字节 = 256位
        byte[] keyBytes2 = new byte[32];
        random.nextBytes(keyBytes1);
        random.nextBytes(keyBytes2);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashedBytes1 = digest.digest(keyBytes1);
        byte[] hashedBytes2 = digest.digest(keyBytes2);

        generateKey.setAppKey(Base64.getEncoder().encodeToString(hashedBytes1));
        generateKey.setSecretKey(Base64.getEncoder().encodeToString(hashedBytes2));
        return generateKey;
    }
}
