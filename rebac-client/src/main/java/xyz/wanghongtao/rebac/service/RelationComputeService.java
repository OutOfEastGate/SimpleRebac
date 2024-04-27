package xyz.wanghongtao.rebac.service;

import org.neo4j.driver.Record;
import xyz.wanghongtao.rebac.object.context.RelationComputeContext;

import java.util.List;


/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
public interface RelationComputeService {
  void addRelation(RelationComputeContext context);

  List<Record> queryObjectList(String namespace, String objectType, String object);

  void removeRelation(RelationComputeContext context);


  Boolean hasRelation(RelationComputeContext context);
}
