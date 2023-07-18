package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.repository.RelationMapper;

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
}
