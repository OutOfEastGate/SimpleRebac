package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.model.AddModelForm;
import xyz.wanghongtao.rebac.object.form.model.DeleteModelForm;
import xyz.wanghongtao.rebac.object.form.policy.UpdatePolicyForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:51
 */
@AllArgsConstructor
@RequestMapping("/model")
@RestController
public class ModelController {

    DatabaseGateway databaseGateway;
    @PostMapping("/add")
    public Result<?> addModel(@Valid @RequestBody AddModelForm addModelForm, @RequestHeader("appKey") String appKey) {
        addModelForm.setAppKey(appKey);
        databaseGateway.addModel(addModelForm);
        return Result.success();
    }
  @PostMapping("/delete")
  public Result<?> deleteModel(@Valid @RequestBody DeleteModelForm deleteModelForm, @RequestHeader("appKey") String appKey) {
    deleteModelForm.setAppKey(appKey);
    databaseGateway.deleteModel(deleteModelForm);
    return Result.success();
  }

    @PostMapping("/updatePolicy")
    public Result<?> updatePolicy(@Valid @RequestBody UpdatePolicyForm updatePolicyForm, @RequestHeader("appKey") String appKey) {
        databaseGateway.updatePolicy(updatePolicyForm);
        return Result.success();
    }

    @GetMapping("/getAllByStoreId")
    public Result<List<ModelDo>> getAllByStoreId(Long storeId) {
        return Result.success(databaseGateway.getAllModelByStoreId(storeId));
    }

    @GetMapping("/getPolicyById")
    public Result<PolicyDo> getPolicyById(String id) {
        return Result.success(databaseGateway.getPolicyById(id));
    }
}
