package xyz.wanghongtao.rebac.object.form.relation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:28
 */
@Data
public class CheckRelationForm {

    @NotNull(message = "model的id不可为空")
    Long modelId;

    @NotEmpty(message = "三元组不可为空")
    String triple;
}
