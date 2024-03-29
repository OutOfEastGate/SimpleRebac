import React from 'react';
import {Button, Space, Table} from 'antd';
import type { TableColumnsType, TableProps } from 'antd';


interface PropsType {
  data: Definition[]
}




const onChange: TableProps<Definition>['onChange'] = (pagination, filters, sorter, extra) => {
  console.log('params', pagination, filters, sorter, extra);
};

const App: React.FC<PropsType> = (props) => {
  function openDefinitionDetail(record: Definition) {

  }

  const columns: TableColumnsType<Definition> = [
    {
      title: '描述',
      dataIndex: 'description',
      width: '30%',
    },
    {
      title: '对象类型',
      dataIndex: 'objectType'
    },
    {
      title: '权限个数',
      dataIndex: 'permissions',
      width: '40%',
      render: (text) => <p>{text == undefined ? 0 : text.length}</p>,
    },
    {
      title: '关系个数',
      dataIndex: 'relations',
      width: '40%',
      render: (text) => <p>{text == undefined ? 0 : text.length}</p>,
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space size="middle">
          <Button type={"primary"} onClick={() => {

            openDefinitionDetail(record)}}>查看权限定义</Button>
          <Button type={"primary"} onClick={() => {

            openDefinitionDetail(record)}}>查看关系定义</Button>
          <Button danger={true}>删除定义</Button>
        </Space>
      ),
    },
  ];


  return <Table columns={columns} dataSource={props.data} onChange={onChange} />
};

export default App;
