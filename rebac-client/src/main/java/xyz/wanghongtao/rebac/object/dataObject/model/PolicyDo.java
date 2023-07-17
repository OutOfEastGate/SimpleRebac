package xyz.wanghongtao.rebac.object.dataObject.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 模型策略
 * @author wanghongtao
 * @data 2023/7/17 22:17
 */
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

}
