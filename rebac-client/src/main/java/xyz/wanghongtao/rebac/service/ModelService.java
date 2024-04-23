package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;

import java.util.List;

public interface ModelService {

    /**
     * 策略模型
     * @param policyDo, modelDo
     */
    void addModel(PolicyDo policyDo, ModelDo modelDo);

    void updatePolicy(PolicyDo policyDo);

    List<ModelDo> getAllModelByStoreId(Long storeId);

    PolicyDo getPolicyById(String id);

    ModelDo getModelById(Long id);
}
