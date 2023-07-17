package xyz.wanghongtao.rebac.object.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:45
 */
@Data
public class AddModelForm {

    @NotEmpty(message = "storeId不可为空")
    Long storeId;

    @Size(max = 10, message = "name长度不可超过10")
    String name;

    @Size(max = 100, message = "description长度不可超过100")
    String description;

    @NotEmpty
    PolicyForm policy;

    String appKey;
}
