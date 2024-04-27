package xyz.wanghongtao.rebac.service;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.wanghongtao.rebac.config.pool.Neo4jConnectFactory;
import xyz.wanghongtao.rebac.object.context.RelationComputeContext;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
@Slf4j
@ConditionalOnProperty(name = "wht.back.startGraphCompute", havingValue = "true", matchIfMissing = true)
@Service
public class Neo4jRelationComputeServiceImpl implements RelationComputeService {

  private final Neo4jConnectFactory connectFactory;

  public Neo4jRelationComputeServiceImpl(Neo4jConnectFactory connectFactory) {
    this.connectFactory = connectFactory;
  }

  //namespace_objectType name
  private final String createQueryTemplate = "MERGE (n:%s_%s {name: '%s'})";

  //namespace_objectType relation namespace_subjectType
  private final String retrieveDataQuery = "MATCH (n:%s_%s)-[:%s]->(m:%s_%s)\n" +
    "RETURN n, m";

  //namespace objectType object
  private final String objectQuery = "MATCH (n:%s_%s {name: '%s'})\n" +
    "RETURN n";

  //namespace objectType object
  private final String objectDelete = "MATCH (n:%s_%s {name: '%s'})\n" +
    "DELETE n";
  //namespace objectType object namespace subjectType subject
  private final String relationDelete = "MATCH ((n:%s_%s {name: '%s'})-[r:%s]-(m:%s_%s {name: '%s'}))\n" +
    "DELETE r";

  //namespace objectType object namespace subjectType subject
  private final String relationAndObjectDelete = "MATCH ((n:%s_%s {name: '%s'})-[r]-(m:%s_%s {name: '%s'}))\n" +
    "DELETE r,n,m";

  //namespace_objectType object namespace_subjectType subject relation
  private final String createRelationshipQuery = "MATCH (n:%s_%s {name: '%s'})\n" +
    "MATCH (m:%s_%s {name: '%s'})\n" +
    "CREATE (n)-[:%s]->(m)";

  private Result execute(String execute) {
    log.info("neo4j execute: {}", execute);
    Driver driver = connectFactory.getConnection();
    Result result = driver.session().run(execute);
    driver.close();
    return result;
  }

  public void addRelation(RelationComputeContext context) {
    if (CollectionUtils.isEmpty(context.getRelationsToAdd())) {
      return;
    }
    String namespace = context.getPolicyId();
    context.getRelationsToAdd().forEach(relationDo -> {
      String object = relationDo.getObject();
      String objectType = relationDo.getObjectType();
      String subject = relationDo.getSubject();
      String subjectType = relationDo.getSubjectType();
      String relation = relationDo.getRelation();
      //删除原有关系
      deleteRelationFromNeo4j(namespace, objectType, object, relation, subjectType, subject);
      String addObject = String.format(createQueryTemplate, namespace, objectType, object);
      //增加
      Result addObjectResult = execute(addObject);
      String addSubject = String.format(createQueryTemplate, namespace, subjectType, subject);
      Result addSubjectResult = execute(addSubject);
      String addRelation = String.format(createRelationshipQuery, namespace, objectType, object, namespace, subjectType, subject, relation);
      Result addRelationResult = execute(addRelation);

    });
  }

  public List<Record> queryObjectList(String namespace, String objectType, String object) {
    String queryObject = String.format(objectQuery, namespace, objectType, object);
    Result result = execute(queryObject);
    return result.list();
  }

  public Integer deleteObject(String namespace, String objectType, String object) {
    String deleteObject = String.format(objectDelete, namespace, objectType, object);
    Result result = execute(deleteObject);
    return result.list().size();
  }



  public void removeRelation(RelationComputeContext context) {
    if(CollectionUtils.isEmpty(context.getRelationsToDelete())) {
      return;
    }
    String namespace = context.getPolicyId();
    context.getRelationsToDelete().forEach(relationDo -> {
      String object = relationDo.getObject();
      String objectType = relationDo.getObjectType();
      String subject = relationDo.getSubject();
      String subjectType = relationDo.getSubjectType();
      String relation = relationDo.getRelation();

      deleteRelationFromNeo4j(namespace, objectType, object, relation, subjectType, subject);
    });
  }



  public Boolean hasRelation(RelationComputeContext context) {
    return null;
  }

  public Integer deleteRelationFromNeo4j (String namespace, String objectType, String object, String relation, String subjectType, String subject) {
    String deleteRelation= String.format(relationDelete, namespace, objectType, object, relation,namespace, subjectType, subject);
    Result result = execute(deleteRelation);
    return result.list().size();
  }

  public Integer deleteRelationAndObjectFromNeo4j (String namespace, String objectType, String object, String subjectType, String subject) {
    String deleteRelation= String.format(relationAndObjectDelete, namespace, objectType, object, namespace, subjectType, subject);
    Result result = execute(deleteRelation);
    return result.list().size();
  }
}
