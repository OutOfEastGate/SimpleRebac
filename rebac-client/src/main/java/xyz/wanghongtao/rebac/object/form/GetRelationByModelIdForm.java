package xyz.wanghongtao.rebac.object.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/23 15:13
 */
@Data
public class GetRelationByModelIdForm {
    @Min(value = 0L,message = "modelId不可为负数")
    @NotNull(message = "modelId不可为空")
    Long modelId;
}
