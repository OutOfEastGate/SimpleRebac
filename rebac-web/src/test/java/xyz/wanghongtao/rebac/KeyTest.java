package xyz.wanghongtao.rebac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.service.KeyGeneratorFactory;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KeyTest {
    @Autowired
    KeyGeneratorFactory keyGeneratorFactory;

    @Test
    public void testGenerateKey() {
        GenerateKey generateKey = keyGeneratorFactory.generateKey("sha256");
        keyGeneratorFactory.deleteKey(generateKey.getAppKey());
    }

    @Test
    public void testGetKey() {
        KeyDo key = keyGeneratorFactory.getKey("42F8kCDsJrq2yGYqScOUi89FT3D2kyeNikzBRHJOzao=");
    }
}
