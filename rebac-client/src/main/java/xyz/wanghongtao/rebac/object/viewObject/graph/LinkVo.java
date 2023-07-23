package xyz.wanghongtao.rebac.object.viewObject.graph;

import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:32
 */
@Data
public class LinkVo {
    NodeVo source;

    NodeVo target;

    /**
     * 连接关系， 如writer、reader
     */
    String text;
}
