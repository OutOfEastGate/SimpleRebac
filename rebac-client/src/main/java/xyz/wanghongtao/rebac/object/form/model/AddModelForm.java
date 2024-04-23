package xyz.wanghongtao.rebac.object.form.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import xyz.wanghongtao.rebac.object.form.policy.PolicyForm;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:45
 */
@Data
public class AddModelForm {

    @NotNull(message = "storeId不可为空")
    Long storeId;

    @Size(max = 10, message = "name长度不可超过10")
    String name;

    @Size(max = 100, message = "description长度不可超过100")
    String description;

    PolicyForm policy;

    String appKey;
}
