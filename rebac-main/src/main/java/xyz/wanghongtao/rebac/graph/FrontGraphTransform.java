package xyz.wanghongtao.rebac.graph;

import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.viewObject.graph.GraphVo;
import xyz.wanghongtao.rebac.object.viewObject.graph.LinkVo;
import xyz.wanghongtao.rebac.object.viewObject.graph.NodeVo;

import java.util.*;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:30
 */
@Component
public class FrontGraphTransform {


    /**
     * 将关系转化为前端展示图模型
     * @param relationByModelId 数据库查询数据
     * @return 展示数据
     */
    public GraphVo transform(List<RelationDo> relationByModelId) {
        GraphVo graphVo = new GraphVo();

        Map<String, NodeVo> objects = new HashMap<>();
        relationByModelId.forEach(relationDo -> {
            objects.put(relationDo.getObject(), null);
            objects.put(relationDo.getSubject(), null);
        });

        List<NodeVo> nodes = new ArrayList<>();
        objects.forEach((object, node) -> {
            NodeVo nodeVo = new NodeVo();
            nodeVo.setId(object);
            nodes.add(nodeVo);
            objects.put(object, nodeVo);
        });

        List<LinkVo> links = new ArrayList<>();
        relationByModelId.forEach(relationDo -> {
            LinkVo linkVo = new LinkVo();
            linkVo.setSource(objects.get(relationDo.getObject()));
            linkVo.setTarget(objects.get(relationDo.getSubject()));
            linkVo.setText(relationDo.getRelation());
            links.add(linkVo);
        });

        graphVo.setNodes(nodes);
        graphVo.setLinks(links);
        return graphVo;
    }
}
