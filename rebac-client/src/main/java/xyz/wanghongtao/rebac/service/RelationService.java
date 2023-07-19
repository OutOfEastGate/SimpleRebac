package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.RelationDo;

import java.util.List;

public interface RelationService {

    RelationDo addRelation(RelationDo relationDo);

    List<RelationDo> getByTriple(String triple);
}
