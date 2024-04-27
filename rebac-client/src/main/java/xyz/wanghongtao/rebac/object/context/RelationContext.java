package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:48
 */
@Builder
@Data
public class RelationContext {
    String objectType;
    String object;
    String relation;
    String subject;
    String subjectType;

    String triple;

  public RelationContext clone() {
    return RelationContext.builder()
      .objectType(this.getObjectType())
      .object(this.getObject())
      .subjectType(this.getSubjectType())
      .subject(this.getSubject())
      .relation(this.getRelation())
      .build();
  }
}
