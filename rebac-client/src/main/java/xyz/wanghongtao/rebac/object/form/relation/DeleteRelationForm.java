package xyz.wanghongtao.rebac.object.form.relation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wanghongtao.rebac.object.form.IdForm;

/**
 * @author wanghongtao
 * @data 2023/7/23 15:23
 */
@Data
public class DeleteRelationForm extends IdForm {
    @Min(value = 0L, message = "model不可小于0")
    @NotNull(message = "modelId不可为空")
    Long modelId;
}
