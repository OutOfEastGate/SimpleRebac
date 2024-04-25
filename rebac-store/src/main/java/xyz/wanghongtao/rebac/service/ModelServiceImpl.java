package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.repository.ModelMapper;
import xyz.wanghongtao.rebac.repository.PolicyRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * @author wanghongtao
 * @data 2023/7/18 22:21
 */
@AllArgsConstructor
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "false", matchIfMissing = true)
@Service
public class ModelServiceImpl implements ModelService {

    ModelMapper modelMapper;

    PolicyRepository policyRepository;

    public void addModel(PolicyDo policyDo, ModelDo modelDo) {
       policyRepository.insert(policyDo);
       modelDo.setPolicyId(policyDo.getId());
       modelMapper.insert(modelDo);
    }

  @Override
  public void updatePolicy(PolicyDo policyDo) {
    policyRepository.save(policyDo);
  }

  @Override
    public List<ModelDo> getAllModelByStoreId(Long storeId) {
        LambdaQueryWrapper<ModelDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ModelDo::getStoreId, storeId);
        return modelMapper.selectList(queryWrapper);
    }

    @Override
    public PolicyDo getPolicyById(String id) {
        Optional<PolicyDo> optionalPolicyDo = policyRepository.findById(id);
        return optionalPolicyDo.orElse(null);
    }

    @Override
    public ModelDo getModelById(Long id) {
        return modelMapper.selectById(id);
    }

  @Override
  public void deleteModelById(Long id) {
    ModelDo modelDo = modelMapper.selectById(id);
    if(modelDo != null) {
      modelMapper.deleteById(modelDo);
      policyRepository.deleteById(modelDo.getPolicyId());
    }
  }
}
