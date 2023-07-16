import React, { useEffect, useRef } from 'react';
import * as d3 from 'd3';

interface Node extends d3.SimulationNodeDatum {
    id: string;
}

interface Link {
    source: Node;
    target: Node;
    text: string;
}

const Graph: React.FC = () => {
    const graphRef = useRef<SVGSVGElement | null>(null);

    useEffect(() => {
        const data: Node[] = [
            { id: '123' },
            { id: 'B' },
            { id: 'C' },
            { id: 'D' },
            { id: 'E' },
            { id: 'F' },
        ];

        const nodeMap: Record<string, Node> = {};
        data.forEach((node) => {
            nodeMap[node.id] = node;
        });

        const links: Link[] = [
            { source: nodeMap['123'], target: nodeMap['B'], text: 'a' },
            { source: nodeMap['123'], target: nodeMap['C'], text: 'b' },
            { source: nodeMap['B'], target: nodeMap['C'], text: 'b' },
            { source: nodeMap['D'], target: nodeMap['123'], text: 'b' },
            { source: nodeMap['E'], target: nodeMap['C'], text: 'b' },
            { source: nodeMap['D'], target: nodeMap['F'], text: 'b' },
        ];

        const width = 1000;
        const height = 500;

        const svg = d3.select(graphRef.current).attr('width', width).attr('height', height);

        const simulation = d3.forceSimulation<Node, Link>(data)
            .force('link', d3.forceLink<Node, Link>(links).id((d) => d.id).distance(150)) // 增加关联线的长度
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
            .data(data)
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
                .attr('x1', (d) => (d.source as Node).x!)
                .attr('y1', (d) => (d.source as Node).y!)
                .attr('x2', (d) => (d.target as Node).x!)
                .attr('y2', (d) => (d.target as Node).y!);

            link.select('.label')
                .attr('x', (d) => ((d.source as Node).x! + (d.target as Node).x!) / 2)
                .attr('y', (d) => ((d.source as Node).y! + (d.target as Node).y!) / 2);

            node.attr('transform', (d) => `translate(${(d as Node).x}, ${(d as Node).y})`);
        });
    }, []);

    return <svg ref={graphRef}></svg>;
};

export default Graph
