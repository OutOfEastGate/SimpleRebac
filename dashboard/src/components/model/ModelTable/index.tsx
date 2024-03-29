import React, {useState} from 'react';
import {Button, message, Space, Table} from 'antd';
import type { TableProps } from 'antd';
import {getPolicy} from "../../../request/api";
import ModelDetail from "./ModelDetail";


interface PropsType {
  data: Model[]
}





const App: React.FC<PropsType> = (props) => {

  const [visibleModelDetail, setVisibleModelDetail] = useState<boolean>(false);
  const [currentModelDetail, setCurrentModelDetail] = useState<Policy>()

  function openModelDetail(record: Model) {
    setVisibleModelDetail(true)
    getPolicy(record.policyId).then(res => {
      setCurrentModelDetail(res.data)
    }).catch(err => {
      message.error('获取详情失败')
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
          <Button danger={true}>删除模型</Button>
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

  return (<div>
    <ModelDetail isModalOpen={visibleModelDetail} handleModalClose={handleModalClose} handleModelOk={handleModelOk} data={currentModelDetail}></ModelDetail>
    <Table columns={columns} dataSource={props.data} />
  </div>)
};

export default App;
