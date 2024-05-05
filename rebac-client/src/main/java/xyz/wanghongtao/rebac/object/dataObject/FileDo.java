package xyz.wanghongtao.rebac.object.dataObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "file")
public class FileDo {

  @Id
  String id;

  String appKey;

  String filename;

  String objectId;

  Long length;

  String url;

  String ext;

  Long createTime;
}
