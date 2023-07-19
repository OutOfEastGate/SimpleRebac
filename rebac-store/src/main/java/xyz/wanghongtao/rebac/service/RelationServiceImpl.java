package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.repository.RelationMapper;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/18 22:36
 */
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
    public List<RelationDo> getByTriple(String triple) {
        LambdaQueryWrapper<RelationDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationDo::getTriple, triple);
        return relationMapper.selectList(queryWrapper);
    }
}
