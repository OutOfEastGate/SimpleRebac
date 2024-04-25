package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.repository.KeyMapper;
import xyz.wanghongtao.rebac.repository.StoreMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:59
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "false", matchIfMissing = true)
@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    KeyMapper keyMapper;

    StoreMapper storeMapper;

    @Override
    public void storeKey(GenerateKey key) {
        KeyDo keyDo = new KeyDo();
        keyDo.setAppKey(key.getAppKey());
        keyDo.setSecretKey(key.getSecretKey());
        int insert = keyMapper.insert(keyDo);
        if (insert != 1) throw new CustomException(ErrorCode.Key_Generate_Error);
    }

    @Override
    public void deleteKey(String appKey) {
        LambdaQueryWrapper<KeyDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KeyDo::getAppKey, appKey);
        keyMapper.delete(wrapper);
    }

    @Override
    public KeyDo getKey(String appKey) {
        LambdaQueryWrapper<KeyDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KeyDo::getAppKey, appKey);
        return keyMapper.selectOne(wrapper);
    }

  @Override
  public List<KeyDo> getKeyList() {
    return keyMapper.selectList(new LambdaQueryWrapper<>());
  }

  @Override
    public AddStore addStore(StoreDo storeDo) {
        int insert = storeMapper.insert(storeDo);
        if (insert != 1) throw new CustomException(ErrorCode.Add_Store_Error);

        AddStore addStore = new AddStore();
        addStore.setId(storeDo.getId());
        return addStore;
    }

    @Override
    public List<StoreDo> getAllByAppId(String appKey) {
        LambdaQueryWrapper<StoreDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreDo::getAppKey, appKey);
        return storeMapper.selectList(queryWrapper);
    }

    @Override
    public StoreDo getStoreById(Long id) {
        return storeMapper.selectById(id);
    }


}
