package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;

import java.util.List;

public interface StoreService {

    /**
     * 存储Key
     * @param key appKey与secretKey
     */
    void storeKey(GenerateKey key);

    void deleteKey(String appKey);

    KeyDo getKey(String appKey);

    /**
     * 存储模型
     * @param storeDo
     * @return
     */
    AddStore addStore(StoreDo storeDo);

    List<StoreDo> getAllByAppId(String appKey);

    StoreDo getStoreById(Long id);

}
