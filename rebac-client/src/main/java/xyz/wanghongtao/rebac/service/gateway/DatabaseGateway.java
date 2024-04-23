package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.model.AddModelForm;
import xyz.wanghongtao.rebac.object.form.relation.AddRelationForm;
import xyz.wanghongtao.rebac.object.form.store.AddStoreForm;
import xyz.wanghongtao.rebac.object.form.relation.DeleteRelationForm;
import xyz.wanghongtao.rebac.object.form.policy.UpdatePolicyForm;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;

import java.util.List;

public interface DatabaseGateway {
    void addModel(AddModelForm addModelForm);

    ModelDo getModelById(Long id);

    List<ModelDo> getAllModelByStoreId(Long storeId);

    PolicyDo getPolicyById(String id);

    void updatePolicy(UpdatePolicyForm updatePolicyForm);

    /**
     * 存储Key
     * @param key appKey与secretKey
     */
    void storeKey(GenerateKey key);

    void deleteKey(String appKey);

    KeyDo getKey(String appKey);

    /**
     * 存储模型
     * @param addStoreForm
     * @return
     */
    AddStore addStore(AddStoreForm addStoreForm);

    List<StoreDo> getAllByAppId(String appKey);

    StoreDo getStoreById(Long id);

    /**
     * 关系
     */
    RelationDo addRelation(AddRelationForm addRelationForm);

    List<RelationDo> getRelationByTriple(String triple);

    List<RelationDo> getRelationByModelId(Long modelId);

    void deleteRelation(DeleteRelationForm deleteRelationForm);
}
