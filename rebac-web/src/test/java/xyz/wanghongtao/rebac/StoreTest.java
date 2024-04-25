package xyz.wanghongtao.rebac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.config.pool.Neo4jConnectFactory;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.service.StoreService;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:47
 */
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
        String retrieveDataQuery = "MATCH (n:Person)-[:FRIENDS]->(m:Person)\n" +
                "RETURN n, m";

//        Driver driver = neo4jConnectFactory.getConnection();
//
//        Result result = driver.session().run(retrieveDataQuery);
//        while (result.hasNext()) {
//            Record record = result.next();
//            System.out.println(record.get("n").get("name").asString() + " is friends with " +
//                    record.get("m").get("name").asString());
//        }
//        driver.close();
    }

    /**
     * 查询全部对象
     */
    @Test
    public void testQueryEntityNeo4j() {
//        Driver driver = neo4jConnectFactory.getConnection();
//        String retrieveDataQuery2 = "MATCH (n)\n" +
//                "RETURN n";
//        Result result2 = driver.session().run(retrieveDataQuery2);
//        while (result2.hasNext()) {
//            Record record = result2.next();
//            System.out.println(record.get("n").get("name"));
//        }
//        driver.close();
    }

    /**
     * 创建对象
     */
    @Test
    public void testCreateNodeNeo4j() {
//        String createQuery = "CREATE (n:Person {name: 'wanghongtao05'})";
//        Driver driver = neo4jConnectFactory.getConnection();
//        Result result = driver.session().run(createQuery);
//        while (result.hasNext()) {
//            Record next = result.next();
//            System.out.println(next);
//        }
    }

    /**
     * 创建关联关系
     */
    @Test
    public void testCreateRelationNeo4j() {
//        Driver driver = neo4jConnectFactory.getConnection();
//        String createRelationshipQuery = "MATCH (n:Person {name: 'wanghongtao05'})\n" +
//                "MATCH (m:Person {name: 'wht'})\n" +
//                "CREATE (n)-[:FRIENDS]->(m)";
//        Result result = driver.session().run(createRelationshipQuery);
//        while (result.hasNext()) {
//            Record next = result.next();
//            System.out.println(next);
//        }
    }

}
