import React, {useState} from 'react';
import {Button, message, Popconfirm, Space, Table} from 'antd';
import type { TableProps } from 'antd';
import {deleteModelById, getPolicy, saveModel, updatePolicy as updatePolicyById} from "../../../request/api";
import ModelDetail from "./ModelDetail";
import AddModelModal from "../AddModelModal";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;


interface PropsType {
  data: Model[]
  updateModel: () => void
  currentSelectStore: number | undefined
}





const App: React.FC<PropsType> = (props) => {

  const [visibleModelDetail, setVisibleModelDetail] = useState<boolean>(false);
  const [currentModelDetail, setCurrentModelDetail] = useState<Policy>()

  const [isShowAddModelModal, setIsShowAddModelModal] = useState<boolean>(false)

  function openModelDetail(record: Model) {
    setVisibleModelDetail(true)
    getPolicy(record.policyId).then(res => {
      setCurrentModelDetail(res.data)
    }).catch(err => {
      message.error('获取详情失败' + err.error)
    })
  }

  function addModel(model:AddModelType) {
    saveModel({
      storeId:props.currentSelectStore,
      name:model.name,
      description:model.description
    }).then(r => {
      if(r.msg === "success") {
        message.success("添加模型成功")
        props.updateModel()
      } else {
        message.error(r.msg)
      }
    }).catch(error => {
      message.error("添加模型失败")
    })
    setIsShowAddModelModal(false)
  }

  function deleteModel(id: string) {
    deleteModelById(id).then(res => {
      if(res.msg === "success") {
        message.success("删除模型成功")
        props.updateModel()
      } else {
        message.error(res.msg)
      }
    })
  }

  const columns: TableProps<Model>['columns'] = [
    {
      title: '唯一标识',
      dataIndex: 'id',
      key: 'id',
      render: (text) => <p>{text}</p>,
    },
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
      render: (text) => <p>{text}</p>,
    },
    {
      title: '描述',
      dataIndex: 'description',
      key: 'description',
      render: (text) => (<p>{text}</p>)
    },
    {
      title: '策略id',
      dataIndex: 'policyId',
      key: 'policyId',
      render: (text) => (<p>{text}</p>)
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space size="middle">
          <Button type={"primary"} onClick={() => {openModelDetail(record)}}>查看模型</Button>
          <Popconfirm
            title="删除模型"
            description="确定要删除模型（关系也会一并删除）？"
            onConfirm={() => {deleteModel(record.id)}}
            onCancel={() => {}}
            okText="确定"
            cancelText="取消"
          >
            <Button danger={true} >删除模型</Button>
          </Popconfirm>

        </Space>
      ),
    },
  ];

  function handleModalClose() {
    setVisibleModelDetail(false)
  }

  function handleModelOk() {
    setVisibleModelDetail(false)
  }

  function updatePolicy(definition: Definition) {
    let newModelDetail = currentModelDetail
    let definitions = currentModelDetail?.definitions
    if(newModelDetail !== undefined && definitions !== undefined) {
      let newDefinitions: Definition[] = []
      definitions.forEach(def => {
        if(def.policyId === definition.policyId && def.objectType === definition.objectType) {
          newDefinitions.push(definition)
        } else {
          newDefinitions.push(def)
        }
      })

      newModelDetail.definitions = newDefinitions

      updatePolicyById(newModelDetail).then(r => {
        if(r.msg === "success") {
          message.success("更新策略成功")
        } else {
          message.error(r.msg)
        }
      }).catch(error => {
        console.log(error)
        message.error("更新策略失败")
      })
    }
  }

  function addDefinition(definition:Definition) {
    let newModelDetail = currentModelDetail
    let definitions = currentModelDetail?.definitions
    if(newModelDetail !== undefined && definitions !== undefined) {
      definitions.push(definition)
      newModelDetail.definitions = definitions
      updatePolicyById(newModelDetail).then(r => {
        message.success("添加定义成功")
        getPolicy(definition.policyId).then(res => {
          setCurrentModelDetail(res.data)
        }).catch(err => {
          message.error('获取详情失败')
        })
      })
    }
  }

  function deleteDefinition(definition:Definition) {
    let newModelDetail = currentModelDetail
    let definitions = currentModelDetail?.definitions
    if(newModelDetail !== undefined && definitions !== undefined) {

      definitions = definitions.filter(def => {
        if(def.objectType !== definition.objectType) {
          return def
        }
      })
      newModelDetail.definitions = definitions
      updatePolicyById(newModelDetail).then(r => {
        message.success("删除定义成功")
        getPolicy(definition.policyId).then(res => {
          setCurrentModelDetail(res.data)
        }).catch(err => {
          message.error('获取详情失败')
        })
      })
    }
  }

  function openAddModel() {
    setIsShowAddModelModal(true)
  }

  function handleAddModelCancel() {
    setIsShowAddModelModal(false)
  }

  return (<div style={{ height: '100%', width: '100%' }}>

    <ModelDetail isModalOpen={visibleModelDetail}
                 handleModalClose={handleModalClose}
                 handleModelOk={handleModelOk}
                 updatePolicy={updatePolicy}
                 addDefinition={addDefinition}
                 deleteDefinition={deleteDefinition}
                 data={currentModelDetail}></ModelDetail>
    <Button type="primary" onClick={() => openAddModel()}> 添加模型 </Button>
    <AddModelModal showModal={isShowAddModelModal} handleCancel={handleAddModelCancel} handleAddModel={addModel}></AddModelModal>
    <Table columns={columns} dataSource={props.data} />
  </div>)
};

export default App;
