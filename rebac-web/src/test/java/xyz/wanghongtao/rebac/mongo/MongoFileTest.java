package xyz.wanghongtao.rebac.mongo;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  public void getFile() {
    GridFsResource test = gridFsTemplate.getResource("test");
    System.out.println(test.getFilename());
  }

}
