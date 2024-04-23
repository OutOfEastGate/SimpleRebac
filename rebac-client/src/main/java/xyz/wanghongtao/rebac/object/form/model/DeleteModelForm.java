package xyz.wanghongtao.rebac.object.form.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.wanghongtao.rebac.object.form.IdForm;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteModelForm extends IdForm {
  String appKey;
}
