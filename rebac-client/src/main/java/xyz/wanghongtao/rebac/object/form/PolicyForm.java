package xyz.wanghongtao.rebac.object.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wanghongtao.rebac.object.dataObject.model.Definition;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:49
 */
@Data
public class PolicyForm {
    /**
     * 描述
     */
    @Max(value = 100L, message = "Policy描述不可超过100")
    String description;

    /**
     * 策略定义
     */
    @NotNull(message = "definitions不可为空")
    List<Definition> definitions;
}
