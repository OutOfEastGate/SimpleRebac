package xyz.wanghongtao.rebac.object.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/23 15:22
 */
@Data
public class IdForm {

    @NotNull(message = "id不可为空")
    Long id;
}
