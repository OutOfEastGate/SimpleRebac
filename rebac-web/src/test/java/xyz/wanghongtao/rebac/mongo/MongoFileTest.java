package xyz.wanghongtao.rebac.mongo;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.AbstractTest;

import java.io.InputStream;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoFileTest extends AbstractTest {

  @Autowired
  private GridFsTemplate gridFsTemplate;

  @Test
  public void testSaveFile() {
    InputStream inputStream = locate("/json/test.json");
    ObjectId objectId = gridFsTemplate.store(inputStream, "test");
    System.out.println(objectId);
  }

  @Test
  public void deleteFile() {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is("663776a24f09900adc20f454"));
    gridFsTemplate.delete(query);
  }

  @Test
  public void getList() {
    org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
    GridFSFindIterable gridFSFiles = gridFsTemplate.find(query);
    for (GridFSFile gridFSFile : gridFSFiles) {
      System.out.println(gridFSFile.getFilename());
      System.out.println(gridFSFile);
    }
    System.out.println(gridFSFiles);
  }

}
