package xyz.wanghongtao.rebac;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.config.pool.Neo4jConnectFactory;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.service.StoreService;
import xyz.wanghongtao.rebac.util.JacksonUtils;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:47
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreTest {
    @Autowired
    StoreService storeService;

    @Autowired
    Neo4jConnectFactory neo4jConnectFactory;


    @Test
    public void testGetById() {
        StoreDo storeById = storeService.getStoreById(1L);
    }

    @Test
    public void testSelectDatabase() {
        String selectDatabaseQuery = "use system";
        String retrieveDataQuery = "MATCH (n:Person)-[:FRIENDS]->(m:Person)\n" +
                "RETURN n, m";

    }

    /**
     * 查询Match
     */
    @Test
    public void testMatchNeo4j() {
        String retrieveDataQuery = "MATCH (n:namespace_Person)-[:FRIENDS]->(m:namespace_Person)\n" +
                "RETURN n, m";

        Driver driver = neo4jConnectFactory.getConnection();

      Result result = driver.session().run(retrieveDataQuery);
      log.info(JacksonUtils.toJson(result));
      while (result.hasNext()) {
        Record next = result.next();
          log.info(next.get("n").get("name").asString() + " is friends with " +
            next.get("m").get("name").asString());
        }
        driver.close();
    }

    /**
     * 查询全部对象
     */
    @Test
    public void testQueryEntityNeo4j() {
        Driver driver = neo4jConnectFactory.getConnection();
        String retrieveDataQuery2 = "MATCH (n)\n" +
                "RETURN n";
        Result result = driver.session().run(retrieveDataQuery2);
        log.info(JacksonUtils.toJson(result));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("n").get("name"));
        }
        driver.close();
    }

    /**
     * 创建对象
     */
    @Test
    public void testCreateNodeNeo4j() {
        String createQuery = "CREATE (n:namespace_Person {name: 'wanghongtao05'})";
        Driver driver = neo4jConnectFactory.getConnection();
        Result result;
        try {
          result = driver.session().run(createQuery);
        } catch (ClientException e) {
          log.error(e.getMessage());
          return;
        }
        log.info(JacksonUtils.toJson(result));
        assert result != null;
        while (result.hasNext()) {
            Record next = result.next();
            System.out.println(next);
        }
    }

    /**
     * 创建关联关系
     */
    @Test
    public void testCreateRelationNeo4j() {
        Driver driver = neo4jConnectFactory.getConnection();
        String createRelationshipQuery = "MATCH (n:namespace_Person {name: 'wanghongtao05'})\n" +
                "MATCH (m:namespace_Person {name: 'wht'})\n" +
                "CREATE (n)-[:FRIENDS]->(m)";
        Result result = driver.session().run(createRelationshipQuery);
        log.info(JacksonUtils.toJson(result));
        while (result.hasNext()) {
            Record next = result.next();
            System.out.println(next);
        }
    }

}
