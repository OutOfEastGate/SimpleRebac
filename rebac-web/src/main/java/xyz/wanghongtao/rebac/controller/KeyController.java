package xyz.wanghongtao.rebac.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.dataObject.KeyDo;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.key.GenerateKey;
import xyz.wanghongtao.rebac.repository.jooq.SelectHandler;
import xyz.wanghongtao.rebac.service.KeyGeneratorFactory;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:32
 */
@AllArgsConstructor
@RequestMapping("/key")
@RestController
public class KeyController {

    KeyGeneratorFactory keyGeneratorFactory;

    DatabaseGateway databaseGateway;

    @GetMapping("/generate")
    public Result<GenerateKey> generateKey(@RequestParam(value = "algorithm", defaultValue = "sha256") String algorithm) {
        return Result.success(keyGeneratorFactory.generateKey(algorithm));
    }

    @GetMapping("/getList")
    public Result<List<KeyDo>> getList() {
      return Result.success(databaseGateway.getKeyList());
    }
}
