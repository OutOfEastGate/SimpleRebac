package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.wanghongtao.rebac.graph.FrontGraphTransform;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.form.AddRelationForm;
import xyz.wanghongtao.rebac.object.form.DeleteRelationForm;
import xyz.wanghongtao.rebac.object.form.GetRelationByModelIdForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.graph.GraphVo;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:51
 */
@AllArgsConstructor
@RequestMapping("/relation")
@RestController
public class RelationController {
    DatabaseGateway databaseGateway;

    FrontGraphTransform frontGraphTransform;

    @PostMapping("/add")
    public Result<RelationDo> addRelation(@Valid @RequestBody AddRelationForm addRelationForm) {
        return Result.success(databaseGateway.addRelation(addRelationForm));
    }

    @GetMapping("/getByModelId")
    public Result<List<RelationDo>> getByModelId(@Valid GetRelationByModelIdForm getRelationByModelIdForm) {
        return Result.success(databaseGateway.getRelationByModelId(getRelationByModelIdForm.getModelId()));
    }

    @GetMapping("/getGraphRelation")
    public Result<?> getGraphRelation(@Valid GetRelationByModelIdForm getRelationByModelIdForm) {
        List<RelationDo> relationByModelId = databaseGateway.getRelationByModelId(getRelationByModelIdForm.getModelId());
        return Result.success(frontGraphTransform.transform(relationByModelId));
    }

    @PostMapping("/deleteById")
    public Result<?> deleteById(@RequestBody DeleteRelationForm deleteRelationForm) {
        databaseGateway.deleteRelation(deleteRelationForm);
        return Result.success();
    }
}
