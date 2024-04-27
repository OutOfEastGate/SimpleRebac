package xyz.wanghongtao.rebac.object.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:49
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PermissionContext {
    private String objectType;
    private String object;
    private String permission;
    private String subject;
    private String subjectType;

  public PermissionContext clone() {
    return PermissionContext.builder()
      .objectType(this.getObjectType())
      .object(this.getObject())
      .subjectType(this.getSubjectType())
      .subject(this.getSubject())
      .permission(this.getPermission())
      .build();
  }
}
