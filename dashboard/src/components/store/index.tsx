import React, {useEffect, useState} from 'react';
import {Button, Card, Col, message, Progress, Row, Space, Statistic, Table} from 'antd';
import type { TableProps } from 'antd';
import {getAllStore} from "../../request/api";
import Loading from "../common/Loading";

interface DataType {
  key: string;
  name: string;
  description: string;
  appKey:string
}

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
        <Button type={"primary"}> 编辑 </Button>
        <Button danger> 删除 </Button>
      </Space>
    ),
  },
];

const App: React.FC = () => {
  const [data, setData] = useState<Store[]>([])
  useEffect(()=> {
    getAllStore().then(r => {
      setData(r.data)
    }).catch(error => {
      message.error("获取存储空间失败")
    })
  },[])
  return(
    <div style={{ height: '100%', width: '100%', lineHeight: '50px',}}>

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
      <Button type={"primary"}>增加存储空间</Button>
      {data.length === 0 ? <Loading></Loading> : <Table columns={columns} dataSource={data} />}
    </div>
  )
}

export default App;
