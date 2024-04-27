package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.RelationDo;

import java.util.List;

public interface RelationService {

    RelationDo addRelation(RelationDo relationDo);

    void batchAddRelation(List<RelationDo> relationDoList);

    List<RelationDo> getByTriple(String triple);

    List<RelationDo> getRelationByModelId(Long modelId);

    void deleteRelation(Long modelId, Long id);

    int deleteRelation(Long modelId);

    List<RelationDo> getRelationBySubjectAndRelation(Long modelId, String objectType, String object, String relation);
}
