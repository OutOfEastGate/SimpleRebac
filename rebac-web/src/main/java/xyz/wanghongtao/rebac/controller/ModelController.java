package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.AddModelForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.service.StoreService;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:51
 */
@AllArgsConstructor
@RequestMapping("/model")
@RestController
public class ModelController {

    StoreService storeService;
    @PostMapping("/add")
    public Result<?> addModel(@Valid @RequestBody AddModelForm addModelForm, @RequestHeader("appKey") String appKey) {
        addModelForm.setAppKey(appKey);
        storeService.addModel(addModelForm);
        return Result.success();
    }

    @GetMapping("/getAllByStoreId")
    public Result<List<ModelDo>> getAllByStoreId(String storeId) {
        return Result.success(storeService.getAllModelByStoreId(storeId));
    }

    @GetMapping("/getPolicyById")
    public Result<PolicyDo> getPolicyById(String id) {
        return Result.success(storeService.getPolicyById(id));
    }
}
