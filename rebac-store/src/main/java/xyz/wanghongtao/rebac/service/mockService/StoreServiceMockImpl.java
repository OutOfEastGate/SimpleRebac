package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.service.StoreService;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "true", matchIfMissing = true)
@Service
public class StoreServiceMockImpl extends AbstractMockService implements StoreService {
  Map<Long, StoreDo> storeDoMap = new HashMap<>();
  Map<Long, KeyDo> keyDoMap = new HashMap<>();
  long keyId = 1;
  long storeId = 1;

  private final String mockStoreDataUrl = "/mockDatabase/mockStoreDatabase.json";
  private final String mockKeyDataUrl = "/mockDatabase/mockKeyDatabase.json";

  {
    storeDoMap = JacksonUtils.fromJsonStr(readFileToString(mockStoreDataUrl), new TypeReference<>() {});
    keyDoMap = JacksonUtils.fromJsonStr(readFileToString(mockKeyDataUrl), new TypeReference<>() {});
  }


  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(storeDoMap), mockStoreDataUrl);
    writeStringToFile(JacksonUtils.toJson(keyDoMap), mockKeyDataUrl);
  }
  @Override
  public void storeKey(GenerateKey key) {
    KeyDo keyDo = new KeyDo();
    keyDo.setAppKey(key.getAppKey());
    keyDo.setSecretKey(key.getSecretKey());
    keyDoMap.put(keyId, keyDo);
    keyId++;
    updateDatabase();
  }

  @Override
  public void deleteKey(String appKey) {
    AtomicReference<Long> id = null;
    keyDoMap.values().forEach(keyDo -> {
      if(keyDo.getAppKey().equals(appKey)) {
        id.set(keyDo.getId());
      }
    });
    keyDoMap.remove(id.get());
    updateDatabase();
  }

  @Override
  public KeyDo getKey(String appKey) {
    for (KeyDo value : keyDoMap.values()) {
      if (value.getAppKey().equals(appKey)) {
        return value;
      }
    }
    return null;
  }

  @Override
  public List<KeyDo> getKeyList() {
    return keyDoMap.values().stream().toList();
  }

  @Override
  public AddStore addStore(StoreDo storeDo) {
    storeDo.setId(storeId);
    storeDoMap.put(storeId, storeDo);
    storeId ++;
    AddStore addStore = new AddStore();
    addStore.setId(storeId);
    updateDatabase();
    return addStore;
  }

  @Override
  public List<StoreDo> getAllByAppId(String appKey) {
    return storeDoMap.values().stream().filter(storeDo -> storeDo.getAppKey().equals(appKey)).collect(Collectors.toList());
  }

  @Override
  public StoreDo getStoreById(Long id) {
    return storeDoMap.get(id);
  }
}
