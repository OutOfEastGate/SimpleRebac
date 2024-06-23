package xyz.wanghongtao.rebac.object.dataObject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型策略
 * @author wanghongtao
 * @data 2023/7/17 22:17
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "policy")
public class PolicyDo {

    /**
     * 唯一标识
     */
    @Id
    private String id;

    /**
     * 描述
     */
    String description;

    /**
     * 策略定义
     */
    List<Definition> definitions;

  @Override
  public PolicyDo clone() {
    long l = System.currentTimeMillis();
    return PolicyDo.builder()
      .id(id)
      .description(description)
      .definitions(new ArrayList<>(definitions))
      .build();
  }
}
