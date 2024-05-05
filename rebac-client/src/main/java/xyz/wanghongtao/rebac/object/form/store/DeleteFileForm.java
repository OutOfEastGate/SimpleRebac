package xyz.wanghongtao.rebac.object.form.store;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@Data
public class DeleteFileForm {
  @NotEmpty(message = "文件id不可为空")
  String objectId;
}
