import React, { useEffect, useState} from 'react'
import {Edge, Graph, Node} from '@antv/x6';
import  './index.less'
import {getGraph, getGraphPreview, saveGraphPreview} from "../../../request/api";
import { MiniMap } from '@antv/x6-plugin-minimap'
import {CircularLayout} from '@antv/layout'
import {Dnd} from "@antv/x6-plugin-dnd";
import AddEdgeModal from "./AddEdgeModal";
import {Button, Divider, message} from "antd";
import AddNodeModal from "./AddNodeModal";
import Loading from "../../common/Loading";

interface PropsType{
  isEmpty:boolean,
  selectModel: string | undefined
}
const templateGraph = [
  {
    "position": {
      "x": 40,
      "y": 40
    },
    "size": {
      "width": 100,
      "height": 40
    },
    "attrs": {
      "text": {
        "text": "示例1"
      }
    },
    "visible": true,
    "shape": "custom-node-width-port",
    "ports": {
      "groups": {
        "top": {
          "position": "top",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "bottom": {
          "position": "bottom",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "in": {
          "position": "top",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "out": {
          "position": "bottom",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        }
      },
      "items": [
        {
          "id": "port_1",
          "group": "in"
        },
        {
          "id": "port_2",
          "group": "out"
        }
      ]
    },
    "id": "node1",
    "zIndex": 1
  },
  {
    "position": {
      "x": 160,
      "y": 180
    },
    "size": {
      "width": 100,
      "height": 40
    },
    "attrs": {
      "text": {
        "text": "示例2"
      }
    },
    "visible": true,
    "shape": "custom-node-width-port",
    "ports": {
      "groups": {
        "top": {
          "position": "top",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "bottom": {
          "position": "bottom",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "in": {
          "position": "top",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        },
        "out": {
          "position": "bottom",
          "attrs": {
            "circle": {
              "magnet": true,
              "stroke": "#8f8f8f",
              "r": 5
            }
          }
        }
      },
      "items": [
        {
          "id": "port_3",
          "group": "in"
        },
        {
          "id": "port_4",
          "group": "out"
        }
      ]
    },
    "id": "node2",
    "zIndex": 1
  },
  {
    "shape": "edge",
    "attrs": {
      "line": {
        "stroke": "#8f8f8f",
        "strokeWidth": 1
      }
    },
    "id": "d0b8a667-2afb-4c75-97e5-ab8f28c49a06",
    "source": {
      "cell": "node1"
    },
    "target": {
      "cell": "node2"
    },
    "labels": [
      {
        "attrs": {
          "label": {
            "text": "关系"
          }
        }
      }
    ],
    "zIndex": 1
  }
]

const nodeToCreate = {
  width: 100,
  height: 40,
  label: 'Rect',
  attrs: {
    body: {
      stroke: '#8f8f8f',
      strokeWidth: 1,
      fill: '#fff',
      rx: 6,
      ry: 6,
    },
  },
  tools: ['button-remove'],
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
}

function MyEditor(props:PropsType) {
  // 初始，mounted生命周期时, container组件还没挂载。
  const [graph, setGraph] = useState<Graph>()
  const [container,setcontainer] = useState<HTMLElement>(document.createElement('content'));
  const [dndContainer, setDndContainer] = useState<HTMLElement>()
  const [minimapContainer, setMinimapContainer] = useState<HTMLElement>()
  const [dnd, setDnd] = useState<Dnd>()
  const [data, setData] = useState<any>({ nodes: [], edges: [] });

  const [showModal, setShowModal] = useState<boolean>(false)
  const [showAddNodeModal, setShowAddNodeModal] = useState<boolean>(false)
  const [currentEdge,setCurrentEdge] = useState<Edge>()
  const [currentNode,setCurrentNode] = useState<Node>()

  const [isSaveLoading, setIsSaveLoading] = useState<Boolean>(false)

  const circularLayout = new CircularLayout({
    type: 'circular',
    center: [350, 250],
  })
  const refContainer=(container: HTMLDivElement)=> {
    setcontainer(container);
  }
  const dndContainerRef = (container: HTMLDivElement) => {
    setDndContainer(container)
  }
  const refMiniMapContainer = (container: HTMLDivElement) => {
    setMinimapContainer(container)
  }

  const fetchData = async () => {
    if (!container ) return;

    let isHasPreview = false

    if(props.selectModel != undefined) {
      getGraphPreview(props.selectModel).then(res => {
        let originData = res.data
        if (originData != null) {
          setData(originData.cells)
          isHasPreview = true
        }  else if(props.selectModel != undefined) {
          getGraph(props.selectModel).then(res => {
            let originData = res.data
            circularLayout.layout(originData)
            setData(originData)
          })
        }
      }).catch(error => {
        message.error(error.message)
      })
    }


  };

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

  const startDrag = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const target = e.currentTarget
    const type = target.getAttribute('data-type')
    if(graph == undefined) return
    const node =
      type === 'rect'
        ? graph.createNode(nodeToCreate)
        : graph.createNode({
          width: 60,
          height: 60,
          shape: 'circle',
          label: 'Circle',
          attrs: {
            body: {
              stroke: '#8f8f8f',
              strokeWidth: 1,
              fill: '#fff',
            },
          },
        })
    if(dnd == undefined) return;
    dnd.start(node, e.nativeEvent as any)
  }

  useEffect(() => {
    console.log(props.isEmpty)
    if(props.isEmpty) {
      setData(templateGraph)
      return
    } else {
      fetchData().then(r => {

      });
    }
  }, [props.isEmpty]);




//官方文档写的是componentDidMount，因为react取消了三个生命周期函数，所以使用useEffect
  useEffect(() => {
      if (!container || minimapContainer==undefined || !data) return;
      // 清空容器内容
      container.innerHTML = '';
      minimapContainer.innerHTML = ''

      const graph=(new Graph({
        grid: true,
        container: container,
        width: 1800,
        height: 600,
        panning: true,
        mousewheel: {
          enabled: true,
          modifiers: ['ctrl', 'meta'],
        },
        connecting: {
          allowNode(args) {
            return true
          },
          allowMulti(args) {
            return true
          },
          allowLoop(args) {
            return true
          },
          allowBlank(args) {
            return false
          },
          allowPort(args){
            return true
          }
        }
      }));
      graph.use(
        new MiniMap({
          container: minimapContainer,
          width: 200,
          height: 160,
          padding: 10,
        }),
      )

      let dnd = new Dnd({
        target: graph,
        scaled: false,
        dndContainer: dndContainer,
      })
      setDnd(dnd)
      graph.fromJSON(data);

      registerListener(graph);

      setGraph(graph)
    }
    , [data])


  function saveGraph() {
    if(graph != undefined) {
      let previewJson = graph.toJSON()
      let previewGraph = {
        modelId: props.selectModel,
        graphDo: {
          id: props.selectModel,
          cells: previewJson.cells
        }
      }
      saveGraphPreview(previewGraph).then(r => {
        message.info("保存成功")
      })
    }

  }

  return (
    <div className="dnd-app">
      <AddEdgeModal showModal={showModal} confirmEdge={confirmEdge} cancelEdge={cancelEdge}></AddEdgeModal>
      <AddNodeModal showModal={showAddNodeModal} confirmNode={confirmNode} cancelNode={cancelNode}></AddNodeModal>
      <div className="dnd-wrap" ref={dndContainerRef}>
        <Divider plain>操作</Divider>
        <Button onClick={saveGraph}> {isSaveLoading ? <Loading></Loading> : "保存"}</Button>
        <Divider plain>节点</Divider>
        <div
          data-type="rect"
          className="dnd-rect"
          onMouseDown={startDrag}
        >
          Rect
        </div>
        <div
          data-type="circle"
          className="dnd-circle"
          onMouseDown={startDrag}
        >
          Circle
        </div>
      </div>
      {Array.isArray(data) ?  <></> : <Loading></Loading>}
      <div className="app-content" ref={refContainer}/>
      <div className="app-minimap" ref={refMiniMapContainer} />
    </div>
  )
  function confirmEdge(addRelationParam:AddRelationType) {
    setShowModal(false)
    if(currentEdge != undefined) {
      currentEdge.setLabels([{
        "attrs": {
          "label": {
            "text": addRelationParam.relation
          }
        }
      }])
    }
  }
  function confirmNode(addNodeParam:AddNodeType) {
    setShowAddNodeModal(false)
    if(currentNode!= undefined) {
      currentNode.attr("text/text", addNodeParam.label)
    }
  }
  function cancelEdge() {
    setShowModal(false)
    if(currentEdge != undefined) {
      graph?.removeEdge(currentEdge.id)
    }
  }

  function cancelNode() {
    setShowAddNodeModal(false)
    if(graph != undefined && currentNode != undefined) {
      graph.removeNode(currentNode.id)
    }
  }

  function registerListener(graph: Graph) {
    graph.on('edge:connected', ({ isNew, edge }) => {
      if (isNew) {
        // 对新创建的边进行插入数据库等持久化操作
        setShowModal(true)
        setCurrentEdge(edge)
      }
    })
    graph.on('node:added', ({node, index, options}) => {
      setShowAddNodeModal(true)
      setCurrentNode(node)
    })

    graph.on('cell:mouseenter', ({cell}) => {
      if (cell.isNode()) {
        cell.addTools([
          {
            name: 'boundary',
            args: {
              attrs: {
                fill: '#7c68fc',
                stroke: '#333',
                'stroke-width': 1,
                'fill-opacity': 0.2,
              },
            },
          },
          {
            name: 'button-remove',
            args: {
              x: 0,
              y: 0,
              offset: {x: 10, y: 10},
            },
          },
        ])
      } else {
        cell.addTools(['vertices', 'segments'])
      }
    })

    graph.on('cell:mouseleave', ({cell}) => {
      cell.removeTools()
    })
  }
}


export default MyEditor;



