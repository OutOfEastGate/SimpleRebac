package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.repository.RelationMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/18 22:36
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "false", matchIfMissing = true)
@AllArgsConstructor
@Service
public class RelationServiceImpl implements RelationService {
    RelationMapper relationMapper;

    @Override
    public RelationDo addRelation(RelationDo relationDo) {
        relationMapper.insert(relationDo);
        return relationDo;
    }

  @Override
  public void batchAddRelation(List<RelationDo> relationDoList) {
      relationDoList.forEach(relationDo -> {
        relationMapper.insert(relationDo);
      });
  }

  @Override
  public List<RelationDo> getByTriple(String triple) {
        LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationDo::getTriple, triple);
        return relationMapper.selectList(queryWrapper);
    }

    @Override
    public List<RelationDo> getRelationByModelId(Long modelId) {
        LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationDo::getModelId, modelId);
        return relationMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteRelation(Long modelId, Long id) {
        LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationDo::getModelId, modelId);
        queryWrapper.eq(RelationDo::getId, id);
        int delete = relationMapper.delete(queryWrapper);

        if (delete != 1) {
            throw new CustomException(ErrorCode.Delete_ERROR);
        }
    }

  public int deleteRelation(Long modelId) {
    LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RelationDo::getModelId, modelId);
    return relationMapper.delete(queryWrapper);
  }

  @Override
  public List<RelationDo> getRelationBySubjectAndRelation(Long modelId, String subjectType, String subject, String relation) {
    LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RelationDo::getModelId, modelId);
    queryWrapper.eq(RelationDo::getSubject, subject);
    queryWrapper.eq(RelationDo::getSubjectType, subjectType);
    queryWrapper.eq(RelationDo::getRelation, relation);
    return relationMapper.selectList(queryWrapper);
  }

  @Override
  public List<RelationDo> getRelationBySubject(Long modelId, String subjectType, String subject) {
    LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RelationDo::getModelId, modelId);
    queryWrapper.eq(RelationDo::getSubject, subject);
    queryWrapper.eq(RelationDo::getSubjectType, subjectType);
    return relationMapper.selectList(queryWrapper);
  }

  @Override
  public List<RelationDo> getRelationByObject(Long modelId, String objectType, String object) {
    LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RelationDo::getModelId, modelId);
    queryWrapper.eq(RelationDo::getObject, object);
    queryWrapper.eq(RelationDo::getObjectType, objectType);
    return relationMapper.selectList(queryWrapper);
  }
}
