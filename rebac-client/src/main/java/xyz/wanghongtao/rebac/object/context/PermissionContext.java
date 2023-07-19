package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:49
 */
@Builder
@Data
public class PermissionContext {
    String objectType;
    String object;
    String permission;
    String subject;
    String subjectType;
}
