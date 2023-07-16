package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;

public interface KeyService {

    /**
     * 生成键值对，用于鉴权
     * @return appKey、SecretKey
     */
    GenerateKey generateKey();
}
