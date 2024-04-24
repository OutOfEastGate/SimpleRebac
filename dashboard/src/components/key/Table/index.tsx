import React, {useEffect, useState} from 'react';
import {message, Space, Table} from 'antd';
import type { TableProps } from 'antd';
import {getAppList} from "../../../request/api";
import {DatabaseOutlined} from "@ant-design/icons";

interface DataType {
  key: string;
  name: string;
  age: number;
  address: string;
  tags: string[];
}

const columns: TableProps<AppKey>['columns'] = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    render: (text) => <p>{text}</p>,
  },
  {
    title: '应用Id',
    dataIndex: 'appKey',
    key: 'appKey',
    render: (text) => <p>{text}</p>,
  },
  {
    title: '密钥',
    dataIndex: 'secretKey',
    key: 'secretKey',
    render: (text) => (text)
  },
  {
    title: '图标',
    dataIndex: 'SecretKey',
    key: 'SecretKey',
    render: (text) => (<DatabaseOutlined />)
  },
  {
    title: 'Action',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <a>查看密钥</a>
        <a>删除密钥</a>
      </Space>
    ),
  },
];


const App: React.FC = () => {
  const [appList, setAppList] = useState<AppKey[]>([])

  useEffect(()=> {
    getAppList().then(r => {
      if(r.msg === "success") {
        setAppList(r.data)
      } else {
        message.error(r.msg)
      }
    })
  },[])
  return (
    <Table columns={columns} dataSource={appList} />
  )
};

export default App;
