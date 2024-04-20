package xyz.wanghongtao.rebac.object.form.policy;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wanghongtao.rebac.object.dataObject.model.Definition;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-20
 */
@Data
public class UpdatePolicyForm {
  @NotNull(message = "策略id不可为空")
  String id;

  /**
   * 描述
   */
  String description;

  /**
   * 策略定义
   */
  List<Definition> definitions;
}
