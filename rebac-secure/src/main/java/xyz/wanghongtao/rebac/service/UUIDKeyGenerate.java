package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;

import java.util.UUID;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:36
 */
@AllArgsConstructor
@Service
public class UUIDKeyGenerate implements KeyService {

    @Override
    public GenerateKey generateKey() {
        GenerateKey generateKey = new GenerateKey();
        generateKey.setAppKey(UUID.randomUUID().toString());
        generateKey.setSecretKey(UUID.randomUUID().toString());
        return generateKey;
    }
}
