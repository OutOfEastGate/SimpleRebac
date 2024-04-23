import React, {useEffect, useState} from 'react';
import {message, Space, Table} from 'antd';
import type { TableProps } from 'antd';
import {getAppList} from "../../../request/api";

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
    title: 'appKey',
    dataIndex: 'appKey',
    key: 'appKey',
    render: (text) => <p>{text}</p>,
  },
  {
    title: 'SecretKey',
    dataIndex: 'SecretKey',
    key: 'SecretKey',
    render: (text) => (<p>***</p>)
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
