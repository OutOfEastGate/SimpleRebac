package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.form.AddRelationForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

/**
 * @author wanghongtao
 * @data 2023/7/16 19:51
 */
@AllArgsConstructor
@RequestMapping("/relation")
@RestController
public class RelationController {
    DatabaseGateway databaseGateway;

    @PostMapping("/add")
    public Result<RelationDo> addRelation(@Valid @RequestBody AddRelationForm addRelationForm) {
        return Result.success(databaseGateway.addRelation(addRelationForm));
    }
}
