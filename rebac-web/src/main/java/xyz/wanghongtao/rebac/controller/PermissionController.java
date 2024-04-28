package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.form.permission.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.relation.CheckRelationForm;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.service.gateway.PermissionServiceGateway;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:16
 */
@AllArgsConstructor
@RestController
public class PermissionController {
    PermissionServiceGateway permissionServiceGateway;

    /**
     * 关键实现：检查是否有权限
     * @param checkPermission 对象 -> 操作 -> 主体
     * @return 是否有权限
     */
    @PostMapping("/checkPermission")
    public Result<CheckPermissionResult> checkPermission(@Valid @RequestBody CheckPermissionForm checkPermission, @RequestHeader("appKey")String appKey) {
      checkPermission.setAppKey(appKey);
      return Result.success(permissionServiceGateway.checkPermission(checkPermission));
    }

    /**
     * 关键实现： 检查是否有关系
     * @param checkRelationForm 对象:对象类型#关系@主体:主体类型
     * @return 是否有关系
     */
    @PostMapping("/checkRelation")
    public Result<Boolean> checkRelation(@Valid @RequestBody CheckRelationForm checkRelationForm) {
        return Result.success(permissionServiceGateway.checkRelation(checkRelationForm));
    }
}
