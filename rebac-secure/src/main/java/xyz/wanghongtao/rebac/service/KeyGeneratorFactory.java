package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;

/**
 * @author wanghongtao
 * @data 2023/7/16 17:57
 */
@AllArgsConstructor
@Component
public class KeyGeneratorFactory {

    StoreService storeService;

    UUIDKeyGenerate uuidKeyGenerate;

    SHA256KeyGenerate sha256KeyGenerate;

    public KeyService getKeyService(String algorithm) {
        if (algorithm.equals("uuid")) {
            return uuidKeyGenerate;
        } else {
            return sha256KeyGenerate;
        }
    }

    public GenerateKey generateKey(String algorithm) {
        GenerateKey generateKey = getKeyService(algorithm).generateKey();
        //存储
        storeService.storeKey(generateKey);

        return generateKey;
    }

    public KeyDo getKey(String appKey) {
        return storeService.getKey(appKey);
    }
    public void deleteKey(String appKey) {
        storeService.deleteKey(appKey);
    }
}
