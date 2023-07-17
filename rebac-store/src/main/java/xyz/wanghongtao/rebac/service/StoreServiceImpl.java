package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.AddModelForm;
import xyz.wanghongtao.rebac.object.form.AddStoreForm;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.repository.KeyMapper;
import xyz.wanghongtao.rebac.repository.ModelMapper;
import xyz.wanghongtao.rebac.repository.PolicyRepository;
import xyz.wanghongtao.rebac.repository.StoreMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:59
 */
@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    KeyMapper keyMapper;

    StoreMapper storeMapper;

    ModelMapper modelMapper;

    PolicyRepository policyRepository;

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
    public AddStore addStore(AddStoreForm addStoreForm) {
        StoreDo storeDo = new StoreDo();
        storeDo.setName(addStoreForm.getName());
        storeDo.setDescription(addStoreForm.getDescription());
        storeDo.setAppKey(addStoreForm.getAppKey());

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

    @Override
    public void addModel(AddModelForm addModelForm) {
        PolicyDo policyDo = PolicyDo.builder()
                .description(addModelForm.getPolicy().getDescription())
                .definitions(addModelForm.getPolicy().getDefinitions())
                .build();
        policyRepository.insert(policyDo);

        ModelDo modelDo = ModelDo.builder()
                .storeId(addModelForm.getStoreId())
                .name(addModelForm.getName())
                .description(addModelForm.getDescription())
                .policyId(policyDo.getId())
                .build();
        modelMapper.insert(modelDo);
    }

    @Override
    public List<ModelDo> getAllModelByStoreId(String storeId) {
        LambdaQueryWrapper<ModelDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ModelDo::getStoreId, storeId);
        return modelMapper.selectList(queryWrapper);
    }

    @Override
    public PolicyDo getPolicyById(String id) {
        Optional<PolicyDo> optionalPolicyDo = policyRepository.findById(id);
        return optionalPolicyDo.orElse(null);
    }


}
