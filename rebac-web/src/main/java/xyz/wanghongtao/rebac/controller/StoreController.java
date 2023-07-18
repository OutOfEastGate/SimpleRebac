package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.form.AddStoreForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 18:11
 */
@AllArgsConstructor
@RequestMapping("/store")
@RestController
public class StoreController {

    DatabaseGateway databaseGateway;

    @PostMapping("/add")
    public Result<AddStore> add(@RequestBody @Valid AddStoreForm addStoreForm, @RequestHeader("appKey") String appKey) {
        addStoreForm.setAppKey(appKey);
        return Result.success(databaseGateway.addStore(addStoreForm));
    }

    @GetMapping("/getAllByAppKey")
    public Result<List<StoreDo>> getAllByAppId(@RequestHeader("appKey") String appKey) {
        return Result.success(databaseGateway.getAllByAppId(appKey));
    }

    @GetMapping("/getStoreById")
    public Result<StoreDo> getStoreById(Long id) {
        return Result.success(databaseGateway.getStoreById(id));
    }

}
