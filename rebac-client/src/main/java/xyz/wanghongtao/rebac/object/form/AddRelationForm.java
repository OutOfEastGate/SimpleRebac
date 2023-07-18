package xyz.wanghongtao.rebac.object.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/18 22:43
 */
@Data
public class AddRelationForm {

    @NotNull(message = "model的id不可为空")
    Long modelId;

    @Pattern(regexp = ".*#.*@.*", message = "三元组格式错误")
    @NotEmpty(message = "三元组不可为空")
    String triple;
}
