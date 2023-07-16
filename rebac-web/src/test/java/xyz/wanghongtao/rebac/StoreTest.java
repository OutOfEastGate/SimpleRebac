package xyz.wanghongtao.rebac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

    @Test
    public void testGetById() {
        StoreDo storeById = storeService.getStoreById(1L);
    }
}
