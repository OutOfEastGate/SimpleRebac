package xyz.wanghongtao.rebac.object.viewObject.graph;

import lombok.Data;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:31
 */
@Data
public class GraphVo {
    List<NodeVo> nodes;

    List<LinkVo> edges;
}
