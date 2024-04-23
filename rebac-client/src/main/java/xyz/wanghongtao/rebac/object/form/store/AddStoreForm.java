package xyz.wanghongtao.rebac.object.form.store;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * @author wanghongtao
 * @data 2023/7/16 18:15
 */
@Data
public class AddStoreForm {
    @NotEmpty(message = "存储模型名称不可为空")
    @Size(min = 3, max = 12, message = "存储模型名称不合法")
    String name;

    String description;

    String appKey;
}
