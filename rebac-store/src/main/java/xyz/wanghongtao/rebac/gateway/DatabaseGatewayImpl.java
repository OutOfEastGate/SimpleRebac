package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.engine.model.ModelEngine;
import xyz.wanghongtao.rebac.engine.relation.RelationEngine;
import xyz.wanghongtao.rebac.engine.user.UserLoginEngine;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.context.RelationContext;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.model.AddModelForm;
import xyz.wanghongtao.rebac.object.form.model.DeleteModelForm;
import xyz.wanghongtao.rebac.object.form.relation.AddRelationForm;
import xyz.wanghongtao.rebac.object.form.store.AddStoreForm;
import xyz.wanghongtao.rebac.object.form.relation.DeleteRelationForm;
import xyz.wanghongtao.rebac.object.form.policy.UpdatePolicyForm;
import xyz.wanghongtao.rebac.object.form.user.LoginForm;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.object.runtime.UserLoginRuntime;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.object.viewObject.user.LoginResult;
import xyz.wanghongtao.rebac.object.viewObject.user.RegisterResult;
import xyz.wanghongtao.rebac.service.KeyService;
import xyz.wanghongtao.rebac.service.ModelService;
import xyz.wanghongtao.rebac.service.RelationService;
import xyz.wanghongtao.rebac.service.StoreService;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;
import xyz.wanghongtao.rebac.util.TripleParserUtil;

import java.util.List;

/**
 * gateway用于处理数据库查询出来的数据，可以与上层模块解耦合
 * @author wanghongtao
 * @data 2023/7/18 22:19
 */
@Slf4j
@AllArgsConstructor
@Component
public class DatabaseGatewayImpl implements DatabaseGateway {
    ModelService modelService;

    StoreService storeService;

    RelationService relationService;




    @Override
    public void addModel(AddModelForm addModelForm) {
        //检查store是否存在
        StoreDo storeById = storeService.getStoreById(addModelForm.getStoreId());
        if (storeById == null) {
            throw new CustomException(ErrorCode.Store_NOT_EXIST);
        }
        //预检查policy
        ModelEngine.preCheckPolicy(addModelForm);

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
  public void deleteModel(DeleteModelForm deleteModelForm) {
    modelService.deleteModelById(deleteModelForm.getId());
    //删除关系
    int deleteRelation = relationService.deleteRelation(deleteModelForm.getId());
    log.info("模型：{}, 删除关系{}条", deleteModelForm.getId(), deleteRelation);
  }

  @Override
    public ModelDo getModelById(Long id) {
        return modelService.getModelById(id);
    }

    @Override
    public List<ModelDo> getAllModelByStoreId(Long storeId) {
        return modelService.getAllModelByStoreId(storeId);
    }

    @Override
    public PolicyDo getPolicyById(String id) {
        return modelService.getPolicyById(id);
    }

  @Override
  public void updatePolicy(UpdatePolicyForm updatePolicyForm) {
      ModelEngine.preCheckPolicy(updatePolicyForm);
      modelService.updatePolicy(PolicyDo.builder()
          .id(updatePolicyForm.getId())
          .definitions(updatePolicyForm.getDefinitions())
          .description(updatePolicyForm.getDescription())
          .build());
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

    public List<KeyDo> getKeyList() {
      return storeService.getKeyList();
    }

    @Override
    public RelationDo addRelation(AddRelationForm addRelationForm) {
        //检查model是否存在
        ModelDo modelById = modelService.getModelById(addRelationForm.getModelId());
        if (modelById == null) {
            throw new CustomException(ErrorCode.Model_NOT_EXIST);
        }
        //解析关系三元组
        RelationContext relationContext = TripleParserUtil.parseRelation(addRelationForm.getTriple());
        //预检查关系定义
        //查出policy定义
        PolicyDo policyById = modelService.getPolicyById(modelById.getPolicyId());
        if (policyById == null) {
            throw new CustomException(ErrorCode.Policy_NOT_EXISTED);
        }
        //预检查relation
        RelationEngine.preCheckRelation(relationContext, policyById);

        RelationDo relationDo = RelationDo.builder()
                .modelId(addRelationForm.getModelId())
                .objectType(relationContext.getObjectType())
                .object(relationContext.getObject())
                .relation(relationContext.getRelation())
                .subjectType(relationContext.getSubjectType())
                .subject(relationContext.getSubject())
                .triple(addRelationForm.getTriple())
                .build();
        return relationService.addRelation(relationDo);
    }



    @Override
    public List<RelationDo> getRelationByTriple(String triple) {
        return relationService.getByTriple(triple);
    }

    @Override
    public List<RelationDo> getRelationByModelId(Long modelId) {
        //根据modelId查找Relation
        List<RelationDo> relationDos = relationService.getRelationByModelId(modelId);
        return relationDos;
    }

    @Override
    public void deleteRelation(DeleteRelationForm deleteRelationForm) {
        //检查model是否存在
        ModelDo modelById = modelService.getModelById(deleteRelationForm.getModelId());
        if (modelById == null) {
            throw new CustomException(ErrorCode.Model_NOT_EXIST);
        }

        //删除relation
        relationService.deleteRelation(deleteRelationForm.getModelId(), deleteRelationForm.getId());
    }



  @Override
  public List<RelationDo> getRelationBySubjectAndRelation(Long modelId, String subjectType, String subject, String relation) {
    return relationService.getRelationBySubjectAndRelation(modelId, subjectType, subject, relation);
  }

  @Override
  public List<RelationDo> getRelationBySubject(Long modelId, String subjectType, String subject) {
    return relationService.getRelationBySubject(modelId, subjectType, subject);
  }
  @Override
  public List<RelationDo> getRelationByObject(Long modelId, String objectType, String object) {
    return relationService.getRelationByObject(modelId, objectType, object);
  }
}
