package xyz.wanghongtao.rebac.object.viewObject.graph;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:32
 */
@Builder
@Data
public class NodeVo {
    /**
     * node的id，节点名称
     */
    String id;

    @Builder.Default
    String shape = "custom-node-width-port";

    String label;

    @Builder.Default
    Integer width = 100;

    @Builder.Default
    Integer height = 40;

    Map<String, Object> attrs;

    Map<String, Object> ports;
}
