package xyz.wanghongtao.rebac.object.form.permission;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:25
 */
@Data
public class CheckPermissionForm {

    @NotNull(message = "model的id不可为空")
    Long modelId;

    @NotEmpty(message = "triple不可为空")
    String triple;

//    @NotEmpty(message = "对象不可为空")
//    String object;
//
//    @NotEmpty(message = "对象类型不可为空")
//    String objectType;
//
//    @NotEmpty(message = "权限不可为空")
//    String permission;
//
//    @NotEmpty(message = "主体不可为空")
//    String subject;
//
//    @NotEmpty(message = "主体类型不可为空")
//    String subjectType;
}
