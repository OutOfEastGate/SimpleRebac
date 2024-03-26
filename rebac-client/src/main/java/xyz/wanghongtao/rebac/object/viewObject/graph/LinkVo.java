package xyz.wanghongtao.rebac.object.viewObject.graph;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:32
 */
@Builder
@Data
public class LinkVo {
    @Builder.Default
    String shape = "edge";

    String source;

    String target;

    /**
     * 连接关系， 如writer、reader
     */
    String text;

    String label;

    Map<String, Object> attrs;
}
