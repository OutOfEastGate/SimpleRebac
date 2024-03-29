package xyz.wanghongtao.rebac.config.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.config.Neo4jConfigProperties;

import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/29 15:56
 */
@AllArgsConstructor
@Component
@Slf4j
public class Neo4jConnectFactory {
    private final Neo4jConfigProperties neo4jConfigProperties;

//    static List<Driver> driverList = new LinkedList<>();

    static final int minPool = 3;
    static final int maxPool = 5;

    @PostConstruct
    private void init() {
        int i = minPool;
        while (i -- != 0) {
            log.info("neo4j线程池-{}初始化",minPool - i);
//            driverList.add(GraphDatabase.driver(neo4jConfigProperties.getUri(),
//                    AuthTokens.basic(neo4jConfigProperties.getUsername(),
//                            neo4jConfigProperties.getPassword())));
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("neo4j线程池销毁");
    }

    // 获取 Neo4j 连接
//    public Driver getConnection() {
//        if (driverList.size() == 0) {
//            while (driverList.size() < maxPool) {
//                driverList.add(GraphDatabase.driver(neo4jConfigProperties.getUri(),
//                        AuthTokens.basic(neo4jConfigProperties.getUsername(),
//                                neo4jConfigProperties.getPassword())));
//            }
//        }
//
//        Driver driver = driverList.remove(0);
//
//        return (Driver) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                new Class[]{Driver.class},
//                (proxy, method, args) -> {
//                    if (!method.getName().equals("close")) {
//                        return method.invoke(driver,args);
//                    } else {
//                        driverList.add(driver);
//                        log.info("driver-{}重新放回线程池,当前池子大小{}", driver.session().hashCode(), driverList.size());
//                        return null;
//                    }
//                });
//    }

}
