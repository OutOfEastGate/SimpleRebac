package xyz.wanghongtao.rebac.object.dataObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wanghongtao
 * @data 2024/4/28 15:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "checkPermissionRecord")
public class CheckPermissionRecordDo {
  @Id
  private String id;

  private String appKey;

  private Long modelId;

  private String policyId;

  private String triple;

  private Boolean hasPermission;

  private Double useTimeSeconds;

  private Long createTimestamp;
}
