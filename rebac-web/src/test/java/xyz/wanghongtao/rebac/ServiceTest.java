package xyz.wanghongtao.rebac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.service.KeyGeneratorFactory;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    KeyGeneratorFactory keyGeneratorFactory;


    @Test
    public void print() {
        System.out.println("Hello World");
    }


}
