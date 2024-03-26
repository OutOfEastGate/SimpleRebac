import React, { useEffect, useState} from 'react'
import { Graph } from '@antv/x6';
import './index.less'
import {getGraph} from "../../../request/api";
import {CircularLayout} from '@antv/layout'

interface PropsType{
  isEmpty:boolean,
  selectModel: string | undefined
}

function MyEditor(props:PropsType) {
  // 初始，mounted生命周期时, container组件还没挂载。
  const [container,setcontainer] = useState<HTMLElement>(document.createElement('content'));
  const [minimapContainer,setminimapContainer] = useState<HTMLElement>(document.createElement('map'));
  const [data, setData] = useState<any>({ nodes: [], edges: [] });

  const circularLayout = new CircularLayout({
    type: 'circular',
    center: [350, 250],
  })
  const refContainer=(container: HTMLDivElement)=> {
    setcontainer(container);
  }

  const refMiniMapContainer = (container: HTMLDivElement) => {
    setminimapContainer(container)
  }

  useEffect(() => {
    const fetchData = async () => {
      if (!container || !minimapContainer) return;
      if(props.selectModel != undefined) {
        getGraph(props.selectModel).then(res => {
          let originData = res.data
          circularLayout.layout(originData)
          setData(originData)
        })
      }
    };
    if(props.isEmpty) {
      setData({
        nodes: [
          {
            id: 'node1',
            shape: 'custom-node-width-port',
            x: 40,
            y: 40,
            width: 100,
            height: 40,
            label: '示例1',
            attrs: {
              body: {
                stroke: '#8f8f8f',
                strokeWidth: 1,
                fill: '#fff',
                rx: 6,
                ry: 6,
              },
            },
            ports: {
              groups: {
                in: {
                  position: 'top',
                  attrs: {
                    circle: {
                      magnet: true,
                      stroke: '#8f8f8f',
                      r: 5,
                    },
                  },
                },
                out: {
                  position: 'bottom',
                  attrs: {
                    circle: {
                      magnet: true,
                      stroke: '#8f8f8f',
                      r: 5,
                    },
                  },
                },
              },
              items: [
                {
                  id: 'port_1',
                  group: 'in',
                },
                {
                  id: 'port_2',
                  group: 'out',
                },
              ],
            },
          },
          {
            id: 'node2',
            shape: 'custom-node-width-port',
            x: 160,
            y: 180,
            width: 100,
            height: 40,
            label: '示例2',
            attrs: {
              body: {
                stroke: '#8f8f8f',
                strokeWidth: 1,
                fill: '#fff',
                rx: 6,
                ry: 6,
              },
            },
            ports: {
              groups: {
                in: {
                  position: 'top',
                  attrs: {
                    circle: {
                      magnet: true,
                      stroke: '#8f8f8f',
                      r: 5,
                    },
                  },
                },
                out: {
                  position: 'bottom',
                  attrs: {
                    circle: {
                      magnet: true,
                      stroke: '#8f8f8f',
                      r: 5,
                    },
                  },
                },
              },
              items: [
                {
                  id: 'port_3',
                  group: 'in',
                },
                {
                  id: 'port_4',
                  group: 'out',
                },
              ],
            },
          },
        ],
        edges: [
          {
            shape: 'edge',
            source: 'node1',
            target: 'node2',
            label: '关系',
            attrs: {
              line: {
                stroke: '#8f8f8f',
                strokeWidth: 1,
              },
            },
          },
        ]
      })
      return
    }

    fetchData();
  }, []);


  //官方文档写的是componentDidMount，因为react取消了三个生命周期函数，所以使用useEffect
  useEffect(()=>{
      if (!container || !minimapContainer || !data.nodes.length || !data.edges.length) return;
      // 清空容器内容
      container.innerHTML = '';
      minimapContainer.innerHTML = ''
      Graph.registerEdge(
        'custom-edge-label',
        {
          inherit: 'edge',
          defaultLabel: {
            markup: [
              {
                tagName: 'rect',
                selector: 'body',
              },
              {
                tagName: 'text',
                selector: 'label',
              },
            ],
            attrs: {
              label: {
                fill: '#000',
                fontSize: 14,
                textAnchor: 'middle',
                textVerticalAnchor: 'middle',
                pointerEvents: 'none',
              },
              body: {
                ref: 'label',
                fill: '#ffd591',
                stroke: '#ffa940',
                refWidth: '140%',
                refHeight: '140%',
                refX: '-20%',
                refY: '-20%',
              },
            },
            position: {
              distance: 50,
              options: {
                absoluteDistance: true,
                reverseDistance: true,
              },
            },
          },
        },
        true,
      )
    Graph.registerNode('custom-node-width-port',
      {
        inherit: 'rect',
        width: 100,
        height: 40,
        attrs: {
          body: {
            stroke: '#8f8f8f',
            strokeWidth: 1,
            fill: '#fff',
            rx: 6,
            ry: 6,
          },
        },
        ports: {
          groups: {
            top: {
              position: 'top',
              attrs: {
                circle: {
                  magnet: true,
                  stroke: '#8f8f8f',
                  r: 5,
                },
              },
            },
            bottom: {
              position: 'bottom',
              attrs: {
                circle: {
                  magnet: true,
                  stroke: '#8f8f8f',
                  r: 5,
                },
              },
            },
          },
        },
      },
      true)
      const graph=(new Graph({
        grid: true,
        container: container,
        width: 1800,
        height: 600,
        panning: true,
        mousewheel: true,
        connecting: {
          allowNode(args) {
            return false
          },
          allowMulti(args) {
            return true
          },
          allowLoop(args) {
            return false
          },
          allowBlank(args) {
            return false
          },
          allowPort(args){
            console.log('是否允许',args)
            return true
          }
        }
      }));


      graph.fromJSON(data);

    }
    , [data])


  return (
    <div className="minimap-app">
      <div id = "content" className="app-content" ref={refContainer} />
    </div>
  )
}


export default MyEditor;



