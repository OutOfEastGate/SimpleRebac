package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.AddModelForm;
import xyz.wanghongtao.rebac.object.form.AddRelationForm;
import xyz.wanghongtao.rebac.object.form.AddStoreForm;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.service.ModelService;
import xyz.wanghongtao.rebac.service.RelationService;
import xyz.wanghongtao.rebac.service.StoreService;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * gateway用于处理数据库查询出来的数据，可以与上层模块解耦合
 * @author wanghongtao
 * @data 2023/7/18 22:19
 */
@AllArgsConstructor
@Component
public class DatabaseGatewayImpl implements DatabaseGateway {
    ModelService modelService;

    StoreService storeService;

    RelationService relationService;

    @Override
    public void addModel(AddModelForm addModelForm) {
        PolicyDo policyDo = PolicyDo.builder()
                .description(addModelForm.getPolicy().getDescription())
                .definitions(addModelForm.getPolicy().getDefinitions())
                .build();
        ModelDo modelDo = ModelDo.builder()
                .storeId(addModelForm.getStoreId())
                .name(addModelForm.getName())
                .description(addModelForm.getDescription())
                .build();
        modelService.addModel(policyDo, modelDo);
    }

    @Override
    public List<ModelDo> getAllModelByStoreId(String storeId) {
        return modelService.getAllModelByStoreId(storeId);
    }

    @Override
    public PolicyDo getPolicyById(String id) {
        return modelService.getPolicyById(id);
    }

    @Override
    public void storeKey(GenerateKey key) {
        storeService.storeKey(key);
    }

    @Override
    public void deleteKey(String appKey) {
        storeService.deleteKey(appKey);
    }

    @Override
    public KeyDo getKey(String appKey) {
        return storeService.getKey(appKey);
    }

    @Override
    public AddStore addStore(AddStoreForm addStoreForm) {
        StoreDo storeDo = new StoreDo();
        storeDo.setName(addStoreForm.getName());
        storeDo.setDescription(addStoreForm.getDescription());
        storeDo.setAppKey(addStoreForm.getAppKey());
        return storeService.addStore(storeDo);
    }

    @Override
    public List<StoreDo> getAllByAppId(String appKey) {
        return storeService.getAllByAppId(appKey);
    }

    @Override
    public StoreDo getStoreById(Long id) {
        return storeService.getStoreById(id);
    }

    @Override
    public RelationDo addRelation(AddRelationForm addRelationForm) {
        //解析三元组
        RelationDo relationDo = new RelationDo();

        return relationService.addRelation(relationDo);
    }


}
