package xyz.wanghongtao.rebac.neo4j;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.object.context.RelationComputeContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.service.Neo4jRelationComputeServiceImpl;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jTest {
  @Autowired
  private Neo4jRelationComputeServiceImpl neo4jRelationComputeService;

  @Test
  public void testCreateRelation() {
    List<RelationDo> relationDoList = new ArrayList<>();
    relationDoList.add(RelationDo.builder()
      .objectType("user")
      .object("user1")
      .relation("admin")
      .subjectType("app")
      .subject("app2")
      .build());
    neo4jRelationComputeService.addRelation(RelationComputeContext.builder()
      .policyId("testPolicy")
      .relationsToAdd(relationDoList)
      .build());
  }

  @Test
  public void testQueryObject() {
    List<Record> records = neo4jRelationComputeService.queryObjectList("testPolicy", "user", "user1");
    records.forEach(record -> {
      System.out.println(record.values());
    });
    log.info(JacksonUtils.toJson(records));
  }

  @Test
  public void testDeleteObject() {
    Integer delete = neo4jRelationComputeService.deleteObject("testPolicy", "user", "user1");

    log.info(String.valueOf(delete));
  }

  @Test
  public void testDeleteRelation() {
    Integer delete = neo4jRelationComputeService.deleteRelationFromNeo4j("testPolicy", "user", "user1","admin",  "app", "app2");

    log.info(String.valueOf(delete));
  }

}
