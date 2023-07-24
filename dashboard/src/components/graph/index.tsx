import React, {useEffect, useRef, useState} from 'react';
import * as d3 from 'd3';
import {getGraph} from "../../request/api";
import {message, Spin} from "antd";

interface propsType {
    selectModel: string | undefined
}

const Graph: React.FC<propsType> = (props) => {
    const graphRef = useRef<SVGSVGElement | null>(null);
    const [nodes, setNodes] = useState<GraphNode[]>([])
    const [nodeLinks, setLinks] = useState<Link[]>([])
    const [isLoading, setIsLoading] = useState<boolean>(false)
    useEffect(() => {
        if (props.selectModel != undefined) {
            setIsLoading(true)
            getGraph(props.selectModel).then(res => {
                if (res.msg === "success") {
                    setNodes(res.data.nodes)
                    setLinks(res.data.links)
                    message.success("获取图信息成功")
                } else {
                    message.error(res.msg)
                }
                setIsLoading(false)
            })
        }
    }, []);

    getGraphView();
    return (
        <Spin spinning={isLoading}>
            <svg ref={graphRef}></svg>
        </Spin>

    );

    function getGraphView() {
        const nodeMap: Record<string, GraphNode> = {};
        nodes.forEach((node) => {
            nodeMap[node.id] = node;
        });

        const links: Link[] = [];

        nodeLinks.forEach(link => {
            const oneLink = {
                source: nodeMap[link.source.id],
                target: nodeMap[link.target.id],
                text: link.text
            }
            links.push(oneLink)
        })

        const width = 1000;
        const height = 500;

        const svg = d3.select(graphRef.current).attr('width', width).attr('height', height);

        const simulation = d3.forceSimulation<GraphNode, Link>(nodes)
            .force('link', d3.forceLink<GraphNode, Link>(links).id((d) => d.id).distance(150)) // 增加关联线的长度
            .force('charge', d3.forceManyBody().strength(-100))
            .force('center', d3.forceCenter(width / 2, height / 2));

        const link = svg
            .selectAll('.link')
            .data(links)
            .enter()
            .append('g')
            .attr('class', 'link');

        link
            .append('line')
            .attr('class', 'line')
            .attr('stroke', 'black')
            .attr('stroke-width', 2)
            .attr('marker-end', 'url(#arrowhead)'); // 添加箭头标记

        link
            .append('text')
            .attr('class', 'label')
            .attr('text-anchor', 'middle')
            .attr('dominant-baseline', 'middle')
            .text((d) => d.text);

        // 添加箭头标记
        svg
            .append('defs')
            .append('marker')
            .attr('id', 'arrowhead')
            .attr('viewBox', '-5 -5 10 10')
            .attr('refX', 13) // 箭头相对于终点的偏移距离
            .attr('refY', 0)
            .attr('orient', 'auto')
            .attr('markerWidth', 10)
            .attr('markerHeight', 10)
            .attr('xoverflow', 'visible')
            .append('svg:path')
            .attr('d', '>') // 箭头的形状

        const node = svg
            .selectAll('.node')
            .data(nodes)
            .enter()
            .append('g')
            .attr('class', 'node');

        node
            .append('circle')
            .attr('r', 20) // 增大圆圈大小
            .attr('fill', 'steelblue');

        node
            .append('text')
            .attr('text-anchor', 'middle')
            .attr('dominant-baseline', 'middle')
            .text((d) => d.id);

        simulation.on('tick', () => {
            link.select('.line')
                .attr('x1', (d) => (d.source as GraphNode).x!)
                .attr('y1', (d) => (d.source as GraphNode).y!)
                .attr('x2', (d) => (d.target as GraphNode).x!)
                .attr('y2', (d) => (d.target as GraphNode).y!);

            link.select('.label')
                .attr('x', (d) => ((d.source as GraphNode).x! + (d.target as GraphNode).x!) / 2)
                .attr('y', (d) => ((d.source as GraphNode).y! + (d.target as GraphNode).y!) / 2);

            node.attr('transform', (d) => `translate(${(d as GraphNode).x}, ${(d as GraphNode).y})`);
        });
    }
};

export default Graph
