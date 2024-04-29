import React, {useEffect, useState} from 'react';
import {Button, Card, Col, message, notification, Progress, Row, Space, Statistic, Table} from 'antd';
import type { TableProps } from 'antd';
import {getAllStore, saveStore} from "../../request/api";
import Loading from "../common/Loading";
import AddStoreModal from "./AddStoreModal";

interface DataType {
  key: string;
  name: string;
  description: string;
  appKey:string
}



const App: React.FC = () => {
  const [data, setData] = useState<Store[]>([])
  const [isShowAddStoreModal, setIsShowAddStoreModalOpen] = useState<boolean>(false)
  const [api, contextHolder] = notification.useNotification();
  const columns: TableProps<Store>['columns'] = [
    {
      title: 'id',
      dataIndex: 'id',
      key: 'id',
      render: (text) => <p>{text}</p>,
    },
    {
      title: '存储空间名称',
      dataIndex: 'name',
      key: 'name',
      render: (text) => <p>{text}</p>,
    },
    {
      title: '存储空间描述',
      dataIndex: 'description',
      key: 'description',
      render: (text) => (<p>{text}</p>)
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space size="middle">
          <Button type={"primary"} onClick={() => {
            function selectModel(record: Store) {
              localStorage.setItem("storeId", record.id.toString())
              api.open({
                key: "store",
                message: '提示',
                description: <div>
                  <p>存储空间已切换，当前存储空间：{record.name}</p>
                </div>,
              });
            }

            selectModel(record)}}> 选择存储空间 </Button>
          <Button danger> 删除 </Button>
        </Space>
      ),
    },
  ];
  function getStoreList() {
    getAllStore().then(r => {
      setData(r.data)
    }).catch(error => {
      message.error("获取存储空间失败")
    })
  }

  useEffect(()=> {
    getStoreList();
  },[])

  function openAddStore() {
    setIsShowAddStoreModalOpen(true)
  }

  function handleCancel() {
    setIsShowAddStoreModalOpen(false)
  }

  function handleAddStore(addStore:AddStoreType) {
    saveStore(addStore).then(res => {
      if(res.msg === "success") {
        message.info("添加存储空间成功")
        getStoreList()
      } else {
        message.error(res.msg)
      }
    }).catch(err => {
      message.error("添加存储空间失败" + err.error)
    })
    setIsShowAddStoreModalOpen(false)
  }

  return(
    <div style={{ height: '100%', width: '100%', lineHeight: '50px',}}>
      {contextHolder}
      <Card>
        <Row gutter={16}>
          <Col span={8}>
            <Statistic title="系统运行时间" value={10} />
          </Col>
          <Col span={8}>
            <Statistic title="CPU核心数" value={12}  />
          </Col>
          <Col span={8}>
            <Statistic title="CPU线程数数" value={12}  />
          </Col>
          <Col span={24} style={{ marginTop: 32 }}>
            <Statistic title="系统总内存" value={32} />
          </Col>
        </Row>
      </Card>
      <AddStoreModal showModal={isShowAddStoreModal} handleCancel={handleCancel} handleAddStore={handleAddStore}></AddStoreModal>
      <Button type={"primary"} onClick={openAddStore}>增加存储空间</Button>
      {data.length === 0 ? <Loading></Loading> : <Table columns={columns} dataSource={data} />}
    </div>
  )
}

export default App;
